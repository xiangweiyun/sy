<template>
  <div class="org">
    <el-form
      :inline="true"
      :size="size"
      class="org-form"
    >
      <el-form-item>
        <el-button type="primary" :size="size" icon="el-icon-edit" @click="handleAdd">新 增</el-button>
        <el-button type="primary" :size="size" icon="el-icon-refresh" @click="handleRefresh">刷 新</el-button>
      </el-form-item>
    </el-form>
    <el-table
      ref="orgTable"
      v-loading="listLoading"
      border
      :data="tableData"
      :height="tableHeight"
      :size="size"
      row-key="id"
      :tree-props="{children: 'children'}"
    >
      <el-table-column prop="code" label="机构编码" />
      <el-table-column prop="name" label="机构名称" align="center" />
      <el-table-column prop="levelCode" label="机构级别" align="center">
        <template slot-scope="scope">
          <span>{{ formatterOrgLevel(scope.row.levelCode) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="orgType" label="机构类型" align="center">
        <template slot-scope="scope">
          <span>{{ formatterOrgType(scope.row.orgType) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="联系电话" align="center" />
      <el-table-column prop="orgAddress" label="地址" align="center" width="160" />
      <el-table-column prop="orgProfile" label="机构简介" align="center" />
      <el-table-column label="操作" align="center" width="110">
        <template slot-scope="scope">
          <el-button type="text" :size="size" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="text" :size="size" @click="handleRemove(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      :visible.sync="orgStatus"
      :close-on-click-modal="false"
      :title="orgTitle"
      width="800px"
      top="50px"
    >
      <el-form
        ref="orgForm"
        :model="orgForm"
        :rules="rules"
        :size="size"
        label-width="80px"
      >
        <el-col :span="12">
          <el-form-item label="机构名称" prop="name">
            <el-input v-model="orgForm.name" placeholder="请输入" />
          </el-form-item>
          <el-form-item label="机构类型" prop="orgType">
            <el-select
              v-model="orgForm.orgType"
              placeholder="请选择"
              style="width: 100%"
            >
              <el-option
                v-for="item in orgTypeList"
                :key="item.dictValue"
                :label="item.dictLabel"
                :value="item.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="上级机构">
            <el-select
              v-model="orgForm.orgLevel"
              clearable
              placeholder="请选择"
              style="width: 100%"
            >
              <el-option
                v-for="item in parentOrgList"
                v-show="item.id != orgForm.id"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="机构编码" prop="code">
            <el-input v-model="orgForm.code" placeholder="请输入" />
          </el-form-item>
          <el-form-item label="机构级别" prop="levelCode">
            <el-select
              v-model="orgForm.levelCode"
              placeholder="请选择"
              style="width: 100%"
            >
              <el-option
                v-for="item in levelCodeList"
                :key="item.dictValue"
                :label="item.dictLabel"
                :value="item.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="联系电话">
            <el-input v-model="orgForm.phone" placeholder="请输入" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="地址">
            <el-input
              v-model="orgForm.orgAddress"
              placeholder="请输入"
              type="textarea"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="经度">
            <el-input v-model="orgForm.longitude" placeholder="请输入" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="纬度">
            <el-input v-model="orgForm.latitude" placeholder="请输入" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="简介">
            <el-input
              v-model="orgForm.orgProfile"
              placeholder="请输入"
              type="textarea"
            />
          </el-form-item>
          <el-form-item label="机构图片">
            <el-upload
              class="avatar-uploader"
              action="#"
              :auto-upload="false"
              name="file"
              :show-file-list="false"
              :on-change="handleChange"
            >
              <img
                v-if="orgForm.picture"
                :src="baseImgUrl + orgForm.picture"
                class="avatar"
              >
              <i v-else class="el-icon-plus avatar-uploader-icon" />
            </el-upload>
          </el-form-item>
        </el-col>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="orgStatus = false">取 消</el-button>
        <el-button type="primary" @click="submitClick">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  listOrg,
  saveOrg,
  delOrg
} from '@/api/system/org'
import {
  listUserOrg
} from '@/api/system/user'
import {
  listDistItem
} from '@/api/system/dist'
import {
  uploadFile
} from '@/api/system/file'
import {
  initBaseUrl
} from '@/utils'

export default {
  data() {
    return {
      size: 'mini',
      // 数据列表加载动画
      listLoading: false,
      // 查询参数
      searchForm: {
        orgId: ''
      },
      orgTitle: '新增机构',
      tableData: [],
      tableHeight: '200px',
      orgStatus: false,
      // 新增、编辑对象
      orgForm: {
        // 主键
        id: '',
        // 机构编码
        code: '',
        // 机构名称
        name: '',
        // 机构级别
        levelCode: 0,
        // 纬度
        latitude: 0,
        // 经度
        longitude: 0,
        // 机构地址
        orgAddress: '',
        // 机构简介
        orgProfile: '',
        // 机构类型
        orgType: '',
        // 上级机构
        parentId: '',
        // 联系电话
        phone: '',
        // 机构图片
        picture: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入机构名称', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入机构编码', trigger: 'blur' }
        ],
        orgType: [
          { required: true, message: '请选择机构类型', trigger: 'change' }
        ],
        levelCode: [
          { required: true, message: '请选择机构级别', trigger: 'change' }
        ]
      },
      selectRow: {},
      // 机构类型选项
      orgTypeList: [],
      // 机构级别选项
      levelCodeList: [],
      parentOrgList: [],
      baseImgUrl: initBaseUrl() + '/image/'
    }
  },
  created: function() {
    this.size = window.CONFIG.SYSTEM_SIZE
    if (this.$store.state) {
      this.searchForm.orgId = this.$store.state.user.userInfo.orgId
      this.searchForm.orgName = this.$store.state.user.userInfo.orgName
    }
    // 机构列表初始化
    this.parentOrgInit()
    this.dictInit()
    this.init()
  },
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - this.$refs['orgTable'].$el.offsetTop - 180
    })
  },
  methods: {
    init() {
      const that = this
      that.listLoading = true
      listOrg(this.searchForm).then(
        (res) => {
          that.tableData = res
          that.listLoading = false
        }
      )
    },
    parentOrgInit() {
      listUserOrg(this.searchForm).then(
        (res) => {
          this.parentOrgList = res
        }
      )
    },
    dictInit() {
      // 机构类型选项
      listDistItem({ dictType: 'ORG_TYPE' }).then((res) => {
        this.orgTypeList = res
      })
      // 机构级别选项
      listDistItem({ dictType: 'ORG_LEVEL' }).then((res) => {
        this.levelCodeList = res
      })
    },
    formatterOrgType(val) {
      let value = ''
      if (val) {
        this.orgTypeList.forEach(item => {
          if (item.dictValue === val) {
            value = item.dictLabel
          }
        })
      }
      return value
    },
    formatterOrgLevel(val) {
      let value = ''
      if (val) {
        this.levelCodeList.forEach(item => {
          if (item.dictValue === val) {
            value = item.dictLabel
          }
        })
      }
      return value
    },
    handleRefresh() {
      this.init()
    },
    handleAdd() {
      this.orgTitle = '新增机构'
      Object.keys(this.orgForm).map(key => {
        this.orgForm[key] = ''
      })
      this.orgStatus = true
    },
    handleEdit(row) {
      this.orgTitle = '编辑机构'
      Object.keys(this.orgForm).map((key) => {
        this.orgForm[key] = row[key]
      })
      this.$nextTick(() => {
        this.$refs['orgForm'].clearValidate()
      })
      this.orgStatus = true
    },
    submitClick() {
      if (this.orgForm.id) {
        if (this.orgForm.id === this.orgForm.parentId) {
          this.$message({
            type: 'warning',
            message: '请重新选择，不能以自己作为上级机构'
          })
          return false
        }
      }
      this.$refs['orgForm'].validate((valid) => {
        if (valid) {
          saveOrg(this.orgForm).then((res) => {
            this.$message({
              type: 'success',
              message: '操作成功'
            })
            this.orgStatus = false
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
          message: '该机构存在子记录不能删除'
        })
        return false
      }
      this.$confirm('此操作将删除选中记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delOrg(row.id).then((res) => {
          this.$message({
            type: 'success',
            message: '操作成功!'
          })
          this.init()
        })
      })
    },
    handleChange(file) {
      const isLt10M = file.size / 1024 / 1024 < 10
      var testmsg = /^image\/(jpeg|png|jpg)$/.test(file.raw.type)
      if (!testmsg) {
        this.$message.error('上传图片只能是 JPEG|PNG|JPG 格式!')
        return
      }
      if (!isLt10M) {
        this.$message.error('上传图片大小不能超过 10MB!')
        return
      }
      const form = new FormData()
      form.append('file', file.raw)
      form.append('source', 'USER')
      uploadFile(form).then(
        (res) => {
          this.$message({
            type: 'success',
            message: '上传成功'
          })
          this.orgForm.picture = res
        }
      )
    }
  }
}
</script>
<style lang="less">
.org {
  &-form {
    .el-form-item {
      margin: 0px 0px 5px 0px
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
