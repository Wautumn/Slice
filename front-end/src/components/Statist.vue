<template>
  <div>


    <el-row :gutter="20">
      <el-col :span="12">
        <div class="grid-content bg-purple">
          <ve-pie :data="dayData"></ve-pie>
        </div>
      </el-col>

      <el-col :span="12">
        <div class="grid-content bg-purple">
          <ve-pie :data="stateData"></ve-pie>
        </div>
      </el-col>
    </el-row>
    <!--<el-row :gutter="20">-->
      <!--<el-col :span="12">-->
        <!--<div class="grid-content bg-purple">-->
          <!--<ve-pie :data="chartData"></ve-pie>-->
        <!--</div>-->
      <!--</el-col>-->
      <!--<el-col :span="12">-->
        <!--<div class="grid-content bg-purple">-->
          <!--<ve-pie :data="chartData"></ve-pie>-->
        <!--</div>-->
      <!--</el-col>-->
    <!--</el-row>-->
  </div>
</template>

<script>
  export default {
    name: "Statist",
    data() {
      return {
        getAllTasksurl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT//getDistributeTask",

        projectid: this.project.id,
        alltasks: null,


        starttime: null,
        endtime: null,
        sumday: 0,
        real: 0,


        state1: 0,
        state2: 0,
        state3: 0,
        state4: 0,
        state5: 0,

        dayData: {
          columns: ['日期', '天数'],
          rows: []
        },

        stateData: {
          columns: ['日期', '天数'],
          rows: []
        },


        chartData: {
          columns: ['日期', '访问用户'],
          rows: [
            {'日期': 'eww', '访问用户': 1393},
            {'日期': '1/2', '访问用户': 3530},
            {'日期': '1/3', '访问用户': 2923},
            {'日期': '1/4', '访问用户': 1723},
            {'日期': '1/5', '访问用户': 3792},
            {'日期': '1/6', '访问用户': 4593}
          ]
        }
      }
    },
    props: {
      project: {
        type: Object
      }
    },
    created() {

      this.starttime = new Date(Date.parse(this.project.starttime.replace(/-/g, "/")));
      this.endtime = new Date(Date.parse(this.project.endtime.replace(/-/g, "/")));
      console.log(this.starttime)
      console.log(this.endtime)
      var difDay = (this.endtime - this.starttime) / (1000 * 60 * 60 * 24)
      this.sumday = difDay
      console.log(difDay)//总天数
      this.handlerealday()
      this.handletasks()
    },
    methods: {

      handlerealday: function () {
        var date = new Date().toLocaleDateString()
        var now = new Date(Date.parse(date.replace(/-/g, "/")));
        console.log(now)
        console.log("now")

        if (now.getTime() < this.starttime.getTime()) {
          console.log("start1")
          this.real = 0;
        } else if (now.getTime() > this.starttime.getTime() && (now.getTime() < this.endtime.getTime())) {
          console.log("start")
          this.real = (now - this.starttime) / (1000 * 60 * 60 * 24)

        } else if (now.getTime() > this.endtime.getTime()) {
          console.log("start3")
          this.real = this.sumday
        }
        console.log(this.real)
        console.log(this.sumday)
        this.dayData = {
          columns: ['日期', '天数'],
          rows: [
            {'日期': '已完成天数', '天数': this.real},
            {'日期': '剩余天数', '天数': this.sumday - this.real},

          ]
        }
      },

      handletasks: function () {
        this.$http.get(this.getAllTasksurl, {params: {projectid: this.projectid}})
          .then(response => {
            console.log("alltasks")
            this.alltasks = response.data
            console.log(this.alltasks)
            for (var i = 0; i < this.alltasks.length; i++) {
              if (this.alltasks[i].status == 1)
                this.state1++
              else if (this.alltasks[i].status == 2)
                this.state2++
              else if (this.alltasks[i].status == 3)
                this.state3++
              else if (this.alltasks[i].status == 4)
                this.state4++
              else if (this.alltasks[i].status == 5)
                this.state5++

            }
            this.stateData={
              columns: ['状态', '任务数'],
              rows: [
                {'状态': '未开始', '任务数': this.state1},
                {'状态': '进行中', '任务数': this.state2},
                {'状态': '已完成', '任务数': this.state3},
                {'状态': '已终止', '任务数': this.state4},
                {'状态': '已过期', '任务数': this.state5},

              ]
            }
          })

      },


    },


  }
</script>

<style scoped>

</style>
