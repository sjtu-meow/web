<template>
<tr>
  <td>
    {{comment.id}} {{comment.deleted ? '（已删）' : ''}}
  </td>
  <td>{{comment.profile.nickname}}（{{comment.profile.id}}）</td>
  <td>
    {{comment.content.substring(0, contentPreviewLength)}}{{comment.content.length > contentPreviewLength ? '…' : ''}}
    <button v-if="comment.content.length > contentPreviewLength" type="button" class="btn btn-link btn-xs" @click="expandCommentContent">展开</button>
  </td>
  <td>{{itemTextualType}}</td>
  <td>{{comment.itemId}}</td>
  <td>
    <button v-if="comment.deleted" type="button" class="btn btn-primary btn-xs" @click="recoverComment">
      <span class="glyphicon glyphicon-ok"/>
    </button>
    <button v-else type="button" class="btn btn-danger btn-xs" @click="deleteComment">
      <span class="glyphicon glyphicon-remove"/>
    </button>
  </td>
</tr>
</template>

<script>
export default {
  name: 'CommentListRow',
  props: ['comment'],
  data() {
    return {
      contentPreviewLength: 50,
      item: {
        content: ''
      }
    }
  },
  computed: {
    itemTextualType() {
      let result = '';
      switch (this.comment.itemType) {
        case 0:
          result = '点滴';
          break;
        case 1:
          result = '文章';
          break;
        case 2:
          result = '问题';
          break;
        case 3:
          result = '回答';
          break;
        case 4:
          result = '评论';
          break;
        default:
          result = '未知类型';
      }
      return result;
    }
  },
  created() {

  },
  methods: {
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
