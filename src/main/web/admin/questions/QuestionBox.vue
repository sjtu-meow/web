<template>
<div class="box">
  <div class="box-header">
    <h3 class="box-title">所有问答</h3>

    <div class="box-tools">
      <div class="input-group input-group-sm" style="width: 150px;">
        <input type="text" class="form-control pull-right" placeholder="搜索" v-model="keywordFromInput" @keyup.enter="search">
        <div class="input-group-btn">
          <button type="button" class="btn btn-default" @click="search"><i class="fa fa-search"></i></button>
        </div>
      </div>
    </div>
  </div>
  <!-- /.box-header -->
  <div class="box-body">
    <question-item v-for="question in questions" :key="question" :question="question"
      @deleteQuestion="deleteQuestion" @recoverQuestion="recoverQuestion" @deleteAnswer="deleteAnswer" @recoverAnswer="recoverAnswer"
      @expandAnswerContent="expandAnswerContent" @expandQuestionContent="expandQuestionContent" />
  </div>
  <!-- /.box-body -->
  <div v-if="pagination.totalPages > 1" class="box-footer clearfix">
    <pagination :pagination="pagination" @changePage="fetchQuestions" />
  </div>
</div>
</template>

<script>
import QuestionItem from './QuestionItem.vue';
import Pagination from '../Pagination.vue';

export default {
  name: 'QuestionBox',
  components: {
    QuestionItem,
    Pagination
  },
  data() {
    return {
      questions: [],
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
    this.fetchQuestions(0)
  },
  methods: {
    fetchQuestions: function(page) {
      let url = ''
      if (this.keywordOfResult) {
        url = '/api/admin/questions?' + 'page=' + page + '&size=' + this.pageSize + '&keyword=' + this.keywordOfResult
      } else {
        url = '/api/admin/questions?' + 'page=' + page + '&size=' + this.pageSize
      }

      this.$http.get(url)
        .then(function(response) {
          this.questions = response.body.content;
          this.pagination.currentPage = response.body.number;
          this.pagination.totalPages = response.body.totalPages;
        }, function(response) {
          alert(response.body.message || '获取问答失败');
        })
    },
    deleteQuestion(question) {
      this.$emit('deleteQuestion', question)
    },
    deleteAnswer(answer) {
      this.$emit('deleteAnswer', answer)
    },
    recoverQuestion(question) {
      this.$emit('recoverQuestion', question)
    },
    recoverAnswer(answer, question) {
      this.$emit('recoverAnswer', answer, question)
    },
    expandQuestionContent(question) {
      this.$emit('expandQuestionContent', question)
    },
    expandAnswerContent(answer) {
      this.$emit('expandAnswerContent', answer)
    },
    search() {
      this.keywordOfResult = this.keywordFromInput;
      this.fetchQuestions(0);
    }
  }
}
</script>
