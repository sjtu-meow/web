<template>
<tr>
  <td>{{answer.id}}{{answer.deleted ? '（已删）' : ''}}</td>
  <td>{{answer.profile.nickname}}（{{answer.profile.id}}）</td>
  <td>
    {{plainContent.substring(0, contentPreviewLength)}}{{plainContent.length > contentPreviewLength ? '…' : ''}}
    <a @click="expandAnswerContent">查看详情</a>
  </td>
  <td>
    <button v-if="answer.deleted" type="button" class="btn btn-primary btn-xs" @click="recoverAnswer">
      <span class="glyphicon glyphicon-ok"/>
    </button>
    <button v-else type="button" class="btn btn-danger btn-xs" @click="deleteAnswer">
      <span class="glyphicon glyphicon-remove"/>
    </button>
  </td>
</tr>
</template>

<script>
export default {
  name: 'AnswerListRow',
  props: ['answer'],
  data() {
    return {
      contentPreviewLength: 100
    }
  },
  computed: {
    plainContent() {
      return $(this.answer.content).text()
    }
  },
  methods: {
    deleteAnswer() {
      this.$emit('deleteAnswer', this.answer)
    },
    recoverAnswer() {
      this.$emit('recoverAnswer', this.answer)
    },
    expandAnswerContent() {
      this.$emit('expandAnswerContent', this.answer)
    }
  }
}
</script>
