<template>
  <div class="exchange-buy-request-container">
    <form class="exchange-buy-form" v-on:submit.prevent="exchangeBuyRequest">
      <div class="exchange-buy-form-details">
        사용자 아이디 : <input id="user_id" v-model="user_id" placeholder="사용자 아이디">
      </div>
      
      <div class="exchange-buy-form-details">
        구매 금액 : <input id="amount" v-model="amount" placeholder="구매 금액">
      </div>

      <div class="exchange-buy-form-details">
        <fieldset>
          <legend> | PG사 선택 | </legend>
          <input type="radio" id="kakaopay-radio" value="kakaopay" v-model="pg" checked @change="toggleOnOff">
          <label for="kakaopay"> 카카오페이 </label>
          <br>
          
          <input type="radio" id="payletter-radio" value="payletter" v-model="pg" @change="toggleOnOff">
          <label for="payletter-radio"> 페이레터</label>
        </fieldset>
        
      </div>

      <div class="exchange-buy-form-details" id="exchange-buy-div-pg-code" v-if="isStatusOn">
        <fieldset>
          <legend> | 결제 수단 선택 | </legend>
          <input type="radio" id="creditcard-pg-code-radio" value="creditcard" v-model="pg_code" checked>
          <label for="kakaopay"> 신용카드 </label>
          <br>
          
          <input type="radio" id="kakaopay-pg-code-radio" value="idonknow" v-model="pg_code">
          <label for="payletter-radio"> 모바일</label>
        </fieldset>
      </div>

      <button class="exchange-submit" type="submit">결제 하기</button>
    </form>
  </div>
  <div class="exchange-buy-response-container">
    여기는 결제 결과
  </div>
</template>


<script>
import axios from 'axios';

export default {
  name: 'BuyDiamondComponent',
  data: function(){
    return{
      isStatusOn: false,
      user_id: null,
      amount: null,
      pg: 'kakaopay',
      pg_code: 'creditcard',



    }
  },
  methods: {
    exchangeBuyRequest(){
      console.log(this.user_id, this.amount, this.pg, this.pg_code);
      axios.post('http://localhost:8080/exchange/buy',
        {
          user_id: this.user_id,
          amount: this.amount,
          pg: this.pg,
          pg_code: this.pg_code
        }
      )
      .then(res => {
        // console.log(JSON.stringify(res.data));
        console.log(JSON.stringify(res.data.value.approve_url));
        window.open(res.data.value.approve_url);
      })
      .catch(error => console.log(error))
    },
    toggleOnOff(){
      console.log(this.isStatusOn);
      if(this.pg === 'payletter') this.isStatusOn = true;
      else this.isStatusOn = false; 
    },
  }
}
</script>


<style scoped>
.exchange-buy-request-container{
  background-color: rgb(196, 196, 196);
  width: 50%;

  display: flex;
  align-items: center;
  justify-content: center;
}

.exchange-buy-response-container{
  background-color: rgb(123, 170, 137);
  flex: 1;
}

.exchange-buy-form{
  width: fit-content;
  height: fit-content;
  background-color: rgb(42, 165, 81);
  padding: 30px;
  border: 1px solid black;
  border-radius: 12px;

  display: flex;
  flex-direction: column;
}
.exchange-buy-form-details{
  margin: 4px 0px 4px 0px;
}

.exchange-submit{
  margin-top: 20px;
  width: 40%;
}
</style>