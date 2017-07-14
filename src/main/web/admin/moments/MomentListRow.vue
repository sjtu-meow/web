<template>
<tr>
  <td>
    {{moment.id}} {{moment.deleted ? '（已删）' : ''}}
  </td>
  <td>{{moment.profile.nickname}}（{{moment.profile.id}}）</td>
  <td>
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
      <span class="glyphicon glyphicon-ok"/>
    </button>
    <button v-else type="button" class="btn btn-danger btn-xs" @click="deleteMoment">
      <span class="glyphicon glyphicon-remove"/>
    </button>
  </td>
</tr>
</template>

<script>
export default {
  name: 'MomentListRow',
  props: ['moment'],
  data() {
    return {
      contentPreviewLength: 100
    }
  },
  methods: {
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
