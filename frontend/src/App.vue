<template>
  <div id="app">
    <h1>Translation API Status</h1>
    <p>{{ status }}</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'App',
  data() {
    return {
      status: 'Loading...'
    }
  },
  mounted() {
    axios.get('/api/status')
      .then(response => {
        this.status = response.data;
      })
      .catch(error => {
        this.status = 'Error: ' + error.message;
      });
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style> 