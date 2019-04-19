<template>
	<section>
    <div v-if="view =='list'">
      <!--工具条-->
      <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true" :model="queryForm">
          <el-row>
            <el-form-item>
              <el-input v-model="queryForm.name" placeholder="会员名称"></el-input>
            </el-form-item>
            <el-form-item>
              <el-input v-model="queryForm.phone" placeholder="会员电话"></el-input>
            </el-form-item>
            <el-form-item>
              <el-select v-model="queryForm.cardLevel" placeholder="请选择会员等级" clearable>
                <el-option
                  v-for="level in levelData"
                  :key="level.level"
                  :label="level.name"
                  :value="level.level">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" v-on:click="getTableData">查询</el-button>
              <el-button type="text" v-on:click="searchMore">高级搜索<i :class="icon"></i></el-button>
            </el-form-item>
          </el-row>
          <el-row v-if="more">
            <el-form-item>
              <el-input v-model="queryForm.cardNo" placeholder="会员卡号"></el-input>
            </el-form-item>
          </el-row>
        </el-form>
      </el-col>

      <!--列表-->
      <el-table :data="tableData" stripe highlight-current-row v-loading="listLoading" @sort-change="sortChanged" style="width: 100%;" :default-sort = "{prop: 'createTime', order: 'descending'}" :empty-text="message">
        <el-table-column prop="name" label="会员名称" width="150" show-overflow-tooltip>
          <template slot-scope="scope">
            <div class="user">
              <img :src = "scope.row.avatarUrl" width="40" height="40"/>
              <span style="margin-left: 10px;">{{ scope.row.name }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="phone" label="电话" width="110">
        </el-table-column>
        <el-table-column prop="cardNo" label="会员卡号" width="150" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="cardLevel" label="会员等级" width="80">
        </el-table-column>
        <el-table-column prop="points" label="积分" width="60">
        </el-table-column>
        <el-table-column prop="createTime" label="注册日期" width="180" sortable='custom'>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="small" @click="handleDetail(scope.$index, scope.row)" v-hasPermission="'customer.detail'">详情</el-button>
            <el-button size="small" @click="handlePointsHistory(scope.$index, scope.row)" v-hasPermission="'customer.points'">变更历史</el-button>
            <el-button type="warning" size="small" @click="handlePoints(scope.$index, scope.row)" v-hasPermission="'customer.points'">调减积分</el-button>
            <el-button type="warning" size="small" @click="handleLock(scope.$index, scope.row)" v-if="scope.row.status === 'normal'" v-hasPermission="'customer.lock'">锁定</el-button>
            <el-button type="warning" size="small" @click="handleUnlock(scope.$index, scope.row)" v-if="scope.row.status === 'locked'" v-hasPermission="'customer.unlock'">解锁</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--工具条-->
      <el-col :span="24" class="toolbar">
        <el-pagination layout="total, prev, pager, next" @current-change="handleCurrentChange" :current-page="page" :page-size="pageSize" :total="total" prev-text="上一页" next-text="下一页" background style="float:right;">
        </el-pagination>
      </el-col>
    </div>

    <div v-if="view =='detail'">
      <div>
        <table>
          <tr>
            <td>
              <div class="user_avatar">
                <img v-lazy="customer.avatarUrl" class="img_avatar">
              </div>
            </td>
            <td>
              <div>
                <span class="display_name">{{customer.nickName}}</span>
                <span :title="statuss[customer.status]" class="tips"><i :class="status"></i></span>
              </div>
              <div>
                <ul class="user_profile">
                  <li>
                    <span class="text_gray">粉龄：<span :title="createTime" class="tips">{{time}}</span></span>
                  </li>
                  <li>
                    <span class="text_gray">手机号码：{{customer.phone}}</span>
                  </li>
                  <li>
                    <span class="text_gray">会员卡：{{customer.cardNo}}</span>
                  </li>
                  <li>
                    <span class="text_gray">积分：{{customer.points}}</span>
                  </li>
                  <li>
                    <span class="text_gray">级别：{{customer.cardLevel}}</span>
                  </li>
                </ul>
              </div>
            </td>
          </tr>
        </table>
      </div>
      <div class="btns">
        <el-button size="small" @click="back">返回</el-button>
      </div>
    </div>

    <div v-show="view =='history'">
      <el-table :data="pointsTableData" stripe highlight-current-row style="width: 100%;" :empty-text="pointsMessage">
        <el-table-column prop="points" label="变更积分数">
        </el-table-column>
        <el-table-column prop="createTime" label="变更时间">
        </el-table-column>
        <el-table-column prop="remark" label="变更原因">
        </el-table-column>
      </el-table>

      <!--工具条-->
      <el-col :span="24" class="toolbar">
        <el-pagination layout="total, prev, pager, next" @current-change="handleCurrentPointsChange" :page-size="pointsPageSize" :total="pointsTotal" prev-text="上一页" next-text="下一页" background style="float:right;">
        </el-pagination>
      </el-col>
      <div class="btns">
        <el-button size="small" @click="back">返回</el-button>
      </div>
    </div>

    <!--调减积分界面-->
		<el-dialog title="调减积分" :visible.sync="pointsFormVisible" :close-on-click-modal="false">
			<el-form :model="pointsForm" label-width="100px" :rules="pointsFormRules" ref="pointsForm">
				<el-form-item label="调整积分数" prop="points">
					<el-input v-model="pointsForm.points" auto-complete="off" placeholder="请输入调整积分数"></el-input>
				</el-form-item>
        <el-form-item label="原因" prop="remark">
					<el-input type="textarea" :rows="4" v-model="pointsForm.remark" auto-complete="off" placeholder="请输入调整积分原因"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="pointsFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="submitPoints" :loading="pointsLoading">提交</el-button>
			</div>
		</el-dialog>

	</section>
</template>

<script>
	export default {
		data() {
			return {
				queryForm: {
          name: '',
          phone: '',
          cardLevel: '',
          cardNo: ''
        },
        tableData: [],
        statuss: {'normal': '正常', 'locked': '锁定'},
        total: 0,
        pages: 0,
        page: 1,
        pageSize: 8,
        sort: 'createTime',
        order: 'desc',
        message: '',
        listLoading: false,
        
        view: 'list',

        more: false,
        icon: 'el-icon-arrow-down',

        customer: {},

        levelData: [],

        pointsTableData: [],
        pointsTotal: 0,
        pointsPages: 0,
        pointsPage: 1,
        pointsPageSize: 8,
        pointsMessage: '',

        pointsFormVisible: false,
        pointsLoading: false,
        pointsForm: {
					points: ''
        },
				pointsFormRules: {
					points: [
						{ required: true, message: '请输入调整积分数', trigger: 'blur' }
          ],
          remark: [
						{ required: true, message: '请输入调整积分原因', trigger: 'blur' }
          ]
				}
			}
		},
		methods: {
			handleCurrentChange(val) {
				this.page = val;
				this.getTableData();
			},
      searchMore: function(){
        this.more = !this.more
        if(this.more){
          this.icon = 'el-icon-arrow-up'
        }else{
          this.icon = 'el-icon-arrow-down'
        }
      },
			//获取会员列表
			getTableData() {
				let para = {
          name: this.queryForm.name,
          phone: this.queryForm.phone,
          cardLevel: this.queryForm.cardLevel,
          cardNo: this.queryForm.cardNo,
          page: this.page,
          pageSize: this.pageSize,
          sort: this.sort,
          order: this.order
				};
        this.listLoading = true
				this.$ajax.get('/customer/page',para).then((res) => {
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
			//锁定
			handleLock: function (index, row) {
				this.$confirm('确认锁定会员【'+row.nickName+'】吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					this.$ajax.post('/customer/lock/'+row.id).then((res) => {
            this.listLoading = false;
            if(res.success){
              this.$message({
                message: '已锁定',
                type: 'success'
              })
              this.getTableData()
            }
					})
				}).catch(() => {

				})
			},
			//解锁
			handleUnlock: function (index, row) {
				this.$confirm('确认解锁会员【'+row.nickName+'】吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true
					this.$ajax.post('/customer/unlock/'+row.id).then((res) => {
            this.listLoading = false
            if(res.success){
              this.$message({
                message: '解锁成功',
                type: 'success'
              })
              this.getTableData()
            }
					})
				}).catch(() => {

				})
			},
      //详情
      handleDetail: function(index, row){
        this.$ajax.get('/customer/get/'+row.id).then((res)=>{
          if(res.success){
            this.customer = res.data
            this.view = 'detail'
          }else{
            this.$message.error(res.message)
          }
        })
      },
      sortChanged: function(column){
        this.sort = column.prop
        this.order = (column.order === 'ascending')?'asc':'desc'
        this.getTableData()
      },
      back: function(){
        this.view = 'list'
      },
      getLevelData: function(){
        this.$ajax.get('/card/levels').then((res)=>{
          if(res.success){
            this.levelData = res.data
          }else{
            this.levelData = []
          }
        })
      },
      getPointsTableData: function(){
        let para = {
            customerId: this.customer.id,
            page: this.pointsPage,
            pageSize: this.pointsPageSize,
            sort: 'createTime',
            order: 'desc'
        }
        this.$ajax.get('/customer/points/page',para).then((res) => {
            if(res.success){
                this.pointsPage = res.page;
                this.pointsPageSize = res.pageSize;
                this.pointsTotal = res.total;
                this.pointsPages = res.pages;
                this.pointsTableData = res.data;
            }else{
                this.pointsTableData = [];
                this.pointsPage = 0;
                this.pointsTotal = 0;
                this.pointsPages = 0;
                this.pointsMessage = res.message;
            }
        })
      },
      handlePointsHistory: function(index, row){
          this.pointsPage = 1
          this.customer.id = row.id
          this.getPointsTableData()
          this.view = 'history'
      },
      handlePoints: function(index, row){
          this.pointsForm.customerId = row.id
          this.pointsFormVisible = true
      },
      submitPoints: function(){
        this.$refs.pointsForm.validate((valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
              this.pointsLoading = true
              this.$ajax.post('/customer/points', this.pointsForm).then((res) => {
                this.pointsLoading = false
                if(res.success){
                  this.$message.success('提交成功')
                  this.pointsFormVisible = false
                  this.getTableData()
                }else{
                  this.$message.error(res.message)
                }
              })
            })
          }
        })
      },
      handleCurrentPointsChange(val) {
          this.pointsPage = val
          this.getPointsTableData()
      }
    },
    created(){
      this.getLevelData()
    },
    computed: {
      time: function(){
        var day = 24 * 60 * 60 *1000
        if(this.customer.createTime){
          var createTime = new Date()
          var dateArr = this.customer.createTime.split(" ")[0].split("-")
          createTime.setFullYear(dateArr[0], dateArr[1]-1, dateArr[2])
          var time = (new Date().getTime() - createTime.getTime())/day
          return time>=365?(Math.floor(time/365)+'年'):time>=30?(Math.floor(time/30)+'个月'):(Math.ceil(time)+'天')
        }
      },
      createTime: function(){
        return "注册时间："+this.customer.createTime
      },
      status: function(){
        if(this.customer.status==='normal'){
          return 'el-icon-success'
        }else{
          return 'el-icon-warning'
        }
      }
    }
	}

</script>

<style scoped lang="scss">
td{
  vertical-align: middle
}
li{
  list-style: none;
  padding-top: 10px;
}
tr, td, ul{
  margin: 0px;
  padding: 0px;
}
.tips{
  cursor: pointer;
}
td{
  vertical-align: top;
  .user_avatar{
    padding-right: 15px;
    .img_avatar{
      width: 180px;
      height: 180px;
      border: 1px solid #ccc;
      padding: 2px;
    }
  }
  .display_name{
    font-size: 20px;
    font-weight: bold;
    color: #454545;
  }
  .user_profile{
    margin-top: 10px;
    line-height: 0.8em;
    color: #333
  }
  .text_gray{
    color: Gray;
  }
}
.btns{
  clear: both;
  width: 100%;
  text-align: center;
}
.user{
  display: flex;
  align-items: center;
  img{
    flex-shrink: 0;
  }
}
</style>
