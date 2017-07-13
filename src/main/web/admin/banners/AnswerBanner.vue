<template>
<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">
      #{{banner.displayOrder}} 一个问题的回答
      <small>来自 {{banner.item.profile.nickname}}（{{banner.item.profile.id}}）</small>
    </h3>
  </div>

  <!-- Default panel contents -->
  <div class="panel-body">
    <div class="row">
      <div class="col-md-4">
        <a class="thumbnail">
            <img :src="banner.bannerCoverUrl">
          </a>
      </div>
      <div class="col-md-8">
        <p v-for="line in plainContent.substring(0, contentPreviewLength).split('\n')">{{line}}</p>
        <p><a @click="expandContent">查看详情</a></p>
        <p>
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
        </p>
      </div>
    </div>
  </div>
</div>
</template>

<script>
export default {
  name: 'AnswerBanner',
  props: ['banner'],
  data() {
    return {
      contentPreviewLength: 200
    }
  },
  computed: {
    plainContent() {
      return $(this.banner.item.content).text();
    }
  },
  methods: {
    moveUp() {
      this.$emit('moveUp', this.banner);
    },
    moveDown() {
      this.$emit('moveDown', this.banner);
    },
    deleteBanner() {
      this.$emit('deleteBanner', this.banner);
    },
    expandContent() {
      this.$emit('expandContent', this.banner.item.content);
    }
  }
}
</script>
