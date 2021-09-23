<template>
  <div class="dict-category">
    <!-- 字典分类 -->
    <el-col :span="12">
      <el-form
        :model="searchForm"
        :inline="true"
        size="mini"
        class="dict-category-form"
      >
        <el-form-item label="字典类型名称:">
          <el-input v-model="searchForm.dictName" placeholder="请输入" size="mini" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="mini" icon="el-icon-search" @click="handleSearch">搜 索</el-button>
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="handleAdd">新 增</el-button>
        </el-form-item>
      </el-form>
      <el-table
        ref="dictTypeTable"
        v-loading="listLoading"
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
          prop="dictName"
          label="字典名称"
          align="center"
          width="180"
        />
        <el-table-column
          prop="dictType"
          label="字典类型"
          align="center"
          width="100"
        />
        <el-table-column
          label="操作"
          align="center"
        >
          <template slot-scope="scope">
            <el-button type="text" size="mini" @click="dictItemClick(scope.row)">查看字典项</el-button>
            <el-button type="text" size="mini" @click="editDictTypeClick(scope.row)">编辑</el-button>
            <el-button type="text" size="mini" @click="removeDictTypeClick(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页栏 -->
      <Pagination :total="total" :page.sync="searchForm.current" :limit.sync="searchForm.size" @pagination="init" />
    </el-col>
    <!-- 字典项 -->
    <el-col :span="12">
      <el-form
        :model="dictItemForm"
        :inline="true"
        size="mini"
        class="dict-category-form"
      >
        <!-- <el-form-item label="字典项名称:">
          <el-input v-model="dictItemForm.itemName" placeholder="请输入" size="mini" />
        </el-form-item> -->
        <el-form-item>
          <el-button type="primary" size="mini" icon="el-icon-search" @click="handleDictItemSearch">搜 索</el-button>
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="handleDictItemAdd">新 增</el-button>
        </el-form-item>
      </el-form>
      <el-table
        v-loading="listItemLoading"
        border
        :data="itemTableData"
        height="390px"
        size="mini"
      >
        <el-table-column
          prop="dictType"
          label="字典类型"
          align="center"
          width="120"
        />
        <el-table-column
          prop="dictLabel"
          label="字典项名称"
          align="center"
        />
        <el-table-column
          prop="dictValue"
          label="字典项编码"
          align="center"
          width="100"
        />
        <el-table-column
          prop="dictSort"
          label="排序"
          align="center"
          width="50"
        />
        <el-table-column
          label="操作"
          align="center"
          width="100"
        >
          <template slot-scope="scope">
            <el-button type="text" size="mini" @click="editItemClick(scope.row)">编辑</el-button>
            <el-button type="text" size="mini" @click="removeItemClick(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-col>
    <!-- 新增、编辑字典类型 -->
    <el-dialog :visible.sync="dictTypeStatus" :close-on-click-modal="false" :title="dictTypeTitle" width="30%">
      <el-form ref="dictTypeForm" :model="dictTypeForm" :rules="dictTypeRules" label-width="80px">
        <el-form-item label="字典名称" prop="dictName">
          <el-input v-model="dictTypeForm.dictName" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="字典类型" prop="dictType">
          <el-input v-model="dictTypeForm.dictType" placeholder="请输入" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dictTypeStatus = false">取 消</el-button>
        <el-button type="primary" @click="submitClick('dictType')">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 新增、编辑字典项 -->
    <el-dialog :visible.sync="dictItemStatus" :close-on-click-modal="false" :title="dictItemTitle" width="30%">
      <el-form ref="dictItemForm" :model="dictItemForm" :rules="dictItemRules" label-width="100px">
        <el-form-item label="字典项编码" prop="itemCode">
          <el-input v-model="dictItemForm.itemCode" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="字典项名称" prop="itemName">
          <el-input v-model="dictItemForm.itemName" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="排序号" prop="sortNo">
          <el-input v-model="dictItemForm.sortNo" placeholder="请输入" type="number" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dictItemStatus = false">取 消</el-button>
        <el-button type="primary" @click="submitClick('dictItem')">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  pageDistType,
  saveDistType,
  delDistType,
  listDistItem
  // saveDistItem,
  // delDistItem
} from '@/api/system/dist'
import Pagination from '../../components/Pagination'
export default {
  components: { Pagination },
  data() {
    return {
      // 数据列表加载动画
      listLoading: true,
      // 数据列表加载动画
      listItemLoading: true,
      // 查询参数对象
      searchForm: {
        current: 1,
        size: 10,
        dictName: ''
      },
      total: 0,
      // 字典类型表格数据
      tableData: [],
      // 选中字典类型
      selectDistType: '',
      dictTypeTitle: '新增字典分类',
      // 字典类型新增、编辑对象
      dictTypeForm: {
        id: '',
        dictName: '',
        dictType: ''
      },
      dictTypeStatus: false,
      dictTypeRules: {
        dictName: [
          { required: true, message: '请输入类型名称', trigger: 'blur' }
        ],
        dictType: [
          { required: true, message: '请输入类型编码', trigger: 'blur' }
        ]
      },
      itemTableData: [],
      dictItemTitle: '新增字典项',
      dictItemStatus: false,
      dictItemTableData: [],
      dictItemForm: {},
      dictItemRules: {
        itemCode: [
          { required: true, message: '请输入字典项编码', trigger: 'blur' }
        ],
        itemName: [
          { required: true, message: '请输入字典项名称', trigger: 'blur' }
        ]
      }
    }
  },
  created: function() {
    this.init()
  },
  methods: {
    init() {
      this.listLoading = true
      var paramData = {
        current: this.searchForm.current,
        size: this.searchForm.size,
        dictName: this.searchForm.dictName
      }
      pageDistType(paramData).then(res => {
        this.total = parseInt(res.total)
        this.tableData = res.records
        this.selectDistType = this.tableData[0].dictType
        this.$refs.dictTypeTable.setCurrentRow(this.tableData[0])
        this.listLoading = false
        this.dictItemInit()
      })
    },
    handleSearch() {
      this.currentPage = 1
      this.init()
    },
    handleRefresh() {
      this.dictTypeForm.dictType = ''
      this.selectCategoryCode = ''
      this.currentPage = 1
      this.init()
    },
    // 新增字典分类
    handleAdd() {
      this.dictTypeTitle = '新增字典分类'
      Object.keys(this.dictTypeForm).map(key => {
        this.dictTypeForm[key] = ''
      })
      this.dictTypeStatus = true
    },
    // 查看字典项
    dictItemClick(row) {
      this.selectDistType = row.dictType
      this.dictItemInit()
    },
    // 编辑字典分类
    editDictTypeClick(row) {
      this.dictTypeTitle = '编辑字典分类'
      Object.keys(this.dictTypeForm).map(key => {
        this.dictTypeForm[key] = row[key]
      })
      this.dictTypeStatus = true
    },
    // 删除字典分类
    removeDictTypeClick(row) {
      this.$confirm('此操作将删除选中记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delDistType(row.id).then(res => {
          this.$message({
            type: 'success',
            message: '操作成功!'
          })
          this.init()
        })
      })
    },
    submitClick(val) {
      if (val === 'dictType') {
        this.$refs['dictTypeForm'].validate((valid) => {
          if (valid) {
            saveDistType(this.dictTypeForm).then(res => {
              this.$message({
                type: 'success',
                message: '操作成功'
              })
              this.dictTypeStatus = false
              this.dictTypeForm = {}
              this.init()
            })
          }
        })
      } else {
        this.$refs['dictItemForm'].validate((valid) => {
          if (valid) {
            var sendData = {
              id: this.dictItemForm.id,
              categoryCode: this.dictItemForm.categoryCode,
              itemCode: this.dictItemForm.itemCode,
              itemName: this.dictItemForm.itemName,
              sortNo: this.dictItemForm.sortNo
            }
            this.$formDataPost(this.config.baseUrl + 'bsfDictItem/save', sendData, false).then(res1 => {
              if (res1.success) {
                this.$message({
                  type: 'success',
                  message: '操作成功'
                })
                this.dictItemStatus = false
                this.dictItemForm = {}
                this.dictItemInit(this.selectCategoryCode)
              } else {
                this.$message({
                  type: 'warning',
                  message: '操作失败'
                })
                return false
              }
            })
          }
        })
      }
    },
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage
      this.init()
    },
    dictItemInit() {
      this.listItemLoading = true
      var paramData = {
        dictType: this.selectDistType,
        itemName: this.dictItemForm.itemName
      }
      listDistItem(paramData).then(res => {
        this.itemTableData = res
        this.listItemLoading = false
      })
    },
    handleDictItemSearch() {
      this.dictItemInit(this.selectCategoryCode)
    },
    handleDictItemRefresh() {
      this.dictItemForm.itemName = ''
      this.dictItemInit(this.selectCategoryCode)
    },
    handleDictItemAdd() {
      this.dictItemTitle = '新增字典项'
      this.dictItemForm = {}
      this.dictItemForm.categoryCode = this.selectCategoryCode
      this.dictItemStatus = true
    },
    editItemClick(row) {
      this.dictItemTitle = '编辑字典项'
      delete row.createTime
      delete row.updateTime
      const addForm = {}
      Object.keys(row).map(key => {
        addForm[key] = row[key]
      })
      this.dictItemForm = addForm
      this.dictItemStatus = true
    },
    removeItemClick(row) {
      this.$confirm('此操作将删除选中记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$formDataPost(this.config.baseUrl + 'bsfDictItem/remove/' + row.id, {}, false).then(res => {
          if (res.success) {
            this.$message({
              type: 'success',
              message: '操作成功!'
            })
            this.dictItemStatus = false
            this.dictItemInit(this.selectCategoryCode)
          } else {
            this.$message({
              type: 'warning',
              message: '操作失败'
            })
            return false
          }
        })
      })
    }
  }
}
</script>
<style lang="less">
.dict-category{
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
