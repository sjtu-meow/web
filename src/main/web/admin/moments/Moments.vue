<template>
<section>
  <section class="content-header">
    <h1>所有点滴</h1>
  </section>
  <section class="content">
    <div class="table-responsive">
      <table class="table table-hover">
        <thead>
          <tr>
            <th class="col-md-1">#</th>
            <th class="col-md-1">用户</th>
            <th class="col-md-3">内容</th>
            <th class="col-md-6">媒体</th>
            <th class="col-md-1"></th>
          </tr>
        </thead>
        <tbody>
          <moment-list-row v-for="moment in moments" :moment="moment" @deleteMoment="promptDeleteMoment" />
        </tbody>
      </table>
    </div>
  </section>
</section>
</template>

<script>
import MomentListRow from './MomentListRow.vue'

export default {
  name: 'Moments',
  components: {
    MomentListRow
  },
  data() {
    return {
      moments: [{
        "id": 1,
        "type": 0,
        "profile": {
          "nickname": "喵喵喵的伙伴",
          "bio": "Web 开发专家",
          "avatar": null,
          "id": 1
        },
        "comments": null,
        "likeCount": 0,
        "commentCount": 0,
        "content": "Rich gifts wax poor when givers prove unkind.",
        "medias": [{
          "id": 3,
          "type": "Image",
          "url": "https://i.ytimg.com/vi/prALrHUJ8Ns/hqdefault.jpg"
        }, {
          "id": 3,
          "type": "Image",
          "url": "https://i.ytimg.com/vi/prALrHUJ8Ns/hqdefault.jpg"
        }]
      }]
    }
  },
  methods: {
    promptDeleteMoment: function() {

    }
  }
}
</script>
