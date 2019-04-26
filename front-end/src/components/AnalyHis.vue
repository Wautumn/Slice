<template>
  <div>
    <el-container>
      <el-aside width="200px">
        <navmenu></navmenu>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import NavMenu from "@/components/NavMenu";
import "echarts/lib/chart/line";
// import VeLine from "v-charts/lib/line";
import VePie from "v-charts/lib/pie.js";

export default {
  components: {
    navmenu: NavMenu,
    VePie
  },
  mounted() {
    console.log("his page");
    this.$router.push({ name: "DayView1" });
  },
  data() {
    return {
      pieData: {
        columns: ["状态", "进度"],
        rows: [{ 状态: "1/1", 进度: 1393 }, { 状态: "1/2", 进度: 3530 }]
      }
    };
  }
};
</script>

<style>
.abandon {
  background-color: #000000 !important;
}

.completed {
  background-color: #1eff00 !important;
}

.waiting {
  background-color: #ffe800 !important;
}

.running {
  background-color: #ff0000 !important;
}
</style>

