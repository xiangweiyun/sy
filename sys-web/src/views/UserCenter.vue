<template>
  <div class="user-center-wrapper clear-fix">
    <el-card shadow="always" :body-style="{padding: '50px'}">
      <div class="user-avatar">
        <img :src="baseImgUrl + userInfo.avatar" alt="avatar">
      </div>
      <ul class="user-info">
        <li>
          <label>用户姓名：</label>
          <span>{{ userInfo.name }}</span>
        </li>
        <li>
          <label>用户账号：</label>
          <span>{{ userInfo.userName }}</span>
        </li>
        <li>
          <label>所属机构：</label>
          <span>{{ userInfo.orgName }}</span>
        </li>
        <li>
          <label>所属科室：</label>
          <span>{{ userInfo.deptName }}</span>
        </li>
        <li>
          <label>职务：</label>
          <span>{{ userInfo.postName }}</span>
        </li>
        <li>
          <label>职称：</label>
          <span>{{ userInfo.jobName }}</span>
        </li>
        <li>
          <label>电话号码：</label>
          <span>{{ userInfo.officePhone }}</span>
        </li>
      </ul>
    </el-card>
  </div>
</template>

<script>
import {
  initBaseUrl
} from '@/utils'

export default {
  name: 'UserCenter',
  data() {
    return {
      userInfo: {},
      baseImgUrl: initBaseUrl() + '/image/'
    }
  },
  created() {
    this.userInfo = this.$store.state.user.userInfo
  }
}
</script>

<style lang="less">
.user-center-wrapper {
  .user-avatar {
    float: left;
    width: 150px;
    height: 150px;
  }
  .user-info {
    float: left;
    width: 800px;
    margin-left: 50px;
    margin-bottom: 50px;
    li {
      height: 34px;
      line-height: 34px;
      label,
      span {
        display: inline-block;
        vertical-align: middle;
      }
      label {
        width: 80px;
        margin-right: 12px;
        text-align: right;
      }
    }
  }
}
</style>
