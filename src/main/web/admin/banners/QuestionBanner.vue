<template>
<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">
      #{{banner.displayOrder + 1}} {{question.title}}
      <small>来自 {{question.profile.nickname}}（{{question.profile.id}}）</small>
    </h3>
  </div>

  <!-- Default panel contents -->
  <div class="panel-body">
    <div class="row">
      <div class="col-md-4">
        <label>吧呢封面</label><span class="text-muted">（点击修改）</span>
        <a class="thumbnail" @click="triggerCoverInputClick">
          <img :src="banner.image">
        </a>
      </div>
      <div class="col-md-8">
        <label>问题预览</label>
        <p v-for="line in question.content.substring(0, contentPreviewLength).split('\n')">{{line}}</p>
        <p>
          <div class="btn-toolbar">
            <div class="btn-group" role="group">
              <button type="button" class="btn btn-default btn-sm" @click="expandContent">
                查看详情
              </button>
            </div>

            <div class="btn-group" role="group">
              <button type="button" class="btn btn-default btn-sm" @click="moveUp">
                <span class="glyphicon glyphicon-arrow-up"/>
              </button>
              <button type="button" class="btn btn-default btn-sm" @click="deleteBanner">
                <span class="glyphicon glyphicon-remove"/>
              </button>
              <button type="button" class="btn btn-default btn-sm" @click="moveDown">
                <span class="glyphicon glyphicon-arrow-down"/>
              </button>
            </div>
          </div>
        </p>
      </div>
    </div>
  </div>
</div>
</template>

<script>
export default {
  name: 'QuestionBanner',
  props: ['banner'],
  data() {
    return {
      contentPreviewLength: 200,
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
    this.$http.get('/api/admin/questions/' + this.banner.itemId)
      .then(function(response) {
        this.question = response.body;
      }, function(response) {
        alert(response.body.message || '获取问题失败');
      })
  },
  methods: {
    moveUp() {
      this.$emit('moveUp', this.banner);
    },
    moveDown() {
      this.$emit('moveDown', this.banner);
    },
    deleteBanner() {
      this.$emit('deleteBanner', this.banner, this.question.title)
    },
    expandContent() {
      this.$emit('expandContent', this.question.content)
    },
    triggerCoverInputClick() {
      this.$emit('triggerCoverInputClick', this.banner)
    }
  }
}
</script>
