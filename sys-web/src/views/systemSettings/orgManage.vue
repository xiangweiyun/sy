<template>
  <div class="org">
    <el-form
      :inline="true"
      size="mini"
      class="org-form"
    >
      <el-form-item>
        <el-button
          type="primary"
          size="mini"
          icon="el-icon-edit"
          @click="handleAdd"
        >新 增</el-button>
        <el-button
          type="primary"
          size="mini"
          icon="el-icon-refresh"
          @click="handleRefresh"
        >刷 新</el-button>
      </el-form-item>
    </el-form>
    <el-table
      ref="orgTable"
      v-loading="listLoading"
      border
      :data="tableData"
      :height="tableHeight"
      size="mini"
      row-key="id"
      :tree-props="{children: 'children'}"
    >
      <el-table-column prop="code" label="机构编码" align="center" />
      <el-table-column prop="name" label="机构名称" align="center" />
      <el-table-column prop="levelCode" label="机构级别" align="center" />
      <el-table-column prop="orgType" label="机构类型" align="center" />
      <el-table-column prop="phone" label="联系电话" align="center" />
      <el-table-column prop="orgDesc" label="地址" align="center" />
      <el-table-column prop="orgProfile" label="机构简介" align="center" />
      <el-table-column prop="sortNo" label="排序号" align="center" />
      <el-table-column prop="picture" label="机构图片" align="center" />
      <el-table-column label="操作" align="center" width="150">
        <template slot-scope="scope">
          <el-button
            type="text"
            size="mini"
            @click="handleEdit(scope.row)"
          >编辑</el-button>
          <el-button
            type="text"
            size="mini"
            @click="handleRemove(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      :visible.sync="orgStatus"
      :title="orgTitle"
      width="80%"
      top="10px"
    >
      <el-form
        ref="orgAddForm"
        :model="orgAddForm"
        :rules="rules"
        class="dialog-form"
        label-width="80px"
      >
        <el-col :span="8">
          <el-form-item label="机构编码" prop="orgCode">
            <el-input v-model="orgAddForm.orgCode" placeholder="请输入" />
          </el-form-item>
          <el-form-item label="机构等次">
            <el-select
              v-model="orgAddForm.orgOrder"
              placeholder="请选择"
              style="width: 100%"
            >
              <el-option
                v-for="item in orgOrderSelect"
                :key="item.itemCode"
                :label="item.itemName"
                :value="item.itemCode"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="是否禁用">
            <el-select
              v-model="orgAddForm.deleteFlag"
              placeholder="请选择"
              style="width: 100%"
            >
              <el-option label="否" value="0" />
              <el-option label="是" value="1" />
            </el-select>
          </el-form-item>
          <el-form-item label="经度">
            <el-input
              v-model="orgAddForm.longitude"
              placeholder="请输入"
              type="number"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="机构名称" prop="orgName">
            <el-input v-model="orgAddForm.orgName" placeholder="请输入" />
          </el-form-item>
          <el-form-item label="管理类型">
            <el-select
              v-model="orgAddForm.manageType"
              placeholder="请选择"
              style="width: 100%"
            >
              <el-option
                v-for="item in manageTypeSelect"
                :key="item.itemCode"
                :label="item.itemName"
                :value="item.itemCode"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="联系人">
            <el-input v-model="orgAddForm.contact" placeholder="请输入" />
          </el-form-item>
          <el-form-item label="纬度">
            <el-input
              v-model="orgAddForm.latitude"
              placeholder="请输入"
              type="number"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="机构级别">
            <el-select
              v-model="orgAddForm.orgLevel"
              placeholder="请选择"
              style="width: 100%"
            >
              <el-option
                v-for="item in orgLevelSelect"
                :key="item.itemCode"
                :label="item.itemName"
                :value="item.itemCode"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="上级机构">
            <el-input
              v-model="orgAddForm.parentName"
              placeholder="请选择"
              :readonly="true"
            ><el-button
              slot="append"
              icon="el-icon-s-operation"
              @click="parentStatus = true"
            /></el-input>
          </el-form-item>
          <el-form-item label="联系电话">
            <el-input v-model="orgAddForm.contactPhone" placeholder="请输入" />
          </el-form-item>
          <el-form-item label="排序号">
            <el-input
              v-model="orgAddForm.sortNo"
              placeholder="请输入"
              type="number"
            />
          </el-form-item>
        </el-col>
        <el-col :span="16">
          <el-form-item label="地址">
            <el-cascader
              v-model="orgAddForm.addrProvinceName"
              :options="clearableOptions"
              :props="clearableProps"
              style="width: 100%"
            />
          </el-form-item>
          <el-form-item label="详细地址">
            <el-input
              v-model="orgAddForm.addrDetail"
              placeholder="请输入"
              type="textarea"
            />
          </el-form-item>
          <el-form-item label="简介">
            <el-input
              v-model="orgAddForm.summary"
              placeholder="请输入"
              type="textarea"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="图片">
            <el-upload
              class="avatar-uploader"
              action="https://jsonplaceholder.typicode.com/posts/"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
            >
              <img
                v-if="orgAddForm.imageUrl"
                :src="orgAddForm.imageUrl"
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
  listOrg
  // saveOrg,
  // delOrg
} from '@/api/system/org'
export default {
  data() {
    return {
      // 数据列表加载动画
      listLoading: false,
      // 查询参数
      orgId: '',
      orgTitle: '新增机构',
      tableData: [],
      tableHeight: '200px',
      orgStatus: false,
      orgAddForm: {},
      rules: {
        orgCode: [
          { required: true, message: '请输入机构编码', trigger: 'blur' }
        ],
        orgName: [
          { required: true, message: '请输入机构名称', trigger: 'blur' }
        ]
      },
      selectRow: {},
      menuStatus: false,
      filterText: '',
      defaultProps: {
        children: 'children',
        label: 'menuName'
      },
      relationUserStatus: false,
      relationUserTableData: [],
      clearableOptions: [],
      clearableProps: {
        lazy: true,
        lazyLoad(node, resolve) {
          /* this.$get(this.config.baseUrl+'bsfDistrict/getByParentId/'+node.data.id, {}).then(res => {
                    if (res.success) {
                        resolve(res.data);
                    }
                }); */
        },
        value: 'id',
        label: 'districtName'
      },
      manageTypeSelect: [],
      orgLevelSelect: [],
      orgOrderSelect: []
    }
  },
  computed: {
    deleteFlag() {
      return this.selectRow.deleteFlag === 1
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val)
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - this.$refs['orgTable'].$el.offsetTop - 100
    })
  },
  created: function() {
    this.init()
    this.clearableOptionsInit()
    this.dictCategoryInit()
  },
  methods: {
    init() {
      const that = this
      that.listLoading = true
      var paramData = {
        orgId: this.orgId
      }
      listOrg(paramData).then(
        (res) => {
          that.tableData = res
          that.listLoading = false
        }
      )
    },
    dictCategoryInit() {
      this.$get(
        this.config.baseUrl + 'bsfDictItem/list/categoryCode/000008',
        {}
      ).then((res) => {
        if (res.success) {
          this.manageTypeSelect = res.data
        }
      })
      this.$get(
        this.config.baseUrl + 'bsfDictItem/list/categoryCode/000005',
        {}
      ).then((res) => {
        if (res.success) {
          this.orgLevelSelect = res.data
        }
      })
      this.$get(
        this.config.baseUrl + 'bsfDictItem/list/categoryCode/000006',
        {}
      ).then((res) => {
        if (res.success) {
          this.orgOrderSelect = res.data
        }
      })
    },
    clearableOptionsInit() {
      this.$get(this.config.baseUrl + 'bsfDistrict/getByParentId/0', {}).then(
        (res) => {
          if (res.success) {
            this.clearableOptions = res.data
          }
        }
      )
    },
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage
      this.init()
    },
    handleSearch() {
      this.init()
    },
    handleRefresh() {
      this.currentPage = 1
      this.searchForm = {}
      this.init()
    },
    handleAdd() {
      this.orgTitle = '新增机构'
      this.orgAddForm = { deleteFlag: '0' }
      this.orgStatus = true
    },
    submitClick() {
      this.$refs['orgAddForm'].validate((valid) => {
        if (valid) {
          this.$formDataPost(
            this.config.baseUrl + 'bsfOrganization/save',
            this.orgAddForm,
            false
          ).then((res) => {
            if (res.success) {
              this.$message({
                type: 'success',
                message: '操作成功'
              })
              this.orgStatus = false
              this.orgAddForm = {}
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
      this.relationUserStatus = true
    },
    handleMenuRelation(row) {
      this.menuStatus = true
    },
    handleEdit(row) {
      this.orgTitle = '编辑机构'
      delete row.createTime
      delete row.updateTime
      const addForm = {}
      Object.keys(row).map((key) => {
        addForm[key] = row[key]
      })
      this.orgAddForm = addForm
      this.orgStatus = true
    },
    handleRemove(row) {
      this.$confirm('此操作将删除选中记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$formDataPost(
          this.config.baseUrl + 'bsfOrganization/remove/' + row.id,
          {},
          false
        ).then((res) => {
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
