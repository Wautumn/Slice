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
      userID:  sessionStorage.userid,
      bestDay: "周三",
      bestTime: "上午",
      pieData: {
        columns: ["time", "taskcount"],
        rows: []
      },
      chartData: {
        columns: ["weekday", "总数", "进行中","已完成"],
        rows: []
      },
      taskRequestUrl:"http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/getTasksByUserid",
      taskData:[],
      currentData:[]
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
              params: {userid: sessionStorage.userid}
            })
            .then(response => {
              this.taskData = response.data;
             console.log(this.taskData)
               //这周任务的切片
          let now = new Date(); //当前时刻
          let finishDate = now.format("yyyy-MM-dd");//当前日期
          let date = new Date();
          let beginDate = date.setDate(date.getDate()-7);//一周前
          let begindate = new Date(beginDate)
          begindate=begindate.format("yyyy-MM-dd")
            console.log(begindate)
          let tmpData = date_slice(begindate,finishDate,this.taskData);
          console.log("tmpdata")
          console.log(tmpData)

          for(let k=0;k<7;k++)
          {
              var j={"weekday":"2019/5/22","总数":0,"进行中":0,"已完成":0}
              let today=new Date()
              let nowDate = today.setDate(today.getDate()-7+k);
              let nowdate=new Date(nowDate);
              j.weekday=nowdate.format("yyyy-MM-dd");
              this.currentData.push(j)

          }
            console.log(this.currentData)
        //统计数量
        for(let i of tmpData){
        
            //对每一个数据
            for(let j of this.currentData)
            {
                if(j.weekday==i.settime)
                {  console.log("status")
                    if(i.status==0)
                    {   
                        j.doingcount=j.doingcount+1
                        j.allcount=j.allcount+1
                    }
                    else if(i.status==0)
                    {
                        j.finishcount=j.finishcount+1
                        j.allcount=j.allcount+1
                    }
                }
            }
          
        }

        console.log(this.currentData)
            }),
            response => {
              console.log("failed");
            };

      

        
   
      
  }
  },
  mounted() {

    
  
   
          
    this.userID = sessionStorage.userid;
    //获取数据
    this.getTasksByUserid()



          this.chartData.rows = this.currentData;
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

