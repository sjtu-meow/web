<template>
<div class="box">
  <div class="box-header">
    <h3 class="box-title">回答举报</h3>

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
          <th>回答ID</th>
          <th>回答预览</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <answer-report-list-row v-for="report in reports" :key="report.id" :report="report"
          @deleteAnswer="deleteAnswer" @recoverAnswer="recoverAnswer" @expandContent="expandContent"
          @closeReport="closeReport" @openReport="openReport" />
      </tbody>
    </table>
  </div>
  <!-- /.box-body -->

  <div v-if="pagination.totalPages > 1" class="box-footer clearfix">
    <pagination :pagination="pagination" @changePage="fetchAnswerReports" />
  </div>
</div>
</template>

<script>
import AnswerReportListRow from './AnswerReportListRow.vue'
import Pagination from '../Pagination.vue'


export default {
  name: 'AnswerBox',
  components: {
    AnswerReportListRow,
    Pagination
  },
  data() {
    return {
      reports: [],
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
    this.fetchAnswerReports(0)
  },
  methods: {
    fetchAnswerReports(page) {
      let url = ''
      if (this.keywordOfResult) {
        url = '/api/admin/reports/answers?' + 'page=' + page + '&size=' + this.pageSize + '&keyword=' + this.keywordOfResult
      } else {
        url = '/api/admin/reports/answers?' + 'page=' + page + '&size=' + this.pageSize
      }

      this.$http.get(url)
        .then(function(response) {
          this.reports = response.body.content;
          this.pagination.currentPage = response.body.number;
          this.pagination.totalPages = response.body.totalPages;
        }, function(response) {
          alert(response.body.message || '获取回答举报失败');
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
    deleteAnswer(answer) {
      this.$emit('deleteAnswer', answer)
    },
    recoverAnswer(answer) {
      this.$emit('recoverAnswer', answer)
    },
    expandContent(answer) {
      this.$emit('expandContent', answer)
    },
    search() {
      this.keywordOfResult = this.keywordFromInput;
      this.fetchAnswerReports(0);
    }
  }
}
</script>
