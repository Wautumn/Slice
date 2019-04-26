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
      userId: sessionStorage.userId,
      isVerified: false,
      load: true
      // isVerified:true,
    };
  },

  methods: {
    //子组件(login)将用户名传过来
    userSignIn(userID) {
      //设置用户ID
      if (sessionStorage.userId && parseInt(sessionStorage.userId) !== userID) {
        console.log(userID);
        //清空前用户的数据
        localStorage.clear();
        deleteDB("weekDB");
        deleteDB("daySumDB");
        deleteDB("dayTaskDB");
        deleteDB("dayTomoDB");
      }
      // localStorage.clear();
      sessionStorage.userId = userID;
      this.userId = sessionStorage.userId;
      sessionStorage.isVerified = true;
      this.isVerified = true;
      this.loading();
    },
    logOut() {
      console.log("logOut");
      // sessionStorage.isVerified = false;
      this.isVerified = false;
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
    }
  }
};
</script>

