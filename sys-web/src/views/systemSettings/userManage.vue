<template>
  <div class="user">
    <el-form
      :inline="true"
      :size="size"
      class="user-form"
      :model="searchForm"
    >
      <el-form-item label="所属机构:">
        <el-select v-model="searchForm.orgId" filterable placeholder="请选择">
          <el-option
            v-for="item in orgList"
            :key="item.orgId"
            :label="item.orgName"
            :value="item.orgId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="用户姓名:">
        <el-input v-model="searchForm.name" clearable placeholder="请输入" :size="size" />
      </el-form-item>
      <el-form-item label="账号:">
        <el-input v-model="searchForm.userName" clearable placeholder="请输入" :size="size" />
      </el-form-item>
      <el-form-item label="工号:">
        <el-input v-model="searchForm.noNum" clearable placeholder="请输入" :size="size" />
      </el-form-item>
      <el-button type="primary" :size="size" icon="el-icon-search" @click="handleSearch">搜 索</el-button>
      <el-button type="primary" :size="size" icon="el-icon-edit" @click="handleAdd">新 增</el-button>
    </el-form>
    <!-- 科室信息展示 -->
    <div class="user-left">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>科室信息</span>
          <el-button style="float: right; padding: 3px 0" type="text" @click="handleSearchDept">刷新</el-button>
        </div>
        <el-input
          v-model="filterDeptText"
          placeholder="科室名称检索"
        />
        <el-tree
          ref="tree"
          class="filter-tree"
          :data="deptData"
          :props="defaultProps"
          :expand-on-click-node="false"
          :filter-node-method="filterNode"
          style="height:350px;overflow-y: auto;"
          accordion
          :size="size"
          @node-click="nodeDeptClick"
        />
      </el-card>
    </div>
    <!-- 用户列表展示 -->
    <div class="user-right">
      <el-table
        ref="userTable"
        v-loading="listLoading"
        border
        :data="tableData"
        :height="tableHeight"
        :size="size"
      >
        <el-table-column type="expand">
          <template slot-scope="scope">
            <el-descriptions :column="5" direction="vertical" :size="size" border>
              <el-descriptions-item label="身份证号">
                {{ scope.row.idcard }}
              </el-descriptions-item>
              <el-descriptions-item label="名族">
                {{ scope.row.nationCode }}
              </el-descriptions-item>
              <el-descriptions-item label="职务">
                {{ scope.row.postName }}
              </el-descriptions-item>
              <el-descriptions-item label="职称">
                {{ scope.row.jobName }}
              </el-descriptions-item>
              <el-descriptions-item label="联系电话">
                {{ scope.row.officePhone }}
              </el-descriptions-item>
              <el-descriptions-item label="擅长领域" :span="3">
                {{ scope.row.goodAtField }}
              </el-descriptions-item>
              <el-descriptions-item label="个人简介">
                {{ scope.row.personalProfile }}
              </el-descriptions-item>
            </el-descriptions>
          </template>
        </el-table-column>
        <el-table-column type="index" label="序号" align="center" width="50" />
        <el-table-column prop="name" label="用户姓名" align="center" width="120" />
        <el-table-column prop="genderName" label="性别" align="center" width="50" />
        <el-table-column prop="username" label="用户账号" align="center" width="160" />
        <el-table-column prop="noNum" label="工号" align="center" width="110" />
        <el-table-column prop="deptName" label="所属科室" align="center" width="120" />
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button type="text" :size="size" @click="showRoles(scope.row)">查看关联角色</el-button>
            <el-button type="text" :size="size" @click="handleRelationRoles(scope.row)">关联角色</el-button>
            <el-button type="text" :size="size" @click="handleCopyUser(scope.row)">复制用户角色</el-button>
            <el-button type="text" :size="size" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="text" :size="size" @click="resetPwd(scope.row)">重置密码</el-button>
            <el-button type="text" :size="size" @click="handleRemove(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页栏 -->
      <Pagination :total="total" :page.sync="searchForm.current" :limit.sync="searchForm.size" @pagination="init" />
    </div>
    <!-- 新增、编辑用户信息 -->
    <el-dialog :visible.sync="userStatus" :close-on-click-modal="false" :title="userTitle" width="800px" top="15px">
      <el-form ref="userForm" :model="userForm" :rules="rules" :size="size" label-width="80px">
        <el-col :span="12">
          <el-form-item label="用户姓名" prop="name">
            <el-input v-model="userForm.name" placeholder="请输入" />
          </el-form-item>
          <el-form-item label="账号" prop="username">
            <el-input v-model="userForm.username" placeholder="请输入" />
          </el-form-item>
          <el-form-item label="所属机构">
            <el-input v-model="userForm.orgName" placeholder="请输入" disabled />
          </el-form-item>
          <el-form-item label="身份证号">
            <el-input v-model="userForm.idcard" placeholder="请输入" />
          </el-form-item>
          <el-form-item label="名族">
            <el-select
              v-model="userForm.nationCode"
              clearable
              placeholder="请选择"
              style="width: 100%"
            >
              <el-option
                v-for="item in nationList"
                :key="item.dictValue"
                :label="item.dictLabel"
                :value="item.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="职务">
            <el-select
              v-model="userForm.postCode"
              clearable
              placeholder="请选择"
              style="width: 100%"
            >
              <el-option
                v-for="item in postList"
                :key="item.dictValue"
                :label="item.dictLabel"
                :value="item.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="工号" prop="noNum">
            <el-input v-model="userForm.noNum" placeholder="请输入" />
          </el-form-item>
          <el-form-item label="是否启用">
            <el-select v-model="userForm.isEnabled" filterable placeholder="请选择" style="width:100%;">
              <el-option
                v-for="item in enabledList"
                :key="item.dictValue"
                :label="item.dictLabel"
                :value="item.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="所属科室" prop="deptId">
            <el-select v-model="userForm.deptId" filterable placeholder="请选择" style="width:100%;">
              <el-option
                v-for="item in parentDeptData"
                :key="item.id"
                :label="item.deptName"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="性别">
            <el-select
              v-model="userForm.genderCode"
              clearable
              placeholder="请选择"
              style="width: 100%"
            >
              <el-option
                v-for="item in sexList"
                :key="item.dictValue"
                :label="item.dictLabel"
                :value="item.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="联系电话">
            <el-input v-model="userForm.officePhone" placeholder="请输入" />
          </el-form-item>
          <el-form-item label="职称">
            <el-select
              v-model="userForm.jobTitleCode"
              clearable
              placeholder="请选择"
              style="width: 100%"
            >
              <el-option
                v-for="item in jobList"
                :key="item.dictValue"
                :label="item.dictLabel"
                :value="item.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="擅长领域">
            <el-input v-model="userForm.goodAtField" placeholder="请输入" type="textarea" />
          </el-form-item>
          <el-form-item label="个人简介">
            <el-input v-model="userForm.personalProfile" placeholder="请输入" type="textarea" />
          </el-form-item>
          <el-form-item label="用户头像">
            <el-upload
              class="avatar-uploader"
              action="#"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
            >
              <img
                v-if="userForm.avatar"
                :src="userForm.avatar"
                class="avatar"
              >
              <i v-else class="el-icon-plus avatar-uploader-icon" />
            </el-upload>
          </el-form-item>
        </el-col>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="userStatus = false">取 消</el-button>
        <el-button type="primary" @click="submitClick">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 用户已关联的角色列表 -->
    <el-dialog :visible.sync="relationRoleStatus" title="关联角色列表" width="60%" top="10px">
      <el-table
        v-loading="roleLoading"
        border
        :data="relationRoleData"
        height="390px"
        :size="size"
      >
        <el-table-column
          type="index"
          label="序号"
          align="center"
          width="50"
        />
        <el-table-column
          prop="roleName"
          label="角色名称"
          align="center"
        />
        <el-table-column
          prop="roleDesc"
          label="角色说明"
          align="center"
        />
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="relationRoleStatus = false">关 闭</el-button>
      </span>
    </el-dialog>
    <!-- 用户关联角色操作 -->
    <el-dialog :visible.sync="roleStatus" title="关联角色" width="800px">
      <div style="text-align: center">
        <el-transfer
          v-model="relationRole"
          v-loading="relationRoleLoading"
          style="text-align: left; display: inline-block"
          :data="roleTableData"
          :titles="['未选', '已选']"
          filterable
        />
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="roleStatus = false">取 消</el-button>
        <el-button type="primary" @click="submitRoleClick">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 复制用户角色 -->
    <el-dialog :visible.sync="copyUserStatus" title="用户列表" width="800px">
      <el-form
        :inline="true"
        :size="size"
        class="user-form"
      >
        <el-form-item label="用户姓名:">
          <el-input v-model="searchUserForm.name" clearable placeholder="请输入" :size="size" />
        </el-form-item>
        <el-form-item label="工号:">
          <el-input v-model="searchUserForm.noNum" clearable placeholder="请输入" :size="size" />
        </el-form-item>
        <el-button type="primary" :size="size" icon="el-icon-search" @click="handleCopySearch">搜 索</el-button>
      </el-form>
      <el-table
        v-loading="listUserLoading"
        border
        :data="userTableData"
        height="350px"
        :size="size"
        highlight-current-row
        @current-change="handleCopySelect"
      >
        <el-table-column type="index" label="序号" align="center" width="50" />
        <el-table-column prop="name" label="用户姓名" align="center" />
        <el-table-column prop="genderName" label="性别" align="center" />
        <el-table-column prop="postName" label="职务" align="center" />
        <el-table-column prop="jobName" label="职称" align="center" />
        <el-table-column prop="username" label="用户账号" align="center" />
        <el-table-column prop="noNum" label="工号" align="center" />
        <el-table-column prop="orgName" label="所属机构" align="center" />
        <el-table-column prop="deptName" label="所属科室" align="center" />
      </el-table>
      <p />
      <!-- 分页栏 -->
      <Pagination :total="userTotal" :page.sync="searchUserForm.current" :limit.sync="searchUserForm.size" @pagination="userListInit" />
      <span slot="footer" class="dialog-footer">
        <el-button @click="copyUserStatus = false">取 消</el-button>
        <el-button type="primary" @click="submitCopyUserClick">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  pageUser,
  saveUser,
  delUser,
  listUserRelationRole,
  saveUserRelationRole,
  resetPassword,
  copyUserRole
} from '@/api/system/user'
import {
  listRole
} from '@/api/system/role'
import {
  listDept,
  listParentByOrgIdAndDeptId
} from '@/api/system/dept'
import {
  listDistItem
} from '@/api/system/dist'
import Pagination from '../../components/Pagination'
export default {
  components: { Pagination },
  data() {
    return {
      size: 'mini',
      // 数据列表加载动画
      listLoading: true,
      roleLoading: true,
      relationRoleLoading: true,
      listUserLoading: true,
      // 搜索对象
      searchForm: {
        current: 1,
        size: 10,
        orgId: '',
        orgName: '',
        userName: '',
        name: '',
        noNum: '',
        deptId: ''
      },
      searchUserForm: {
        current: 1,
        size: 10,
        orgId: '',
        orgName: '',
        name: '',
        noNum: '',
        deptId: ''
      },
      orgList: [],
      deptData: [],
      parentDeptData: [],
      defaultProps: {
        children: 'children',
        label: 'deptName'
      },
      filterDeptText: '',
      userTitle: '新增用户',
      tableData: [],
      userTableData: [],
      tableHeight: '200px',
      total: 0,
      userTotal: 0,
      userStatus: false,
      userForm: {
        id: '',
        orgId: '',
        orgName: '',
        deptId: '',
        name: '',
        username: '',
        noNum: '',
        idcard: '',
        genderCode: '',
        nationCode: '',
        postCode: '',
        jobTitleCode: '',
        goodAtField: '',
        personalProfile: '',
        officePhone: '',
        avatar: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ],
        noNum: [
          { required: true, message: '请输入工号', trigger: 'blur' }
        ],
        username: [
          { required: true, message: '请输入登录账号', trigger: 'blur' }
        ],
        deptId: [
          { required: true, message: '请选择所属科室', trigger: 'change' }
        ]
      },
      selectRow: {},
      roleStatus: false,
      roleTableData: [],
      relationRoleStatus: false,
      relationRoleData: [],
      relationRole: [],
      copyUserStatus: false,
      copyUserRow: {},
      // 名族选项
      nationList: [],
      // 性别选项
      sexList: [],
      // 职务选项
      postList: [],
      // 职称选项
      jobList: [],
      // 职称选项
      enabledList: [
        {
          dictLabel: '是',
          dictValue: 1
        },
        {
          dictLabel: '否',
          dictValue: 0
        }
      ]
    }
  },
  watch: {
    filterDeptText(val) {
      this.$refs.tree.filter(val)
    }
  },
  created() {
    if (this.$store.state) {
      this.orgList = this.$store.state.user.userInfo.listOrg
      this.searchForm.orgId = this.orgList[0].orgId
      this.searchForm.orgName = this.orgList[0].orgName
    }
    this.size = window.CONFIG.SYSTEM_SIZE
    this.dictInit()
    this.deptListInit()
    this.parentDeptListInit()
    this.init()
  },
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - this.$refs['userTable'].$el.offsetTop - 180
    })
  },
  methods: {
    // 分页查询用户信息初始化
    init() {
      this.listLoading = true
      pageUser(this.searchForm).then(res => {
        this.total = parseInt(res.total)
        this.tableData = res.records
        this.listLoading = false
      })
    },
    dictInit() {
      // 名族选项
      listDistItem({ dictType: 'NATION' }).then((res) => {
        this.nationList = res
      })
      // 性别选项
      listDistItem({ dictType: 'SEX' }).then((res) => {
        this.sexList = res
      })
      // 职务选项
      listDistItem({ dictType: 'POST' }).then((res) => {
        this.postList = res
      })
      // 职称选项
      listDistItem({ dictType: 'JOB' }).then((res) => {
        this.jobList = res
      })
    },
    // 科室信息初始化
    deptListInit() {
      listDept(this.searchForm.orgId).then(res => {
        this.deptData = res
      })
    },
    parentDeptListInit() {
      listParentByOrgIdAndDeptId({ orgId: this.searchForm.orgId }).then(res => {
        this.parentDeptData = res
      })
    },
    // 科室信息检索
    filterNode(value, data) {
      if (!value) {
        return true
      }
      return data.deptName.indexOf(value) !== -1
    },
    // 选择科室
    nodeDeptClick(row) {
      this.searchForm.deptId = row.id
      this.init()
    },
    // 科室信息刷新
    handleSearchDept() {
      this.searchForm.deptId = ''
      this.deptListInit()
      this.init()
    },
    // 用户信息搜索
    handleSearch() {
      this.searchForm.current = 1
      this.init()
    },
    // 新增用户信息
    handleAdd() {
      this.userTitle = '新增用户'
      Object.keys(this.userForm).map(key => {
        this.userForm[key] = ''
      })
      this.userForm.orgId = this.searchForm.orgId
      this.userForm.orgName = this.searchForm.orgName
      this.userForm.isEnabled = 1
      this.userStatus = true
    },
    // 编辑用户信息
    handleEdit(row) {
      this.userTitle = '编辑用户'
      Object.keys(this.userForm).map(key => {
        this.userForm[key] = row[key]
      })
      this.$nextTick(() => {
        this.$refs['userForm'].clearValidate()
      })
      this.userForm.orgId = this.searchForm.orgId
      this.userForm.orgName = this.searchForm.orgName
      this.userStatus = true
    },
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw)
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    },
    // 提交用户信息
    submitClick() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {
          saveUser(this.userForm).then(res => {
            if (this.userForm.id) {
              this.$message({
                type: 'success',
                message: '操作成功'
              })
            } else {
              this.$alert({
                title: '新增用户',
                type: 'success',
                message: '操作成功,初始密码为[123456]'
              })
            }
            this.userStatus = false
            this.searchForm.current = 1
            this.init()
          })
        }
      })
    },
    // 查询用户信息
    handleRemove(row) {
      this.$confirm('此操作将删除选中记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delUser(row.id).then(res => {
          this.$message({
            type: 'success',
            message: '操作成功!'
          })
          this.init()
        })
      })
    },
    // 重置密码
    resetPwd(row) {
      this.$confirm('确定重置用户[' + row.name + ']的密码吗？重置后密码为[123456]?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        resetPassword(row.id).then(res => {
          this.$message({
            type: 'success',
            message: '操作成功!'
          })
          this.init()
        })
      })
    },
    // 显示关联角色
    showRoles(row) {
      this.roleLoading = true
      this.selectRow = row
      this.relationRoleStatus = true
      listUserRelationRole({ userId: row.id }).then(res => {
        this.relationRoleData = res
        this.roleLoading = false
      })
    },
    // 用户关联角色操作
    handleRelationRoles(row) {
      this.relationRoleLoading = true
      this.selectRow = row
      this.roleTableData = []
      this.relationRole = []
      listRole({ orgId: row.orgId }).then(res => {
        res.forEach(item => {
          this.roleTableData.push({
            key: item.id,
            label: item.name + '[' + item.code + ']'
          })
        })
        listUserRelationRole({ userId: row.id }).then(res => {
          res.forEach(item => {
            this.relationRole.push(item.roleId)
          })
          this.relationRoleLoading = false
        })
      })
      this.roleStatus = true
    },
    // 提交角色关联
    submitRoleClick() {
      if (this.relationRole.length < 1) {
        this.$message({
          type: 'warning',
          message: '请选择至少一条数据'
        })
        return false
      }
      const paramData = []
      this.relationRole.forEach(item => {
        paramData.push({
          userId: this.selectRow.id,
          roleId: item
        })
      })
      saveUserRelationRole(paramData).then(res => {
        this.$message({
          type: 'success',
          message: '操作成功!'
        })
        this.roleStatus = false
      })
    },
    // 获取复制用户列表
    userListInit() {
      this.listUserLoading = true
      this.searchUserForm.orgId = this.searchForm.orgId
      pageUser(this.searchUserForm).then(res => {
        this.userTotal = parseInt(res.total)
        this.userTableData = res.records
        this.listUserLoading = false
      })
    },
    // 查询复制的用户列表
    handleCopyUser(row) {
      this.selectRow = row
      this.copyUserRow = {}
      this.searchUserForm.current = 1
      this.userListInit()
      this.copyUserStatus = true
    },
    // 复制用户列表搜索
    handleCopySearch() {
      this.searchUserForm.current = 1
      this.copyUserInit()
    },
    // 选择复制用户
    handleCopySelect(row) {
      this.copyUserRow = row
    },
    // 提交复制用户角色
    submitCopyUserClick() {
      if (JSON.stringify(this.copyUserRow) === '{}') {
        this.$message({
          type: 'warning',
          message: '请选择一条数据'
        })
        return false
      }
      if (this.copyUserRow.id === this.selectRow.id) {
        this.$message({
          type: 'warning',
          message: '请重新选择，不能复制本人的角色'
        })
        return false
      }
      const paramData = {
        userIdTo: this.selectRow.id,
        userIdFr: this.copyUserRow.id
      }
      copyUserRole(paramData).then(res => {
        this.$message({
          type: 'success',
          message: '操作成功!'
        })
        this.copyUserStatus = false
      })
    }
  }
}
</script>
<style lang="less">
.user{
  &-form{
    .el-form-item{
      margin: 0px 0px 5px 0px
    }
  }
  &-left{
    float: left;
    width: 200px;
  }
  &-right{
    float: right;
    width: calc(100% - 205px);
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409eff;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
}
</style>
<style scoped>
.el-scrollbar__wrap {
  overflow-x: hidden;
}
</style>
