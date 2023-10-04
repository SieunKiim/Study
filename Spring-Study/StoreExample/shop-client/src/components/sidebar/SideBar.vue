<template>
    <div class="userBalance">
      <UserDiamondBalance></UserDiamondBalance>
    </div>
    
    <div class="menu">
      <div class="menu-target">
        <div class="menu-target-css">
          <router-link to="/exchange">다이아 충전</router-link>
        </div>
      </div>
      <div class="menu-target">
        <div class="menu-target-css">
          <router-link to="/shop">상점</router-link>
        </div>
      </div>
    </div>
</template>

<script>
import UserDiamondBalance from './UserDiamondBalance.vue';


export default{
    name: 'SideBar',
    components: {
      UserDiamondBalance,
    }
}
</script>

<style scoped>
.userBalance {
  background-color: rgba(47, 143, 76, 0.569);
  border-bottom: 1px solid black;
  border-bottom-right-radius: 44px;
  height: 200px;
}

.menu {
  flex: 1;
  /* background-color: purple; */
  display: flex;
  flex-direction: column;
  padding-top: 50px;
  align-items: center;
}

.menu-target{
  background-color: rgba(183, 183, 183, 0.605);
  border: 1px solid black;
  width: 94%;
  height: 40px;
  margin-bottom: 15px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
