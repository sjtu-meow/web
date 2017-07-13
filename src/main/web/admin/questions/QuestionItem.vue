<template>
<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">
      #{{question.id}}{{question.deleted ? '（已删）' : ''}} {{question.title}}
      <small>来自 {{question.profile.nickname}}（{{question.profile.id}}）</small>
    </h3>
  </div>

  <!-- Default panel contents -->
  <div class="panel-body">
    <div class="row">
      <div class="col-md-11">
        <p>{{question.content.substring(0, contentPreviewLength)}}</p>
        <a v-if="question.content.length > contentPreviewLength" @click="expandQuestionContent">展开</a>
      </div>
      <div class="col-md-1">
        <button v-if="question.deleted" type="button" class="btn btn-primary btn-xs" @click="recoverQuestion">
          <span class="glyphicon glyphicon-ok"/>
        </button>
        <button v-else type="button" class="btn btn-danger btn-xs" @click="deleteQuestion">
          <span class="glyphicon glyphicon-remove"/>
        </button>
      </div>
    </div>
  </div>

  <!-- Table -->
  <div class="table-responsive">
    <table class="table table-hover">
      <thead>
        <tr>
          <th class="col-md-1">#</th>
          <th class="col-md-2">用户</th>
          <th class="col-md-8">回答文字预览</th>
          <th class="col-md-1"></th>
        </tr>
      </thead>
      <tbody>
        <answer-list-row v-for="answer in question.answers" :key="answer.id" :answer="answer"
          @deleteAnswer="deleteAnswer" @recoverAnswer="recoverAnswer" @expandAnswerContent="expandAnswerContent" />
      </tbody>
    </table>
  </div>
</div>
</template>

<script>
import AnswerListRow from './AnswerListRow.vue'

export default {
  name: 'QuestionItem',
  components: {
    AnswerListRow
  },
  props: ['question'],
  data() {
    return {
      contentPreviewLength: 200
    }
  },
  methods: {
    deleteQuestion() {
      this.$emit('deleteQuestion', this.question)
    },
    deleteAnswer(answer) {
      this.$emit('deleteAnswer', answer)
    },
    recoverQuestion() {
      this.$emit('recoverQuestion', this.question)
    },
    recoverAnswer(answer) {
      this.$emit('recoverAnswer', answer)
    },
    expanQuetiondContent() {
      this.$emit('expandQuestionContent', this.question)
    },
    expandAnswerContent(answer) {
      this.$emit('expandAnswerContent', answer)
    }
  }
}
</script>
