<template>
	<section>
    <div v-if="view =='list'">
      <!--工具条-->
      <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true" :model="queryForm">
          <el-row>
            <el-form-item>
              <el-input v-model="queryForm.consultantName" placeholder="汽车顾问姓名"></el-input>
            </el-form-item>
            <el-form-item>
              <el-date-picker
                v-model="queryForm.bespeakTimeStart"
                type="datetime"
                placeholder="选择看车开始时间"
                value-format="yyyy-MM-dd HH:mm:ss"
                default-time="00:00:00">
              </el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-date-picker
                v-model="queryForm.bespeakTimeEnd"
                type="datetime"
                placeholder="选择看车结束时间"
                value-format="yyyy-MM-dd HH:mm:ss"
                default-time="23:59:59">
              </el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" v-on:click="getTableData">查询</el-button>
              <el-button type="text" v-on:click="searchMore">高级搜索<i :class="icon"></i></el-button>
            </el-form-item>
          </el-row>
          <el-row v-if="more">
            <el-form-item>
              <el-input v-model="queryForm.franchiserName" placeholder="经销商名称"></el-input>
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
          </el-row>
        </el-form>
      </el-col>

      <!--列表-->
      <el-table :data="tableData" stripe highlight-current-row v-loading="listLoading" @sort-change="sortChanged" style="width: 100%;" :default-sort = "{prop: 'bespeakTime', order: 'descending'}" :empty-text="message">
        <el-table-column prop="customerName" label="会员姓名" width="150" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="customerPhone" label="手机号码" width="120" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="consultantName" label="顾问姓名" width="150" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="franchiserName" label="经销商名称" width="150" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="locationName" label="看车地区">
          <template slot-scope="scope">{{scope.row.locationName.replace(new RegExp("/","gm"),"")}}</template>
        </el-table-column>
        <el-table-column prop="bespeakTime" label="看车时间" width="180" sortable='custom'>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="small" @click="handleDetail(scope.$index, scope.row)" v-hasPermission="'bespeak.detail'">详情</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--工具条-->
      <el-col :span="24" class="toolbar">
        <el-pagination layout="total, prev, pager, next" @current-change="handleCurrentChange" :page-size="pageSize" :total="total" prev-text="上一页" next-text="下一页" background style="float:right;">
        </el-pagination>
      </el-col>
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
          consultantName: '',
          franchiserName: '',
          locationId: '',
          bespeakTimeStart: '',
          bespeakTimeEnd: ''
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

        bespeak: {},

        more: false,
        icon: 'el-icon-arrow-down'
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
      searchMore: function(){
        this.more = !this.more
        if(this.more){
          this.icon = 'el-icon-arrow-up'
        }else{
          this.icon = 'el-icon-arrow-down'
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
			//获取预约看车列表
			getTableData() {
				let para = {
          consultantName: this.queryForm.consultantName,
          franchiserName: this.queryForm.franchiserName,
          locationId: this.queryForm.locationId,
          page: this.page,
          pageSize: this.pageSize,
          sort: this.sort,
          order: this.order
				};
        this.listLoading = true
				this.$ajax.get('/bespeak/page',para).then((res) => {
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
      sortChanged: function(column){
        this.sort = column.prop
        this.order = (column.order === 'ascending')?'asc':'desc'
        this.getTableData()
      },
      //详情
      handleDetail: function(index, row){
        this.$ajax.get('/bespeak/get/'+row.id).then((res)=>{
          if(res.success){
            this.bespeak = res.data
            this.view = 'detail'
          }else{
            this.$message.error(res.message)
          }
        })
      },
      back: function(){
        this.view = 'list'
      }
    },
    created(){
      this.getLocationData()
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
