<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="6">
        <div class="grid-content bg-purple" style="font-size: 20px;border-color: azure">任务名:</div>
      </el-col>
      <el-col :span="18">
        <div class="grid-content bg-purple-light" style="font-size: 20px">{{project.name}}</div>
      </el-col>
    </el-row>

    <br>
    <el-row :gutter="20">
      <el-col :span="6">
        <div class="grid-content bg-purple" style="font-size: 20px;border-color: azure">任务描述:</div>
      </el-col>
      <el-col :span="18">
        <div class="grid-content bg-purple-light" style="font-size: 20px">
          {{project.description}}
        </div>
      </el-col>
      <!--<el-col :span="6">-->
      <!--<el-button type="info" plain @click="changetask">修改任务描述</el-button>-->
      <!--</el-col>-->
    </el-row>

    <br>
    <el-row :gutter="20">
      <el-col :span="6">
        <div class="grid-content bg-purple" style="font-size: 20px;border-color: azure">开始时间:</div>
      </el-col>
      <el-col :span="18">
        <div class="grid-content bg-purple-light" style="font-size: 20px">{{project.starttime}}</div>
      </el-col>
    </el-row>

    <br>
    <el-row :gutter="20">
      <el-col :span="6">
        <div class="grid-content bg-purple" style="font-size: 20px;border-color: azure">结束时间:</div>
      </el-col>
      <el-col :span="218">
        <div class="grid-content bg-purple-light" style="font-size: 20px">{{project.endtime}}</div>
      </el-col>
    </el-row>

    <br>
    <el-row :gutter="20">
      <el-col :span="6">
        <div class="grid-content bg-purple" style="font-size: 20px;border-color: azure">当前成员:</div>
      </el-col>
      <el-col :span="14">
        <!--<div class="grid-content bg-purple-light" style="font-size: 20px" v-for="attend in attender">{{attend}}</div>-->
        <el-row>
          <!--<el-col v-for="attend in attender" ><div class="grid-content bg-purple" style="font-size: 20px;border-color: azure" >{{attend}}</div></el-col>-->
          <el-tag v-for="item in attender" @close="handleClose(tag)" style="margin-right: 10px">{{item}}
          </el-tag>
        </el-row>
      </el-col>
      <el-col :span="4">
        <el-button  @click="dialogVisible = true" icon="el-icon-circle-plus-outline">添加成员</el-button>
        <el-dialog
          title="添加成员"
          :visible.sync="dialogVisible"
          width="30%"
          :before-close="handleClose">
          <span>输入用户名</span>
          <el-input v-model="adduser"></el-input>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="addattend">确 定</el-button>
          </span>
        </el-dialog>

      </el-col>
    </el-row>
    <br>

    <el-row :gutter="20">
      <el-col :span="6">
        <div class="grid-content bg-purple" style="font-size: 20px;border-color: azure">当前子任务:</div>
      </el-col>
      <el-col :span="14">
        <!--<div class="grid-content bg-purple-light" style="font-size: 20px" v-for="attend in attender">{{attend}}</div>-->
        <el-row>
          <!--<el-col v-for="attend in attender" ><div class="grid-content bg-purple" style="font-size: 20px;border-color: azure" >{{attend}}</div></el-col>-->
          <el-tag v-for="subtask in taskandattend" @close="handleClose(tag)" style="margin-right: 10px">
            {{subtask.name }}
          </el-tag>
        </el-row>
      </el-col>
      <el-col :span="4">
        <el-button  @click="dialogVisible1 = true" icon="el-icon-circle-plus-outline">添加子任务</el-button>
        <el-dialog
          title="添加子任务"
          :visible.sync="dialogVisible1"
          width="30%"
          :before-close="handleClose">
          <span>输入任务名</span>
          <el-input v-model="addnewsubtask.name"></el-input>
          <span>输入任务描述</span>
          <el-input v-model="addnewsubtask.description"></el-input>
          <span>开始时间</span>
          <el-date-picker type="datetime" placeholder="选择时间" v-model="addnewsubtask.starttime" style="width: 100%;"
                          value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>

          <span>结束时间</span>
          <el-date-picker type="datetime" placeholder="选择时间" v-model="addnewsubtask.endtime" style="width: 100%;"
                          value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible1 = false">取 消</el-button>
            <el-button type="primary" @click="addsubtask">确 定</el-button>
          </span>
        </el-dialog>

      </el-col>
    </el-row>
    <br>

    <el-row :gutter="20">
      <el-col :span="6">
        <div class="grid-content bg-purple" style="font-size: 20px;border-color: azure">当前任务分配:</div>
      </el-col>
      <el-col :span="18">
        <!--<div class="grid-content bg-purple-light" style="font-size: 20px" v-for="attend in attender">{{attend}}</div>-->
        <el-row v-for="taskuser in taskandattend" style="font-size: 15px;padding-bottom: 10px">
          <!--<el-col v-for="attend in attender" ><div class="grid-content bg-purple" style="font-size: 20px;border-color: azure" >{{attend}}</div></el-col>-->
          <el-col :span="9" closable @close="handleClose(tag)" style="margin-right: 10px">
            任务名：{{taskuser.name }}
          </el-col>
          <el-col :span="9" closable @close="handleClose(tag)" style="margin-right: 10px">
            用户名：{{taskuser.username}}
          </el-col>


        </el-row>
      </el-col>
    </el-row>
    <br>

    <el-row :gutter="20">
      <el-col :span="6">
        <div class="grid-content bg-purple" style="font-size: 20px;border-color: azure">分配任务:</div>
      </el-col>
      <el-col :span="18">
        <el-select v-model="selecttask" placeholder="请选择子任务">
          <el-option
            v-for="item in taskandattend"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>

        <el-select v-model="selectuser" placeholder="请选择用户">
          <el-option
            v-for="item in attender"
            :key="item"
            :label="item"
            :value="item">
          </el-option>
        </el-select>

        <el-button plain @click="distributetask">确认分配</el-button>
      </el-col>
    </el-row>
    <br>


    <el-button type="danger" plain @click="deletetask">删除当前任务</el-button>
    <!--<el-button type="primary" plain @click="changetask">编辑当前任务</el-button>-->
    <!--<el-button type="primary" plain @click="changetask">编辑当前任务</el-button>-->


  </div>
</template>

<script>
  export default {
    name: "ProjectDetail",
    data() {
      return {
        getProjectByIdurl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT//findProjectById",
        getProjectUserurl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT///getProjectUser",
        getAllSubTaskurl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT//findAllSubTasks",
        getAllSubTaskAndUserurl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT//getDistributeTask",
        distributetaskurl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT//distributeTask",
        fingpeopleurl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/findUserid",
        addsubtaskurl:"http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT//addSubTasks",

        ifchange: 0,
        taskDetail: null,
        attender: null,
        subtasks: null,
        taskandattend: null,
        selecttask: null,
        selectuser: null,
        adduser: null,
        addnewsubtask:{
          name:null,
          description:null,
          starttime:null,
          endtime:null,
        },

        dialogVisible: false,
        dialogVisible1: false
      }
    },
    props: {
      project: {
        type: Object
      }
    },

    created() {
      console.log("获得" + this.project)
      this.getProjectDetail()
      this.getProjectAttend()
      this.getSubTask()
      this.getProjectTaskAndUser()
    },
    computed: {},
    methods: {
      getProjectDetail: function () {
        this.$http.get(this.getProjectByIdurl, {params: {id: this.project.id}}).then(response => {
          this.taskDetail = response.data
          console.log(this.taskDetail.name)
        })
      },

      getProjectAttend: function () {
        this.$http.get(this.getProjectUserurl, {params: {projectid: this.project.id}}).then(response => {
          this.attender = response.data
          console.log(this.attender)
        })
      },

      getSubTask: function () {
        this.$http.get(this.getAllSubTaskurl, {params: {projectid: this.project.id}}).then(response => {
          this.subtasks = response.data
          console.log(this.subtasks)
        })
      },

      getProjectTaskAndUser: function () {
        this.$http.get(this.getAllSubTaskAndUserurl, {params: {projectid: this.project.id}}).then(response => {
          this.taskandattend = response.data
          console.log(this.taskDetail.name)
        })
      },
      handleCloseUser: function () {
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        })

      },
      handleClose(tag) {
        this.attender.splice(this.attender.indexOf(tag), 1);
      },

      distributetask: function () {
        var task = this.selecttask
        var user = this.selectuser
        console.log(task)
        console.log(user)
        this.$http.get(this.distributetaskurl, {
          params: {
            projectid: this.project.id,
            taskid: task,
            username: user
          }
        }).then(response => {
          if (response.data == 1) {
            console.log("success")
            this.$alert('分配成功', '提示', {
              confirmButtonText: '确定',
              callback: action => {
                this.$message({
                  type: 'info',
                  message: `action: ${ action }`
                });
              }
            });
          } else {
            console.log("failure")
            this.$alert('分配失败', '提示', {
              confirmButtonText: '确定',
              callback: action => {
                this.$message({
                  type: 'info',
                  message: `action: ${ action }`
                });
              }
            });
          }
        })


      },
      deletetask: function () {
        //删除的逻辑
      },
      changetask: function () {


      },
      addattend: function () {
        this.dialogVisible = false
        console.log("ada")
        this.$http
          .get(this.fingpeopleurl, {params: {username: this.adduser}})
          .then(response => {
            console.log(response.data)
            if (response.data == -1) {
              this.$alert('此用户不存在！', '添加失败', {
                confirmButtonText: '确定',
              });
            } else {
              this.$alert('已添加' + this.adduser, '添加成功', {
                confirmButtonText: '确定',
              });
              this.attender.push(this.adduser)

            }
          }),


          console.log(this.nowpeople)

      },
      addsubtask:function () {
        this.dialogVisible = false
        this.$http.post(this.addsubtaskurl,{
          projectid:this.project.id,
          name:this.addnewsubtask.name,
          description:this.addnewsubtask.description,
          starttime:this.addnewsubtask.starttime,
          endtime:this.addnewsubtask.endtime
        }).then(response=>{
          if(response.data!=-1){
            //添加成功
          }else {
            //冲突信息
          }
        })

      }


    }
  }
</script>

<style scoped>

</style>
