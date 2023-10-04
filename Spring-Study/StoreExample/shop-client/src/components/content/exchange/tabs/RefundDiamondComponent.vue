<template>
  <div class="exchange-refund-request">
    <form class="exchange-refund-request-form" v-on:submit.prevent="exchangeRefundRequest">
      <div class="exchange-refund-request-form-details">
        PG : <input id="pg" v-model="pg" placeholder="결제한 pg사">
      </div>
      
      <div class="exchange-refund-request-form-details">
        ORDER ID : <input id="order_id" v-model="order_id" placeholder="주문 id">
      </div>

      <button class="exchange-refund-submit" type="submit">주문 취소</button>
    </form>
  </div>

  <div class="exchange-refund-response">
    <div class="exchange-refund-response-result">
      result : {{ message }}
      <br>
      잔여 다이아 : {{ leftBalance }}
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'RefundDiamondComponent',
  data: function(){
    return{
      pg: "",
      order_id: "",
      leftBalance:"",
      message:"",
    }
  },
  methods: {
    exchangeRefundRequest(){
      axios.post('http://localhost:8080/exchange/refund',
        {
          pg: this.pg,
          order_id: this.order_id
        }
      ).then(res => {
        console.log(JSON.stringify(res.data.value));
        this.message = "취소 성공";
        this.leftBalance = res.data.value.user_balance;
      })
      .catch(error => {
        console.log(error)
        this.message = "취소 실패 ( " + error.response.data.message + " )";
        this.leftBalance = "";
        // this.
      })
    }
  },
}
</script>

<style scoped>
#order_id{
  width: 250px;
}
.exchange-refund-request {
  width: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.exchange-refund-response {
  flex: 1;

  display: flex;
  align-items: center;
  justify-content: center;
}

.exchange-refund-request-form{
  background-color: rgb(89, 182, 100);
  border: 1px solid black;
  border-radius: 14px;
  padding: 40px;

  display: flex;
  flex-direction: column;
}
.exchange-refund-request-form-details{
  border-bottom: 0.4px solid black;
  margin-bottom: 15px;
}

.exchange-refund-submit {
  width: 100px;
  
}

.exchange-refund-response-result{
  background-color: rgb(204, 204, 204);
  width: 70%;
  height: 60px;
  border: 1px solid black;
  border-radius: 10px;
}

</style>