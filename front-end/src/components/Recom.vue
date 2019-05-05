<template>
  <div class="container">
    <div class="section-header">
      <h2>推荐文章</h2>
      <div style="margin-top:10px">
        <el-tag
          class="head-tag"
          v-if="loading"
          v-for="tag in tags"
          :key="tag.name"
          closable
          :disable-transitions="false"
          @close="handleClose(tag)"
          :type="tag.type"
        >{{tag.name}}</el-tag>
      </div>
    </div>
    <div class="section-container">
      <el-card class="box-card" v-for="i in showArticles" :key="i.id">
        <img :src="i.img" class="image">
        <div class="article">
          <div class="article-container">
            <a :href="i.url">
              <h3>{{i.title}}</h3>
            </a>
            <p>{{i.intro}}</p>
          </div>
          <div class="article-footer">
            <p class="aurthor">{{i.author}}</p>
            <!-- <p class="click-count">{{i.clickcount}}</p> -->
            <p class="tag">{{numToTag[i.tags]}}</p>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
var articleurl = "http://localhost:8080/getRecommendArticle";

export default {
  data() {
    return {
      articles: [],
      showArticles: [],
      userID: 2,
      loading: false,
      tags: [
        { name: "标签一", type: "" },
        { name: "标签二", type: "success" },
        { name: "标签三", type: "info" },
        { name: "标签四", type: "warning" },
        { name: "标签五", type: "danger" }
      ],
      numToTag: []
    };
  },
  methods: {
    handleClose(tag) {
      this.tags.splice(this.tags.indexOf(tag), 1);
      console.log(tag.name);
      for (let i = 0; i < this.showArticles.length; i++) {
        let tagNum = this.showArticles[i].tags;
        let tagName = this.numToTag[tagNum];
        //debugger;
        if (tagName == tag.name) {
          //删除对应位置article后，需要保持游标位置不变
          this.showArticles.splice(i--, 1);
        }
      }
      this.showArticles = this.showArticles.sort(function() {
        return 0.5 - Math.random();
      });
    }
  },
  mounted() {
    this.userID = sessionStorage.userId;
    // Lambda写法
    this.$http
      .get(articleurl, {
        params: { userId: this.userID }
      })
      .then(
        res => {
          // 响应成功回调
          var returnData = res.body;
          this.articles = returnData.Article;
          this.articles = this.articles.filter(item => {
            return item.intro;
          });
          this.showArticles = this.articles;
          this.showArticles = this.showArticles.sort(function() {
            return 0.5 - Math.random();
          });
          this.numToTag = returnData.tags;
          var tagName = Object.values(this.numToTag);
          for (var i = 0; i < tagName.length; i++) {
            this.tags[i].name = tagName[i];
          }
          this.tags = this.tags.slice(0, tagName.length);
          this.loading = true;
        },
        res => {
          // 响应错误回调
          console.log("fail");
        }
      )
      .catch(() => {
        console.log("fail2");
      });
  }
};
</script>

<style scoped>
.head-tag {
  margin: 3px;
}
.container {
  margin-left: 30px;
}

.tag {
  margin: 5px;
  border: 1px solid #dcdfe6;
  border-color: #ebeef5;
  border-radius: 18px;
  padding: 7px 15px;
  float: right;
}

.aurthor {
  color: #3dd07d;
  font-size: 14px;
  margin: 5px;
}

.click-count {
  font-size: 0.8em;
  color: #bababa;
  margin: 5px;
}

.article {
  border: 20px;
}

.article-footer {
  position: relative;
  padding: 18px;
}

.article-container {
  padding: 18px;
  position: relative;
}

.section-container {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: center;
}

.section-header h2:before {
  content: "";
  position: absolute;
  bottom: 0;
  left: 50%;
  width: 70px;
  height: 2px;
  background: #3dd07d;
  transform: translateX(-50%);
}

.section-header h2 {
  position: relative;
  display: inline-block;
  font-size: 18px;
  padding-bottom: 10px;
}

.section-header-class {
  margin-top: 15px;
  font-size: 15px;
}

.section-header {
  margin: 0 0 60px;
  margin-top: 60px;
  text-align: center;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
}

.image {
  display: block;
  width: 100%;
  height: 180px;
  object-fit: cover;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}

.box-card {
  width: 350px;
  margin: 13px;
}

h3 {
  color: #343434;
  font-size: 1.5em;
  line-height: 1.35;
  margin-bottom: 1em;
}
</style>

<style>
.el-card__body {
  padding: 0px !important;
}
</style>


