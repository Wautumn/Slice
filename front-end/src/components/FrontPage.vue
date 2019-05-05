<template>
  <div class="container">
    <div class="info">
      <h1>
        <span class="brand-color">Growth</span>
      </h1>
      <h2>基于番茄工作法的简单易行的时间管理工具</h2>
      <div class="center">
        <el-carousel :interval="5000" arrow="always" class="carousel">
          <el-carousel-item v-for="item in images" :key="item.id">
            <img :src="item.path" :alt="item.alt">
            <!-- <h3>{{ item }}</h3> -->
          </el-carousel-item>
        </el-carousel>
      </div>
      <p>
        <Button type="error" shape="circle" class="btn" @click="ToLog">立即体验</Button>
      </p>
      <h2>Growth能帮你做什么？</h2>
    </div>

    <div class="row">
      <div class="col-md-4">
        <h2>整理</h2>
        <ul>
          <li>列出所有要完成的事项并按优先级排序</li>
          <li>选择优先级高的事项作为今日的待办事项</li>
          <li>把今日待办事项当成对自己的承诺,全力完成</li>
          <li>事项越来越少,成就感/信心越来越高</li>
        </ul>
      </div>
      <div class="col-md-4">
        <h2>监督</h2>
        <ul>
          <li>指导你最优处理外部打扰,减少中断</li>
          <li>走动的番茄钟监督工作,让你少分心,专注工作</li>
          <li>帮你巩固达成目标的决心</li>
          <li>安排你定时休息,劳逸结合!</li>
        </ul>
      </div>
      <div class="col-md-4">
        <h2>统计</h2>
        <ul>
          <li>自动帮你统计工作耗时、工作量</li>
          <li>帮你统计每天的实际工作时长</li>
          <li>便捷添加工作总结、心得</li>
          <li>有总结、有统计,决策评估完全用数据说话</li>
        </ul>
      </div>
    </div>
  </div>
</template>


<script>
export default {
  data() {
    return {
      images: [
        {
          path: require("../assets/1.png"),
          alt: "历史纪录"
        },
        {
          path: require("../assets/2.png"),
          alt: "统计分析"
        },
        {
          path: require("../assets/3.png"),
          alt: "工作台"
        }
      ]
    };
  },
  methods: {
    ToLog() {
      console.log("login");
      this.$router.push({ name: "Login" });
    }
  }
};
</script>


<style scoped>
.container .info {
  text-align: center;
}

.container {
  padding-top: 50px;
}

.info h1 {
  font-family: "Droid Sans", â€‹ "Helvetica Neue", â€‹Helvetica, â€‹sans-serif;
  color: #999999;
  font-size: 80px;
  font-weight: 700;
  line-height: 160px;
  text-shadow: 0 0 1px rgba(0, 0, 0, 0.05), 0 1px 1px rgba(0, 0, 0, 0.3);
}

.info h2 {
  color: #777777;
  font-size: 42px;
  font-weight: normal;
  line-height: 120px;
  text-shadow: 1px 1px 0 #ffffff, 1px 3px 0 #efefef;
}

.info {
  margin: 40px 0 40px 0;
}

.info img {
  /* border: 1px solid #d9d9d9; */
  padding: 1px;
}

img {
  display: block;
  width: 100%;
  /* height: 500px; */
  object-fit: cover;
  /* padding: auto; */
}

.info .carousel-control.right,
.info .carousel-control.left {
  background: none;
}

.center {
  margin: auto;
  width: 75%;
  /* height: 600px; */
}

.el-carousel__item h3 {
  color: #475669;
  font-size: 18px;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
}

.info .btn {
  font-size: 21px;
  padding: 14px 24px;
  margin: 60px auto;
}

.row h2 {
  color: #333;
  margin-bottom: 5px;
  text-shadow: 1px 1px 0 #ffffff, 1px 3px 0 #efefef;
}

.col-md-4 {
  width: 33.33333333333333%;
  float: left;
  position: relative;
  min-height: 1px;
  padding-right: 15px;
  padding-left: 15px;
}

.row {
  margin-right: -15px;
  margin-left: -15px;
  font: 14px "Droid Sans", "Helvetica Neue", Helvetica, sans-serif;
  line-height: 1.3em;
  padding: 10%;
  padding-top: 3%;
}

.row ul {
  padding: 0;
  margin-left: 15px;
}
.row li {
  line-height: 28px;
  color: #777;
  list-style-type: disc;
  padding: 0;
  margin: 0;
}
</style>
