<template>
  <div class="role">
    <el-form
      :inline="true"
      :size="size"
      :model="searchForm"
      class="role-form"
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
      <el-form-item label="角色名称:">
        <el-input v-model="searchForm.name" clearable placeholder="请输入" :size="size" />
      </el-form-item>
      <el-form-item label="角色编码:">
        <el-input v-model="searchForm.code" clearable placeholder="请输入" :size="size" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" :size="size" icon="el-icon-search" @click="handleSearch">搜 索</el-button>
        <el-button type="primary" :size="size" icon="el-icon-add" @click="handleAdd">新 增</el-button>
      </el-form-item>
    </el-form>
    <el-table
      ref="roleTable"
      v-loading="listLoading"
      border
      :data="tableData"
      :height="tableHeight"
      :size="size"
    >
      <el-table-column type="index" label="序号" align="center" width="50" />
      <el-table-column prop="name" label="角色名称" align="center" />
      <el-table-column prop="code" label="角色编码" align="center" />
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="text" :size="size" @click="handleRelationUser(scope.row)">查看关联用户</el-button>
          <el-button type="text" :size="size" @click="handleMenuRelation(scope.row)">菜单授权</el-button>
          <el-button type="text" :size="size" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="text" :size="size" @click="handleRemove(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页栏 -->
    <Pagination :total="total" :page.sync="searchForm.current" :limit.sync="searchForm.size" @pagination="init" />
    <!-- 查询角色绑定用户 -->
    <el-dialog :visible.sync="relationUserStatus" :title="relationUserTitle" width="800px">
      <el-table
        v-loading="userLoading"
        border
        :data="relationUserTableData"
        height="350px"
        :size="size"
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
      <!-- 分页栏 -->
      <Pagination :total="userTotal" :page.sync="searchUserForm.current" :limit.sync="searchUserForm.size" @pagination="pageRelationUserInit" />
      <span slot="footer" class="dialog-footer" />
    </el-dialog>
    <el-dialog :visible.sync="roleStatus" :close-on-click-modal="false" :title="roleTitle" width="400px">
      <el-form ref="roleForm" :model="roleForm" :rules="rules" :size="size" label-width="80px">
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
    <!-- 菜单授权 -->
    <el-dialog :visible.sync="menuStatus" title="菜单授权" width="400px" top="10px">
      <el-input
        v-model="filterText"
        placeholder="输入关键字进行过滤"
      />
      <el-tree
        ref="tree"
        v-loading="parentMenuLoading"
        class="filter-tree"
        :data="menuData"
        :props="defaultProps"
        show-checkbox
        node-key="id"
        :filter-node-method="filterNode"
        style="height:350px;overflow-y: auto;"
        :default-checked-keys="relationMenus"
        accordion
        :size="size"
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
  delRole,
  listRoleMenu,
  saveBatchRoleMenu,
  pageRelationUser
} from '@/api/system/role'
import {
  listMenu
} from '@/api/system/menu'
import Pagination from '../../components/Pagination'
export default {
  components: { Pagination },
  data() {
    return {
      size: 'mini',
      // 数据列表加载动画
      listLoading: true,
      userLoading: true,
      parentMenuLoading: true,
      // 搜索对象
      searchForm: {
        current: 1,
        size: 10,
        orgId: '',
        orgName: '',
        name: '',
        code: ''
      },
      searchUserForm: {
        current: 1,
        size: 10,
        userName: '',
        name: '',
        noNum: ''
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
        children: 'children',
        label: 'name'
      },
      relationUserTitle: '',
      relationUserStatus: false,
      relationUserTableData: [],
      userTotal: 0
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val)
    }
  },
  created() {
    this.size = window.CONFIG.SYSTEM_SIZE
    // 机构列表初始化
    if (this.$store.state) {
      this.orgList = this.$store.state.user.userInfo.listOrg
      this.searchForm.orgId = this.orgList[0].orgId
      this.searchForm.orgName = this.orgList[0].orgName
    }
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
      this.$nextTick(() => {
        this.$refs['roleForm'].clearValidate()
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
      this.selectRow = row
      this.relationUserTitle = '角色[' + row.name + ']对应用户列表'
      this.pageRelationUserInit()
    },
    pageRelationUserInit() {
      this.userLoading = true
      const paramData = {
        roleId: this.selectRow.id
      }
      pageRelationUser(paramData).then(res => {
        this.userTotal = parseInt(res.total)
        this.relationUserTableData = res.records
        this.userLoading = false
      })
      this.relationUserStatus = true
    },
    handleMenuRelation(row) {
      this.parentMenuLoading = true
      this.selectRow = row
      listMenu().then(res => {
        this.menuData = res
        this.parentMenuLoading = false
      })
      listRoleMenu(row.id).then(res => {
        console.log(res)
        this.relationMenus = []
        res.forEach(item => {
          this.relationMenus.push(item.menuId)
        })
      })
      this.menuStatus = true
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
      const paramList = []
      selectMenuData.forEach(item => {
        const paramData = {
          roleId: this.selectRow.id,
          menuId: item
        }
        paramList.push(paramData)
      })
      saveBatchRoleMenu(paramList).then(res => {
        this.$message({
          type: 'success',
          message: '操作成功!'
        })
        this.menuStatus = false
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
