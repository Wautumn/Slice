// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import VueResource from 'vue-resource'
import VCharts from 'v-charts'
import Vuetify from 'vuetify'
import DaySpanVuetify from 'dayspan-vuetify'

import 'vuetify/dist/vuetify.min.css'
import 'material-design-icons-iconfont/dist/material-design-icons.css'
import 'dayspan-vuetify/dist/lib/dayspan-vuetify.min.css'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import iView from 'iview';
import 'iview/dist/styles/iview.css';
import 'dhtmlx-gantt/codebase/dhtmlxgantt.css'
import 'dhtmlx-gantt/codebase/dhtmlxgantt'
import "../static/api"
import "../static/demo-3"
import "../static/html5"
import "../static/rAF"
import "../static/EasePack.min"
import "../static/TweenLite.min"


Vue.config.productionTip = false

Vue.use(Vuetify)
Vue.use(DaySpanVuetify, {
  methods: {
    getDefaultEventColor: () => '#1976d2'
  }
})
Vue.config.productionTip = false

Vue.use(ElementUI);
Vue.use(VueResource);
Vue.use(iView);
Vue.use(VCharts)


/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
