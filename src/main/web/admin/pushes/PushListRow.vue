<template>
<tr>
  <td>{{push.id}}</td>
  <td>{{push.text}}</td>
  <td>{{itemTextualType}}</td>
  <td>{{push.itemId}}</td>
</tr>
</template>

<script>
export default {
  name: 'PushListRow',
  props: ['push'],
  computed: {
    itemTextualType() {
      let result = '';
      switch (this.push.itemType) {
        case 0:
          result = '点滴';
          break;
        case 1:
          result = '文章';
          break;
        case 2:
          result = '问题';
          break;
        case 3:
          result = '回答';
          break;
        default:
          result = '未知类型';
      }
      return result;
    }
  }
}
</script>
