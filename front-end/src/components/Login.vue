<template>
  <div>
    <div class="login-wrap" @keyup.enter="login">
      <h3>登录</h3>
      <p v-show="showTishi">{{tishi}}</p>
      <el-input  style="width:240px" placeholder="请输入用户名" v-model="username" clearable></el-input>
      <br> <br>  
      <el-input c style="width:240px" placeholder="请输入密码" v-model="password" type="password" clearable></el-input>
      <br>  <br>  
      <el-button type="primary" @click="login" round class="button">登录</el-button>
      <span v-on:click="ToRegister">没有账号？马上注册</span>
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
  margin-bottom: 7px;
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


export default {
  data() {
    return {
      showTishi: false,
      tishi: "",
      username: "",
      password: "",
      userid:"",
      token:"",
      email:"",
      loginurl :"http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/login",
      regiurl :"http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/register",
      findUserIdUrl :"http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/findUserid",
      getTokenUrl :"http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/getToken"
    };
  },
  props: ["isReg"],
  mounted() {
    /*页面挂载获取cookie，如果存在username的cookie，则跳转到主页，不需登录*/
    // var userId = getCookie("userid");
    // if (userId) {
    //   //修改
    //   this.$emit("userSignIn", userId);
    //   this.$router.push("/TaskMode");
    //   console.log("cookie exist");
    // }
  },
  methods: {
    ToRegister() {
      console.log("register");
      this.$router.push({ name: "Reg" });
    },
    logInSuccess(userid) {
      /*向父组件传值*/
      this.$emit("userSignIn", userid);
      // setCookie("userid", userID, 1000 * 60);
      setTimeout(
        function() {
          this.$router.push("/TaskMode");
        }.bind(this),
        1000
      );
    },
    login() {
      if (this.username == "" || this.password == "") {
        alert("请输入用户名或密码");
      }
      //  else if (this.username == "admin") {
      //   this.$router.push("/Admin");
      // } 
      else {
        let data = { password: this.password, username: this.username };
        /*接口请求*/
        this.$http
        .get(this.findUserIdUrl,
        {params: { username: this.username }}).
            then(response => {
              this.userid = response.data;
              console.log("now data is");
              console.log(this.userid);
              console.log(response.data);
              if(response.data==-1)
              {
               this.$alert('用户名不存在', '出错了！', {
                confirmButtonText: '确定',
                callback: action => {
                this.$message({
                    type: 'info',
                    message: "登陆失败！"
            });
          }
          })
              }
              else{
              this.$http.post(this.getTokenUrl, 
           {
            username:this.username,
            password:this.password,
            userid:this.userid},)
           .then(
             response => {
             this.token = response.bodyText;
             console.log(response);
             console.log("now token is");
             console.log(this.token);
             if(response.bodyText=="")
             {console.log("response data is null")
               this.$alert('密码错误', '出错了！', {
                confirmButtonText: '确定',
                callback: action => {
                this.$message({
                    type: 'info',
                    message: "登陆失败！"
            });
              }
          })
             }
             else{
              this.$http.post(this.loginurl, 
           {
            userid:this.userid,
            token:this.token
            },)
           .then(
             response => {
               if(response.data==1)
              {this.tishi = "登录成功";
              this.showTishi = true;

              console.log("登陆成功")
              //console.log(this.email)


              localStorage.username=this.username
              localStorage.userid=this.userid
              localStorage.password=this.password
              //sessionStorage.email=this.email
              // var userID = code;
              // this.logInSuccess(code);
               this.$emit("userSignIn",this.userid);
              this.$router.push("/TaskMode");
              }
              else{
                console.log("登陆失败")
                this.tishi="登陆失败";
                this.showTishi=true;
              }
            }

           );
             }
             }

             
           );}

          
              });
            
           
          // .then(res => {
          //   console.log(res);
          //   res = res.body;
          //   var code = res[0];
          //   sessionStorage.username = res[1];
          //   sessionStorage.email = res[2];
          //   sessionStorage.tomoLength = res[4];
          //   sessionStorage.dayGoal = res[5];
          //   sessionStorage.weekGoal = res[6];
          //   sessionStorage.monthGoal = res[7];
          //   /*接口的传值是(-1,该用户不存在),(0,密码错误)，同时还会检测管理员账号的值*/
          //   if (code == "No user!") {
          //     this.tishi = "该用户不存在";
          //     this.showTishi = true;
          //   } else if (code == "Wrong password!") {
          //     this.tishi = "密码输入错误";
          //     this.showTishi = true;
          //     // this.logInSuccess(2);
          //   } else {
          //     // debugger;
          //     this.tishi = "登录成功";
          //     this.showTishi = true;
          //     // var userID = code;
          //     this.logInSuccess(code);
          //   }
          // })
          // .catch(res => {
          //   console.log("fail");
          // });
      }
    }
  }
};
</script>