<template>
<section>
  <section class="content-header">
    <h1>
      当前吧呢
      <button type="button" class="btn btn-default" data-toggle="modal" data-target="#add-banner-modal">
        <span class="glyphicon glyphicon-plus"/>
      </button>
    </h1>
  </section>

  <section class="content">
    <template v-for="banner in banners">
      <article-banner v-if="banner.type === 'article'" :banner="banner"
        @deleteBanner="deleteBanner" @moveUp="moveUp" @moveDown="moveDown" @expandContent="expandContent"/>
      <answer-banner v-if="banner.type === 'answer'" :banner="banner"
        @deleteBanner="deleteBanner" @moveUp="moveUp" @moveDown="moveDown" @expandContent="expandContent"/>
    </template>
  </section>

  <!-- Add Modal -->
  <div class="modal fade" id="add-banner-modal" tabindex="-1" role="dialog" aria-labelledby="add-banner-modal-title">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="add-banner-modal-title">添加吧呢</h4>
        </div>
        <div class="modal-body">
          <div class="row">
            <form class="col-md-6">
              <div class="form-group">
                <label class="control-label" for="new-banner-cover">吧呢封面</label><span class="text-muted">（点击修改）</span>
                <a class="thumbnail" @click="triggerCoverInputClick">
                  <img :src="newBanner.bannerCoverUrl">
                </a>
                <input type="file" name="file" id="coverInput" style="display: none;" @change="uploadPicture" />
              </div>
            </form>
            <form class="col-md-6">
              <div class="form-group">
                <label class="control-label" for="new-banner-type">类型</label>
                <select class="form-control" id="new-banner-type" v-model="newBanner.itemType" required>
                  <option disabled value="">请选择</option>
                  <option>回答</option>
                  <option>文章</option>
                </select>
                <span class="help-block">{{newBanner.itemType}}</span>
              </div>
              <div class="form-group">
                <label class="control-label" for="new-banner-item-id">项目ID</label>
                <input type="number" class="form-control" id="new-banner-item-id" v-model="newBanner.itemId" required/>
                <span class="help-block"></span>
              </div>
            </form>
          </div>
          <p v-if="banners.length >= 5" class="text-danger">当前吧呢数量为 <b>{{banners.length}}</b>，系统仅保留前 <b>5</b> 个吧呢</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          <button type="button" class="btn btn-primary" @click="addBanner">添加</button>
        </div>
      </div>
    </div>
  </div>

  <!-- Expand Content Modal -->
  <div class="modal fade" id="content-detail-modal" tabindex="-1" role="dialog" aria-labelledby="content-detail-modal-title">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="content-detail-modal-title">内容详情</h4>
        </div>
        <div class="modal-body">
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        </div>
      </div>
    </div>
  </div>
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
          displayOrder: 0,
          type: 'article',
          bannerCoverUrl: 'http://lorempixel.com/400/200',
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
            title: "铲屎官必读文章(0)",
            summary: "简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介",
            content: "<p style=\"color:#63c;\">第一段</p><p>第二段</p>",
            cover: "http://lorempixel.com/400/200",
            deleted: true
          }
        },
        {
          displayOrder: 1,
          type: 'article',
          bannerCoverUrl: 'http://lorempixel.com/400/200',
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
          displayOrder: 2,
          type: 'article',
          bannerCoverUrl: 'http://lorempixel.com/400/200',
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
            title: "铲屎官必读文章(2)",
            summary: "简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介",
            content: "<p style=\"color:#63c;\">第一段</p><p>第二段</p>",
            cover: "http://lorempixel.com/400/200",
            deleted: true
          }
        },
        {
          displayOrder: 3,
          type: 'article',
          bannerCoverUrl: 'http://lorempixel.com/400/200',
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
            title: "铲屎官必读文章(3)",
            summary: "简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介",
            content: "<p style=\"color:#63c;\">第一段</p><p>第二段</p>",
            cover: "http://lorempixel.com/400/200",
            deleted: true
          }
        },
        {
          displayOrder: 4,
          type: 'answer',
          bannerCoverUrl: 'http://lorempixel.com/400/200',
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
      ],
      newBanner: {
        itemType: '',
        itemId: 66,
        bannerCoverUrl: 'http://lorempixel.com/400/200'
      },
      rawHtml: ''
    }
  },
  created() {
    this.fetchBanners()
  },
  methods: {
    fetchBanners() {
      this.$http.get('http://106.14.156.19/api/admin/banners')
        .then(function(response) {
          this.banners = response.body;
        }, function(response) {
          alert(response.body.message || '获取吧呢失败');
        })
    },
    deleteBanner(banner) {
      this.banners.splice(banner.displayOrder, 1);
      for (var i = banner.displayOrder; i < this.banners.length; i++) {
        --this.banners[i].displayOrder;
      }

      // TODO: send changes to server
    },
    moveUp(banner) {
      if (banner.displayOrder == 0) {
        return;
      }

      this.banners.splice(banner.displayOrder, 1, this.banners[banner.displayOrder - 1]);
      this.banners.splice(banner.displayOrder - 1, 1, banner);

      this.banners[banner.displayOrder].displayOrder = banner.displayOrder;
      banner.displayOrder = banner.displayOrder - 1;

      // TODO: send changes to server
    },
    moveDown(banner) {
      if (banner.displayOrder == this.banners.length - 1) {
        return;
      }

      this.banners.splice(banner.displayOrder, 1, this.banners[banner.displayOrder + 1]);
      this.banners.splice(banner.displayOrder + 1, 1, banner);

      this.banners[banner.displayOrder].displayOrder = banner.displayOrder;
      banner.displayOrder = banner.displayOrder + 1;

      // TODO: send changes to server
    },
    expandContent(rawHtml) {
      console.log(rawHtml);
      $('#content-detail-modal .modal-body').html(rawHtml);
      $('#content-detail-modal').modal('show');
    },
    triggerCoverInputClick: function() {
      $('#coverInput').click();
    },
    uploadPicture: function(event) {
      // get token
      this.$http.get('http://106.14.156.19/api/web/upload/token')
        .then(function(response) {
          const token = response.body.token

          // send form data
          let data = new FormData();
          data.append('file', event.target.files[0])
          data.append('token', token)
          this.$http.post('http://upload.qiniu.com/', data)
            .then(function(response) {
              const key = response.body.key;
              this.newBanner.bannerCoverUrl = 'http://osg5c99b1.bkt.clouddn.com/' + key
            }, function(response) {
              alert(response.body.error || '上传图片失败');
            })
        }, function(response) {
          alert(response.body.message || '获取token失败');
        })
    },
    addBanner() {
      let banners = [];
      let itemType = '';
      switch (this.newBanner.itemType) {
        case '回答':
          itemType = 'answer';
          break;
        case '文章':
          itemType = 'article';
          break;
        default:
          return;
      }

      // add new banner
      banners.push({
        displayOrder: 0,
        itemType: itemType,
        itemId: this.newBanner.itemId,
        image: this.newBanner.bannerCoverUrl
      })

      // add the rest of banners (4 at most)
      for (var i = 0; i < this.banners.length && i < 4; i++) {
        banners.push({
          displayOrder: this.banners[i].displayOrder,
          itemType: this.banners[i].itemType,
          itemId: this.banners[i].itemId,
          image: this.banners[i].bannerCoverUrl
        })
      }

      // update to server
      this.$http.put('http://106.14.156.19/api/admin/banners', {
        data: banners
      }).then(function(response) {
        this.fetchBanners();
        $('#add-banner-modal').modal('hide');
      }, function(response) {
        alert(response.body.message || '更新吧呢失败');
      })
    }
  }
}
</script>
