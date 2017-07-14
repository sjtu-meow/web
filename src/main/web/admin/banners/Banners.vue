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
      <article-banner v-if="banner.itemType === 1" :banner="banner"
        @deleteBanner="deleteBanner" @moveUp="moveUp" @moveDown="moveDown"
        @expandContent="expandContent"@triggerCoverInputClick="triggerCoverInputClick"/>
      <answer-banner v-if="banner.itemType === 3" :banner="banner"
        @deleteBanner="deleteBanner" @moveUp="moveUp" @moveDown="moveDown"
        @expandContent="expandContent"@triggerCoverInputClick="triggerCoverInputClick"/>
    </template>
  </section>

  <!-- For ajax image upload -->
  <input type="file" name="file" id="coverInput" style="display: none;" @change="uploadPicture" />

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
                <label>吧呢封面</label><span class="text-muted">（点击修改）</span>
                <a class="thumbnail" @click="triggerCoverInputClick(newBanner)">
                  <img :src="newBanner.image">
                </a>
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
      banners: [],
      newBanner: {
        itemType: '',
        itemId: '',
        image: 'http://lorempixel.com/400/200'
      },
      bannerToUpdateImage: {},
      rawHtml: ''
    }
  },
  created() {
    this.fetchBanners()
  },
  methods: {
    fetchBanners() {
      this.$http.get('/api/admin/banners')
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

      this.updateBanners();
    },
    moveUp(banner) {
      if (banner.displayOrder == 0) {
        return;
      }

      this.banners.splice(banner.displayOrder, 1, this.banners[banner.displayOrder - 1]);
      this.banners.splice(banner.displayOrder - 1, 1, banner);

      this.banners[banner.displayOrder].displayOrder = banner.displayOrder;
      banner.displayOrder = banner.displayOrder - 1;

      this.updateBanners()
    },
    moveDown(banner) {
      if (banner.displayOrder == this.banners.length - 1) {
        return;
      }

      this.banners.splice(banner.displayOrder, 1, this.banners[banner.displayOrder + 1]);
      this.banners.splice(banner.displayOrder + 1, 1, banner);

      this.banners[banner.displayOrder].displayOrder = banner.displayOrder;
      banner.displayOrder = banner.displayOrder + 1;

      this.updateBanners()
    },
    expandContent(rawHtml) {
      $('#content-detail-modal .modal-body').html(rawHtml);
      $('#content-detail-modal').modal('show');
    },
    triggerCoverInputClick(target) {
      this.bannerToUpdateImage = target;
      $('#coverInput').click();
    },
    uploadPicture(event) {
      // get token
      this.$http.get('/api/web/upload/token')
        .then(function(response) {
          const token = response.body.token

          // send form data
          let data = new FormData();
          data.append('file', event.target.files[0])
          data.append('token', token)
          this.$http.post('http://upload.qiniu.com/', data)
            .then(function(response) {
              const key = response.body.key;
              this.bannerToUpdateImage.image = 'http://osg5c99b1.bkt.clouddn.com/' + key

              // save updates (even change is made to new banner (not yet saved))
              this.updateBanners();
            }, function(response) {
              alert(response.body.error || '上传图片失败');
            })
        }, function(response) {
          alert(response.body.message || '获取token失败');
        })
    },
    addBanner() {
      let itemType = 4;  // an invalid value
      switch (this.newBanner.itemType) {
        case '回答':
          itemType = 3;
          break;
        case '文章':
          itemType = 1;
          break;
        default:
          return;
      }

      // add new banner
      this.banners.unshift({
        displayOrder: 0,
        itemType: itemType,
        itemId: this.newBanner.itemId,
        image: this.newBanner.image
      });
      for (var i = 1; i < this.banners.length; i++) {
        ++this.banners[i].displayOrder;
      }

      // update to server
      this.updateBanners();
      $('#add-banner-modal').modal('hide');
    },
    updateBanners() {
      let banners = [];
      for (var i = 0; i < this.banners.length; i++) {
        banners.push({
          displayOrder: this.banners[i].displayOrder,
          itemType: this.banners[i].itemType,
          itemId: this.banners[i].itemId,
          image: this.banners[i].image
        })
      }

      this.$http.put('/api/admin/banners', banners)
      .then(function(response) {
        this.fetchBanners();
      }, function(response) {
        alert(response.body.message || '更新吧呢失败');
      })
    }
  }
}
</script>
