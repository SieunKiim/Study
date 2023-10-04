<template>
  <div class="app">
    <div class="sidebar">
      <SideBar></SideBar>
    </div>
    <div class="content">
      <router-view />
    </div>
  </div>
 
</template>

<script>
import SideBar from './components/sidebar/SideBar.vue'

export default {
  name: 'App',
  components: {
    SideBar,
  }
}
</script>

<style scoped>
.app {
  width: 100vw;
  height: 100vh;
  display: flex;
}

.sidebar {
  height: 100%;
  width: 250px;
  display: flex;
  flex-direction: column;
  border-right: 1px solid black;
  background-color: rgba(47, 143, 76, 0.648);
}

.content {
  display: flex;
  flex-direction: column;
  height: 100%;
  flex: 1;
}

</style>
