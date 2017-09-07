<template>
<div class="box">
  <div class="box-header">
    <h3 class="box-title">问题举报</h3>

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
          <th>问题ID</th>
          <th>问题预览</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <question-report-list-row v-for="report in reports" :key="report.id" :report="report" @deleteQuestion="deleteQuestion" @recoverQuestion="recoverQuestion" @expandContent="expandContent" @closeReport="closeReport" @openReport="openReport" />
      </tbody>
    </table>
  </div>
  <!-- /.box-body -->

  <div v-if="pagination.totalPages > 1" class="box-footer clearfix">
    <pagination :pagination="pagination" @changePage="fetchQuestionReports" />
  </div>
</div>
</template>

<script>
import QuestionReportListRow from './QuestionReportListRow.vue'
import Pagination from '../Pagination.vue'

export default {
  name: 'QuestionReportBox',
  components: {
    QuestionReportListRow,
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
    this.fetchQuestionReports(0)
  },
  methods: {
    fetchQuestionReports(page) {
      let url = ''
      if (this.keywordOfResult) {
        url = '/api/admin/reports/questions?' + 'page=' + page + '&size=' + this.pageSize + '&keyword=' + this.keywordOfResult
      } else {
        url = '/api/admin/reports/questions?' + 'page=' + page + '&size=' + this.pageSize
      }

      this.$http.get(url)
        .then(function(response) {
          this.reports = response.body.content;
          this.pagination.currentPage = response.body.number;
          this.pagination.totalPages = response.body.totalPages;
        }, function(response) {
          alert(response.body.message || '获取问题举报失败');
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
    deleteQuestion(question) {
      this.$emit('deleteQuestion', question)
    },
    recoverQuestion(question) {
      this.$emit('recoverQuestion', question)
    },
    expandContent(question) {
      this.$emit('expandContent', question)
    },
    search() {
      this.keywordOfResult = this.keywordFromInput;
      this.fetchQuestionReports(0);
    }
  }
}
</script>
