<template>
<tr>
  <td>
    {{report.id}} {{report.closed ? '（已关闭）' : ''}}
  </td>
  <td>{{report.profile.nickname}}（{{report.profile.id}}）</td>
  <td>{{report.reason}}</td>
  <td>{{report.itemId}}</td>
  <td>
    {{plainContent.substring(0, contentPreviewLength)}}{{plainContent.length > contentPreviewLength ? '…' : ''}}
    <button type="button" class="btn btn-link btn-xs" @click="expandContent">查看详情</button>
  </td>
  <td>
    <button v-if="answer.deleted" type="button" class="btn btn-primary btn-xs" @click="recoverAnswer">
      <span class="glyphicon glyphicon-ok"/> 恢复回答
    </button>
    <button v-else type="button" class="btn btn-danger btn-xs" @click="deleteAnswer">
      <span class="glyphicon glyphicon-remove"/> 删除回答
    </button>
    <button v-if="report.closed" type="button" class="btn btn-primary btn-xs" @click="openReport">
      <span class="glyphicon glyphicon-ok"/> 开启举报
    </button>
    <button v-else type="button" class="btn btn-danger btn-xs" @click="closeReport">
      <span class="glyphicon glyphicon-remove"/> 关闭举报
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
