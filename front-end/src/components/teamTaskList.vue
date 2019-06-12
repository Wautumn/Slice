<template>
   <el-tree
      :data="data"
      show-checkbox
      node-key="id"
      default-expand-all
      :expand-on-click-node="false">
      <span class="custom-tree-node" slot-scope="{ node, data }">
        <span>{{ node.label }}</span>
        <span>
            
          <el-table
      ref="singleTable"
      :data="tableData"
      :row-class-name="tableRowClassName"
      highlight-current-row
      @current-change="handleCurrentChange"
      style="width: 100%"
      height="1000"
    >
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="table-expand">
            <el-form-item label="任务描述：">
              <span>{{props.row.description}}</span>
            </el-form-item>
            
            <el-form-item label="任务状态：">
              <!-- <span>{{tableRowStatus(props.row.status)}}</span> -->
           
               <span>
                 <!-- {{props.row.status}} -->
                   <template v-if="props.row.status==1">未开始</template>
        <template v-else-if="props.row.status==2">进行中</template>
        <template v-else-if="props.row.status==3">已完成</template>
        <template v-else-if="props.row.status==4">已过期</template>
        <template v-else-if="props.row.status==5">已终止</template>
                 </span>
            </el-form-item>
            <el-form-item label="开始时间：">
              <span>{{props.row.starttime}}</span>
            </el-form-item>
            <el-form-item label="截止时间：">
              <span>{{props.row.endtime}}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column property="name" label="任务"></el-table-column>
      <el-table-column label=" 状态" width="75">
        <template slot-scope="taskscope">
        <template v-if="taskscope.row.status==1">未开始</template>
        <template v-else-if="taskscope.row.status==2">进行中</template>
        <template v-else-if="taskscope.row.status==3">已完成</template>
        <template v-else-if="taskscope.row.status==4">已过期</template>
        <template v-else-if="taskscope.row.status==5">已终止</template>
         </template>
      </el-table-column>
    </el-table>

         
        </span>
      </span>
    </el-tree>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        teamTaskUrl:"http://101.132.194.45:8081/slice-0.0.1-SNAPSHOT/getTodaysTasks",
        tabledate:[],
        data: [{
          label: '一级 1',
          children: [{
            label: '二级 1-1',
            children: [{
              label: '三级 1-1-1'
            }]
          }]
        }, {
          label: '一级 2',
          children: [{
            label: '二级 2-1',
            children: [{
              label: '三级 2-1-1'
            }]
          }, {
            label: '二级 2-2',
            children: [{
              label: '三级 2-2-1'
            }]
          }]
        }, {
          label: '一级 3',
          children: [{
            label: '二级 3-1',
            children: [{
              label: '三级 3-1-1'
            }]
          }, {
            label: '二级 3-2',
            children: [{
              label: '三级 3-2-1'
            }]
          }]
        }],
        defaultProps: {
          children: 'children',
          label: 'label'
        }
      };
    },
    methods: {
      handleNodeClick(data) {
        console.log(data);
      },
      getTask(){
          this.$http
            .get(this.teamTaskUrl, {
              // params: { userId: sessionStorage.userId }
              params: {userid: "10"}
            })
            .then(response => {
              this.taskData = response.data;
              var nlist = [];
              for (var item of this.taskData) {
                // if (item.deadline >= this.getcurrentTime)
                nlist.push(item);
              }
              
             var j={"weekday":"2019/5/22","总数":0,"进行中":0,"已完成":0}
              let today=new Date()
              let nowDate = today.setDate(today.getDate()-7+k);
              let nowdate=new Date(nowDate);
              j.weekday=nowdate.format("yyyy-MM-dd");
              this.currentData.push(j)


            }),
            response => {
              console.log("failed");
            };
          console.log(this.taskData);
          return;
      },

    }
  };
</script>
