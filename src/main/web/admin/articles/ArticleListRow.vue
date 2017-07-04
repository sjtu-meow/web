<template>
<tr>
  <td>{{article.id}}</td>
  <td>{{article.profile.nickname}}（{{article.profile.id}}）</td>
  <td>{{article.content.substring(0, 50)}}</td>
  <td>
    <button type="button" class="btn btn-danger btn-xs" @click="deleteArticle">
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
    }
  }
}
</script>
