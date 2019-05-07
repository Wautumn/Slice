<template>
  <div>
    <el-container>
      <el-aside width="400px">
        <tasklist @transferTask="getTasksByUserid" :tableData="taskData"></tasklist>
      </el-aside>

      <el-main>
        <div style="text-align: center">
          <strong
            style="font-size: 200px; line-height: 300px; color: #409eff"
          >{{minutes}}:{{seconds}}</strong>
          <br>
          <el-button
            :type="countButtonType"
            v-show="!currentCondition"
            :disabled="countOn || currentStatus == -1"
            @click="startCount"
          >开始时钟</el-button>
          <el-button
            type="warning"
            v-show="!currentCondition"
            :disabled="!countOn"
            @click="stopCount"
          >中止时钟</el-button>
          <el-button
            type="success"
            v-show="currentCondition"
            :disabled="currentStatus == 2"
            @click="finishTask"
          >完成任务</el-button>
          <br>
        </div>
        <div v-show="selected" style="margin-left: 20px; margin-right: 20px;">
          <div style="margin-top: 20px; margin-bottom:20px;">
            <h1>{{currentTaskName}}</h1>
          </div>
          <el-steps :active="currentFinishedPomo" space="100px" finish-status="success">
            <el-step v-for="n in currentTotalPomo"></el-step>
          </el-steps>
          <br>
          <h1 v-show="selected" style="margin-top: 10px;">任务时间</h1>
          <div style="margin-top: 10px; margin-bottom: 10px; margin-left: 20px;">
            <h2>至{{currentDeadline}}</h2>
          </div>
          <h1 v-show="selected">任务详情</h1>
          <div style="margin-top: 20px">
            <el-form v-show="selected">
              <el-form-item>
                <el-input type="textarea" v-model="currentTaskDetail" :disabled="currentCondition"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="onSubmit">保存</el-button>
                <el-button type="danger" style="float: right;" @click="deleteTask">删除记录</el-button>
                <el-button
                  type="warning"
                  style="float: right;"
                  :disabled="currentStatus == -1"
                  @click="breakTask"
                >废弃任务</el-button>
              </el-form-item>
            </el-form>
          </div>
        </div>
        <el-footer height="60px" style="margin-top: 30px;">
          <h1>每日小结 {{getcurrentTime}}</h1>
          <Rate show-text allow-half v-model="selfRating">
            <span style="color: #f5a632">{{selfRating}}</span>
          </Rate>
          <el-input
            type="textarea"
            v-model="dailySummary"
            style="margin-top: 10px; margin-bottom:10px;"
          ></el-input>
          <el-button type="primary" @click="saveDailySummary" style="float: right;">提交</el-button>
        </el-footer>
      </el-main>
    </el-container>
    
  </div>
  
</template>

<script>
import { clearInterval } from "timers";
import { setInterval } from "timers";
import TaskList from "@/components/TaskList";

export default {
  components: {
    tasklist: TaskList
  },
  data() {
    return {
      tomoStartTime: "",
      dailySummary: "",
      selfRating: 0,
      countButtonType: "success",
      countOn: false,
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
      currentStatus: -2,
      currentDeadline: null,
      taskData: [],
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

      taskRequestUrl: "http://101.132.194.45:8080/slice-0.0.1-SNAPSHOT/getTasksByUserid",
      deleteTaskUrl: "http://101.132.194.45:8080/slice-0.0.1-SNAPSHOT/deleteTask",

      taskStartUrl: "http://101.132.194.45:8080/slice-0.0.1-SNAPSHOT/task/startTask",
      tomatoStartUrl: "http://101.132.194.45:8080/slice-0.0.1-SNAPSHOT/startTomato",
      tomatoBreakUrl: "http://101.132.194.45:8080/slice-0.0.1-SNAPSHOT/breakTomato",
      tomatoEndUrl: "http://101.132.194.45:8080/slice-0.0.1-SNAPSHOT/endTomato", 
      modifyTaskUrl: "http://101.132.194.45:8080/slice-0.0.1-SNAPSHOT/changeTaskDescription",
      breakTaskUrl: "http://101.132.194.45:8080/slice-0.0.1-SNAPSHOT/task/breakTask",
      endTaskUrl: "http://101.132.194.45:8080/slice-0.0.1-SNAPSHOT/task/endTask",
      dailySummaryUrl: "http://101.132.194.45:8080/slice-0.0.1-SNAPSHOT/summary/save"
    };
  },
  //加载
  mounted() {
    sessionStorage.userId = "1";
    this.getcurrentTime = this.currentTime();
    this.getcurrentTime = this.getcurrentTime.substring(0, 19);
    this.$http
      .get(this.taskRequestUrl, { params: { userid: sessionStorage.userId } })
      .then(response => {
        this.taskData = response.data;
        var nlist = [];
        for (var item of this.taskData) {
          // if (item.deadline >= this.getcurrentTime) 
          nlist.push(item);
        }
        this.taskData = nlist;
      }),
      response => {};

    sessionStorage.listLock = "false";
    console.log(this.getcurrentTime);
  },
  methods: {
    //*********************
    startCount() {
      if (!this.selected) {
        this.$alert("请选择一个任务以开始本番茄钟", "提示", {
          confirmButtonText: "确定",
          callback: action => {}
        });
        return;
      }
      if (sessionStorage.tomoLength) {
        var TIME_COUNT = parseInt(sessionStorage.tomoLength) * 60;
      } else {
        var TIME_COUNT = 5;
      }
      var TIME_COUNT = 5;
      if (!this.timer) {
        this.count = TIME_COUNT;
        this.countOn = true;
        sessionStorage.listLock = "true";
        this.countButtonType = "danger";
        this.seconds = this.count % 60;
        this.minutes = parseInt(this.count / 60);
        if (this.taskData[this.currentTask].status == 0) {
          this.$http.get(this.taskStartUrl, {
            params: {
              userid: sessionStorage.userId,
              taskName: this.taskData[this.currentTask].name
            }
          });
          this.taskData[this.currentTask].status = 1;
        }
        this.tomoStartTime = new Date().format("yyyy-MM-dd hh:mm:ss");
        this.$http.get(this.tomatoStartUrl, {
          params: {
            userid: sessionStorage.userId,
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
            sessionStorage.listLock = "false";
            if (this.count == 0) {
              this.currentFinishedPomo++;
              this.taskData[this.currentTask].tomatoCompleted++;
              this.$http.get(this.tomatoEndUrl, {
                params: {
                  userid: sessionStorage.userId,
                  needAssociation: true,
                  taskName: this.currentTaskName,
                  startTime: this.tomoStartTime,
                  endTime: new Date().format("yyyy-MM-dd hh:mm:ss")
                }
              });
              this.$http.get(this.modifyTaskUrl, {
                params: {
                  userid: sessionStorage.userId,
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
          userid: sessionStorage.userId,
          breakTime: new Date().format("yyyy-MM-dd hh:mm:ss"),
          startTime: this.tomoStartTime
        }
      });
    },
    //**************************** */

    //！！！！！！！！！！！！！！！！！！
    getTasksByUserid(msg) {
      if (msg == "new") {
        this.$http
          .get(this.taskRequestUrl, {
            // params: { userId: sessionStorage.userId }
            params:{userid:"1"}
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
      this.currentDeadline = msg.finishtime;
      this.currentStatus = msg.status;
      this.currentCondition = false;
      // if (
      //   this.taskData[this.currentTask].tomatoCompleted ==
      //   this.taskData[this.currentTask].expectedTomato
      // )
      //   this.currentCondition = true;
      // else this.currentCondition = false;
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
                params:{id:this.taskData[this.currentTask].id}
                // id:"1"
             // }
            })
            .then(response => {
              //重新加载页面
              this.$http
                .get(this.taskRequestUrl, {
                  params: { userid: sessionStorage.userId }
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
    finishTask() {
      this.$http.get(this.endTaskUrl, {
        params: {
          userid: sessionStorage.userId,
          taskName: this.currentTaskName,
          startTime: new Date().format("yyyy-MM-dd hh:mm:ss")
        }
      });
      this.taskData[this.currentTask].status = 2;
      this.currentStatus = 2;
    },
    breakTask() {
      this.$confirm("此操作将废弃当前任务, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$http.get(this.breakTaskUrl, {
            params: {
              userid: sessionStorage.userId,
              taskName: this.currentTaskName
            }
          });
          this.taskData[this.currentTask].status = -1;
          this.currentStatus = -1;
          this.$message({
            type: "success",
            message: "废弃成功!"
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消废弃"
          });
        });
    },
    //提交修改
    onSubmit() {
      this.$http
        .post(this.modifyTaskUrl, {
         
            id: sessionStorage.userId,
            // taskName: this.taskData[this.currentTask].name,
            description: "description",
            // value: this.currentTaskDetail
          
        },)
        .then(() => {
          this.$http
            .get(this.taskRequestUrl, {
              params: { userid: sessionStorage.userId }
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
      this.$http
        .get(this.dailySummaryUrl, {
          params: {
            userid: sessionStorage.userId,
            content: this.dailySummary,
            time: this.getcurrentTime,
            selfRating: this.selfRating
          }
        })
        .then(() => {
          this.$http
            .get(this.taskRequestUrl, {
              params: { userid: sessionStorage.userId }
            })
            .then(response => {
              this.taskData = response.data;
              var nlist = [];
              for (var item of this.taskData) {
                if (item.deadline >= this.getcurrentTime) nlist.push(item);
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
    }
  }
};
</script>

<style>
</style>