<template>
  <div>

    <el-table
      :data="alltasks"
      style="width: 100%">

      <el-table-column
        prop="name"
        label="任务名"
        width="180">
      </el-table-column>
      <el-table-column
        prop="description"
        label="任务描述"
        width="180">
      </el-table-column>
      <el-table-column
        prop="starttime"
        label="开始时间"
        width="100">
      </el-table-column>
      <el-table-column
        prop="endtime"
        label="结束时间"
        width="100">
      </el-table-column>
      <el-table-column
        prop="address"
        label="实际完成时间"
        width="100">
      </el-table-column>
      <el-table-column
        prop="status"
        label="状态"
        width="100">
      </el-table-column>
      <el-table-column
        prop="usedata"
        label="负责人"
        width="180">
      </el-table-column>
      <el-table-column
        prop="pretasks[0].name"
        label="前置任务"
        width="180">
      </el-table-column>
      <el-table-column
        label="操作"
        width="100">
        <template slot-scope="scope">
          <!--<el-button @click="addpeople(scope.row)" type="text">添加成员</el-button>-->
          <!--<br>-->
          <el-button @click="handleClick(scope.row)" type="text">推迟任务</el-button>
          <br>
          <el-button @click="deletesubtask(scope.row)" type="text">删除任务</el-button>
          <br>
        </template>
      </el-table-column>

    </el-table>

    <el-button class="el-icon-circle-plus-outline" style="position:relative" @click="dialogVisible1=true">添加任务
    </el-button>

    <el-dialog
      title="推迟任务"
      :visible.sync="delayvisible"
      width="50%"
      :before-close="closedialog">

      <!--<span>原结束时间：{{currentrow}}</span>-->
      <!--<br><br>-->
      <span>推迟至：</span>
      <el-date-picker type="datetime" placeholder="选择时间" style="width: 100%;" v-model="delaytaskendtime"
                      value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
      <br><br>
      <span>影响任务：</span>
      <div v-for="(before,index) in currentbeforetask" class="content">
        {{before.name}}
        <br>
        <el-col :span="12">
          <el-date-picker type="datetime" placeholder="修改开始时间" style="width: 100%;" v-model="addtask.endtime"
                          value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
        </el-col>

        <el-col :span="12">
          <el-date-picker type="datetime" placeholder="修改结束时间" style="width: 100%;" v-model="addtask.endtime"
                          value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
        </el-col>

      </div>

      <br>

      <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible1 = false">取 消</el-button>
            <el-button type="primary" @click="delaytask">确 定</el-button>
          </span>
    </el-dialog>


    <el-dialog
      title="添加子任务"
      :visible.sync="dialogVisible1"
      width="30%"
      :before-close="closedialog">
      <span>任务名</span>
      <el-input v-model="addtask.name"></el-input>
      <span>任务描述</span>
      <el-input v-model="addtask.description"></el-input>
      <span>任务成员</span>
      <el-checkbox-group
        v-model="addtask.users"
        :min="1"
        :max="5">
        <el-checkbox v-for="(user,index) in allusers" :label="user" :key="user">{{user}}</el-checkbox>
      </el-checkbox-group>

      <br>
      <span>前置任务</span>
      <el-radio v-model="radio" label="1">存在前置任务</el-radio>
      <el-radio v-model="radio" label="2">不存在前置任务</el-radio>
      <br><br>
      <el-select v-if="radio==1" v-model="addtask.pretask" placeholder="请选择">
        <el-option
          v-for="item in alltasks"
          :key="item.name"
          :label="item.name"
          :value="item.id">
        </el-option>
      </el-select>

      <br>
      <span>开始时间</span>
      <el-date-picker type="datetime" placeholder="选择时间" style="width: 100%;" v-model="addtask.starttime"
                      value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
      <!---->
      <span>结束时间</span>
      <el-date-picker type="datetime" placeholder="选择时间" style="width: 100%;" v-model="addtask.endtime"
                      value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
      <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible1 = false">取 消</el-button>
            <el-button type="primary" @click="addsubtask">确 定</el-button>
          </span>
    </el-dialog>


  </div>

</template>

<script>
  export default {
    name: "ProjectList",

    data() {
      return {
        getAllTasksurl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT//getDistributeTask",
        // addTaskurl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT//addSubTasks",
        fingpeopleurl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/findUserid",
        findpretaskurl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/findPreTask",
        delaytaskurl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/delayProjectTask",
        getAllusersurl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT//getProjectUser",//获取所有成员
        addsubtaskurl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/addSubTasks",//添加子任务
        setpretaskurl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/setPreTask",
        deleteprojecttaskurl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT//deleteProjectTask",


        alltasks: [],
        addtask: {
          name: null,
          description: null,
          starttime: null,
          endtime: null,
          users: [],
          pretask: null,
        },
        userid: null,
        projectid: null,

        dialogVisible1: false,
        delayvisible: false,
        currentrow: null,
        currentbeforetask: null,//当前选中的任务的前置任务

        delaytaskendtime: null,//原推迟子任务

        allusers: null,//项目的所有成员
        addtaskuser: [],//增加的子任务的成员

        radio: '1',

      }
    },
    props: {
      project: {
        type: Object
      }
    },
    created() {
      this.projectid = this.project.id
      console.log(this.projectid)

      this.getTasks()
      this.getallusers()


    },
    methods: {

      //获取所有的子任务数据
      getTasks: function () {
        this.$http.get(this.getAllTasksurl, {
          params: {
            projectid: this.projectid
          }
        })
          .then(response => {
            console.log("a")
            this.alltasks = response.data
            console.log(response.data)
            console.log(response.data[0].users)
            console.log(response.data.length)

            //取前置任务
            for (var i = 0; i < this.alltasks.length; i++) {
              console.log(i)
              // console.log(this.alltasks[i].pretasks[0].name)
              console.log(i)

              if (this.alltasks[i].pretasks == null) {
                this.alltasks[i].pretasks[0].name = "空"
              }
              console.log('数量' + this.alltasks.length)

              // console.log(this.alltasks[i].pretasks.name)
            }
            console.log('数量' + this.alltasks.length)
            //把状态码映射为状态类型
            for (var i = 0; i < this.alltasks.length; i++) {
              console.log('状态' + this.alltasks[i].status)
              // console.log(this.alltasks[i].)
              if (this.alltasks[i].status == 1) {
                this.alltasks[i].status = "未开始";
              } else if (this.alltasks[i].status == 2) {
                this.alltasks[i].status = "进行中";
              } else if (this.alltasks[i].status == 3) {
                this.alltasks[i].status = "已完成";
                console.log("asa")
              } else if (this.alltasks[i].status == 4) {
                this.alltasks[i].status = "已终止";
              } else if (this.alltasks[i].status == 5) {
                this.alltasks[i].status = "已过期";
              } else if (this.alltasks[i].status == 6) {
                this.alltasks[i].status = "推迟中";
              }
            }

            for (var i = 0; i < this.alltasks.length; i++) {
              console.log("用户")
              var allusers = this.alltasks[i].users
              console.log(allusers)
              var userdata = "";
              for (var j = 0; j < allusers.length; j++) {
                if (j == 0) userdata += allusers[j]
                else {
                  userdata += "," + allusers[j]
                }
              }
              this.alltasks[i].usedata = userdata
            }

            console.log(this.alltasks)
          })

      },

      addsubtask: function () {
        if (this.addtask.name == null || this.addtask.description == null || this.addtask.username == null || this.addtask.starttime == null || this.addtask.endtime == null) {
          //为空的提示
        }
        console.log(this.addtask)
        this.dialogVisible1 = false
        this.$http.post(this.addsubtaskurl, {
          projectid: this.project.id,
          name: this.addtask.name,
          usernames: this.addtask.users,
          description: this.addtask.description,
          starttime: this.addtask.starttime,
          endtime: this.addtask.endtime
        }).then(response => {
          if (response.data == -1) {
            this.$alert('添加失败', {
              confirmButtonText: '确定',
            });
          } else {
            if (this.addtask.pretask != null) {
              console.log(this.addtask.pretask + "qian")
            }
            var taskid = response.data
            this.$http.get(this.setpretaskurl, {
              params: {
                projectid: this.projectid,
                taskid: taskid,
                preid: this.addtask.pretask

              }
            })


          }
        })

      },


      getallusers: function () {
        this.$http.get(this.getAllusersurl, {
          params: {
            projectid: this.projectid
          }
        }).then(response => {
          console.log("all" + response.data)
          this.allusers = response.data
        })

      },


      handleClick(row) {

        //推迟任务：需要先找出所有以其为前置任务的任务，列出来
        //逐个推迟
        var taskidnow = row.id
        this.currentrow = row
        this.delayvisible = true
        console.log(taskidnow)
        var posttaskurl = "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT//findPostTask"
        this.$http.get(posttaskurl, {
          params: {
            taskid: taskidnow
          }
        }).then(response => {
          var post = response.data
          this.currentbeforetask = post
          console.log(post)//找到以其为前置任务的任务
          console.log(response.data[0])

        })


        console.log(row);
      },


      //子任务增加成员
      addpeople(row) {
        console.log(row);
        var taskid = row.id
        var projectid = row.projectid


      },
      //删除子任务
      deletesubtask(row) {
        var taskid = row.id
        var projectid = row.projectid
        this.$http.get(this.deleteprojecttaskurl,{params:{
          id:taskid
          }}).then(response=>{
          if (response.data == -1) {
            this.$alert('任务删除失败！', '删除失败', {
              confirmButtonText: '确定',
            });
          } else if (response.data == -2) {
            this.$alert('此任务存在后置任务，请先删除后置任务！！', '删除失败', {
              confirmButtonText: '确定',
            });
          } else {
            this.$alert('删除成功', {
              confirmButtonText: '确定',
            })
            // location.reload()
            this.$router.push('/Empty')

          }})

      },

      //找前置任务
      findpretask(taskid) {
        this.$http.get(this.findpretaskurl, {
          params: {
            taskid: taskid
          }
        }).then(response => {
          console.log("kanakn")
          console.log(response.data)
          return response.data
        })
      },

      delaytask: function () {
        this.$http.get(this.delaytaskurl, {
          params: {
            taskid: this.currentrow.id,
            endtime: this.delaytaskendtime
          }
        }).then(response => {
          console.log(response.data)
        })
        this.delayvisible = false


      }


    }
  }

</script>

<style scoped>
  .content {
    font-family: "Times New Roman", Times, serif;
    font-size: 18px;
  }

</style>
