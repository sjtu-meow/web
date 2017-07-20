<template>
<tr>
  <td :class="{'text-muted': report.closed}">
    {{report.id}} {{report.closed ? '（已关闭）' : ''}}
  </td>
  <td :class="{'text-muted': report.closed}">{{report.profile.nickname}}（{{report.profile.id}}）</td>
  <td :class="{'text-muted': report.closed}">{{report.reason}}</td>
  <td :class="{'text-muted': report.closed}">{{report.itemId}}</td>
  <td :class="{'text-muted': report.closed}">
    {{moment.content.substring(0, contentPreviewLength)}}{{moment.content.length > contentPreviewLength ? '…' : ''}}
    <button v-if="moment.content.length > contentPreviewLength" type="button" class="btn btn-link btn-xs" @click="expandContent">展开</button>
  </td>
  <td>
    <template v-for="media in moment.medias">
      <img v-if="media.type === 'Image'" class="img-rounded" :src="media.url" style="height: 60px; padding-bottom: 4px">
      <video v-if="media.type === 'Video'" :src="media.url" style="height: 60px; padding-bottom: 4px" controls/>
    </template>
  </td>
  <td>
    <button v-if="moment.deleted" type="button" class="btn btn-primary btn-xs" @click="recoverMoment">
      恢复点滴
    </button>
    <button v-else type="button" class="btn btn-danger btn-xs" @click="deleteMoment">
      删除点滴
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
  name: 'MomentReportListRow',
  props: ['report'],
  data() {
    return {
      contentPreviewLength: 100,
      moment: {
        title: '',
        content: '',
        profile: {
          nickname: ''
        }
      }
    }
  },
  created() {
    this.$http.get('/api/admin/moments/' + this.report.itemId)
      .then(function(response) {
        this.moment = response.body;
      }, function(response) {
        alert(response.body.message || '获取点滴失败');
      })
  },
  methods: {
    closeReport() {
      this.$emit('closeReport', this.report)
    },
    openReport() {
      this.$emit('openReport', this.report)
    },
    deleteMoment() {
      this.$emit('deleteMoment', this.moment)
    },
    recoverMoment() {
      this.$emit('recoverMoment', this.moment)
    },
    expandContent() {
      this.$emit('expandContent', this.moment)
    }
  }
}
</script>
