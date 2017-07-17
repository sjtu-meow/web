<template>
<nav>
  <ul class="pagination pagination-sm no-margin pull-right" v-if="pagination.totalPages > 1">
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
        offset: 2
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

      let pages = [];
      let length = this.config.offset * 2 + 1 > this.pagination.totalPages ? this.pagination.totalPages : this.config.offset * 2 + 1;
      let from = this.pagination.currentPage - this.config.offset;
      let to = this.pagination.currentPage + this.config.offset;
      if (from < 0) {
        from = 0;
        to = from + length - 1;
      } else if (to >= this.pagination.totalPages) {
        to = this.pagination.totalPages - 1;
        from = to - length + 1;
      }

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
