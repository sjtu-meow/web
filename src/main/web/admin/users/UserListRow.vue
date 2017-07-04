<template>
<tr>
  <!-- TODO: update attribute name -->
  <td>{{user.id}}</td>
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
      {{user.nickname}}
      <button type="button" class="btn btn-default btn-xs" @click="editingNickname = true">
        <span class="glyphicon glyphicon-edit"/>
      </button>
    </template>
  </td>
  <td>
    <template v-if="editingPhone">
      <div class="input-group input-group-sm">
        <input type="text" class="form-control" v-model="newPhone">
        <span class="input-group-btn">
        <button class="btn btn-default" @click="updatePhone">
            <span class="glyphicon glyphicon-refresh"></span>
        </button>
        </span>
      </div>
    </template>
    <template v-else>
      {{user.phone}}
      <button type="button" class="btn btn-default btn-xs" @click="editingPhone = true">
          <span class="glyphicon glyphicon-edit"/>
      </button>
    </template>
  </td>
  <td>
    <template v-if="editingPassword">
      <div class="input-group input-group-sm">
        <input type="text" class="form-control" v-model="newPassword">
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
    <button type="button" class="btn btn-danger btn-xs" @click="deleteUser">
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
      editingPhone: false,
      editingPassword: false,
      newNickname: '',
      newPhone: '',
      newPassword: ''
    }
  },
  methods: {
    updateNickname: function(event) {
      const self = this;
      $.ajax({
        url: '/',
        type: 'PUT',
        data: {},
        success: function(data) {
          if (data === true) {
            self.editingNickname = false;
            self.user.nickname = self.newNickname;
            self.newNickname = '';
          }
        }
      });
    },
    updatePhone: function(event) {
      const self = this;
      $.ajax({
        url: '/',
        type: 'PUT',
        data: {},
        success: function(data) {
          if (data === true) {
            self.editingPhone = false;
            self.user.phone = self.newPhone;
            self.newPhone = '';
          }
        }
      });
    },
    updatePassword: function(event) {
      const self = this;
      $.ajax({
        url: '/',
        type: 'PUT',
        data: {},
        success: function(data) {
          if (data === true) {
            self.editingPassword = false;
            self.user.password = self.newPassword;
            self.newPassword = '';
          }
        }
      });
    },
    deleteUser: function(event) {
      this.$emit('deleteUser', this.user)
    }
  }
}
</script>
