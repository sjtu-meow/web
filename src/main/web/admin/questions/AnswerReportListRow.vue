<template>
<tr>
  <td :class="{'text-muted': report.closed}">
    {{report.id}} {{report.closed ? '（已结束）' : ''}}
  </td>
  <td :class="{'text-muted': report.closed}">{{report.profile.nickname}}（{{report.profile.id}}）</td>
  <td :class="{'text-muted': report.closed}">{{report.reason}}</td>
  <td :class="{'text-muted': report.closed}">{{report.itemId}}</td>
  <td :class="{'text-muted': report.closed}">
    {{plainContent.substring(0, contentPreviewLength)}}{{plainContent.length > contentPreviewLength ? '…' : ''}}
    <button type="button" class="btn btn-link btn-xs" @click="expandContent">查看详情</button>
  </td>
  <td>
    <button v-if="answer.deleted" type="button" class="btn btn-primary btn-xs" @click="recoverAnswer">
      恢复回答
    </button>
    <button v-else type="button" class="btn btn-danger btn-xs" @click="deleteAnswer">
      删除回答
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
  name: 'AnswerReportListRow',
  props: ['report'],
  data() {
    return {
      contentPreviewLength: 100,
      answer: {
        title: '',
        content: '',
        profile: {
          nickname: ''
        }
      }
    }
  },
  computed: {
    plainContent() {
      return $(this.answer.content).text()
    }
  },
  created() {
    this.$http.get('/api/admin/answers/' + this.report.itemId)
      .then(function(response) {
        this.answer = response.body;
      }, function(response) {
        alert(response.body.message || '获取回答失败');
      })
  },
  methods: {
    closeReport() {
      this.$emit('closeReport', this.report)
    },
    openReport() {
      this.$emit('openReport', this.report)
    },
    deleteAnswer() {
      this.$emit('deleteAnswer', this.answer)
    },
    recoverAnswer() {
      this.$emit('recoverAnswer', this.answer)
    },
    expandContent() {
      this.$emit('expandContent', this.answer)
    }
  }
}
</script>
