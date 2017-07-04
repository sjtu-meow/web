<template>
<tr>
  <td>{{moment.id}}</td>
  <td>{{moment.profile.nickname}}（{{moment.profile.id}}）</td>
  <td>{{moment.content}}</td>
  <td>
    <template v-for="media in moment.medias">
      <img v-if="media.type === 'Image'" class="img-rounded" :src="media.url" height="60">
      <video v-if="media.type === 'Video'" :src="media.url" height="60" controls/>
      &nbsp;
    </template>
  </td>
  <td>
    <button type="button" class="btn btn-danger btn-xs" @click="deleteMoment">
      <span class="glyphicon glyphicon-remove"/>
    </button>
  </td>
</tr>
</template>

<script>
export default {
  name: 'MomentListRow',
  props: ['moment'],
  methods: {
    deleteMoment() {
      this.$emit('deleteMoment', this.moment)
    }
  }
}
</script>
