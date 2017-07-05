<template>
<tr>
  <td>{{article.id}}</td>
  <td>{{article.profile.nickname}}（{{article.profile.id}}）</td>
  <td>{{article.content.substring(0, 200)}}</td>
  <td>
    <button v-if="article.deleted" type="button" class="btn btn-primary btn-xs" @click="recoverMoment">
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
  methods: {
    deleteArticle() {
      this.$emit('deleteArticle', this.article)
    },
    recoverArticle() {
      this.$emit('recoverArticle', this.article)
    }
  }
}
</script>
