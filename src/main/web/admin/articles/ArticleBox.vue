<template>
<div class="box">
  <div class="box-header">
    <h3 class="box-title">所有用户</h3>

    <div class="box-tools">
      <div class="input-group input-group-sm" style="width: 150px;">
        <input type="text" class="form-control pull-right" placeholder="搜索" v-model="keywordFromInput">
        <div class="input-group-btn">
          <button type="button" class="btn btn-default" @click="search"><i class="fa fa-search"></i></button>
        </div>
      </div>
    </div>
  </div>
  <!-- /.box-header -->
  <div class="box-body table-responsive no-padding">
    <table class="table table-hover">
      <thead>
        <tr>
          <th>#</th>
          <th>用户</th>
          <th>标题</th>
          <th>文章内容预览</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <article-list-row v-for="article in articles" :key="article.id" :article="article" @deleteArticle="deleteArticle" @recoverArticle="recoverArticle" @expandContent="expandContent" />
      </tbody>
    </table>
  </div>
  <!-- /.box-body -->

  <div v-if="pagination.totalPages > 1" class="box-footer clearfix">
    <pagination :pagination="pagination" @changePage="fetchArticles" />
  </div>
</div>
</template>

<script>
import ArticleListRow from './ArticleListRow.vue'
import Pagination from '../Pagination.vue'

export default {
  name: 'ArticleBox',
  components: {
    ArticleListRow,
    Pagination
  },
  data() {
    return {
      articles: [],
      pagination: {
        currentPage: 0,
        totalPages: 1
      },
      pageSize: 2,
      keywordFromInput: '',
      keywordOfResult: '',
    }
  },
  created() {
    this.fetchArticles(0);
  },
  methods: {
    fetchArticles(page) {
      let url = ''
      if (this.keywordOfResult) {
        url = '/api/admin/articles?' + 'page=' + page + '&size=' + this.pageSize + '&keyword=' + this.keywordOfResult
      } else {
        url = '/api/admin/articles?' + 'page=' + page + '&size=' + this.pageSize
      }

      this.$http.get(url)
        .then(function(response) {
          this.articles = response.body.content;
          this.pagination.currentPage = response.body.number;
          this.pagination.totalPages = response.body.totalPages;
        }, function(response) {
          alert(response.body.message || '获取文章失败');
        })
    },
    deleteArticle(article) {
      this.$emit('deleteArticle', article)
    },
    recoverArticle(article) {
      this.$emit('recoverArticle', article)
    },
    expandContent(article) {
      this.$emit('expandContent', article)
    },
    search() {
      this.keywordOfResult = this.keywordFromInput;
      this.fetchArticles(0);
    }
  }
}
</script>
