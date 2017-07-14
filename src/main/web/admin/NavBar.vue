<template>
  <header class="main-header">
    <a href="#" class="logo">
      <!-- LOGO -->
      <span class="logo-lg"><b>喵喵喵的管理平台</b></span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top" role="navigation">
      <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a>
      <!-- Navbar Right Menu -->
      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
          <li>
            <a v-if="loggedIn" @click="logout">退出登录</a>
            <a v-else @click="login">登录</a>
          </li>
        </ul>
      </div>
    </nav>
  </header>
</template>

<script>
export default {
  name: 'NavBar',
  props: ['loggedIn'],
  methods: {
    logout() {
      this.$emit('logout');
    },
    login() {
      this.$emit('login');
    }
  }
}
</script>
