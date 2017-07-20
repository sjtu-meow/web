<template>
<tr>
  <td>
    {{report.id}} {{report.closed ? '（已关闭）' : ''}}
  </td>
  <td>{{report.profile.nickname}}（{{report.profile.id}}）</td>
  <td>{{report.reason}}</td>
  <td>{{report.itemId}}</td>
  <td>
    {{question.content.substring(0, contentPreviewLength)}}{{question.content.length > contentPreviewLength ? '…' : ''}}
    <button v-if="question.content.length > contentPreviewLength" type="button" class="btn btn-link btn-xs" @click="expandContent">展开</button>
  </td>
  <td>
    <template v-for="media in question.medias">
      <img v-if="media.type === 'Image'" class="img-rounded" :src="media.url" style="height: 60px; padding-bottom: 4px">
      <video v-if="media.type === 'Video'" :src="media.url" style="height: 60px; padding-bottom: 4px" controls/>
    </template>
  </td>
  <td>
    <button v-if="question.deleted" type="button" class="btn btn-primary btn-xs" @click="recoverQuestion">
      <span class="glyphicon glyphicon-ok"/>
    </button>
    <button v-else type="button" class="btn btn-danger btn-xs" @click="deleteQuestion">
      <span class="glyphicon glyphicon-remove"/>
    </button>
    <button v-if="report.closed" type="button" class="btn btn-primary btn-xs" @click="openReport">
      <span class="glyphicon glyphicon-ok"/>
    </button>
    <button v-else type="button" class="btn btn-danger btn-xs" @click="closeReport">
      <span class="glyphicon glyphicon-remove"/>
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
