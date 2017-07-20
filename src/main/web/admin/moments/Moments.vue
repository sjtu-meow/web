<template>
<section>
  <section class="content-header">
    <h1>点滴管理</h1>
  </section>
  <section class="content">
    <div class="row">
      <div class="col-md-12">
        <moment-box @deleteMoment="promptDeleteMoment" @recoverMoment="promptRecoverMoment" @expandContent="expandContent" />
      </div>
    </div>

    <!-- <div class="row">
      <div class="col-md-12">
        <moment-report-box @deleteMoment="promptDeleteMoment" @recoverMoment="promptRecoverMoment" @expandContent="expandContent" />
      </div>
    </div> -->
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
          <p class="text-danger">确定删除 <b>{{momentToDelete.profile.nickname}}</b> 的点滴<b>「{{momentToDelete.content.substring(0, 30)}}{{momentToDelete.content.length > 30 ? '…' : ''}}」</b>吗？</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          <button type="button" class="btn btn-danger" @click="deleteMoment">删除</button>
        </div>
      </div>
    </div>
  </div>

  <!-- Recover Modal -->
  <div class="modal fade" id="recover-moment-modal" tabindex="-1" role="dialog" aria-labelledby="recover-moment-modal-title">
    <div class="modal-dialog modal-sm" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="recover-moment-modal-title">删除点滴</h4>
        </div>
        <div class="modal-body">
          <p>确定恢复 <b>{{momentToRecover.profile.nickname}}</b> 的点滴<b>「{{momentToRecover.content.substring(0, 30)}}{{momentToRecover.content.length > 30 ? '…' : ''}}」</b>吗？</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          <button type="button" class="btn btn-primary" @click="recoverMoment">恢复</button>
        </div>
      </div>
    </div>
  </div>

  <!-- Expand Content Modal -->
  <div class="modal fade" id="moment-content-detail-modal" tabindex="-1" role="dialog" aria-labelledby="moment-content-detail-modal-title">
    <div class="modal-dialog modal-sm" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="moment-content-detail-modal-title">点滴全文</h4>
        </div>
        <div class="modal-body">
          <p v-for="line in momentToShow.content.split('\n')">
            {{line}}
          </p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        </div>
      </div>
    </div>
  </div>
</section>
</template>

<script>
import MomentBox from './MomentBox.vue'

export default {
  name: 'Moments',
  components: {
    MomentBox
  },
  data() {
    return {
      momentToDelete: {
        profile: {
          nickname: 'haha'
        },
        content: '+1s now'
      },
      momentToRecover: {
        profile: {
          nickname: 'haha'
        },
        content: '+1s now'
      },
      momentToShow: {
        profile: {
          nickname: 'haha'
        },
        content: '+1s now'
      }
    }
  },
  methods: {
    promptDeleteMoment(moment) {
      this.momentToDelete = moment;
      $('#delete-moment-modal').modal('show');
    },
    deleteMoment() {
      this.$http.delete('/api/admin/moments/' + this.momentToDelete.id)
        .then(function(response) {
          $('#delete-moment-modal').modal('hide');
          this.momentToDelete.deleted = true;
        }, function(response) {
          alert(response.body.message || '修改失败');
        })
    },
    promptRecoverMoment(moment) {
      this.momentToRecover = moment;
      $('#recover-moment-modal').modal('show');
    },
    recoverMoment() {
      this.$http.patch('/api/admin/moments/' + this.momentToRecover.id, {
        isDeleted: false
      }).then(function(response) {
        $('#recover-moment-modal').modal('hide');
        this.momentToRecover.deleted = false;
      }, function(response) {
        alert(response.body.message || '修改失败');
      })
    },
    expandContent(moment) {
      this.momentToShow = moment;
      $('#moment-content-detail-modal').modal('show');
    }
  }
}
</script>
