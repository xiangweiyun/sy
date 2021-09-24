<template>
  <div class="menu">
    <el-form
      :inline="true"
      size="mini"
      class="menu-form"
    >
      <el-form-item>
        <el-button
          type="primary"
          size="mini"
          icon="el-icon-search"
          @click="handleRefresh"
        >刷 新</el-button>
        <el-button
          type="primary"
          size="mini"
          icon="el-icon-edit"
          @click="handleAdd()"
        >新 增</el-button>
      </el-form-item>
    </el-form>
    <el-table
      ref="menuTable"
      v-loading="listLoading"
      :data="tableData"
      row-key="id"
      border
      lazy
      :height="tableHeight"
      :tree-props="{children: 'children'}"
      size="mini"
    >
      <el-table-column prop="name" label="菜单名称" />
      <el-table-column prop="parentName" label="上级菜单" />
      <el-table-column prop="menuType" label="菜单类型" align="center" />
      <el-table-column prop="url" label="菜单地址" />
      <el-table-column prop="icon" label="菜单图标" align="center" />
      <el-table-column prop="target" label="打开方式" align="center" />
      <el-table-column prop="orderNum" label="排序" align="center" width="50" />
      <el-table-column
        label="操作"
        align="center"
        width="160"
      >
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="handleAdd(scope.row)">新增子记录</el-button>
          <el-button type="text" size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="text" size="mini" @click="handleRemove(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 新增、编辑菜单信息 -->
    <el-dialog :visible.sync="menuStatus" :close-on-click-modal="false" :title="menuTitle" width="400px">
      <el-form ref="menuForm" :model="menuForm" :rules="rules" label-width="80px">
        <el-form-item label="菜单名称" prop="name">
          <el-input v-model="menuForm.name" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="上级菜单">
          <el-input v-model="menuForm.parentName" placeholder="请选择" :readonly="true"><el-button slot="append" icon="el-icon-s-operation" @click="parentStatus=true" /></el-input>
        </el-form-item>
        <el-form-item label="菜单类型" prop="menuType">
          <el-select
            v-model="menuForm.menuType"
            placeholder="请选择"
            style="width: 100%"
          >
            <el-option
              v-for="item in menuTypeList"
              :key="item.dictValue"
              :label="item.dictLabel"
              :value="item.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="菜单地址">
          <el-input v-model="menuForm.url" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="菜单图标">
          <el-input v-model="menuForm.icon" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="打开方式">
          <el-select
            v-model="menuForm.target"
            placeholder="请选择"
            style="width: 100%"
          >
            <el-option
              v-for="item in targetList"
              :key="item.dictValue"
              :label="item.dictLabel"
              :value="item.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="排序号">
          <el-input v-model="menuForm.orderNum" placeholder="请输入" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="menuStatus = false">取 消</el-button>
        <el-button type="primary" @click="submitClick">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 上级菜单 -->
    <el-dialog :visible.sync="parentStatus" :close-on-click-modal="false" title="上级菜单" width="400px" top="10px">
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
        <el-button @click="parentStatus = false">取 消</el-button>
        <el-button type="primary" @click="submitParentMenuClick">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  listMenu,
  saveMenu,
  delMenu
} from '@/api/system/menu'
import {
  listDistItem
} from '@/api/system/dist'
export default {
  data() {
    return {
      // 数据列表加载动画
      listLoading: true,
      tableHeight: '200px',
      menuTitle: '新增菜单',
      tableData: [],
      menuStatus: false,
      menuForm: {
        // 主键
        id: '',
        // 菜单名称
        name: '',
        // 上级菜单
        parentId: '',
        parentName: '',
        // 菜单类型
        menuType: '',
        // 菜单地址
        url: '',
        // 菜单图标
        icon: '',
        // 打开方式
        target: '',
        // 显示顺序
        orderNum: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入菜单名称', trigger: 'blur' }
        ],
        menuType: [
          { required: true, message: '请选择菜单类型', trigger: 'change' }
        ]
      },
      // 菜单类型集合
      menuTypeList: [],
      // 打开方式集合
      targetList: [],
      selectRow: {},
      filterText: '',
      selectParentRow: {},
      parentMenuData: [],
      parentStatus: false,
      defaultProps: {
        isLeaf: 'leaf',
        label: 'districtName'
      }
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val)
    }
  },
  created: function() {
    this.dictInit()
    this.init()
  },
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - this.$refs['menuTable'].$el.offsetTop - 180
    })
  },
  methods: {
    init() {
      this.listLoading = true
      listMenu().then(res => {
        this.tableData = res
        this.listLoading = false
      })
    },
    dictInit() {
      // 菜单类型选项
      listDistItem({ dictType: 'MENU_TYPE' }).then((res) => {
        this.menuTypeList = res
      })
      // 菜单打开方式选项
      listDistItem({ dictType: 'TARGET' }).then((res) => {
        this.targetList = res
      })
    },
    handleRefresh() {
      this.init()
    },
    handleAdd(row) {
      this.menuTitle = '新增菜单'
      Object.keys(this.menuForm).map(key => {
        this.menuForm[key] = ''
      })
      console.log(row)
      if (row) {
        this.menuForm.parentId = row.id
        this.menuForm.parentName = row.name
      }
      this.menuStatus = true
    },
    handleEdit(row) {
      this.selectRow = row
      this.menuTitle = '编辑菜单'
      Object.keys(this.menuForm).map(key => {
        this.menuForm[key] = row[key]
      })
      this.menuStatus = true
    },
    submitClick() {
      this.$refs['menuForm'].validate((valid) => {
        if (valid) {
          saveMenu(this.menuForm).then((res) => {
            this.$message({
              type: 'success',
              message: '操作成功'
            })
            this.menuStatus = false
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
          message: '该菜单存在子记录不能删除'
        })
        return false
      }
      this.$confirm('此操作将删除选中记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delMenu(row.id).then((res) => {
          this.$message({
            type: 'success',
            message: '操作成功!'
          })
          this.init()
        })
      })
    },
    filterNode(value, data) {
      if (!value) {
        return true
      }
      return data.name.indexOf(value) !== -1
    },
    nodeClick(row) {
      this.selectParentRow = row
    },
    submitParentmenuClick() {
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
      this.menuForm.parentName = this.selectParentRow.name
      this.menuForm.parentId = this.selectParentRow.id
      this.parentStatus = false
    }
  }
}
</script>
<style lang="less">
.menu {
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
