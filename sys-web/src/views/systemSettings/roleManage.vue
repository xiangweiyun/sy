<template>
  <div class="role">
    <el-form
      :inline="true"
      size="mini"
      :style="{margin:'0px 0px 5px 0px'}"
      class="role-form"
    >
      <el-form-item label="角色名称:">
        <el-input v-model="nameLike" placeholder="请输入" size="mini" />
      </el-form-item>
      <el-button type="primary" size="mini" icon="el-icon-search" @click="handleSearch">搜 索</el-button>
      <el-button type="primary" size="mini" icon="el-icon-search" @click="handleRefresh">刷 新</el-button>
      <el-button type="primary" size="mini" icon="el-icon-check" @click="handleAdd">新 增</el-button>
    </el-form>
    <el-table
      border
      :data="tableData"
      height="390px"
      size="mini"
      highlight-current-row
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
      <el-table-column
        label="操作"
        align="center"
      >
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="handleRelationUser(scope.row)">查看关联用户</el-button>
          <el-button type="text" size="mini" @click="handleMenuRelation(scope.row)">菜单授权</el-button>
          <el-button type="text" size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="text" size="mini" @click="handleRemove(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <p />
    <el-pagination
      :current-page="currentPage"
      :page-size="pagesize"
      layout="total, prev, pager, next"
      :total="total"
      @current-change="handleCurrentChange"
    />

    <el-dialog :visible.sync="relationUserStatus" title="查看关联用户" width="60%" top="10px">
      <el-table
        border
        :data="relationUserTableData"
        height="350px"
        size="mini"
      >
        <el-table-column
          type="index"
          label="序号"
          align="center"
        />
        <el-table-column
          prop="userName"
          label="用户名"
          align="center"
        />
        <el-table-column
          prop="userTypeName"
          label="用户类型"
          align="center"
        />
        <el-table-column
          prop="loginAccount"
          label="登录账号"
          align="center"
        />
        <el-table-column
          prop="orgName"
          label="机构名称"
          align="center"
        />
        <el-table-column
          prop="isWxResult"
          label="是否已关联微信"
          align="center"
        />
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="relationUserStatus = false">关 闭</el-button>
      </span>
    </el-dialog>
    <el-dialog :visible.sync="roleStatus" :title="roleTitle" width="30%">
      <el-form ref="roleAddForm" :model="roleAddForm" :rules="rules" class="dialog-form" label-width="80px">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="roleAddForm.roleName" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="角色说明">
          <el-input v-model="roleAddForm.roleDesc" placeholder="请输入" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="roleStatus = false">取 消</el-button>
        <el-button type="primary" @click="submitClick">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog :visible.sync="menuStatus" title="菜单列表" width="35%" top="10px">
      <el-input
        v-model="filterText"
        placeholder="输入关键字进行过滤"
      />
      <el-tree
        ref="tree"
        class="filter-tree"
        :data="menuData"
        :props="defaultProps"
        show-checkbox
        node-key="id"
        :filter-node-method="filterNode"
        style="height:350px;overflow-y: auto;"
        :default-checked-keys="relationMenus"
        accordion
      />
      <span slot="footer" class="dialog-footer">
        <el-button @click="menuStatus = false">取 消</el-button>
        <el-button type="primary" @click="submitMenuClick">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'Dashboard',
  data() {
    return {
      nameLike: '',
      roleTitle: '新增角色',
      tableData: [],
      currentPage: 1,
      pagesize: 10,
      total: 0,
      roleStatus: false,
      roleAddForm: {},
      rules: {
        roleName: [
          { required: true, message: '请输入角色名称', trigger: 'blur' }
        ]
      },
      selectRow: {},
      menuStatus: false,
      filterText: '',
      menuData: [],
      relationMenus: [],
      defaultProps: {
        children: 'childList',
        label: 'menuName'
      },
      relationUserStatus: false,
      relationUserTableData: []
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val)
    }
  },
  created: function() {
    this.init()
  },
  methods: {
    init() {
      var paramData = {
        pageNum: this.currentPage,
        pageSize: this.pagesize,
        roleName: this.nameLike
      }
      this.$get(this.config.baseUrl + 'bsfRole/page', paramData).then(res => {
        if (res.success) {
          this.total = parseInt(res.data.total)
          this.tableData = res.data.rows
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
      this.currentPage = 1
      this.init()
    },
    handleAdd() {
      this.roleTitle = '新增角色'
      this.roleAddForm = {}
      this.roleStatus = true
    },
    submitClick() {
      this.$refs['roleAddForm'].validate((valid) => {
        if (valid) {
          this.$formDataPost(this.config.baseUrl + 'bsfRole/save', this.roleAddForm, false).then(res => {
            if (res.success) {
              this.$message({
                type: 'success',
                message: '操作成功'
              })
              this.roleStatus = false
              this.roleAddForm = {}
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
    handleRelationUser(row) {
      const paramData = {
        roleName: row.roleName,
        translateFlag: true
      }
      this.$get(this.config.baseUrl + 'bsfUser/listByRoleName', paramData).then(res => {
        if (res.success) {
          this.relationUserTableData = res.data
        }
      })
      this.relationUserStatus = true
    },
    handleMenuRelation(row) {
      this.selectRow = row
      this.menuStatus = true
      this.$get(this.config.baseUrl + 'bsfMenu/queryTreeMenu', {}).then(res => {
        if (res.success) {
          this.menuData = res.data
        }
      })
      this.$get(this.config.baseUrl + 'bsfRoleMenu/listByRoleId/' + row.id, {}).then(res => {
        if (res.success) {
          this.relationMenus = []
          res.data.forEach(element => {
            this.relationMenus.push(element.menuId)
          })
        }
      })
    },
    handleEdit(row) {
      this.roleTitle = '编辑角色'
      delete row.createTime
      delete row.updateTime
      const addForm = {}
      Object.keys(row).map(key => {
        addForm[key] = row[key]
      })
      this.roleAddForm = addForm
      this.roleStatus = true
    },
    handleRemove(row) {
      this.$confirm('此操作将删除选中记录以及子记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$formDataPost(this.config.baseUrl + 'bsfRole/remove/' + row.id, {}, false).then(res => {
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
    filterNode(value, data) {
      if (!value) {
        return true
      }
      return data.menuName.indexOf(value) !== -1
    },
    submitMenuClick() {
      const selectMenuData = this.$refs.tree.getCheckedKeys()
      if (selectMenuData.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选择至少一条数据'
        })
        return false
      }
      const paramData = {
        roleId: this.selectRow.id,
        menuList: selectMenuData
      }
      this.$formDataPost(this.config.baseUrl + 'bsfRoleMenu/save', paramData, false).then(res => {
        if (res.success) {
          this.$message({
            type: 'success',
            message: '操作成功!'
          })
          this.menuStatus = false
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
.role{
    &-form{
        .el-form-item{
            margin-bottom: 0px;
        }
    }
}
</style>
<style scoped>
.el-scrollbar__wrap {
  overflow-x: hidden;
}
</style>
