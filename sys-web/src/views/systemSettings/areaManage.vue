<template>
  <div class="area">
    <el-form
      :inline="true"
      :size="size"
      class="area-form"
    >
      <el-button type="primary" :size="size" icon="el-icon-search" @click="handleRefresh">刷 新</el-button>
      <el-button type="primary" :size="size" icon="el-icon-edit" @click="handleAdd">新 增</el-button>
    </el-form>
    <el-table
      ref="areaTable"
      v-loading="listLoading"
      :data="tableData"
      row-key="id"
      border
      lazy
      :load="load"
      :height="tableHeight"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      :size="size"
    >
      <el-table-column prop="districtName" label="行政区划名称" />
      <el-table-column prop="districtCode" label="行政区划代码" />
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="text" :size="size" @click="handleAdd(scope.row)">新增子记录</el-button>
          <el-button type="text" :size="size" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="text" :size="size" @click="handleRemove(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="areaStatus" :title="areaTitle" width="30%">
      <el-form
        ref="areaForm"
        :model="areaForm"
        :rules="rules"
        label-width="110px"
        :size="size"
      >
        <el-form-item label="行政区划名称" prop="districtName">
          <el-input v-model="areaForm.districtName" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="行政区划编码" prop="districtCode">
          <el-input v-model="areaForm.districtCode" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="上级行政区划">
          <el-input
            v-model="areaForm.parentName"
            placeholder="上级行政区划"
            clearable
            :readonly="true"
          >
            <el-button slot="append" icon="el-icon-circle-close" @click="handelRemoveParent" />
            <el-button slot="append" icon="el-icon-s-operation" @click="parentStatus = true" />
          </el-input>
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
      width="400px"
    >
      <el-tree
        class="filter-tree"
        :props="defaultProps"
        :load="loadNode"
        lazy
        style="height: 380px; overflow-y: auto"
        :expand-on-click-node="false"
        accordion
        :size="size"
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
import {
  listArea,
  listChildrenArea,
  saveArea,
  delArea
} from '@/api/system/area'
export default {
  data() {
    return {
      size: 'mini',
      listLoading: true,
      tableHeight: '200px',
      areaTitle: '新增行政区划',
      tableData: [],
      areaStatus: false,
      areaForm: {
        // 主键
        id: '',
        // 行政区划名称
        districtName: '',
        // 行政区划编码
        districtCode: '',
        // 上级行政区划
        parentId: '',
        parentName: ''
      },
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
        isLeaf: 'leaf',
        label: 'districtName'
      }
    }
  },
  created: function() {
    this.size = window.CONFIG.SYSTEM_SIZE
    this.init()
  },
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - this.$refs['areaTable'].$el.offsetTop - 180
    })
  },
  methods: {
    init() {
      this.listLoading = true
      this.tableData = []
      listArea().then(res => {
        this.tableData = res
        this.listLoading = false
      })
    },
    handleRefresh() {
      this.init()
    },
    handleAdd(row) {
      this.areaTitle = '新增行政区划'
      Object.keys(this.areaForm).map(key => {
        this.areaForm[key] = ''
      })
      if (row) {
        this.areaForm.parentId = row.id
        this.areaForm.parentName = row.districtName
      }
      this.areaStatus = true
    },
    handleEdit(row) {
      this.selectRow = row
      this.areaTitle = '编辑行政区划'
      Object.keys(this.areaForm).map(key => {
        this.areaForm[key] = row[key]
      })
      this.$nextTick(() => {
        this.$refs['areaForm'].clearValidate()
      })
      this.areaStatus = true
    },
    submitClick() {
      this.$refs['areaForm'].validate((valid) => {
        if (valid) {
          if (!this.areaForm.parentName) {
            this.areaForm.parentId = ''
          }
          saveArea(this.areaForm).then((res) => {
            this.$message({
              type: 'success',
              message: '操作成功'
            })
            this.areaStatus = false
            this.init()
          })
        }
      })
    },
    handleRemove(row) {
      const children = row.hasChildren
      if (children && children.length > 0) {
        this.$message({
          type: 'warning',
          message: '该行政区划存在子记录不能删除'
        })
        return false
      }
      this.$confirm('此操作将删除选中记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delArea(row.id).then((res) => {
          this.$message({
            type: 'success',
            message: '操作成功!'
          })
          this.init()
        })
      })
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
          message: '请重新选择，不能以自己作为上级行政区划'
        })
        return false
      }
      this.areaForm.parentName = this.selectParentRow.districtName
      this.areaForm.parentId = this.selectParentRow.id
      this.selectParentRow = {}
      this.parentStatus = false
    },
    load(row, treeNode, resolve) {
      listChildrenArea(row.id).then((res) => {
        res.forEach(item => {
          item.parentName = row.districtName
        })
        console.log(res)
        resolve(res)
      })
    },
    loadNode(row, resolve) {
      if (row.data === undefined) {
        const mainArea = []
        this.tableData.forEach(item => {
          const data = item
          data.leaf = !item.hasChildren
          mainArea.push(data)
        })
        return resolve(mainArea)
      }
      listChildrenArea(row.data.id).then((res) => {
        const childrenArea = []
        res.forEach(item => {
          const data = item
          data.leaf = !item.hasChildren
          data.parentName = row.data.districtName
          childrenArea.push(data)
        })
        resolve(childrenArea)
      })
    },
    nodeClick(row) {
      this.selectParentRow = row
    },
    handelRemoveParent() {
      this.areaForm.parentId = ''
      this.areaForm.parentName = ''
    }
  }
}
</script>
<style lang="less">
.area {
  &-form {
    .el-form-item {
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
