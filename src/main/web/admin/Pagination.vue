<template>
<nav>
  <ul class="pagination" v-if="pagination.totalPages > 1">
    <li :class="{'disabled': pagination.currentPage === 0}">
      <a href="#" @click.prevent="changePage(0)">
        <span>«</span>
      </a>
    </li>
    <li v-for="num in array" :class="{ active: num === pagination.currentPage }">
      <a href="#" @click.prevent="changePage(num)">{{ num + 1 }}</a>
    </li>
    <li :class="{'disabled': this.pagination.currentPage >= this.pagination.totalPages - 1}">
      <a href="#" @click.prevent="changePage(pagination.totalPages - 1)">
        <span aria-hidden="true">»</span>
      </a>
    </li>
  </ul>
</nav>
</template>
<script>
export default {
  name: 'Pagination',
  data() {
    return {
      config: {
        offset: 3
      }
    }
  },
  props: {
    pagination: {
      type: Object,
      required: true
    }
  },
  computed: {
    array() {
      if (this.pagination.totalPages <= 0) {
        return [];
      }

      let from = this.pagination.currentPage - this.config.offset;
      from = from > 0 ? from : 0;

      let to = from + (this.config.offset * 2);
      to = to <= this.pagination.totalPages ? to : this.pagination.totalPages - 1;

      let pages = [];
      while (from <= to) {
        pages.push(from);
        from++;
      }

      return pages;
    }
  },
  methods: {
    changePage(page) {
      if (this.pagination.currentPage === page) {
        return;
      }
      this.$emit('changePage', page)
    }
  }
}
</script>
<style></style>
