<template>
  <el-main>
  <el-tabs v-model="activeName" @tab-click="handleClick" type="border-card">
    <el-tab-pane label="个人任务" name="first">
      <newmonthview></newmonthview>
      </el-tab-pane>
    <el-tab-pane label="团队任务" name="second">
      <newmonthview></newmonthview>
      </el-tab-pane>
  
  </el-tabs>

  </el-main>
</template>
<script>
 import MonthView1 from "@/components/MonthView1";
 import MonthView2 from "@/components/MonthView2";
 import newMonthView from "@/components/newMonthView";


  export default {
    components: {
      monthview1:MonthView1,
      monthview2:MonthView2,
      newmonthview:newMonthView
    },
    data() {
      return {
        activeName: 'first',
      };
    },
    methods: {
      handleClick(tab, event) {
        console.log(tab._data.index);
        let index=tab._data.index;
        if(index==0)
        {
          this.url="http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/getTasksByUserid"
          console.log(this.url)
        }
        else{
          this.url="http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/getAllTasks"
          console.log(this.url)
        }
      }
    }
  };
</script>
