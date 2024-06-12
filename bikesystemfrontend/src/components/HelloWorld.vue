<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-11 09:50:41
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-11 18:09:48
-->
<script setup lang="ts">
</script>
<template>
  <div class="greetings">
    <h1 class="green"> 
      单车管理系统
    </h1>
    <h3>
      Everything is bicycle.
    </h3>
  </div>
</template>

<style scoped>
.greetings {
  display: flex;
  flex-direction: column;
  align-items: center; /* 使文字居中 */
  justify-content: center;
  height: 100vh; /* 使内容占满整个视口高度 */
  background-color: #2e2d2d;
}

h1 {
  font-weight: 500;
  font-size: 4.6rem;
  position: relative;
  top: -10px;
  /* 使用渐变色 */
  background: linear-gradient(to right, #007BFF, #0410b9);
  /* 创建背景的剪裁区域 */
  -webkit-background-clip: text;
  /* 使文本颜色透明以显示背景 */
  -webkit-text-fill-color: transparent;
}

h3 {
  font-size: 1.2rem;
  /* 使用渐变色 */
  background: linear-gradient(to right, #007BFF, #666);
  /* 创建背景的剪裁区域 */
  -webkit-background-clip: text;
  /* 使文本颜色透明以显示背景 */
  -webkit-text-fill-color: transparent;
}

.greetings h1,
.greetings h3 {
  text-align: center;
  margin-bottom: 1rem; /* 增加下边距 */
}


</style>