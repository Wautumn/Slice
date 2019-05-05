<template>
  <div>
    <div class="p-contain">
      <span>每日目标：</span>
      <ve-pie :data="pieData1"></ve-pie>
    </div>
    <div class="p-contain">
      <span>每周目标：</span>
      <ve-pie :data="pieData2"></ve-pie>
    </div>
    <div class="p-contain">
      <span>每月目标：</span>
      <ve-pie :data="pieData3"></ve-pie>
    </div>
  </div>
</template>


<style scoped>
.p-contain {
  float: left;
  width: 320px;
  height: 320px;
  margin: 30px;
}

span {
  font-family: "Helvetica Neue";
  font-size: 1.3em;
}
</style>

<script>
import "echarts/lib/chart/line";
// import VeLine from "v-charts/lib/line";
import VePie from "v-charts/lib/pie.js";

export default {
  components: {
    VePie
  },
  mounted() {
    console.log("his page");
    // this.$router.push({ name: "DayView1" });
  },
  data() {
    return {
      pieData1: {
        columns: ["状态", "进度"],
        rows: [{ 状态: "未完成", 进度: 0 }, { 状态: "已完成", 进度: 1 }]
      },
      pieData2: {
        columns: ["状态", "进度"],
        rows: [{ 状态: "未完成", 进度: 4 }, { 状态: "已完成", 进度: 3 }]
      },
      pieData3: {
        columns: ["状态", "进度"],
        rows: [{ 状态: "未完成", 进度: 15 }, { 状态: "已完成", 进度: 10 }]
      }
    };
  }
};
</script>