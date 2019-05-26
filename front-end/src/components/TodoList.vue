<template>
<div>
  <el-table
    :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
    style="width: 100%">
    <el-table-column
      label="备忘项"
      prop="name"> 
    </el-table-column>
    
    <el-table-column
      align="right">
      <template slot="header" slot-scope="scope">
       <!-- <el-row> -->
            <!-- <el-col :span="18"> -->
               <el-button
                    size="mini"
                    @click="createTodoModal = true">新建</el-button>
               <el-input
                    v-model="search"
                    size="mini"
                    placeholder="输入关键字搜索"/>
                    <!-- </el-col> -->
            <!-- <el-col :span="6"> 
              <el-button
                    size="mini"
                    @click="createTodoModal = true">新建</el-button>
                    </el-col>
          </el-row> -->
     </template>

      <template slot-scope="scope">
        <el-button
          size="mini"
          @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
        <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.$index, scope.row)">删除</el-button>
      </template>

    </el-table-column>
  </el-table>


   <Modal v-model="createTodoModal" title="新建备忘录" @on-ok="modalTodoOk" draggable>
      <el-form>
        <el-form-item>
          <el-input v-model="newTodo" placeholder="请输入备忘录" type="textarea"
  :rows="8"></el-input>
        </el-form-item>
      </el-form>
    </Modal>

      <Modal v-model="changeTodoModal" title="修改备忘录" @on-ok="changeTodoOk" draggable>
      <el-form>
        <el-form-item>
          <el-input v-model="editTodo" :placeholder="currentText" type="textarea"
  :rows="8"></el-input>
        </el-form-item>
      </el-form>
    </Modal>
</div>
</template>

<script>
  export default {
    data() {
      return {
        newTodoUrl:"http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/createTodo",
        deleteTodoUrl:"http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/deleteTodo",
        changeTodoUrl:"http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/changeTodo",
        findTodoUrl:"http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/findTodo",
        createTodoModal: false,
        changeTodoModal:false,
        newTodo: "",
        editTodo:"",
        tableData: [],
        search: '',
        currentText:'',
        currentHandleId:null
      }
    },
     created() {
  
        this.$http
        .get(this.findTodoUrl, {
           params: {
            userid:"8"
           }
        },)
        .then(response => {
         
         this.tableData=response.data
         console.log(this.tableData)
         
         });
    },
    methods: {
      handleEdit(index, row) {
        this.changeTodoModal=true;
      
        console.log(index, row);
        this.currentText=row.name;
        this.currentHandleId=this.tableData[index].id
        
       
      },
      handleDelete(index, row) {
        console.log(index, row);
        let currentid=this.tableData[index].id
         this.$http
        .get(this.deleteTodoUrl, {
           params: {
            id: currentid
           }
        },)
        .then(response => {
         
          if(response.data==2)
          {
            this.$message({
            message: "删除失败！",
            type: "fail"
          });
          }
          else
          {
          this.refreshlist();
          this.$message({
            message: "删除成功！",
            type: "success"
          });
         
          }
        });
      },

      modalTodoOk() {
     
      this.$http
        .get(this.newTodoUrl, {
           params: {
            name:this.newTodo,
            userid:"8"
           }
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
            // this.$emit("transferTask", "new");
          this.$message({
            message: "创建成功！",
            type: "success"
          });
          this.refreshlist()
          }
        });
    },

        changeTodoOk(){
       
            this.$http
        .get(this.changeTodoUrl, {
           params: {
            id:this.currentHandleId,
            name:this.editTodo
           }
        },)
        .then(response => {
         
          if(response.data==2)
          {
            this.$message({
            message: "修改失败！",
            type: "fail"
          });
          }
          else
          {
           this.refreshlist()
          this.$message({
            message: "修改成功！",
            type: "success"
          });
          
          }
        });
          
        },

    refreshlist(){
      this.$http
        .get(this.findTodoUrl, {
           params: {
            userid:"8"
           }
        },)
        .then(response => {
         
         this.tableData=response.data
        });
    },

    },
  }
</script>