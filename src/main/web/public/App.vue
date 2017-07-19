<template>
<div id="app">
  <div class="container">
    <!-- nav bar header -->
    <div class="header clearfix">
      <nav>
        <ul class="nav nav-pills pull-right">
          <li role="presentation"><a href="#" @click="saveArticle">保存</a></li>
          <li role="presentation"><a href="#" @click="postArticle">发布</a></li>
          <li role="presentation">
            <a v-show="loggedIn" @click="logout">退出</a>
            <a v-show="!loggedIn" data-toggle="modal" data-target="#loginModal">登录</a>
          </li>
        </ul>
      </nav>
      <h3 class="text-muted">喵喵喵的文章编辑器🐱</h3>
    </div>

    <!-- article meta data -->
    <div class="row">
      <div class="col-md-4">
        <form action="/image/avatar" method="post" enctype="multipart/form-data">
          <div class="form-group">
            <label class="control-label" for="article-title">封面</label><span class="text-muted">（点击修改）</span>
            <a href="#" class="thumbnail" @click="triggerCoverInputClick">
              <img width="100%" :src="coverUrl">
            </a>
            <input type="file" name="file" id="coverInput" style="display: none;" @change="uploadPicture" />
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

    <!-- summernote entry point -->
    <div id="summernote"></div>

    <!-- login modal -->
    <div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="loginModalTitle">
      <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
          <!-- title -->
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="loginModalTitle">登录</h4>
          </div>

          <!-- form body -->
          <div class="modal-body">
            <form>
              <div class="form-group">
                <label for="phone">手机号</label>
                <input type="text" class="form-control" id="phone" placeholder="手机号" v-model="phone">
              </div>
              <div class="form-group">
                <label for="password">密码</label>
                <input type="password" class="form-control" id="password" placeholder="密码" v-model="password">
              </div>
            </form>
          </div>

          <!-- footer button -->
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" @click="login">登录</button>
          </div>
        </div>
      </div>
    </div>

    <footer class="footer">
      <p>&copy; 2017 SJTU-Meow.</p>
    </footer>

  </div>
</div>
</template>

<script>
import Vue from 'vue'
import VueResource from 'vue-resource'

Vue.use(VueResource);

export default {
  name: 'app',
  data() {
    return {
      loggedIn: true,
      title: '',
      summary: '',
      coverUrl: 'https://i.ytimg.com/vi/prALrHUJ8Ns/hqdefault.jpg'
    }
  },
  created() {
    const vueModule = this;
    // initialize editor
    $(function() {
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
          ['insert', ['link', 'picture', 'video']],
          ['code', ['codeview']]
        ],
        callbacks: {
          // upload image to Qiniu
          onImageUpload: function(files) {
            // get token
            vueModule.$http.get('/api/web/upload/token')
            .then(function(response) {
              const token = response.body.token

              for (var i = 0; i < files.length; i++) {
                let file = files[i]

                // send form data
                let data = new FormData();
                data.append('file', file)
                data.append('token', token)
                vueModule.$http.post('http://upload.qiniu.com/', data)
                .then(function(response) {
                  const key = response.body.key;
                  let imageUrl = 'http://osg5c99b1.bkt.clouddn.com/' + key
                  let imgNode = document.createElement('img');
                  imgNode.setAttribute('src', imageUrl)

                  $('#summernote').summernote('insertNode', imgNode);
                }, function(response) {
                  alert(response.body.error || '上传图片失败');
                })
              }
            }, function(response) {
              alert(response.body.message || '获取token失败');
            })
          },
        }
      });
    });
  },
  methods: {
    logout() {
      this.$http.delete('/api/web/auth')
        .then(function(response) {
          this.loggedIn = false;
        }, function(response) {
          alert(response.body.message || '退出失败');
        })
    },
    getArticleHtml() {
      return $('#summernote').summernote('code');
    },
    saveArticle() {
      alert('还没实现呢');
    },
    postArticle() {
      this.$http.post('/api/web/articles', {
        title: this.title,
        summary: this.summary,
        content: this.getArticleHtml(),
        cover: this.coverUrl
      }).then(function(response) {
          alert('发布成功')
        }, function(response) {
          alert(response.body.error || '发布失败');
        })
    },
    triggerCoverInputClick: function() {
      $('#coverInput').click();
    },
    uploadPicture: function(event) {
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
              this.coverUrl = 'http://osg5c99b1.bkt.clouddn.com/' + key
            }, function(response) {
              alert(response.body.error || '上传图片失败');
            })
        }, function(response) {
          alert(response.body.message || '获取token失败');
        })
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
