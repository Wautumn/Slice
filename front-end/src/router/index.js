import Vue from 'vue'
import Router from 'vue-router'
import Menus from '@/config/header-config'
import menus from '@/config/menu-config'
import ProjectDetail from "../components/ProjectDetail";

Vue.use(Router)

var routes = [];
var croutes = [];


Menus.forEach(element => {
  routes.push({
    path: `/${element.componentName}`,
    name: element.componentName,
    component: () => import(`@/components/${element.componentName}`)
  })
});

menus.forEach(element => {
  element.sub.forEach((sub) => {
    croutes.push({
      path: `/${sub.componentName}`,
      name: sub.componentName,
      component: () => import(`@/components/${sub.componentName}`)
    })
  })
});

//二级路由
routes[2].children = croutes;

routes.push({
  path: `/Login`,
  name: 'Login',
  component: () => import(`@/components/Login`),
  // props: true,
})

routes.push({
  path: `/Reg`,
  name: 'Reg',
  component: () => import(`@/components/Register`),
  // props: true,
})

routes.push({
  path: `/`,
  name: 'FrontPage',
  component: () => import(`@/components/FrontPage`),
  // props: true,
})

routes.push({
  path: `/FeedBack`,
  name: 'FeedBack',
  component: () => import(`@/components/FeedBack`),
})

routes.push({
  path: `/FeedList`,
  name: 'FeedList',
  component: () => import(`@/components/FeedList`),
})


routes.push({
  path: `/Admin`,
  name: 'FeedHandle',
  component: () => import(`@/components/AdminFeedHandle`),
  children:[{
    path:'detail',
    name:'detail',
    component:ProjectDetail,
  }]
})

routes.push({
  path: `/MonthView`,
  name: 'MonthView',
  component: () => import(`@/components/MonthView1`),
})
routes.push({
  path: `/Project`,
  name: 'Project',
  component: () => import(`@/views/Project`),
})
routes.push({
  path: `/AddProject`,
  name: 'AddProject',
  component: () => import(`@/components/AddProject`),
})


routes.push({
  path: `/AnalyHis`,
  name: 'AnalyHis',
  component: () => import(`@/components/AnalyHis`),
})

export default new Router({
  routes
})
