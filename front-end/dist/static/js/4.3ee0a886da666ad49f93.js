webpackJsonp([4],{GHRY:function(t,e){},XKqf:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var l=a("HzJ8"),n=a.n(l),o={data:function(){return{pageSize:5,reply:"",currentRow:null,tableData:[],allData:[]}},computed:{hasSelect:function(){return null!==this.currentRow},size:function(){return this.allData.length}},mounted:function(){var t=this,e=null;this.$http.get("http://localhost:8080/getAllFeedback").then(function(a){e=a.body;var l=!0,o=!1,r=void 0;try{for(var s,i=n()(e);!(l=(s=i.next()).done);l=!0){var c=s.value;0==c.status?c.status="未处理":1==c.status?c.status="已处理":c.status="未知"}}catch(t){o=!0,r=t}finally{try{!l&&i.return&&i.return()}finally{if(o)throw r}}t.allData=e,console.log(t.allData),t.pageChange(1)},function(t){console.log("fail")}).catch(function(){console.log("process fail")})},methods:{pageChange:function(t){console.log("pageChange");var e=(t-1)*this.pageSize,a=t*this.pageSize;this.tableData=this.allData.slice(e,a)},submitReply:function(){var t=this;console.log(this.currentRow.id);var e={id:this.currentRow.id,answer:this.reply};this.$http.post("http://localhost:8080/handleFeedback",e,{emulateJSON:!0}).then(function(e){t.$Message.info("提交成功"),t.currentRow.status="已处理",t.currentRow.answer=t.reply,console.log(e)},function(t){console.log("fail")}).catch(function(){console.log("process fail")})},setCurrent:function(t){this.$refs.singleTable.setCurrentRow(t),this.currentRow=null},handleCurrentChange:function(t){this.currentRow=t}}},r={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticStyle:{"padding-left":"15%","padding-right":"15%","margin-top":"20px"}},[a("el-table",{ref:"singleTable",staticStyle:{width:"100%"},attrs:{data:t.tableData,"tooltip-effect":"dark","highlight-current-row":""},on:{"current-change":t.handleCurrentChange}},[a("el-table-column",{attrs:{type:"expand"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-form",{staticClass:"demo-table-expand",attrs:{"label-position":"left",inline:""}},[a("el-form-item",{attrs:{label:"内容："}},[a("span",[t._v(t._s(e.row.content))])]),t._v(" "),a("el-form-item",{attrs:{label:"回复："}},[a("span",[t._v(t._s(e.row.answer))])])],1)]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"时间",width:"180"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.date))]}}])}),t._v(" "),a("el-table-column",{attrs:{prop:"name",label:"用户名",width:"120",sortable:""}}),t._v(" "),a("el-table-column",{attrs:{prop:"status",label:"状态",width:"120",sortable:""}}),t._v(" "),a("el-table-column",{attrs:{prop:"title",label:"标题","show-overflow-tooltip":""}})],1),t._v(" "),a("div",{staticStyle:{"margin-top":"20px"}},[a("el-button",{staticStyle:{float:"right"},on:{click:function(e){return t.setCurrent()}}},[t._v("取消选择")])],1),t._v(" "),t.hasSelect?a("div",{staticStyle:{"margin-top":"30px"},attrs:{height:"60px"}},[a("h1",[t._v("回复")]),t._v(" "),a("el-input",{staticStyle:{"margin-top":"10px","margin-bottom":"10px"},attrs:{type:"textarea"},model:{value:t.reply,callback:function(e){t.reply=e},expression:"reply"}}),t._v(" "),a("el-button",{staticStyle:{float:"right"},attrs:{type:"primary"},on:{click:t.submitReply}},[t._v("提交")])],1):t._e(),t._v(" "),a("Page",{staticClass:"page-footer",attrs:{total:t.size,"page-size":t.pageSize,"show-elevator":""},on:{"on-change":t.pageChange}})],1)},staticRenderFns:[]};var s=a("C7Lr")(o,r,!1,function(t){a("GHRY")},"data-v-76cd8c51",null);e.default=s.exports}});
//# sourceMappingURL=4.3ee0a886da666ad49f93.js.map