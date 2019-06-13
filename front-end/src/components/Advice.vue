<template>
  <div class="container">
    <el-card class="box-card-his">
      <div slot="header" class="clearfix">
        <span>最近一月工作情况</span>
        <el-button @click="navToDayView" style="float: right; padding: 3px 0" type="text">查看详细数据</el-button>
      </div>

      <ve-line :data="chartData" :settings="chartSettings"></ve-line>

      <p class="align-center">您的最佳工作日是{{bestDay}}</p>
    </el-card>

    <el-card class="box-card">
      <ve-pie :data="pieData" class="pie-chart"></ve-pie>
      <p class="align-center">您的最佳工作时段是{{bestTime}}</p>
    </el-card>
  </div>
</template>


<style scoped>
.align-center {
  margin: 0 auto;
  text-align: center;
}

.pie-chart {
  margin-top: 10px;
}

.container {
  display: flex;
  flex-direction: row;
}

.box-card-his {
  width: 600px;
  margin: 50px;
  margin-left: 80px;
  font-size: 13px;
}

.box-card {
  margin: 50px;
  margin-left: 80px;
  font-size: 13px;
  width: 400px;
}
</style>

<script>
import "echarts/lib/chart/line";
import VeLine from "v-charts/lib/line";
import VePie from "v-charts/lib/pie.js";

var weekdata = [  //测试数据
  { weekday: "周一", allcount: 1393, doingcount: 1093,finishcount:1000},
  { weekday: "周二", allcount: 1393, doingcount: 1093,finishcount:1000},
  { weekday: "周三", allcount: 1393, doingcount: 1093,finishcount:1000},
  { weekday: "周四",allcount: 1393, doingcount: 1093,finishcount:1000},
  { weekday: "周五", allcount: 1393, doingcount: 1093,finishcount:1000},
  { weekday: "周六", allcount: 1393, doingcount: 1093,finishcount:1000 },
  { weekday: "周日", allcount: 1393, doingcount: 1093,finishcount:1000}
];


var taskRequestUrl = "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/getTasksByUserid";

function findBest(data) {
  var best = 0;
  var index = 0;
  for (var i = 0; i < data.length; i++) {
    if (data[i] >= best) {
      best = data[i];
      index = i;
    }
  }
  return index;
}

export default {
  components: { VeLine, VePie },
  data() {
    this.chartSettings = {
      stack: { 用户: ["taskcount", "tomatocount"] },
      area: true
    };

    return {
      userID:  sessionStorage.userid,
      bestDay: "周三",
      bestTime: "上午",
      pieData: {
        columns: ["time", "taskcount"],
        rows: []
      },
      chartData: {
        columns: ["weekday", "allcount", "doingcount","finishcount"],
        rows: []
      },
      taskData:[]
    };
  },
  methods: {
    navToDayView() {
      this.$router.push({ path: "DayView1" });
    },
      //获取所有任务及状态
      getTasksByUserid() {
     
        //获取所有任务
          this.$http
            .get(this.taskRequestUrl, {
              // params: { userId: sessionStorage.userId }
              params: {userid:sessionStorage.userid}
            })
            .then(response => {
              this.taskData = response.data;
              var nlist = [];
              for (var item of this.taskData) {
                nlist.push(item);
              }
              this.taskData = nlist;
            }),
            response => {
              console.log("failed");
            };

        //这周任务的切片
          
          let finishDate = now.format("yyyy-MM-dd");//当前日期
          //let monthAgo = now.format("yyyy-MM") + "-01";//当月1日
          let beginDate = date.setDate(date.getDate()-7);;//一周前
          console.log("beginData")
          console.log(beginData)
          //let tmpData = date_slice(beginDate,finishDate,taskData);
          let tmpData = date_slice(monthAgo,finishDate,taskData);
   
        //
      
  }
  },
  mounted() {
    let beginDate = date.setDate(date.getDate()-7);;//一周前
          console.log("beginData")
          console.log(beginData)
   
          
    this.userID =  sessionStorage.userid;
    //获取数据
    this.getTasksByUserid()
  
          this.chartData.rows = weekdata;
          // var totalNum = weekdata.map(item => {
          //   return item.allcount;
          // });
          // this.bestDay = returnData.data[findBest(totalNum)].weekday;
        
      
    // this.$http
    //   .get(timeurl, {
    //     params: { userid: this.userID }
    //   })
    //   .then(
    //     res => {
    //       // 响应成功回调
    //       //console.log(res.body);
    //       var returnData = res.body;
    //       this.pieData.rows = returnData.data;
    //       this.bestTime = returnData.result;
    //     },
    //     res => {
    //       // 响应错误回调
    //       console.log("time advice request fail");
    //     }
    //   );
  }
};
</script>

