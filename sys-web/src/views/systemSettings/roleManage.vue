<template>
  <div class="role">
    <el-form
      :inline="true"
      size="mini"
      :model="searchForm"
      class="role-form"
    >
      <el-form-item label="所属机构:">
        <el-select v-model="searchForm.orgId" filterable placeholder="请选择">
          <el-option
            v-for="item in orgList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="角色名称:">
        <el-input v-model="searchForm.name" clearable placeholder="请输入" size="mini" />
      </el-form-item>
      <el-form-item label="角色编码:">
        <el-input v-model="searchForm.code" clearable placeholder="请输入" size="mini" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" icon="el-icon-search" @click="handleSearch">搜 索</el-button>
        <el-button type="primary" size="mini" icon="el-icon-add" @click="handleAdd">新 增</el-button>
      </el-form-item>
    </el-form>
    <el-table
      ref="roleTable"
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
        prop="name"
        label="角色名称"
        align="center"
      />
      <el-table-column
        prop="code"
        label="角色编码"
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
    <!-- 分页栏 -->
    <Pagination :total="total" :page.sync="searchForm.current" :limit.sync="searchForm.size" @pagination="init" />
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
    <el-dialog :visible.sync="roleStatus" :close-on-click-modal="false" :title="roleTitle" width="400px">
      <el-form ref="roleForm" :model="roleForm" :rules="rules" label-width="80px">
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="roleForm.name" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="角色编码" prop="code">
          <el-input v-model="roleForm.code" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="所属机构">
          <el-input v-model="searchForm.orgName" placeholder="请输入" disabled />
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
import {
  pageRole,
  saveRole,
  delRole
} from '@/api/system/role'
import {
  listOrg
} from '@/api/system/org'
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
      tableHeight: '200px',
      tableData: [],
      total: 0,
      orgList: [],
      roleTitle: '新增角色',
      roleStatus: false,
      // 新增、编辑角色信息对象
      roleForm: {
        // 主键
        id: '',
        // 机构ID
        orgId: '',
        // 机构名称
        orgName: '',
        // 角色名称
        name: '',
        // 角色编码
        code: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入角色名称', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入角色编码', trigger: 'blur' }
        ]
      },
      selectRow: {},
      menuStatus: false,
      // 菜单搜索关键字
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
  async created() {
    // 机构列表初始化
    await this.orgListInit()
    this.init()
  },
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - this.$refs['roleTable'].$el.offsetTop - 180
    })
  },
  methods: {
    // 角色列表初始化
    init() {
      this.listLoading = true
      pageRole(this.searchForm).then(res => {
        this.total = parseInt(res.total)
        this.tableData = res.records
        this.listLoading = false
      })
    },
    async orgListInit() {
      await listOrg().then(res => {
        this.orgList = res
        this.searchForm.orgId = this.orgList[0].id
        this.searchForm.orgName = this.orgList[0].name
      })
    },
    handleSearch() {
      this.init()
    },
    handleAdd() {
      this.roleTitle = '新增角色'
      Object.keys(this.roleForm).map(key => {
        this.roleForm[key] = ''
      })
      this.roleStatus = true
    },
    handleEdit(row) {
      this.roleTitle = '编辑角色'
      Object.keys(this.roleForm).map(key => {
        this.roleForm[key] = row[key]
      })
      this.roleStatus = true
    },
    submitClick() {
      this.$refs['roleForm'].validate((valid) => {
        if (valid) {
          this.roleForm.orgId = this.searchForm.orgId
          this.roleForm.orgName = this.searchForm.orgName
          saveRole(this.roleForm).then(res => {
            this.$message({
              type: 'success',
              message: '操作成功'
            })
            this.roleStatus = false
            this.init()
          })
        }
      })
    },
    handleRemove(row) {
      this.$confirm('此操作将删除选中记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delRole(row.id).then(res => {
          this.$message({
            type: 'success',
            message: '操作成功!'
          })
          this.init()
        })
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
