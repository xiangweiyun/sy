<template>
  <el-dropdown class="user-avatar-wrapper" @command="handleCommand">
    <div class="avatar-box">
      <span class="user-name">{{ userInfo.name }}</span>
      <el-avatar size="small" :src="baseImgUrl + userInfo.avatar" />
      <i class="el-icon-caret-bottom" />
    </div>
    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item command="userCenter">个人中心</el-dropdown-item>
      <el-dropdown-item command="updatePwd">修改密码</el-dropdown-item>
      <el-dropdown-item command="loginOut">退出登录</el-dropdown-item>
    </el-dropdown-menu>
    <el-dialog :visible.sync="userStatus" :close-on-click-modal="false" title="修改密码" width="400px">
      <el-form ref="userForm" :model="userForm" :rules="rules" :size="size" label-width="80px">
        <el-form-item label="旧密码" prop="oldPwd">
          <el-input v-model="userForm.oldPwd" placeholder="请输入" show-password />
        </el-form-item>
        <el-form-item label="新密码" prop="newPwd">
          <el-input v-model="userForm.newPwd" placeholder="请输入" show-password />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="userStatus = false">取 消</el-button>
        <el-button type="primary" @click="submitClick">确 定</el-button>
      </span>
    </el-dialog>
  </el-dropdown>
</template>

<script>
import Avatar from '../../assets/img/avatar.png'
import { removeToken } from '../../utils/cookie'
import {
  updatePassword
} from '@/api/system/user'
import {
  initBaseUrl
} from '@/utils'

export default {
  name: 'UserAvatar',
  data() {
    return {
      size: '',
      userInfo: {},
      avatarSrc: Avatar,
      userStatus: false,
      userForm: {
        // 用户ID
        userId: '',
        // 用户账号
        userName: '',
        // 旧密码
        oldPwd: '',
        // 新密码
        newPwd: ''
      },
      rules: {
        oldPwd: [
          { required: true, message: '请输入旧密码', trigger: 'blur' }
        ],
        newPwd: [
          { required: true, message: '请输入新密码', trigger: 'blur' }
        ]
      },
      baseImgUrl: initBaseUrl() + '/image/'
    }
  },
  created() {
    this.size = window.CONFIG.SYSTEM_SIZE
    this.userInfo = this.$store.state.user.userInfo
  },
  methods: {
    handleCommand(command) {
      if (command === 'userCenter') {
        this.$router.push({ path: '/user-center' })
      }
      if (command === 'updatePwd') {
        this.userStatus = true
      }
      if (command === 'loginOut') {
        this.loginOut()
      }
    },
    submitClick() {
      this.userForm.userId = this.userInfo.id
      this.userForm.userName = this.userInfo.userName
      updatePassword(this.userForm).then(res => {
        this.$message({
          type: 'success',
          message: '操作成功'
        })
        this.userStatus = false
      })
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
      }).catch(() => {
        console.log('取消退出系统操作')
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
