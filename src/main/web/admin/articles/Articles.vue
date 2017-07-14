<template>
<section>
  <section class="content-header">
    <h1>所有文章</h1>
  </section>
  <section class="content">
    <div class="table-responsive">
      <table class="table table-hover">
        <thead>
          <tr>
            <th class="col-md-1">#</th>
            <th class="col-md-2">用户</th>
            <th class="col-md-2">标题</th>
            <th class="col-md-6">文章内容预览</th>
            <th class="col-md-1"></th>
          </tr>
        </thead>
        <tbody>
          <article-list-row v-for="article in articles" :key="article.id" :article="article"
            @deleteArticle="promptDeleteArticle" @recoverArticle="promptRecoverArticle" @expandContent="expandContent" />
        </tbody>
      </table>
    </div>

    <div class="text-center">
      <pagination :pagination="pagination" @changePage="fetchArticles"/>
    </div>
  </section>

  <!-- Delete Modal -->
  <div class="modal fade" id="delete-article-modal" tabindex="-1" role="dialog" aria-labelledby="delete-article-modal-title">
    <div class="modal-dialog modal-sm" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="delete-article-modal-title">删除文章</h4>
        </div>
        <div class="modal-body">
          <p class="text-danger">确定删除 <b>{{articleToDelete.profile.nickname}}</b> 的文章<b>「{{plainContentToDelete.substring(0, 30)}}{{plainContentToRecover.length > 30 ? '…' : ''}}」</b>吗？</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          <button type="button" class="btn btn-danger" @click="deleteArticle">删除</button>
        </div>
      </div>
    </div>
  </div>

  <!-- Recover Modal -->
  <div class="modal fade" id="recover-article-modal" tabindex="-1" role="dialog" aria-labelledby="recover-article-modal-title">
    <div class="modal-dialog modal-sm" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="recover-article-modal-title">恢复文章</h4>
        </div>
        <div class="modal-body">
          <p>确定恢复 <b>{{articleToRecover.profile.nickname}}</b> 的文章<b>「{{plainContentToRecover.substring(0, 30)}}{{plainContentToRecover.length > 30 ? '…' : ''}}」</b>吗？</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          <button type="button" class="btn btn-primary" @click="recoverArticle">恢复</button>
        </div>
      </div>
    </div>
  </div>

  <!-- Expand Content Modal -->
  <div class="modal fade" id="article-content-detail-modal" tabindex="-1" role="dialog" aria-labelledby="article-content-detail-modal-title">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="article-content-detail-modal-title">{{articleToShow.title}}</h4>
        </div>
        <div class="modal-body">
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
import ArticleListRow from './ArticleListRow.vue'
import Pagination from '../Pagination.vue'

export default {
  name: 'Articles',
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
      articleToDelete: {
        profile: {
          nickname: 'haha'
        },
        content: '<p>+1s or not +1s, this is a question.</p><p><img src="http://lorempixel.com/200/200" /></p><p>+1s or not +1s, this is a question.</p>',
        title: '如何续命'
      },
      articleToRecover: {
        profile: {
          nickname: 'haha'
        },
        content: '<p>+1s or not +1s, this is a question.</p><p><img src="http://lorempixel.com/200/200" /></p><p>+1s or not +1s, this is a question.</p>',
        title: '如何续命'
      },
      articleToShow: {
        profile: {
          nickname: 'haha'
        },
        content: '<p>+1s or not +1s, this is a question.</p><p><img src="http://lorempixel.com/200/200" /></p><p>+1s or not +1s, this is a question.</p>',
        title: '如何续命'
      }
    }
  },
  computed: {
    plainContentToDelete() {
      return $(this.articleToDelete.content).text()
    },
    plainContentToRecover() {
      return $(this.articleToRecover.content).text()
    }
  },
  created() {
    this.fetchArticles(0);
  },
  methods: {
    fetchArticles(page) {
      this.$http.get('/api/admin/articles?' + 'page=' + page + '&size=' + this.pageSize)
        .then(function(response) {
          this.articles = response.body.content;
          this.pagination.currentPage = response.body.number;
          this.pagination.totalPages = response.body.totalPages;
        }, function(response) {
          alert(response.body.message || '获取文章失败');
        })
    },
    promptDeleteArticle(article) {
      this.articleToDelete = article;
      $('#delete-article-modal').modal('show');
    },
    deleteArticle() {
      this.$http.delete('/api/admin/articles/' + this.articleToDelete.id)
        .then(function(response) {
          this.articleToDelete.deleted = true;
          $('#delete-article-modal').modal('hide');
        }, function(response) {
          alert(response.body.message || '修改失败');
        })
    },
    promptRecoverArticle(article) {
      this.articleToRecover = article;
      $('#recover-article-modal').modal('show');
    },
    recoverArticle() {
      this.$http.patch('/api/admin/articles/' + this.articleToRecover.id, {
        isDeleted: false
      }).then(function(response) {
        this.articleToRecover.deleted = false;
        $('#recover-article-modal').modal('hide');
      }, function(response) {
        alert(response.body.message || '修改失败');
      });
    },
    expandContent(article) {
      this.articleToShow = article;
      $('#article-content-detail-modal .modal-body').html(article.content);
      $('#article-content-detail-modal').modal('show');
    }
  }
}
</script>
