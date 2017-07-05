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
            <p>确定恢复 <b>{{questionToRecover.profile.nickname}}</b> 的回复<b>「{{questionToRecover.title}}」</b>吗？</p>
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
import QuestionItem from './QuestionItem.vue'

export default {
  name: 'Questions',
  components: {
    QuestionItem
  },
  data() {
    return {
      questions: [{
        id: 1,
        title: '四川的猫吃辣吗？',
        content: '看了回复，有必要说明下。。\n是这样的，我那天忽然想到万一我在四川领养了一只猫...\n拿秋刀鱼该加辣椒给它嘛...\n就是这个问题....',
        medias: [{
          "id": 3,
          "type": "Image",
          "url": "http://lorempixel.com/200/200",
          "deleted": false
        }],
        answers: [{
          id: 1,
          content: '<p>+1s or not +1s, this is a question.</p><p><img src="http://lorempixel.com/200/200" /></p><p>+1s or not +1s, this is a question.</p>',
          profile: {
            id: 1,
            nickname: 'haha'
          }
        }],
        profile: {
          id: 1,
          nickname: 'haha'
        }
      }],
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

    },
    promptRecoverQuestion(question) {
      this.questionToDelete = question;
      $('#recover-question-modal').modal('show');
    },
    recoverQuestion() {

    },
    promptDeleteAnswer(answer) {
      this.answerToDelete = answer;
      $('#delete-answer-modal').modal('show');
    },
    deleteAnswer() {

    },
    promptRecoverAnswer(answer) {
      this.answerToDelete = answer;
      $('#recover-answer-modal').modal('show');
    },
    recoverAnswer() {

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
