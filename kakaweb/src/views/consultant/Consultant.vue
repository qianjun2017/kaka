<template>
	<section>
    <div v-if="view =='list'">
      <!--工具条-->
      <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true" :model="queryForm">
          <el-form-item>
            <el-input v-model="queryForm.name" placeholder="汽车顾问姓名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="queryForm.phone" placeholder="汽车顾问手机号码"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" v-on:click="getTableData">查询</el-button>
            <el-button type="primary" @click="handleAdd" v-hasPermission="'consultant.add'">新增</el-button>
          </el-form-item>
        </el-form>
      </el-col>

      <!--列表-->
      <el-table :data="tableData" stripe highlight-current-row v-loading="listLoading" style="width: 100%;" :empty-text="message">
        <el-table-column prop="name" label="姓名" width="150" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="imageUrl" label="头像" width="150">
          <template slot-scope="scope">
          <img class="img" v-lazy = "scope.row.imageUrl" width="40" height="40" @click="handlePicture(scope.row.imageUrl)"/>
          </template>
        </el-table-column>
        <el-table-column prop="phone" label="手机号码">
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="small" @click="handleUpdate(scope.$index, scope.row)" v-hasPermission="'consultant.update'">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)" v-hasPermission="'consultant.delete'">删除</el-button>
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
          <el-form :model="consultantForm" label-width="100px" :rules="consultantFormRules" ref="consultantForm">
            <el-form-item label="头像" required>
              <el-upload
                list-type="picture-card"
                :action="action"
                :on-preview="handlePictureCardPreview"
                :file-list="fileList"
                :on-success="handleSuccess"
                :on-remove="handleRemove"
                :data="{type: 'image', size: '480x480'}"
                :limit="1">
              <i class="el-icon-plus"></i>
              </el-upload>
            </el-form-item>
            <el-form-item label="姓名" prop="name">
              <el-input v-model="consultantForm.name" auto-complete="off" placeholder="请输入汽车顾问姓名"></el-input>
            </el-form-item>
            <el-form-item label="手机号码" prop="phone">
              <el-input v-model="consultantForm.phone" auto-complete="off" placeholder="请输入汽车顾问手机号码"></el-input>
            </el-form-item>
          </el-form>
          <div class="btns">
            <el-button type="primary" size="small" @click="submitConsultant">提交</el-button>
            <el-button size="small" @click="back">取消</el-button>
          </div>
        </el-col>
      </el-row>
    </div>

    <el-dialog :visible.sync="dialogVisible">
		  <img width="100%" :src="dialogImageUrl">
		</el-dialog>

	</section>
</template>

<script>
	export default {
		data() {
			return {
				queryForm: {
          name: '',
          phone: ''
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

        dialogVisible: false,
        dialogImageUrl: "",
        
        view: 'list',

        consultantForm: {},
        consultantFormRules: {
          name: [
            { required: true, message: '请输入汽车顾问名称', trigger: 'blur' }
          ],
          imageUrl: [
            { required: true, message: '请选择汽车顾问头像', trigger: 'change' }
          ],
          phone: [
            { required: true, message: '请输入汽车顾问手机号码', trigger: 'blur' }
          ]
        },

        fileList: [],
        imageUrlList: [],
        service: ''
			}
		},
		methods: {
			handleCurrentChange(val) {
				this.page = val;
				this.getTableData();
      },
      handlePicture: function(url){
        this.dialogImageUrl = url
        this.dialogVisible = true
      },
      handlePictureCardPreview: function(file){
        this.dialogImageUrl = file.url;
        this.dialogVisible = true;
      },
			//获取汽车顾问列表
			getTableData() {
				let para = {
          name: this.queryForm.name,
          phone: this.queryForm.phone,
          page: this.page,
          pageSize: this.pageSize,
          sort: this.sort,
          order: this.order
				};
        this.listLoading = true
				this.$ajax.get('/consultant/page',para).then((res) => {
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
					this.$ajax.post('/consultant/delete/'+row.id).then((res) => {
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
        this.imageUrlList = []
        this.$ajax.get('/consultant/get/'+row.id).then((res)=>{
          if(res.success){
            this.consultantForm = res.data
            this.view = 'update'
            this.fileList = []
            this.fileList.push({url: res.data.imageUrl})
          }else{
            this.$message.error(res.message)
          }
        })
			},
      back: function(){
        this.view = 'list'
      },
      handleAdd: function(){
        this.fileList = []
        this.imageUrlList = []
        this.consultantForm = {}
        this.view = 'add'
      },
      handleSuccess: function(response, file, fileList){
        if(response.success){
          this.imageUrlList = []
          fileList.forEach(element => {
            if(element.response === undefined){
              this.imageUrlList.push(element.url)
            }else{
              this.imageUrlList.push(this.service+element.response.data[0])
            }
          })
        }
      },
      handleRemove: function(file, fileList){
        this.imageUrlList = []
        fileList.forEach(element => {
          if(element.response === undefined){
            this.imageUrlList.push(element.url)
          }else{
            this.imageUrlList.push(this.service+element.response.data[0])
          }
        })
      },
      submitConsultant: function(){
        if(this.imageUrlList.length>0){
          this.consultantForm.imageUrl = this.imageUrlList[0]
        }
        this.$refs.consultantForm.validate((valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
              this.$ajax.post('/consultant/'+(this.consultantForm.id?'update':'add'),this.consultantForm).then((res) => {
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
    },
    mounted(){
      this.service = this.$imageService
    },
    computed: {
      action: function(){
        return this.service + "/file/up"
      }
    }
	}

</script>

<style scoped lang="scss">
.btns{
  clear: both;
  width: 100%;
  text-align: center;
}
.img{
  cursor: pointer;
}
</style>
