<template>
  <div class="menu">
    <el-form
      :inline="true"
      size="mini"
      :style="{margin:'0px 0px 5px 0px'}"
      class="menu-form"
    >
      <el-button type="primary" size="mini" icon="el-icon-search" @click="handleRefresh">刷 新</el-button>
      <el-button type="primary" size="mini" icon="el-icon-check" @click="handleAdd">新 增</el-button>
      <el-button type="primary" size="mini" icon="el-icon-check" @click="handleEdit">编 辑</el-button>
      <el-button type="primary" size="mini" icon="el-icon-check" @click="handleRemove">删 除</el-button>
    </el-form>
    <el-table
      ref="menuTable"
      :data="tableData"
      row-key="id"
      border
      highlight-current-row
      height="400px"
      :tree-props="{children: 'childList'}"
      size="mini"
      @current-change="handleSelectionChange"
    >
      <el-table-column
        prop="menuName"
        label="菜单名称"
        width="180"
      />
      <el-table-column
        prop="url"
        label="菜单地址"
      />
      <el-table-column
        prop="sortNo"
        label="排序号"
        width="180"
      />
    </el-table>

    <el-dialog :visible.sync="menuStatus" :title="menuTitle" width="30%">
      <el-form ref="menuAddForm" :model="menuAddForm" :rules="rules" class="dialog-form" label-width="80px">
        <el-form-item label="菜单名称" prop="menuName">
          <el-input v-model="menuAddForm.menuName" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="菜单地址">
          <el-input v-model="menuAddForm.url" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="排序号">
          <el-input v-model="menuAddForm.sortNo" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="上级菜单">
          <el-input v-model="menuAddForm.parentName" placeholder="请选择" :readonly="true"><el-button slot="append" icon="el-icon-s-operation" @click="parentMenuStatus=true" /></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="menuStatus = false">取 消</el-button>
        <el-button type="primary" @click="submitClick">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog :visible.sync="parentMenuStatus" title="上级菜单" width="35%" top="10px">
      <el-input
        v-model="filterText"
        placeholder="输入关键字进行过滤"
      />
      <el-tree
        ref="tree"
        class="filter-tree"
        :data="parentMenuData"
        :props="defaultProps"
        :expand-on-click-node="false"
        :filter-node-method="filterNode"
        style="height:350px;overflow-y: auto;"
        accordion
        @node-click="nodeClick"
      />
      <span slot="footer" class="dialog-footer">
        <el-button @click="parentMenuStatus = false">取 消</el-button>
        <el-button type="primary" @click="submitParentMenuClick">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'Dashboard',
  data() {
    return {
      menuTitle: '新增菜单',
      tableData: [],
      menuStatus: false,
      menuAddForm: {},
      rules: {
        menuName: [
          { required: true, message: '请输入菜单名称', trigger: 'blur' }
        ]
      },
      selectRow: {},
      selectParentRow: {
        id: 0,
        menuName: ''
      },
      parentMenuStatus: false,
      filterText: '',
      parentMenuData: [],
      defaultProps: {
        children: 'childList',
        label: 'menuName'
      }
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
      this.$get(this.config.baseUrl + 'bsfMenu/queryTreeMenu', {}).then(res => {
        if (res.success) {
          this.tableData = res.data
          this.parentMenuData = res.data
        }
      })
    },
    handleRefresh() {
      this.init()
    },
    handleAdd() {
      this.menuTitle = '新增菜单'
      this.menuAddForm = { parentId: 0 }
      this.menuStatus = true
    },
    submitClick() {
      this.$refs['menuAddForm'].validate((valid) => {
        if (valid) {
          this.$formDataPost(this.config.baseUrl + 'bsfMenu/save', this.menuAddForm, false).then(res => {
            if (res.success) {
              this.$message({
                type: 'success',
                message: '操作成功'
              })
              this.menuStatus = false
              this.menuAddForm = {}
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
      this.menuTitle = '编辑菜单'
      delete selectData.createTime
      delete selectData.updateTime
      const addForm = {}
      Object.keys(selectData).map(key => {
        addForm[key] = selectData[key]
      })
      this.menuAddForm = addForm
      this.menuStatus = true
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
        this.$formDataPost(this.config.baseUrl + 'bsfMenu/remove/' + selectData.id, {}, false).then(res => {
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
      delete row.createTime
      delete row.updateTime
      this.selectRow = row
    },
    filterNode(value, data) {
      if (!value) {
        return true
      }
      return data.menuName.indexOf(value) !== -1
    },
    submitParentMenuClick() {
      if (this.selectParentRow.id === 0) {
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
      this.menuAddForm.parentName = this.selectParentRow.menuName
      this.menuAddForm.parentId = this.selectParentRow.id
      this.parentMenuStatus = false
    },
    nodeClick(node) {
      this.selectParentRow = {
        id: node.id,
        menuName: node.menuName
      }
    }
  }
}
</script>
<style lang="less">
.menu{
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
