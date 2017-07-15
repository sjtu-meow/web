<template>
<section>
  <section class="content-header">
    <h1>点滴管理</h1>
  </section>
  <section class="content">
    <div class="row">
      <div class="col-md-12">
        <div class="box">
          <div class="box-header">
            <h3 class="box-title">所有点滴</h3>

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
                  <th>#</th>
                  <th>用户</th>
                  <th>点滴文字预览</th>
                  <th>媒体</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                <moment-list-row v-for="moment in moments" :key="moment.id" :moment="moment" @deleteMoment="promptDeleteMoment" @recoverMoment="promptRecoverMoment" @expandContent="expandContent" />
              </tbody>
            </table>
          </div>
          <!-- /.box-body -->

          <div class="box-footer clearfix">
            <pagination :pagination="pagination" @changePage="fetchMoments" />
          </div>
        </div>
        <!-- /.box -->
      </div>
    </div>
  </section>

  <!-- Delete Modal -->
  <div class="modal fade" id="delete-moment-modal" tabindex="-1" role="dialog" aria-labelledby="delete-moment-modal-title">
    <div class="modal-dialog modal-sm" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="delete-moment-modal-title">删除点滴</h4>
        </div>
        <div class="modal-body">
          <p class="text-danger">确定删除 <b>{{momentToDelete.profile.nickname}}</b> 的点滴<b>「{{momentToDelete.content.substring(0, 30)}}{{momentToDelete.content.length > 30 ? '…' : ''}}」</b>吗？</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          <button type="button" class="btn btn-danger" @click="deleteMoment">删除</button>
        </div>
      </div>
    </div>
  </div>

  <!-- Recover Modal -->
  <div class="modal fade" id="recover-moment-modal" tabindex="-1" role="dialog" aria-labelledby="recover-moment-modal-title">
    <div class="modal-dialog modal-sm" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="recover-moment-modal-title">删除点滴</h4>
        </div>
        <div class="modal-body">
          <p>确定恢复 <b>{{momentToRecover.profile.nickname}}</b> 的点滴<b>「{{momentToRecover.content.substring(0, 30)}}{{momentToRecover.content.length > 30 ? '…' : ''}}」</b>吗？</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          <button type="button" class="btn btn-primary" @click="recoverMoment">恢复</button>
        </div>
      </div>
    </div>
  </div>

  <!-- Expand Content Modal -->
  <div class="modal fade" id="moment-content-detail-modal" tabindex="-1" role="dialog" aria-labelledby="moment-content-detail-modal-title">
    <div class="modal-dialog modal-sm" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="moment-content-detail-modal-title">点滴全文</h4>
        </div>
        <div class="modal-body">
          <p v-for="line in momentToShow.content.split('\n')">
            {{line}}
          </p>
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
import MomentListRow from './MomentListRow.vue'
import Pagination from '../Pagination.vue'

export default {
  name: 'Moments',
  components: {
    MomentListRow,
    Pagination
  },
  data() {
    return {
      moments: [],
      pagination: {
        currentPage: 0,
        totalPages: 1
      },
      pageSize: 2,
      keywordFromInput: '',
      keywordOfResult: '',
      momentToDelete: {
        profile: {
          nickname: 'haha'
        },
        content: '+1s now'
      },
      momentToRecover: {
        profile: {
          nickname: 'haha'
        },
        content: '+1s now'
      },
      momentToShow: {
        profile: {
          nickname: 'haha'
        },
        content: '+1s now'
      }
    }
  },
  created() {
    this.fetchMoments(0)
  },
  methods: {
    fetchMoments(page) {
      let url = ''
      if (this.keywordOfResult) {
        url = '/api/admin/moments?' + 'page=' + page + '&size=' + this.pageSize + '&keyword=' + this.keywordOfResult
      } else {
        url = '/api/admin/moments?' + 'page=' + page + '&size=' + this.pageSize
      }

      this.$http.get(url)
        .then(function(response) {
          this.moments = response.body.content;
          this.pagination.currentPage = response.body.number;
          this.pagination.totalPages = response.body.totalPages;
        }, function(response) {
          alert(response.body.message || '获取点滴失败');
        })
    },
    promptDeleteMoment(moment) {
      this.momentToDelete = moment;
      $('#delete-moment-modal').modal('show');
    },
    deleteMoment() {
      this.$http.delete('/api/admin/moments/' + this.momentToDelete.id)
        .then(function(response) {
          $('#delete-moment-modal').modal('hide');
          this.momentToDelete.deleted = true;
        }, function(response) {
          alert(response.body.message || '修改失败');
        })
    },
    promptRecoverMoment(moment) {
      this.momentToRecover = moment;
      $('#recover-moment-modal').modal('show');
    },
    recoverMoment() {
      this.$http.patch('/api/admin/moments/' + this.momentToRecover.id, {
        isDeleted: false
      }).then(function(response) {
        $('#recover-moment-modal').modal('hide');
        this.momentToRecover.deleted = false;
      }, function(response) {
        alert(response.body.message || '修改失败');
      })
    },
    expandContent(moment) {
      this.momentToShow = moment;
      $('#moment-content-detail-modal').modal('show');
    },
    search() {
      this.keywordOfResult = this.keywordFromInput;
      this.fetchMoments(0);
    }
  }
}
</script>
