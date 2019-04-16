<template>
    <section>
        <div v-if="view=='list'">
            <el-tabs v-model="activeName" @tab-click="handleClick">
                <el-tab-pane label="我的模板" name="template">
                    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                        个人模板库
                        <el-button type="primary" size="small" v-on:click="handleAdd" v-hasPermission="'template.add'"  style="float: right;">添加</el-button>
                        <el-button size="small" v-on:click="handleSync" :loading="syncLoading" v-hasPermission="'template.sync'">同步</el-button>
                    </el-col>
                    <!--列表-->
                    <el-table :data="tableData" stripe highlight-current-row v-loading="listLoading" @sort-change="sortChanged" style="width: 100%;" :default-sort = "{prop: 'createTime', order: 'descending'}" :empty-text="message">
                        <el-table-column prop="title" label="标题" width="300" show-overflow-tooltip>
                        </el-table-column>
                        <el-table-column prop="templateId" label="模板ID" width="250">
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
                <el-tab-pane label="模板库" name="library">
                    <el-table :data="tableLibraryData" stripe highlight-current-row v-loading="libraryListLoading" style="width: 100%;" :empty-text="libraryMessage">
                        <el-table-column prop="id" label="ID" width="300">
                        </el-table-column>
                        <el-table-column prop="title" label="标题">
                        </el-table-column>
                        <el-table-column label="操作" width="150">
                            <template slot-scope="scope">
                                <el-button size="small" @click="handleUse(scope.$index, scope.row)" v-hasPermission="'template.library.use'">选用</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-col :span="24" class="toolbar">
                        <el-pagination layout="total, prev, pager, next" @current-change="handleCurrentChange" :page-size="pageSize" :total="total" prev-text="上一页" next-text="下一页" background style="float:right;">
                        </el-pagination>
                    </el-col>
                </el-tab-pane>
            </el-tabs>
        </div>
        <div v-if="view == 'detail'">
            <div class="btns">
                <el-button size="small" @click="back">返回</el-button>
            </div>
        </div>
        <div v-if="view == 'use'">
            <div></div>
            <div class="btns">
                <el-button type="primary" size="small" @click="handleSubmit">提交</el-button>
                <el-button size="small" @click="back">返回</el-button>
            </div>
        </div>
    </section>
</template>

<script>
export default {
    data(){
        return {
            activeName: 'template',
            tableData: [],
            listLoading: false,
            message: '',
            sort: '',
            order: '',

            syncLoading: false,

            tableLibraryData: [],
            total: 0,
            pages: 0,
            page: 1,
            pageSize: 8,
            libraryMessage: '',
            libraryListLoading: false,

            template: {},

            view: 'list',

            templateLibrary: {},
            libraryLoading: false
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
        },
        handleDelete: function(index, row){

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
        handleUse: function(index, row){
            this.view = 'use'
            this.libraryLoading = true
            this.$ajax.get('/template/library/get/'+row.id).then(res=>{
                this.libraryLoading = false
                if(res.success){
                    this.templateLibrary = res.data
                }else{
                    this.$message.error(res.message)
                }
            })
        },
        getTableLibraryData: function(){
            this.libraryListLoading = true
            this.$ajax.get('/template/library/page',{pageSize: this.pageSize, page: this.page}).then((res) => {
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

        }
    }
}
</script>

<style lang="scss" scoped>
.btns{
    clear: both;
    width: 100%;
    text-align: center;
}
</style>

