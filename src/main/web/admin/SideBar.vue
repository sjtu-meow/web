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
    </ul>
    <!-- /.sidebar-menu -->

  </section>
  <!-- /.sidebar -->
</aside>
<!-- /.main-sidebar -->
</template>

<script>
export default {
  'name': 'SideBar',
  data() {
    return {
      'items': [{
          'name': '吧呢管理',
          'icon': 'fa-flag',
          'link': '/banners'
        },
        {
          'name': '推送管理',
          'icon': 'fa-arrow-circle-up',
          'link': '/pushes'
        },
        {
          'name': '用户管理',
          'icon': 'fa-user',
          'link': '/users'
        },
        {
          'name': '点滴管理',
          'icon': 'fa-tint',
          'link': '/moments'
        },
        {
          'name': '文章管理',
          'icon': 'fa-file-text',
          'link': '/articles'
        },
        {
          'name': '问答管理',
          'icon': 'fa-question',
          'link': '/questions'
        },
        {
          'name': '评论管理',
          'icon': 'fa-comments',
          'link': '/comments'
        }
      ]
    }
  }
}
</script>

<style>

</style>
