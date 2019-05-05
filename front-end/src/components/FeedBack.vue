<template>
  <div>
    <div class="freeback-container">
      <div class="freeback-img-box">
        <img src="@/assets/feedback.png">
      </div>
      <div class="freeback-box-border">
        <div class="freeback-box">
          <div class="freeback-title">
            <h1>意见反馈</h1>
            <h2>感谢你的反馈，我们会积极改善，做出更好的服务的</h2>
          </div>
          <div class="freeback-content">
            <div class="freeback-form">
              <Form :model="formItem" :label-width="80">
                <FormItem label="标题">
                  <i-input v-model="formItem.title" placeholder="请输入标题"></i-input>
                </FormItem>
                <FormItem label="反馈信息">
                  <i-input
                    v-model="formItem.content"
                    type="textarea"
                    :autosize="{minRows: 8,maxRows: 10}"
                    placeholder="请输入反馈信息"
                  ></i-input>
                </FormItem>
                <FormItem>
                  <Button type="primary" @click="submit">提交</Button>
                  <Button type="error" class="returnButton" @click="goBack">返回</Button>
                </FormItem>
              </Form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
var submitUrl = "http://localhost:8080/submitFeedback";

export default {
  name: "Freeback",
  data() {
    return {
      formItem: {
        title: "",
        content: "",
        userID: null
      }
    };
  },
  mounted() {
    this.userID = sessionStorage.userId;
    // this.userID = 1;
  },
  methods: {
    goBack() {
      console.log("feedlist");
      this.$router.push({ name: "FeedList" });
    },
    submit() {
      var data = {
        content: this.formItem.content,
        title: this.formItem.title,
        userid: this.userID
      };
      console.log("submit");
      // debugger;
      this.$http
        .post(submitUrl, data, { emulateJSON: true })
        .then(
          res => {
            // 响应成功回调
            this.$Message.info("提交成功");
            console.log("提交成功");
            this.$router.push({ name: "FeedList" });
          },
          res => {
            // 响应错误回调
            console.log("fail");
          }
        )
        .catch(() => {
          console.log("process fail");
        });
    }
  }
};
</script>

<style scoped>
.freeback-container {
  margin: 15px auto;
  width: 80%;
  height: 600px;
  display: flex;
  align-items: center;
  /* background-color: #ccc; */
}
.freeback-img-box {
  width: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.freeback-img-box img {
  width: 80%;
}
.freeback-box-border {
  width: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.freeback-box {
  width: 480px;
}
.freeback-content {
  margin: 15px auto;
  border: 1px #ccc dotted;
}
.freeback-form {
  margin: 30px auto;
  width: 90%;
}
.returnButton {
  display: right;
}
</style>