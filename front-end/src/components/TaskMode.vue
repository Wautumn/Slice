<template>
  <div>
    <el-container>

      <el-aside width="400px">

        <el-card class="box-card">
          <div slot="header" class="clearfix">
    <span>

    <el-radio-group v-model="radio" @change="handleCommand">
    <el-radio :label="3">个人任务</el-radio>
    <el-radio :label="6">团队任务</el-radio>
  </el-radio-group>
    </span>
            <el-popover
              placement="right"
              width="400"
              trigger="click">
              <todolist></todolist>
              <el-button slot="reference" style="float: right; padding: 3px 0" type="text">备忘录</el-button>
            </el-popover>

          </div>
          <div>
            <template>
              <template v-if="tasktype==0">
                <tasklist @transferTask="getTasksByUserid" :tableData="taskData" :currentmode="tasktype"></tasklist>
              </template>
              <template v-else-if="tasktype==1">
                <tasklist @transferTask="getTasksByUserid" :tableData="taskData" :currentmode="tasktype"></tasklist>
                <!-- <teamTasklist></teamTasklist> -->
              </template>
            </template>
            <!-- <tasklist @transferTask="getTasksByUserid" :tableData="taskData" :currentmode="tasktype"></tasklist> -->
          </div>
        </el-card>

      </el-aside>

      <el-main>
        <el-row>
          <el-col :span="24">

            <div style="text-align: center">
              <div
                style="font-size: 25px; line-height: 100px; color: slategrey;"
              >当前时间：{{date.format("yyyy-MM-dd hh:mm:ss")}}
              </div>
              <clock :time="clocktime"></clock>
              <br>
              <strong style="font-size: 20px; line-height: 80px; color: slategrey ;text-align: left"
                      v-if="ifstart==0&&currentStatus<3">距离任务开始:
                {{endstart1}}天 {{endstart2}}时 {{endstart3}}分 {{endstart4}}秒</strong>
              <strong style="font-size: 20px;line-height: 80px; color: slategrey ;text-align: left"
                      v-else-if="ifstart==1">距离任务结束:
                {{endfinish1}}天 {{endfinish2}}时 {{endfinish3}}分 {{endfinish4}}秒</strong>
              <strong style="font-size: 20px; line-height: 80px; color: slategrey ;text-align: left"
                      v-else-if="ifstart==2">任务已结束</strong>
              <strong style="font-size: 20px; line-height: 80px; color: slategrey ;text-align: left"
                      v-else-if="currentStatus==5">任务已过期</strong>
              <strong style="font-size: 20px; line-height: 80px; color: slategrey ;text-align: left"
                      v-else-if="currentStatus==4">任务已废弃</strong>
              <br>

              <el-button
                :type="countButtonType"
                :disabled="isTaskNotStart==0||isTaskFinish==0||cannotstart==1"
                @click="finishTask"
              >完成任务
              </el-button>
              <el-button
                type="warning"
                :disabled="isTaskFinish==0||tasktype==1"
                @click="breakTask"
              >废弃任务
              </el-button>
              <!--<el-button-->
              <!--type="info"-->
              <!--v-show="tasktype==1"-->
              <!--@click="putoff"-->
              <!--&gt;推迟任务-->
              <!--</el-button>-->
              <br>
            </div>
            <div v-show="selected" style="margin-left: 20px; margin-right: 20px;">
              <div v-if="cannotstart==1&&tasktype==1" style="margin-top: 50px; margin-bottom:20px;color: #dd3e3a">
                <!--<h1>当前任务前置任务未完成！此任务无法完成！</h1>-->
                <!--<h1>请到团队任务视图修改当前团队任务计划</h1>-->
                  <el-alert
    title="当前任务前置任务未完成,此任务无法完成！"
    type="error"
     :closable="false"
     show-icon
     center>
  </el-alert>
  <br>
    <el-alert
    title="请到团队任务视图修改当前团队任务计划"
    type="warning"
     :closable="false"
     show-icon
     center>
  </el-alert>
                <!-- <el-card class="box-card">
                  <div slot="header" class="clearfix">
                  </div>
                  <h1>当前任务前置任务未完成！此任务无法完成！</h1>
                  <h1>请到团队任务视图修改当前团队任务计划</h1>
                </el-card> -->

              </div>
              <h1 v-show="selected" style="margin-top: 10px;">任务名</h1>
              <div style="margin-top: 20px; margin-bottom:20px;">
                <h1>{{currentTaskName}}</h1>
              </div>
              <el-steps :active="currentFinishedPomo" space="100px" finish-status="success">
                <el-step v-for="n in currentTotalPomo"></el-step>
              </el-steps>
              <br>
              <h1 v-show="selected" style="margin-top: 10px;">任务时间</h1>
              <div style="margin-top: 10px; margin-bottom: 10px; margin-left: 20px;">
                <h2>{{currentStarttime}}至{{currentDeadline}}</h2>
              </div>
              <div v-if="tasktype==1">
                <h1 v-show="selected" style="margin-top: 10px;">前置任务</h1>
                <div style="margin-top: 10px; margin-bottom: 10px; margin-left: 20px;">

                  <h2 v-if="currentpretaskname!=null">{{currentpretaskname}}</h2>
                  <h2 v-else>无</h2>
                </div>
              </div>

              <div v-if="tasktype==1">
                <h1 v-show="selected" style="margin-top: 10px;">前置任务状态</h1>
                <div style="margin-top: 10px; margin-bottom: 10px; margin-left: 20px;">

                  <h2 v-if="currentpretasksta!=null">
                    <div v-if="currentpretasksta==5">已过期</div>
                    <div v-if="currentpretasksta==1">未开始</div>
                    <div v-if="currentpretasksta==2">进行中</div>
                    <div v-if="currentpretasksta==4">已完成</div>
                  </h2>
                  <h2 v-else>无</h2>
                </div>
              </div>
              <h1 v-show="selected">任务详情</h1>
              <div style="margin-top: 20px">
                <el-form v-show="selected">
                  <el-form-item>
                    <el-input type="textarea" v-model="currentTaskDetail" :disabled="currentCondition"></el-input>
                  </el-form-item>
                  <el-form-item>
                    <el-button type="primary" @click="onSubmit" style="float: right;">保存</el-button>
                    <!-- <el-button type="danger" style="float: right;" @click="deleteTask">删除记录</el-button> -->
                    <!-- <el-button
                      type="warning"
                      style="float: right;"
                      :disabled="currentStatus == -1"
                      @click="breakTask"
                    >废弃任务
                    </el-button> -->
                  </el-form-item>
                </el-form>
              </div>
            </div>
          </el-col>

        </el-row>

        <el-footer height="60px" style="margin-top: 30px;">
          <h1>每日小结</h1>
          <Rate show-text allow-half v-model="selfRating">
            <span style="color: #f5a632">{{selfRating}}</span>
          </Rate>
          <el-input
            type="textarea"
            v-model="dailySummary"
            style="margin-top: 10px; margin-bottom:10px;"
          >
          </el-input>
          <el-button type="primary" @click="saveDailySummary" style="float: right;">{{currentSummaryText}}</el-button>
        </el-footer>
      </el-main>
    </el-container>

  </div>

</template>

<script>
  import {clearInterval} from "timers";
  import {setInterval} from "timers";
  import TaskList from "@/components/TaskList";
  import TodoList from "@/components/TodoList";
  import teamTaskList from "@/components/teamTaskList";
  import Clock from 'vue-clock2';

  export default {
    components: {
      tasklist: TaskList,
      todolist: TodoList,
      teamTasklist: teamTaskList,
      Clock
    },
    data() {
      return {
        tasktype: 0,
        isTaskFinish: 1,
        isTaskNotStart:1,
        currentTaskUrl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/getTodayTasksByUserid",
        date: new Date(),
        // clocktime:null,
        ifstart: 0,
        endstart: null,//未开始任务，距离任务开始时间
        endstart1: null,//day
        endstart2: null,//minute
        endstart3: null,//second
        endstart4: null,//second

        endfinish: null,//已开始任务，距离任务结束时间
        endfinish1: 0,//hour
        endfinish2: 0,//minute
        endfinish3: 0,//second
        endfinish4: 0,//hour

        currentSummaryText: "提交",
        tomoStartTime: "",
        dailySummary: "",
        selfRating: 0,
        countButtonType: "success",
        countOn: false,
        summaryCountOn: false,
        selected: false,
        selectCancel: false,
        count: "0",
        minutes: "0",
        seconds: "0",
        timer: null,
        getcurrentTime: null,
        previousStartTime: new Date(),
        currentTask: null,
        currentTaskName: "",
        currentTaskDetail: "",
        currentFinishedPomo: null,
        currentTotalPomo: null,
        currentCondition: false,
        currentSummaryCondition: false,
        currentStatus: -2,
        currentDeadline: null,
        currentStarttime: null,
        taskData: [],
        currentSummaryId: null,
        radio: 3,

        currentpretaskname: null,
        currentpretasksta: null,
        cannotstart: 0,
        // taskRequestUrl: "http://localhost:8080/task/getTask",
        // taskStartUrl: "http://localhost:8080/task/startTask",
        // tomatoStartUrl: "http://localhost:8080/startTomato",
        // tomatoBreakUrl: "http://localhost:8080/breakTomato",
        // tomatoEndUrl: "http://localhost:8080/endTomato",
        // deleteTaskUrl: "http://localhost:8080/task/deleteTask",
        // modifyTaskUrl: "http://localhost:8080/task/modifyTask",
        // breakTaskUrl: "http://localhost:8080/task/breakTask",
        // endTaskUrl: "http://localhost:8080/task/endTask",
        // dailySummaryUrl: "http://localhost:8080/summary/save",

        taskRequestUrl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/getTodayTasksByUserid",
        teamRequestUrl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/getTodaysTasks",
        deleteTaskUrl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/deleteTask",
        dailySummaryUrl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/writeSummary",
        summaryRequestUrl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/getTodaysSummary",
        changeDailySummaryUrl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/changeSummary",

        taskStartUrl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/task/startTask",
        tomatoStartUrl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/startTomato",
        tomatoBreakUrl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/breakTomato",
        tomatoEndUrl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/endTomato",
        modifyTaskUrl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/changeTaskDescription",
        // breakTaskUrl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/task/breakTask",
        // endTaskUrl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/task/endTask",


        startTaskurl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/startTask",
        breakTaskUrl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/breakTask",
        endTaskUrl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/finishTask",
        delayTaskUrl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/delayTask",


        endProjectTaskUrl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/finishProjectTask",
        startProjectTaskurl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/startProjectTask",
        delayProjectTaskUrl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/delayTask1",


        findpretaskurl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/findPreTask"


      };
    },
    //加载
    mounted() {
      console.log("localStorage"+localStorage.userid)
      var _this = this; //声明一个变量指向vue实例this,保证作用域一致
      this.timer = setInterval(function () {
        _this.date = new Date();//修改数据date

      }, 1000);


      // sessionStorage.userId = "10";
      this.getcurrentTime = this.currentTime();
      this.getcurrentTime = this.getcurrentTime.substring(0, 19);
      this.$http
        .get(this.currentTaskUrl, {params: {userid: localStorage.userid}})
        .then(response => {
          this.taskData = response.data;
          var nlist = [];
          for (var item of this.taskData) {
            // if (item.deadline >= this.getcurrentTime)
            nlist.push(item);
          }
          this.taskData = nlist;
        }),
        response => {
        };

      localStorage.listLock = "false";
      console.log(this.getcurrentTime);
      this.getSummary();

    },
    methods: {
      handleCommand(command) {

        if (command == 3) {
          this.currentTaskUrl = this.taskRequestUrl
          this.tasktype = 0
        } else if (command == 6) {
          this.currentTaskUrl = this.teamRequestUrl
          this.tasktype = 1
        }
        this.$http
          .get(this.currentTaskUrl, {
            params: {userid: localStorage.userid}
          })
          .then(response => {
            this.taskData = response.data;
            var nlist = [];
            for (var item of this.taskData) {
              //if (item.deadline >= this.getcurrentTime)
              nlist.push(item);
            }
            this.taskData = nlist;

          }),
          response => {
            console.log(failed);
          };
      },
      //*********************
      startCount() {
        if (!this.selected) {
          this.$alert("请选择一个任务以开始本番茄钟", "提示", {
            confirmButtonText: "确定",
            callback: action => {
            }
          });
          return;
        }
        if (localStorage.tomoLength) {
          var TIME_COUNT = parseInt(localStorage.tomoLength) * 60;
        } else {
          var TIME_COUNT = 5;
        }
        var TIME_COUNT = 5;
        if (!this.timer) {
          this.count = TIME_COUNT;
          this.countOn = true;
           localStorage.listLock = "true";
          this.countButtonType = "danger";
          this.seconds = this.count % 60;
          this.minutes = parseInt(this.count / 60);
          if (this.taskData[this.currentTask].status == 0) {
            this.$http.get(this.taskStartUrl, {
              params: {
                userid: localStorage.userid,
                taskName: this.taskData[this.currentTask].name
              }
            });
            this.taskData[this.currentTask].status = 1;
          }
          this.tomoStartTime = new Date().format("yyyy-MM-dd hh:mm:ss");
          this.$http.get(this.tomatoStartUrl, {
            params: {
              userid: localStorage.userid,
              taskName: this.currentTaskName,
              startTime: this.tomoStartTime
            }
          });
          this.timer = setInterval(() => {
            if (this.count > 0 && this.count <= TIME_COUNT) {
              this.count--;
              this.seconds = this.count % 60;
              this.minutes = parseInt(this.count / 60);
            } else {
              this.countOn = false;
              this.countButtonType = "success";
              clearInterval(this.timer);
              this.timer = null;
              localStorage.listLock = "false";
              if (this.count == 0) {
                this.currentFinishedPomo++;
                this.taskData[this.currentTask].tomatoCompleted++;
                this.$http.get(this.tomatoEndUrl, {
                  params: {
                    userid: localStorage.userid,
                    needAssociation: true,
                    taskName: this.currentTaskName,
                    startTime: this.tomoStartTime,
                    endTime: new Date().format("yyyy-MM-dd hh:mm:ss")
                  }
                });
                this.$http.get(this.modifyTaskUrl, {
                  params: {
                    userid: localStorage.userid,
                    taskName: this.currentTaskName,
                    property: "tomatoCompleted",
                    value: this.currentFinishedPomo
                  }
                });
                if (
                  this.taskData[this.currentTask].tomatoCompleted ==
                  this.taskData[this.currentTask].expectedTomato
                )
                  this.currentCondition = true;
                else this.currentCondition = false;
              }
            }
          }, 1000);
        }
      },
      //**********************

      //*************** */
      stopCount() {
        this.count = "-1";
        this.minutes = "0";
        this.seconds = "0";
        //this.currentFinishedPomo--;
        this.$http.get(this.tomatoBreakUrl, {
          params: {
            userid: localStorage.userid,
            breakTime: new Date().format("yyyy-MM-dd hh:mm:ss"),
            startTime: this.tomoStartTime
          }
        });
      },
      //**************************** */

      //！！！！！！！！！！！！！！！！！！
      getTasksByUserid(msg) {
        console.log("----------------------msg")
        console.log("local")
        console.log(msg)
        if (msg == "new") {
          this.$http
            .get(this.currentTaskUrl, {
              // params: { userId: sessionStorage.userId }
              params: {userid: localStorage.userid}
            })
            .then(response => {
              this.taskData = response.data;
              var nlist = [];
              for (var item of this.taskData) {
                // if (item.deadline >= this.getcurrentTime)
                nlist.push(item);
              }
              this.taskData = nlist;
            }),
            response => {
              console.log("failed");
            };
          console.log(this.taskData);
          return;
        }
        if (this.selectCancel) this.selectCancel = false;
        else this.selected = true;
        for (
          this.currentTask = 0;
          this.taskData[this.currentTask] != msg;
          this.currentTask++
        )
          if (this.taskData[this.currentTask] == msg) {
            break;
          }
        //this.currentTask = msg.index;
        this.currentTaskName = msg.name;
        // this.currentFinishedPomo = msg.tomatoCompleted;
        // this.currentTotalPomo = parseInt(msg.expectedTomato);
        this.currentTaskDetail = msg.description;
        this.currentStatus = msg.status

        console.log("what is message")
        console.log(msg)
        console.log(this.tasktype)

        if (this.tasktype == 1) {
          this.currentDeadline = msg.endtime;
          this.$http.get(this.findpretaskurl, {
            params: {
              taskid: msg.id
            }
          }).then(response => {
            console.log(response.data.length)
            if (response.data.length > 0) {
              this.currentpretasksta = response.data[0].status
              this.currentpretaskname = response.data[0].name
            }
            this.currentStarttime = msg.starttime;
            console.log(this.currentStarttime)

            this.currentStatus = msg.status;
            this.currentTaskid = msg.id;
            this.currentCondition = false;
            if (msg.status == 3) {
              this.isTaskFinish = 0
              this.isTaskNotStart=1
            } else if (msg.status == 4 || msg.status == 5) {
              this.isTaskFinish = 0
              this.isTaskNotStart=1
            } else if (msg.status == 2) {
              this.isTaskFinish = 1
              this.isTaskNotStart= 1
            } else if(msg.status == 1)
            { this.isTaskFinish = 1
              this.isTaskNotStart= 0
            }
            console.log(this.currentStarttime + "is fucking ok!")
            console.log(this.currentDeadline + "is working normally")


            if (msg.status == 1 || msg.status == 2) {
              if (Date.parse(this.date) < Date.parse(this.currentStarttime.replace(/-/g, "/"))) {
                this.ifstart = 0//还没开始
                console.log("1")
                this.endstart = Date.parse(this.currentStarttime - this.date)
              } else if ((Date.parse(this.date) > Date.parse(this.currentStarttime.replace(/-/g, "/"))) && (Date.parse(this.date) < Date.parse(this.currentDeadline.replace(/-/g, "/")))) {
                this.ifstart = 1;//进行中
                console.log("2")
                this.starttask()//任务开始修改状态
                this.endfinish = Date.parse(this.currentDeadline) - Date.parse(this.date);
              } else {
                console.log("3")
                this.ifstart = 2;//已经结束
                this.delayTask()//过期修改状态
              }
            }

            console.log(this.ifstart)
            var _this = this;


            //前置任务未完成,此任务不能开始,需要对前置任务进行操作
            if (this.tasktype === 1 && (this.currentpretasksta !== null) && (this.currentpretasksta !== 3)) {
              this.cannotstart = 1
            } else {
              //时钟定时器
              this.timer = setInterval(function () {
                var start = new Date(_this.currentStarttime.replace(/-/g, "/"));
                var now = new Date()
                var finish = new Date(_this.currentDeadline.replace(/-/g, "/"))
                var nowstate = msg.status
                if (nowstate == 1) {
                  console.log("未开始")
                  console.log("rtt" + _this.currentTaskid)
                  console.log(now)
                  console.log(start)
                  var ddd = start.getTime() - now.getTime();
                  console.log(ddd)
                  if (ddd <= 0) {
                    console.log("到达开始时间")
                    console.log("aa" + _this)
                    _this.$http.get(_this.startTaskurl, {
                      params: {
                        id: _this.currentTaskid,
                      }
                    }).then(res => {
                      console.log("aaa")
                      var a = res.data
                      _this.currentStatus = 2
                    });

                  }
                } else if (nowstate == 2) {
                  console.log("进行中")

                }
                var dateDiff1 = start.getTime() - now.getTime();//未开始
                var dayDiff = Math.floor(dateDiff1 / (24 * 3600 * 1000));//计算出相差天数
                var leave1 = dateDiff1 % (24 * 3600 * 1000)    //计算天数后剩余的毫秒数
                var hours = Math.floor(leave1 / (3600 * 1000))//计算出小时数
                //计算相差分钟数
                var leave2 = leave1 % (3600 * 1000)    //计算小时数后剩余的毫秒数
                var minutes = Math.floor(leave2 / (60 * 1000))//计算相差分钟数
                //计算相差秒数
                var leave3 = leave2 % (60 * 1000)      //计算分钟数后剩余的毫秒数
                var seconds = Math.floor(leave3 / 1000)


                // console.log(" 相差 "+dayDiff+"天 "+hours+"小时 "+minutes+" 分钟"+seconds+" 秒")
                _this.endstart1 = dayDiff
                _this.endstart2 = hours
                _this.endstart3 = minutes
                _this.endstart4 = seconds

                var dateDiff2 = finish.getTime() - now.getTime();//进行中
                var dayDiff2 = Math.floor(dateDiff2 / (24 * 3600 * 1000));//计算出相差天数
                var leave21 = dateDiff2 % (24 * 3600 * 1000)    //计算天数后剩余的毫秒数
                var hours2 = Math.floor(leave21 / (3600 * 1000))//计算出小时数
                // //计算相差分钟数
                var leave22 = leave21 % (3600 * 1000)    //计算小时数后剩余的毫秒数
                var minutes2 = Math.floor(leave22 / (60 * 1000))//计算相差分钟数
                // //计算相差秒数
                var leave23 = leave22 % (60 * 1000)      //计算分钟数后剩余的毫秒数
                var seconds2 = Math.round(leave23 / 1000)
                // console.log(" 相差 "+dayDiff2+"天 "+hours2+"小时 "+minutes2+" 分钟"+seconds2+" 秒")
                _this.endfinish1 = dayDiff2
                _this.endfinish2 = hours2
                _this.endfinish3 = minutes2
                _this.endfinish4 = seconds2

              }, 1000)
            }

            console.log(this.ifstart)

            // if(this.currentStarttime)
            // if (
            //   this.taskData[this.currentTask].tomatoCompleted ==
            //   this.taskData[this.currentTask].expectedTomato
            // )
            //   this.currentCondition = true;
            // else this.currentCondition = false;


          })

        }
        else if (this.tasktype == 0) {
          this.currentDeadline = msg.finishtime;
          this.currentStarttime = msg.starttime;
          console.log(this.currentStarttime)
          console.log(this.currentDeadline)

          this.currentStatus = msg.status;
          this.currentTaskid = msg.id;
          this.currentCondition = false;
          // if (msg.status == 3) {
          //   this.isTaskFinish = 0
          // } else if (msg.status == 4 || msg.status == 5) {
          //   this.isTaskFinish = 0
          // } else if ( msg.status == 2) {
          //   this.isTaskFinish = 1
          // } else if(msg.status == 1){
          //   this.isTaskNotStart=0
          // }
          if (msg.status == 3) {
              this.isTaskFinish = 0
              this.isTaskNotStart=1
            } else if (msg.status == 4 || msg.status == 5) {
              this.isTaskFinish = 0
              this.isTaskNotStart=1
            } else if (msg.status == 2) {
              this.isTaskFinish = 1
              this.isTaskNotStart= 1
            } else if(msg.status == 1)
            { this.isTaskFinish = 1
              this.isTaskNotStart= 0
            }
          console.log(this.currentStarttime + "is fucking ok!")
          console.log(this.currentDeadline + "is working normally")


          if (msg.status == 1 || msg.status == 2) {
            if (Date.parse(this.date) < Date.parse(this.currentStarttime.replace(/-/g, "/"))) {
              this.ifstart = 0//还没开始
              console.log("1")
              this.endstart = Date.parse(this.currentStarttime - this.date)
            } else if ((Date.parse(this.date) > Date.parse(this.currentStarttime.replace(/-/g, "/"))) && (Date.parse(this.date) < Date.parse(this.currentDeadline.replace(/-/g, "/")))) {
              this.ifstart = 1;//进行中
              console.log("2")
              this.starttask()//任务开始修改状态
              this.endfinish = Date.parse(this.currentDeadline) - Date.parse(this.date);
            } else  {
              console.log("3")
              this.ifstart = 2;//已经结束
              this.delayTask()//过期修改状态
            }
          }

          console.log(this.ifstart)
          var _this = this;
            //时钟定时器
            this.timer = setInterval(function () {
              var start = new Date(_this.currentStarttime.replace(/-/g, "/"));
              var now = new Date()
              var finish = new Date(_this.currentDeadline.replace(/-/g, "/"))
              var nowstate = msg.status
              if (nowstate == 1&&nowstate!=4&&nowstate!=5) {
                console.log("未开始")
                var ddd = start.getTime() - now.getTime();
                console.log(ddd)
                if (ddd <= 0) {
                  console.log("到达开始时间")
                  _this.$http.get(_this.startTaskurl, {
                    params: {
                      id: _this.currentTaskid,
                    }
                  }).then(res => {
                    var a = res.data
                    _this.currentStatus = 2
                  });

                }
              } else if (nowstate == 2) {
                console.log("进行中")

              }
              var dateDiff1 = start.getTime() - now.getTime();//未开始
              var dayDiff = Math.floor(dateDiff1 / (24 * 3600 * 1000));//计算出相差天数
              var leave1 = dateDiff1 % (24 * 3600 * 1000)    //计算天数后剩余的毫秒数
              var hours = Math.floor(leave1 / (3600 * 1000))//计算出小时数
              //计算相差分钟数
              var leave2 = leave1 % (3600 * 1000)    //计算小时数后剩余的毫秒数
              var minutes = Math.floor(leave2 / (60 * 1000))//计算相差分钟数
              //计算相差秒数
              var leave3 = leave2 % (60 * 1000)      //计算分钟数后剩余的毫秒数
              var seconds = Math.floor(leave3 / 1000)


              // console.log(" 相差 "+dayDiff+"天 "+hours+"小时 "+minutes+" 分钟"+seconds+" 秒")
              _this.endstart1 = dayDiff
              _this.endstart2 = hours
              _this.endstart3 = minutes
              _this.endstart4 = seconds

              var dateDiff2 = finish.getTime() - now.getTime();//进行中
              var dayDiff2 = Math.floor(dateDiff2 / (24 * 3600 * 1000));//计算出相差天数
              var leave21 = dateDiff2 % (24 * 3600 * 1000)    //计算天数后剩余的毫秒数
              var hours2 = Math.floor(leave21 / (3600 * 1000))//计算出小时数
              // //计算相差分钟数
              var leave22 = leave21 % (3600 * 1000)    //计算小时数后剩余的毫秒数
              var minutes2 = Math.floor(leave22 / (60 * 1000))//计算相差分钟数
              // //计算相差秒数
              var leave23 = leave22 % (60 * 1000)      //计算分钟数后剩余的毫秒数
              var seconds2 = Math.round(leave23 / 1000)
              // console.log(" 相差 "+dayDiff2+"天 "+hours2+"小时 "+minutes2+" 分钟"+seconds2+" 秒")
              _this.endfinish1 = dayDiff2
              _this.endfinish2 = hours2
              _this.endfinish3 = minutes2
              _this.endfinish4 = seconds2

            }, 1000)
          }
          console.log(this.ifstart)
        },
      deleteTask() {
        this.$confirm("此操作将永久删除该任务, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(() => {
            this.$http
              .delete(this.deleteTaskUrl, {
                //params:
                // {
                // userid: this.taskData[this.currentTask].userid,
                // taskName: this.taskData[this.currentTask].name,
                // starttime: null
                params: {id: this.taskData[this.currentTask].id}
                // id:"1"
                // }
              })
              .then(response => {
                //重新加载页面
                this.$http
                  .get(this.currentTaskUrl, {
                    params: {userid: localStorage.userid}
                  })
                  .then(response => {
                    this.taskData = response.data;
                    var nlist = [];
                    for (var item of this.taskData) {
                      // if (item.deadline >= this.getcurrentTime)
                      nlist.push(item);
                    }
                    this.taskData = nlist;
                    this.selectCancel = true;
                    this.selected = false;
                    this.$message({
                      type: "success",
                      message: "删除成功!"
                    }),
                      response => {
                        console.log("failed");
                      };
                  });
              });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除"
            });
          });
      },
      //提交修改
      onSubmit() {
        this.$http
          .post(this.modifyTaskUrl, {

            id: localStorage.userid,
            // taskName: this.taskData[this.currentTask].name,
            description: "description",
            // value: this.currentTaskDetail

          },)
          .then(() => {
            this.$http
              .get(this.currentTaskUrl, {
                params: {userid: localStorage.userid}
              })
              .then(response => {
                this.taskData = response.data;
                var nlist = [];
                for (var item of this.taskData) {
                  //if (item.deadline >= this.getcurrentTime)
                  nlist.push(item);
                }
                this.taskData = nlist;
                this.$message({
                  message: "保存成功！",
                  type: "success"
                });
              }),
              response => {
                console.log(failed);
              };
          });
      },
      dateFtt(fmt, date) {
        //author: meizz
        var o = {
          "M+": date.getMonth() + 1, //月份
          "d+": date.getDate(), //日
          "h+": date.getHours(), //小时
          "m+": date.getMinutes(), //分
          "s+": date.getSeconds(), //秒
          "q+": Math.floor((date.getMonth() + 3) / 3), //季度
          S: date.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt))
          fmt = fmt.replace(
            RegExp.$1,
            (date.getFullYear() + "").substr(4 - RegExp.$1.length)
          );
        for (var k in o)
          if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(
              RegExp.$1,
              RegExp.$1.length == 1
                ? o[k]
                : ("00" + o[k]).substr(("" + o[k]).length)
            );
        return fmt;
      },
      currentTime() {
        var time = new Date();
        return this.dateFtt("yyyy-MM-dd hh:mm:ss", time);
      },
      saveDailySummary() {
        if (this.currentSummaryText == "提交") {
          this.$http
            .post(this.dailySummaryUrl, {

              // userid: sessionStorage.userId,
              userid: localStorage.userId,
              content: this.dailySummary,
              // date: this.getcurrentTime,
              score: this.selfRating

            },)
            .then(response => {
              this.currentSummaryId = response.data
              this.$http
                .get(this.currentTaskUrl, {
                  params: {userid: localStorage.userid}
                })
                .then(response => {
                  this.taskData = response.data;
                  var nlist = [];
                  for (var item of this.taskData) {
                    nlist.push(item);
                  }
                  this.taskData = nlist;
                  this.$message({
                    message: "保存成功！",
                    type: "success"
                  });
                }),
                response => {
                  console.log(failed);
                };
            });
        } else {
          this.changeSummary()
        }
      },
      changeSummary() {
        this.$http
          .post(this.changeDailySummaryUrl, {


            id: this.currentSummaryId,
            content: this.dailySummary,
            score: this.selfRating

          },)
          .then(() => {
            this.$http
              .get(this.currentTaskUrl, {
                params: {userid: localStorage.userid}
              })
              .then(response => {
                this.taskData = response.data;
                var nlist = [];
                for (var item of this.taskData) {
                  nlist.push(item);
                }
                this.taskData = nlist;
                this.$message({
                  message: "保存成功！",
                  type: "success"
                });
              }),
              response => {
                console.log(failed);
              };
          });

      },
      getSummary() {
        this.$http
          .get(this.summaryRequestUrl, {
            params: {userid: localStorage.userId}
          })
          .then(response => {
            //如果有小结
            console.log("summary")
            if (response.data) {
              this.currentSummaryText = "修改"
              this.dailySummary = response.data.content
              this.selfRating = response.data.score
              this.currentSummaryId = response.data.id
            } else {
              this.currentSummaryText = "提交"
            }
            //设置按钮状态
          }),
          response => {
            console.log(failed);
          };
      },
      starttask: function () {
        //个人任务
        // if(this.tasktype==0)
        // {
        this.$http.get(this.startTaskurl, {
          params: {
            id: this.currentTaskid,
          }
        }).then(res => {
          var a = res.data
          this.currentStatus = 2
        });
        // }
        // //团队任务
        // else if(tasktype==1)
        // {
        this.$http.get(this.startProjectTaskurl, {
          params: {
            taskid: this.currentTaskid,
          }
        }).then(res => {
          var a = res.data
          this.currentStatus = 2
        });
        this.refreshTask()
        // }
      },
      finishTask() {//完成任务
        console.log("task" + this.tasktype)
        //个人任务
        if (this.tasktype == 0) {
          this.$http.get(this.endTaskUrl, {
            params: {
              id: this.currentTaskid,
              time: new Date().format("yyyy-MM-dd hh:mm:ss")
            }
          }).then(res => {
            this.currentStatus = 3
            this.isTaskFinish = 0

            this.$message({
              type: 'info',
              message: "任务完成！"
            });

          });
        } else if (this.tasktype == 1) {
          console.log("铁狮门 ")
          //团队任务
          this.$http.get(this.endProjectTaskUrl, {
            params: {
              taskid: this.currentTaskid,
              time: new Date().format("yyyy-MM-dd hh:mm:ss")
            }
          }).then(res => {
            this.currentStatus = 3
            this.isTaskFinish = 0

            this.$message({
              type: 'info',
              message: "任务完成！"
            });

          });
        }
                this.refreshTask()

      },
      //推迟团队任务
      putoff() {

      },
      breakTask() {//中断
        this.$http.get(this.breakTaskUrl, {
          params: {
            id: this.currentTaskid,
            time: new Date().format("yyyy-MM-dd hh:mm:ss")
          }
        }).then(res => {
          this.currentStatus = 4
          this.isTaskFinish = 0
          this.getCurrentTaskList()

        });
                this.refreshTask()

      },
      delayTask() {//过期
        if (this.tasktype == 0) {//个人
          this.$http.get(this.delayTaskUrl, {
            params: {
              id: this.currentTaskid,
              time: new Date().format("yyyy-MM-dd hh:mm:ss")
            }
          }).then(res => {
            this.currentStatus = 4
            this.getCurrentTaskList()
          });
        } else if (this.tasktype == 1) {//团队
          this.$http.get(this.delayProjectTaskUrl, {
            params: {
              taskid: this.currentTaskid,
              //time: new Date().format("yyyy-MM-dd hh:mm:ss")
            }
          }).then(res => {
            this.currentStatus = 5
            this.getCurrentTaskList()
          });
        }
        this.refreshTask()

      },
      getCurrentTaskList() {//重新获取任务列表
        this.$http
          .get(this.currentTaskUrl, {
            // params: { userId: sessionStorage.userId }
            params: {userid: localStorage.userid}
          })
          .then(response => {
            this.taskData = response.data;
            var nlist = [];
            for (var item of this.taskData) {
              // if (item.deadline >= this.getcurrentTime)
              nlist.push(item);
            }
            this.taskData = nlist;
          }),
          response => {
            console.log("failed");
          };
        console.log(this.taskData);
        return;
      },
      refreshTask() {
        console.log("refreshTask")
        console.log(this.currentTaskUrl)
          this.$http
            .get(this.currentTaskUrl, {
              // params: { userId: sessionStorage.userId }
              params: {userid: localStorage.userid}
            })
            .then(response => {
              this.taskData = response.data;
      });
     
   
    }
    }
  };
      

</script>

<style>
  .el-dropdown-link {
    cursor: pointer;

  }

  .el-icon-arrow-down {
    font-size: 12px;
  }
</style>
