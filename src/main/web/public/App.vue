<template>
<div id="app">
  <div class="container">
    <div class="header clearfix">
      <nav>
        <ul class="nav nav-pills pull-right">
          <li role="presentation"><a href="#" @click="saveArticle">保存</a></li>
          <li role="presentation"><a href="#" @click="postArticle">发布</a></li>
          <li role="presentation"><a href="/logout">退出</a></li>
        </ul>
      </nav>
      <h3 class="text-muted">喵喵喵的文章编辑器</h3>
    </div>

    <div class="row">
      <div class="col-md-4">
        <form action="/image/avatar" method="post" enctype="multipart/form-data">
          <div class="form-group">
            <label class="control-label" for="article-title">封面</label><span class="text-muted">（点击修改）</span>
            <a href="#" class="thumbnail" @click="triggerCoverInputClick">
              <img width="100%" src="https://i.ytimg.com/vi/prALrHUJ8Ns/hqdefault.jpg">
            </a>
            <input type="file" name="file" id="cover-input" style="display: none;" @change="uploadPicture" />
          </div>
        </form>
      </div>
      <div class="col-md-8">
        <form>
          <div class="form-group">
            <label for="article-title">文章标题</label>
            <input type="text" class="form-control" id="article-title" placeholder="在这里写下文章标题" v-model="title">
          </div>
          <div class="form-group">
            <label for="article-summary">文章简介</label>
            <textarea class="form-control" id="article-summary" placeholder="在这里写下文章简介" rows="4" v-model="summary" />
          </div>
        </form>
      </div>
    </div>

    <div id="summernote"></div>

    <footer class="footer">
      <p>&copy; 2017 SJTU-Meow.</p>
    </footer>

  </div>
</div>
</template>

<script>
import Vue from 'vue'

export default {
  name: 'app',
  data() {
    return {
      title: '',
      summary: ''
    }
  },
  created() {
    $(function() {
      // initialize editor
      $('#summernote').summernote({
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
        ],
        callbacks: {
          // upload image to Qiniu
          onImageUpload: function(files) {
            var imgNode = document.createElement('img');
            imgNode.setAttribute('src', 'https://i.ytimg.com/vi/prALrHUJ8Ns/hqdefault.jpg')

            // upload image to server and create imgNode...
            $('#summernote').summernote('insertNode', imgNode);
          },
        }

      });
    });
  },
  methods: {
    getArticleHtml() {
      return $('#summernote').summernote('code');
    },
    saveArticle() {
      alert('还没实现呢');
    },
    postArticle() {
      alert('还没实现呢');
    },
    triggerCoverInputClick() {
      $('#cover-input').click();
    },
    uploadPicture: function(event) {
      let data = new FormData();
      data.append('file', event.target.files[0])
      //TODO: change the way of uploading avatar image
    }
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
