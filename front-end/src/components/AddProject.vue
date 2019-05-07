<template>
  <div>
    <el-form label-position="left" ref="form" :model="form" label-width="100px">
      <el-form-item label="团队任务名称">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="任务描述">
        <el-input v-model="form.description"></el-input>
      </el-form-item>
      <el-form-item label="任务成员">
        <el-col :span="11">
          <el-input v-model="nowpeople" clearable></el-input>
        </el-col>
        <el-col class="line" :span="2">-</el-col>
        <el-col :span="11">
          <el-button @click="addpeople">添加</el-button>
        </el-col>
      </el-form-item>
      <el-form-item label="当前成员">
        <el-col :span="11"></el-col>
        <!--<el-col class="line" :span="4" v-for="people in form.joiner">-->
        <!--<el-button >{{people}}</el-button></el-col>-->
        <el-tag
          v-for="people in form.joiner" :key="people" closable :disable-transitions="false" @close="handleClose(tag)">
          {{people}}
        </el-tag>

      </el-form-item>

      <el-form-item label="子任务">
        <el-col :span="11">
          <el-input v-model="nowsubtask" clearable></el-input>
        </el-col>
        <el-col class="line" :span="2">-</el-col>
        <el-col :span="11">
          <el-button @click="addsubtask">添加</el-button>
        </el-col>
      </el-form-item>

      <el-form-item label="当前子任务">
        <el-col :span="11"></el-col>
        <el-tag
          v-for="people in form.subtask" :key="people" closable :disable-transitions="false" @close="handleClose(tag)">
          {{people}}
        </el-tag>
      </el-form-item>

      <el-form-item label="开始时间">
        <el-col :span="11">
          <el-date-picker type="datetime" placeholder="选择时间" v-model="form.starttime" style="width: 100%;" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
        </el-col>

      </el-form-item>

      <el-form-item label="结束时间">
        <el-col :span="11">
          <el-date-picker type="datetime" placeholder="选择时间" v-model="form.finishtime" style="width: 100%;" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
        </el-col>

      </el-form-item>


      <el-form-item>
        <el-button type="primary" @click="onSubmit">立即创建</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>

<script>
  export default {
    name: "addProject",
    data() {
      return {
        userid: 1,
        nowpeople: '',
        nowsubtask: '',
        newprojecturl: "http://localhost:8080//createProject",
        fingpeopleurl: "http://101.132.194.45:8080/slice-0.0.1-SNAPSHOT/findUserid",
        joinerid: [],
        addpeopleresult:0,
        form: {
          name: '',
          description: '',
          joiner: [],
          subtask: [],
          starttime:'',
          finishtime:''
        }
      }

    },
    methods: {
      onSubmit() {
        console.log('submit!');
        this.$http
          .post(this.newprojecturl, {
            userid:10 ,//this.userid,
            name:this.form.name,
            description: this.form.description,
            members:this.form.joiner,
            // subtask: this.form.subtask,
            starttime:this.form.starttime,
            endtime:this.form.finishtime
          },).then(response => {
          console.log(response.data);
          if (response.data == -1) {
            this.$alert( '添加失败', {
              confirmButtonText: '确定',
            });
          }else {
            this.$alert( '新建成功', {
              confirmButtonText: '确定',
            });
          }
        });
      },
      addpeople: function () {
        this.$http
          .get(this.fingpeopleurl, {params: {username: this.nowpeople}})
          .then(response => {
            console.log(response.data)
            if(response.data==-1){
              this.$alert('此用户不存在！', '添加失败', {
                confirmButtonText: '确定',
              });
            }else {
              this.$alert('已添加'+this.nowpeople, '添加成功', {
                confirmButtonText: '确定',
              });
              this.joinerid.push(response.data)
              this.form.joiner.push(this.nowpeople)
            }
          }),


        console.log(this.nowpeople)
      },
      addsubtask: function () {
        this.form.subtask.push(this.nowsubtask)
        console.log(this.nowsubtask)
      },
      handleClose(tag) {
        this.form.joiner.splice(this.form.joiner.indexOf(tag), 1);
      },

    }
  }
</script>

<style scoped>
  .header {
    color: black;
    font-size: 20px;

  }

  .el-row {
    margin-bottom: 20px;

  }

  .el-col {
    border-radius: 4px;
  }

  .bg-purple-dark {
    background: slategrey;
  }

  .bg-purple {
    background: #d3dce6;
  }

  .bg-purple-light {
    background: #e5e9f2;
  }

  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }

  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
</style>
