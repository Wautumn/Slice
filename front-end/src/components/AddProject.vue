<template>
  <div>
    <el-form label-position="left" ref="form" :model="form" label-width="100px">
      <el-form-item label="团队任务名称">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="任务描述">
        <el-input v-model="form.description"></el-input>
      </el-form-item>
      <el-form-item label="任务成员">
        <el-col :span="11">
          <el-input v-model="nowpeople" clearable></el-input>
        </el-col>
        <el-col class="line" :span="2">-</el-col>
        <el-col :span="11">
          <el-button @click="addpeople">添加</el-button>
        </el-col>
      </el-form-item>
      <el-form-item label="当前成员">
        <el-col :span="11"></el-col>
        <el-tag
          v-for="people in form.joiner" :key="people" closable :disable-transitions="false" @close="handleClose(tag)">
          {{people}}
        </el-tag>

      </el-form-item>

      <el-form-item label="子任务">
        <el-row :span="24">
          <el-form-item label="子任务名称：">
            <el-input v-model="nowsubtask.name"></el-input>
          </el-form-item>
        </el-row>
        <el-row :span="24">
          <el-form-item label="子任务描述：">
            <el-input v-model="nowsubtask.description"></el-input>
          </el-form-item>
        </el-row>
        <!--<el-col class="line" :span="2">-</el-col>-->
        <el-form-item label="开始时间">
          <el-row :span="11">
            <el-date-picker type="datetime" placeholder="选择时间" v-model="nowsubtask.starttime" style="width: 100%;"
                            value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
          </el-row>
        </el-form-item>
        <el-form-item label="结束时间">
          <el-row :span="11">
            <el-date-picker type="datetime" placeholder="选择时间" v-model="nowsubtask.finishtime" style="width: 100%;"
                            value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
          </el-row>
        </el-form-item>
        <el-form-item label="子任务成员">
          <el-row :span="11">
            <el-checkbox-group
              v-model="nowsubtask.subtaskuser"
              :min="1"
              :max="5">
              <el-checkbox v-for="user in form.joiner" :label="user" :key="user">{{user}}</el-checkbox>
            </el-checkbox-group>
          </el-row>
        </el-form-item>

        <el-form-item label="前置任务">
          <el-radio v-model="radio" label="1">存在前置任务</el-radio>
          <el-radio v-model="radio" label="2">不存在前置任务</el-radio>

          <el-select v-if="radio==1" v-model="nowsubtask.beforetask" placeholder="请选择">
            <el-option
              v-for="item in form.subtask"
              :key="item.name"
              :label="item.name"
              :value="item">
            </el-option>
          </el-select>
        </el-form-item>

        <el-col :span="11">
          <el-button @click="addsubtask">确认添加</el-button>
        </el-col>
      </el-form-item>

      <el-form-item label="当前子任务">
        <el-col :span="11"></el-col>
        <el-table
          :data="form.subtask"
          height="250"
          border
          style="width: 100%">
          <el-table-column
            prop="name"
            label="名称"
            width="180">
          </el-table-column>
          <el-table-column
            prop="starttime"
            label="开始时间"
            width="180">
          </el-table-column>
          <el-table-column
            prop="finishtime"
            label="结束时间" width="180">
          </el-table-column>
          <el-table-column
            prop="beforetask.name"
            label="前置任务" width="180">
          </el-table-column>
          <!--<el-table-column-->
            <!--label="操作">-->
            <!--<template slot-scope="scope">-->
              <!--<el-button @click="deleteaa(scope.row)" type="text">删除任务</el-button>-->
              <!--<br>-->
            <!--</template>-->
          <!--</el-table-column>-->
        </el-table>
        <!--<el-tag-->
        <!--v-for="subtask in form.subtask" :key="people" closable :disable-transitions="false" @close="handleClose(subtask)">-->
        <!--{{subtask.name}} 开始时间{{subtask.starttime}} 结束时间{{subtask.finishtime}}-->
        <!--</el-tag>-->
      </el-form-item>

      <el-form-item label="开始时间">
        <el-col :span="11">
          <el-date-picker type="datetime" placeholder="选择时间" v-model="form.starttime" style="width: 100%;"
                          value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
        </el-col>

      </el-form-item>

      <el-form-item label="结束时间">
        <el-col :span="11">
          <el-date-picker type="datetime" placeholder="选择时间" v-model="form.finishtime" style="width: 100%;"
                          value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
        </el-col>

      </el-form-item>


      <el-form-item>
        <el-button type="primary" @click="onSubmit">立即创建</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>


  </div>


</template>

<script>
  export default {
    name: "addProject",
    data() {
      return {
        newprojecturl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT//createProject",
        fingpeopleurl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/findUserid",
        addsubtaskurl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/addSubTasks",
        setpretaskurl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/setPreTask",
        findprojecttaskid: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/findProjectTaskId",


        dialogVisible: false,
        form: {
          name: '',
          description: '',
          joiner: [],
          subtask: [],
          starttime: '',
          finishtime: '',
        },


        userid: localStorage.userid,
        nowpeople: '',//当前添加的人员
        nowsubtask: {
          name: '',
          description: '',
          starttime: '',
          finishtime: '',
          subtaskuser: [],
          beforetask: null,

        }, //当前添加的子任务

        joinerid: [],
        addpeopleresult: 0,

        radio: '1',
        value: null,
      }

    },
    methods: {
      //创建任务的总提交
      onSubmit() {
        console.log('submit!');
        console.log(this.form)

        var start = new Date(this.form.starttime.replace(/-/g, "/"));
        var finish = new Date(this.form.finishtime.replace(/-/g, "/"));
        console.log(start.getDay())

        var projectstart = this.form.starttime
        var projectend = this.form.finishtime
        var start = new Date(projectstart.replace(/-/g, "/"));//项目开始时间
        var end = new Date(projectend.replace(/-/g, "/"));//项目结束时间

        var minstart = null;
        var maxend = 0;

        // var flag=0
        for (var i = 0; i < this.form.subtask.length; i++) {
          let substart = new Date(this.form.subtask[i].starttime.replace(/-/g, "/"));
          let subend = new Date(this.form.subtask[i].finishtime.replace(/-/g, "/"));
          if (minstart === null)
            minstart = substart.getTime()
          else {
            if (substart < minstart)
              minstart = substart.getTime()
          }
          if (maxend === null)
            maxend = subend.getTime()
          else {
            if (subend > maxend)
              maxend = subend.getTime()
          }

        }
        if (start.getTime() > minstart || end.getTime() < maxend) {
          this.$message.error('添加团队任务失败！任务与子任务时间冲突！');
          return
        }


        if (start.getTime() > finish.getTime()) {
          this.$message('添加团队任务失败！任务起始时间应早于任务结束时间！');
        } else if (start.getTime() < new Date().getTime()) {
          this.$message('添加团队任务失败！任务开始时间应晚于当前');
        } else if (this.form.name == "" || this.form.starttime === "" || this.form.finishtime === "" || this.form.joiner.length === 0) {
          this.$message('添加团队任务失败！请正确填写相关信息！');
        } else {
          this.$http
            .post(this.newprojecturl, {
              userid: localStorage.userid,//this.userid,
              name: this.form.name,
              description: this.form.description,
              members: this.form.joiner,
              subtasks: [],
              starttime: this.form.starttime,
              endtime: this.form.finishtime
            },).then(response => {
            console.log(response.data);
            if (response.data == -1) {
              this.$alert('添加失败', {
                confirmButtonText: '确定',
              });
            } else {
              console.log("添加项目成功")
              var nowprojectid = response.data
              console.log("项目id" + nowprojectid)
              //添加子任务
              console.log("下面是添加子任务啦")

              for (let i = 0; i < this.form.subtask.length; i++) {
                console.log("添加子任务" + this.form.subtask[i].name)
                this.$http
                  .post(this.addsubtaskurl,
                    {
                      name: this.form.subtask[i].name,
                      description: this.form.subtask[i].description,
                      starttime: this.form.subtask[i].starttime,
                      endtime: this.form.subtask[i].finishtime,
                      projectid: nowprojectid,
                      usernames: this.form.subtask[i].subtaskuser
                    }).then(response => {
                  var subtaskid = response.data;
                  console.log("增加的子任务成功");
                  console.log(subtaskid);
                  //增加前置任务
                  console.log("看看前置任务有没有2" + this.form.subtask[i].beforetask + i);
                  if (this.form.subtask[i].beforetask != null) {
                    //根据名字去找前置任务的id

                    console.log("现在是添加前置任务")
                    var beforetaskname = this.form.subtask[i].beforetask.name;//前置任务的名字
                    var beforetaskid;//前置任务的id
                    this.$http.get(this.findprojecttaskid, {
                      params: {
                        projectid: nowprojectid,
                        name: beforetaskname,
                      }
                    }).then(response => {
                      beforetaskid = response.data
                      console.log("找到子任务的id是" + beforetaskid)

                      this.$http.get(this.setpretaskurl, {
                        params:
                          {
                            projectid: nowprojectid,
                            taskid: subtaskid,
                            preid: beforetaskid

                          }
                      }).then(response => {
                        if (response.data === -5) {
                          this.$message.error("添加任务" + this.form.subtask[i].name + "前置任务失败！时间冲突！")
                        }
                        console.log("添加子任务的结果" + response.data)
                      })
                    })


                  }
                })
              }
              this.$router.push('/Empty')
              this.$alert('新建成功', {
                confirmButtonText: '确定',
              });

            }
          });
        }

      },

      //添加前置任务的
      addpre: function () {
        console.log(this.form.subtask)

        for (var i = 0; i < this.form.subtask.length; i++) {
          var before = this.form.subtask[i].beforetask
          if (before != null) {
            //根据名字去找前置任务的id
            console.log("现在是添加前置任务")
            var beforetaskname = before;//前置任务的名字
            var beforetaskid;//前置任务的id
            this.$http.get(this.findprojecttaskid, {
              params: {
                projectid: nowprojectid,
                name: beforetaskname,
              }
            }).then(response => {
              beforetaskid = response.data
              console.log("找到子任务的id是" + beforetaskid)
              this.$http.get(this.setpretaskurl, {
                params:
                  {
                    projectid: nowprojectid,
                    taskid: subtaskid,
                    preid: beforetaskid

                  }
              }).then(response => {
                console.log("添加子任务的结果" + response.data)
              })
            })
          }
        }

      },


      addpeople: function () {
        console.log("xianzai de")
        console.log(this.form.joiner)
        console.log(this.nowpeople)
        var flag = 1
        for (var a = 0; a < this.form.joiner.length; a++) {
          console.log("wsha")
          console.log(a)
          console.log(this.nowpeople)
          console.log("bbbbb")

          if (this.form.joiner[a] === this.nowpeople) {
            console.log("aaa")
            flag = 0
          }
        }
        if (flag === 0) {
          this.$alert('此用户已经存在！', '添加失败', {
            confirmButtonText: '确定',
          })
          this.nowpeople = null
        } else {
          this.$http
            .get(this.fingpeopleurl, {params: {username: this.nowpeople}})
            .then(response => {
              console.log(response.data)
              if (response.data == -1) {
                this.$alert('此用户不存在！', '添加失败', {
                  confirmButtonText: '确定',
                })
                this.nowpeople = null;
              } else {
                this.$alert('已添加' + this.nowpeople, '添加成功', {
                  confirmButtonText: '确定',
                });
                this.joinerid.push(response.data)
                this.form.joiner.push(this.nowpeople)
                this.nowpeople = null
              }
            }),

            console.log(this.nowpeople)
        }

      },
      //子任务添加到任务表单里面去

      addsubtask: function () {
        console.log("a")
        var current = Object.assign({}, this.nowsubtask)
        var start = new Date(current.starttime.replace(/-/g, "/"));
        var finish = new Date(current.finishtime.replace(/-/g, "/"));
        console.log(current)
        console.log(start.getTime())

        var beforeend = null
        if (current.beforetask != null) {
          beforeend = current.beforetask.finishtime
          var compare = new Date(beforeend.replace(/-/g, "/"));
          if (compare.getTime() > start.getTime()) {
            this.$message.error('添加子任务失败！前置任务时间冲突！');
            return
          }
        }

        if (start.getTime() > finish.getTime()) {
          this.$message.error('添加子任务失败！任务起始时间应早于任务结束时间！');
        } else if (current.starttime === "" || current.finishtime === "" || current.subtaskuser.length === 0 || current.name === "") {
          this.$message.error('添加子任务失败！请填写完整信息！');

        } else if (start.getTime() < new Date().getTime()) {
          this.$message.error('添加子任务失败！子任务开始时间应晚于当前');
          return
        } else if (this.form.subtask.length > 0) {
          var flag = 0
          for (var i = 0; i < this.form.subtask.length; i++) {
            if (this.form.subtask[i].name == current.name) {
              flag = 1
            }
          }
          if (flag === 1) {
            this.$message.error('添加子任务失败！当前存在同名子任务！');
            console.log("fanafnnnn")
            return
            console.log("fanafnnnn")
          } else {
            console.log("jinlai")
            this.form.subtask.push(current)
            console.log(this.nowsubtask)
            console.log(this.form.subtask)
            this.$message('添加子任务成功！');
          }


        } else {
          console.log("jinlai")
          this.form.subtask.push(current)
          console.log(this.nowsubtask)
          console.log(this.form.subtask)
          this.$message('添加子任务成功！');

        }
        // this.nowsubtask.name = null
        // this.nowsubtask.description = null
        // this.nowsubtask.finishtime = null
        // this.nowsubtask.starttime = null
        // this.nowsubtask.subtaskuser = null
        this.nowsubtask.beforetask = null


      },

      handleClose2(task) {
        console.log("删除")
        console.log(task, "a删除的名字")
        // for(var i=0;i<this.form.subtask.length;i++){
        //   if(this.form.subtask[i]!==null) {
        //     if (this.form.subtask[i].beforetask.name === task.name) {
        //       this.$message.error("此任务存在后置任务，请级联删除！")
        //       return
        //     }
        //   }
        // }

        console.log("删除")
        console.log(task)

        this.form.subtask.splice(this.form.subtask.indexOf(task), 1);
      },
      deleteaa(row) {

        window.console.log(row.name)
        for (var i = 0; i < this.form.subtask.length; i++) {
          if (this.form.subtask[i].name === row.name) {

          }
        }
        this.$alert('任务名' + row.name, '确认删除', {
          confirmButtonText: '确定',
          callback: action => {
            for (var i = 0; i < this.form.subtask.length; i++) {
              if (this.form.subtask[i].beforetask !== null) {
                // if (this.form.subtask[i].beforetask.name === row.name) {
                  this.$message.error("删除错误！！！")
                // }
              } else {
                this.$message("删除成功！！！")
                this.form.subtask.splice(this.form.subtask.indexOf(row), 1);
              }
            }

          }
        });

        window.console.log("dsds")
      }


    }
  }
</script>

<style scoped>
  .header {
    color: black;
    font-size: 20px;

  }

  .el-row {
    margin-bottom: 20px;

  }

  .el-col {
    border-radius: 4px;
  }

  .bg-purple-dark {
    background: slategrey;
  }

  .bg-purple {
    background: #d3dce6;
  }

  .bg-purple-light {
    background: #e5e9f2;
  }

  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }

  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
</style>
