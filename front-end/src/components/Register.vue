<template>
  <div>
    <div class="register-wrap" @keyup.enter="register">
      <h3>注册</h3>
      <p v-show="showTishi">{{tishi}}</p>
      <el-input style="width:240px" placeholder="请输入用户名" v-model="newUsername" clearable></el-input>
      <br>  <br>

      <el-input style="width:240px" placeholder="请输入邮箱" v-model="mailAddress" clearable></el-input>
      <br>  <br>
      <el-input style="width:240px" placeholder="请输入密码" v-model="newPassword" type="password" clearable></el-input>

      <br> <br>
      <el-button type="primary" @click="register" round class="button">注册</el-button>


      <span v-on:click="ToLogin">已有账号？马上登录</span>
    </div>
  </div>
</template>

<style scoped>
.login-wrap {
  text-align: center;
  margin-top: 50px;
}
.register-wrap {
  text-align: center;
  margin-top: 50px;
}

.input {
  width: 18%;
  margin: 0 auto;
  margin-bottom: 14px;
  padding: 8px;
}
p {
  color: red;
}

.button {
  display: block;
  width: 200px;
  height: 40px;
  margin: 0 auto;
  margin-bottom: 5px;
}

span {
  cursor: pointer;
}
span:hover {
  color: #41b883;
}

h3 {
  margin-bottom: 10px;
}
</style>

<script>
import { setCookie, getCookie } from "../assets/cookie.js";


var loginurl = "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/login";
var regiurl = "http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/register";

export default {
  data() {
    return {
      showLogin: true,
      showRegister: false,
      showTishi: false,
      tishi: "",
      newUsername: "",
      newPassword: "",
      mailAddress: ""
    };
  },
  props: ["isReg"],
  mounted() {
    /*页面挂载获取cookie，如果存在username的cookie，则跳转到主页，不需登录*/
    if (getCookie("username")) {
      // this.$router.push("/PomoMode");
      console.log("cookie exist");
    }
  },
  methods: {
    ToLogin() {
      console.log("login");
      this.$router.push({ name: "Login" });
    },
    register() {
      if (this.newUsername == "" || this.newPassword == "") {
        alert("请输入用户名或密码");
      } else {
        let data = {
          username: this.newUsername,
          password: this.newPassword,
          email: this.mailAddress
        };
        // this.$http.post(regiurl, data, { emulateJSON: true }).then(res => {
          this.$http.post(regiurl,
           {
             username: this.newUsername,
             password: this.newPassword,
             email: this.mailAddress
           },).then(res => {
          console.log(res);
          if (res.data == 1) {
            this.tishi = "注册成功";
            this.showTishi = true;
            this.username = "";
            this.password = "";
            /*注册成功之后再跳回登录页*/
            setTimeout(
              function() {
                this.showRegister = false;
                this.ToLogin();
                this.showTishi = false;
              }.bind(this),
              1000
            );
          } else {
            this.tishi = "注册失败,用户名已存在";
            this.showTishi = true;
          }
        });
      }
    }
  }
};
</script>