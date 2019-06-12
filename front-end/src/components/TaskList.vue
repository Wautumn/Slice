<template>
  <div>
    <div style="margin-top: 10px; margin-bottom: 20px; text-align:center;">
      <el-button size="medium" @click="createModal = true" v-if="currentmode==0">新建任务</el-button>
      <!-- <el-button size="medium" @click="refresh">刷新列表</el-button> -->
    </div>



    <el-table
      ref="singleTable"
      :data="tableData"
      :row-class-name="tableRowClassName"
      highlight-current-row
      @current-change="handleCurrentChange"
      style="width: 100%"
      height="1000"
    >
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="table-expand">
            <el-form-item label="任务描述：">
              <span>{{props.row.description}}</span>
            </el-form-item>
            
            <el-form-item label="任务状态：">
              <!-- <span>{{tableRowStatus(props.row.status)}}</span> -->
           
               <span>
                 <!-- {{props.row.status}} -->
                   <template v-if="props.row.status==1">未开始</template>
        <template v-else-if="props.row.status==2">进行中</template>
        <template v-else-if="props.row.status==3">已完成</template>
        <template v-else-if="props.row.status==4">已过期</template>
        <template v-else-if="props.row.status==5">已终止</template>
                 </span>
            </el-form-item>
            <el-form-item label="开始时间：">
              <span>{{props.row.starttime}}</span>
            </el-form-item>
            <el-form-item label="截止时间：">
              <span>
        <template v-if="currentmode==1"> {{props.row.endtime}}</template>
        <template v-else-if="currentmode==0">{{props.row.finishtime}}</template>
               </span>
            </el-form-item>
             
          </el-form>
        </template>
      </el-table-column>
      <el-table-column property="id" label="项目" v-if="currentmode==1"></el-table-column>
      <el-table-column property="name" label="任务"></el-table-column>
      <el-table-column label=" 状态" width="75">
        <template slot-scope="taskscope">
        <template v-if="taskscope.row.status==1">未开始</template>
        <template v-else-if="taskscope.row.status==2">进行中</template>
        <template v-else-if="taskscope.row.status==3">已完成</template>
        <template v-else-if="taskscope.row.status==4">已过期</template>
        <template v-else-if="taskscope.row.status==5">已终止</template>
         </template>
      </el-table-column>
    </el-table>

    <Modal v-model="createModal" title="新建任务" @on-ok="modalOk" draggable>
      <el-form>
        <el-form-item>任务名称：
          <el-input v-model="newTaskName" placeholder="请输入任务名称"></el-input>
        </el-form-item>
        <el-form-item>任务描述：
          <el-input v-model="newTaskDescription" placeholder="请输入任务描述"></el-input>
        </el-form-item>
        <el-form-item>开始日期与截止日期：
          <el-date-picker
            v-model="newTaskTime"
            type="datetimerange"
            range-separator="至"
            start-placeholde="提示时间"
            end-placeholde="截止时间"
            value-format="yyyy-MM-dd HH:mm:ss"
          ></el-date-picker>
        </el-form-item>
      </el-form>
    </Modal>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // currentmode:0,
      currentRow: null,
      createModal: false,
      newTaskName: "",
      newTaskDescription: "",
      newTaskExpectedTomato: 0,
      newTaskTime: null,
      // newTaskUrl: "http://localhost:8080/task/addTask",
      newTaskUrl: "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/insertTask"
    };
  },
  props: ["tableData","currentmode"],
  methods: {
    setCurrent(row) {
      this.$refs.singleTable.setCurrentRow(row);
      this.$emit("transferTask", null);
    },
    handleCurrentChange(val) {
      if (sessionStorage.listLock == "false") {
        this.currentRow = val;
        this.$emit("transferTask", this.currentRow);
        console.log(this.tableData);
      }
    },
    tableRowClassName({ row }) {
      if (row.status == 1) {
        return "new-task";
      } else if (
        row.status == 2
      ) {
        return "processing-task";
      } else if (row.status == 3) {
        return "finished-task";
      } else if (row.status == 4) {
        return "aborted-task";
      }
      return "";
    },
    tableRowStatus(status) {
      switch (status) {
        case -1:
          return "废弃";
        case 0:
          return "未开始";
        case 1:
          return "进行中";
        case 2:
          return "已完成";
      }
    },
    createTask() {
      this.$prompt("请输入任务内容", "新建任务", {
        confirmButtonText: "确定",
        cancelButtonText: "取消"
      });
    },
    handleEdit(index, row) {
      console.log(index, row);
    },
    handleDelete(index, row) {
      console.log(index, row);
    },
    refresh() {
      this.$emit("transferTask", "new");
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
    modalOk() {
      var currentTime = this.currentTime();
      console.log(currentTime);
      this.$http
        .post(this.newTaskUrl, {
      
            name:this.newTaskName,
            description:this.newTaskDescription,
            // settime:currentTime,
            //starttime:this.newTaskTime[0],
            starttime:this.newTaskTime[0],
            finishtime:this.newTaskTime[1],
            userid:"10"
        },)

        .then(response => {
          if(response.data==-1)
          {
            this.$message({
            message: "创建失败！",
            type: "fail"
          });
          }
          else
          {
            this.$emit("transferTask", "new");
          this.$message({
            message: "创建成功！",
            type: "success"
          });
          }
        });
    //比较起始时间与当前时间
    
    }
  }
};
</script>

<style>
.el-table .processing-task {
  background: #F0FFFF;
}
.el-table .finished-task {
  background: #f0f9eb;
}
.el-table .aborted-task {
  background: #ffd4d4;
}
.table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 100%;
}
</style>