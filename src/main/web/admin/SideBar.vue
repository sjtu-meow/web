<template>
  <aside class="main-sidebar">
    <!-- Inner sidebar -->
    <section class="sidebar">
      <!-- Sidebar Menu -->
      <ul class="sidebar-menu">
        <router-link activeClass="active" :to="item.link" tag="li" v-for="item in items" :key="item.name">
          <a>
            <i class="fa" v-bind:class="item.icon"></i><span>{{item.name}}</span>
          </a>
        </router-link>
      </ul><!-- /.sidebar-menu -->

    </section><!-- /.sidebar -->
  </aside><!-- /.main-sidebar -->
</template>

<script>
  export default {
    'name': 'SideBar',
    data() {
      return {
        'items': [
          {'name': '用户管理', 'icon': 'fa-user', 'link': '/users'},
        ]
      }
    }
  }
</script>

<style>

</style>
