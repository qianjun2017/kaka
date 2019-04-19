<template>
	<section>
    <div v-show="view=='detail'">
      <div v-if="!card.id" class="no-card">{{'暂无会员卡'}}<el-button type="text" @click="handleCardAdd" v-hasPermission="'card.add'">去创建>></el-button></div>
      <div v-else>
        <div class="card">
          <div class="card-name">{{card.name}}<el-button type="text" @click="handleCardAdd" v-hasPermission="'card.update'">修改>></el-button></div>
          <div class="card-rule" v-html="card.rule"></div>
        </div>
        <div class="levels">
          <template v-for="(level, index) in levelData">
            <div :key="index" class="level">
              <img :src="level.imageUrl"/>
              <div class="name">{{level.name}}</div>
              <div class="op">
                <div
                  class="edit"
                  @click="handleCardLevelEdit(index)"
                  v-hasPermission="'card.level.update'"
                >
                  <i class="el-icon-edit"></i>
                </div>
                <div
                  class="delete"
                  @click="handleCardLevelDelete(index)"
                  v-hasPermission="'card.level.delete'"
                >
                  <i class="el-icon-delete"></i>
                </div>
              </div>
            </div>
          </template>
          <div class="level add" @click="handleCardLevelAdd" v-hasPermission="'card.level.add'">
            <i class="el-icon-plus"></i>
          </div>
        </div>
      </div>
    </div>
    <div v-show="view=='edit'">
      <el-row>
        <el-col :span="16">
          <el-form :model="card" label-width="100px" :rules="cardRules" ref="card">
            <el-form-item label="会员卡名称" prop="name">
              <el-input v-model="card.name" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="使用规则">
              <div id="rule"></div>
            </el-form-item>
          </el-form>
          <div class="btns">
            <el-button type="primary" size="small" @click="submitCard">提交</el-button>
            <el-button size="small" @click="back">取消</el-button>
          </div>
        </el-col>
      </el-row>
    </div>
    <div v-if="view=='level'">
      <el-row>
        <el-col :span="16">
          <el-form :model="cardLevelForm" label-width="100px" :rules="cardLevelFormRules" ref="cardLevelForm">
            <el-form-item label="级别图片" required>
              <el-upload
                list-type="picture-card"
                :action="action"
                :on-preview="handlePictureCardPreview"
                :file-list="fileList"
                :on-success="handleSuccess"
                :on-remove="handleRemove"
                :data="{type: 'image'}"
                :limit="1">
              <i class="el-icon-plus"></i>
              </el-upload>
            </el-form-item>
            <el-form-item label="字体颜色" prop="color">
              <el-input v-model="cardLevelForm.color" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="级别编码" prop="level">
              <el-input v-model="cardLevelForm.level" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="级别名称" prop="name">
              <el-input v-model="cardLevelForm.name" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="最低积分" prop="points">
              <el-input v-model="cardLevelForm.points" auto-complete="off"></el-input>
            </el-form-item>
          </el-form>
          <div class="btns">
            <el-button type="primary" size="small" @click="submitCardLevel">提交</el-button>
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
        card: {},
        levelData: [],
        view: 'detail',

        cardRules: {
          name: [
						{ required: true, message: '请输入会员卡名称', trigger: 'blur' }
          ]
        },

        editor: undefined,
        service: '',

        cardLevelForm: {},
        cardLevelFormRules: {
          level: [
						{ required: true, message: '请输入会员卡级别编码', trigger: 'blur' }
          ],
          name: [
						{ required: true, message: '请输入会员卡级别名称', trigger: 'blur' }
          ],
          points: [
						{ required: true, message: '请输入会员卡级别最低积分', trigger: 'blur' }
          ],
          color: [
						{ required: true, message: '请输入会员卡级别图片中字体颜色', trigger: 'blur' }
          ]
        },
        fileList: [],
        imageUrlList: [],

        dialogVisible: false,
        dialogImageUrl: ""
			}
		},
		methods: {
      getCardLevelData: function(){
        this.$ajax.get('/card/levels').then((res)=>{
          if(res.success){
            this.levelData = res.data
          }else{
            this.levelData = []
          }
        })
      },
      getCardData: function(){
        this.$ajax.get('/card/info').then((res)=>{
          if(this.editor==undefined){
            var self = this
            var E = window.wangEditor
            this.editor = new E('#rule')
            this.editor.customConfig.uploadImgServer = this.action
            this.editor.customConfig.uploadImgMaxSize = 10 * 1024 * 1024
            this.editor.customConfig.uploadFileName = 'file'
            this.editor.customConfig.uploadImgHooks = {
              customInsert: function(insertImg, res, editor){
                if(res.success){
                  let data = res.data || []
                  data.forEach(function (link) {
                    insertImg(self.service+link)
                  })
                }
              }
            }
            this.editor.create()
          }
          if(res.success){
            this.card = res.data
          }else{
            this.card = {}
          }
          if(this.card.rule!=undefined || this.card.rule!=''){
            this.editor.txt.html(this.card.rule)
          }
        })
      },
      handleCardAdd: function(){
        this.view = 'edit'
      },
      back: function(){
        this.view = 'detail'
      },
      submitCard: function(){
        if(this.editor){
          this.card.rule = this.editor.txt.html()
        }
        this.$refs.card.validate((valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
              this.$ajax.post('/card/'+(this.card.id?'update':'add'),this.card).then((res) => {
                if(res.success){
                  this.$message.success('提交成功')
                  this.getCardData()
                  this.view = 'detail'
                }else{
                  this.$message.error(res.message)
                }
              })
            })
          }
        })
      },
      handleCardLevelAdd: function(){
        this.fileList = []
        this.imageUrlList = []
        this.view = 'level'
        this.cardLevelForm = {}
      },
      handleCardLevelUpdate: function(id){
        this.imageUrlList = []
        this.$ajax.get('/card/level/get/'+id).then((res)=>{
          if(res.success){
            this.cardLevelForm = res.data
            this.fileList = []
            this.fileList.push({url: res.data.imageUrl})
            this.view = 'level'
          }else{
            this.$message.error(res.message)
          }
        })
      },
      handlePictureCardPreview: function(file){
        this.dialogImageUrl = file.url
        this.dialogVisible = true
      },
      handleCardLevelEdit: function(index){
        this.fileList = []
        this.imageUrlList = []
        this.view = 'level'
        this.cardLevelForm = this.levelData[index]
        this.fileList.push({url: this.cardLevelForm.imageUrl})
      },
      handleCardLevelDelete: function(index){
        let data = this.levelData[index]
        this.$confirm('确认删除'+data.name+'吗？', '提示', {}).then(() => {
          this.$ajax.post('/card/level/delete/'+data.id).then((res) => {
            if(res.success){
              this.$message.success('删除成功')
              this.getCardLevelData()
            }else{
              this.$message.error(res.message)
            }
          })
        })
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
      submitCardLevel: function(){
        if(this.imageUrlList.length>0){
          this.cardLevelForm.imageUrl = this.imageUrlList[0]
        }
        this.cardLevelForm.cardId = this.card.id
        this.$refs.card.validate((valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
              this.$ajax.post('/card/level/'+(this.cardLevelForm.id?'update':'add'),this.cardLevelForm).then((res) => {
                if(res.success){
                  this.$message.success('提交成功')
                  this.getCardLevelData()
                  this.view = 'detail'
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
      this.getCardData()
      this.getCardLevelData()
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
.no-card{
  width: 100%;
  height: 500px;
  line-height: 500px;
  text-align: center;
}
.btns{
  clear: both;
  width: 100%;
  text-align: center;
}
.card-rule {
  clear: left;
  margin-top: 10px;
  background: #eef1f6;
  padding: 15px 16px;
  line-height: 30px;
  font-family: "Source Sans Pro", "Helvetica Neue", Arial, sans-serif;
  display: block;
  word-break: break-all;
}
.levels{
  margin-top: 10px;
  display: flex;
  flex-wrap: wrap;
  .level {
    text-align: center;
    width: 48%;
    margin: 0.5%;
    position: relative;
    img{
      width: 100%;
    }
    .name{
      position: absolute;
      right: 10px;
      top: 10px;
    }
    .op{
      position: absolute;
      width: 100%;
      height: 100%;
      left: 0;
      top: 0;
      color: #fff;
      opacity: 0;
      font-size: 20px;
      background-color: rgba(0,0,0,.5);
      -webkit-transition: opacity .3s;
      -o-transition: opacity .3s;
      transition: opacity .3s;
      i{
        font-size: 28px;
        color: #fff;
        position: absolute;
        top: 50%;
        margin-top: -14px;
        cursor: pointer;
      }
      .edit,.delete{
        display: inline-block;
        height: 28px;
        width: 28px;
      }
      .delete{
        margin-left: 5px;
      }
    }
    .op:hover{
      opacity: 1;
    }
  }
  .add{
    border: 1px dashed #c0ccda;
    cursor: pointer;
    min-height: 200px;
    i{
      font-size: 28px;
      color: #8c939d;
      position: absolute;
      top: 50%;
      margin-top: -14px;
    }
  }
  .add:hover{
    border-color: #FF5000;
  }
}
</style>
