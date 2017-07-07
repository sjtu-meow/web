<template>
<div id="app" class="wrapper" style="height: auto;">
  <nav-bar></nav-bar>
  <p>{{test}}</p>
  <side-bar></side-bar>
  <div class="content-wrapper" style="min-height: 320px; background-color: #ffffff;">
    <router-view></router-view>
  </div>
</div>
</template>

<script>
import Vue from 'vue'
import VueRouter from 'vue-router'
import VueResource from 'vue-resource'
import Vue2Filters from 'vue2-filters'
import NavBar from './NavBar.vue'
import SideBar from './SideBar.vue'
import Users from './users/Users.vue'
import Moments from './moments/Moments.vue'
import Articles from './articles/Articles.vue'
import Questions from './questions/Questions.vue'

Vue.use(VueRouter)
Vue.use(VueResource);
Vue.use(Vue2Filters)

const router = new VueRouter({
  routes: [{
      path: '/users',
      component: Users
    },
    {
      path: '/moments',
      component: Moments
    },
    {
      path: '/articles',
      component: Articles
    },
    {
      path: '/questions',
      component: Questions
    }
  ]
})

export default {
  name: 'app',
  data() {
    return {
      test: 'hahaha'
    }
  },
  router,
  components: {
    NavBar,
    SideBar
  }
}
</script>
