<template>
  <div class="refund-item-container">
  <h1>아이템 구매 취소</h1>

  <form class="refund-item-request" v-on:submit.prevent="refundItems">
    <div class="refund-itme-request-details">
      사용자 id : <input id="user_id" v-model="user_id" placeholder="user id">
    </div>
    <div class="refund-itme-request-details">
      거래 id : <input id="sale_id" v-model="sale_id" placeholder="sale id">
    </div>
    <button class="refund-item-submit" type="submit">아이템 구매 취소</button>
  </form>
  <div class="item-info">
    <p>1번 아이템 : 10원</p>
    <p>2번 아이템 : 20원</p>
    <p>3번 아이템 : 300원</p>
    <p>4번 아이템 : 4000원</p>
    <p>5번 아이템 : 5000원</p>
  </div>
  </div>
  <div class="refund-item-response">
    <div class="refund-item-response-details">
      결과 메시지 : {{ resultMessage }}
    </div>
    <div class="refund-item-response-details">
      총 사용 다이아 : {{ totalCost }}

    </div>
    <div class="refund-item-response-details">
      사용자 잔여 다이아 : {{ leftBalance }}

    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'RefundItemComponent',
  data: function(){
    return{
      user_id: "",
      sale_id: "",
      resultMessage : "",
      totalCost: "",
      leftBalance: ""
    }
  },
  methods: {
    refundItems(){
      if(this.user_id === "" || this.sale_id === ""){
        this.resultMessage = "입력 정보가 올바르지 않습니다.";
        this.totalCost = "";
        this.leftBalance = "";
      } else {
         axios.post('http://localhost:8080/sale/refund',
          {
            user_id: this.user_id,
            sale_id: this.sale_id,
          }
        )
        .then(res => {
          console.log(JSON.stringify(res.data));
          this.resultMessage = "아이템 구매 취소 성공!";
          this.totalCost = res.data.value.total_cost;
          this.leftBalance = res.data.value.left_balance;
        })
        .catch(error => {
          this.resultMessage = error.response.data.message;
          this.totalCost = "";
          this.leftBalance = "";
        })
      }
     
    }
  }
}
</script>

<style scoped>
.refund-item-container{
  width: 50%;
  display: flex;
  flex-direction: column;
}
.refund-item-request{
  background-color: rgb(89, 182, 100);
  width: 100%;
  height: 30%;
}
.refund-item-response{
  background-color: rgb(78, 132, 90);
  flex: 1;
}
</style>