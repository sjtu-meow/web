<template>
<section>
  <section class="content-header">
    <h1>
      吧呢管理
    </h1>
  </section>

  <section class="content">
    <div class="row">
      <div class="col-md-12">
        <div class="box">
          <div class="box-header">
            <h3 class="box-title">
              当前吧呢
              <button type="button" class="btn btn-default btn-xs" data-toggle="modal" data-target="#add-banner-modal">
                <span class="glyphicon glyphicon-plus"/>
              </button>
            </h3>
          </div>
          <!-- /.box-header -->
          <div class="box-body">
            <transition-group name="list">
              <template v-for="banner in banners">
                <moment-banner v-if="banner.itemType === 0" :banner="banner" :key="banner.id"
                  @deleteBanner="promptDeleteBanner" @moveUp="moveUp" @moveDown="moveDown"
                  @expandContent="expandContent"@triggerCoverInputClick="triggerCoverInputClick"/>
                <article-banner v-if="banner.itemType === 1" :banner="banner" :key="banner.id"
                  @deleteBanner="promptDeleteBanner" @moveUp="moveUp" @moveDown="moveDown"
                  @expandContent="expandContent"@triggerCoverInputClick="triggerCoverInputClick"/>
                <question-banner v-if="banner.itemType === 2" :banner="banner" :key="banner.id"
                  @deleteBanner="promptDeleteBanner" @moveUp="moveUp" @moveDown="moveDown"
                  @expandContent="expandContent"@triggerCoverInputClick="triggerCoverInputClick"/>
                <answer-banner v-if="banner.itemType === 3" :banner="banner" :key="banner.id"
                  @deleteBanner="promptDeleteBanner" @moveUp="moveUp" @moveDown="moveDown"
                  @expandContent="expandContent"@triggerCoverInputClick="triggerCoverInputClick"/>
              </template>
            </transition-group>
          </div>
          <!-- /.box-body -->
        </div>
        <!-- /.box -->
      </div>
    </div>
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
              <div class="form-group">
                <label class="control-label" for="new-banner-type">类型</label>
                <select class="form-control" id="new-banner-type" v-model="newBanner.itemTextualType" required>
                  <option disabled value="">请选择</option>
                  <option>点滴</option>
                  <option>文章</option>
                  <option>问题</option>
                  <option>回答</option>
                </select>
              </div>
              <div class="form-group">
                <label class="control-label" for="new-banner-item-id">项目ID</label>
                <input type="number" class="form-control" id="new-banner-item-id" v-model="newBanner.itemId" required/>
                <span class="help-block"></span>
              </div>
            </form>
            <form class="col-md-6">
              <label>内容预览</label>
              <h4>{{previewTitle}}</h4>
              <p v-for="line in previewContent.split('\n')">{{line}}</p>
            </form>
          </div>
          <p v-if="banners.length >= bannerSizeBound" class="text-danger">当前吧呢数量为 <b>{{banners.length}}</b>，系统仅保留前 <b>{{bannerSizeBound}}</b> 个吧呢</p>
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

  <!-- Delete Modal -->
  <div class="modal fade" id="delete-banner-modal" tabindex="-1" role="dialog" aria-labelledby="delete-banner-modal-title">
    <div class="modal-dialog modal-sm" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="delete-banner-modal-title">删除吧呢</h4>
        </div>
        <div class="modal-body">
          <p class="text-danger">确定删除吧呢<b>「{{promptForDeletion.substring(0, 30)}}{{promptForDeletion.length > 30 ? '…' : ''}}」</b>吗？</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          <button type="button" class="btn btn-danger" @click="deleteBanner">删除</button>
        </div>
      </div>
    </div>
  </div>
</section>
</template>

<script>
import ArticleBanner from './ArticleBanner.vue'
import AnswerBanner from './AnswerBanner.vue'
import QuestionBanner from './QuestionBanner.vue'
import MomentBanner from './MomentBanner.vue'

export default {
  components: {
    ArticleBanner,
    AnswerBanner,
    QuestionBanner,
    MomentBanner
  },
  data() {
    return {
      banners: [],
      newBanner: {
        itemTextualType: '',
        itemId: '',
        image: 'http://lorempixel.com/400/200',
        valid: false
      },
      previewTitle: '',
      previewContent: '',
      bannerToUpdateImage: {},
      bannerToDelete: {},
      promptForDeletion: '',
      bannerSizeBound: 5
    }
  },
  watch: {
    newBanner: {
      deep: true,
      handler(newVal) {
        if (newVal.itemTextualType === '' || newVal.itemId === '') {
          return;
        }

        switch (newVal.itemTextualType) {
          case '点滴':
            this.$http.get('/api/admin/moments/' + newVal.itemId).then(
              function(response) {
                if (response.body.deleted) {
                  this.previewTitle = '点滴已被删除';
                  this.newBanner.valid = false;
                } else {
                  this.previewTitle = '来自' + response.body.profile.nickname + '的点滴';
                  this.previewContent = response.body.content;
                  this.newBanner.valid = true;
                }
              },
              function(response) {
                this.previewTitle = response.body.message || '获取点滴失败';
                this.previewContent = '';
                this.newBanner.valid = false;
              }
            )
            break;
          case '文章':
            this.$http.get('/api/admin/articles/' + newVal.itemId).then(
              function(response) {
                if (response.body.deleted) {
                  this.previewTitle = '文章已被删除';
                  this.newBanner.valid = false;
                } else {
                  this.previewTitle = response.body.title;
                  this.previewContent = $('<div>' + response.body.content + '</div>').text();
                  this.newBanner.valid = true;
                }
              },
              function(response) {
                this.previewTitle = response.body.message || '获取文章失败';
                this.previewContent = '';
                this.newBanner.valid = false;
              }
            )
            break;
          case '问题':
            this.$http.get('/api/admin/questions/' + newVal.itemId).then(
              function(response) {
                if (response.body.deleted) {
                  this.previewTitle = '问题已被删除';
                  this.newBanner.valid = false;
                } else {
                  this.previewTitle = response.body.title;
                  this.previewContent = response.body.content;
                  this.newBanner.valid = true;
                }
              },
              function(response) {
                this.previewTitle = response.body.message || '获取问题失败';
                this.previewContent = '';
                this.newBanner.valid = false;
              }
            )
            break;
          case '回答':
            this.$http.get('/api/admin/answers/' + newVal.itemId).then(
              function(response) {
                if (response.body.deleted) {
                  this.previewTitle = '回答已被删除';
                  this.newBanner.valid = false;
                } else {
                  this.previewTitle = response.body.profile.nickname;

                  this.previewContent = $('<div>' + response.body.content + '</div>').text();
                  this.$http.get('/api/admin/questions/' + response.body.questionId)
                    .then(function(response) {
                      this.previewTitle += '的回答（' + response.body.title + '）';
                      this.newBanner.valid = true;
                    }, function(response) {
                      this.previewTitle = response.body.message || '获取问题失败';
                      this.previewContent = '';
                      this.newBanner.valid = false;
                    });
                }
              },
              function(response) {
                this.previewTitle = response.body.message || '获取回答失败';
                this.previewContent = '';
                this.newBanner.valid = false;
              }
            )
            break;
          default:
            this.previewTitle = '类型错误';
            this.previewContent = '';
            this.newBanner.valid = false;
        }
      }
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
    promptDeleteBanner(banner, prompt) {
      this.bannerToDelete = banner;
      this.promptForDeletion = prompt;
      $('#delete-banner-modal').modal('show');
    },
    deleteBanner() {
      this.banners.splice(this.bannerToDelete.displayOrder, 1);
      for (var i = this.bannerToDelete.displayOrder; i < this.banners.length; i++) {
        --this.banners[i].displayOrder;
      }

      this.updateBanners();
      $('#delete-banner-modal').modal('hide');
    },
    moveUp(banner) {
      if (banner.displayOrder == 0) {
        return;
      }

      this.banners.splice(banner.displayOrder, 1, this.banners[banner.displayOrder - 1]);
      this.banners.splice(banner.displayOrder - 1, 1, banner);

      this.banners[banner.displayOrder].displayOrder = banner.displayOrder;
      banner.displayOrder = banner.displayOrder - 1;

      setTimeout(this.updateBanners, 1000);
    },
    moveDown(banner) {
      if (banner.displayOrder == this.banners.length - 1) {
        return;
      }

      this.banners.splice(banner.displayOrder, 1, this.banners[banner.displayOrder + 1]);
      this.banners.splice(banner.displayOrder + 1, 1, banner);

      this.banners[banner.displayOrder].displayOrder = banner.displayOrder;
      banner.displayOrder = banner.displayOrder + 1;

      setTimeout(this.updateBanners, 1000);
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
      if (!this.newBanner.valid) {
        return;
      }

      let itemType = 4; // an invalid value
      switch (this.newBanner.itemTextualType) {
        case '点滴':
          itemType = 0;
          break;
        case '文章':
          itemType = 1;
          break;
        case '问题':
          itemType = 2;
          break;
        case '回答':
          itemType = 3;
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
      for (var i = 0; i < this.banners.length && i < this.bannerSizeBound; i++) {
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

<style>
.list-move {
  transition: transform 1s;
}
</style>
