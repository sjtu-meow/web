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
            <th class="col-md-1">用户</th>
            <th class="col-md-9">预览</th>
            <th class="col-md-1"></th>
          </tr>
        </thead>
        <tbody>
          <article-list-row v-for="article in articles" :key="article.id" :article="article"
            @deleteArticle="promptDeleteArticle" @recoverArticle="promptRecoverArticle" />
        </tbody>
      </table>
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
          <p class="text-danger">确定删除 <b>{{articleToDelete.profile.nickname}}</b> 的文章（{{articleToDelete.content.substring(0, 50)}}）吗？</p>
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
          <p class="text-danger">确定恢复 <b>{{articleToRecover.profile.nickname}}</b> 的文章（{{articleToRecover.content.substring(0, 30)}}）吗？</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          <button type="button" class="btn btn-danger" @click="recoverArticle">恢复</button>
        </div>
      </div>
    </div>
  </div>
</section>
</template>

<script>
import ArticleListRow from './ArticleListRow.vue'

export default {
  name: 'Articles',
  components: {
    ArticleListRow
  },
  data() {
    return {
      articles: [{
        id: 1,
        content: '+1s or not +1s, this is a question.',
        profile: {
          id: 1,
          nickname: 'haha'
        }
      }],
      articleToDelete: {
        profile: {
          nickname: 'haha'
        },
        content: '+1s or not +1s, this is a question.'
      },
      articleToRecover: {
        profile: {
          nickname: 'haha'
        },
        content: '+1s or not +1s, this is a question.'
      }
    }
  },
  methods: {
    promptDeleteArticle(article) {
      this.articleToDelete = article;
      $('#delete-article-modal').modal('show');
    },
    deleteArticle() {

    },
    promptRecoverArticle(article) {
      this.articleToDelete = article;
      $('#recover-article-modal').modal('show');
    },
    recoverArticle() {

    }
  }
}
</script>
