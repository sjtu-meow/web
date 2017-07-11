<template>
<section>
  <section class="content-header">
    <h1>所有问答</h1>
  </section>
  <section class="content">
    <question-item v-for="question in questions" :key="question" :question="question"
      @deleteQuestion="promptDeleteQuestion" @recoverQuestion="promptRecoverQuestion"
      @deleteAnswer="promptDeleteAnswer" @recoverAnswer="promptRecoverAnswer"
      @expandAnswerContent="expandAnswerContent" @expandQuestionContent="expandQuestionContent" />

    <div class="text-center">
      <pagination :pagination="pagination" @changePage="fetchQuestions"/>
    </div>

    <!-- Delete Question Modal -->
    <div class="modal fade" id="delete-question-modal" tabindex="-1" role="dialog" aria-labelledby="delete-question-modal-title">
      <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="delete-question-modal-title">删除问题</h4>
          </div>
          <div class="modal-body">
            <p class="text-danger">确定删除 <b>{{questionToDelete.profile.nickname}}</b> 的问题<b>「{{questionToDelete.title}}」</b>吗？</p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-danger" @click="deleteQuestion">删除</button>
          </div>
        </div>
      </div>
    </div>

    <!-- Delete Answer Modal -->
    <div class="modal fade" id="delete-answer-modal" tabindex="-1" role="dialog" aria-labelledby="delete-answer-modal-title">
      <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="delete-answer-modal-title">删除回答</h4>
          </div>
          <div class="modal-body">
            <p class="text-danger">确定删除 <b>{{answerToDelete.profile.nickname}}</b> 的回答<b>「{{plainAnswerContentToDelete.substring(0, 30)}}{{plainAnswerContentToDelete.length > 30 ? '…' : ''}}」</b>吗？</p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-danger" @click="deleteAnswer">删除</button>
          </div>
        </div>
      </div>
    </div>

    <!-- Recover Question  Modal -->
    <div class="modal fade" id="recover-question-modal" tabindex="-1" role="dialog" aria-labelledby="recover-question-modal-title">
      <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="recover-question-modal-title">恢复问题</h4>
          </div>
          <div class="modal-body">
            <p>确定恢复 <b>{{questionToRecover.profile.nickname}}</b> 的问题<b>「{{questionToRecover.title}}」</b>吗？</p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" @click="recoverQuestion">恢复</button>
          </div>
        </div>
      </div>
    </div>

    <!-- Recover Answer  Modal -->
    <div class="modal fade" id="recover-answer-modal" tabindex="-1" role="dialog" aria-labelledby="recover-answer-modal-title">
      <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="recover-answer-modal-title">恢复回答</h4>
          </div>
          <div class="modal-body">
            <p>确定恢复 <b>{{answerToRecover.profile.nickname}}</b> 的回答<b>「{{plainAnswerContentToRecover.substring(0, 30)}}{{plainAnswerContentToDelete.length > 30 ? '…' : ''}}」</b>吗？</p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" @click="recoverAnswer">恢复</button>
          </div>
        </div>
      </div>
    </div>

    <!-- Expand Question Content Modal -->
    <div class="modal fade" id="question-content-detail-modal" tabindex="-1" role="dialog" aria-labelledby="question-content-detail-modal-title">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="question-content-detail-modal-title">问题描述全文</h4>
          </div>
          <div class="modal-body">
            <p v-for="line in questionToShow.content.split('\n')">
              {{line}}
            </p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
          </div>
        </div>
      </div>
    </div>

    <!-- Expand Answer Content Modal -->
    <div class="modal fade" id="answer-content-detail-modal" tabindex="-1" role="dialog" aria-labelledby="answer-content-detail-modal-title">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="answer-content-detail-modal-title">回答全文</h4>
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
</section>
</template>

<script>
import QuestionItem from './QuestionItem.vue';
import Pagination from '../Pagination.vue';

export default {
  name: 'Questions',
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
      questionToDelete: {
        profile: {
          nickname: 'haha'
        },
        content: '+1s now'
      },
      questionToRecover: {
        profile: {
          nickname: 'haha'
        },
        content: '+1s now'
      },
      questionToShow: {
        profile: {
          nickname: 'haha'
        },
        content: '+1s now'
      },
      answerToDelete: {
        profile: {
          nickname: 'haha'
        },
        content: '+1s now'
      },
      answerToRecover: {
        profile: {
          nickname: 'haha'
        },
        content: '+1s now'
      }
    }
  },
  computed: {
    plainAnswerContentToDelete() {
      return $(this.answerToDelete.content).text()
    },
    plainAnswerContentToRecover() {
      return $(this.answerToRecover.content).text()
    }
  },
  created() {
    this.fetchQuestions(0)
  },
  methods: {
    fetchQuestions: function(page) {
      //TODO: change url
      this.$http.get('http://106.14.156.19/api/admin/questions?' + 'page=' + page + '&size=' + this.pageSize)
        .then(function(response) {
          this.questions = response.body.content;
          this.pagination.currentPage = response.body.number;
          this.pagination.totalPages = response.body.totalPages;
        }, function(response) {
          alert(response.body.message || '获取问答失败');
        })
    },
    promptDeleteQuestion(question) {
      this.questionToDelete = question;
      $('#delete-question-modal').modal('show');
    },
    deleteQuestion() {
      this.$http.delete('http://106.14.156.19/api/admin/questions/' + this.questionToDelete.id)
        .then(function(response) {
          this.questionToDelete.deleted = true;
          $('#delete-question-modal').modal('hide');
        }, function(response) {
          alert(response.body.message || '修改失败');
        })
    },
    promptRecoverQuestion(question) {
      this.questionToRecover = question;
      $('#recover-question-modal').modal('show');
    },
    recoverQuestion() {
      this.$http.patch('http://106.14.156.19/api/admin/questions/' + this.questionToRecover.id, {
        isDeleted: false
      }).then(function(response) {
        this.questionToRecover.deleted = false;
        $('#recover-question-modal').modal('hide');
      }, function(response) {
        alert(response.body.message || '修改失败');
      });
    },
    promptDeleteAnswer(answer) {
      this.answerToDelete = answer;
      $('#delete-answer-modal').modal('show');
    },
    deleteAnswer() {
      this.$http.delete('http://106.14.156.19/api/admin/answers/' + this.answerToDelete.id)
        .then(function(response) {
          this.answerToDelete.deleted = true;
          $('#delete-answer-modal').modal('hide');
        }, function(response) {
          alert(response.body.message || '修改失败');
        })
    },
    promptRecoverAnswer(answer) {
      this.answerToRecover = answer;
      $('#recover-answer-modal').modal('show');
    },
    recoverAnswer() {
      this.$http.patch('http://106.14.156.19/api/admin/answers/' + this.answerToRecover.id, {
        isDeleted: false
      }).then(function(response) {
        this.answerToRecover.deleted = false;
        $('#recover-answer-modal').modal('hide');
      }, function(response) {
        alert(response.body.message || '修改失败');
      });
    },
    expandAnswerContent(answer) {
      $('#answer-content-detail-modal .modal-body').html(answer.content);
      $('#answer-content-detail-modal').modal('show');
    },
    expandQuestionContent(question) {
      this.quesitionToShow = question;
      $('#question-content-detail-modal').modal('show');
    }
  }
}
</script>
