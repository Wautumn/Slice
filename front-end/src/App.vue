<template>
  <div id="app">
    <el-container>
      <el-header class="header">
        <vheader v-if="load" @logOut="logOut" @reload="reload" :isVerified="this.isVerified"/>
      </el-header>
      <router-view @userSignIn="userSignIn"></router-view>
    </el-container>
  </div>
</template>

<script>
import Header from "@/components/Header";

export default {
  name: "app",
  // provide(){}
  components: {
    vheader: Header
  },
  data() {
    return {
      userId: localStorage.userid,
      isVerified: false,
      load: true
      // isVerified:true,
    };
  },
  methods: {
    //子组件(login)将用户名传过来
    userSignIn(userid) {
      console.log("isVerified Change")
      //设置用户ID
      console.log("清空缓存数据")
      // if (localStorage.userid && parseInt(localStorage.userid) !== userid) {
        console.log(userid);
        //清空前用户的数据
        console.log("delete user message")
        localStorage.clear();
        deleteDB("weekDB");
        deleteDB("daySumDB");
        deleteDB("dayTaskDB");
        deleteDB("dayTomoDB");
      // }
      // localStorage.clear();
      // sessionStorage.userId = userID;
      // this.userId = sessionStorage.userId;
      localStorage.isVerified = true;
      this.isVerified = true;
      this.loading();
      console.log(this.isVerified)

    },
    logOut() {
      console.log("logOut");
      // sessionStorage.isVerified = false;
      console.log("输出看一眼")
      this.isVerified = false;
      this.$router.push("/Welcome");
    },
    loading() {
      this.load = false;
      this.$nextTick(() => {
        this.load = true;
      });
    },
    reload() {
      console.log("header重载反馈");
      this.isVerified = true;
      console.log("输出看一眼")
      console.log(this.isVerified)
    }
  }
};
</script>

