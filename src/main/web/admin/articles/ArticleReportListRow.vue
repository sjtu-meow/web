<template>
<tr>
  <td>
    {{report.id}} {{report.closed ? '（已关闭）' : ''}}
  </td>
  <td>{{report.profile.nickname}}（{{report.profile.id}}）</td>
  <td>{{report.reason}}</td>
  <td>{{report.itemId}}</td>
  <td>
    {{plainContent.substring(0, contentPreviewLength)}}{{plainContent.length > contentPreviewLength ? '…' : ''}}
    <button type="button" class="btn btn-link btn-xs" @click="expandContent">查看详情</button>
  </td>
  <td>
    <button v-if="article.deleted" type="button" class="btn btn-primary btn-xs" @click="recoverArticle">
      <span class="glyphicon glyphicon-ok"/> 恢复文章
    </button>
    <button v-else type="button" class="btn btn-danger btn-xs" @click="deleteArticle">
      <span class="glyphicon glyphicon-remove"/> 删除文章
    </button>
    <button v-if="report.closed" type="button" class="btn btn-primary btn-xs" @click="openReport">
      <span class="glyphicon glyphicon-ok"/> 开启举报
    </button>
    <button v-else type="button" class="btn btn-danger btn-xs" @click="closeReport">
      <span class="glyphicon glyphicon-remove"/> 关闭举报
    </button>
  </td>
</tr>
</template>

<script>
export default {
  name: 'ArticleReportListRow',
  props: ['report'],
  data() {
    return {
      contentPreviewLength: 100,
      article: {
        title: '',
        content: '',
        profile: {
          nickname: ''
        }
      }
    }
  },
  computed: {
    plainContent() {
      return $(this.article.content).text()
    }
  },
  created() {
    this.$http.get('/api/admin/articles/' + this.report.itemId)
      .then(function(response) {
        this.article = response.body;
      }, function(response) {
        alert(response.body.message || '获取文章失败');
      })
  },
  methods: {
    closeReport() {
      this.$emit('closeReport', this.report)
    },
    openReport() {
      this.$emit('openReport', this.report)
    },
    deleteArticle() {
      this.$emit('deleteArticle', this.article)
    },
    recoverArticle() {
      this.$emit('recoverArticle', this.article)
    },
    expandContent() {
      this.$emit('expandContent', this.article)
    }
  }
}
</script>
