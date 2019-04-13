<template>
	<section>
    <div v-if="view =='list'">
      <!--工具条-->
      <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true" :model="queryForm">
          <el-form-item>
            <el-input v-model="queryForm.name" placeholder="门店名称"></el-input>
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
            <el-input v-model="queryForm.address" placeholder="门店地址"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" v-on:click="getTableData">查询</el-button>
            <el-button type="primary" @click="handleAdd" v-hasPermission="'shop.add'">新增</el-button>
          </el-form-item>
        </el-form>
      </el-col>

      <!--列表-->
      <el-table :data="tableData" stripe highlight-current-row v-loading="listLoading" style="width: 100%;" :empty-text="message">
        <el-table-column prop="name" label="门店名称" width="250" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="longitude" label="经纬度" width="200">
          <template slot-scope="scope">{{scope.row.latitude}},{{scope.row.longitude}}</template>
        </el-table-column>
        <el-table-column prop="radius" label="误差半径(米)" width="150">
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="small" @click="handleDetail(scope.$index, scope.row)" v-hasPermission="'shop.detail'">详情</el-button>
            <el-button size="small" type="primary" @click="handleAcode(scope.$index, scope.row)" v-hasPermission="'shop.acode'">积分码</el-button>
            <el-button size="small" type="warning" @click="handleUpdate(scope.$index, scope.row)" v-hasPermission="'shop.update'">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)" v-hasPermission="'shop.delete'">删除</el-button>
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
          <el-form :model="shopForm" label-width="100px" :rules="shopFormRules" ref="shopForm">
            <el-form-item label="门店名称" prop="name">
              <el-input v-model="shopForm.name" auto-complete="off" placeholder="请输入门店名称"></el-input>
            </el-form-item>
            <el-form-item label="门店代码" prop="code">
              <el-input v-model="shopForm.code" auto-complete="off" placeholder="请输入门店代码"></el-input>
            </el-form-item>
            <el-form-item label="门店地区" prop="locationId">
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
            <el-form-item label="门店地址" prop="address">
              <el-input v-model="shopForm.address" auto-complete="off" placeholder="请输入门店地址"></el-input>
            </el-form-item>
            <el-form-item label="经纬度" prop="ll" placeholder="纬度,经度">
              <el-input v-model="shopForm.ll" auto-complete="off">
                <template slot="append">
                  <a :href="lbs" target="_blank" class="lbs">腾讯坐标拾取器</a>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item label="误差半径" prop="radius">
              <el-input v-model="shopForm.radius" auto-complete="off" placeholder="请输入门店经纬度误差半径">
                <template slot="append">米</template>
              </el-input>
            </el-form-item>
          </el-form>
          <div class="btns">
            <el-button type="primary" size="small" @click="submitShop">提交</el-button>
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
          address: '',
          locationId: ''
        },
        tableData: [],
        total: 0,
        pages: 0,
        page: 1,
        pageSize: 8,
        sort: 's.id',
        order: 'desc',
        message: '',
        listLoading: false,

        locations: [],
        defaultProps: {value: 'id', label: 'locationName', children: 'subLocationList'},
        area: [],
        
        view: 'list',

        shop: {},

        shopForm: {},
        shopFormRules: {
          name: [
            { required: true, message: '请输入门店名称', trigger: 'blur' }
          ],
          code: [
            { required: true, message: '请输入门店代码', trigger: 'blur' }
          ],
          locationId: [
            { required: true, message: '请选择门店地区', trigger: 'change' }
          ],
          address: [
            { required: true, message: '请输入门店地址', trigger: 'blur' }
          ],
          ll: [
            { required: true, message: '请输入门店经纬度', trigger: 'blur' }
          ],
          radius: [
            { required: true, message: '请输入门店经纬度误差半径', trigger: 'blur' }
          ]
        },
        location: [],
        lbs: 'https://lbs.qq.com/tool/getpoint/index.html'
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
          this.shopForm.locationId = value[value.length-1]
        }else{
          this.shopForm.locationId = ''
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
			//获取门店列表
			getTableData() {
				let para = {
          name: this.queryForm.name,
          address: this.queryForm.address,
          page: this.page,
          pageSize: this.pageSize,
          sort: this.sort,
          order: this.order
				};
        this.listLoading = true
				this.$ajax.get('/shop/page',para).then((res) => {
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
				});
			},
			//删除
			handleDelete: function (index, row) {
				this.$confirm('确认删除'+row.name+'吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					this.$ajax.post('/shop/delete/'+row.id).then((res) => {
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
        this.$ajax.get('/shop/get/'+row.id).then((res)=>{
          if(res.success){
            this.shopForm = res.data
            this.shopForm.ll = this.shopForm.latitude+','+this.shopForm.longitude
            this.view = 'update'
          }else{
            this.$message.error(res.message)
          }
        })
			},
      //详情
      handleDetail: function(index, row){
        this.$ajax.get('/shop/get/'+row.id).then((res)=>{
          if(res.success){
            this.shop = res.data
            this.view = 'detail'
          }else{
            this.$message.error(res.message)
          }
        })
      },
      handleAcode: function(index, row){

      },
      back: function(){
        this.view = 'list'
      },
      handleAdd: function(){
        this.shopForm = {}
        this.view = 'add'
      },
      submitShop: function(){
        this.$refs.shopForm.validate((valid) => {
          if (valid) {
            let split = this.shopForm.ll.split(',')
            if(split.length!=2){
              this.$message.error('经纬度格式错误')
              return
            }
            this.shopForm.latitude = split[0]
            this.shopForm.longitude = split[1]
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
              this.$ajax.post('/shop/'+(this.shopForm.id?'update':'add'),this.shopForm).then((res) => {
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
