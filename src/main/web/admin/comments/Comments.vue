<template>
<section>
  <section class="content-header">
    <h1>评论管理</h1>
  </section>
  <section class="content">
    <div class="row">
      <div class="col-md-12">
        <comment-box @deleteComment="promptDeleteComment" @recoverComment="promptRecoverComment" @expandCommentContent="expandCommentContent" />
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <comment-report-box @deleteComment="promptDeleteComment" @recoverComment="promptRecoverComment" @expandCommentContent="expandCommentContent" />
      </div>
    </div>
  </section>

  <!-- Delete Modal -->
  <div class="modal fade" id="delete-comment-modal" tabindex="-1" role="dialog" aria-labelledby="delete-comment-modal-title">
    <div class="modal-dialog modal-sm" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="delete-comment-modal-title">删除评论</h4>
        </div>
        <div class="modal-body">
          <p class="text-danger">确定删除 <b>{{commentToDelete.profile.nickname}}</b> 的评论<b>「{{commentToDelete.content.substring(0, 30)}}{{commentToDelete.content.length > 30 ? '…' : ''}}」</b>吗？</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          <button type="button" class="btn btn-danger" @click="deleteComment">删除</button>
        </div>
      </div>
    </div>
  </div>

  <!-- Recover Modal -->
  <div class="modal fade" id="recover-comment-modal" tabindex="-1" role="dialog" aria-labelledby="recover-comment-modal-title">
    <div class="modal-dialog modal-sm" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="recover-comment-modal-title">删除评论</h4>
        </div>
        <div class="modal-body">
          <p>确定恢复 <b>{{commentToRecover.profile.nickname}}</b> 的评论<b>「{{commentToRecover.content.substring(0, 30)}}{{commentToRecover.content.length > 30 ? '…' : ''}}」</b>吗？</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          <button type="button" class="btn btn-primary" @click="recoverComment">恢复</button>
        </div>
      </div>
    </div>
  </div>

  <!-- Expand Content Modal -->
  <div class="modal fade" id="comment-content-detail-modal" tabindex="-1" role="dialog" aria-labelledby="comment-content-detail-modal-title">
    <div class="modal-dialog modal-sm" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="comment-content-detail-modal-title">评论全文</h4>
        </div>
        <div class="modal-body">
          <p v-for="line in commentToShow.content.split('\n')">
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
import CommentBox from './CommentBox.vue'
// import CommentReportBox from './CommentReportBox.vue'

export default {
  name: 'Comments',
  components: {
    CommentBox
    // CommentReportBox
  },
  data() {
    return {
      commentToDelete: {
        profile: {
          nickname: 'haha'
        },
        content: '+1s now'
      },
      commentToRecover: {
        profile: {
          nickname: 'haha'
        },
        content: '+1s now'
      },
      commentToShow: {
        profile: {
          nickname: 'haha'
        },
        content: '+1s now'
      }
    }
  },
  methods: {
    promptDeleteComment(comment) {
      this.commentToDelete = comment;
      $('#delete-comment-modal').modal('show');
    },
    deleteComment() {
      this.$http.delete('/api/admin/comments/' + this.commentToDelete.id)
        .then(function(response) {
          $('#delete-comment-modal').modal('hide');
          this.commentToDelete.deleted = true;
        }, function(response) {
          alert(response.body.message || '修改失败');
        })
    },
    promptRecoverComment(comment) {
      this.commentToRecover = comment;
      $('#recover-comment-modal').modal('show');
    },
    recoverComment() {
      this.$http.patch('/api/admin/comments/' + this.commentToRecover.id, {
        isDeleted: false
      }).then(function(response) {
        $('#recover-comment-modal').modal('hide');
        this.commentToRecover.deleted = false;
      }, function(response) {
        alert(response.body.message || '修改失败');
      })
    },
    expandCommentContent(comment) {
      this.commentToShow = comment;
      $('#comment-content-detail-modal').modal('show');
    }
  }
}
</script>
