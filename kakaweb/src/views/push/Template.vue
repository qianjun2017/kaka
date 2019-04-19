<template>
    <section>
        <div v-if="view=='list'">
            <el-tabs v-model="activeName" @tab-click="handleClick">
                <el-tab-pane label="我的模板" name="template">
                    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                        <el-form :inline="true">
                            <el-form-item>
                                个人模板库
                            </el-form-item>
                            <el-form-item style="float: right;"><span class="desc">还可添加{{25-tableData.length}}个</span>
                                <el-button type="primary" v-on:click="handleAdd" v-hasPermission="'template.add'" v-if="25-tableData.length>0">添加</el-button>
                                <el-button type="primary"  v-on:click="handleSync" :loading="syncLoading" v-hasPermission="'template.sync'">同步</el-button>
                            </el-form-item>
                        </el-form>
                    </el-col>
                    <!--列表-->
                    <el-table :data="tableData" stripe highlight-current-row v-loading="listLoading" @sort-change="sortChanged" style="width: 100%;" :default-sort = "{prop: 'createTime', order: 'descending'}" :empty-text="message">
                        <el-table-column prop="title" label="标题" width="200" show-overflow-tooltip>
                        </el-table-column>
                        <el-table-column prop="templateId" label="模板ID" width="400">
                        </el-table-column>
                        <el-table-column prop="createTime" label="创建时间" width="180" sortable='custom'>
                        </el-table-column>
                        <el-table-column label="操作">
                            <template slot-scope="scope">
                                <el-button size="small" @click="handleDetail(scope.$index, scope.row)" v-hasPermission="'template.detail'">详情</el-button>
                                <el-button type="warning" size="small" @click="handleDelete(scope.$index, scope.row)" v-hasPermission="'template.delete'">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-tab-pane>
                <el-tab-pane label="模板库" name="library" v-hasPermission="'template.add'">
                    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                        <el-form :inline="true" :model="queryForm">
                            <el-form-item>
                                <el-input v-model="queryForm.title" placeholder="请输入搜索关键字"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" v-on:click="getTableLibraryData">查询</el-button>
                                <el-button type="primary" @click="handleSyncLibrary" v-hasPermission="'template.library.sync'" :loading="syncLibraryLoading">同步</el-button>
                            </el-form-item>
                        </el-form>
                    </el-col>
                    <el-table :data="tableLibraryData" stripe highlight-current-row v-loading="libraryListLoading" style="width: 100%;" :empty-text="libraryMessage">
                        <el-table-column prop="templateId" label="ID" width="300">
                        </el-table-column>
                        <el-table-column prop="title" label="标题">
                        </el-table-column>
                        <el-table-column prop="createTime" label="同步时间" width="180" sortable='custom'>
                        </el-table-column>
                        <el-table-column label="操作" width="150">
                            <template slot-scope="scope">
                                <el-button size="small" @click="handleUse(scope.$index, scope.row)" v-hasPermission="'template.add'">选用</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-col :span="24" class="toolbar">
                        <el-pagination layout="total, prev, pager, next" @current-change="handleCurrentChange" :page-size="pageSize" :total="total" :current-page="page" prev-text="上一页" next-text="下一页" background style="float:right;">
                        </el-pagination>
                    </el-col>
                </el-tab-pane>
            </el-tabs>
        </div>
        <div v-if="view == 'detail'">
            <div class="detail-box">
                <div class="tmplmsg-preview detail-preview">
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
                            <div class="tmplmsg-preview-meta-list">
                                <template v-for="(keyword, index) in template.keywordList">
                                    <div class="tmplmsg-preview-meta-item" :key="index">
                                        <label>{{keyword.name}}</label>
                                        <p>{{keyword.example}}</p>
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
                <div class="tmplmsg-detail-info">
                    <div class="tmplmsg-detail-info-item">
                        <label>模板ID</label>
                        <p>{{template.templateId}}</p>
                    </div>
                    <div class="tmplmsg-detail-info-item">
                        <label>标题</label>
                        <p>{{template.title}}</p>
                    </div>
                    <div class="tmplmsg-detail-info-item tmplmsg-detail-info-item-keyword">
                        <label>关键词</label>
                        <div class="tmplmsg-detail-info-item-keyword-list">
                            <template v-for="(keyword, index) in template.keywordList">
                                <div class="tmplmsg-detail-info-item-keyword-item" :key="index">
                                    <label>{{keyword.name}}</label>
                                    <p>{{'{{'+keyword.keydata+'\}\}'}}</p>
                                </div>
                            </template>
                        </div>
                    </div>
                </div>
            </div>
            <div class="btns">
                <el-button size="small" @click="back">返回</el-button>
            </div>
        </div>
        <div v-if="view == 'use'">
            <div class="mod-box">
                <div class="page-tips-box">
                    <p class="desc">你可用该标题的模板搭配不同的关键词使用，配置提交后关键词种类和顺序将不能修改</p>
                </div>
                <div class="tmplmsg-box">
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
                                <div class="tmplmsg-preview-title">{{templateLibrary.title}}</div>
                                <div class="tmplmsg-preview-desc">{{year}}年{{month}}月</div>
                                
                                <div class="tmplmsg-preview-meta-list">
                                    <template v-for="(templateLibraryKeyword, index) in templateLibraryKeywords">
                                        <div class="tmplmsg-preview-meta-item" :key="index">
                                            <label>{{templateLibraryKeyword.name}}</label>
                                            <p>{{templateLibraryKeyword.example}}</p>
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
                    <div class="portable-editor">
                        <div class="editor-inner">
                            <div class="editor-inner-item">
                                <h3>配置关键词</h3>
                                <div class="keywords-box">
                                    <span class="attach-search">
                                        <input type="text" placeholder="请输入关键词过滤"/>
                                        <i class="el-icon-search"></i>
                                    </span>
                                    <div class="tmplmsg-keywords-list">
                                        <el-checkbox v-model="keyword.checkbox" @change="handleCheckedKeywordsChange" v-for="(keyword,index) in templateLibrary.keyword_list" :true-label="index+1" :false-label="-index-1" :key="'keyword'+keyword.keyword_id">{{keyword.name}}</el-checkbox>
                                    </div>
                                </div>
                            </div>
                            <div class="editor-inner-item">
                                <h3>已选中的关键词<p class="desc">拖拽可调整顺序</p></h3>
                                <div class="tmplmsg-keywords-selected-list">
                                    <draggable v-model="templateLibraryKeywords">
                                        <div class="tmplmsg-keywords-selected-item" v-for="(keyword, index) in templateLibraryKeywords" :key="'selected'+keyword.keyword_id">
                                            <span aria-checked="mixed" class="el-checkbox__input is-checked" @click="handleUnChecked(index)"><span class="el-checkbox__inner"></span></span>
                                            <div class="move">
                                                <div class="selected-item-name">{{keyword.name}}</div>
                                                <i class="el-icon-sort"></i>
                                            </div>
                                        </div>
                                    </draggable>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="btns">
                <el-button type="primary" size="small" @click="handleSubmit">提交</el-button>
                <el-button size="small" @click="back">返回</el-button>
            </div>
        </div>
    </section>
</template>

<script>
import draggable from 'vuedraggable'
export default {
    components: {
        draggable
    },
    data(){
        return {
            activeName: 'template',
            tableData: [],
            listLoading: false,
            message: '',
            sort: '',
            order: '',

            syncLoading: false,

            queryForm: {
                title: ''
            },
            tableLibraryData: [],
            total: 0,
            pages: 0,
            page: 1,
            pageSize: 8,
            libraryMessage: '',
            libraryListLoading: false,

            syncLibraryLoading: false,

            template: {},

            view: 'list',

            templateLibrary: {},
            libraryLoading: false,
            templateLibraryKeywords: [],

            appName: '',
            appIcon: ''
        }
    },
    methods: {
        handleClick: function(tab, event){
            if(this.activeName=='library' && this.tableLibraryData.length==0){
                this.getTableLibraryData()
            }
        },
        getTableData: function(){
            this.listLoading = true
            this.$ajax.get('/template/page',{pageSize: 20, sort: this.sort, order: this.order}).then((res) => {
                this.listLoading = false
                if(res.success){
                    this.tableData = res.data
                }else{
                    this.message = res.message
                    this.tableData = []
                }
            })
        },
        sortChanged: function(column){
            this.sort = column.prop
            this.order = (column.order === 'ascending')?'asc':'desc'
            this.getTableData()
        },
        handleDetail: function(index, row){
            this.$ajax.get('/template/get/'+row.id).then(res=>{
                if(res.success){
                    this.template = res.data
                    this.view = 'detail'
                }else{
                    this.$message.error(res.message)
                }
            })
            if(!this.appName){
                this.$ajax.get('/system/config/name', {propertyName: 'wx.name'}).then((res)=>{
                    if(res.success){
                        this.appName = res.data
                    }
                })
            }
            if(!this.appIcon){
                this.$ajax.get('/system/config/name', {propertyName: 'wx.icon'}).then((res)=>{
                    if(res.success){
                        this.appIcon = res.data
                    }else{
                        this.appIcon = '/static/app.jpg'
                    }
                })
            }
        },
        handleDelete: function(index, row){
            this.$confirm('确认删除'+row.title+'吗?', '提示', {
                type: 'warning'
            }).then(()=>{
                this.$ajax.post('/template/delete/'+row.id).then((res) => {
                    if(res.success){
                        this.$message.success('删除成功')
                        this.getTableData()
                    }else{
                        this.$message.error(res.message)
                    }
                })
            })
        },
        handleAdd: function(){
            this.activeName = 'library'
            if(this.tableLibraryData.length==0){
                this.getTableLibraryData()
            }
        },
        handleSync: function(){
            this.syncLoading = true
            this.$ajax.post('/template/sync').then(res=>{
                this.syncLoading = false
                if(res.success){
                    this.$message.success('同步成功')
                    this.getTableData()
                }else{
                    this.$message.error(res.message)
                }
            })
        },
        handleSyncLibrary: function(){
            this.syncLibraryLoading = true
            this.$ajax.post('/template/library/sync').then(res=>{
                this.syncLibraryLoading = false
                this.$message.success('模板库正在同步，可能需要1分钟，请稍后再使用')
                if(res.success){
                    this.$message.success('同步成功')
                    this.getTableLibraryData()
                }else{
                    this.$message.error(res.message)
                }
            })
        },
        handleUse: function(index, row){
            this.templateLibraryKeywords = []
            this.libraryLoading = true
            this.$ajax.get('/template/library/get/'+row.id).then(res=>{
                this.libraryLoading = false
                if(res.success){
                    this.templateLibrary = res.data
                    this.view = 'use'
                }else{
                    this.$message.error(res.message)
                }
            })
            if(!this.appName){
                this.$ajax.get('/system/config/name', {propertyName: 'wx.name'}).then((res)=>{
                    if(res.success){
                        this.appName = res.data
                    }
                })
            }
            if(!this.appIcon){
                this.$ajax.get('/system/config/name', {propertyName: 'wx.icon'}).then((res)=>{
                    if(res.success){
                        this.appIcon = res.data
                    }else{
                        this.appIcon = '/static/app.jpg'
                    }
                })
            }
        },
        getTableLibraryData: function(){
            this.libraryListLoading = true
            this.$ajax.get('/template/library/page',{pageSize: this.pageSize, page: this.page, title: this.queryForm.title}).then((res) => {
                this.libraryListLoading = false
                if(res.success){
                    this.tableLibraryData = res.data
                    this.page = res.page
                    this.pageSize = res.pageSize
                    this.total = res.total
                    this.pages = res.pages
                }else{
                    this.tableLibraryData = []
                    this.page = 0
                    this.total = 0
                    this.pages = 0
                    this.libraryMessage = res.message
                }
            })
        },
        handleCurrentChange(val) {
            this.page = val
            this.getTableLibraryData()
        },
        back: function(){
            this.view = 'list'
        },
        handleSubmit: function(){
            if(this.templateLibraryKeywords.length==0){
                this.$message.warning('请配置关键字')
            }
            let keywordList = []
            for(let index=0; index<this.templateLibraryKeywords.length; index++){
                keywordList.push(this.templateLibraryKeywords[index].keyword_id)
            }
            let param = {
                id: this.templateLibrary.id,
                keywordList: keywordList
            }
            this.$confirm('确认提交吗?', '提示', {
                type: 'warning'
            }).then(()=>{
                this.$ajax.post('/template/add', param).then((res) => {
                    if(res.success){
                        this.$message.success('提交成功')
                        this.getTableData()
                        this.activeName = 'template'
                        this.view = 'list'
                    }else{
                        this.$message.error(res.message)
                    }
                })
            })
        },
        handleCheckedKeywordsChange: function(val){
            if(val>0){
                if(this.templateLibraryKeywords.length>=10){
                    this.templateLibrary.keyword_list[val-1].checkbox = -val
                    this.$message.warning('最多选择10个关键词')
                }else{
                    this.templateLibraryKeywords.push(this.templateLibrary.keyword_list[val-1])
                }
            }else{
                let keyword = this.templateLibrary.keyword_list[-val-1]
                for(let index=0; index<this.templateLibraryKeywords.length; index++){
                    if(this.templateLibraryKeywords[index].keyword_id==keyword.keyword_id){
                        this.templateLibraryKeywords.splice(index, 1)
                        break
                    }
                }
            }
        },
        handleUnChecked: function(val){
            let keyword = this.templateLibraryKeywords[val]
            this.templateLibrary.keyword_list[keyword.checkbox-1].checkbox=-keyword.checkbox-1
            this.templateLibraryKeywords.splice(val, 1)
        }
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
.btns{
    clear: both;
    width: 100%;
    text-align: center;
    padding-top: 20px;
}
.mod-box{
    padding: 45px 90px 20px;
    min-height: 580px;
    box-sizing: border-box;
    background-color: #fff;
    border-radius: 4px;
    box-shadow: 0 1px 2px rgba(150,150,150,0.3);
}
.detail-box{
    padding:60px;
    background-color: #fff;
    border-radius: 4px;
    box-shadow: 0 1px 2px rgba(150,150,150,0.3);
    overflow: hidden;
    display: flex;
}
.page-tips-box{
    padding-bottom: 20px;
    margin-bottom: 10px;
    border-bottom: 1px dashed #e7e7eb;
    .desc{
        color: #9a9a9a;
    }
}
.tmplmsg-box{
    overflow: hidden;
    margin-top: 30px;
    display: flex;
}
.tmplmsg-preview{
    margin-right: 30px;
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
.portable-editor{
    padding-left: 12px;
    color: #353535;
    flex-grow: 1;
    .editor-inner{
        border-radius: 5px;
        -moz-border-radius: 5px;
        -webkit-border-radius: 5px;
        padding: 40px 30px;
        background-color: #f6f8f9;
        border: 0;
        display: flex;
        flex-direction: column;
        .editor-inner-item{
            margin-bottom: 20px;
            h3{
                font-weight: 400;
                padding: 10px 0;
                font-size: 14px;
                .desc{
                    color: #9a9a9a;
                    display: inline-block;
                    margin-left: 1em;
                }
            }
            .keywords-box{
                background-color: #fff;
                border-radius: 4px;
                padding: 5px 20px;
                .attach-search{
                    padding-left: 10px;
                    height: 40px;
                    line-height: 40px;
                    border: 0;
                    border-bottom: 1px solid #e7e7eb;
                    margin-bottom: 10px;
                    display: flex;
                    width: auto;
                    input{
                        border: 0;
                        outline: 0;
                        flex-grow: 1;
                    }
                    i{
                        font-size: 20px;
                        padding: 10px;
                    }
                }
                .tmplmsg-keywords-list{
                    max-height: 150px;
                    overflow-y: auto;
                    padding-bottom: 20px;
                    display: flex;
                    flex-direction: column;
                    .el-checkbox{
                        margin-left: 12px;
                        line-height: 40px;
                    }
                    .el-checkbox__label {
                        color: #fff;
                    }
                }
            }
            .tmplmsg-keywords-selected-list{
                display: flex;
                flex-direction: column;
                .tmplmsg-keywords-selected-item{
                    display: flex;
                    padding: 0 12px;
                    border-radius: 3px;
                    margin-top: 5px;
                    line-height: 40px;
                    background-color: #fff;
                    align-items: center;
                    .move{
                        flex-grow: 1;
                        display: flex;
                        cursor: move;
                        .selected-item-name{
                            flex-grow: 1;
                            padding-left: 10px;
                            font-size: 14px;
                        }
                        i{
                            font-size: 14px;
                            padding: 13px;
                        }
                    }
                }
                .tmplmsg-keywords-selected-item:first-child{
                    margin-top: 0px;
                }
            }
        }
    }
}
.detail-preview{
    margin-right: 60px;
}
.tmplmsg-detail-info{
    display: flex;
    flex-direction: column;
    color: #353535;
    font-size: 14px;
    .tmplmsg-detail-info-item{
        padding: 1em 0;
        display: flex;
        align-items: center;
        label{
            color: #9a9a9a;
            margin-right: 1em;
            width: 5em;
            flex-shrink: 0;
        }
        p{
            overflow: hidden;
            word-wrap: break-word;
            word-break: break-all;
        }
        .tmplmsg-detail-info-item-keyword-list{
            display: flex;
            flex-direction: column;
            .tmplmsg-detail-info-item-keyword-item{
                display: flex;
                align-items: center;
                p{
                    margin: 0px;
                }
            }
        }
    }
    .tmplmsg-detail-info-item:first-child{
        margin-top: 0;
        border-top-width: 0;
    }
    .tmplmsg-detail-info-item-keyword label{
        align-self: flex-start;
    }
}
</style>

