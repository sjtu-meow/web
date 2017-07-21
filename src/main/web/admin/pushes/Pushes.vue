<template>
<section>
  <section class="content-header">
    <h1>推送管理</h1>
  </section>
  <section class="content">
    <div class="row">
      <div class="col-md-12">
        <div class="box">
          <div class="box-header">
            <h3 class="box-title">所有推送</h3>

            <div class="box-tools">
              <div class="input-group input-group-sm" style="width: 150px;">
                <input type="text" class="form-control pull-right" placeholder="搜索" v-model="keywordFromInput">
                <div class="input-group-btn">
                  <button type="button" class="btn btn-default" @click="search"><i class="fa fa-search"></i></button>
                </div>
              </div>
            </div>
          </div>
          <!-- /.box-header -->
          <div class="box-body table-responsive no-padding">
            <table class="table table-hover">
              <thead>
                <tr>
                  <th>
                    <button class="btn btn-default btn-xs" data-toggle="modal" data-target="#add-push-modal">
                          <span class="glyphicon glyphicon-plus"/>
                        </button>
                  </th>
                  <th>文字</th>
                  <th>类型</th>
                  <th>项目ID</th>
                </tr>
              </thead>
              <tbody>
                <push-list-row v-for="push in pushes" :key="push.id" :push="push" />
              </tbody>
            </table>
          </div>
          <!-- /.box-body -->

          <div v-if="pagination.totalPages > 1" class="box-footer clearfix">
            <pagination :pagination="pagination" @changePage="fetchPushes" />
          </div>
        </div>
        <!-- /.box -->
      </div>
    </div>

    <div class="modal fade" id="add-push-modal" tabindex="-1" role="dialog" aria-labelledby="add-push-modal-title">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="add-push-modal-title">添加推送</h4>
          </div>
          <div class="modal-body">
            <div class="row">
              <form class="col-md-6">
                <div class="form-group">
                  <label class="control-label" for="new-push-description">文字</label>
                  <textarea class="form-control" id="new-push-description" v-model="newPush.text" rows="3" required/>
                  <span class="help-block"></span>
                </div>
                <div class="form-group">
                  <label class="control-label" for="new-push-type">类型</label>
                  <select class="form-control" id="new-push-type" v-model="newPush.itemTextualType" required>
                    <option disabled value="">请选择</option>
                    <option>点滴</option>
                    <option>文章</option>
                    <option>问题</option>
                    <option>回答</option>
                  </select>
                </div>
                <div class="form-group">
                  <label class="control-label" for="new-push-item-id">项目ID</label>
                  <input type="number" class="form-control" id="new-push-item-id" v-model="newPush.itemId" required/>
                  <span class="help-block"></span>
                </div>
              </form>
              <div class="col-md-6">
                <label>内容预览</label>
                <h4>{{previewTitle}}</h4>
                <p v-for="line in previewContent.split('\n')">{{line}}</p>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" @click="addPush">推送</button>
          </div>
        </div>
      </div>
    </div>
  </section>
</section>
</template>

<script>
import Pagination from '../Pagination.vue'
import PushListRow from './PushListRow.vue'

export default {
  name: 'Pushes',
  components: {
    Pagination,
    PushListRow
  },
  data() {
    return {
      pushes: [],
      pagination: {
        currentPage: 0,
        totalPages: 1
      },
      pageSize: 2,
      keywordFromInput: '',
      keywordOfResult: '',
      newPush: {
        text: '',
        itemTextualType: '',
        itemId: '',
        valid: false
      },
      previewTitle: '',
      previewContent: '',
    }
  },
  watch: {
    newPush: {
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
                  this.newPush.valid = false;
                } else {
                  this.previewTitle = '来自' + response.body.profile.nickname + '的点滴';
                  this.previewContent = response.body.content;
                  this.newPush.valid = true;
                }
              },
              function(response) {
                this.previewTitle = response.body.message || '获取点滴失败';
                this.previewContent = '';
                this.newPush.valid = false;
              }
            )
            break;
          case '文章':
            this.$http.get('/api/admin/articles/' + newVal.itemId).then(
              function(response) {
                if (response.body.deleted) {
                  this.previewTitle = '文章已被删除';
                  this.newPush.valid = false;
                } else {
                  this.previewTitle = response.body.title;
                  this.previewContent = $(response.body.content).text();
                  this.newPush.valid = true;
                }
              },
              function(response) {
                this.previewTitle = response.body.message || '获取文章失败';
                this.previewContent = '';
                this.newPush.valid = false;
              }
            )
            break;
          case '问题':
            this.$http.get('/api/admin/questions/' + newVal.itemId).then(
              function(response) {
                if (response.body.deleted) {
                  this.previewTitle = '问题已被删除';
                  this.newPush.valid = false;
                } else {
                  this.previewTitle = response.body.title;
                  this.previewContent = response.body.content;
                  this.newPush.valid = true;
                }
              },
              function(response) {
                this.previewTitle = response.body.message || '获取问题失败';
                this.previewContent = '';
                this.newPush.valid = false;
              }
            )
            break;
          case '回答':
            this.$http.get('/api/admin/answers/' + newVal.itemId).then(
              function(response) {
                if (response.body.deleted) {
                  this.previewTitle = '回答已被删除';
                  this.newPush.valid = false;
                } else {
                  this.previewTitle = response.body.profile.nickname;
                  this.previewContent = $(response.body.content).text();
                  this.$http.get('/api/admin/questions/' + response.body.questionId)
                    .then(function(response) {
                      this.previewTitle += '的回答（' + response.body.title + '）';
                      this.newPush.valid = true;
                    }, function(response) {
                      this.previewTitle = response.body.message || '获取问题失败';
                      this.previewContent = '';
                      this.newPush.valid = false;
                    });
                }
              },
              function(response) {
                this.previewTitle = response.body.message || '获取回答失败';
                this.previewContent = '';
                this.newPush.valid = false;
              }
            )
            break;
          default:
            this.previewTitle = '类型错误';
            this.previewContent = '';
            this.newPush.valid = false;
        }
      }
    }
  },
  created: function() {
    this.fetchPushes(0)
  },
  methods: {
    fetchPushes: function(page) {
      let url = ''
      if (this.keywordOfResult) {
        url = '/api/admin/pushes?' + 'page=' + page + '&size=' + this.pageSize + '&keyword=' + this.keywordOfResult
      } else {
        url = '/api/admin/pushes?' + 'page=' + page + '&size=' + this.pageSize
      }

      this.$http.get(url)
        .then(function(response) {
          this.pushes = response.body.content;
          this.pagination.currentPage = response.body.number;
          this.pagination.totalPages = response.body.totalPages;
        }, function(response) {
          alert(response.body.message || '获取推送失败');
        })
    },
    promptAddPush: function(event) {
      $('#add-push-modal').modal('show');
    },
    addPush() {
      if (this.newPush.valid) {
        this.$http.post('/api/admin/pushes', {})
          .then(function(response) {
            this.fetchPushes(this.pagination.totalPages - 1);
            $('#add-push-modal').modal('hide');
          }, function(response) {
            alert(response.body.message)
          });
      }
    },
    search() {
      this.keywordOfResult = this.keywordFromInput;
      this.fetchPushes(0);
    }
  }
}
</script>
