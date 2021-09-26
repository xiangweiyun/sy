<template>
  <div class="dept">
    <el-form
      :inline="true"
      :size="size"
      class="dept-form"
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
      <el-button type="primary" :size="size" icon="el-icon-search" @click="handleSearch">搜 索</el-button>
      <el-button type="primary" :size="size" icon="el-icon-add" @click="handleAdd">新 增</el-button>
    </el-form>
    <el-table
      ref="deptTable"
      v-loading="listLoading"
      border
      :data="tableData"
      :height="tableHeight"
      :size="size"
      row-key="id"
      :tree-props="{children: 'children'}"
    >
      <el-table-column prop="deptName" label="科室名称" />
      <el-table-column prop="code" label="科室编码" align="center" />
      <el-table-column prop="orderNum" label="显示顺序" align="center" />
      <el-table-column prop="phone" label="联系电话" align="center" />
      <el-table-column prop="email" label="邮箱" align="center" />
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="text" :size="size" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="text" :size="size" @click="handleRemove(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <p />
    <!-- 新增、编辑部门信息 -->
    <el-dialog :visible.sync="deptStatus" :close-on-click-modal="false" :title="deptTitle" width="400px">
      <el-form ref="deptForm" :model="deptForm" :rules="rules" :size="size" label-width="80px">
        <el-form-item label="科室名称" prop="deptName">
          <el-input v-model="deptForm.deptName" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="科室编码" prop="code">
          <el-input v-model="deptForm.code" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="所属机构" prop="orgId">
          <el-input v-model="deptForm.orgName" placeholder="请输入" disabled />
        </el-form-item>
        <el-form-item label="上级科室">
          <el-select v-model="deptForm.parentId" clearable filterable placeholder="请选择" style="width:100%;">
            <el-option
              v-for="item in parentDeptList"
              v-show="deptForm.id != item.id"
              :key="item.id"
              :label="item.deptName"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="deptForm.phone" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="deptForm.email" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="显示排序">
          <el-input v-model="deptForm.orderNum" placeholder="请输入" type="number" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="deptStatus = false">取 消</el-button>
        <el-button type="primary" @click="submitClick">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  listDept,
  saveDept,
  delDept,
  listParentByOrgIdAndDeptId
} from '@/api/system/dept'
export default {
  data() {
    return {
      size: 'mini',
      // 数据列表加载动画
      listLoading: false,
      searchForm: {
        orgId: '',
        orgName: ''
      },
      tableData: [],
      tableHeight: '200px',
      selectRow: {},
      orgList: [],
      parentDeptList: [],
      deptTitle: '新增科室',
      deptStatus: false,
      deptForm: {
        // 主键
        id: '',
        // 所属机构
        orgId: '',
        // 科室名称
        deptName: '',
        // 科室编码
        code: '',
        // 上级科室
        parentId: '',
        // 联系电话
        phone: '',
        // 邮箱
        email: '',
        // 显示排序
        orderNum: ''
      },
      rules: {
        deptName: [
          { required: true, message: '请输入科室名称', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入科室编码', trigger: 'blur' }
        ],
        orgId: [
          { required: true, message: '请选择所属机构', trigger: 'change' }
        ]
      },
      businessTypeSelect: []
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
      this.tableHeight = window.innerHeight - this.$refs['deptTable'].$el.offsetTop - 180
    })
  },
  methods: {
    init() {
      this.listLoading = true
      listDept(this.searchForm.orgId).then(res => {
        this.tableData = res
        this.listLoading = false
      })
      // 上级科室列表初始化
      this.deptListInit()
    },
    deptListInit() {
      const param = {
        orgId: this.searchForm.orgId,
        deptId: this.selectRow.id
      }
      listParentByOrgIdAndDeptId(param).then(res => {
        this.parentDeptList = res
      })
    },
    handleSearch() {
      this.init()
    },
    handleAdd() {
      this.deptTitle = '新增科室'
      Object.keys(this.deptForm).map(key => {
        this.deptForm[key] = ''
      })
      this.deptForm.orgId = this.searchForm.orgId
      this.deptForm.orgId = this.searchForm.orgName
      this.deptStatus = true
    },
    handleEdit(row) {
      this.selectRow = row
      this.deptTitle = '编辑科室'
      Object.keys(this.deptForm).map(key => {
        this.deptForm[key] = row[key]
      })
      this.$nextTick(() => {
        this.$refs['deptForm'].clearValidate()
      })
      this.deptStatus = true
    },
    submitClick() {
      if (this.deptForm.id) {
        if (this.deptForm.id === this.deptForm.parentId) {
          this.$message({
            type: 'warning',
            message: '请重新选择，不能以自己作为上级科室'
          })
          return false
        }
      }
      this.$refs['deptForm'].validate((valid) => {
        if (valid) {
          saveDept(this.deptForm).then(res => {
            this.$message({
              type: 'success',
              message: '操作成功'
            })
            this.selectRow = {}
            this.deptStatus = false
            this.init()
          })
        }
      })
    },
    handleRemove(row) {
      const children = row.children
      if (children) {
        this.$message({
          type: 'warning',
          message: '该科室存在子记录不能删除'
        })
        return false
      }
      this.$confirm('此操作将删除选中记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delDept(row.id).then(res => {
          this.$message({
            type: 'success',
            message: '操作成功!'
          })
          this.init()
        })
      })
    }
  }
}
</script>
<style lang="less">
.dept{
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
