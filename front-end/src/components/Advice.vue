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

// var weekdata = [  //测试数据
//   { weekday: "周一", taskcount: 1393, tomatocount: 1093 },
//   { weekday: "周二", taskcount: 3530, tomatocount: 3230 },
//   { weekday: "周三", taskcount: 2923, tomatocount: 2623 },
//   { weekday: "周四", taskcount: 1723, tomatocount: 1423 },
//   { weekday: "周五", taskcount: 3792, tomatocount: 3492 },
//   { weekday: "周六", taskcount: 4593, tomatocount: 4293 },
//   { weekday: "周日", taskcount: 4593, tomatocount: 4293 }
// ];

var weekurl = "http://localhost:8080/getWeekdaySuggestion";
var timeurl = "http://localhost:8080/getTimeSuggestion";

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
      userID: 2,
      bestDay: "周三",
      bestTime: "上午",
      pieData: {
        columns: ["time", "taskcount"],
        rows: []
      },
      chartData: {
        columns: ["weekday", "taskcount", "tomatocount"],
        rows: []
      }
    };
  },
  methods: {
    navToDayView() {
      this.$router.push({ path: "DayView1" });
    }
  },
  mounted() {
    this.userID = sessionStorage.userId;
    this.$http
      .get(weekurl, {
        params: { userid: this.userID }
      })
      .then(
        res => {
          // 响应成功回调
          //console.log(res.body);
          var returnData = res.body;
          this.chartData.rows = returnData.data;
          var totalNum = returnData.data.map(item => {
            return item.taskcount + item.tomatocount;
          });
          this.bestDay = returnData.data[findBest(totalNum)].weekday;
        },
        res => {
          // 响应错误回调
          console.log("week advice request fail");
        }
      );
    this.$http
      .get(timeurl, {
        params: { userid: this.userID }
      })
      .then(
        res => {
          // 响应成功回调
          //console.log(res.body);
          var returnData = res.body;
          this.pieData.rows = returnData.data;
          this.bestTime = returnData.result;
        },
        res => {
          // 响应错误回调
          console.log("time advice request fail");
        }
      );
  }
};
</script>

