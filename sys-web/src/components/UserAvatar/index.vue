<template>
  <el-dropdown class="user-avatar-wrapper" @command="handleCommand">
    <div class="avatar-box">
      <span class="user-name">{{ name }}</span>
      <el-avatar size="small" :src="avatarSrc" />
      <i class="el-icon-caret-bottom" />
    </div>
    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item command="userCenter">个人中心</el-dropdown-item>
      <el-dropdown-item command="loginOut">退出登录</el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</template>

<script>
import Avatar from '../../assets/img/avatar.png'
import { removeToken } from '../../utils/cookie'

export default {
  name: 'UserAvatar',
  data() {
    return {
      name: '管理员',
      avatarSrc: Avatar
    }
  },
  methods: {
    handleCommand(command) {
      if (command === 'userCenter') {
        this.$router.push({ path: '/user-center' })
      }
      if (command === 'loginOut') {
        this.loginOut()
      }
    },
    loginOut() {
      this.$confirm('确定注销并退出系统吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$store.dispatch('LogOut').then(() => {
          removeToken()
          location.href = '/'
        })
      })
    }
  }
}
</script>

<style lang="less">
.user-avatar-wrapper {
  float: left;
  width: 118px;
  padding: 3px 0 3px 0px;
  margin-left: 20px;
  border-left: solid 1px #ddd;
  cursor: pointer;
  .user-name {
    margin-right: 5px;
  }
  .avatar-box {
    outline: none;
    text-align: right;
  }
  .el-avatar--small {
    display: inline-block;
    vertical-align: middle;
    width: 32px;
    height: 32px;
    line-height: 32px;
  }
  i {
    display: inline-block;
    vertical-align: middle;
    margin-left: 2px;
  }
}
</style>
