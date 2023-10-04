<template>
  <div class="buy-item-container">
  <h1>아이템 구매</h1>
  <form class="buy-item-request" v-on:submit.prevent="buyItems">
    <div class="buy-itme-request-details">
      사용자 id : <input id="user_id" v-model="user_id" placeholder="사용자 아이디">

    </div>
    <div class="buy-itme-request-details">
      구매 아이템 id : <input id="item_id" v-model="item_id" placeholder="아이템 아이디">
    </div>
    <div class="buy-itme-request-details">
      구매 수량 : <input id="amount" v-model="amount" placeholder="수량을 입력해주세요">
    </div>
    <button class="buy-item-submit" type="submit">아이템 구매</button>

  </form>
  <div class="item-info">
    <p>1번 아이템 : 10원</p>
    <p>2번 아이템 : 20원</p>
    <p>3번 아이템 : 300원</p>
    <p>4번 아이템 : 4000원</p>
    <p>5번 아이템 : 5000원</p>
  </div>
  </div>
  <div class="buy-item-response">
    <div class="buy-item-response-details">
      결과 메시지 : {{ resultMessage }}
    </div>
    <div class="buy-item-response-details">
      총 사용 다이아 : {{ totalCost }}

    </div>
    <div class="buy-item-response-details">
      사용자 잔여 다이아 : {{ leftBalance }}

    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'BuyItemComponent',
  data: function(){
    return{
      user_id: "",
      item_id: "",
      amount: "",
      resultMessage : "",
      totalCost: "",
      leftBalance: ""
    }
  },
  methods: {
    buyItems(){
      if(this.user_id === "" || this.item_id === "" || this.amount ===""){
        this.resultMessage = "입력 정보가 올바르지 않습니다.";
        this.totalCost = "";
        this.leftBalance = "";
      } else {
         axios.post('http://localhost:8080/sale/buy',
          {
            user_id: this.user_id,
            item_id: this.item_id,
            amount: this.amount
          }
        )
        .then(res => {
          console.log(JSON.stringify(res.data));
          this.resultMessage = "아이템 구매 성공!";
          this.totalCost = res.data.value.cost;
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
.buy-item-container{
  width: 50%;
  display: flex;
  flex-direction: column;
}
.buy-item-request{
  background-color: rgb(89, 182, 100);
  width: 100%;
  height: 30%;
}
.buy-item-response{
  background-color: rgb(78, 132, 90);
  flex: 1;
}

</style>