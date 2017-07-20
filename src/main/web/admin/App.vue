<template>
<div id="app" class="wrapper" style="height: auto;">
  <nav-bar :loggedIn="loggedIn" @logout="logout"></nav-bar>
  <side-bar :loggedIn="loggedIn"></side-bar>
  <div class="content-wrapper" style="min-height: 320px;">
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
import Banners from './banners/Banners.vue'
import Pushes from './pushes/Pushes.vue'
import Comments from './comments/Comments.vue'

Vue.use(VueRouter);
Vue.use(VueResource);
Vue.use(Vue2Filters);

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
    },
    {
      path: '/banners',
      component: Banners
    },
    {
      path: '/pushes',
      component: Pushes,
    },
    {
      path: '/comments',
      component: Comments
    }
  ]
})

export default {
  name: 'app',
  router,
  components: {
    NavBar,
    SideBar
  },
  data() {
    return {
      loggedIn: true
    }
  },
  methods: {
    logout() {
      this.$http.delete('/api/web/auth')
        .then(function(response) {
          this.loggedIn = false;
          window.location = window.location.pathname;
        }, function(response) {
          alert(response.body.message || '退出失败');
        })
    }
  }
}
</script>
