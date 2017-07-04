<template>
<section>
  <section class="content-header">
    <h1>所有点滴</h1>
  </section>
  <section class="content">
    <div class="table-responsive">
      <table class="table table-hover">
        <thead>
          <tr>
            <th class="col-md-1">#</th>
            <th class="col-md-1">用户</th>
            <th class="col-md-3">内容</th>
            <th class="col-md-6">媒体</th>
            <th class="col-md-1"></th>
          </tr>
        </thead>
        <tbody>
          <moment-list-row v-for="moment in moments" :key="moment.id" :moment="moment" @deleteMoment="promptDeleteMoment" />
        </tbody>
      </table>
    </div>
  </section>

  <!-- Delete Modal -->
  <div class="modal fade" id="delete-moment-modal" tabindex="-1" role="dialog" aria-labelledby="delete-moment-modal-title">
    <div class="modal-dialog modal-sm" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="delete-moment-modal-title">删除点滴</h4>
        </div>
        <div class="modal-body">
          <p class="text-danger">确定删除 <b>{{momentToDelete.profile.nickname}}</b> 的点滴（{{momentToDelete.content.substring(0, 10)}}）吗？</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          <button type="button" class="btn btn-danger" @click="deleteMoment">删除</button>
        </div>
      </div>
    </div>
  </div>
</section>
</template>

<script>
import MomentListRow from './MomentListRow.vue'

export default {
  name: 'Moments',
  components: {
    MomentListRow
  },
  data() {
    return {
      moments: [{
        "id": 1,
        "type": 0,
        "profile": {
          "nickname": "喵喵喵的伙伴",
          "bio": "Web 开发专家",
          "avatar": null,
          "id": 1
        },
        "comments": null,
        "likeCount": 0,
        "commentCount": 0,
        "content": "Rich gifts wax poor when givers prove unkind.",
        "medias": [{
          "id": 3,
          "type": "Image",
          "url": "https://i.ytimg.com/vi/prALrHUJ8Ns/hqdefault.jpg"
        }, {
          "id": 3,
          "type": "Image",
          "url": "https://i.ytimg.com/vi/prALrHUJ8Ns/hqdefault.jpg"
        }]
      }],
      momentToDelete: {
        profile: {
          nickname: 'haha'
        },
        content: '+1s now'
      }
    }
  },
  methods: {
    promptDeleteMoment(moment) {
      this.userToDelete = moment;
      $('#delete-moment-modal').modal('show');
    },
    deleteMoment() {
      const self = this;
      //TODO: finish delete moment ajax call
      $.ajax({
        url: '/',
        type: 'PUT',
        data: {},
        success: function(data) {
          if (data === true) {
            $('#delete-moment-modal').modal('hide');
            self.fetchUsers();
          }
        }
      });
    }
  }
}
</script>
