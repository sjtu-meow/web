<template>
<tr>
  <td :class="{'text-muted': article.deleted}">{{article.id}} {{article.deleted ? '（已删）' : ''}}</td>
  <td :class="{'text-muted': article.deleted}">{{article.profile.nickname}}（{{article.profile.id}}）</td>
  <td :class="{'text-muted': article.deleted}">{{article.title}}</td>
  <td :class="{'text-muted': article.deleted}">
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
      return $('<div>' + this.article.content + '</div>').text()
    }
  }
}
</script>
