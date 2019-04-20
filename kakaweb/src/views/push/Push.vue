<template>
    <section>
        <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="消息推送" name="push">
                <div class="push-box">
                    <div class="tmplmsg-preview">
                        <div class="tmplmsg-card">
                            <div class="tmplmsg-card-hd">
                                <div class="hd-cell">
                                    <div class="app-avatar">
                                        <img :src="appIcon"/>
                                    </div>
                                    <div>{{appName}}</div>
                                </div>
                            </div>
                            <div class="tmplmsg-card-bd">
                                <div class="tmplmsg-preview-title">{{template.title}}</div>
                                <div class="tmplmsg-preview-desc">{{year}}年{{month}}月</div>
                                <div class="tmplmsg-preview-meta-list" v-if="template.keywordList && template.keywordList.length>0">
                                    <template v-for="(keyword, index) in template.keywordList">
                                        <div class="tmplmsg-preview-meta-item" :key="index">
                                            <label>{{keyword.name}}</label>
                                            <p>{{pushForm.keywords[keyword.keyword]}}</p>
                                        </div>
                                    </template>
                                </div>
                            </div>
                            <div class="tmplmsg-card-ft">
                                <div class="ft-cell">
                                    <span>查看详情</span>
                                    <i class="el-icon-arrow-right"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="push-form">
                        <el-form :model="pushForm" label-width="100px" :rules="pushFormRules" ref="pushForm">
                            <el-form-item label="推送类型" prop="type">
                                <el-select v-model="pushForm.type" placeholder="请选择推送类型" clearable @change="changePushType">
                                    <el-option
                                        v-for="(value,key) in types"
                                        :key="key"
                                        :label="value"
                                        :value="key">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="选择接收人" v-if="pushForm.type=='assign'">
                                <el-select v-model="pushForm.userList" placeholder="请选择接收人" multiple collapse-tags clearable>
                                    <el-option
                                        v-for="user in userFormList"
                                        :key="user.userId"
                                        :label="user.userName"
                                        :value="user.userId">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="发送模板" prop="templateId">
                                <el-select v-model="pushForm.templateId" placeholder="请选择发送模板" clearable @change="changeTemplate">
                                    <el-option
                                        v-for="template in templateData"
                                        :key="template.id"
                                        :label="template.title"
                                        :value="template.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                            <template v-for="keyword in template.keywordList" v-if="template.keywordList && template.keywordList.length>0">
                                <el-form-item :key="keyword.keyword" :label="keyword.name">
                                    <el-input type="textarea" v-model="pushForm.keywords[keyword.keyword]" :placeholder="keyword.example"></el-input>
                                </el-form-item>
                            </template>
                            <el-form-item label="放大关键字" v-if="template.keywordList && template.keywordList.length>0">
                                <el-select v-model="pushForm.emphasisKeyword" placeholder="请选择放大关键字" clearable>
                                    <el-option
                                        v-for="keyword in template.keywordList"
                                        :key="keyword.keydata"
                                        :label="keyword.name"
                                        :value="keyword.keydata">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="详情页面" v-if="pageData.length>0">
                                <el-select v-model="pushForm.pageId" placeholder="请选择跳转详情页" clearable>
                                    <el-option
                                        v-for="page in pageData"
                                        :key="page.id"
                                        :label="page.name"
                                        :value="page.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="详情参数" v-if="pushForm.pageId">
                                <el-input v-model="pushForm.param" placeholder="请输入详情页参数"></el-input>
                            </el-form-item>
                        </el-form>
                    </div>
                </div>
                <div class="btns" v-if="templateData.length>0">
                    <el-button type="primary" size="small" @click="handleSubmit" :loading="submitLoading">提交</el-button>
                </div>
            </el-tab-pane>
            <el-tab-pane label="推送历史" name="history">
                <div v-show="view=='list'">
                    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                        <el-form :inline="true" :model="queryForm">
                            <el-form-item>
                                <el-input v-model="queryForm.title" placeholder="请输入模板标题"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" v-on:click="getTableHistoryData">查询</el-button>
                            </el-form-item>
                        </el-form>
                    </el-col>
                    <el-table :data="tableHistoryData" stripe highlight-current-row v-loading="listLoading" style="width: 100%;" :empty-text="message">
                        <el-table-column prop="title" label="推送模板">
                        </el-table-column>
                        <el-table-column prop="createTime" label="推送时间" width="180" sortable='custom'>
                        </el-table-column>
                        <el-table-column label="推送状态">
                            <template slot-scope="scope">
                                <span>{{ statuss[scope.row.status] }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="num" label="推送数量">
                        </el-table-column>
                        <el-table-column label="操作" width="150">
                            <template slot-scope="scope">
                                <el-button size="small" @click="handleDetail(scope.$index, scope.row)" v-if="scope.row.num>0">推送人员</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-col :span="24" class="toolbar">
                        <el-pagination layout="total, prev, pager, next" @current-change="handleCurrentChange" :page-size="pageSize" :total="total" :current-page="page" prev-text="上一页" next-text="下一页" background style="float:right;">
                        </el-pagination>
                    </el-col>
                </div>
                <div v-show="view=='user'">
                    <el-table :data="pushUserTableData" height="600" stripe highlight-current-row style="width: 100%;" :empty-text="pushUserMessage">
                        <el-table-column prop="name" label="人员姓名">
                        </el-table-column>
                        <el-table-column label="推送状态">
                            <template slot-scope="scope">
                                <span>{{ scope.row.success?'成功':(scope.row.success==false?'失败':'等待推送') }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="message" label="失败原因" width="500" show-overflow-tooltip>
                        </el-table-column>
                    </el-table>
                    <div class="btns">
                        <el-button size="small" @click="back">返回</el-button>
                    </div>
                </div>
            </el-tab-pane>
        </el-tabs>
    </section>
</template>

<script>
export default {
    data(){
        return {
            template: {},
            appName: '',
            appIcon: '',

            templateData: [],

            pushForm: {
                type: 'all',
                keywords: {},
                userList: []
            },
            pushFormRules: {
                type: [
                    { required: true, message: '请选择推送类型', trigger: 'change' }
                ],
                templateId: [
                    { required: true, message: '请选择发送模板', trigger: 'change' }
                ]
            },
            pageData: [],
            types: {'all': '所有用户', 'assign': '指定用户'},
            statuss: {'undo': '未推送', 'doing': '推送中', 'done': '已推送'},
            userFormList: [],

            submitLoading: false,

            activeName: 'push',

            queryForm: {
                title: ''
            },
            tableHistoryData: [],
            total: 0,
            pages: 0,
            page: 1,
            pageSize: 8,
            message: '',
            listLoading: false,

            pushUserTableData: [],
            pushUserMessage: '',

            view: 'list'
        }
    },
    methods: {
        handleClick: function(tab, event){
            if(this.activeName=='history' && this.tableHistoryData.length==0){
                this.getTableHistoryData()
            }
        },
        getTableHistoryData: function(){
            this.listLoading = true
            this.$ajax.get('/push/history/page',{pageSize: this.pageSize, page: this.page, title: this.queryForm.title}).then((res) => {
                this.listLoading = false
                if(res.success){
                    this.tableHistoryData = res.data
                    this.page = res.page
                    this.pageSize = res.pageSize
                    this.total = res.total
                    this.pages = res.pages
                }else{
                    this.tableHistoryData = []
                    this.page = 0
                    this.total = 0
                    this.pages = 0
                    this.message = res.message
                }
            })
        },
        handleCurrentChange(val) {
            this.page = val
            this.getTableHistoryData()
        },
        handleDetail: function(index, row){
            this.$ajax.get('/push/history/get/'+row.id).then((res)=>{
                if(res.success){
                    this.pushUserTableData = res.data
                    this.view = 'user'
                }else{
                    this.pushUserTableData = []
                    this.pushUserMessage = res.message
                }
            })
        },
        getTemplateData: function(){
            this.$ajax.get('/template/page',{pageSize: 20}).then((res) => {
                if(res.success){
                    this.templateData = res.data
                }else{
                    this.templateData = []
                }
            })
        },
        getPageData: function(){
            this.$ajax.get('/page/all').then((res) => {
                if(res.success){
                    this.pageData = res.data
                }else{
                    this.pageData = []
                }
            })
        },
        changeTemplate: function(templateId){
            this.pushForm.keywords = {}
            if(templateId===undefined || templateId===''){
                this.template = []
                return
            }
            this.$ajax.get('/template/get/'+templateId).then(res=>{
                if(res.success){
                    this.template = res.data
                }else{
                    this.$message.error(res.message)
                }
            })
        },
        changePushType: function(type){
            this.pushForm.userList = []
            this.userFormList = []
            if(type=='assign'){
                this.$ajax.get('/push/customer').then(res=>{
                    if(res.success){
                        this.userFormList = res.data
                    }else{
                        this.$message.error(res.message)
                    }
                })
            }
        },
        handleSubmit: function(){
            this.$confirm('确认推送吗?', '提示', {
                type: 'warning'
            }).then(()=>{
                this.submitLoading = true
                this.$ajax.post('/push/'+this.pushForm.type, this.pushForm).then((res) => {
                    this.submitLoading = false
                    if(res.success){
                        this.$message.success('提交成功')
                        this.view = 'list'
                        this.activeName = 'history'
                        this.getTableHistoryData()
                    }else{
                        this.$message.error(res.message)
                    }
                })
            })
        },
        back: function(){
            this.view = 'list'
        }
    },
    created(){
        this.$ajax.get('/system/config/name', {propertyName: 'wx.name'}).then((res)=>{
            if(res.success){
                this.appName = res.data
            }
        })
        this.$ajax.get('/system/config/name', {propertyName: 'wx.icon'}).then((res)=>{
            if(res.success){
                this.appIcon = res.data
            }else{
                this.appIcon = '/static/app.jpg'
            }
        }),
        this.getTemplateData()
        this.getPageData()
    },
    computed: {
        year: function(){
            let date = new Date()
            return date.getFullYear()
        },
        month: function(){
            let date = new Date()
            let month = date.getMonth()+1
            if (month >= 1 && month <= 9) {
                month = "0" + month;
            }
            return month
        }
    }
}
</script>

<style lang="scss" scoped>
.push-box{
    padding:60px;
    background-color: #fff;
    border-radius: 4px;
    box-shadow: 0 1px 2px rgba(150,150,150,0.3);
    overflow: hidden;
    display: flex;
}
.tmplmsg-preview{
    margin-right: 60px;
    width: 320px;
    .tmplmsg-card{
        border: 1px solid #e7e7eb;
        border-radius: 5px;
        line-height: 1.6;
        font-family: "Helvetica Neue","Hiragino Sans GB","Microsoft YaHei","\9ED1\4F53",Arial,sans-serif;
        font-size: 14px;
        .tmplmsg-card-hd{
            padding: 14px 15px 10px;
            color: #9a9a9a;
            font-size: 13px;
            border-bottom: 1px solid #e7e7eb;
            .hd-cell{
                padding: 9px 15px;
                display: flex;
                .app-avatar{
                    width: 32px;
                    height: 32px;
                    padding: 0;
                    margin-right: 12px;
                    img{
                        display: block;
                        width: 100%;
                        height: 100%;
                    }
                }
            }
        }
        .tmplmsg-card-bd{
            min-height: 230px;
            padding: 15px;
            .tmplmsg-preview-title{
                color: #353535;
            }
            .tmplmsg-preview-desc{
                color: #9a9a9a;
                font-size: 12px;        
            }
            .tmplmsg-preview-meta-list{
                padding: 30px 0;
                font-size: 13px;
                display: flex;
                flex-direction: column;
                .tmplmsg-preview-meta-item{
                    overflow: hidden;
                    display: flex;
                    align-items: center;
                    label{
                        color: #9a9a9a;
                        margin-right: 1em;
                        width: 5em;
                        flex-shrink: 0;
                        padding: 13px 0px;
                    }
                    p{
                        overflow: hidden;
                        word-wrap: break-word;
                        word-break: break-all;
                    }
                }
            }
        }
        .tmplmsg-card-ft{
            color: #9a9a9a;
            border-top: 1px solid #e7e7eb;
            .ft-cell{
                padding: 9px 15px;
                i{
                    float: right;
                }
            }
        }
    }
}
</style>

