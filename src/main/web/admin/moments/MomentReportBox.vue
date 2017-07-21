<template>
<div class="box">
  <div class="box-header">
    <h3 class="box-title">点滴举报</h3>

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
          <th>举报人</th>
          <th>举报信息</th>
          <th>点滴ID</th>
          <th>点滴预览</th>
          <th>点滴媒体</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <moment-report-list-row v-for="report in reports" :key="report.id" :report="report"
          @deleteMoment="deleteMoment" @recoverMoment="recoverMoment" @expandContent="expandContent"
          @closeReport="closeReport" @openReport="openReport" />
      </tbody>
    </table>
  </div>
  <!-- /.box-body -->

  <div v-if="pagination.totalPages > 1" class="box-footer clearfix">
    <pagination :pagination="pagination" @changePage="fetchMomentReports" />
  </div>
</div>
</template>

<script>
import MomentReportListRow from './MomentReportListRow.vue'
import Pagination from '../Pagination.vue'


export default {
  name: 'MomentBox',
  components: {
    MomentReportListRow,
    Pagination
  },
  data() {
    return {
      reports: [{
        id: 1,
        profile: {
          nickname: '小蛤蛤',
          id: 3
        },
        reason: '吓死我了这个点滴',
        itemId: 1,
        itemType: 0,
        closed: false
      }],
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
    this.fetchMomentReports(0)
  },
  methods: {
    fetchMomentReports(page) {
      let url = ''
      if (this.keywordOfResult) {
        url = '/api/admin/reports/moments?' + 'page=' + page + '&size=' + this.pageSize + '&keyword=' + this.keywordOfResult
      } else {
        url = '/api/admin/reports/moments?' + 'page=' + page + '&size=' + this.pageSize
      }

      this.$http.get(url)
        .then(function(response) {
          this.reports = response.body.content;
          this.pagination.currentPage = response.body.number;
          this.pagination.totalPages = response.body.totalPages;
        }, function(response) {
          alert(response.body.message || '获取点滴举报失败');
        })
    },
    closeReport(report) {
      this.$http.patch('/api/admin/reports/' + report.id, {
        closed: true
      }).then(function(response) {
        report.closed = true;
      }, function(response) {
        alert(response.body.message || '修改失败');
      })
    },
    openReport(report) {
      this.$http.patch('/api/admin/reports/' + report.id, {
        closed: false
      }).then(function(response) {
        report.closed = false;
      }, function(response) {
        alert(response.body.message || '修改失败');
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
      this.fetchMomentReports(0);
    }
  }
}
</script>
