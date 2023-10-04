<template>
  <div class="exchange-history-container">
    <div class="exchange-history-radio">
      <div>
        사용자 아이디 : <input id="user_id" v-model="user_id" placeholder="사용자 아이디">
      </div>
      <div class="exchange-history-radio-div">
        <input type="radio" value="buy" v-model="exchangeHistory" checked @change="getExchangeHistory"> 구매
      </div>
      <div class="exchange-history-radio-div"> 
        <input type="radio" value="refund" v-model="exchangeHistory" @change="getExchangeHistory"> 구매 취소
      </div>
    </div>
    <div class="exchange-history-list">
      {{ reusltMessage }}
      <p v-for="item in histories" :key="item">{{ item }}</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: "ExchangeHistoryComponent",
  data: function(){
    return{
      exchangeHistory: "",
      user_id:"",
      reusltMessage: "",
      histories: null
    }
  },
  methods: {
    getExchangeHistory(){
      if(this.user_id === ""){
        this.reusltMessage = "아이디가 입력되지 않음";
        this.histories = null;
      } else{
        console.log(this.user_id + this.exchangeHistory);
        axios.get('http://localhost:8080/exchange/'+this.exchangeHistory+'?user_id='+this.user_id)
      .then(res =>{
        this.reusltMessage = null;
        console.log("결과값 : " + res.data.value);

        this.histories = res.data.value;

      })
      .catch(error => {
        console.log(error);
        this.reusltMessage = error.response.data.message;
        this.histories = null;
      })
      }
      
    }
  }
}
</script>

<style scoped>
.exchange-history-container{
  flex:  1;
  display: flex;
  flex-direction: column;
}

.exchange-history-radio{
  background-color: rgb(89, 182, 100);
  display: flex;
  flex-direction: column;
}
.exchange-history-list{
  background-color: rgb(204, 204, 204);
  flex: 1;
}
.exchange-history-radio-div{
  margin-top: 5px;
}
</style>