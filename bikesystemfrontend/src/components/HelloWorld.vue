<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-11 09:50:41
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-15 01:05:38
-->

<template>
  <div class="container">
    <div class="greetings">
      <h1 class="green"> 
        单车管理系统
      </h1>
      <h3 v-if="isLoggedIn">
        Everything is bicycle.
      </h3>
      <h3 v-else>
        if you want to do something, please login first.
      </h3>
    </div>

    <div class="visitor-visible" v-show="!isLoggedIn" @click="loginFormVisible = true">
      <div class="login-button">登录</div>
    </div>
  </div>


  <el-dialog v-model="loginFormVisible" title="登录" width="500" class="center-dialog">
    <el-form :model="userForm">
      <el-form-item label="用户名：" :label-width="formLabelWidth">
        <el-input v-model="userForm.username" autocomplete="off" />
      </el-form-item>
      <el-form-item label="密码：" :label-width="formLabelWidth">
        <el-input v-model="userForm.password" show-password autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="loginFormVisible = false">取消</el-button>
        <el-button type="primary" @click="login" >
          确认
        </el-button>
      </div>
    </template>
  </el-dialog>

</template>

<script setup lang="ts">
import { computed, reactive, ref } from 'vue'
import { useUserStore } from '../stores/user'
import router from '../router'

// Use a single import statement for importing multiple Vue composition API functions
const userStore = useUserStore()
const isLoggedIn = computed(() => userStore.isLogged)

// Removed duplicate import of useUserStore
const loginFormVisible = ref(false)

const userForm = reactive({
  username: '',
  password: ''
})

const formLabelWidth = '140px'

const login = async () => {
  await userStore.login(userForm.username, userForm.password)
  loginFormVisible.value = false
}


</script>

<style scoped>
.container {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #2e2d2d;
  height: 100vh; /* Adjust height as needed */
}

.greetings, .visitor-visible {
  position: absolute;
}

.greetings {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: #2e2d2d;
}

h1 {
  font-weight: 500;
  font-size: 4.6rem;
  position: relative;
  top: -10px;
  background: linear-gradient(to right, #007BFF, #0410b9);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

h3 {
  font-size: 2.8rem;
  background: linear-gradient(to right, #007BFF, #666);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.greetings h1, .greetings h3 {
  text-align: center;
  margin-bottom: 1rem;
}

.visitor-visible {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 120vh; /* 使容器占满整个视口高度 */
  padding: 10px; /* 可选，避免按钮贴着视口底部 */
}

.visitor-visible .login-button {
  cursor: pointer;
  width: 200px;
  height: 200px;
  background-color: red;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  color: white;
  font-size: 60px;
  text-transform: uppercase;
  transition: background-color 0.3s ease;

  /* Center the button horizontally and position it towards the bottom */
  position: absolute;
  left: 50%;
  bottom: 20%; /* Adjust this value to move the button higher or lower */
  transform: translateX(-50%);
}

/* Change background color on hover */
.visitor-visible .login-button:hover {
  background-color: green;
}


</style>