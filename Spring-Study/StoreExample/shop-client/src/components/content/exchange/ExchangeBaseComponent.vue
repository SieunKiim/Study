<template>
  <div class="exchange-tab">
    <div class="exchange-tab-detail">
      <div class="exchange-tab-detail-css">
        <router-link to="/exchange/buy">다이아 구매</router-link>
      </div>
    </div>

    <div class="exchange-tab-detail">
      <div class="exchange-tab-detail-css">
        <router-link to="/exchange/refund">다이아 구매 취소</router-link>
      </div>
    </div>

    <div class="exchange-tab-detail">
      <div class="exchange-tab-detail-css">
        <router-link to="/exchange/history">환전 내역</router-link>
      </div>
    </div>
  </div>
  <div class="exchange-feature">
    <router-view />
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
.exchange-tab {
  background-color: rgb(251, 205, 200);
  height: 55px;
  display: flex;
  align-items: center;
  padding-left: 15px;
  border-bottom: 1px solid black;
}

.exchange-tab-detail {
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

.exchange-feature {
  /* background-color: cadetblue; */
  flex: 1;

  display: flex;
}

.exchange-tab-detail-css{
  height: fit-content;
}
</style>