<template>
<tr>
  <!-- TODO: update attribute name -->
  <td>{{user.id}} {{user.admin ? '🤓' : ''}} {{user.deleted ? '（已删）' : ''}}</td>
  <td>
    <img class="img-circle" :src="user.profile.avatar ? user.profile.avatar : 'https://pbs.twimg.com/profile_images/625633822235693056/lNGUneLX.jpg'"
      height="40px" />
  </td>
  <td>
    <template v-if="editingNickname">
      <div class="input-group input-group-sm">
        <input type="text" class="form-control" v-model="newNickname">
        <span class="input-group-btn">
        <button class="btn btn-default" @click="updateNickname">
            <span class="glyphicon glyphicon-refresh"></span>
        </button>
        </span>
      </div>
    </template>
    <template v-else>
      {{user.profile.nickname}}
      <button type="button" class="btn btn-default btn-xs" @click="editingNickname = true">
        <span class="glyphicon glyphicon-edit"/>
      </button>
    </template>
  </td>
  <td>
    <template v-if="editingBio">
      <div class="input-group input-group-sm">
        <input type="text" class="form-control" v-model="newBio">
        <span class="input-group-btn">
        <button class="btn btn-default" @click="updateBio">
            <span class="glyphicon glyphicon-refresh"></span>
        </button>
        </span>
      </div>
    </template>
    <template v-else>
      {{user.profile.bio}}
      <button type="button" class="btn btn-default btn-xs" @click="editingBio = true">
        <span class="glyphicon glyphicon-edit"/>
      </button>
    </template>
  </td>
  <td>
    {{user.phone}}
  </td>
  <td>
    <template v-if="editingPassword">
      <div class="input-group input-group-sm">
        <input type="password" class="form-control" v-model="newPassword">
        <span class="input-group-btn">
        <button class="btn btn-default" @click="updatePassword">
            <span class="glyphicon glyphicon-refresh"></span>
        </button>
        </span>
      </div>
    </template>
    <template v-else>
      ****
      <button type="button" class="btn btn-default btn-xs" @click="editingPassword = true">
        <span class="glyphicon glyphicon-edit"/>
      </button>
    </template>
  </td>
  <td>
    <button v-if="user.admin" type="button" class="btn btn-default btn-xs" @click="unsetAdmin">
      <span class="glyphicon glyphicon-eye-close"/>
    </button>
    <button v-else type="button" class="btn btn-default btn-xs" @click="setAdmin">
      <span class="glyphicon glyphicon-eye-open"/>
    </button>

    <button v-if="user.deleted" type="button" class="btn btn-primary btn-xs" @click="recoverUser">
      <span class="glyphicon glyphicon-ok"/>
    </button>
    <button v-else type="button" class="btn btn-danger btn-xs" @click="deleteUser">
      <span class="glyphicon glyphicon-remove"/>
    </button>
  </td>
</tr>
</template>

<script>
export default {
  name: 'UserListRow',
  props: ['user'],
  data: function() {
    return {
      editingNickname: false,
      editingBio: false,
      editingPassword: false,
      newNickname: '',
      newBio: '',
      newPassword: ''
    }
  },
  methods: {
    updateNickname(event) {
      //TODO: change url
      this.$http.patch('http://106.14.156.19/api/admin/users/' + this.user.id, {
        nickname: this.newNickname
      }).then(function (response) {
        this.user.profile.nickname = this.newNickname;
        this.editingNickname = false;
      }, function (response) {
        alert(response.body.message || '修改失败')
      })
    },
    updateBio(event) {
      //TODO: change url
      this.$http.patch('http://106.14.156.19/api/admin/users/' + this.user.id, {
        bio: this.newBio
      }).then(function (response) {
        this.user.profile.bio = this.newBio;
        this.editingBio = false;
      }, function (response) {
        alert(response.body.message || '修改失败')
      })
    },
    updatePassword(event) {
      //TODO: change url
      this.$http.patch('http://106.14.156.19/api/admin/users/' + this.user.id, {
        password: this.newPassword
      }).then(function (response) {
        this.editingPassword = false;
      }, function (response) {
        alert(response.body.message || '修改失败')
      })
    },
    deleteUser(event) {
      this.$emit('deleteUser', this.user)
    },
    recoverUser(event) {
      this.$emit('recoverUser', this.user)
    },
    setAdmin(event) {
      this.$emit('setAdminUser', this.user)
    },
    unsetAdmin(event) {
      this.$emit('unsetAdminUser', this.user)
    }
  }
}
</script>
