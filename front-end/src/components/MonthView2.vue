<template>
  <div>
    <full-calendar :events="fcEvents" locale="en" @changeMonth="TimeChange" @eventClick="eventClick"></full-calendar>
      <template slot="fc-event-card" scope="p">
            <p><i class="fa">sadfsd</i> {{ p.event.title }} test</p>
        </template>
  </div>
</template>

<script>

//得到的每个月的最后一天
//投机做法
function getLastMonthDay(firstDay) {
  var day = new Date(firstDay);
  var month = day.format("yyyy-MM");
  return month + "-31";
}

var now = new Date(); //当前时刻
var nowTime = now.format("yyyy-MM-dd");//当前日期
var monthAgo = now.format("yyyy-MM") + "-01";//当月1日
var monthStart = new Date(monthAgo);//当月1日




export default {
  data() {
    return {
      fcEvents: [],
      storedTaskYear: [],
      date: [monthAgo, nowTime],//[当月1日，当前日期]
      userID:8,
      description:[],
      taskurl:"http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/getAllTasks"
    };
  },

  beforeCreate() {
    console.log("刷新");
    localStorage.clear();
  },
  //该组件在mounted之前就调用了TimeChange所以一些操作需要提前到created来做
  created() {
    console.log("团队任务视图")
    //++++++++++++++++dev++++++++++++++++++++++++
        // deleteDB("weekDB");
        // deleteDB("daySumDB");
        // deleteDB("dayTaskDB");
        // deleteDB("dayTomoDB");
    //--------------------------------------------
   
    openDB("teamTaskDB")
    //openDB("dayTaskDB");
    if (localStorage.storedTaskYear) {
      //如果任务数据库已经被创建
      console.log("任务数据库已经被创建");
      this.storedTaskYear = JSON.parse(localStorage.storedTaskYear);
      // js的懒加载机制见识到了
      // searchData(
      //   monthAgo,
      //   nowTime,
      //   () => {
      //     var selectedData = dbData;
      //     this.updateData(selectedData);
      //   },
      //   "dayTaskDB"
      // );
    } else {
      //如果数据库没有被创建
      console.log("任务数据库没有被创建");
      this.requestData(new Date().getFullYear(), 2);
    }
  },
  components: {
    "full-calendar": require("vue-fullcalendar")
  },
  methods: {

      eventClick(event, jsEvent, pos) {
       console.log('eventClick', event, jsEvent, pos)
      
        this.$alert(this.description[event.cellIndex-1], event.title, {
          confirmButtonText: '确定',
          callback: action => {
            // this.$message({
            //   type: 'info',
            //   message: `action: ${ action }`
            // });
          }
        })
    },

    //月份切换
    TimeChange(start, end, current) {
      console.log("Month:时间变化");
      this.date = [current, getLastMonthDay(current)];
      let year = current.split("-")[0];
      console.log("current month")
      console.log(current)
      if (this.storedTaskYear.indexOf(year) == -1) {
        console.log("Month:没有目标缓存");
        this.requestData(year, 2);
      } else {
        console.log("Month:有目标缓存");
        searchData(
          // current,
          // getLastMonthDay(current),
          () => {
          // var selectedData = dbData;
          let beginDate = this.date[0];//当月1日
          let finishDate = this.date[1];//当前日期
          let tmpData = date_slice(beginDate,finishDate,dbData);
          this.updateData(tmpData);
            
            console.log("month change")
            console.log(dbData)

          
          },
          "teamTaskDB"
        );
      }
    },

    updateData(selectedData) {
      this.fcEvents = [];
      console.log("in updateData function, selectedData is")
      console.log(selectedData);
      for (let i = 0; i < selectedData.length; i++) {
        let item = selectedData[i];
        // let date = item.settime;
        // let dayEvents = item.tempResult;
        // for (let j = 0; j < dayEvents.length; j++) {
          let ccolor;
          console.log("status")
          console.log(item.status)
          switch (item.status) {
            case 1:
              ccolor ="yellow"; //"waiting";
              break;
            case 4:
              ccolor ="red";// "abandoned";
              break;
            case 2:
              ccolor = "green";//"running";
              break;
            case 3:
              ccolor = "blue";//"completed";
              break;
            case 5:
              ccolor = "black";//"break";
              break;
            default:
              break;
          }
          this.description=[]
          this.description.push(item.description)
          this.fcEvents.push({
            start: item.starttime,
            title: item.name,
            end:item.endtime,
            color: ccolor
          });
       // }
      }
    },
    //direction参数用来确定请求情况
    //0为上溢出
    //1为下溢出
    //2为全部溢出
    requestData(year, direction) {
      console.log("requestData function");
      this.$http
        .get(this.taskurl, {
          params: {
            userid: this.userID,       
          }
        })
        .then(res => {
          // 响应成功回调
          console.log("requestData");
          console.log(res.body);
          var returnData = res.body;
          // for(var item in returnData)
          // {
          //   settime=item.settime;//起始时间
          //   deadline=item.deadline;//截止时间
          //   name=item.name;//名字
          //   description=item.description;//描述
          // }
          var startDate = this.date[0];//当月1日
          var endDate = this.date[1];//当前日期
          if (direction === 0) {
            //上溢出更新起始日期
            startDate = year + "-01-01";
          } else if (direction === 1) {
            //下溢出更新截止日期
            endDate = year + "-12-31";
          }
          console.log("startDate"+startDate)
          console.log("endDate"+endDate)
          //完全溢出什么都不更新
          console.log("date_slice begin:")
          var selectedData = date_slice(startDate,endDate,returnData);
          console.log("data_slice result:")
          console.log(selectedData)
          console.log("data_slice result!")
          this.updateData(selectedData);
          for (var i of returnData) {
            console.log("now begin to save data")
            saveData(i, "teamTaskDB");
          }
          console.log("here store the year")
          this.storedTaskYear.push(year);
          localStorage.storedTaskYear = JSON.stringify(this.storedTaskYear);
        });
        
    }
  }
};
</script>

<style scoped>
.comp-full-calendar {
  padding: 15px;
  background: #fff;
  max-width: none;
  margin: 0 auto;
  font-size: medium;
}
</style>



