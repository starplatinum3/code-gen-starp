<template>
    <div>
    <el-form ref="form" :model="form" label-width="110px">
       #elmFormItems#
    </el-form>

        <el-button type="primary" @click="saveEdit">提交新增</el-button>


    </div>
</template>

<script>

    import axios from "axios";
    import Common from '../../util/Common';
    import DateUtil from "../../util/DateUtil";

    export default {
        name: '#类名#Form',
        data() {
            return {
                form: {},
            };
        },
        created() {

        },
        methods: {

            saveEdit() {
                // axios.post(Common.baseUrl + "/problem/save", this.form).then(res => {
                axios.post(Common.baseUrl + "/#实体名#/save", this.form).then(res => {
                    console.log("res");
                    console.log(res);
                    // if (res.data.port == "200") {
                    if (res.data.code == "200") {
                        this.$message.success('新增成功！');
                    } else {
                        this.$message.error(res.data.msg);
                    }
                });
            },
        }
    };
</script>

<style scoped>

</style>
