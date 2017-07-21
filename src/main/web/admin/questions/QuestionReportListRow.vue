<template>
<tr>
  <td :class="{'text-muted': report.closed}">
    {{report.id}} {{report.closed ? '（已结束）' : ''}}
  </td>
  <td :class="{'text-muted': report.closed}">{{report.profile.nickname}}（{{report.profile.id}}）</td>
  <td :class="{'text-muted': report.closed}">{{report.reason}}</td>
  <td :class="{'text-muted': report.closed}">{{report.itemId}}</td>
  <td :class="{'text-muted': report.closed}">
    {{question.content.substring(0, contentPreviewLength)}}{{question.content.length > contentPreviewLength ? '…' : ''}}
    <button v-if="question.content.length > contentPreviewLength" type="button" class="btn btn-link btn-xs" @click="expandContent">展开</button>
  </td>
  <td>
    <button v-if="question.deleted" type="button" class="btn btn-primary btn-xs" @click="recoverQuestion">
      恢复问题
    </button>
    <button v-else type="button" class="btn btn-danger btn-xs" @click="deleteQuestion">
      删除问题
    </button>
    <button v-if="report.closed" type="button" class="btn btn-danger btn-xs" @click="openReport">
      重启举报
    </button>
    <button v-else type="button" class="btn btn-primary btn-xs" @click="closeReport">
      结束举报
    </button>
  </td>
</tr>
</template>

<script>
export default {
  name: 'QuestionReportListRow',
  props: ['report'],
  data() {
    return {
      contentPreviewLength: 30,
      question: {
        title: '',
        content: '',
        profile: {
          nickname: ''
        }
      }
    }
  },
  created() {
    this.$http.get('/api/admin/questions/' + this.report.itemId)
      .then(function(response) {
        this.question = response.body;
      }, function(response) {
        alert(response.body.message || '获取问题失败');
      })
  },
  methods: {
    closeReport() {
      this.$emit('closeReport', this.report)
    },
    openReport() {
      this.$emit('openReport', this.report)
    },
    deleteQuestion() {
      this.$emit('deleteQuestion', this.question)
    },
    recoverQuestion() {
      this.$emit('recoverQuestion', this.question)
    },
    expandContent() {
      this.$emit('expandContent', this.question)
    }
  }
}
</script>
