<template>
<tr>
  <td :class="{'text-muted': answer.deleted}">{{answer.id}}{{answer.deleted ? '（已删）' : ''}}</td>
  <td :class="{'text-muted': answer.deleted}">{{answer.profile.nickname}}（{{answer.profile.id}}）</td>
  <td :class="{'text-muted': answer.deleted}">
    {{plainContent.substring(0, contentPreviewLength)}}{{plainContent.length > contentPreviewLength ? '…' : ''}}
    <button type="button" class="btn btn-link btn-xs" @click="expandAnswerContent">查看详情</button>
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
      return $('<div>' + this.answer.content + '</div>').text()
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
