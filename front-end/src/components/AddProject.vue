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
              :value="item.name">
            </el-option>
          </el-select>
        </el-form-item>

        <el-col :span="11">
          <el-button @click="addsubtask">确认添加</el-button>
        </el-col>
      </el-form-item>

      <el-form-item label="当前子任务">
        <el-col :span="11"></el-col>
        <el-tag
          v-for="subtask in form.subtask" :key="people" closable :disable-transitions="false" @close="handleClose(tag)">
          {{subtask.name}}
        </el-tag>
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

        form: {
          name: '',
          description: '',
          joiner: [],
          subtask: [],
          starttime: '',
          finishtime: '',
        },


        userid: 10,
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
        this.$http
          .post(this.newprojecturl, {
            userid: 10,//this.userid,
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
              var before=new Object()
              before = this.form.subtask[i].beforetask
              Object.freeze(before)
              console.log("看看前置任务有没有" + before + i );
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
                console.log("看看前置任务有没有2" + this.form.subtask[i].beforetask+i);
                if (this.form.subtask[i].beforetask != null) {
                  //根据名字去找前置任务的id
                  console.log("现在是添加前置任务")
                  var beforetaskname = this.form.subtask[i].beforetask;//前置任务的名字
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
              })
            }
            this.$alert('新建成功', {
              confirmButtonText: '确定',
            });
          }
        });
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
        this.$http
          .get(this.fingpeopleurl, {params: {username: this.nowpeople}})
          .then(response => {
            console.log(response.data)
            if (response.data == -1) {
              this.$alert('此用户不存在！', '添加失败', {
                confirmButtonText: '确定',
              });
            } else {
              this.$alert('已添加' + this.nowpeople, '添加成功', {
                confirmButtonText: '确定',
              });
              this.joinerid.push(response.data)
              this.form.joiner.push(this.nowpeople)
            }
          }),

          console.log(this.nowpeople)
      },
      //子任务添加到任务表单里面去
      addsubtask: function () {
        var current = Object.assign({}, this.nowsubtask)
        this.form.subtask.push(current)
        console.log(this.nowsubtask)
        console.log(this.form.subtask)
      },
      handleClose(tag) {
        this.form.joiner.splice(this.form.joiner.indexOf(tag), 1);
      },

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
