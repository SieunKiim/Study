<template>
  <div>
    <div>
      <h3>잔여 다이아 조회</h3>
      <form id="getDiamondBalance" v-on:submit.prevent="getUserDiamondBalance">
        <input id="userId" v-model="userId" placeholder="사용자 아이디 입력">
      </form>
    </div>
    <div>
      <h1 id="userBalance">{{ userDiamondBalance }}</h1>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'UserDiamondBalance',
  data: function(){
    return {
      userId: null,
      userDiamondBalance: null,
    }
  },
  methods: {
    getUserDiamondBalance() {
      axios.get('http://localhost:8080/user/'+ this.userId + '/diamond-balance')
      .then(res => {
        this.userDiamondBalance = res.data.value.diamond_balance;
        console.log("결과값 : " + res.data.value.diamond_balance);
      })
      .catch(error => {
        console.log(error);
      })
    }
  }
}
</script>

<style scoped>

</style>