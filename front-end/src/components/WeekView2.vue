<template>
  <div>
    <div class="block">
      <DatePicker
        type="daterange"
        show-week-numbers
        v-model="date"
        placement="bottom-end"
        placeholder="Select date"
        style="width: 200px"
        @on-change="timeChange"
      ></DatePicker>
    </div>
    <ve-line :data="chartData" :settings="chartSettings" :grid="grid"></ve-line>
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="date" label="日期" width="180"></el-table-column>
      <el-table-column prop="tomatocount" label="tomatocount" width="180"></el-table-column>
      <el-table-column prop="taskCount" label="taskCount"></el-table-column>
    </el-table>
    <Page :total="size" :page-size="pageSize" @on-change="pageChange" show-elevator class="footer"/>
  </div>
</template>


<script>
import VeLine from "v-charts/lib/line";

//判断溢出类型
//1 溢出
//0 不溢出
function check(start, arr) {
  //起止日期同一年
  if (arr.indexOf(start) > -1) {
    //在数组内
    return 0;
  } else {
    //不在数组内
    return 1;
  }
}

var sourceData = [
  //测试数据
  { date: "2018-11-01", tomatocount: 3, taskCount: 0 },
  { date: "2018-11-20", tomatocount: 6, taskCount: 0 },
  { date: "2018-11-21", tomatocount: 6, taskCount: 0 },
  { date: "2018-12-02", tomatocount: 3, taskCount: 1 },
  { date: "2018-12-03", tomatocount: 3, taskCount: 1 },
  { date: "2018-12-20", tomatocount: 6, taskCount: 0 },
  { date: "2018-12-21", tomatocount: 6, taskCount: 0 },
  { date: "2018-12-23", tomatocount: 5, taskCount: 2 },
  { date: "2018-12-24", tomatocount: 5, taskCount: 2 },
  { date: "2018-12-25", tomatocount: 5, taskCount: 1 },
  { date: "2018-12-29", tomatocount: 5, taskCount: 1 }
];

const DATA_FROM_BACKEND = {
  columns: ["date", "tomatocount", "taskCount"],
  rows: sourceData
};
const EMPTY_DATA = {
  columns: ["date", "tomatocount", "taskCount"],
  rows: []
};

var now = new Date(); //当前时刻
var nowTime = now.format("yyyy-MM-dd");
var monthAgo = now.format("yyyy-MM") + "-01";

var historyurl = "http://localhost:8080/getOneYearHistoryData";

export default {
  data() {
    this.chartSettings = {
      xAxisType: "time"
    };
    this.grid = { right: 30 }; //坐标图右侧宽度为60
    return {
      //默认为最近一月的数据
      userID:  sessionStorage.userid,
      date: [monthAgo, nowTime],
      chartData: EMPTY_DATA,
      tableData: EMPTY_DATA.rows,
      storedYear: [],
      size: 0,
      pageSize: 8
    };
  },
  components: { VeLine },
  beforeCreate() {
    // console.log("刷新");
    localStorage.clear();
  },
  mounted() {
    this.userID = sessionStorage.userid;
    openDB("weekDB");
    if (localStorage.weekhis) {
      //如果不是第一次进入页面，从数据库请求
      this.storedYear = JSON.parse(localStorage.storedYear);
      searchData(
        monthAgo,
        nowTime,
        () => {
          var selectedData = dbData;
          this.updateData(selectedData);
        },
        "weekDB"
      );
    } else {
      //如果是第一次进入页面，从后端请求
      localStorage.weekhis = true;
      this.$http
        .get(historyurl, {
          params: {
            userid: sessionStorage.userid,
            date: new Date().getFullYear() + "-01-01"
          }
        })
        .then(
          res => {
            // 响应成功回调
            //console.log(res.body);
            var returnData = res.body;
            var selectedData = date_slice(
              this.date[0].format("yyyy-MM-dd"),
              this.date[1].format("yyyy-MM-dd"),
              returnData
            );
            this.updateData(selectedData);
            for (var i of returnData) {
              //debugger;
              saveData(i, "weekDB");
            }
            this.storedYear = [new Date().getFullYear().toString()];
            localStorage.storedYear = JSON.stringify(this.storedYear);
          },
          res => {
            console.log("获取信息失败" + res);
          }
        )
        .catch(res => {
          console.log("处理请求失败");
        });
      //将请求的年份存储到session
    }
  },
  methods: {
    timeChange(val) {
      //时间变化回调
      //console.log(this.date);
      console.log("timechange");
      var reqMaxYear = val[1].split("-")[0];
      var reqMinYear = val[0].split("-")[0];
      //debugger;
      if (reqMaxYear !== reqMinYear) {
        this.$Message.info("选择的时间范围不能跨年");
        console.log("跨年选择失败");
        this.date = [];
        return;
      }
      if (check(reqMaxYear, this.storedYear)) {
        //溢出
        console.log("溢出");
        this.requestData(reqMinYear, 2);
      } else {
        //不溢出
        console.log("不溢出");
        searchData(
          val[0],
          val[1],
          () => {
            var selectedData = dbData;
            this.updateData(selectedData);
          },
          "weekDB"
        );
      }
    },
    pageChange(index) {
      //页数变化回调
      var start = (index - 1) * this.pageSize;
      var end = index * this.pageSize;
      this.tableData = this.chartData.rows.slice(start, end);
      //this.pageCurrent=index;
    },
    updateData(selectedData) {
      //更新页面数据
      //this.tableData = selectedData;
      this.chartData.rows = selectedData;
      this.size = selectedData.length;
      this.pageChange(1);
    },
    //direction参数用来确定请求情况
    //0为上溢出
    //1为下溢出
    //2为全部溢出
    requestData(year, direction) {
      this.$http
        .get(historyurl, {
          params: {
            userid:  sessionStorage.userid,
         
          }
        })
        .then(res => {
          // 响应成功回调
          //console.log(res.body);
          var returnData = res.body;
          var startDate = this.date[0].format("yyyy-MM-dd");
          var endDate = this.date[1].format("yyyy-MM-dd");
          if (direction === 0) {
            //上溢出更新起始日期
            startDate = year + "-01-01";
          } else if (direction === 1) {
            //下溢出更新截止日期
            endDate = year + "-12-31";
          }
          //完全溢出什么都不更新
          var selectedData = date_slice(startDate, endDate, returnData);
          //debugger;
          this.updateData(selectedData);
          for (var i of returnData) {
            //debugger;
            saveData(i, "weekDB");
          }
          this.storedYear.push(year);
          localStorage.storedYear = JSON.stringify(this.storedYear);
        })
        .catch(() => {
          console.log("获取信息失败");
        });
    }
  }
};
</script>

<style>
.footer {
  margin-top: 50px;
}

.demo-spin-container {
  display: inline-block;
  width: 200px;
  height: 100px;
  position: relative;
  border: 1px solid #eee;
}
</style>
