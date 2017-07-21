<template>
<div class="box">
  <div class="box-header">
    <h3 class="box-title">所有评论</h3>

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
          <th>评论</th>
          <th>对象类型</th>
          <th>对象ID</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <comment-list-row v-for="comment in comments" :key="comment.id" :comment="comment"
          @deleteComment="deleteComment" @recoverComment="recoverComment" @expandCommentContent="expandCommentContent" />
      </tbody>
    </table>
  </div>
  <!-- /.box-body -->

  <div v-if="pagination.totalPages > 1" class="box-footer clearfix">
    <pagination :pagination="pagination" @changePage="fetchComments" />
  </div>
</div>
</template>

<script>
import CommentListRow from './CommentListRow.vue'
import Pagination from '../Pagination.vue'

export default {
  name: 'CommentBox',
  components: {
    CommentListRow,
    Pagination
  },
  data() {
    return {
      comments: [],
      pagination: {
        currentPage: 0,
        totalPages: 1
      },
      pageSize: 2,
      keywordFromInput: '',
      keywordOfResult: ''
    }
  },
  created() {
    this.fetchComments(0)
  },
  methods: {
    fetchComments(page) {
      let url = ''
      if (this.keywordOfResult) {
        url = '/api/admin/comments?' + 'page=' + page + '&size=' + this.pageSize + '&keyword=' + this.keywordOfResult
      } else {
        url = '/api/admin/comments?' + 'page=' + page + '&size=' + this.pageSize
      }

      this.$http.get(url)
        .then(function(response) {
          this.comments = response.body.content;
          this.pagination.currentPage = response.body.number;
          this.pagination.totalPages = response.body.totalPages;
        }, function(response) {
          alert(response.body.message || '获取评论失败');
        })
    },
    deleteComment(comment) {
      this.$emit('deleteComment', comment)
    },
    recoverComment(comment) {
      this.$emit('recoverComment', comment)
    },
    expandCommentContent(comment) {
      this.$emit('expandCommentContent', comment)
    },
    search() {
      this.keywordOfResult = this.keywordFromInput;
      this.fetchComments(0);
    }
  }
}
</script>
