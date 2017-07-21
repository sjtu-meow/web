<template>
<tr>
  <td :class="{'text-muted': report.closed}">
    {{report.id}} {{report.closed ? '（已结束）' : ''}}
  </td>
  <td :class="{'text-muted': report.closed}">{{report.profile.nickname}}（{{report.profile.id}}）</td>
  <td :class="{'text-muted': report.closed}">{{report.reason}}</td>
  <td :class="{'text-muted': report.closed}">{{report.itemId}}</td>
  <td :class="{'text-muted': report.closed}">
    {{comment.content.substring(0, contentPreviewLength)}}{{comment.content.length > contentPreviewLength ? '…' : ''}}
    <button v-if="comment.content.length > contentPreviewLength" type="button" class="btn btn-link btn-xs" @click="expandCommentContent">展开</button>
  </td>
  <td>
    <template v-for="media in comment.medias">
      <img v-if="media.type === 'Image'" class="img-rounded" :src="media.url" style="height: 60px; padding-bottom: 4px">
      <video v-if="media.type === 'Video'" :src="media.url" style="height: 60px; padding-bottom: 4px" controls/>
    </template>
  </td>
  <td>
    <button v-if="comment.deleted" type="button" class="btn btn-primary btn-xs" @click="recoverComment">
      恢复评论
    </button>
    <button v-else type="button" class="btn btn-danger btn-xs" @click="deleteComment">
      删除评论
    </button>
    <button v-if="report.closed" type="button" class="btn btn-danger btn-xs" @click="openReport">
      重启举报
    </button>
    <button v-else type="button" class="btn btn-primary btn-xs" @click="closeReport">
      结束举报
    </button>
  </td>
</tr>
</template>

<script>
export default {
  name: 'CommentReportListRow',
  props: ['report'],
  data() {
    return {
      contentPreviewLength: 50,
      comment: {
        title: '',
        content: '',
        profile: {
          nickname: ''
        }
      }
    }
  },
  created() {
    this.$http.get('/api/admin/comments/' + this.report.itemId)
      .then(function(response) {
        this.comment = response.body;
      }, function(response) {
        alert(response.body.message || '获取评论失败');
      })
  },
  methods: {
    closeReport() {
      this.$emit('closeReport', this.report)
    },
    openReport() {
      this.$emit('openReport', this.report)
    },
    deleteComment() {
      this.$emit('deleteComment', this.comment)
    },
    recoverComment() {
      this.$emit('recoverComment', this.comment)
    },
    expandCommentContent() {
      this.$emit('expandCommentContent', this.comment)
    }
  }
}
</script>
