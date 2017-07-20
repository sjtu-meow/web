<template>
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
          <th>点滴预览</th>
          <th>点滴媒体</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <moment-list-row v-for="moment in moments" :key="moment.id" :moment="moment"
          @deleteMoment="deleteMoment" @recoverMoment="recoverMoment" @expandContent="expandContent" />
      </tbody>
    </table>
  </div>
  <!-- /.box-body -->

  <div v-if="pagination.totalPages > 1" class="box-footer clearfix">
    <pagination :pagination="pagination" @changePage="fetchMoments" />
  </div>
</div>
</template>

<script>
import MomentListRow from './MomentListRow.vue'
import Pagination from '../Pagination.vue'


export default {
  name: 'MomentBox',
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
      keywordOfResult: ''
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
    deleteMoment(moment) {
      this.$emit('deleteMoment', moment)
    },
    recoverMoment(moment) {
      this.$emit('recoverMoment', moment)
    },
    expandContent(moment) {
      this.$emit('expandContent', moment)
    },
    search() {
      this.keywordOfResult = this.keywordFromInput;
      this.fetchMoments(0);
    }
  }
}
</script>
