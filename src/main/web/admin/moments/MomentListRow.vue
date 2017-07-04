<template>
<tr>
  <td>{{moment.id}}</td>
  <td>{{moment.profile.nickname}}（{{moment.profile.id}}）</td>
  <td>{{moment.content}}</td>
  <td>
    <template v-if="moment.medias.length > 0">
      <template v-if="moment.medias[0].type='Image'">
        <template v-for="media in moment.medias">
          <img class="img-rounded" :src="media.url" height="40">&nbsp;
        </template>
      </template>
      <template v-else>
        <video :src="moment.medias[0].url" height="40"/>
      </template>
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

    }
  }
}
</script>
