<template>
<section>
  <section class="content-header">
    <h1>问答管理</h1>
  </section>
  <section class="content">
    <div class="row">
      <div class="col-md-12">
        <question-box @deleteQuestion="promptDeleteQuestion" @recoverQuestion="promptRecoverQuestion" @expandQuestionContent="expandQuestionContent"
          @deleteAnswer="promptDeleteAnswer" @recoverAnswer="promptRecoverAnswer" @expandAnswerContent="expandAnswerContent"/>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <question-report-box @deleteQuestion="promptDeleteQuestion" @recoverQuestion="promptRecoverQuestion" @expandContent="expandQuestionContent" />
      </div>
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
import QuestionBox from './QuestionBox.vue'
import QuestionReportBox from './QuestionReportBox.vue'

export default {
  name: 'Questions',
  components: {
    QuestionBox,
    QuestionReportBox
  },
  data() {
    return {
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
  methods: {
    promptDeleteQuestion(question) {
      this.questionToDelete = question;
      $('#delete-question-modal').modal('show');
    },
    deleteQuestion() {
      this.$http.delete('/api/admin/questions/' + this.questionToDelete.id)
        .then(function(response) {
          // delete question
          this.questionToDelete.deleted = true;

          // delete related answers
          for (var i = 0; i < this.questionToDelete.answers.length; i++) {
            this.questionToDelete.answers[i].deleted = true;
          }

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
      this.$http.patch('/api/admin/questions/' + this.questionToRecover.id, {
        isDeleted: false
      }).then(function(response) {
        // recover question
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
      this.$http.delete('/api/admin/answers/' + this.answerToDelete.id)
        .then(function(response) {
          this.answerToDelete.deleted = true;
          $('#delete-answer-modal').modal('hide');
        }, function(response) {
          alert(response.body.message || '修改失败');
        })
    },
    promptRecoverAnswer(answer, question) {
      this.answerToRecover = answer;
      this.questionToRecover = question;
      $('#recover-answer-modal').modal('show');
    },
    recoverAnswer() {
      this.$http.patch('/api/admin/answers/' + this.answerToRecover.id, {
        isDeleted: false
      }).then(function(response) {
        this.answerToRecover.deleted = false;
        this.questionToRecover.deleted = false;
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
      this.questionToShow = question;
      $('#question-content-detail-modal').modal('show');
    }
  }
}
</script>
