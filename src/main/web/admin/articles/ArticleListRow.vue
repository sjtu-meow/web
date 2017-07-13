<template>
<tr>
  <td>{{article.id}}</td>
  <td>{{article.profile.nickname}}（{{article.profile.id}}）</td>
  <td>{{article.title}}</td>
  <td>
    {{plainContent.substring(0, contentPreviewLength)}}{{plainContent.length > contentPreviewLength ? '…' : ''}}
    <button type="button" class="btn btn-link btn-xs" @click="expandContent">查看详情</button>
  </td>
  <td>
    <button v-if="article.deleted" type="button" class="btn btn-primary btn-xs" @click="recoverArticle">
      <span class="glyphicon glyphicon-ok"/>
    </button>
    <button v-else type="button" class="btn btn-danger btn-xs" @click="deleteArticle">
      <span class="glyphicon glyphicon-remove"/>
    </button>
  </td>
</tr>
</template>

<script>
export default {
  name: 'ArticleListRow',
  props: ['article'],
  data() {
    return {
      contentPreviewLength: 100
    }
  },
  methods: {
    deleteArticle() {
      this.$emit('deleteArticle', this.article)
    },
    recoverArticle() {
      this.$emit('recoverArticle', this.article)
    },
    expandContent() {
      this.$emit('expandContent', this.article)
    }
  },
  computed: {
    plainContent() {
      return $(this.article.content).text()
    }
  }
}
</script>
