<template>
	<section>
    <div v-if="view =='list'">
      <!--工具条-->
      <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true" :model="queryForm">
          <el-form-item>
            <el-input v-model="queryForm.name" placeholder="页面名称"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="queryForm.code" placeholder="页面编码"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" v-on:click="getTableData">查询</el-button>
            <el-button type="primary" @click="handleAdd" v-hasPermission="'page.add'">新增</el-button>
          </el-form-item>
        </el-form>
      </el-col>

      <!--列表-->
      <el-table :data="tableData" stripe highlight-current-row v-loading="listLoading" style="width: 100%;" :empty-text="message">
        <el-table-column prop="name" label="页面名称" width="250" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="code" label="页面编码" width="100">
        </el-table-column>
        <el-table-column prop="page" label="页面路径" width="300">
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="small" @click="handleUpdate(scope.$index, scope.row)" v-hasPermission="'page.update'">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)" v-hasPermission="'page.delete'">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--工具条-->
      <el-col :span="24" class="toolbar">
        <el-pagination layout="total, prev, pager, next" @current-change="handleCurrentChange" :page-size="pageSize" :total="total" prev-text="上一页" next-text="下一页" background style="float:right;">
        </el-pagination>
      </el-col>
    </div>

    <div v-if="view =='add' || view =='update'">
      <el-row>
        <el-col :span="16">
          <el-form :model="pageForm" label-width="100px" :rules="pageFormRules" ref="pageForm">
            <el-form-item label="页面名称" prop="name">
              <el-input v-model="pageForm.name" auto-complete="off" placeholder="请输入页面名称"></el-input>
            </el-form-item>
            <el-form-item label="页面编码" prop="code">
              <el-input v-model="pageForm.code" auto-complete="off" placeholder="请输入页面编码"></el-input>
            </el-form-item>
            <el-form-item label="页面路径" prop="page">
              <el-input v-model="pageForm.page" auto-complete="off" placeholder="请输入页面路径"></el-input>
            </el-form-item>
          </el-form>
          <div class="btns">
            <el-button type="primary" size="small" @click="submitPage">提交</el-button>
            <el-button size="small" @click="back">取消</el-button>
          </div>
        </el-col>
      </el-row>
    </div>

	</section>
</template>

<script>
	export default {
		data() {
			return {
				queryForm: {
          name: '',
          code: ''
        },
        tableData: [],
        total: 0,
        pages: 0,
        page: 1,
        pageSize: 8,
        sort: 'id',
        order: 'desc',
        message: '',
        listLoading: false,
        
        view: 'list',

        pageForm: {},
        pageFormRules: {
          name: [
            { required: true, message: '请输入页面名称', trigger: 'blur' }
          ],
          code: [
            { required: true, message: '请输入页面代码', trigger: 'blur' }
          ],
          page: [
            { required: true, message: '请输入页面路径', trigger: 'blur' }
          ]
        }
			}
		},
		methods: {
			handleCurrentChange(val) {
				this.page = val;
				this.getTableData();
      },
			//获取页面列表
			getTableData() {
				let para = {
          name: this.queryForm.name,
          code: this.queryForm.code,
          page: this.page,
          pageSize: this.pageSize,
          sort: this.sort,
          order: this.order
				};
        this.listLoading = true
				this.$ajax.get('/page/page',para).then((res) => {
          this.listLoading = false
          if(res.success){
            this.page = res.page
            this.pageSize = res.pageSize
            this.total = res.total
            this.pages = res.pages
            this.tableData = res.data
          }else{
            this.tableData = []
            this.page = 0
            this.total = 0
            this.pages = 0
            this.message = res.message
          }
				})
			},
			//删除
			handleDelete: function (index, row) {
				this.$confirm('确认删除'+row.name+'吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					this.$ajax.post('/page/delete/'+row.id).then((res) => {
            this.listLoading = false;
            if(res.success){
              this.$message.success('删除成功')
              this.getTableData()
            }else{
              this.$message.error(res.message)
            }
					})
				}).catch(() => {

				})
			},
			//修改
			handleUpdate: function (index, row) {
        this.$ajax.get('/page/get/'+row.id).then((res)=>{
          if(res.success){
            this.pageForm = res.data
            this.view = 'update'
          }else{
            this.$message.error(res.message)
          }
        })
			},
      back: function(){
        this.view = 'list'
      },
      handleAdd: function(){
        this.pageForm = {}
        this.view = 'add'
      },
      submitPage: function(){
        this.$refs.pageForm.validate((valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
              this.$ajax.post('/page/'+(this.pageForm.id?'update':'add'),this.pageForm).then((res) => {
                if(res.success){
                  this.$message.success('提交成功')
                  this.getTableData()
                  this.view = 'list'
                }else{
                  this.$message.error(res.message)
                }
              })
            })
          }
        })
      }
    },
    created(){
      this.getTableData()
    }
	}

</script>

<style scoped lang="scss">
.btns{
  clear: both;
  width: 100%;
  text-align: center;
}
</style>
