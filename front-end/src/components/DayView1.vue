<template>
  <div class="list-container">
    <div class="list-side">
      <DatePicker
        type="daterange"
        show-week-numbers
        v-model="date"
        placement="bottom-end"
        placeholder="Select date"
        @on-change="timeChange"
      ></DatePicker>
      <Select v-model="selectedItem" class="selector" @on-change="valueChange">
        <OptionGroup label="Task">
          <Option value="已完成的任务">已完成的任务</Option>
          <Option value="放弃的任务">放弃的任务</Option>
        </OptionGroup>
        <OptionGroup label="Pomoto">
          <Option value="已完成的番茄">已完成的番茄</Option>
          <Option value="中断的番茄">中断的番茄</Option>
        </OptionGroup>
      </Select>
    </div>
    <div
      class="list-head"
      v-if="showData.length !==0"
      v-for="(item,index) in showData"
      :key="item.id"
    >
      <div class="list-second-head">
        <p class="card-head">{{item.date}}</p>
        <p class="card-extra">{{getWeekByDay(item.date)}}</p>
      </div>
      <Timeline class="time-line" v-if="selectedItem === '已完成的番茄' || selectedItem === '中断的番茄'">
        <TimelineItem v-for="i in item.content" :key="i.id">
          <p class="time">{{i.start+'-'+i.end}}</p>
          <p class="content">{{i.text}}</p>
        </TimelineItem>
      </Timeline>
      <Timeline class="time-line" v-if="selectedItem === '已完成的任务' || selectedItem === '放弃的任务'">
        <TimelineItem v-for="i in item.tempResult" :key="i.id">
          <p class="time">{{i.time}}</p>
          <p class="content">{{i.content}}</p>
        </TimelineItem>
      </Timeline>
      <div class="box-card" v-if="selectedItem === '已完成的番茄'">
        <p>完成了{{tomotoNum(item.content)}}个番茄</p>
        <p>总计{{totalTime(item.content)}}</p>
      </div>
      <div class="box-card" v-else-if="selectedItem === '已完成的任务'">
        <p>完成了{{tomotoNum(item.tempResult)}}个任务</p>
      </div>
      <div class="box-card" v-else-if="selectedItem === '放弃的任务'">
        <p>放弃了{{tomotoNum(item.tempResult)}}个任务</p>
      </div>
      <div class="box-card" v-else-if="selectedItem === '中断的番茄'">
        <p>放弃了{{tomotoNum(item.content)}}个番茄</p>
      </div>
      <el-card v-if="updateSum" shadow="never" class="box-card card-sum">每日小结
        <Rate style="float:right;" allow-half disabled :value="0"/>
        <p class="sum-content">你今天没有写小结哦。</p>
      </el-card>
      <el-card v-else shadow="never" class="box-card card-sum">每日小结
        <Rate style="float:right;" allow-half disabled :value="showSumData[index].selfRating"/>
        <p class="sum-content">{{showSumData[index].content}}</p>
      </el-card>
    </div>
    <div v-if="showData.length ==0">没有可用数据</div>
    <Page :total="size" :page-size="pageSize" @on-change="pageChange" show-elevator class="footer"/>
  </div>
</template>


<script>
const tomourl = "http://localhost:8080/viewYearHistory";
const taskurl = "http://localhost:8080/task/querybyyear";
const sumurl = "http://localhost:8080/summary/querybyyear";

//测试数据
var sourceData = [
  [
    { time: "10:00-10:25", content: "Apple I 问世" },
    { time: "11:00-11:25", content: "发布 Macintosh" },
    { time: "11:00-11:25", content: "发布 Macintosh" }
  ],
  [
    { time: "10:00-10:25", content: "Apple I 问世" },
    { time: "11:00-11:25", content: "发布 Macintosh" },
    { time: "11:00-11:25", content: "发布 Macintosh" }
  ]
];

function getTimeDiff(start, end) {
  const strtime = "2014-04-23 ";
  var from_date = new Date(strtime + start);
  var end_date = new Date(strtime + end);
  var time_different = (end_date - from_date) / 60000; //毫秒转分钟
  return time_different;
}

//type 0 为番茄数据
//type 1 为任务数据
function filter(source, status, type) {
  if (type == 0) {
    var temp = source.map(item => {
      //先过滤状态
      return {
        content: item.content.filter(sitem => {
          return sitem.status == status;
        }),
        date: item.date
      };
    });
    return temp.filter(item => {
      //再过滤长度为0
      return item.content.length > 0;
    });
  } else if (type == 1) {
    var temp = source.map(item => {
      // debugger;
      //先过滤状态
      return {
        tempResult: item.tempResult.filter(sitem => {
          return sitem.status == status;
        }),
        date: item.date
      };
    });
    return temp.filter(item => {
      //再过滤长度为0
      return item.tempResult.length > 0;
    });
  } else {
    return source;
  }
}

var now = new Date(); //当前时刻
var nowTime = now.format("yyyy-MM-dd");
var monthAgo = now.format("yyyy-MM") + "-01";
var monthStart = new Date(monthAgo);

var tempSum = { selfRating: 0, content: "今天没有写自评哦" };

export default {
  data() {
    return {
      i: 0,
      userID: 2,
      updateSum: true,
      storedTomoYear: [],
      storedTaskYear: [],
      date: [monthStart, now],
      showSumData: [tempSum, tempSum],
      showData: [], //显示的内容
      sourceData: [], //选择的内容
      rate: 5,
      pageSize: 2,
      size: 0,
      selectedItem: "已完成的番茄"
    };
  },
  beforeCreate() {
    // console.log("刷新");
    localStorage.clear();
  },
  mounted() {
    openDB("dayTomoDB");
    openDB("dayTaskDB");
    openDB("daySumDB");
    this.userID = sessionStorage.userId;
    if (localStorage.dayhis) {
      //如果不是第一次进入页面，从数据库请求
      this.storedTomoYear = JSON.parse(localStorage.storedTomoYear);
      this.storedTaskYear = JSON.parse(localStorage.storedTaskYear);
      searchData(
        monthAgo,
        nowTime,
        () => {
          var selectedData = dbData;
          this.updateData(selectedData);
        },
        "dayTomoDB"
      );
    } else {
      //如果是第一次进入页面，从后端请求
      //先请求小结数据
      this.$http
        .get(sumurl, {
          params: {
            userid: this.userID,
            year: new Date().getFullYear()
          }
        })
        .then(res => {
          // 响应成功回调
          //console.log(res.body);
          var returnData = res.body;
          var startDate = this.date[0].format("yyyy-MM-dd");
          var endDate = this.date[1].format("yyyy-MM-dd");
          //this.updateData(selectedData);
          for (var i of returnData) {
            saveData(i, "daySumDB");
          }
        })
        .catch(() => {
          console.log("获取小结信息失败");
        });
      localStorage.dayhis = true;
      this.$http
        .get(tomourl, {
          params: {
            userId: this.userID,
            year: new Date().getFullYear() + "-01-01"
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
              saveData(i, "dayTomoDB");
            }
            this.storedTomoYear = [new Date().getFullYear().toString()];
          },
          res => {
            console.log("获取番茄信息失败");
          }
        )
        .catch(res => {
          console.log("处理请求失败" + res);
        });
      //将请求的年份存储到session
      localStorage.storedTomoYear = JSON.stringify(this.storedTomoYear);
      localStorage.storedTaskYear = JSON.stringify(this.storedTaskYear);
    }
  },
  methods: {
    getWeekByDay(dayValue) {
      var day = new Date(Date.parse(dayValue.replace(/-/g, "/"))); //将日期值格式化
      var today = new Array(
        "星期日",
        "星期一",
        "星期二",
        "星期三",
        "星期四",
        "星期五",
        "星期六"
      ); //创建星期数组
      return today[day.getDay()]; //返一个星期中的某一天，其中0为星期日
    },
    //时间变化回调
    timeChange(val) {
      // this.selectedDate = val[0];
      // this.weekday = this.getWeekByDay(val[0]);
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
      let storedYear, reqDB;
      if (
        this.selectedItem == "已完成的番茄" ||
        this.selectedItem == "中断的番茄"
      ) {
        storedYear = this.storedTomoYear;
        reqDB = "dayTomoDB";
      } else if (
        this.selectedItem == "已完成的任务" ||
        this.selectedItem == "放弃的任务"
      ) {
        storedYear = this.storedTaskYear;
        reqDB = "dayTaskDB";
      }
      if (storedYear.indexOf(reqMaxYear) == -1) {
        //溢出
        debugger;
        console.log("溢出");
        this.requestData(reqMinYear, 2, reqDB);
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
          reqDB
        );
      }
    },
    //显示类型变化回调
    valueChange(val) {
      let storedYear, reqDB;
      var reqMaxYear = this.date[0].format("yyyy");
      if (val == "已完成的番茄" || val == "中断的番茄") {
        storedYear = this.storedTomoYear;
        reqDB = "dayTomoDB";
      } else if (val == "已完成的任务" || val == "放弃的任务") {
        storedYear = this.storedTaskYear;
        reqDB = "dayTaskDB";
      }
      if (storedYear.indexOf(reqMaxYear) == -1) {
        //溢出
        console.log("溢出");
        this.requestData(reqMaxYear, 2, reqDB);
      } else {
        console.log("不溢出");
        searchData(
          this.date[0].format("yyyy-MM-dd"),
          this.date[1].format("yyyy-MM-dd"),
          () => {
            var selectedData = dbData;
            this.updateData(selectedData);
          },
          reqDB
        );
      }
    },
    //页数变化回调
    pageChange(index) {
      var start = (index - 1) * this.pageSize;
      var end = index * this.pageSize;
      this.showData = this.sourceData.slice(start, end);
      //更新每日小结数据
      //想回复到初始状态
      this.updateSum = true;
      this.i = 0;
      for (var i = 0; i < this.showData.length; i++) {
        let date = this.showData[i].date;
        // var that = this;
        readData(date, "daySumDB", () => {
          this.updateSumData(dbData);
        });
      }
    },
    //更新自我总结数据
    updateSumData(selectedData) {
      // debugger;
      if (selectedData) {
        this.showSumData[this.i] = selectedData;
      } else {
        this.showSumData[this.i] = tempSum;
      }
      this.i++;
      if (this.i == this.showData.length) {
        this.updateSum = false;
      }
      //debugger;
    },
    //计算一共做了几个番茄或任务
    tomotoNum: function(val) {
      if (val) {
        return val.length;
      }
    },
    //计算一共花了多少时间
    totalTime: function(items) {
      if (items) {
        var total = 0;
        for (var i = 0; i < items.length; i++) {
          total += getTimeDiff(items[i].start, items[i].end);
        }
        var hours = parseInt(total / 60); //小时
        var minutes = total % 60; //分钟
        if (hours === 0) {
          return minutes + "分钟";
        }
        return hours + "小时" + minutes + "分钟";
      }
    },
    //更新页面数据
    updateData(selectedData) {
      //this.tableData = selectedData;
      let status, type;
      if (this.selectedItem == "已完成的番茄") {
        status = 1;
        type = 0;
      } else if (this.selectedItem == "已完成的任务") {
        status = 2;
        type = 1;
      } else if (this.selectedItem == "放弃的任务") {
        status = -1;
        type = 1;
      } else if (this.selectedItem == "中断的番茄") {
        status = -1;
        type = 0;
      }
      this.sourceData = filter(selectedData, status, type);
      // debugger;
      this.size = this.sourceData.length;
      this.pageChange(1);
    },
    //direction参数用来确定请求情况
    //0为上溢出
    //1为下溢出
    //2为全部溢出
    requestData(year, direction, dbName) {
      //先请求小结数据
      this.$http
        .get(sumurl, {
          params: {
            userid: this.userID,
            year: year
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

          for (var i of returnData) {
            saveData(i, "daySumDB");
          }
        })
        .catch(() => {
          console.log("获取小结失败");
        });
      //如果请求的是番茄数据
      if (dbName == "dayTomoDB") {
        this.$http
          .get(tomourl, {
            params: {
              userId: this.userID,
              year: year + "-01-01"
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
              saveData(i, "dayTomoDB");
            }
            this.storedTomoYear.push(year);
            localStorage.storedTomoYear = JSON.stringify(this.storedTomoYear);
          })
          .catch(() => {
            console.log("获取番茄信息失败");
          });
      }
      //如果请求的是任务的数据
      else if (dbName == "dayTaskDB") {
        this.$http
          .get(taskurl, {
            params: {
              userId: this.userID,
              startyear: year,
              endyear: year
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
              saveData(i, "dayTaskDB");
            }
            this.storedTaskYear.push(year);
            localStorage.storedTaskYear = JSON.stringify(this.storedTaskYear);
          })
          .catch(res => {
            console.log("获取任务信息失败" + res);
          });
      }
    }
  },
  computed: {}
};
</script>

<style scoped>
.time {
  font-size: 14px;
  font-weight: bold;
}
.content {
  padding-left: 5px;
}

.list-side {
  width: 90%;
  margin: 10px;
  margin-left: 60px;
}

.list-container {
  display: flex;
  flex-flow: column;
  height: 100%;
}

.list-head {
  margin: 20px;
  display: flex;
  padding: 20px;
  flex-flow: row;
  border: 1px solid #ebeef5;
  border-radius: 18px;
}

.list-second-head {
  float: left;
}

.time-line {
  margin-left: 50px;
  margin-top: 50px;
}

.box-card {
  width: 300px;
  margin: 50px;
  margin-left: 70px;
  font-size: 13px;
}

.card-head {
  font-size: 15px;
  font-weight: bold;
}

.card-extra {
  font-size: 12px;
  font-style: oblique;
}

.card-sum {
  margin: 50px;
  padding: 10px;
  width: 380px;
  font-size: 15px;
  font-weight: bold;
  border-width: 2px;
}

.sum-content {
  font-size: 12px;
  padding-top: 5px;
  font-weight: lighter;
}

.selector {
  width: 150px;
  margin-left: 0px;
  float: right;
}
</style>
