<template>
  <div class="user">
    <el-form
      :inline="true"
      size="mini"
      class="user-form"
      :model="searchForm"
    >
      <el-form-item label="账号:">
        <el-input v-model="searchForm.name" placeholder="请输入" size="mini" />
      </el-form-item>
      <el-button type="primary" size="mini" icon="el-icon-search" @click="handleSearch">搜 索</el-button>
      <el-button type="primary" size="mini" icon="el-icon-edit" @click="handleAdd">新 增</el-button>
    </el-form>
    <el-table
      ref="userTable"
      v-loading="listLoading"
      border
      :data="tableData"
      :height="tableHeight"
      size="mini"
    >
      <el-table-column
        type="index"
        label="序号"
        align="center"
        width="50"
      />
      <el-table-column
        prop="loginAccount"
        label="账号"
        align="center"
        width="180"
      />
      <el-table-column
        prop="userName"
        label="员工姓名"
        align="center"
        width="180"
      />
      <el-table-column
        prop="userTypeName"
        label="账号类型"
        align="center"
        width="180"
      />
      <el-table-column
        label="操作"
        align="center"
      >
        <template slot-scope="scope">
          <el-button v-show="scope.row.userType == 1" type="text" size="mini" @click="showRoles(scope.row)">查看关联角色</el-button>
          <el-button v-show="scope.row.userType == 1" type="text" size="mini" @click="handleRelationRoles(scope.row)">关联角色</el-button>
          <el-button v-show="scope.row.userType == 1" type="text" size="mini" @click="showCopyUser(scope.row)">复制用户角色</el-button>
          <el-button v-show="scope.row.userType == 1" type="text" size="mini" @click="showEmp(scope.row)">关联员工</el-button>
          <el-button type="text" size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="text" size="mini" @click="resetPwd(scope.row)">重置密码</el-button>
          <el-button type="text" size="mini" @click="handleRemove(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页栏 -->
    <Pagination :total="total" :page.sync="searchForm.current" :limit.sync="searchForm.size" @pagination="init" />

    <el-dialog :visible.sync="userStatus" :title="userTitle" width="30%">
      <el-form ref="userAddForm" :model="userAddForm" :rules="rules" class="dialog-form" label-width="80px">
        <el-form-item label="账号" prop="loginAccount">
          <el-input v-model="userAddForm.loginAccount" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="用户类别">
          <el-select v-model="userAddForm.userType" placeholder="请选择" style="width: 100%;">
            <el-option label="机构用户" value="1" />
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="userStatus = false">取 消</el-button>
        <el-button type="primary" @click="submitClick">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog :visible.sync="relationRoleStatus" title="关联角色列表" width="60%" top="10px">
      <el-table
        border
        :data="relationRoleData"
        height="390px"
        size="mini"
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

    <el-dialog :visible.sync="roleStatus" title="关联角色" width="40%" top="10px">
      <div style="text-align: center">
        <el-transfer
          v-model="relationRole"
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

    <el-dialog :visible.sync="copyUserStatus" title="用户列表" width="60%" top="10px">
      <el-form
        :inline="true"
        size="mini"
        :style="{margin:'0px 0px 5px 0px'}"
        class="user-form"
      >
        <el-form-item label="账号:">
          <el-input v-model="copyNameLike" placeholder="请输入" size="mini" />
        </el-form-item>
        <el-button type="primary" size="mini" icon="el-icon-search" @click="handleCopySearch">搜 索</el-button>
      </el-form>
      <el-table
        border
        :data="copyUserTableData"
        height="350px"
        size="mini"
        highlight-current-row
        @current-change="handleCopySelect"
      >
        <el-table-column
          type="index"
          label="序号"
          align="center"
        />
        <el-table-column
          prop="loginAccount"
          label="账号"
          align="center"
        />
        <el-table-column
          prop="userName"
          label="员工姓名"
          align="center"
        />
        <el-table-column
          prop="userTypeName"
          label="账号类型"
          align="center"
        />
      </el-table>
      <p />
      <el-pagination
        :current-page="copyCurrentPage"
        :page-size="copyPagesize"
        layout="total, prev, pager, next"
        :total="copyTotal"
        @current-change="handleCopyCurrentChange"
      />
      <span slot="footer" class="dialog-footer">
        <el-button @click="copyUserStatus = false">取 消</el-button>
        <el-button type="primary" @click="submitCopyUserClick">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog :visible.sync="empStatus" title="员工列表" width="60%" top="10px">
      <el-form
        :inline="true"
        size="mini"
        :style="{margin:'0px 0px 5px 0px'}"
        class="user-form"
      >
        <el-form-item label="员工名称:">
          <el-input v-model="empNameLike" placeholder="请输入" size="mini" />
        </el-form-item>
        <el-button type="primary" size="mini" icon="el-icon-search" @click="handleEmpSearch">搜 索</el-button>
      </el-form>
      <el-table
        border
        :data="empTableData"
        height="350px"
        size="mini"
        highlight-current-row
        @current-change="handleEmpSelect"
      >
        <el-table-column
          type="index"
          label="序号"
          align="center"
        />
        <el-table-column
          prop="loginAccount"
          label="账号"
          align="center"
        />
        <el-table-column
          prop="userName"
          label="员工姓名"
          align="center"
        />
        <el-table-column
          prop="userTypeName"
          label="账号类型"
          align="center"
        />
      </el-table>
      <p />
      <el-pagination
        :current-page="empCurrentPage"
        :page-size="empPagesize"
        layout="total, prev, pager, next"
        :total="empTotal"
        @current-change="handleEmpCurrentChange"
      />
      <span slot="footer" class="dialog-footer">
        <el-button @click="empStatus = false">取 消</el-button>
        <el-button type="primary" @click="submitEmpClick">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  pageUser,
  saveUser,
  delUser
} from '@/api/system/user'
import Pagination from '../../components/Pagination'
export default {
  components: { Pagination },
  data() {
    return {
      // 数据列表加载动画
      listLoading: true,
      // 搜索对象
      searchForm: {
        current: 1,
        size: 10,
        orgId: '',
        orgName: '',
        name: '',
        code: ''
      },
      nameLike: '',
      userTitle: '新增用户',
      tableData: [],
      currentPage: 1,
      pagesize: 10,
      total: 0,
      userStatus: false,
      userAddForm: {},
      rules: {
        loginAccount: [
          { required: true, message: '请输入账号', trigger: 'blur' }
        ]
      },
      selectRow: {},
      roleStatus: false,
      roleTableData: [],
      relationRoleStatus: false,
      relationRoleData: [],
      relationRole: [],
      copyUserStatus: false,
      copyUserTableData: [],
      copyCurrentPage: 1,
      copyPagesize: 10,
      copyTotal: 0,
      copyNameLike: '',
      copyUserRow: {},
      empNameLike: '',
      selectEmpRow: {},
      empStatus: false,
      empTableData: [],
      empCurrentPage: 1,
      empPagesize: 10,
      empTotal: 0
    }
  },
  created: function() {
    this.init()
  },
  methods: {
    init() {
      pageUser(this.searchForm).then(res => {
        if (res.success) {
          this.total = parseInt(res.data.total)
          this.tableData = res.data.rows
        }
      })
    },
    copyUserInit() {
      var paramData = {
        loginAccount: this.copyNameLike,
        pageNum: this.copyCurrentPage,
        pageSize: this.copyPagesize
      }
      this.$get(this.config.baseUrl + 'bsfUser/getPage', paramData).then(res => {
        if (res.success) {
          this.copyTotal = parseInt(res.data.total)
          this.copyUserTableData = res.data.rows
        }
      })
    },
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage
      this.init()
    },
    handleSearch() {
      this.init()
    },
    handleRefresh() {
      this.nameLike = ''
      this.init()
    },
    handleAdd() {
      this.userTitle = '新增用户'
      this.userAddForm = {}
      this.userStatus = true
    },
    submitClick() {
      this.$refs['userAddForm'].validate((valid) => {
        if (valid) {
          saveUser(this.userAddForm).then(res => {
            if (res.success) {
              this.$message({
                type: 'success',
                message: '操作成功'
              })
              this.userStatus = false
              this.userAddForm = {}
              this.init()
            } else {
              this.$message({
                type: 'warning',
                message: '操作失败'
              })
              return false
            }
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    handleCopyCurrentChange(currentPage) {
      this.copyCurrentPage = currentPage
      this.copyUserInit()
    },
    handleRelationUser(row) {
      this.relationUserStatus = true
    },
    handleMenuRelation(row) {
      this.menuStatus = true
    },
    handleEdit(row) {
      this.userTitle = '编辑用户'
      delete row.createTime
      delete row.updateTime
      const addForm = {}
      Object.keys(row).map(key => {
        addForm[key] = row[key]
      })
      this.userAddForm = addForm
      this.userStatus = true
    },
    handleRemove(row) {
      this.$confirm('此操作将删除选中记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delUser(row.id).then(res => {
          if (res.success) {
            this.$message({
              type: 'success',
              message: '操作成功!'
            })
            this.init()
          } else {
            this.$message({
              type: 'warning',
              message: '操作失败'
            })
            return false
          }
        })
      })
    },
    resetPwd(row) {
      this.$confirm('确定重置账号[' + row.loginAccount + ']的密码吗？重置后密码为【000000】?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delete row.createTime
        delete row.updateTime
        this.$formDataPost(this.config.baseUrl + 'bsfUser/resetPassword', row, false).then(res => {
          if (res.success) {
            this.$message({
              type: 'success',
              message: '操作成功!'
            })
            this.init()
          } else {
            this.$message({
              type: 'warning',
              message: '操作失败'
            })
            return false
          }
        })
      })
    },
    showRoles(row) {
      this.selectRow = row
      this.relationRoleStatus = true
      this.$get(this.config.baseUrl + 'bsfUserRole/listByUserId/' + row.id, {}).then(res => {
        if (res.success) {
          this.relationRoleData = res.data
        }
      })
    },
    handleRelationRoles(row) {
      this.roleTableData = []
      this.relationRole = []
      this.$get(this.config.baseUrl + 'bsfRole/getRoleList', {}).then(res => {
        if (res.success) {
          res.data.forEach(element => {
            this.roleTableData.push({
              key: element.id,
              label: element.roleName
            })
          })
          this.$get(this.config.baseUrl + 'bsfUserRole/listByUserId/' + row.id, {}).then(res => {
            if (res.success) {
              res.data.forEach(element => {
                this.relationRole.push(element.roleId)
              })
            }
          })
        }
      })
      this.roleStatus = true
    },
    submitRoleClick() {
      if (this.relationRole.length < 1) {
        this.$message({
          type: 'warning',
          message: '请选择至少一条数据'
        })
        return false
      }
      const selectRoleIds = this.relationRole.split(',')
      const paramData = {
        userId: this.selectRow.id,
        roleIdList: selectRoleIds
      }
      this.$formDataPost(this.config.baseUrl + 'bsfUserRole/modifyByUserId', paramData, false).then(res => {
        if (res.success) {
          this.$message({
            type: 'success',
            message: '操作成功!'
          })
          this.roleStatus = false
        } else {
          this.$message({
            type: 'warning',
            message: '操作失败'
          })
          return false
        }
      })
    },
    showCopyUser(row) {
      this.selectRow = row
      this.copyUserRow = {}
      this.copyCurrentPage = 1
      this.copyUserInit()
      this.copyUserStatus = true
    },
    handleCopySearch() {
      this.copyCurrentPage = 1
      this.copyUserInit()
    },
    handleCopySelect(row) {
      this.copyUserRow = row
    },
    submitCopyUserClick() {
      if (JSON.stringify(this.copyUserRow) === '{}') {
        this.$message({
          type: 'warning',
          message: '请选择一条数据'
        })
        return false
      }
      const paramData = {
        oldUserId: this.selectRow.id,
        newUserId: this.copyUserRow.id
      }
      this.$formDataPost(this.config.baseUrl + 'bsfUserRole/listByUserId/copyUserRole', paramData, false).then(res => {
        if (res.success) {
          this.$message({
            type: 'success',
            message: '操作成功!'
          })
          this.copyUserStatus = false
        } else {
          this.$message({
            type: 'warning',
            message: '操作失败'
          })
          return false
        }
      })
    },
    empInit() {

    },
    showEmp(row) {
      this.selectRow = row
      this.selectEmpRow = {}
      this.empStatus = true
    },
    handleEmpSearch() {
      this.empCurrentPage = 1
      this.empInit
    },
    handleEmpSelect(row) {
      this.selectEmpRow = row
    },
    handleEmpCurrentChange(currentPage) {
      this.empCurrentPage = currentPage
      this.empInit()
    },
    submitEmpClick() {
      if (JSON.stringify(this.selectEmpRow) === '{}') {
        this.$message({
          type: 'warning',
          message: '请选择一条数据'
        })
        return false
      }
      const paramData = {
        id: this.selectRow.id,
        empId: this.selectEmpRow.id
      }
      this.$formDataPost(this.config.baseUrl + 'bsfUser/bindEmp', paramData, false).then(res => {
        if (res.success) {
          this.$message({
            type: 'success',
            message: '操作成功!'
          })
          this.empStatus = false
        } else {
          this.$message({
            type: 'warning',
            message: '操作失败'
          })
          return false
        }
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
}
</style>
<style scoped>
.el-scrollbar__wrap {
  overflow-x: hidden;
}
</style>
