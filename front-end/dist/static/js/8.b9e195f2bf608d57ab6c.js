webpackJsonp([8],{"9r/T":function(t,e,s){(function(t){var a=void 0!==t&&t||"undefined"!=typeof self&&self||window,n=Function.prototype.apply;function r(t,e){this._id=t,this._clearFn=e}e.setTimeout=function(){return new r(n.call(setTimeout,a,arguments),clearTimeout)},e.setInterval=function(){return new r(n.call(setInterval,a,arguments),clearInterval)},e.clearTimeout=e.clearInterval=function(t){t&&t.close()},r.prototype.unref=r.prototype.ref=function(){},r.prototype.close=function(){this._clearFn.call(a,this._id)},e.enroll=function(t,e){clearTimeout(t._idleTimeoutId),t._idleTimeout=e},e.unenroll=function(t){clearTimeout(t._idleTimeoutId),t._idleTimeout=-1},e._unrefActive=e.active=function(t){clearTimeout(t._idleTimeoutId);var e=t._idleTimeout;e>=0&&(t._idleTimeoutId=setTimeout(function(){t._onTimeout&&t._onTimeout()},e))},s("AYvJ"),e.setImmediate="undefined"!=typeof self&&self.setImmediate||void 0!==t&&t.setImmediate||this&&this.setImmediate,e.clearImmediate="undefined"!=typeof self&&self.clearImmediate||void 0!==t&&t.clearImmediate||this&&this.clearImmediate}).call(e,s("9AUj"))},AYvJ:function(t,e,s){(function(t,e){!function(t,s){"use strict";if(!t.setImmediate){var a,n,r,i,o,c=1,u={},l=!1,m=t.document,h=Object.getPrototypeOf&&Object.getPrototypeOf(t);h=h&&h.setTimeout?h:t,"[object process]"==={}.toString.call(t.process)?a=function(t){e.nextTick(function(){f(t)})}:!function(){if(t.postMessage&&!t.importScripts){var e=!0,s=t.onmessage;return t.onmessage=function(){e=!1},t.postMessage("","*"),t.onmessage=s,e}}()?t.MessageChannel?((r=new MessageChannel).port1.onmessage=function(t){f(t.data)},a=function(t){r.port2.postMessage(t)}):m&&"onreadystatechange"in m.createElement("script")?(n=m.documentElement,a=function(t){var e=m.createElement("script");e.onreadystatechange=function(){f(t),e.onreadystatechange=null,n.removeChild(e),e=null},n.appendChild(e)}):a=function(t){setTimeout(f,0,t)}:(i="setImmediate$"+Math.random()+"$",o=function(e){e.source===t&&"string"==typeof e.data&&0===e.data.indexOf(i)&&f(+e.data.slice(i.length))},t.addEventListener?t.addEventListener("message",o,!1):t.attachEvent("onmessage",o),a=function(e){t.postMessage(i+e,"*")}),h.setImmediate=function(t){"function"!=typeof t&&(t=new Function(""+t));for(var e=new Array(arguments.length-1),s=0;s<e.length;s++)e[s]=arguments[s+1];var n={callback:t,args:e};return u[c]=n,a(c),c++},h.clearImmediate=d}function d(t){delete u[t]}function f(t){if(l)setTimeout(f,0,t);else{var e=u[t];if(e){l=!0;try{!function(t){var e=t.callback,a=t.args;switch(a.length){case 0:e();break;case 1:e(a[0]);break;case 2:e(a[0],a[1]);break;case 3:e(a[0],a[1],a[2]);break;default:e.apply(s,a)}}(e)}finally{d(t),l=!1}}}}}("undefined"==typeof self?void 0===t?this:t:self)}).call(e,s("9AUj"),s("V0EG"))},RtVQ:function(t,e,s){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var a=s("HzJ8"),n=s.n(a),r=s("9r/T"),i={components:{tasklist:s("fU07").default},data:function(){return{tomoStartTime:"",dailySummary:"",selfRating:0,getcurrentTime:"",countButtonType:"success",selected:!1,selectCancel:!1,countOn:!1,count:"0",minutes:"0",seconds:"0",timer:null,currentTask:null,currentTaskName:"",currentTaskDetail:"",currentFinishedPomo:null,currentTotalPomo:null,currentCondition:!1,currentStatus:-2,currentDeadline:null,pomoCondition:!1,taskData:[],taskRequestUrl:"http://localhost:8080/task/getTask",taskStartUrl:"http://localhost:8080/task/startTask",tomatoStartUrl:"http://localhost:8080/startTomato",tomatoBreakUrl:"http://localhost:8080/breakTomato",tomatoEndUrl:"http://localhost:8080/endTomato",deleteTaskUrl:"http://localhost:8080/task/deleteTask",modifyTaskUrl:"http://localhost:8080/task/modifyTask",endTaskUrl:"http://localhost:8080/task/endTask",breakTaskUrl:"http://localhost:8080/task/breakTask",dailySummaryUrl:"http://localhost:8080/summary/save"}},mounted:function(){var t=this;this.$http.get(this.taskRequestUrl,{params:{userId:sessionStorage.userId}}).then(function(e){t.taskData=e.data;var s=[],a=!0,r=!1,i=void 0;try{for(var o,c=n()(t.taskData);!(a=(o=c.next()).done);a=!0){var u=o.value;u.deadline>=t.getcurrentTime&&s.push(u)}}catch(t){r=!0,i=t}finally{try{!a&&c.return&&c.return()}finally{if(r)throw i}}t.taskData=s}),this.getcurrentTime=this.currentTime(),this.getcurrentTime=this.getcurrentTime.substring(0,10)},methods:{startCount:function(){var t=this;if(sessionStorage.tomoLength)var e=60*parseInt(sessionStorage.tomoLength);else e=5;e=5;this.timer||(this.count=e,this.countOn=!0,this.countButtonType="danger",this.pomoCondition=!1,this.seconds=this.count%60,this.minutes=parseInt(this.count/60),this.tomoStartTime=(new Date).format("yyyy-MM-dd hh:mm:ss"),this.$http.get(this.tomatoStartUrl,{params:{userId:sessionStorage.userId,taskName:this.currentTaskName,startTime:this.tomoStartTime}}),this.timer=Object(r.setInterval)(function(){t.count>0&&t.count<=e?(t.count--,t.seconds=t.count%60,t.minutes=parseInt(t.count/60)):(t.countOn=!1,t.countButtonType="success",Object(r.clearInterval)(t.timer),t.timer=null,0==t.count&&(t.pomoCondition=!0,t.selected||t.$alert("请选择一个任务以完成本番茄钟","提示",{confirmButtonText:"确定",callback:function(t){}})))},1e3))},stopCount:function(){this.count="-1",this.minutes="0",this.seconds="0",this.$http.get(this.tomatoBreakUrl,{params:{userId:sessionStorage.userId,breakTime:(new Date).format("yyyy-MM-dd hh:mm:ss"),startTime:this.tomoStartTime}})},getTask:function(t){var e=this;if("new"==t)return this.$http.get(this.taskRequestUrl,{params:{userId:sessionStorage.userId}}).then(function(t){e.taskData=t.data;var s=[],a=!0,r=!1,i=void 0;try{for(var o,c=n()(e.taskData);!(a=(o=c.next()).done);a=!0){var u=o.value;u.deadline>=e.getcurrentTime&&s.push(u)}}catch(t){r=!0,i=t}finally{try{!a&&c.return&&c.return()}finally{if(r)throw i}}e.taskData=s}),void console.log(this.taskData);for(this.selectCancel?this.selectCancel=!1:this.selected=!0,this.currentTask=0;this.taskData[this.currentTask]!=t&&this.taskData[this.currentTask]!=t;this.currentTask++);this.currentTaskName=t.name,this.currentFinishedPomo=t.tomatoCompleted,this.currentTotalPomo=parseInt(t.expectedTomato),this.currentTaskDetail=t.description,this.currentDeadline=t.deadline,this.currentStatus=t.status,this.taskData[this.currentTask].tomatoCompleted==this.taskData[this.currentTask].expectedTomato?this.currentCondition=!0:this.currentCondition=!1},completePomos:function(){this.pomoCondition=!1,this.currentFinishedPomo++,this.taskData[this.currentTask].tomatoCompleted++,this.$http.get(this.tomatoEndUrl,{params:{userId:sessionStorage.userId,needAssociation:!0,taskName:this.currentTaskName,startTime:this.tomoStartTime,endTime:(new Date).format("yyyy-MM-dd hh:mm:ss")}}),this.$http.get(this.modifyTaskUrl,{params:{userId:sessionStorage.userId,taskName:this.currentTaskName,property:"tomatoCompleted",value:this.currentFinishedPomo}}),this.taskData[this.currentTask].currentPomo==this.taskData[this.currentTask].totalPomo?this.currentCondition=!0:this.currentCondition=!1},deleteTask:function(){var t=this;this.$confirm("此操作将永久删除该任务, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){t.$http.get(t.deleteTaskUrl,{params:{userId:t.taskData[t.currentTask].userId,taskName:t.taskData[t.currentTask].name,starttime:null}}).then(function(){t.$http.get(t.taskRequestUrl,{params:{userId:sessionStorage.userId}}).then(function(e){t.taskData=e.data;var s=[],a=!0,r=!1,i=void 0;try{for(var o,c=n()(t.taskData);!(a=(o=c.next()).done);a=!0){var u=o.value;u.deadline>=t.getcurrentTime&&s.push(u)}}catch(t){r=!0,i=t}finally{try{!a&&c.return&&c.return()}finally{if(r)throw i}}t.taskData=s,t.selectCancel=!0,t.selected=!1,t.$message({type:"success",message:"删除成功!"})})})}).catch(function(){t.$message({type:"info",message:"已取消删除"})})},finishTask:function(){this.$http.get(this.endTaskUrl,{params:{userId:sessionStorage.userId,taskName:this.currentTaskName,startTime:(new Date).format("yyyy-MM-dd hh:mm:ss")}}),this.taskData[this.currentTask].status=2,this.currentStatus=2},breakTask:function(){var t=this;this.$confirm("此操作将废弃当前任务, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){t.$http.get(t.breakTaskUrl,{params:{userId:sessionStorage.userId,taskName:t.currentTaskName}}),t.taskData[t.currentTask].status=-1,t.currentStatus=-1,t.$message({type:"success",message:"废弃成功!"})}).catch(function(){t.$message({type:"info",message:"已取消废弃"})})},onSubmit:function(){var t=this;this.$http.get(this.modifyTaskUrl,{params:{userId:sessionStorage.userId,taskName:this.taskData[this.currentTask].name,property:"description",value:this.currentTaskDetail}}).then(function(){t.$http.get(t.taskRequestUrl,{params:{userId:sessionStorage.userId}}).then(function(e){t.taskData=e.data;var s=[],a=!0,r=!1,i=void 0;try{for(var o,c=n()(t.taskData);!(a=(o=c.next()).done);a=!0){var u=o.value;u.deadline>=t.getcurrentTime&&s.push(u)}}catch(t){r=!0,i=t}finally{try{!a&&c.return&&c.return()}finally{if(r)throw i}}t.taskData=s,t.$message({message:"保存成功！",type:"success"})})})},saveDailySummary:function(){var t=this;this.$http.get(this.dailySummaryUrl,{params:{userid:sessionStorage.userId,content:this.dailySummary,time:this.getcurrentTime,selfRating:this.selfRating}}).then(function(){t.$http.get(t.taskRequestUrl,{params:{userId:sessionStorage.userId}}).then(function(e){t.taskData=e.data;var s=[],a=!0,r=!1,i=void 0;try{for(var o,c=n()(t.taskData);!(a=(o=c.next()).done);a=!0){var u=o.value;u.deadline>=t.getcurrentTime&&s.push(u)}}catch(t){r=!0,i=t}finally{try{!a&&c.return&&c.return()}finally{if(r)throw i}}t.taskData=s,t.$message({message:"保存成功！",type:"success"})})})},dateFtt:function(t,e){var s={"M+":e.getMonth()+1,"d+":e.getDate(),"h+":e.getHours(),"m+":e.getMinutes(),"s+":e.getSeconds(),"q+":Math.floor((e.getMonth()+3)/3),S:e.getMilliseconds()};for(var a in/(y+)/.test(t)&&(t=t.replace(RegExp.$1,(e.getFullYear()+"").substr(4-RegExp.$1.length))),s)new RegExp("("+a+")").test(t)&&(t=t.replace(RegExp.$1,1==RegExp.$1.length?s[a]:("00"+s[a]).substr((""+s[a]).length)));return t},currentTime:function(){var t=new Date;return this.dateFtt("yyyy-MM-dd hh:mm:ss",t)}}},o={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",[s("el-container",[s("el-aside",{attrs:{width:"400px"}},[s("tasklist",{attrs:{tableData:t.taskData},on:{transferTask:t.getTask}})],1),t._v(" "),s("el-main",[s("div",{staticStyle:{"text-align":"center"}},[s("strong",{staticStyle:{"font-size":"200px","line-height":"300px",color:"#409eff"}},[t._v(t._s(t.minutes)+":"+t._s(t.seconds))]),t._v(" "),s("br"),t._v(" "),s("el-button",{directives:[{name:"show",rawName:"v-show",value:!t.currentCondition&&!t.pomoCondition,expression:"!currentCondition && !pomoCondition"}],attrs:{type:t.countButtonType,disabled:t.countOn||-1==t.currentStatus},on:{click:t.startCount}},[t._v("开始番茄钟")]),t._v(" "),s("el-button",{directives:[{name:"show",rawName:"v-show",value:!t.currentCondition,expression:"!currentCondition"}],attrs:{type:"warning",disabled:!t.countOn},on:{click:t.stopCount}},[t._v("中止番茄钟")]),t._v(" "),s("el-button",{directives:[{name:"show",rawName:"v-show",value:!t.currentCondition&&t.pomoCondition&&t.selected,expression:"!currentCondition && pomoCondition && selected"}],attrs:{type:"success"},on:{click:t.completePomos}},[t._v("完成番茄钟")]),t._v(" "),s("el-button",{directives:[{name:"show",rawName:"v-show",value:t.currentCondition,expression:"currentCondition"}],attrs:{type:"success",disabled:2==t.currentStatus},on:{click:t.finishTask}},[t._v("完成任务")]),t._v(" "),s("br")],1),t._v(" "),s("div",{directives:[{name:"show",rawName:"v-show",value:t.selected,expression:"selected"}],staticStyle:{"margin-left":"20px","margin-right":"20px"}},[s("div",{staticStyle:{"margin-top":"20px","margin-bottom":"20px"}},[s("h2",[t._v(t._s(t.currentTaskName))])]),t._v(" "),s("el-steps",{attrs:{active:t.currentFinishedPomo,space:"100px","finish-status":"success"}},t._l(t.currentTotalPomo,function(t){return s("el-step")}),1),t._v(" "),s("br"),t._v(" "),s("h1",{directives:[{name:"show",rawName:"v-show",value:t.selected,expression:"selected"}],staticStyle:{"margin-top":"10px"}},[t._v("任务时间")]),t._v(" "),s("div",{staticStyle:{"margin-top":"10px","margin-bottom":"10px","margin-left":"20px"}},[s("h2",[t._v("至"+t._s(t.currentDeadline))])]),t._v(" "),s("h1",{directives:[{name:"show",rawName:"v-show",value:t.selected,expression:"selected"}]},[t._v("任务详情")]),t._v(" "),s("div",{staticStyle:{"margin-top":"20px"}},[s("el-form",[s("el-form-item",[s("el-input",{directives:[{name:"show",rawName:"v-show",value:t.selected,expression:"selected"}],attrs:{type:"textarea",disabled:t.currentCondition},model:{value:t.currentTaskDetail,callback:function(e){t.currentTaskDetail=e},expression:"currentTaskDetail"}})],1),t._v(" "),s("el-form-item",[s("el-button",{directives:[{name:"show",rawName:"v-show",value:t.selected,expression:"selected"}],attrs:{type:"primary"},on:{click:t.onSubmit}},[t._v("保存")]),t._v(" "),s("el-button",{staticStyle:{float:"right"},attrs:{type:"danger"},on:{click:t.deleteTask}},[t._v("删除记录")]),t._v(" "),s("el-button",{staticStyle:{float:"right"},attrs:{type:"warning",disabled:-1==t.currentStatus},on:{click:t.breakTask}},[t._v("废弃任务")])],1)],1)],1)],1),t._v(" "),s("el-footer",{staticStyle:{"margin-top":"30px"},attrs:{height:"60px"}},[s("h1",[t._v("每日小结 "+t._s(t.getcurrentTime))]),t._v(" "),s("Rate",{attrs:{"show-text":"","allow-half":""},model:{value:t.selfRating,callback:function(e){t.selfRating=e},expression:"selfRating"}},[s("span",{staticStyle:{color:"#f5a632"}},[t._v(t._s(t.selfRating))])]),t._v(" "),s("el-input",{staticStyle:{"margin-top":"10px","margin-bottom":"10px"},attrs:{type:"textarea"},model:{value:t.dailySummary,callback:function(e){t.dailySummary=e},expression:"dailySummary"}}),t._v(" "),s("el-button",{staticStyle:{float:"right"},attrs:{type:"primary"},on:{click:t.saveDailySummary}},[t._v("提交")])],1)],1)],1)],1)},staticRenderFns:[]};var c=s("C7Lr")(i,o,!1,function(t){s("SMN8")},null,null);e.default=c.exports},SMN8:function(t,e){},V0EG:function(t,e){var s,a,n=t.exports={};function r(){throw new Error("setTimeout has not been defined")}function i(){throw new Error("clearTimeout has not been defined")}function o(t){if(s===setTimeout)return setTimeout(t,0);if((s===r||!s)&&setTimeout)return s=setTimeout,setTimeout(t,0);try{return s(t,0)}catch(e){try{return s.call(null,t,0)}catch(e){return s.call(this,t,0)}}}!function(){try{s="function"==typeof setTimeout?setTimeout:r}catch(t){s=r}try{a="function"==typeof clearTimeout?clearTimeout:i}catch(t){a=i}}();var c,u=[],l=!1,m=-1;function h(){l&&c&&(l=!1,c.length?u=c.concat(u):m=-1,u.length&&d())}function d(){if(!l){var t=o(h);l=!0;for(var e=u.length;e;){for(c=u,u=[];++m<e;)c&&c[m].run();m=-1,e=u.length}c=null,l=!1,function(t){if(a===clearTimeout)return clearTimeout(t);if((a===i||!a)&&clearTimeout)return a=clearTimeout,clearTimeout(t);try{a(t)}catch(e){try{return a.call(null,t)}catch(e){return a.call(this,t)}}}(t)}}function f(t,e){this.fun=t,this.array=e}function p(){}n.nextTick=function(t){var e=new Array(arguments.length-1);if(arguments.length>1)for(var s=1;s<arguments.length;s++)e[s-1]=arguments[s];u.push(new f(t,e)),1!==u.length||l||o(d)},f.prototype.run=function(){this.fun.apply(null,this.array)},n.title="browser",n.browser=!0,n.env={},n.argv=[],n.version="",n.versions={},n.on=p,n.addListener=p,n.once=p,n.off=p,n.removeListener=p,n.removeAllListeners=p,n.emit=p,n.prependListener=p,n.prependOnceListener=p,n.listeners=function(t){return[]},n.binding=function(t){throw new Error("process.binding is not supported")},n.cwd=function(){return"/"},n.chdir=function(t){throw new Error("process.chdir is not supported")},n.umask=function(){return 0}}});
//# sourceMappingURL=8.b9e195f2bf608d57ab6c.js.map