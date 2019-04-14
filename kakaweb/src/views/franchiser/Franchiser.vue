<template>
	<section>
    <div v-if="view =='list'">
      <!--工具条-->
      <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true" :model="queryForm">
          <el-form-item>
            <el-input v-model="queryForm.name" placeholder="经销商名称"></el-input>
          </el-form-item>
          <el-form-item>
            <el-cascader placeholder="请选择地区"
              :options="locations"
              :props="defaultProps"
              change-on-select
              @change="handleChange"
              v-model="area"
              filterable
              clearable
              style="display: block"
            ></el-cascader>
          </el-form-item>
          <el-form-item>
            <el-input v-model="queryForm.address" placeholder="经销商地址"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" v-on:click="getTableData">查询</el-button>
            <el-button type="primary" @click="handleAdd" v-hasPermission="'franchiser.add'">新增</el-button>
          </el-form-item>
        </el-form>
      </el-col>

      <!--列表-->
      <el-table :data="tableData" stripe highlight-current-row v-loading="listLoading" style="width: 100%;" :empty-text="message">
        <el-table-column prop="name" label="经销商名称" width="250" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="longitude" label="经销商地址">
          <template slot-scope="scope">{{scope.row.locationName.replace(new RegExp("/","gm"),"")}}{{scope.row.address}}</template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="small" @click="handleDetail(scope.$index, scope.row)" v-hasPermission="'franchiser.detail'">详情</el-button>
            <el-button size="small" type="warning" @click="handleUpdate(scope.$index, scope.row)" v-hasPermission="'franchiser.update'">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)" v-hasPermission="'franchiser.delete'">删除</el-button>
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
          <el-form :model="franchiserForm" label-width="100px" :rules="franchiserFormRules" ref="franchiserForm">
            <el-form-item label="经销商名称" prop="name">
              <el-input v-model="franchiserForm.name" auto-complete="off" placeholder="请输入经销商名称"></el-input>
            </el-form-item>
            <el-form-item label="经销商地区" prop="locationId">
              <el-cascader placeholder="请选择地区"
                :options="locations"
                :props="defaultProps"
                change-on-select
                @change="handleLocationChange"
                v-model="location"
                filterable
                clearable
                style="display: block"
              ></el-cascader>
            </el-form-item>
            <el-form-item label="经销商地址" prop="address">
              <el-input v-model="franchiserForm.address" auto-complete="off" placeholder="请输入经销商地址"></el-input>
            </el-form-item>
          </el-form>
          <div class="btns">
            <el-button type="primary" size="small" @click="submitfranchiser">提交</el-button>
            <el-button size="small" @click="back">取消</el-button>
          </div>
        </el-col>
      </el-row>
    </div>

    <div v-if="view =='detail'">
      <div class="btns">
        <el-button size="small" @click="back">返回</el-button>
      </div>
    </div>

	</section>
</template>

<script>
	export default {
		data() {
			return {
				queryForm: {
          name: '',
          address: '',
          locationId: ''
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

        locations: [],
        defaultProps: {value: 'id', label: 'locationName', children: 'subLocationList'},
        area: [],
        
        view: 'list',

        franchiser: {},

        franchiserForm: {},
        franchiserFormRules: {
          name: [
            { required: true, message: '请输入经销商名称', trigger: 'blur' }
          ],
          locationId: [
            { required: true, message: '请选择经销商地区', trigger: 'change' }
          ],
          address: [
            { required: true, message: '请输入经销商地址', trigger: 'blur' }
          ]
        },
        location: []
			}
		},
		methods: {
			handleCurrentChange(val) {
				this.page = val;
				this.getTableData();
      },
      handleChange(value){
        if(value.length>0){
          this.queryForm.locationId = value[value.length-1]
        }else{
          this.queryForm.locationId = ''
        }
      },
      handleLocationChange(value){
        if(value.length>0){
          this.franchiserForm.locationId = value[value.length-1]
        }else{
          this.franchiserForm.locationId = ''
        }
      },
      getLocationData() {
        this.$ajax.get('/system/location/tree').then((res)=>{
          if(res.success){
            res.root.subLocationList.forEach(element => {
              this.locations.push(element)
            });
          }
        })
      },
			//获取经销商列表
			getTableData() {
				let para = {
          name: this.queryForm.name,
          address: this.queryForm.address,
          locationId: this.queryForm.locationId,
          page: this.page,
          pageSize: this.pageSize,
          sort: this.sort,
          order: this.order
				};
        this.listLoading = true
				this.$ajax.get('/franchiser/page',para).then((res) => {
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
					this.$ajax.post('/franchiser/delete/'+row.id).then((res) => {
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
        this.$ajax.get('/franchiser/get/'+row.id).then((res)=>{
          if(res.success){
            this.franchiserForm = res.data
            if(res.data.locationArray!=null){
              this.location = this.franchiserForm.locationArray
            }else{
              this.location = []
            }
            this.view = 'update'
          }else{
            this.$message.error(res.message)
          }
        })
			},
      //详情
      handleDetail: function(index, row){
        this.$ajax.get('/franchiser/get/'+row.id).then((res)=>{
          if(res.success){
            this.franchiser = res.data
            this.view = 'detail'
          }else{
            this.$message.error(res.message)
          }
        })
      },
      back: function(){
        this.view = 'list'
      },
      handleAdd: function(){
        this.franchiserForm = {}
        this.view = 'add'
      },
      submitfranchiser: function(){
        this.$refs.franchiserForm.validate((valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
              this.$ajax.post('/franchiser/'+(this.franchiserForm.id?'update':'add'),this.franchiserForm).then((res) => {
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
      this.getLocationData()
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
.lbs{
  display: block;
  color: #606266;
  line-height: 1;
  text-decoration: none;
  font-size: 12px;
  font-family: Arial;
  white-space: nowrap;
}
</style>
