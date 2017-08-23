<template>
<div id="app" class="wrapper" style="height: auto;">
  <nav-bar @logout="logout"></nav-bar>
  <side-bar></side-bar>
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
  created() {
    this.$http.get('/api/web/auth')
      .then(function(response) {
        if (response.body.loggedIn === false) {
          alert('请先登录')
          window.location.href = '/';
        } else if (response.body.admin === false) {
          alert('权限不足')
          window.location.href = '/';
        }
      }, function(response) {
        alert(response.body.message || '获取登录状态失败');
        window.location.href = '/';
      })
  },
  methods: {
    logout() {
      this.$http.delete('/api/web/auth')
        .then(function(response) {
          window.location = '/';
        }, function(response) {
          alert(response.body.message || '退出失败');
        })
    }
  }
}
</script>
