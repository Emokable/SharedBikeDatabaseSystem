<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-11 15:01:47
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-12 23:37:31
-->
<template>
    <el-menu
      defalut-active="/hello"
      class="menu-demo"
      mode="horizontal"
      :ellipsis="false"
      @select="handleSelect"
      :router = "true"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
    >
      <!-- <el-menu-item index="0">
        <img
          style="width: 100px"
          src="/images/element-plus-logo.svg"
          alt="Element logo"
        />
      </el-menu-item> -->
      <el-menu-item index="/hello">首页</el-menu-item>
      <div class="flex-grow" />
      <div class="visitor-visible"  v-show="!store.isLogged">
        <el-button plain @click="loginFormVisible = true">
          登录
        </el-button>
      </div>
      <div class="user-visible" v-show="store.isLogged">、
        <el-sub-menu index="personSpace">
          <template #title>个人中心</template>
          <el-menu-item index="/Profile">个人信息管理</el-menu-item>
          <el-button plain @click="logout">
          退出
          </el-button>
        </el-sub-menu>
      </div>
      <el-sub-menu index="workSpace">
        <template #title>数据中心</template>
        <el-sub-menu index="ws-search">
          <template #title>数据查询</template>
          <el-menu-item index="/mapPanel">地图信息查询</el-menu-item>
          <el-menu-item index="/search/user">用户信息查询</el-menu-item>
          <el-menu-item index="/search/bike">自行车信息查询</el-menu-item>
          <el-menu-item index="/search/record">骑行记录查询</el-menu-item>
          <el-menu-item index="/search/noparking">禁停区查询</el-menu-item>
        </el-sub-menu>
      </el-sub-menu>
    </el-menu>

    <el-dialog v-model="loginFormVisible" title="登录" width="500">
    <el-form :model="userForm">
      <el-form-item label="用户名：" :label-width="formLabelWidth">
        <el-input v-model="userForm.username" autocomplete="off" />
      </el-form-item>
      <el-form-item label="密码：" :label-width="formLabelWidth">
        <el-input v-model="userForm.password" autocomplete="off" />
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
import {reactive, ref} from 'vue'
import {http} from '../utils/http'
import { useUserStore } from '../stores/user';
import router from '../router';


const loginFormVisible = ref(false)
const store = useUserStore()

const userForm = reactive({
  username: '',
  password: ''
})

const formLabelWidth = '140px'

const login = () => {
  console.log
  store.login(userForm.username, userForm.password)
  loginFormVisible.value = false
}

const logout = () => {
  store.logout()
  // 跳转到首页
  router.push('/hello')
}

const handleSelect = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
</script>

<style>
.flex-grow {
  flex-grow: 1;
}
.user-visible {
  display: flex;
  align-items: center;
}

.visitor-visible {
  display: flex;
  align-items: center;
}
</style>