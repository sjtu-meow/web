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
            <th class="col-md-1">头像</th>
            <th class="col-md-2">昵称</th>
            <th class="col-md-3">签名</th>
            <th class="col-md-2">手机</th>
            <th class="col-md-2">密码</th>
            <th class="col-md-1"></th>
          </tr>
        </thead>
        <tbody>
          <user-list-row v-for="user in users" :key="user.id" :user="user"
            @deleteUser="promptDeleteUser" @recoverUser="promptRecoverUser"
            @setAdminUser="promptSetAdminUser" @unsetAdminUser="promptUnsetAdminUser" />
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
                  <label class="control-label" for="new-user-avatar">头像</label><span class="text-muted">（点击图片上传）</span>
                  <a class="thumbnail" @click="triggerAvatarInputClick">
                    <!-- TODO: add default image url -->
                    <img src="https://i.ytimg.com/vi/prALrHUJ8Ns/hqdefault.jpg">
                  </a>
                  <input type="file" name="file" id="avatarInput" style="display: none;" @change="uploadPicture" />
                </div>
              </form>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          <button type="button" class="btn btn-primary" @click="addUser">添加</button>
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
          <p class="text-danger">确定删除此用户 <b>{{userToDelete.profile.nickname}}（{{userToDelete.phone}}）</b> 吗？</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          <button type="button" class="btn btn-danger" @click="deleteUser">删除</button>
        </div>
      </div>
    </div>
  </div>

  <!-- Recover Modal -->
  <div class="modal fade" id="recover-user-modal" tabindex="-1" role="dialog" aria-labelledby="recover-user-modal-title">
    <div class="modal-dialog modal-sm" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="recover-user-modal-title">删除用户</h4>
        </div>
        <div class="modal-body">
          <p class="text-danger">确定删除此用户 <b>{{userToRecover.profile.nickname}}（{{userToRecover.phone}}）</b> 吗？</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          <button type="button" class="btn btn-danger" @click="recoverUser">恢复</button>
        </div>
      </div>
    </div>
  </div>

  <!-- Set Admin Modal -->
  <div class="modal fade" id="set-admin-user-modal" tabindex="-1" role="dialog" aria-labelledby="set-admin-user-modal-title">
    <div class="modal-dialog modal-sm" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="set-admin-user-modal-title">设置管理员</h4>
        </div>
        <div class="modal-body">
          <p>确定设置此用户 <b>{{userToSetAdmin.profile.nickname}}（{{userToSetAdmin.phone}}）</b> 为管理员吗？</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          <button type="button" class="btn btn-primary" @click="setAdminUser">设置</button>
        </div>
      </div>
    </div>
  </div>

  <!-- Unset Admin Modal -->
  <div class="modal fade" id="unset-admin-user-modal" tabindex="-1" role="dialog" aria-labelledby="unset-admin-user-modal-title">
    <div class="modal-dialog modal-sm" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="unset-admin-user-modal-title">撤销管理员</h4>
        </div>
        <div class="modal-body">
          <p>确定撤销此用户 <b>{{userToUnsetAdmin.profile.nickname}}（{{userToUnsetAdmin.phone}}）</b> 管理员权限吗？</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
          <button type="button" class="btn btn-primary" @click="unsetAdminUser">撤销</button>
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
      users: [],
      newUserNickname: '',
      newUserPassword: '',
      newUserPhone: '',
      newUserisAdmin: false,
      userToDelete: {
        id: 1,
        phone: '13512341234',
        profile: {
          nickname: 'haha'
        }
      },
      userToRecover: {
        id: 1,
        phone: '13512341234',
        profile: {
          nickname: 'haha'
        }
      },
      userToSetAdmin: {
        id: 1,
        phone: '13512341234',
        profile: {
          nickname: 'haha'
        }
      },
      userToUnsetAdmin: {
        id: 1,
        phone: '13512341234',
        profile: {
          nickname: 'haha'
        }
      }
    }
  },
  created: function() {
    this.fetchUsers()
  },
  methods: {
    fetchUsers: function() {
      //TODO: change url
      this.$http.get('http://106.14.156.19/api/admin/users')
        .then(function(response) {
          this.users = response.body;
        }, function (response) {
          alert(response.body.message || '获取用户失败');
        })
    },
    promptAddUser: function(event) {
      $('#add-user-modal').modal('show');
    },
    addUser() {
      //TODO: change url
      this.$http.post('http://106.14.156.19/api/admin/users', {
        phone: this.newUserPhone,
        password: this.newUserPassword,
        nickname: this.newUserNickname,
        admin: this.newUserisAdmin
      }).then(function(response) {
        this.fetchUsers();
        $('#add-user-modal').modal('hide');
      }, function(response) {
        alert(response.body.message)
      });
    },
    promptDeleteUser: function(user) {
      this.userToDelete = user;
      $('#delete-user-modal').modal('show');
    },
    deleteUser: function(event) {
      //TODO: change url and test this function call
      this.$http.delete('http://106.14.156.19/api/admin/users/' + this.userToDelete.id)
        .then(function(response) {
          this.userToDelete.deleted = true;
          $('#delete-user-modal').modal('hide');
        }, function(response) {
          alert(response.body.message || '修改失败');
        })
    },
    promptRecoverUser(user) {
      this.userToRecover = user;
      $('#recover-user-modal').modal('show');
    },
    recoverUser() {
      //TODO: change url and test implementation
      this.$http.put('http://106.14.156.19/api/admin/users/' + this.userToRecover.id + '/recover')
        .then(function(response) {
          this.userToRecover.deleted = false;
          $('#recover-user-modal').modal('hide');
        }, function(response) {
          alert(response.body.message || '修改失败');
        })
    },
    promptSetAdminUser(user) {
      this.userToSetAdmin = user;
      $('#set-admin-user-modal').modal('show');
    },
    setAdminUser() {
      //TODO: change url
      this.$http.patch('http://106.14.156.19/api/admin/users/' + this.userToSetAdmin.id, {
        admin: true
      }).then(function (response) {
        this.userToSetAdmin.admin = true;
        $('#set-admin-user-modal').modal('hide');
      }, function (response) {
        alert(response.body.message || '修改失败')
      })
    },
    promptUnsetAdminUser(user) {
      this.userToUnsetAdmin = user;
      $('#unset-admin-user-modal').modal('show');
    },
    unsetAdminUser() {
      //TODO: change url
      this.$http.patch('http://106.14.156.19/api/admin/users/' + this.userToUnsetAdmin.id, {
        admin: false
      }).then(function (response) {
        this.userToUnsetAdmin.admin = false;
        $('#unset-admin-user-modal').modal('hide');
      }, function (response) {
        alert(response.body.message || '修改失败')
      })
    },
    triggerAvatarInputClick: function() {
      $('#avatarInput').click();
    },
    uploadPicture: function(event) {
      let data = new FormData();
      data.append('file', event.target.files[0])
      //TODO: change the way of uploading avatar image
    }
  }
}
</script>
