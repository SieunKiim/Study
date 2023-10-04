<template>
  <div class="sale-history-container">
    <div class="sale-history-radio">
      <div>
        사용자 아이디 : <input id="user_id" v-model="user_id" placeholder="사용자 아이디">
      </div>
      <div class="sale-history-radio-div">
        <input type="radio" value="buy" v-model="saleHistory" checked @change="getSaleHistory"> 구매
      </div>
      <div class="sale-history-radio-div"> 
        <input type="radio" value="refund" v-model="saleHistory" @change="getSaleHistory"> 구매 취소
      </div>
    </div>
    <div class="sale-history-list">
      {{ reusltMessage }}
      <p v-for="item in histories" :key="item">{{ item }}</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: "SaleHistoryComponent",
  data: function(){
    return{
      saleHistory: "",
      user_id:"",
      reusltMessage: "",
      histories: null
    }
  },
  methods: {
    getSaleHistory(){
      if(this.user_id === ""){
        this.reusltMessage = "아이디가 입력되지 않음";
        this.histories = null;
      } else{
          console.log(this.user_id + this.exchangeHistory);
          axios.get('http://localhost:8080/sale/'+this.saleHistory+'?user_id='+this.user_id+'&page=0&size=100')
        .then(res =>{
          this.reusltMessage = null;
          console.log("결과값 : " + res.data.value);

          this.histories = res.data.value.content;

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
.sale-history-container{
  flex:  1;
  display: flex;
  flex-direction: column;
}

.sale-history-radio{
  background-color: rgb(89, 182, 100);
  display: flex;
  flex-direction: column;
}
.sale-history-list{
  background-color: rgb(204, 204, 204);
  flex: 1;
}
.sale-history-radio-div{
  margin-top: 5px;
}
</style>