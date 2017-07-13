<template>
<section>
  <section class="content-header">
    <h1>当前吧呢</h1>
  </section>
  <section class="content">
    <template v-for="banner in banners">
      <article-banner v-if="banner.type === 'article'" :banner="banner"/>
      <answer-banner v-if="banner.type === 'answer'" :banner="banner"/>
    </template>
  </section>
</section>
</template>

<script>
import ArticleBanner from './ArticleBanner.vue'
import AnswerBanner from './AnswerBanner.vue'

export default {
  components: {
    ArticleBanner,
    AnswerBanner
  },
  data() {
    return {
      banners: [{
          index: 1,
          type: 'article',
          bannerCover: 'http://lorempixel.com/400/200',
          item: {
            createTime: "2017-07-12 09:03:02.0",
            updateTime: "2017-07-12 09:03:02.0",
            id: 2,
            type: 1,
            profile: {
              createTime: "2017-07-12 09:03:01.0",
              updateTime: "2017-07-12 09:03:01.0",
              nickname: "喵喵喵的伙伴",
              bio: "Web 开发专家",
              avatar: null,
              id: 1,
              deleted: false
            },
            comments: [],
            likeCount: 0,
            commentCount: 0,
            title: "铲屎官必读文章(1)",
            summary: "简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介",
            content: "<p style=\"color:#63c;\">第一段</p><p>第二段</p>",
            cover: "http://lorempixel.com/400/200",
            deleted: true
          }
        },
        {
          index: 2,
          type: 'answer',
          bannerCover: 'http://lorempixel.com/400/200',
          item: {
            createTime: "2017-07-12 10:08:30.0",
            updateTime: "2017-07-12 10:08:30.0",
            id: 4,
            type: 3,
            profile: {
              createTime: "2017-07-12 10:08:29.0",
              updateTime: "2017-07-12 10:08:29.0",
              nickname: "喵喵喵的伙伴",
              bio: "Web 开发专家",
              avatar: null,
              id: 1,
              deleted: false
            },
            comments: [],
            likeCount: 0,
            commentCount: 0,
            content: "<p style=\"color:#63c;\">吃的！吃的！</p>",
            deleted: true
          }
        }
      ]
    }
  }
}
</script>
