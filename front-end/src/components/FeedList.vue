<template>
 <el-main>
  <div class="container">
    <el-card class="box-card">
    <div class="list" v-for="(item,index) in tableData" :key="index">
      <div class="ticket" v-if="item.isAdmin">
        <div class="avatar">
          <img
            class="image"
            src="https://i1.hoopchina.com.cn/user/930/259537937358930/259537937358930-1527780723.jpeg@194h_194w_2e"
          >
        </div>
        <div class="title">
          <span class="status-open mini-status">管理员</span>
          {{item.title}}
        </div>
        <div class="content">{{item.content}}</div>
        <!-- <div class="author">
           by
          {{item.author}}
          <br>
          <span class="time">{{item.time}}</span>
        </div>-->
      </div>
      <div class="ticket" v-else>
        <div class="avatar">
          <img src="@/assets/none.png">
        </div>
        <div class="title">{{item.title}}</div>
        <div class="content">{{item.content}}</div>
        <div class="answer">回复:{{item.answer}}</div>
        <div class="author">
          <!-- by
          {{item.author}}-->
          <br>
          <span class="time">{{item.settime}}</span>
        </div>
      </div>
    </div>
    <el-button class="newButton" type="primary" icon="el-icon-edit" @click="newFeed" plain>新建反馈</el-button>
    <Page :total="size" :page-size="pageSize" @on-change="pageChange" show-elevator class="footer"/>
    </el-card>
  </div>
   </el-main>
</template>

<script>





export default {
  name: "Order",
  beforeRouteEnter(to, from, next) {
    window.scrollTo(0, 0);
    next();
  },

  data() {
    return {
      avatar:
        "http://cdn.v2ex.com/gravatar/d86a1f8a3c75e155a0417a9af2a41ade?s=48&amp;d=mm",
      tableData: null,
      // allData: testData,
      pageSize: 10,
      remarks: "",
      userID: null,
      getUrl:"http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/feedbackList"
    };
  },
  created(){
        this.$http
              .get(this.getUrl, {
                params: {userid:"8"}
              })
              .then(response => {
                console.log("?")
                console.log(response.data)
                this.tableData=response.data
                console.log("tableData")
                console.log(this.tableData)
                console.log(this.tableData.size)
              }),
              response => {
                console.log(failed);
              };
      
  },
  computed: {
    totalPrice() {
      let price = 0;
      this.goodsCheckList.forEach(item => {
        price += item.price * item.count;
      });
      return price;
    },
    size() {
      return this.tableData.length;
    }
  },
  methods: {
    newFeed() {
      console.log("feedback");
      this.$router.push({ name: "FeedBack" });
    },
    pageChange(index) {
      //页数变化回调
      var start = (index - 1) * this.pageSize;
      var end = index * this.pageSize;
      this.tableData = this.allData.slice(start, end);
      //this.pageCurrent=index;
    },
    select(selection, row) {
      console.log(selection);
      this.goodsCheckList = selection;
    },
    changeAddress(data) {
      const father = this;
      this.address.forEach(item => {
        if (item.addressId === data) {
          father.checkAddress.name = item.name;
          father.checkAddress.address = `${item.name} ${item.province} ${
            item.city
          } ${item.address} ${item.phone} ${item.postalcode}`;
        }
      });
    }
  },
  mounted() {
    this.userID = 8;
    // this.userID = 1;

          
    // this.$http
    //   .get(getUrl, {
    //     params: { userid: this.userID }
    //   })
    //   .then(
    //     res => {
    //       // 响应成功回调
    //       var allData = res.body;
    //       for (var i = 0; i < allData.length; i++) {
    //         var item = allData[i];
    //         item.isAdmin = false;
    //         if (item.answer !== null) {
    //           var nitem = {
    //             title: "reply to " + item.title,
    //             time: item.time,
    //             content: item.answer,
    //             isAdmin: true,
    //             answer: null
    //           };

    //           allData.splice(++i, 0, nitem);
    //         }
    //       }

    //       console.log(allData);
    //       this.allData = allData;
    //       this.pageChange(1);
    //     },
    //     res => {
    //       // 响应错误回调
    //       console.log("fail");
    //     }
    //   )
    //   .catch(() => {
    //     console.log("process fail");
    //   });
  }
};
</script>

<style scoped>
.newButton {
  float: right;
  margin-top: 10px;
  margin-bottom: 10px;
  font-size: 1.3em;
}

.footer {
  margin-top: 60px;
}

.time {
  color: #777;
  font-style: italic;
  font-size: 1.1em;
}

.mini-status.status-open {
  background: #52c27d;
}

.mini-status {
  display: inline-block;
  -moz-border-radius: 3px;
  -webkit-border-radius: 3px;
  border-radius: 3px;
  padding: 3px 4px 2px;
  line-height: 1;
  color: #fff;
  font-weight: 600;
  margin: 0 10px 0 0;
  position: relative;
  top: -2px;
  font-size: 10px;
  text-transform: uppercase;
  background: #ccc;
}

.container {
  max-width: 860px;
  margin: 0 auto 2em;
  min-height: 480px;
  width: 95%;
}

.ticket {
  position: relative;
  display: block;
  margin: 0;
  padding: 17px 90px 18px 74px;
  border-bottom: 1px #dfdfdf dashed;
  color: blue;
}

.title {
  font-size: 1em;
  padding-bottom: 0.5em;
  padding-top: 0;
  width: 85%;
  background: rgba(0, 0, 0, 0);
  font-weight: 400;
}

.reply {
  position: absolute;
  right: 1em;
  color: #ccc;
}

.content {
  font-size: 1.0em;
  color: #666;
}

.answer {
  top: 10em;
  color: #ccc;
}


.author {
  position: absolute;
  right: 1em;
  top: 2em;
  font-size: 0.9em;
  color: #666;
  /* float: right; */
}

.image {
  display: block;
  width: 48px;
  height: 48px;
  object-fit: cover;
  left: 10px;
}

.avatar {
  position: absolute;
  /* object-fit: cover; */
  width: 64px;
  left: 10px;
  /* background: url(/src/assets/none.png) no-repeat; */
  height: 64px;
}
</style>