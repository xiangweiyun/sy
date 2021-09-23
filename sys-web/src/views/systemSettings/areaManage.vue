<template>
  <div class="area">
    <el-form
      :inline="true"
      size="mini"
      :style="{ margin: '0px 0px 5px 0px' }"
      class="area-form"
    >
      <el-button
        type="primary"
        size="mini"
        icon="el-icon-search"
        @click="handleRefresh"
      >刷 新</el-button>
      <el-button
        type="primary"
        size="mini"
        icon="el-icon-check"
        @click="handleAdd"
      >新 增</el-button>
      <el-button
        type="primary"
        size="mini"
        icon="el-icon-check"
        @click="handleEdit"
      >编 辑</el-button>
      <el-button
        type="primary"
        size="mini"
        icon="el-icon-check"
        @click="handleRemove"
      >删 除</el-button>
    </el-form>
    <el-table
      ref="areaTable"
      :data="tableData"
      row-key="id"
      border
      highlight-current-row
      lazy
      :load="load"
      height="400px"
      :tree-props="{ hasChildren: 'hasChildren' }"
      size="mini"
      @current-change="handleSelectionChange"
    >
      <el-table-column prop="districtName" label="行政区划名称" />
      <el-table-column prop="districtCode" label="行政区划代码" />
      <el-table-column prop="parentDistrictName" label="上级行政区划" />
    </el-table>

    <el-dialog :visible.sync="areaStatus" :title="areaTitle" width="30%">
      <el-form
        ref="areaAddForm"
        :model="areaAddForm"
        :rules="rules"
        class="dialog-form"
        label-width="80px"
      >
        <el-form-item label="区域代码" prop="districtCode">
          <el-input v-model="areaAddForm.districtCode" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="区域名称" prop="districtName">
          <el-input v-model="areaAddForm.districtName" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="上级区域">
          <el-input
            v-model="areaAddForm.parentDistrictName"
            placeholder="请选择"
            :readonly="true"
          ><el-button
            slot="append"
            icon="el-icon-s-operation"
            @click="parentStatus = true"
          /></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="areaStatus = false">取 消</el-button>
        <el-button type="primary" @click="submitClick">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      :visible.sync="parentStatus"
      title="上级行政区划"
      width="35%"
      top="10px"
    >
      <el-tree
        class="filter-tree"
        :props="defaultProps"
        :load="loadNode"
        lazy
        style="height: 380px; overflow-y: auto"
        :expand-on-click-node="false"
        accordion
        @node-click="nodeClick"
      />
      <span slot="footer" class="dialog-footer">
        <el-button @click="parentStatus = false">取 消</el-button>
        <el-button
          type="primary"
          @click="submitParentareaClick"
        >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'Dashboard',
  data() {
    return {
      areaTitle: '新增行政区划',
      tableData: [],
      areaStatus: false,
      areaAddForm: {},
      rules: {
        districtCode: [
          { required: true, message: '请输入行政区划代码', trigger: 'blur' }
        ],
        districtName: [
          { required: true, message: '请输入行政区划名称', trigger: 'blur' }
        ]
      },
      selectRow: {},
      selectParentRow: {},
      parentStatus: false,
      defaultProps: {
        children: 'leaf',
        isLeaf: 'leaf',
        label: 'districtName'
      }
    }
  },
  created: function() {
    this.init()
  },
  methods: {
    init() {
      this.tableData = []
      this.$get(this.config.baseUrl + 'bsfDistrict/getByParentId/0', {}).then(
        (res) => {
          if (res.success) {
            this.tableData = res.data
          }
        }
      )
    },
    handleRefresh() {
      this.init()
    },
    handleAdd() {
      this.areaTitle = '新增行政区划'
      this.areaAddForm = { parentId: 0 }
      this.areaStatus = true
    },
    submitClick() {
      this.$refs['areaAddForm'].validate((valid) => {
        if (valid) {
          this.$formDataPost(
            this.config.baseUrl + 'bsfDistrict/save',
            this.areaAddForm,
            false
          ).then((res) => {
            if (res.success) {
              this.$message({
                type: 'success',
                message: '操作成功'
              })
              this.areaStatus = false
              this.areaAddForm = {}
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
    handleEdit() {
      const selectData = this.selectRow
      if (JSON.stringify(selectData) === '{}') {
        this.$message({
          type: 'warning',
          message: '请选择一条数据进行操作'
        })
        return false
      }
      this.areaTitle = '编辑行政区划'
      delete selectData.createTime
      delete selectData.updateTime
      const addForm = {}
      Object.keys(selectData).map((key) => {
        addForm[key] = selectData[key]
      })
      this.areaAddForm = addForm
      this.areaStatus = true
    },
    handleRemove() {
      const selectData = this.selectRow
      if (JSON.stringify(selectData) === '{}') {
        this.$message({
          type: 'warning',
          message: '请选择一条数据进行操作'
        })
        return false
      }
      this.$confirm('此操作将删除选中记录以及子记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$formDataPost(
          this.config.baseUrl + 'bsfDistrict/remove/' + selectData.id,
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
    handleSelectionChange(row) {
      this.selectRow = {
        id: row.id,
        parentId: row.parentId,
        parentDistrictName: row.parentDistrictName,
        districtCode: row.districtCode,
        districtName: row.districtName
      }
    },
    submitParentareaClick() {
      if (JSON.stringify(this.selectParentRow) === '{}') {
        this.$message({
          type: 'warning',
          message: '请选择一条数据'
        })
        return false
      }
      if (this.selectParentRow.id === this.selectRow.id) {
        this.$message({
          type: 'warning',
          message: '请重新选择，不能以自己作为上级菜单'
        })
        return false
      }
      this.areaAddForm.parentDistrictName = this.selectParentRow.districtName
      this.areaAddForm.parentId = this.selectParentRow.id
      this.parentStatus = false
    },
    nodeClick(row) {
      this.selectParentRow = {
        id: row.id,
        districtCode: row.districtCode,
        districtName: row.districtName
      }
    },
    load(row, treeNode, resolve) {
      this.$get(
        this.config.baseUrl + 'bsfDistrict/getByParentId/' + row.id,
        {}
      ).then((res) => {
        if (res.success) {
          resolve(res.data)
        }
      })
    },
    loadNode(row, resolve) {
      if (row.data === undefined) {
        this.$get(this.config.baseUrl + 'bsfDistrict/getByParentId/0', {}).then(
          (res) => {
            if (res.success) {
              return resolve(res.data)
            }
          }
        )
        return
      }
      this.$get(
        this.config.baseUrl + 'bsfDistrict/getByParentId/' + row.data.id,
        {}
      ).then((res) => {
        if (res.success) {
          resolve(res.data)
        }
      })
    }
  }
}
</script>
<style lang="less">
.area {
  &-form {
    .el-form-item {
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
