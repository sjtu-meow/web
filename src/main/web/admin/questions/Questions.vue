<template>
<section>
  <section class="content-header">
    <h1>所有问答</h1>
  </section>
  <section class="content">
    <div class="table-responsive">
      <table class="table table-hover">
        <thead>
          <tr>
            <th class="col-md-1">#</th>
            <th class="col-md-1">用户</th>
            <th class="col-md-3">文字预览</th>
            <th class="col-md-6">媒体</th>
            <th class="col-md-1"></th>
          </tr>
        </thead>
        <tbody>
          <!-- <moment-list-row v-for="moment in moments" :key="moment.id" :moment="moment" @deleteMoment="promptDeleteMoment" @recoverMoment="promptRecoverMoment" /> -->
        </tbody>
      </table>
    </div>
  </section>
</section>
</template>

<script>
export default {
  name: 'Questions',
  data() {
    return {
      questions: []
    }
  }
}
</script>
