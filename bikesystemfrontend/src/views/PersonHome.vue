<template>
  <div class="user-center">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>用户中心</span>
      </div>
      <el-avatar size="large" :src="avatarPath"></el-avatar>
        <el-button type="primary" @click="showUpdatePasswordDialog = true">更新密码</el-button>
    </el-card>

  <el-dialog v-model="showUpdatePasswordDialog" title="修改密码" width="30%">
    <el-form label-position="top">
      <el-form-item label="新密码">
        <el-input v-model="password" show-password></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="updatePassword">确认修改</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
  
</div>
  
</template>

<script>
import { ref } from 'vue';
import { useUserStore } from '../stores/user';
import { http } from '../utils/http';

export default {
  setup() {
    const password = ref('');
    const userStore = useUserStore();
    const avatarPath = ref(userStore.avatar); // Use ref for reactive avatarPath
    const showUpdatePasswordDialog = ref(false);

    const updatePassword = () => {
      // Ensure to use password.value inside setup()
      console.log(password.value);
      http.changePassWord("/admins", userStore.token, password.value).then((res) => {
        // Handle response
        window.alert('密码修改成功');
        showUpdatePasswordDialog.value = false; // Close dialog after successful password change
      }).catch((error) => {
        // Handle error
        console.error('密码修改失败', error);
        window.alert('密码修改失败');
      });
    };

    return {
      password,
      avatarPath,
      showUpdatePasswordDialog,
      updatePassword,
    };
  },
};
</script>

<style scoped>
.user-center {
  max-width: 400px;
  margin: 50px auto;
}
</style>