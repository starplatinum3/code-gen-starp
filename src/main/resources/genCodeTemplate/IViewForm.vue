<template>
    <Form label-position="left" :label-width="120" :model="form" ref="form" :rules="ruleValidate">
        <Row class="writer-form">
            <Col span="12">
                #formInputs#


            </Col>

        </Row>

        <FormItem :label-width="0">
            <Button type="primary" style="margin-right: 20px" @click="commit">{{ editButton }}</Button>
            <Button>重置</Button>
        </FormItem>
    </Form>
</template>

<script>
    import {cityData} from "../../utils/cityData";
    import PhoneUpload from "../upload/phone-upload";
    import TeamSelect from "../input/team-select";
    import FieldSelect from "../input/field-select";
    import {createWriter, getWriterInfo, updateWriter, uploadPhoto,uploadExcel} from "../../api/writer";
    import {formatDate} from "../../utils/common";
    import FieldMultiSelect from "../input/field-multi-select";
    import {validatorTel} from "../../utils/validate";
    import {update,create,post} from "../../utils/request";
    export default {
        name: "writer-form",
        components: {FieldMultiSelect, FieldSelect, TeamSelect, PhoneUpload},
        props: {
            wid: {
                default: null
            },
            // 三种类型: 修改（update）、管理员添加（create）、用户注册（registerwr）
            type: {
                default:'create',
                // required: true,
                required: false,
                // required: true,
            },
            entityId:{
                required: false
            }
        },
        // 父组件
        beforeMount() {
            window.parentMounted = this._isMounted	// _isMounted是当前实例mouned()是否执行 此时为false
        },
        created() {
            // 某个person id
            this.personId = this.$route.query.personId;
            this.form.personId=this.$route.query.personId;
            if(  this.personId ){
                let data={
                    // personId:  this.personId
                    personId:  this.form.personId
                }
                // this.editButton = '确认修改';
                this.editButton = '确认提交此人的身体信息';
                // post(this.tableName+"/findOneBy",data).then(res=>{
                post(this.tableName+"/findOneBy", this.form).then(res=>{
                    console.log(res)
                    this.form=res.data.data
                }).catch(err=>{
                    console.log(err)
                })

                return
            }
            if(!!this.entityId){
                this.editButton = '确认修改';
                post(this.tableName+"/find?id="+ this.entityId).then(res=>{
                    console.log(res)
                    this.form=res.data.data
                })
                return
                // return
            }
            this.editButton = '确认添加';

        },
        computed: {

        },
        data() {
            return {
                personId:null,
                editButton: '',
                cityData: cityData,
                form:#jsonDefaultNull#,
                tableName:#实体名#,


                photoData: null,
                excelData:null,

            }
        },
        methods: {

            selectPage(){
                selectPage({},this.pageNumber,this.pageSize).then(res=>{
                    console.log(res)
                    this.permitionList=res.data.data
                })
            },
            updateForm() {
                this.$refs['form'].validate(valid => {
                    if(!valid){
                        return false;
                    }
                    update(this.form,this.tableName)
                        .then((res) => {
                            console.log(res)
                            this.$Message.success('修改成功');
                            this.selectPage()
                        })

                })
            },
            createForm() {
                // 这里是提交作家
                this.$refs['form'].validate(valid => {
                    if (valid) {
                        create(this.form,this.tableName).then(res => {
                            this.$Message.success('添加成功');
                            this.selectPage()
                        })
                    } else {
                        return false;
                    }
                })
            },

            commit() {
                this.createForm()
            },

        }
    }
</script>

<style scoped>
    .writer-form /deep/ .ivu-form-item {
        width: 80%;
    }

    .experience-form /deep/ .ivu-form-item {
        width: 90%;
    }

</style>
