<template>
<div id="app">
  <div class="container">
    <div class="header clearfix">
      <nav>
        <ul class="nav nav-pills pull-right">
          <li role="presentation"><a href="/logout">退出</a></li>
        </ul>
      </nav>
      <h3 class="text-muted">喵喵喵的文章编辑器</h3>
    </div>

    <div class="row">
      <div class="col-md-4">
        <a href="#" class="thumbnail">
          <img width="100%" src="https://pbs.twimg.com/profile_images/625633822235693056/lNGUneLX.jpg">
        </a>
        <p class="text-muted">点击修改封面</p>
      </div>
      <div class="col-md-8">
        <form>
          <div class="form-group">
            <label for="articleTitle">文章标题</label>
            <input type="text" class="form-control" id="articleTitle" placeholder="在这里写下文章标题">
          </div>
          <div class="form-group">
            <label for="articleSummary">文章简介</label>
            <textarea class="form-control" id="articleSummary" placeholder="在这里写下文章简介" rows="4"/>
          </div>
        </form>
      </div>
    </div>

    <div class="summernote"></div>

    <footer class="footer">
      <p>&copy; 2017 SJTU-Meow.</p>
    </footer>

  </div>
</div>
</template>

<script>
export default {
  created() {
    $(function() {
      $('.summernote').summernote({
        lang: 'zh-CN',
        minHeight: 200,
        focus: true,
        placeholder: '书写肆意喵生……',
        toolbar: [
          ['style', ['style']],
          ['style', ['bold', 'italic', 'clear']],
          ['color', ['color']],
          ['para', ['ul', 'ol', 'paragraph']],
          ['Insert', ['link', 'picture', 'video']]
        ]
      });
    });
  }
}
</script>

<style>
/* Space out content a bit */

body {
  padding-top: 20px;
  padding-bottom: 20px;
}








/* Everything but the jumbotron gets side spacing for mobile first views */

.header,
.footer {
  padding-right: 15px;
  padding-left: 15px;
}








/* Custom page header */

.header {
  padding-bottom: 20px;
  border-bottom: 1px solid #e5e5e5;
}








/* Make the masthead heading the same height as the navigation */

.header h3 {
  margin-top: 0;
  margin-bottom: 0;
  line-height: 40px;
}








/* Custom page footer */

.footer {
  padding-top: 19px;
  color: #777;
  border-top: 1px solid #e5e5e5;
}








/* Customize container */

@media (min-width: 768px) {
  .container {
    max-width: 730px;
  }
}

.container-narrow>hr {
  margin: 30px 0;
}








/* Responsive: Portrait tablets and up */

@media screen and (min-width: 768px) {
  /* Remove the padding we set earlier */
  .header,
  .footer {
    padding-right: 0;
    padding-left: 0;
  }
  /* Space out the masthead */
  .header {
    margin-bottom: 30px;
  }
}
</style>
