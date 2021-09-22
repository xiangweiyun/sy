<template>
  <div class="employee">
    <el-form
      :inline="true"
      size="mini"
      :style="{margin:'0px 0px 5px 0px'}"
      class="employee-form"
      :model="empSearchForm"
    >
      <el-form-item label="姓名:">
        <el-input v-model="empSearchForm.empName" placeholder="请输入" size="mini" />
      </el-form-item>
      <el-form-item label="机构:">
        <el-select v-model="empSearchForm.hospitalId" clearable filterable placeholder="请选择">
          <el-option
            v-for="item in orgOptions"
            :key="item.id"
            :label="item.orgName"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="职称:">
        <el-select v-model="empSearchForm.title" clearable filterable placeholder="请选择">
          <el-option
            v-for="item in titleOptions"
            :key="item.itemCode"
            :label="item.itemName"
            :value="item.itemCode"
          />
        </el-select>
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
        prop="empName"
        label="姓名"
        align="center"
      />
      <el-table-column
        prop="empSexName"
        label="性别"
        align="center"
      />
      <el-table-column
        prop="contactPhone"
        label="联系电话"
        align="center"
      />
      <el-table-column
        prop="jobTypeName"
        label="职务"
        align="center"
      />
      <el-table-column
        prop="titleName"
        label="职称"
        align="center"
      />
      <el-table-column
        label="操作"
        align="center"
      >
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="handleDeptRelation(scope.row)">关联科室</el-button>
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

    <el-dialog :visible.sync="employeeStatus" :title="employeeTitle" width="60%">
      <el-form ref="employeeAddForm" :model="employeeAddForm" :rules="rules" class="dialog-form" label-width="80px">
        <el-col :span="12">
          <el-form-item label="姓名" prop="empName">
            <el-input v-model="employeeAddForm.empName" placeholder="请输入" />
          </el-form-item>
          <el-form-item label="性别">
            <el-select v-model="employeeAddForm.empSex" placeholder="请选择" style="width: 100%;">
              <el-option label="男" value="1" />
              <el-option label="女" value="2" />
            </el-select>
          </el-form-item>
          <el-form-item label="选择机构">
            <el-select v-model="employeeAddForm.orgId" clearable filterable placeholder="请选择" style="width:100%;">
              <el-option
                v-for="item in orgOptions"
                :key="item.id"
                :label="item.orgName"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="所属部门">
            <el-select v-model="employeeAddForm.deptId" clearable filterable placeholder="请选择" style="width:100%;">
              <el-option
                v-for="item in deptOptions"
                :key="item.id"
                :label="item.deptName"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="职务">
            <el-select v-model="employeeAddForm.jobType" clearable placeholder="请选择" style="width: 100%;">
              <el-option
                v-for="item in jobTypeOptions"
                :key="item.itemCode"
                :label="item.itemName"
                :value="item.itemCode"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="职称">
            <el-select v-model="employeeAddForm.title" clearable placeholder="请选择" style="width: 100%;">
              <el-option
                v-for="item in titleOptions"
                :key="item.itemCode"
                :label="item.itemName"
                :value="item.itemCode"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="联系电话">
            <el-input v-model="employeeAddForm.contactPhone" placeholder="请输入" />
          </el-form-item>
          <el-form-item label="是否禁用">
            <el-select v-model="employeeAddForm.deleteFlag" placeholder="请选择" style="width: 100%;">
              <el-option label="否" value="0" />
              <el-option label="是" value="1" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="employeeStatus = false">取 消</el-button>
        <el-button type="primary" @click="submitClick">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog :visible.sync="deptStatus" title="科室列表" width="35%">
      <el-input
        v-model="filterText"
        placeholder="输入关键字进行过滤"
      />
      <el-tree
        ref="tree"
        class="filter-tree"
        :data="deptData"
        :props="defaultProps"
        show-checkbox
        default-expand-all
        node-key="id"
        highlight-current
        :expand-on-click-node="false"
        :filter-node-method="filterNode"
      />
      <span slot="footer" class="dialog-footer">
        <el-button @click="deptStatus = false">取 消</el-button>
        <el-button type="primary">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'Dashboard',
  data() {
    return {
      empSearchForm: {
        empName: '',
        hospitalId: '',
        title: ''
      },
      orgOptions: [],
      deptOptions: [],
      jobTypeOptions: [],
      titleOptions: [],
      employeeTitle: '新增员工',
      tableData: [],
      currentPage: 1,
      pagesize: 10,
      total: 0,
      employeeStatus: false,
      employeeAddForm: {},
      rules: {
        empName: [
          { required: true, message: '请输入员工名称', trigger: 'blur' }
        ]
      },
      selectRow: {},
      filterText: '',
      deptData: [],
      defaultProps: {
        children: 'children',
        label: 'deptName'
      },
      relationUserTableData: [],
      deptStatus: false
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val)
    }
  },
  created: function() {
    this.init()
    this.optionInit()
  },
  methods: {
    init() {
      this.empSearchForm.pageNum = this.currentPage
      this.empSearchForm.pageSize = this.pagesize
      this.$get(this.config.baseUrl + 'bsfEmployee/page', {}).then(res => {
        if (res.success) {
          this.total = parseInt(res.data.total)
          this.tableData = res.data.rows
        }
      })
    },
    optionInit() {
      this.$get(this.config.baseUrl + 'bsfOrganization/list/BsfOrganization', {}).then(res => {
        if (res.success) {
          this.orgOptions = res.data
        }
      })
      this.$get(this.config.baseUrl + 'bsfDepartment/listDepartmentList', {}).then(res => {
        if (res.success) {
          this.deptOptions = res.data
        }
      })
      this.$get(this.config.baseUrl + 'bsfDictItem/list/categoryCode/000002', {}).then(res => {
        if (res.success) {
          this.jobTypeOptions = res.data
        }
      })
      this.$get(this.config.baseUrl + 'bsfDictItem/list/categoryCode/000016', {}).then(res => {
        if (res.success) {
          this.titleOptions = res.data
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
      Object.keys(this.empSearchForm).map(key => {
        this.empSearchForm[key] = ''
      })
      this.init()
    },
    handleAdd() {
      this.employeeTitle = '新增员工'
      this.employeeAddForm = {}
      this.employeeStatus = true
    },
    submitClick() {
      this.$refs['employeeAddForm'].validate((valid) => {
        if (valid) {
          this.$formDataPost(this.config.baseUrl + 'bsfEmployee/save', this.employeeAddForm, false).then(res => {
            if (res.success) {
              this.$message({
                type: 'success',
                message: '操作成功'
              })
              this.employeeAddForm = {}
              this.employeeStatus = false
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
    handleEdit(row) {
      this.employeeTitle = '编辑员工'
      delete row.createTime
      delete row.updateTime
      const addForm = {}
      Object.keys(row).map(key => {
        addForm[key] = row[key]
      })
      this.employeeAddForm = addForm
      this.employeeStatus = true
    },
    handleRemove(row) {
      this.$confirm('此操作将删除选中记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$formDataPost(this.config.baseUrl + 'bsfEmployee/remove/' + row.id, {}, false).then(res => {
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
      return data.deptName.indexOf(value) !== -1
    },
    handleDeptRelation(row) {
      this.deptStatus = true
    },
    submitdeptClick() {
    }
  }
}
</script>
<style lang="less">
.employee{
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
