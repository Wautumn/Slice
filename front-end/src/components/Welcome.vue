<template>
<div>
  <div class="container demo-3">
    <div class="content">
      <div id="large-header" class="large-header">
        <canvas id="demo-canvas"></canvas>
        <h1 class="main-title">Slice</h1>
        <h2>简单易行的时间管理工具</h2>
      </div>
      <nav class="codrops-demos">
        <!--<a href="index.html">Demo 1</a>-->
        <!--<a href="index2.html">Demo 2</a>-->
        <!--<a class="current-demo" href="index3.html">Demo 3</a>-->
        <!--<a href="index4.html">Demo 4</a>-->
      </nav>
    </div>
  </div><!-- /container -->
    <div class="center">
        <el-carousel :interval="5000" arrow="always" class="carousel">
          <el-carousel-item v-for="item in images" :key="item.id">
            <img :src="item.path" :alt="item.alt">
            <!-- <h3>{{ item }}</h3> -->
          </el-carousel-item>
        </el-carousel>
      </div>
  </div>
</template>


<script>
  import {demo3} from "../../static/demo-3";

  export default {
    name: "Welcome",
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
    mounted() {
      this.methods1()
    },
    methods: {
      methods1: function () {
        demo3()

      }
    }
  }
</script>

<style scoped>
  article, aside, details, figcaption, figure, footer, header, hgroup, main, nav, section, summary {
    display: block;
  }
  h2 {
    position:absolute;
    left:320px;
    top:400px;
    color: white;
    font-size: 42px;
    font-weight: normal;
    line-height: 120px;
    /*text-shadow: 1px 1px 0 #ffffff, 1px 3px 0 #efefef;*/
  }


  body{
    background-color: #dd3e3a;
    color: #dd3e3a;
  }

  .container {
    /*margin-left: 0px;*/
    /*margin-right: 0px;*/

    width: 2700px;

    border: 0px solid green;
    /*padding: 0px;*/
    /*margin: 100px;*/
  }

  audio, canvas, video {
    display: inline-block;
  }

  audio:not([controls]) {
    display: none;
    height: 0;
  }

  [hidden] {
    display: none;
  }

  html {
    font-family: sans-serif;
    -ms-text-size-adjust: 100%;
    -webkit-text-size-adjust: 100%;
  }

  body {
    margin: 0;
  }

  a:focus {
    outline: thin dotted;
  }

  a:active, a:hover {
    outline: 0;
  }

  h1 {
    font-size: 2em;
    margin: 0.67em 0;
  }

  abbr[title] {
    border-bottom: 1px dotted;
  }

  b, strong {
    font-weight: bold;
  }

  dfn {
    font-style: italic;
  }

  hr {
    -moz-box-sizing: content-box;
    box-sizing: content-box;
    height: 0;
  }

  mark {
    background: #ff0;
    color: #000;
  }

  code, kbd, pre, samp {
    font-family: monospace, serif;
    font-size: 1em;
  }

  pre {
    white-space: pre-wrap;
  }

  q {
    quotes: "\201C" "\201D" "\2018" "\2019";
  }

  small {
    font-size: 80%;
  }

  sub, sup {
    font-size: 75%;
    line-height: 0;
    position: relative;
    vertical-align: baseline;
  }

  sup {
    top: -0.5em;
  }

  sub {
    bottom: -0.25em;
  }

  img {
    border: 0;
  }

  svg:not(:root) {
    overflow: hidden;
  }

  figure {
    margin: 0;
  }

  fieldset {
    border: 1px solid #c0c0c0;
    margin: 0 2px;
    padding: 0.35em 0.625em 0.75em;
  }

  legend {
    border: 0;
    padding: 0;
  }

  button, input, select, textarea {
    font-family: inherit;
    font-size: 100%;
    margin: 0;
  }

  button, input {
    line-height: normal;
  }

  button, select {
    text-transform: none;
  }

  button, html input[type="button"], input[type="reset"], input[type="submit"] {
    -webkit-appearance: button;
    cursor: pointer;
  }

  button[disabled], html input[disabled] {
    cursor: default;
  }

  input[type="checkbox"], input[type="radio"] {
    box-sizing: border-box;
    padding: 0;
  }

  input[type="search"] {
    -webkit-appearance: textfield;
    -moz-box-sizing: content-box;
    -webkit-box-sizing: content-box;
    box-sizing: content-box;
  }

  input[type="search"]::-webkit-search-cancel-button, input[type="search"]::-webkit-search-decoration {
    -webkit-appearance: none;
  }

  button::-moz-focus-inner, input::-moz-focus-inner {
    border: 0;
    padding: 0;
  }

  textarea {
    overflow: auto;
    vertical-align: top;
  }

  table {
    border-collapse: collapse;
    border-spacing: 0;
  }

  @font-face {
    font-weight: normal;
    font-style: normal;
    font-family: 'codropsicons';
    src: url('../../static/fonts/codropsicons/codropsicons.eot');
    src: url('../../static/fonts/codropsicons/codropsicons.eot?#iefix') format('embedded-opentype'),
    url('../../static/fonts/codropsicons/codropsicons.woff') format('woff'),
    url('../../static/fonts/codropsicons/codropsicons.ttf') format('truetype'),
    url('../../static/fonts/codropsicons/codropsicons.svg#codropsicons') format('svg');
  }

  *, *:after, *:before {
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
  }

  .clearfix:before, .clearfix:after {
    content: '';
    display: table;
  }

  .clearfix:after {
    clear: both;
  }

  body {
    background: #fff;
    color: #383a3c;
    font-weight: 400;
    font-size: 1em;
    line-height: 1.25;
    font-family: 'Raleway', Calibri, Arial, sans-serif;
  }

  a, button {
    outline: none;
  }

  a {
    color: #566473;
    text-decoration: none;
  }

  a:hover, a:focus {
    color: #34495e;
  }

  section {
    padding: 1em;
    text-align: center;
  }

  p.ref {
    text-align: center;
    padding: 2em 1em;
  }

  /* Header */
  .codrops-header {
    margin: 0 auto;
    padding: 2em;
    text-align: center;
    max-width: 900px;
  }

  .codrops-header h1 {
    margin: 0;
    font-size: 4.5em;
    line-height: 1;
    font-weight: 200;
  }

  .codrops-header h1 span {
    display: block;
    padding: 1em 0 1.5em;
    font-size: 36%;
    color: #95a5a6;
    line-height: 1.4;
  }

  /* To Navigation Style */
  .codrops-top {
    width: 100%;
    text-transform: uppercase;
    font-weight: 700;
    font-size: 0.69em;
    text-align: center;
    padding: 3em 0;
  }

  .codrops-top a {
    display: inline-block;
    padding: 1.5em;
    text-decoration: none;
    letter-spacing: 1px;
  }

  .codrops-icon:before {
    margin: 0 4px;
    text-transform: none;
    font-weight: normal;
    font-style: normal;
    font-variant: normal;
    font-family: 'codropsicons';
    line-height: 1;
    speak: none;
    -webkit-font-smoothing: antialiased;
  }

  .codrops-icon-drop:before {
    content: "\e001";
  }

  .codrops-icon-prev:before {
    content: "\e004";
  }

  /* Demo Buttons Style */
  .codrops-demos {
    padding-top: 1em;
    font-size: 0.8em;
    text-align: center;
  }

  .codrops-demos a {
    display: inline-block;
    margin: 0.35em 0.1em;
    padding: 0.5em 1.2em;
    outline: none;
    text-decoration: none;
    text-transform: uppercase;
    letter-spacing: 1px;
    font-weight: 700;
    border-radius: 2px;
    font-size: 110%;
    border: 2px solid transparent;
  }

  .codrops-demos a:hover,
  .codrops-demos a.current-demo {
    border-color: #383a3c;
  }

  .codrops-demos h3 {
    margin: 0;
    padding: 1em 0 0.5em 0;
    font-size: 0.9em;
    float: left;
    min-width: 90px;
    clear: left;
  }

  .codrops-demos div:not(:first-child) h3 {
    padding-top: 2em;
  }

  .codrops-demos a:hover,
  .codrops-demos a.current-demo {
    color: inherit;
    border-color: initial;
  }

  /* Related demos */
  .related {
    padding: 10em 0;
  }

  .related p {
    font-size: 1.5em;
  }

  .related > a {
    display: inline-block;
    text-align: center;
    margin: 20px 10px;
    padding: 25px;
    vertical-align: middle;
  }

  .related a img {
    max-width: 100%;
    opacity: 0.8;
    border-radius: 10px;
  }

  .related a:hover img,
  .related a:active img {
    opacity: 1;
  }

  .related a h3 {
    margin: 0;
    min-height: 63px;
    padding: 0.5em 0 0.3em;
    max-width: 300px;
    text-align: center;
    font-weight: 400;
    font-size: 1em;
  }

  @media screen and (max-width: 40em) {

    .codrops-header h1 {
      font-size: 2.5em;
    }
  }

  /* Header */
  .large-header {
    position: relative;
    width: 100%;
    background: #333;
    overflow: hidden;
    background-size: cover;
    background-position: center center;
    z-index: 1;
  }


  .demo-3 .large-header {
    background: #7f8c8d;
  }

  .demo-4 .large-header {
    background: #f9f1e9;
  }

  .main-title {
    position: absolute;
    margin: 0;
    padding: 0;
    color: #f9f1e9;
    text-align: center;
    top: 50%;
    left: 50%;
    -webkit-transform: translate3d(-50%, -50%, 0);
    transform: translate3d(-50%, -50%, 0);
  }

  .demo-1 .main-title,
  .demo-3 .main-title {
    text-transform: uppercase;
    font-size: 4.2em;
    letter-spacing: 0.1em;
  }

  .demo-2 .main-title {
    font-family: 'Clicker Script', cursive;
    font-weight: normal;
    font-size: 8em;
    padding-left: 10px;
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.4);
  }


  .demo-3 .main-title {
    padding: 10px 40px;
    border: 10px double #f9f1e9;
    text-transform: uppercase;
    font-family: Londrina Outline, sans-serif;
  }

  .demo-4 .main-title {
    font-size: 6em;
    font-weight: 300;
    padding: 10px 30px;
    text-transform: uppercase;
    color: #222;
  }

  .main-title .thin {
    font-weight: 200;
  }

  @media only screen and (max-width: 768px) {
    .demo-1 .main-title,
    .demo-3 .main-title,
    .demo-4 .main-title {
      font-size: 3em;
    }

    .demo-2 .main-title {
      font-size: 4em;
    }
  }

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
  height: 600px;
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
