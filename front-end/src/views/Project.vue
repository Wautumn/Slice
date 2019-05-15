<template>
  <el-container style="height: 500px; border: 1px solid #eee">
    <el-aside width="300px" style="background-color: rgb(238, 241, 246)">
      <el-menu :default-openeds="['1', '3']">
        <el-submenu index="1" @click="cli">
          <template slot="title">
            <i class="el-icon-time"></i>
            团队任务
          </template>
          <el-menu-item-group>
            <el-menu-item index="1-1" @click="cli">新建</el-menu-item>

          </el-menu-item-group>

        </el-submenu>


        <el-submenu index="2">
          <template slot="title">
            <i class="el-icon-tickets"></i>我创建的团队项目
          </template>
          <el-menu-item-group v-for="task in createdProject">
            <el-menu-item @click='taskDetail(task)' style="color: black" >{{task.name}}
            </el-menu-item>
          </el-menu-item-group>



        </el-submenu>

        <el-submenu index="3">
          <template slot="title">
            <i class="el-icon-date"></i>我参与的团队项目
          </template>
          <el-menu-item-group v-for="task in joinedProject">
            <el-menu-item @click="taskDetail($event)" >{{task.name}}</el-menu-item>
          </el-menu-item-group>

        </el-submenu>
        <el-submenu index="4">
          <template slot="title"><i class="el-icon-message"></i>导航一</template>
          <el-menu-item-group>
            <template slot="title">分组一</template>
            <el-menu-item @click='sub' >选项1</el-menu-item>
            <el-menu-item >选项2</el-menu-item>
          </el-menu-item-group >

            <el-menu-item >选项3</el-menu-item>

            <template slot="title">选项4</template>
            <el-menu-item index="1-4-1">选项4-1</el-menu-item>

        </el-submenu>
      </el-menu>
    </el-aside>

    <el-container>
      <el-main>
        <div v-if="type==0">
          <div class="AddProject">
            <add-project></add-project>
          </div>
        </div>
        <div v-if="type==1">
          <project-detail :project="this.currentProject"></project-detail>

        </div>
        <div v-if="type==2">

        </div>
        <div v-if="type==3">

        </div>
        <div v-if="type==4">

        </div>
      </el-main>
    </el-container>
  </el-container>
</template>


<script>
  import AddProject from '../components/AddProject.vue'
  import ProjectDetail from "../components/ProjectDetail";

  export default {
    name: "Project",
    components: {
      ProjectDetail,
      AddProject

    },
    data() {
      const item = {
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      };
      return {
        myCreateProjecturl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT//getCreatedProject",
        myjoinedProjecturl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT///getJoinedProject",

        createdProject: [],
        joinedProject: [],
        userid: 10,
        currentProject: 0,
        typeitem: ["新建"],
        type: 0,
        tableData: Array(20).fill(item)
      }
    },
    created() {
      this.getCreateProject();
      this.getJoinedProject();
    },
    methods: {
      getCreateProject: function () {
        this.$http.get(this.myCreateProjecturl, {params: {userid: this.userid}}).then(response => {
          this.createdProject = response.data
          console.log(this.createdProject.length)
        })
      },

      getJoinedProject: function () {
        this.$http.get(this.myjoinedProjecturl, {params: {userid: this.userid}}).then(response => {
          this.joinedProject = response.data
          console.log(this.joinedProject.length)
        })
      },
      //新建任务
      cli: function () {
        this.type = 0
        console.log("ddddd")
        console.log(this.type)
        // console.log(event.currentTarget);
        // console.log(event.target);
        // var el = event.currentTarget;
        // var content = el.innerText;
        // console.log(content);
        // if (content == "新建") {
        //   this.type = 1
        // } else if (content == "修改") {
        //   this.type = 2
        // } else if (content == "删除") {
        //   this.type = 3
        // } else if (content == "查看") {
        //   this.type = 4
        // }
        // console.log(this.type);

      },
      sub:function(){
        console.log("click")
        this.type=1
      },
      //任务详情
      taskDetail: function (message) {
        console.log(message)
        this.type = 1
        this.currentProject=message
        console.log("当前"+this.currentProject.name)



      },


    }
  }
</script>


<style>

</style>
