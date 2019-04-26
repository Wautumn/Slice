<template>
  <div>
    <full-calendar :events="fcEvents" locale="en" @changeMonth="TimeChange"></full-calendar>
  </div>
</template>

<script>
//测试数据
var demoEvents = [
  {
    title: "Sunny Out of Office",
    start: "2018-12-06"
  },
  {
    title: "Sunny Out of Office",
    start: "2018-12-08"
  }
];

//得到的每个月的最后一天
//投机做法
function getLastMonthDay(firstDay) {
  var day = new Date(firstDay);
  var month = day.format("yyyy-MM");
  return month + "-31";
}

var now = new Date(); //当前时刻
var nowTime = now.format("yyyy-MM-dd");
var monthAgo = now.format("yyyy-MM") + "-01";
var monthStart = new Date(monthAgo);

const historyurl = "http://101.132.194.45:8080/slice-0.0.1-SNAPSHOT/getTasksByUserid";

export default {
  data() {
    return {
      fcEvents: [],
      storedTaskYear: [],
      date: [monthAgo, nowTime],
      userID: 2
    };
  },
  beforeCreate() {
    // console.log("刷新");
    localStorage.clear();
  },
  //该组件在mounted之前就调用了TimeChange所以一些操作需要提前到created来做
  created() {
    sessionStorage.userId="1";
    this.userID = sessionStorage.userId;
    openDB("dayTaskDB");
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
    TimeChange(start, end, current) {
      console.log("Month:时间变化");
      this.date = [current, getLastMonthDay(current)];
      let year = current.split("-")[0];
      if (this.storedTaskYear.indexOf(year) == -1) {
        console.log("Month:没有目标缓存");
        //debugger;
        this.requestData(year, 2);
      } else {
        console.log("Month:有目标缓存");
        searchData(
          current,
          getLastMonthDay(current),
          () => {
            var selectedData = dbData;
            this.updateData(selectedData);
          },
          "dayTaskDB"
        );
      }
    },
    updateData(selectedData) {
      this.fcEvents = [];
      console.log("selectedData is")
      console.log(selectedData);
      for (let i = 0; i < selectedData.length; i++) {
        let item = selectedData[i];
        let date = item.date;
        let dayEvents = item.tempResult;
        for (let j = 0; j < dayEvents.length; j++) {
          // let css;
          // switch (dayEvents[j].status) {
          //   case 0:
          //     css = "waiting";
          //     break;
          //   case -1:
          //     css = "abandoned";
          //     break;
          //   case 1:
          //     css = "running";
          //     break;
          //   case 2:
          //     css = "completed";
          //     break;
          //   default:
          //     break;
          // }
          //debugger;
          this.fcEvents.push({
            start: dayEvents[j].settime,
            title: dayEvents[j].name,
            // cssClass: css
          });
        }
      }
    },
    //direction参数用来确定请求情况
    //0为上溢出
    //1为下溢出
    //2为全部溢出
    requestData(year, direction) {
      this.$http
        .get(historyurl, {
          params: {
            userid: this.userID,
            
          }
        })
        .then(res => {
          // 响应成功回调
          console.log("requestData");
          console.log(res.body);
          var returnData = res.body;
          for(var item in returnData)
          {
            settime=item.settime;
            deadline=item.deadline;
            name=item.name;
            description=item.description;
          }
          var startDate = this.date[0];
          var endDate = this.date[1];
          // if (direction === 0) {
          //   //上溢出更新起始日期
          //   settime = year + "-01-01";
          // } else if (direction === 1) {
          //   //下溢出更新截止日期
          //   deadline = year + "-12-31";
          // }
          //完全溢出什么都不更新
          var selectedData = date_slice(settime, deadline, returnData);
          //debugger;
          this.updateData(selectedData);
          // for (var i of returnData) {
          //   //debugger;
          //   saveData(i, "dayTaskDB");
          // }
          this.storedTaskYear.push(year);
          localStorage.storedTaskYear = JSON.stringify(this.storedTaskYear);
        })
        .catch(() => {
          console.log("获取信息失败");
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



