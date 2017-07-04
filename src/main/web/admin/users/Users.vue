<template>
<section>
  <section class="content-header">
    <h1>所有用户</h1>
  </section>
  <section class="content">
    <div class="table-responsive">
      <table class="table table-hover">
        <thead>
          <tr>
            <th class="col-md-1">
              <button class="btn btn-default btn-xs" data-toggle="modal" data-target="#add-user-modal">
                <span class="glyphicon glyphicon-plus"/>
              </button>
            </th>
            <th class="col-md-3">昵称</th>
            <th class="col-md-3">手机</th>
            <th class="col-md-4">密码</th>
            <th class="col-md-1"></th>
          </tr>
        </thead>
        <tbody>
          <user-list-row v-for="user in users" :key="user.id" :user="user" @deleteUser="promptDeleteUser" />
        </tbody>
      </table>
    </div>
  </section>

  <!-- Add Modal -->
  <div class="modal fade" id="add-user-modal" tabindex="-1" role="dialog" aria-labelledby="add-user-modal-title">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="add-user-modal-title">添加用户</h4>
        </div>
        <div class="modal-body">
          <div class="row">
            <form class="col-md-8">
              <div class="form-group">
                <label class="control-label" for="new-user-phone">手机</label>
                <input type="number" class="form-control" id="new-user-phone" v-model="newUserPhone" required/>
                <span class="help-block"></span>
              </div>
              <div class="form-group">
                <label class="control-label " for="new-user-nickname">昵称</label>
                <input type="text" class="form-control" id="new-user-nickname" v-model="newUserNickname" required>
                <span class="help-block"></span>
              </div>
              <div class="form-group">
                <label class="control-label " for="new-user-password">密码</label>
                <input type="password" class="form-control" id="new-user-password" v-model="newUserPassword" required>
                <span class="help-block"></span>
              </div>
              <div class="checkbox">
                <label>
                    <input type="checkbox" v-model="newUserisAdmin"/> 管理员权限
                  </label>
              </div>
            </form>
            <div class="col-md-4">
              <form action="/image/avatar" method="post" enctype="multipart/form-data">
                <div class="form-group">
                  <label class="control-label" for="new-user-avatar">头像</label>
                  <a class="thumbnail" @click="triggerAvatarInputClick">
                    <!-- TODO: add default image url -->
                    <img src="https://i.ytimg.com/vi/prALrHUJ8Ns/hqdefault.jpg">
                  </a>
                  <input type="file" name="file" id="avatarInput" style="display: none;" @change="uploadPicture" />
                  <span class="help-block">点击图片上传</span>
                </div>
              </form>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          <button type="button" class="btn btn-primary">添加</button>
        </div>
      </div>
    </div>
  </div>

  <!-- Delete Modal -->
  <div class="modal fade" id="delete-user-modal" tabindex="-1" role="dialog" aria-labelledby="delete-user-modal-title">
    <div class="modal-dialog modal-sm" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="delete-user-modal-title">删除用户</h4>
        </div>
        <div class="modal-body">
          <p class="text-danger">确定删除此用户 <b>{{userToDelete.nickname}}（{{userToDelete.phone}}）</b> 吗？</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          <button type="button" class="btn btn-danger" @click="deleteUser">删除</button>
        </div>
      </div>
    </div>
  </div>
</section>
</template>

<script>
import UserListRow from './UserListRow.vue'

export default {
  name: 'Users',
  components: {
    UserListRow
  },
  data: function() {
    return {
      //TODO: remove default user
      users: [{
        id: 1,
        nickname: 'haha',
        phone: '13512341234'
      }],
      newUserNickname: '',
      newUserPassword: '',
      newUserPhone: '',
      newUserisAdmin: false,
      userToDelete: {
        id: 1,
        nickname: 'haha',
        phone: '13512341234'
      }
    }
  },
  created: function() {
    this.fetchUsers()
  },
  methods: {
    fetchUsers: function() {
      const self = this;
      //TODO: finish fetch users list ajax call
      $.ajax({
        url: '/',
        type: 'PUT',
        data: {},
        success: function(data) {
          if (data === true) {
            // self.users = ...
          }
        }
      });
    },
    promptAddUser: function(event) {
      $('#add-user-modal').modal('show');
    },
    promptDeleteUser: function(user) {
      this.userToDelete = user;
      $('#delete-user-modal').modal('show');
    },
    deleteUser: function(event) {
      const self = this;
      //TODO: finish delete user ajax call
      $.ajax({
        url: '/',
        type: 'PUT',
        data: {},
        success: function(data) {
          if (data === true) {
            $('#delete-user-modal').modal('hide');
            self.fetchUsers();
          }
        }
      });
    },
    triggerAvatarInputClick: function() {
      $('#avatarInput').click();
    },
    uploadPicture: function(event) {
      let data = new FormData();
      data.append('file', event.target.files[0])

      $.ajax({
        url: "/image/avatar",
        type: "POST",
        data: data,
        contentType: false,
        cache: false,
        processData: false,
        success: function(data) {
          if (data === true) {
            window.location.reload()
          }
        }
      });
    }
  }
}
</script>
