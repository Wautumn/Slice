<template>
  <el-container style="height: 500px; border: 1px solid #eee">
    <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
      <el-menu :default-openeds="['1', '3']">
        <el-submenu index="1">
          <template slot="title">
            <i class="el-icon-time"></i>团队任务
          </template>
          <el-menu-item-group>

            <el-menu-item index="1-1" @click="cli($event)">{{typeitem[0]}}</el-menu-item>
            <!--<el-menu-item index="1-2" @click="cli($event)">{{typeitem[1]}}</el-menu-item>-->


          </el-menu-item-group>

        </el-submenu>
        <el-submenu index="2">
          <template slot="title">
            <i class="el-icon-tickets"></i>我创建的团队项目
          </template>
          <el-menu-item-group>
            <el-menu-item index="1-1">项目1</el-menu-item>

          </el-menu-item-group>

        </el-submenu>

        <el-submenu index="3">
          <template slot="title">
            <i class="el-icon-date"></i>我参与的团队项目
          </template>
          <el-menu-item-group>
            <el-menu-item index="1-1">项目1</el-menu-item>

          </el-menu-item-group>

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
          <div class="AddProject">
            <add-project></add-project>
          </div>
        </div>
        <div v-if="type==2">
          <div class="AddProject">
            <add-project></add-project>
          </div>
        </div>
        <div v-if="type==3">
          <div class="AddProject">
            <add-project></add-project>
          </div>
        </div>
        <div v-if="type==4">
          <div class="AddProject">
            <add-project></add-project>
          </div>
        </div>
      </el-main>
    </el-container>
  </el-container>
</template>


<script>
  import AddProject from '../components/AddProject.vue'

  export default {
    name: "Project",
    components: {
      AddProject

    },
    data() {
      const item = {
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      };
      return {
        typeitem: ["新建"],
        type: 0,
        tableData: Array(20).fill(item)
      }
    },
    methods: {
      cli: function () {
        // console.log(event.currentTarget);
        // console.log(event.target);
        var el = event.currentTarget;
        var content = el.innerText;
        console.log(content);
        if (content == "新建") {
          this.type = 1
        } else if (content == "修改") {
          this.type = 2
        } else if (content == "删除") {
          this.type = 3
        } else if (content == "查看") {
          this.type = 4
        }
        console.log(this.type);

      }

    }
  }
</script>


<style>

</style>
