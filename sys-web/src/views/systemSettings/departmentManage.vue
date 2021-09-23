<template>
  <div class="dept">
    <el-form
      :inline="true"
      :model="deptSearchForm"
      size="mini"
      :style="{margin:'0px 0px 5px 0px'}"
      class="dept-form"
    >
      <el-form-item label="所属机构:">
        <el-select v-model="deptSearchForm.orgId" clearable filterable placeholder="请选择">
          <el-option
            v-for="item in orgOptions"
            :key="item.id"
            :label="item.orgName"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="标准科室:">
        <el-select v-model="deptSearchForm.standardDeptSelect" clearable filterable placeholder="请选择">
          <el-option
            v-for="item in orgOptions"
            :key="item.id"
            :label="item.orgName"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="有无标准科室:">
        <el-select v-model="deptSearchForm.isStandardDept" filterable placeholder="请选择">
          <el-option label="全部" value="" />
          <el-option label="有" value="1" />
          <el-option label="无" value="0" />
        </el-select>
      </el-form-item>
      <el-form-item label="是否禁用:">
        <el-select v-model="deptSearchForm.deleteFlag" filterable placeholder="请选择">
          <el-option label="全部" value="" />
          <el-option label="是" value="1" />
          <el-option label="否" value="0" />
        </el-select>
      </el-form-item>
      <el-form-item label="科室编码:">
        <el-input v-model="deptSearchForm.deptCode" placeholder="请输入" size="mini" />
      </el-form-item>
      <el-form-item label="科室名称:">
        <el-input v-model="deptSearchForm.deptName" placeholder="请输入" size="mini" />
      </el-form-item>
      <el-button type="primary" size="mini" icon="el-icon-search" @click="handleSearch">搜 索</el-button>
      <el-button type="primary" size="mini" icon="el-icon-search" @click="handleRefresh">刷 新</el-button>
      <el-button type="primary" size="mini" icon="el-icon-check" @click="handleAdd">新 增</el-button>
    </el-form>
    <el-table
      border
      :data="tableData"
      height="350px"
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
        prop="deptCode"
        label="科室编码"
        align="center"
      />
      <el-table-column
        prop="deptName"
        label="科室名称"
        align="center"
      />
      <el-table-column
        prop="sortNo"
        label="排序号"
        align="center"
      />
      <el-table-column
        prop="orgName"
        label="所属机构"
        align="center"
      />
      <el-table-column
        prop="standardDeptName"
        label="标准科室"
        align="center"
      />
      <el-table-column
        prop="parentName"
        label="上级科室"
        align="center"
      />
      <el-table-column
        prop="bookingRegister"
        label="可排班"
        align="center"
      />
      <el-table-column
        prop="deptHead"
        label="科主任"
        align="center"
      />
      <el-table-column
        prop="deptHeadPhone"
        label="科主任电话"
        align="center"
      />
      <el-table-column
        label="操作"
        align="center"
      >
        <template slot-scope="scope">
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

    <el-dialog :visible.sync="deptStatus" :title="deptTitle" width="60%" top="10px">
      <el-form ref="deptAddForm" :model="deptAddForm" :rules="rules" class="dialog-form" label-width="100px">
        <el-col :span="12">
          <el-form-item label="科室编码" prop="deptCode">
            <el-input v-model="deptAddForm.deptCode" placeholder="请输入" />
          </el-form-item>
          <el-form-item label="所属机构" prop="orgId">
            <el-select v-model="deptAddForm.orgId" clearable filterable placeholder="请选择" style="width:100%;">
              <el-option
                v-for="item in orgOptions"
                :key="item.id"
                :label="item.orgName"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="上级科室">
            <el-select v-model="deptAddForm.parentId" clearable filterable placeholder="请选择" style="width:100%;">
              <el-option
                v-for="item in deptOptions"
                :key="item.id"
                :label="item.deptName"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="科主任">
            <el-input v-model="deptAddForm.deptHead" placeholder="请输入" />
          </el-form-item>
          <el-form-item label="排序号">
            <el-input v-model="deptAddForm.sortNo" placeholder="请输入" />
          </el-form-item>
          <el-form-item label="是否可预约">
            <el-select v-model="deptAddForm.bookingRegister" placeholder="请选择" style="width: 100%;">
              <el-option label="否" value="0" />
              <el-option label="是" value="1" />
            </el-select>
          </el-form-item>
          <el-form-item label="是否禁用">
            <el-select v-model="deptAddForm.deleteFlag" placeholder="请选择" style="width: 100%;">
              <el-option label="否" value="0" />
              <el-option label="是" value="1" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="科室名称" prop="deptName">
            <el-input v-model="deptAddForm.deptName" placeholder="请输入" />
          </el-form-item>
          <el-form-item label="标准科室">
            <el-select v-model="deptAddForm.standardDeptId" clearable filterable placeholder="请选择" style="width:100%;">
              <el-option
                v-for="item in standardDeptOptions"
                :key="item.id"
                :label="item.deptName"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="业务类型">
            <el-select v-model="deptAddForm.businessType" placeholder="请选择" style="width: 100%;">
              <el-option
                v-for="item in businessTypeSelect"
                :key="item.itemCode"
                :label="item.itemName"
                :value="item.itemCode"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="科主任电话">
            <el-input v-model="deptAddForm.deptHeadPhone" placeholder="请输入" />
          </el-form-item>
          <el-form-item label="图片">
            <el-upload
              class="avatar-uploader"
              action="https://jsonplaceholder.typicode.com/posts/"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
            >
              <img v-if="deptAddForm.imageUrl" :src="deptAddForm.imageUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon" />
            </el-upload>
          </el-form-item>
        </el-col>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="deptStatus = false">取 消</el-button>
        <el-button type="primary" @click="submitClick">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'Dashboard',
  data() {
    return {
      deptSearchForm: {
        orgId: '',
        standardDeptSelect: '',
        isStandardDept: '',
        deleteFlag: '',
        deptCode: '',
        deptName: ''
      },
      orgOptions: [],
      deptOptions: [],
      standardDeptOptions: [],
      deptTitle: '新增科室',
      tableData: [],
      currentPage: 1,
      pagesize: 10,
      total: 0,
      deptStatus: false,
      deptAddForm: {},
      rules: {
        deptCode: [
          { required: true, message: '请输入科室编码', trigger: 'blur' }
        ],
        deptName: [
          { required: true, message: '请输入科室名称', trigger: 'blur' }
        ],
        orgId: [
          { required: true, message: '请输入机构名称', trigger: 'blur' }
        ]
      },
      selectRow: {},
      relationUserTableData: [],
      businessTypeSelect: []
    }
  },
  created: function() {
    this.init()
    this.optionInit()
  },
  methods: {
    init() {
      this.deptSearchForm.pageNum = this.currentPage
      this.deptSearchForm.pageSize = this.pagesize
      this.$get(this.config.baseUrl + 'bsfDepartment/page', this.deptSearchForm).then(res => {
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
      this.$get(this.config.baseUrl + 'bsfDictItem/list/categoryCode/900010', {}).then(res => {
        if (res.success) {
          this.businessTypeSelect = res.data
        }
      })
      this.$get(this.config.baseUrl + 'bsfStandardDept/getStandardDept', {}).then(res => {
        if (res.success) {
          this.standardDeptOptions = res.data
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
      Object.keys(this.deptSearchForm).map(key => {
        this.deptSearchForm[key] = ''
      })
      this.init()
    },
    handleAdd() {
      this.deptTitle = '新增科室'
      this.deptAddForm = { bookingRegister: '0', deleteFlag: '0' }
      this.deptStatus = true
    },
    submitClick() {
      this.$refs['deptAddForm'].validate((valid) => {
        if (valid) {
          this.$formDataPost(this.config.baseUrl + 'bsfDepartment/save', this.deptAddForm, false).then(res => {
            if (res.success) {
              this.$message({
                type: 'success',
                message: '操作成功'
              })
              this.deptStatus = false
              this.deptAddForm = {}
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
      this.deptTitle = '编辑科室'
      delete row.createTime
      delete row.updateTime
      const addForm = {}
      Object.keys(row).map(key => {
        addForm[key] = row[key]
      })
      this.deptAddForm = addForm
      this.deptStatus = true
    },
    handleRemove(row) {
      this.$confirm('此操作将删除选中记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$formDataPost(this.config.baseUrl + 'bsfDepartment/remove/' + row.id, {}, false).then(res => {
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
    }
  }
}
</script>
<style lang="less">
.dept{
    &-form{
        .el-form-item{
            margin-bottom: 0px;
        }
    }
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
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
