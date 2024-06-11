<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-11 15:01:47
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-11 21:06:41
-->
<template>
    <el-menu
      defalut-active="/hello"
      class="menu-demo"
      mode="horizontal"
      :ellipsis="false"
      @select="handleSelect"
      :router = "true"
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
      <el-menu-item index="/login">登录</el-menu-item>
      <el-sub-menu index="workSpace">
        <template #title>管理中心</template>
        <el-menu-item index="/Profile">个人信息管理</el-menu-item>
        <el-sub-menu index="ws-search">
          <template #title>数据查询</template>
          <el-menu-item index="/mapPanel">用户信息查询</el-menu-item>
          <el-menu-item index="/search/user">用户信息查询</el-menu-item>
          <el-menu-item index="/search/bike">自行车信息查询</el-menu-item>
          <el-menu-item index="/search/record">骑行记录查询</el-menu-item>
          <el-menu-item index="/search/noparking">禁停区查询</el-menu-item>
        </el-sub-menu>
      </el-sub-menu>
    </el-menu>
  </template>

<script setup lang="ts">
import { RouterLink } from 'vue-router'
import {computed} from 'vue'

const handleSelect = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
</script>

<style>
.flex-grow {
  flex-grow: 1;
}
</style>