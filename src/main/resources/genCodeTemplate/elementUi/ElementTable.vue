<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 设备信息
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <div class="container">
            <div class="handle-box">


                #elmQueryInputs#

                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>


                <el-select v-model="value" placeholder="请选择" style="margin:10px" @change=" handleSearch">
                    <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                    </el-option>
                </el-select>

                <el-checkbox v-model="radio" @change=" handleSearch" style="margin-left:20px">显示已删除</el-checkbox>

            </div>

            <el-button link type="primary" size="small" @click="add"
            >新增
            </el-button >

            <el-table
            border
                  highlight-current-row
                      v-loading="listLoading"
                      fit
                      style="width: 100%"
                      @row-click="itemSelect"
                    @selection-change="handleSelectionChange"
                    :data="tableData"

                    class="table"
                    ref="multipleTable"
                    header-cell-class-name="table-header"

            >

                #elmCols#

                <!--                <el-table-column label="操作" width="120" align="center" fixed="right">-->
                <!--                    <template slot-scope="scope">-->
                <!--                        <el-button-->
                <!--                            type="text"-->
                <!--                            icon="el-icon-edit"-->
                <!--                            @click="handleEdit(scope.$index, scope.row)"-->
                <!--                        >编辑</el-button>-->
                <!--                        <el-button-->
                <!--                            type="text"-->
                <!--                            icon="el-icon-delete"-->
                <!--                            class="red"-->
                <!--                            @click="handleDelete(scope.$index, scope.row)"-->
                <!--                        >删除</el-button>-->
                <!--                    </template>-->
                <!--                </el-table-column>-->

                <el-table-column fixed="right" label="操作" width="240">
                    <!--                    <template #default="row">-->
                    <template #default="scope">
                        <!--                        <el-button link type="primary" size="small" @click="handleClick(scope.$index, scope.row)"-->
                        <!--                        <el-button link type="primary" size="small" @click="handleClickTableRow(row)"-->
                        <el-button link type="primary" size="small" @click="handleClickTableRow(scope)"
                        >详细
                        </el-button
                        >


                        <el-button link type="primary" @click="handleEdit(scope.$index, scope.row)"
                                   size="small">编辑
                        </el-button>
                        <el-button link type="primary" @click="handleDelete(scope.$index, scope.row)"
                                   size="small">删除
                        </el-button>

                        <!--                        <el-button link type="primary" @click="handleEdit(scope.$index, scope.row)" size="small">编辑</el-button>-->
                        <!--                        <el-button link type="primary" @click="handleDelete(scope.$index, scope.row)" size="small">删除</el-button>-->
                    </template>
                </el-table-column>

            </el-table>
            <div class="pagination">
                <el-pagination
                        background
                        layout="prev, pager, next"
                        :current-page="pageIndex+1"
                        :page-size="pageSize"
                        :total="pageTotal"
                        @current-change="load"
                >
                </el-pagination>

            </div>
        </div>


        <!-- 编辑弹出框 -->
                <el-dialog title="编辑" :visible.sync="editVisible" width="30%">
<!--        <el-dialog title="编辑" v-model="editVisible" width="30%">-->
            <el-form ref="form" :model="form" label-width="110px">
                #elmFormItems#


            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false;getData()">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>

<!--        <el-dialog title="详细" v-model="showVisible" width="30%">-->
        <el-dialog title="详细" :visible.sync="showVisible" width="30%">
            <el-form ref="form" :model="selectedRow" label-width="110px">
                #elmFormItemsSelectedRow#

            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="showVisible = false;">确 定</el-button>

            </span>
        </el-dialog>



    </div>
</template>

<script>

    import axios from "axios";
    // import Common from '../util/Common';
    // import DateUtil from "../util/DateUtil";

    import Common from '../../util/Common';
    import DateUtil from "../../util/DateUtil";
export default {
    name: 'company',
    data() {
        let mockList=[
            #jsonDefaultNull# 
        ]
        return {
            selectedRow:{

            },


            query: #jsonDefaultNull#,
            pageIndex: 0,
            pageSize: 10,
            tableData: [],
            multipleSelection: [],
            delList: [],
            editVisible: false,
            editVisible2: false,
            pageTotal: 0,
            radio:false,
            form: {
            },
            idx: -1,
            id: -1,
            editAss: false,
            query2: #jsonDefaultNull#,
            pageTotal2: 0,
            tableData2: [],
            radio2:false,
            companyoptions:[],
            company: '',
            selectedRows:[],
        };
    },
    created() {
        this.getData();

    },
    methods: {
        handleSelectionChange(selectedRows) {
            // multipleSelection.value = val;
            console.log("selectedRows")
            console.log(selectedRows)
            this.selectedRows=selectedRows

        },

        add(){
            this.$router.push({
                name: '#类名#Form'
            })
        },
        handleClickTableRow(scope) {
            console.log("scope")
            console.log(scope)

            console.log("scope.row")
            console.log(scope.row)

            console.log("scope.$index")
            console.log(scope.$index)
            this.showVisible=true
            this.selectedRow=scope.row
        },
        handleClick(tab, event) {

        },
        // 获取数据
        getData() {
            axios.post(Common.baseUrl + `/#实体名#/list?pageNumber=${this.pageIndex}&pageSize=${this.pageSize}`, this.query).then(res => {
                console.log("res");
                console.log(res);
                this.tableData = res.data.data.content;

            });

        },
        // 触发搜索按钮
        handleSearch() {
            // this.$set(this.query, 'pageIndex', 1);
            // this.$set(this.query2, 'pageIndex', 1);
            this.getData();
        },
        // 删除操作
        handleDelete(index, row) {

            let  data= {
                id:row.id
            }
            axios.post(Common.baseUrl + "/#实体名#/deleteBy",data).then(res=> {
                console.log("res");
                console.log(res);
                    if(res.data.port=="400") {
                        this.$message.error(res.data.msg);
                    }else{
                        this.$message.success('删除成功');
                        this.getData();
                    }
            });


        },

        // 编辑操作
        handleEdit(index, row) {

            console.log("row")
            console.log(row)
            this.form=row
            this.editVisible = true;
            console.log("this.editVisible")
            console.log(this.editVisible)
        },




        saveEdit() {

            axios.post(Common.baseUrl + "/#实体名#/save", this.form).then(res => {
                console.log("res");
                console.log(res);
                // if (response.data.port == "200") {
                if (res.data.code == "200") {
                    this.$message.success('修改成功！');
                    this.editVisible = false;
                    this.getData();
                } else {
                    this.$message.error(res.data.msg);
                }
            });
        },

    formatDate(row, column) {
    // 获取单元格数据
        return DateUtil.formatDate(row, column)
        // let data = row[column.property]
        // let dt = new Date(data)
        // return dt.getFullYear() + '-' + (dt.getMonth() + 1 > 9?(dt.getMonth() + 1):'0'+(dt.getMonth() + 1)) + '-' +( dt.getDate()>9?dt.getDate():('0'+dt.getDate()))
        // + ' ' + (dt.getHours()>9?dt.getHours():('0'+dt.getHours())) + ':' + (dt.getMinutes()>9?dt.getMinutes():('0'+dt.getMinutes()))+':'+(dt.getSeconds()>9?dt.getSeconds():('0'+dt.getSeconds()))


      },




            // 分页导航
        load(val) {
            // this.pageIndex=val;
            this.pageIndex = val - 1;
            console.log("this.pageIndex")
            console.log(this.pageIndex)
            this.getData();
        },
    }
};
</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 200px;
    display: inline-block;
}
.table {
    width: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
}
.green {
    color: green;
}
.mr10 {
    margin-right: 10px;
}
.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}
</style>
