import Vue from 'vue'
import Router from 'vue-router'
//import Menus from '@/config/header-config'
import menus from '@/config/menu-config'
import ProjectDetail from "../components/ProjectDetail";

Vue.use(Router)

var routes = [];
var croutes = [];


// Menus.forEach(element => {
//   routes.push({
//     path: `/${element.componentName}`,
//     name: element.componentName,
//     component: () => import(`@/components/${element.componentName}`)
//   })
// });

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
//routes[2].children = croutes;
routes.push({
  path: `/TaskMode`,
  name: 'TaskMode',
  component: () => import(`@/components/TaskMode`),

})


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
  name: 'Welcome',
  component: () => import(`@/components/Welcome`),
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
  name: 'Admin',
  component: () => import(`@/components/Admin`),
  // path: `/Admin`,
  // name: 'FeedHandle',
  // component: () => import(`@/components/AdminFeedHandle`),
  children:[{
    path:'detail',
    name:'detail',
    component:ProjectDetail,
  }]
})

routes.push({
  path: `/Month`,
  name: 'Month',
  component: () => import(`@/components/Month`),
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
  path: `/WeekAdvice`,
  name: 'WeekAdvice',
  component: () => import(`@/components/WeekAdvice`),


})

routes.push({
  path: `/Gantt`,
  name: 'gantt',
  component: () => import(`@/components/gantt`),
})

routes.push({
  path: `/TodoList`,
  name: 'TodoList',
  component: () => import(`@/components/TodoList`),

  
}),
routes.push({
  path: `/Recom`,
  name: 'Recom',
  component: () => import(`@/components/Recom`),

  
}),
routes.push({
  path: `/Empty`,
  name: 'Empty',
  component: () => import(`@/components/Empty`),

  
}),

routes.push({
  path: `/newMonthView`,
  name: 'newMonthView',
  component: () => import(`@/components/newMonthView`),

  
})

export default new Router({
  routes
})
