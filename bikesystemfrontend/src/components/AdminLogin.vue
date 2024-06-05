<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-05 12:19:37
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-05 15:15:35
-->
<template>
  <div>
    用户名:<input type="text" v-model="loginForm.username" placeholder="请输入用户名" />
    <br><br>
    密码：<input type="password" v-model="loginForm.password" placeholder="请输入密码" />
    <br><br>
    <button @click="login">登录</button>
  </div>
</template>

<script>
import { reactive, toRefs } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

export default {
  name: 'AdminLogin',
  setup() {
    const router = useRouter()
    const loginForm = reactive({
      username: '',
      password: ''
    })

    const login = () => {
      axios
        .post('/api/login', {
          username: loginForm.username,
          password: loginForm.password
        })
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            router.replace({ path: '/index' })
          } else {
            window.alert('登录失败：' + successResponse.data.message)
          }
        })
        .catch(failResponse => {
          console.log(failResponse)
          window.alert('登录失败：无法连接到服务器')
        })
    }

    return {
      loginForm,
      ...toRefs(loginForm),
      login
    }
  }
}
</script>