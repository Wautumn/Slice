<template>
  <div>

    <el-table
      :data="alltasks"
      style="width: 100%" >

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
        prop="username"
        label="负责人"
        width="180">
      </el-table-column>
        <el-table-column
          prop="address"
          label="备注"
          width="180">
        </el-table-column>

    </el-table>

    <el-button  class="el-icon-circle-plus-outline" style="position:relative" @click="dialogVisible1=true">添加任务</el-button>
    <el-dialog
      title="添加子任务"
      :visible.sync="dialogVisible1"
      width="30%"
      :before-close="closedialog">
      <span>任务名</span>
      <el-input v-model="addtask.name"></el-input>
      <span>任务描述</span>
      <el-input v-model="addtask.description"></el-input>
      <span>负责人</span>
      <el-input v-model="addtask.username"></el-input>
      <span>开始时间</span>
      <el-date-picker type="datetime" placeholder="选择时间"  style="width: 100%;" v-model="addtask.starttime"
                      value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
<!---->
      <span>结束时间</span>
      <el-date-picker type="datetime" placeholder="选择时间"  style="width: 100%;" v-model="addtask.endtime"
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
        addTaskurl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT//addSubTasks",


        alltasks:[],
        addtask:{
          name: null,
          description: null,
          starttime: null,
          endtime: null,
          username:null,
        },
        userid: null,
        projectid: null,

        dialogVisible1:false,
      }
    },
    props: {
      project: {
        type: Object
      }
    },
    created() {
      this.projectid=this.project.id
      console.log(this.projectid)

      this.getTasks()




    },
    methods: {
      getTasks: function () {
        this.$http.get(this.getAllTasksurl, {params: {projectid: this.projectid}})
          .then(response => {
            console.log("a")
            this.alltasks = response.data
            for (var i = 0; i < this.alltasks.length; i++) {
              console.log(this.alltasks[i].status)
              if (this.alltasks[i].status==1) {
                this.alltasks[i].status = "未开始";
              }
              else if (this.alltasks[i].status ==2) {
                this.alltasks[i].status = "进行中";
              }
              else if (this.alltasks[i].status == 3) {
                this.alltasks[i].status = "已完成";
                console.log("asa")
              }
              else if (this.alltasks[i].status == 4) {
                this.alltasks[i].status = "已终止";
              }
              else if (this.alltasks[i].status ===5){
                this.alltasks[i].status = "已过期";
                }



            }
            console.log(this.alltasks)
          })

      },

      addsubtask:function () {
        if(this.addtask.name==null||this.addtask.description==null||this.addtask.username==null||this.addtask.starttime==null||this.addtask.endtime==null){
          //为空的提示
        }
        this.dialogVisible1 = false
        this.$http.post(this.addsubtaskurl, {
          projectid: this.project.id,
          name: this.addtask.name,
          username:this.addtask.username,
          description: this.addtask.description,
          starttime: this.addtask.starttime,
          endtime: this.addtask.endtime
        }).then(response => {
          if (response.data != -1) {
            //添加成功
          } else {
            //冲突信息
          }
        })

      }

    }
  }

</script>

<style scoped>

</style>
