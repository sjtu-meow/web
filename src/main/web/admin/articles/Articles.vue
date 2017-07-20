<template>
<section>
  <section class="content-header">
    <h1>文章管理</h1>
  </section>
  <section class="content">
    <div class="row">
      <div class="col-md-12">
        <article-box @deleteArticle="promptDeleteArticle" @recoverArticle="promptRecoverArticle" @expandContent="expandContent"/>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <article-report-box @deleteArticle="promptDeleteArticle" @recoverArticle="promptRecoverArticle" @expandContent="expandContent" />
      </div>
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
import ArticleBox from './ArticleBox.vue'
import ArticleReportBox from './ArticleReportBox.vue'

export default {
  name: 'Articles',
  components: {
    ArticleBox,
    ArticleReportBox
  },
  data() {
    return {
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
  methods: {
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
