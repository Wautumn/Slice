<template>
  <div>
    <el-menu
      :default-active="activeIndex"
      class="el-menu-demo"
      active-text-color="#FF2D00"
      mode="horizontal"
      @select="handleSelect"
      router
    >
      <a class="HeaderTitle" href="/">
        <span>Growth</span>
      </a>
      <!-- <el-menu-item
        v-for="item in Menus"
        v-text="item.name"
        :index="item.componentName"
        :key="item.id"
        v-if="isVerified"
      ></el-menu-item> -->
      <el-menu-item index="/TaskMode">日视图</el-menu-item>
      <el-menu-item index="/Month">月视图</el-menu-item>
      <el-menu-item index="/Project">团队任务</el-menu-item>
      <el-menu-item index="/WeekAdvice">分析</el-menu-item>
      <el-menu-item index="/Recom">推荐</el-menu-item>
      <el-menu-item index="/FeedList">反馈</el-menu-item>

      <el-submenu index="2" v-if="isVerified">
        <template slot="title">设置</template>
        <el-menu-item index="2-1" :route="{path:this.$route.path}" @click="doSetting">账户设置</el-menu-item>
        <el-menu-item index="2-2" :route="{path:this.$route.path}" @click="taskShow = true">任务设置</el-menu-item>
        <el-menu-item index="2-3" :route="{path:this.$route.path}" @click="logOut">退出</el-menu-item>
      </el-submenu>
      <el-button @click="ToReg" v-if="!isVerified" type="success" size="small" class="reg-button">注册</el-button>
      <el-button @click="ToLog" v-if="!isVerified" type="text" size="small" class="log-button">登录</el-button>
    </el-menu>
    <Modal v-model="accountShow" @on-ok="ok" @on-cancel="cancel" width="400">
      <Tabs value="name1" @on-click="accountTab">
        <TabPane label="账号" name="name1">
          <div>
            <label class="one-line">昵称：</label>
            <div class="one-line" v-if="changeName">
              <Input v-model="nameValue" placeholder="Enter something..." style="width: 200px"/>
              <Button @click="nameChange">保存</Button>
            </div>
            <div class="one-line" v-else>
              <span>{{username}}</span>
              <Button type="text" @click="changeName = true">更换</Button>
            </div>
          </div>
          <br>
          <div>
            <label class="one-line">邮箱：</label>
            <div class="one-line" v-if="changeMail">
              <Input v-model="mailValue" placeholder="Enter something..." style="width: 200px"/>
              <Button @click="mailChange">保存</Button>
            </div>
            <div class="one-line" v-else>
              <span>{{email}}</span>
              <Button type="text" @click="changeMail = true">更换</Button>
            </div>
          </div>
        </TabPane>
        <TabPane label="账号安全" name="name2">
          <div>原密码:
            <Input v-model="originalPW" type="password" placeholder="请输入原密码" class="input-item"/>
          </div>
          <br>
          <div>新密码:
            <Input v-model="newPW" type="password" placeholder="请输入新密码" class="input-item"/>
          </div>
        </TabPane>
        <TabPane label="修改头像" name="name3">
          <el-upload
            class="avatar-uploader"
            action="https://jsonplaceholder.typicode.com/posts/"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </TabPane>
      </Tabs>
      <div slot="footer" v-if="changePass">
        <!-- <div>确定</div> -->
        <Button type="error" @click="passChange">确认</Button>
      </div>
    </Modal>
    <!-- <Modal v-model="taskShow" @on-ok="timeChange" @on-cancel="cancel">
      <Tabs value="name1">
        <TabPane label="目标设置" name="name1">
          <div class="one-line">
            <span>每日目标</span>
            <InputNumber :min="1" v-model="dayGoal" class="input-number"></InputNumber>
            <span>个番茄</span>
          </div>
          <br>
          <div class="one-line">
            <span>每周目标</span>
            <InputNumber :min="1" v-model="weekGoal" class="input-number"></InputNumber>
            <span>个番茄</span>
          </div>
          <br>
          <div class="one-line">
            <span>每月目标</span>
            <InputNumber :min="1" v-model="monthGoal" class="input-number"></InputNumber>
            <span>个番茄</span>
          </div>
        </TabPane>
        <TabPane label="番茄设置" name="name2">
          <div class="one-line">
            <span>番茄时间</span>
            <InputNumber :min="10" v-model="pomoTime" class="input-number"></InputNumber>
            <span>分钟</span>
          </div>
          <br>
          <div class="one-line">
            <span>休息时间</span>
            <InputNumber :min="1" v-model="restTime" class="input-number"></InputNumber>
            <span>分钟</span>
          </div>
        </TabPane>
      </Tabs>
    </Modal> -->
  </div>
</template>


<script>
import Menus from "@/config/header-config";
import { delCookie } from "../assets/cookie.js";

var mailurl = "http://localhost:8080/changeEmail ";
var nameurl = "http://localhost:8080/changeUsername ";
var pomourl = "http://localhost:8080/changeTomatoLength ";
var passurl = "http://localhost:8080/changePassword ";
var changeDayGoal = "http://localhost:8080/changeDayGoal ";
var changeWeekGoal = "http://localhost:8080/changeWeekGoal ";
var changeMonthGoal = "http://localhost:8080/changeMonthGoal ";

export default {
  data() {
    return {
      imageUrl: "",
      username: "",
      email: "",
      activeIndex: "3",
      activeIndex2: "2",
      accountShow: false,
      changeName: false,
      changePass: false,
      taskShow: false,
      dayGoal: 1,
      weekGoal: 7,
      monthGoal: 30,
      pomoTime: 25,
      restTime: 5,
      nameValue: "",
      changeMail: false,
      mailValue: "",
      originalPW: "",
      newPW: "",
      Menus: Menus,
      userId: null
    };
  },
  mounted() {
    debugger;
    console.log("header重载");
    if (sessionStorage.userId) {
      this.$emit("reload");
    }

    this.userId = sessionStorage.userId;
    // if (this.userId == undefined) {
    //   debugger;
    //   this.$emit("reload");
    // }
    this.username = sessionStorage.username;
    this.email = sessionStorage.email;
    this.dayGoal = parseInt(sessionStorage.dayGoal);
    this.weekGoal = parseInt(sessionStorage.weekGoal);
    this.monthGoal = parseInt(sessionStorage.monthGoal);
    if (sessionStorage.tomoLength) {
      this.pomoTime = parseInt(sessionStorage.tomoLength);
    }
  },
  props: ["isVerified"],
  methods: {
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      debugger;
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },
    passChange() {
      console.log("修改密码");
      if (this.newPW && this.originalPW) {
        let data = {
          oldPassword: this.originalPW,
          newPassword: this.newPW,
          userId: this.userId
        };
        /*接口请求*/
        this.$http
          .post(passurl, data, { emulateJSON: true })
          .then(res => {
            if (res.body == true) {
              this.$Message.info("密码修改成功");
            }
          })
          .catch(res => {
            console.log("fail");
            this.$Message.info("密码修改失败");
          });
      } else {
        this.$Message.info("请输入密码");
      }
    },
    accountTab(val) {
      console.log(val);
      if (val == "name2") {
        this.changePass = true;
      } else {
        this.changePass = false;
      }
    },
    logOut() {
      this.$emit("logOut");
      delCookie("userid");
      sessionStorage.userId = "";
      this.$router.push({ name: "FrontPage" });
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    timeChange() {
      console.log("changeTime&Goal");
      this.$http
        .get(pomourl, {
          params: {
            userId: this.userId,
            tomatoLength: this.pomoTime
          }
        })
        .then(res => {
          // 响应成功回调
          //console.log(res.body);
          // this.$Message.info("番茄时间修改成功");
          sessionStorage.tomoLength = this.pomoTime;
        })
        .catch(() => {
          console.log("番茄时间修改失败");
          // this.$Message.info("番茄时间修改失败");
        });
      this.$http
        .get(changeDayGoal, {
          params: {
            userId: this.userId,
            dayGoal: this.dayGoal
          }
        })
        .then(res => {
          // 响应成功回调
          //console.log(res.body);
          // this.$Message.info("每日目标修改成功");
          sessionStorage.dayGoal = this.dayGoal;
        })
        .catch(() => {
          console.log("每日目标修改失败");
          // this.$Message.info("番茄时间修改失败");
        });
      this.$http
        .get(changeWeekGoal, {
          params: {
            userId: this.userId,
            weekGoal: this.weekGoal
          }
        })
        .then(res => {
          // 响应成功回调
          //console.log(res.body);
          // this.$Message.info("每日目标修改成功");
          sessionStorage.weekGoal = this.weekGoal;
        })
        .catch(() => {
          console.log("每周目标修改失败");
          // this.$Message.info("番茄时间修改失败");
        });
      this.$http
        .get(changeMonthGoal, {
          params: {
            userId: this.userId,
            monthGoal: this.monthGoal
          }
        })
        .then(res => {
          // 响应成功回调
          //console.log(res.body);
          // this.$Message.info("每日目标修改成功");
          sessionStorage.monthGoal = this.monthGoal;
        })
        .catch(() => {
          console.log("每月目标修改失败");
          // this.$Message.info("番茄时间修改失败");
        });
    },
    mailChange() {
      console.log("changeMail");
      if (this.mailValue) {
        this.$http
          .get(mailurl, {
            params: {
              userId: this.userId,
              email: this.mailValue
            }
          })
          .then(res => {
            // 响应成功回调
            //console.log(res.body);
            this.$Message.info("邮箱修改成功");
          })
          .catch(() => {
            console.log("邮箱修改失败");
            this.$Message.info("邮箱修改失败");
          });
      } else {
        this.$Message.info("请输入有效的邮箱地址");
      }
    },
    nameChange() {
      console.log("changeName");
      if (this.nameValue) {
        this.$http
          .get(nameurl, {
            params: {
              userId: this.userId,
              username: this.nameValue
            }
          })
          .then(res => {
            // 响应成功回调
            //console.log(res.body);
            this.$Message.info("昵称修改成功");
          })
          .catch(() => {
            console.log("昵称修改失败");
            this.$Message.info("昵称修改失败");
          });
      } else {
        this.$Message.info("请输入有效的昵称");
      }
    },
    ok() {
      this.$Message.info("确认提交");
    },
    cancel() {},
    ToReg() {
      console.log("register");
      this.$router.push({ name: "Reg" });
    },
    ToLog() {
      console.log("login");
      this.$router.push({ name: "Login" });
    },
    doSetting() {
      console.log("打开");
      this.accountShow = true;
      this.changeName = this.changeMail = false;
    }
  }
};
</script>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 90px;
  height: 90px;
  line-height: 90px;
  text-align: center;
}
.avatar {
  width: 90px;
  height: 90px;
  display: block;
}

.HeaderTitle {
  color: #149290;
  float: left;
  padding: 15px 15px;
  font-size: 24px;
  line-height: 20px;
  outline: none; /*取消选中后的蓝框*/
  text-decoration: none; /*取消a标签下划线*/
}
.el-menu--horizontal > .el-submenu {
  float: right;
}
.input-item {
  width: 200px;
}
.one-line {
  display: inline-block;
}

.input-number {
  margin-left: 20px;
  margin-bottom: 5px;
  width: 50px;
}

.reg-button {
  float: right;
  margin-top: 8px;
  margin-right: 10px;
  /* height: 20px; */
}

.log-button {
  float: right;
  margin-top: 8px;
  margin-right: 20px;
  /* height: 20px; */
}
</style>