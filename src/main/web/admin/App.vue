<template>
<div id="app" class="wrapper" style="height: auto;">
  <nav-bar :loggedIn="loggedIn" @login="promptLogin" @logout="logout"></nav-bar>
  <side-bar :loggedIn="loggedIn"></side-bar>
  <div class="content-wrapper" style="min-height: 320px; background-color: #ffffff;">
    <router-view></router-view>
  </div>

  <!-- login modal -->
  <div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="loginModalTitle">
    <div class="modal-dialog modal-sm" role="document">
      <div class="modal-content">
        <!-- title -->
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="loginModalTitle">登录</h4>
        </div>

        <!-- form body -->
        <div class="modal-body">
          <form>
            <div class="form-group">
              <label for="phone">手机号</label>
              <input type="text" class="form-control" id="phone" placeholder="手机号" v-model="phone">
            </div>
            <div class="form-group">
              <label for="password">密码</label>
              <input type="password" class="form-control" id="password" placeholder="密码" v-model="password">
            </div>
          </form>
        </div>

        <!-- footer button -->
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          <button type="button" class="btn btn-primary" @click="login">登录</button>
        </div>
      </div>
    </div>
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
      loggedIn: false,
      phone: '',
      password: ''
    }
  },
  created() {
    this.$http.get('/api/web/auth')
      .then(function (response) {
        if (response.body.loggedIn) {
          this.loggedIn = true;
        } else {
          this.loggedIn = false;
        }
      })
  },
  methods: {
    promptLogin() {
      $('#loginModal').modal('show');
    },
    login() {
      // TODO: change to admin API
      this.$http.post('/api/web/auth', {
        phone: this.phone,
        password: this.password
      }).then(function(response) {
        this.loggedIn = true;
        $('#loginModal').modal('hide')
      }, function(response) {
        alert(response.body.message || '登录失败');
      });
    },
    logout() {
      // TODO: change to admin API
      this.$http.delete('/api/web/auth')
        .then(function(response) {
          this.loggedIn = false;
        }, function(response) {
          alert(response.body.message || '退出失败');
        })
    }
  }
}
</script>
