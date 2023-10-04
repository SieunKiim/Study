<template>
  <div class="shop-tab">
    <div class="shop-tab-detail">
      <div class="shop-tab-detail-css">
        <router-link to="/shop/buy">아이템 구매</router-link>
      </div>
    </div>

    <div class="shop-tab-detail">
      <div class="shop-tab-detail-css">
        <router-link to="/shop/refund">아이템 구매 취소</router-link>
      </div>
    </div>

    <div class="shop-tab-detail">
      <div class="shop-tab-detail-css">
        <router-link to="/shop/history">아이템 거래 내역</router-link>
      </div>
    </div>
  </div>
  <div class="shop-feature">
    <router-view />
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
.shop-tab {
  background-color: rgb(201, 211, 246);
  height: 55px;
  display: flex;
  align-items: center;
  padding-left: 15px;
  border-bottom: 1px solid black;
}

.shop-tab-detail {
  /* background-color: bisque; */
  height: 80%;
  width: 150px;
  margin-left: 10px;
  border: 1px solid black;
  border-radius: 7px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.shop-tab-detail-css{
  height: fit-content;
}

.shop-feature {
  /* background-color: cadetblue; */
  flex: 1;

  display: flex;
}
</style>