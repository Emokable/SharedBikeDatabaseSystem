<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-11 15:01:47
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-15 00:27:47
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
      v-show="store.isLogged"
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
      <div class="user-visible" v-show="store.isLogged">、
        <el-sub-menu index="personSpace">
          <template #title>用户中心</template>
          <el-menu-item index="/Profile">个人信息管理</el-menu-item>
          <el-button type="danger" round @click="logout">
          退出
          </el-button>
        </el-sub-menu>
      </div>
      <el-sub-menu index="workSpace">
        <template #title v-show="store.isLogged">数据管理中心</template>
        <el-menu-item index="/mapPanel">信息分析</el-menu-item>
        <el-menu-item index="/adminView">管理员管理</el-menu-item>
        <el-menu-item index="/riderView">骑行者管理</el-menu-item>
        <el-menu-item index="/bikeView">自行车管理</el-menu-item>
        <el-menu-item index="/recordView">骑行记录</el-menu-item>
        <el-menu-item index="/noParkingView">禁停区</el-menu-item>
      </el-sub-menu>
    </el-menu>

  </template>

<script setup lang="ts">
import {reactive, ref} from 'vue'
import {http} from '../utils/http'
import { useUserStore } from '../stores/user';
import router from '../router';

const store = useUserStore()

const logout = () => {
  store.logout()
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

</style>