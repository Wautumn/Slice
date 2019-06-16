<template>
  <div class="container">
    <el-card class="box-card-his">
      <div slot="header" class="clearfix">
        <span>最近一周工作情况</span>
        <!-- <el-button @click="navToDayView" style="float: right; padding: 3px 0" type="text">查看详细数据</el-button> -->
      </div>
      <ve-line :data="chartData" :settings="chartSettings"></ve-line>
      <!-- <p class="align-center">您的最佳工作日是{{bestDay}}</p> -->
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
// import "echarts/lib/chart/line";
import VeLine from "v-charts/lib/line";
// import VePie from "v-charts/lib/pie.js";

// var weekdata = [  //测试数据
//   { weekday: "周一", allcount: 1393, doingcount: 13,finishcount:1000},
//   { weekday: "周二", allcount: 1393, doingcount: 193,finishcount:1000},
//   { weekday: "周三", allcount: 1393, doingcount: 1093,finishcount:1000},
//   { weekday: "周四", allcount: 1393, doingcount: 93,finishcount:1300},
//   { weekday: "周五", allcount: 1393, doingcount: 1093,finishcount:10050},
//   { weekday: "周六", allcount: 1393, doingcount: 1093,finishcount:10 },
//   { weekday: "周日", allcount: 1393, doingcount: 1093,finishcount:1000}
// ];



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
  components: { VeLine },
  data() {
    this.chartSettings = {
      stack: { 用户: ["taskcount", "tomatocount"] },
      area: true
    };

    return {
      userID: localStorage.userid,
      bestDay: "周三",
      bestTime: "上午",
      pieData: {
        columns: ["time", "taskcount"],
        rows: []
      },
      chartData: {
        columns: ["weekday", "中断", "过期","已完成"],
        rows: []
      },
      taskRequestUrl:"http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/getAnaly",
      taskData:[],
      currentData:[]
    };
  },
  methods: {
    navToDayView() {
      this.$router.push({ path: "DayView1" });
    },

  },
  mounted() {

    
  
   
          
    this.userID = localStorage.userid;
    //获取数据
  
          console.log("输出看一眼"+localStorage.userid)
          this.$http.get(
            this.taskRequestUrl,{
              params:{userid:localStorage.userid}
            }
          ).then(
            res=>{
              console.log("输出看两眼"+res)
               this.chartData.rows = res.data;
            }
          )
         
          
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

