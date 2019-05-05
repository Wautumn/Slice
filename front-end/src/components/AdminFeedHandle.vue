<template>
  <div style="padding-left: 15%;  padding-right: 15%; margin-top: 20px">
    <el-table
      ref="singleTable"
      :data="tableData"
      tooltip-effect="dark"
      style="width: 100%"
      highlight-current-row
      @current-change="handleCurrentChange"
    >
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="内容：">
              <span>{{ props.row.content }}</span>
            </el-form-item>
            <el-form-item label="回复：">
              <span>{{ props.row.answer }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <!-- <el-table-column type="selection" width="55"></el-table-column> -->
      <el-table-column label="时间" width="180">
        <template slot-scope="scope">{{ scope.row.date }}</template>
      </el-table-column>
      <el-table-column prop="name" label="用户名" width="120" sortable></el-table-column>
      <el-table-column prop="status" label="状态" width="120" sortable></el-table-column>
      <el-table-column prop="title" label="标题" show-overflow-tooltip></el-table-column>
    </el-table>
    <div style="margin-top: 20px">
      <el-button @click="setCurrent()" style="float: right ">取消选择</el-button>
    </div>
    <div v-if="hasSelect" height="60px" style="margin-top: 30px; ">
      <h1>回复</h1>
      <el-input type="textarea" v-model="reply" style="margin-top: 10px; margin-bottom:10px;"></el-input>
      <el-button type="primary" @click="submitReply" style="float: right;">提交</el-button>
    </div>
    <Page
      :total="size"
      :page-size="pageSize"
      @on-change="pageChange"
      show-elevator
      class="page-footer"
    />
  </div>
</template>

<script>
var getUrl = "http://localhost:8080/getAllFeedback";
var submitUrl = "http://localhost:8080/handleFeedback";
var testData = [
  {
    date: "2016-05-03",
    name: "王大虎",
    status: "已处理",
    title: "上海市普陀区金沙江路 1518 弄",
    content: "上海市普陀区金沙江路 1518 弄"
  },
  {
    date: "2016-05-02",
    name: "王小虎",
    status: "已处理",
    title: "上海市普陀区金沙江路 1518 弄",
    content: "上海市普陀区金沙江路 1518 弄"
  }
];

export default {
  data() {
    return {
      pageSize: 5,
      reply: "",
      currentRow: null,
      tableData: [],
      allData: []
    };
  },
  computed: {
    hasSelect() {
      return this.currentRow === null ? false : true;
    },
    size() {
      return this.allData.length;
    }
  },
  mounted() {
    var allData = null;
    this.$http
      .get(getUrl)
      .then(
        res => {
          // 响应成功回调
          allData = res.body;

          for (var item of allData) {
            if (item.status == 0) {
              item.status = "未处理";
            } else if (item.status == 1) {
              item.status = "已处理";
            } else {
              item.status = "未知";
            }
          }
          this.allData = allData;
          console.log(this.allData);
          this.pageChange(1);
        },
        res => {
          // 响应错误回调
          console.log("fail");
        }
      )
      .catch(() => {
        console.log("process fail");
      });
  },

  methods: {
    pageChange(index) {
      console.log("pageChange");
      //页数变化回调
      var start = (index - 1) * this.pageSize;
      var end = index * this.pageSize;
      this.tableData = this.allData.slice(start, end);
      //this.pageCurrent=index;
    },
    submitReply() {
      console.log(this.currentRow.id);
      var data = { id: this.currentRow.id, answer: this.reply };
      this.$http
        .post(submitUrl, data, { emulateJSON: true })
        .then(
          res => {
            // 响应成功回调
            this.$Message.info("提交成功");
            this.currentRow.status = "已处理";
            this.currentRow.answer = this.reply;
            console.log(res);
          },
          res => {
            // 响应错误回调
            console.log("fail");
          }
        )
        .catch(() => {
          console.log("process fail");
        });
    },
    setCurrent(row) {
      this.$refs.singleTable.setCurrentRow(row);
      this.currentRow = null;
    },
    handleCurrentChange(val) {
      this.currentRow = val;
    }
  }
};
</script>

<style scoped>
.page-footer {
  margin-top: 30px;
}

.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>
