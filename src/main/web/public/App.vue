<template>
<div id="app">
  <div class="container">
    <h1>summernote webpack test</h1>
    <div class="summernote"></div>
  </div>
</div>
</template>

<script>
export default {
  created() {
    $(function() {
      $('.summernote').summernote({
        height: 300, // set editor height
        minHeight: null, // set minimum height of editor
        maxHeight: null, // set maximum height of editor
        focus: true // set focus to editable area after initializing summernote
      });
    });
  }
}
</script>
