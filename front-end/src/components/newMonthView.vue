<template>
  <v-app id="dayspan" v-cloak>

    <ds-calendar-app ref="app"
      :calendar="calendar"
      :read-only="readOnly"
      @change="saveState">

      <template slot="title">
        Slice
      </template>
      <template slot="title">
          <v-btn icon @click="jumpToHome()">
        <v-icon>home</v-icon>
      </v-btn>
      </template>
       <template slot="title">

      <v-btn-toggle v-model="icon" @change="changeType">
              <v-btn flat value="personal" color="info">
                <span>个人</span>
                <v-icon>person</v-icon>
              </v-btn>
              <v-btn flat value="peoples" color="info">
                <span>团队</span>
                <v-icon>people</v-icon>
              </v-btn>
            </v-btn-toggle>
        </template>



      <template slot="eventPopover" slot-scope="slotData">
         <ds-calendar-event-popover
          v-bind="slotData"
          :read-only="readOnly"
          @finish="saveState"
        ></ds-calendar-event-popover>
        
      </template>

      <template slot="eventCreatePopover" slot-scope="{placeholder, calendar, close}">
        <ds-calendar-event-create-popover
          :calendar-event="placeholder"
          :calendar="calendar"
          :close="$refs.app.$refs.calendar.clearPlaceholder"
          @create-edit="$refs.app.editPlaceholder"
          @create-popover-closed="saveState"
        ></ds-calendar-event-create-popover>
      </template>

      <template slot="eventTimeTitle" slot-scope="{calendarEvent, details}">
        <div>
          <v-icon class="ds-ev-icon"
            v-if="details.icon"
            size="14"
            :style="{color: details.forecolor}">
            {{ details.icon }}
          </v-icon>
          <strong class="ds-ev-title">{{ details.title }}</strong>
        </div>
        <div class="ds-ev-description">{{ getCalendarTime( calendarEvent ) }}

        </div>
        
      </template>

      <template slot="drawerBottom">
        <div class="pa-3">
          <v-checkbox
            label="Read Only?"
            v-model="readOnly"
          ></v-checkbox>
        </div>
      </template>

    </ds-calendar-app>

  </v-app>
</template>

<script>
import { Calendar, Weekday, Month } from 'dayspan';
import Vue from 'vue';

//-----------------
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
//-----------------
export default {

  name: 'app',

  data: () => ({
    storeKey: 'dayspanState',
    calendar: Calendar.months(),
    readOnly: false,
    row: null,
    // text: 'center',
    icon: "personal",
    // toggle_none: null,
    // toggle_exclusive: 2,
    // toggle_multiple: [0, 1, 2],
    //--------------
    storedTaskYear: [],
      date: [monthAgo, nowTime],//[当月1日，当前日期]
      userID: localStorage.userid,
      description:[],
      taskurl:"http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/getTasksByUserid",
    //--------------
    defaultEvents: []
  }),
//--------------------------------
 //该组件在mounted之前就调用了TimeChange所以一些操作需要提前到created来做
  created() {
    //localStorage.clear();
  },
//--------------------------------
  mounted()
  {
    window.app = this.$refs.app;
    console.log("mounted")
    //this.loadState();
    this.changeType();
  },

  methods:
  { changeType()
  {
    console.log("icon"+this.icon)
   if(this.icon=="personal"){
     this.taskurl="http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/getTasksByUserid"
     this.statechange(0)
   }
   else{
     this.taskurl="http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/getAllTasks"
      this.statechange(1)  
   }
   console.log("url"+this.taskurl)
  },
    statechange(ttype)
    { console.log("!!!!!!!!!!!!!")
      console.log(localStorage.userid)
      console.log("！！！！！！！！")
      let state={}
        console.log("requestData function");
      this.$http
        .get(this.taskurl, {
          params: {
            userid: localStorage.userid,       
          }
        })
        .then(res => {
          this.defaultEvents=[];
            console.log("default data1:")
            console.log(this.defaultEvents)
            console.log("res data1")
          console.log(res.data)
          let selectedData=res.data
        for (let i = 0; i < selectedData.length; i++) {
        let item = selectedData[i];
          var ccolor='#4CAF50';
          switch (item.status) {
            case 1:
              ccolor ='#FFF68F'; //"waiting";
              break;
            case 4:
              ccolor ='#ffd4d4';// "abandoned";
              break;
            case 2:
              ccolor = '#4CAF50';//"running";
              break;
            case 3:
              ccolor = '#2196F3';//"completed";
              break;
            case 5:
              ccolor = '#000000';//"break";
              break;
            default:
              break;
          }
          let tmpdefaultdata=new Object()
          if(ttype==0)
          {
          tmpdefaultdata={
           data: {
          title: item.name,
          color: ccolor
          //color: '#4CAF50'
        },
        schedule: {
          // weekspanOfMonth: [0],
          //dayOfWeek: [Weekday.FRIDAY],
          // month: [Month.JUNE],
          // dayOfMonth: [20],
          // duration: 3,
          // durationUnit: 'days'
          start:item.starttime,
          end:item.finishtime,
        }}
          }
          else{
            tmpdefaultdata={
           data: {
          title: item.name,
          color: ccolor
          //color: '#4CAF50'
        },
        schedule: {
          // weekspanOfMonth: [0],
          //dayOfWeek: [Weekday.FRIDAY],
          // month: [Month.JUNE],
          // dayOfMonth: [20],
          // duration: 3,
          // durationUnit: 'days'
          start:item.starttime,
          end:item.endtime,
        }}
          }
        JSON.stringify(tmpdefaultdata)
        this.defaultEvents.push(tmpdefaultdata)
      }
      console.log("executed")
        state.events = this.defaultEvents;
        console.log(this.defaultEvents)
        
        state.events.forEach(ev =>
            {
                console.log("here for each")
                let defaults = this.$dayspan.getDefaultEventDetails();
                console.log(defaults)
                ev.data = Vue.util.extend( defaults, ev.data );
            });

            this.$refs.app.setState( state );



        });
    },
    jumpToHome()
    {
      this.$router.push("/TaskMode");
    },
    getCalendarTime(calendarEvent)
    {
      let sa = calendarEvent.start.format('a');
      let ea = calendarEvent.end.format('a');
      let sh = calendarEvent.start.format('h');
      let eh = calendarEvent.end.format('h');

      if (calendarEvent.start.minute !== 0)
      {
        sh += calendarEvent.start.format(':mm');
      }

      if (calendarEvent.end.minute !== 0)
      {
        eh += calendarEvent.end.format(':mm');
      }

      return (sa === ea) ? (sh + ' - ' + eh + ea) : (sh + sa + ' - ' + eh + ea);
    },

    saveState()
    {
      let state = this.calendar.toInput(true);
      let json = JSON.stringify(state);

      localStorage.setItem(this.storeKey, json);
    },

    loadState()
    { console.log("loadstate")
      let state = {};

      try
      {
        console.log("try")
        let savedState = JSON.parse(localStorage.getItem(this.storeKey));

        if (savedState)
        {
          state = savedState;
          state.preferToday = false;
        }
      }
      catch (e)
      {
        console.log("what")
        // eslint-disable-next-line
        console.log( e );
      }

      if (!state.events || !state.events.length)
      {


        //---------------------
     
     
      console.log("requestData function");
      this.$http
        .get(this.taskurl, {
          params: {
            userid:localStorage.userid,       
          }
        })
        .then(res => {
          this.defaultEvents=[];
            console.log("default data1:")
            console.log(this.defaultEvents)
            console.log("res data1")
          console.log(res.data)
          let selectedData=res.data
        for (let i = 0; i < selectedData.length; i++) {
        let item = selectedData[i];
          var ccolor='#4CAF50';
          switch (item.status) {
            case 1:
              ccolor ='#FFF68F'; //"waiting";
              break;
            case 4:
              ccolor ='#ffd4d4';// "abandoned";
              break;
            case 2:
              ccolor = '#4CAF50';//"running";
              break;
            case 3:
              ccolor = '#2196F3';//"completed";
              break;
            case 5:
              ccolor = '#000000';//"break";
              break;
            default:
              break;
          }
          
          let tmpdefaultdata={
           data: {
          title: item.name,
          color: ccolor
          //color: '#4CAF50'
        },
        schedule: {
          // weekspanOfMonth: [0],
          //dayOfWeek: [Weekday.FRIDAY],
          // month: [Month.JUNE],
          // dayOfMonth: [20],
          // duration: 3,
          // durationUnit: 'days'
          start:item.starttime,
          end:item.finishtime,
        }}
        JSON.stringify(tmpdefaultdata)
        this.defaultEvents.push(tmpdefaultdata)
      }
      console.log("executed")
        state.events = this.defaultEvents;
        console.log(this.defaultEvents)
        
        state.events.forEach(ev =>
            {
                console.log("here for each")
                let defaults = this.$dayspan.getDefaultEventDetails();
                console.log(defaults)
                ev.data = Vue.util.extend( defaults, ev.data );
            });

            this.$refs.app.setState( state );



        });
        
    
        //---------------------
        // console.log("default data2:")
        // console.log(this.defaultEvents)
        //-----------------
        //state.events = this.defaultEvents;
      }

    //   state.events.forEach(ev =>
    //   {
    //     let defaults = this.$dayspan.getDefaultEventDetails();

    //     ev.data = Vue.util.extend( defaults, ev.data );
    //   });

    //   this.$refs.app.setState( state );
    },
    //-----------------------------
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
          "dayTaskDB"
        );
      }
    },

    updateData(selectedData) {
      //this.fcEvents = [];
      this.defaultEvents=[];
      console.log("in updateData function, selectedData is")
      console.log(selectedData);
      for (let i = 0; i < selectedData.length; i++) {
        let item = selectedData[i];
          var ccolor='cssyellow';
          switch (item.status) {
            case 1:
              ccolor ='cssyellow'; //"waiting";
              break;
            case 4:
              ccolor ='cssred';// "abandoned";
              break;
            case 2:
              ccolor = 'cssgreen';//"running";
              break;
            case 3:
              ccolor = 'cssblue';//"completed";
              break;
            case 5:
              ccolor = 'cssblack';//"break";
              break;
            default:
              break;
          }
        // this.defaultEvents.push({
        //    data: {
        //   title: item.name,
        //   color: '#4CAF50'
        // },
        // schedule: {
        //   weekspanOfMonth: [0],
        //   dayOfWeek: [Weekday.FRIDAY],
        //   duration: 3,
        //   durationUnit: 'days'
        // }
        // })
      

        this.defaultEvents.push(  {
        data: {
          title: 'Inauguration Day',
          color: '#2196F3',
          calendar: 'US Holidays'
        },
        schedule: {
          month: [Month.JANUARY],
          dayOfMonth: [20]
        }
      })
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
            userid: localStorage.userid,       
          }
        })
        .then(res => {
          // 响应成功回调
          console.log("requestData");
          console.log(res.body);
          var returnData = res.body;
          var startDate = this.date[0];//当月1日
          var endDate = this.date[1];//当前日期
          if (direction === 0) {
            //上溢出更新起始日期
            startDate = year + "-01-01";
          } else if (direction === 1) {
            //下溢出更新截止日期
            endDate = year + "-12-31";
          }
          var selectedData = date_slice(startDate,endDate,returnData);
          this.updateData(selectedData);
          for (var i of returnData) {
            saveData(i, "dayTaskDB");
          }
          console.log("here store the year")
          this.storedTaskYear.push(year);
          localStorage.storedTaskYear = JSON.stringify(this.storedTaskYear);
        });
        
    }
  
    //-----------------------------
  }
}
</script>

<style>

body, html, #app, #dayspan {
  font-family: Roboto, sans-serif !important;
  width: 100%;
  height: 100%;
}

.v-btn--flat,
.v-text-field--solo .v-input__slot {
  background-color: #f5f5f5 !important;
  margin-bottom: 8px !important;
}

</style>
