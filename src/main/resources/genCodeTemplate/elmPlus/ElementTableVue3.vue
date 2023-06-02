<template>
    <div class="reserved-order">
        <SearchFilter
        class="SearchFilter"
            v-model="conditions"
            :searchOptions="searchOptions"
            type
            date
            search
        />
        <el-row
        type="flex"
        justify="end"
        style="flex-wrap: wrap; flex-direction: row"
          >

        {formItemRows}

        </el-row>

                <el-button type="primary" @click="search">查询</el-button>
        <el-button    type="primary"     @click="addOne()"  > 新增 </el-button>

        <div class="reserved-order__table">
            <el-table :data="list" :default-sort="defaultSort" stripe border>
                {elTableColumnRows}
              
                <el-table-column align="center" label="操作" width="300">
                    <template #default="scope">
                        <div class="l-flex l-flex__justify--around">

                            <el-button
                                    size="mini"
                                      type="primary"
                                     @click="addOne(scope.row)"
                                         >
                                           新增
                                 </el-button>
                                 <el-button
                                size="mini"
                                @click="toModify(scope.row)"
                            >
                                修改
                            </el-button>
                         
                            <el-popconfirm
                                icon="el-icon-info"
                                iconColor="red"
                                confirmButtonText="是"
                                cancelButtonText="否"
                                title="确定要删除吗？"
                                @confirm="deleteOne(scope.row)"
                            >
                                <template #reference>
                                    <el-button size="mini" type="danger">
                                        删除
                                    </el-button>
                                </template>
                            </el-popconfirm>
                        </div>
                        {toWhereButton}
                    </template>
                </el-table-column>
            </el-table>



                  <el-pagination
                                :page-sizes="[10, 20, 30, 40]"
                                :page-size="pagination.pageSize"
                                :current-page="pagination.currentPage"
                                :total="pagination.total"
                                @size-change="handleSizeChange"
                                layout="total, sizes, prev, pager, next, ->, jumper"
                                @current-change="handleCurrentChange"
                            ></el-pagination>

        </div>
    </div>
</template>

<script>
import { reactive, ref, toRefs, inject, watch,computed } from 'vue';

import Options from '@/utils/options';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';
import { ElMessage } from 'element-plus';
import loading from '@/utils/loading';
import socketIOTool from '@/utils/socketIOTool';
import HttpUtil from '@/utils/HttpUtil';
import k from '@/utils/Tables';
import UiUtil from '@/utils/UiUtil';
import { ElPagination,ElInputNumber} from 'element-plus';


// function getOrdersRequest(state) {
// import { get{className}sRequest, delete{className}Request } from '@/utils/{entityName}Request';
import {
    getFormatDate,
    getFormatNextDate,
    getFormatDateTime,
} from '@/utils/dateTool';
import SearchFilter from '@/components/SearchFilter';

export default {
    name: 'ReservedOrder',
    components: {
        SearchFilter,
        ElPagination,
         ElInputNumber,
    },
    setup() {
const tableName=k.{entityName}

      let mockList=[
    {jsonMock}
]

        const tableData = reactive({
            origin: [],
            list: mockList,
        });


    //   const currentPage = ref(1);
    //     const pageSize = 10;
    //     const total = ref(0);
    //     const handlePageChange = (newPage) => {
    //       // 处理分页变更事件
    //       currentPage.value = newPage;
    //       // 根据新的页码请求数据或执行其他操作
    //       fetchData(newPage);
    //     };



            const setPageData = (res) => {
                    let data= res.data
                currentPage.value=data.currentPage
                total.value=data.total
                };



        console.log("set up");
        const router = useRouter();
        const store = useStore();

        store.commit('clearNoticeOrderCount');

        const reload = inject('reload');
const form = reactive({jsonDefaultNull});
        const searchOptions = [
            {
                label: '订单编号',
                value: 'oid',
            },
            {
                label: '联系方式',
                value: 'contact',
            },
        ];

        const search = () => {
                    // HttpUtil.getList(tableName,this.form)
                 //   UiUtil.getList(tableName,tableData,this.form)
                    UiUtil.selectPage(tableName, tableData, form, pagination);
                }

        const conditionsData = reactive({
            conditions: [],
        });

 // UiUtil.selectPage(tableName,tableData,form)
  // UiUtil.selectPage(tableName,tableData,{})
        watch(
            () => conditionsData.conditions,
            () => {
                search();
            }
        );


  const pagination = reactive({
            currentPage: 1,
            pageSize: 10,
            total: 0,
        });
        const handleSizeChange = (val) => {
            pagination.pageSize = val;
            pagination.currentPage = 1; // 重置为第一页
        };

        const handleCurrentChange = (val) => {
            pagination.currentPage = val;
            UiUtil.selectPage(tableName, tableData, form, pagination);

            //   UiUtil.selectPage(tableName, tableData, form, currentPage.value, pageSize.value)
        };




const rules = reactive({
            idkey: [
                { required: true, message: '密保不能为空', trigger: 'blur' },
            ],
            password: [
                { required: true, message: '密码不能为空', trigger: 'blur' },
                {
                    pattern: /^\w{4,20}$/,
                    message: '长度4-20的数字或字母或下划线',
                    trigger: 'blur',
                },
            ],
            againPassword: [
                { required: true, message: '密码不能为空', trigger: 'blur' },
                {
                    validator: (rule, value, callback) => {
                        if (value !== form.password) {
                            callback(new Error('两次密码输入不一致'));
                        } else {
                            callback();
                        }
                    },
                    trigger: 'blur',
                },
            ],
        });

        const defaultSort = { prop: 'reservationDate', order: 'ascending' };

        const sortType = (a, b) => {
            return a.type - b.type;
        };

        const sortReserved = (a, b) => {
            return a.reservation_time - b.reservation_time;
        };
   // UiUtil.selectPage(tableName, tableData, form, pagination);
        const typeTextArray = ['大床间', '单人间', '双人间'];
        console.log("socketIOTool.on('new-order', (");
        socketIOTool.on('new-order', (socket) => {
            socket.orderArray.forEach((order) => {
                order.typeText = typeTextArray[order.type];
                let date = getFormatDate(order.reservation_time);
                let nextDate = getFormatNextDate(
                    order.reservation_time,
                    order.reservation_during
                );

                order.placeTime = getFormatDateTime(order.place_time);
                order.reservationDate = date + ' 至 ' + nextDate;
            });

            let newList = tableData.origin.concat(socket.orderArray);
            tableData.origin = newList;
            tableData.list = newList;
        });

     // UiUtil.getList(tableName,tableData,{})
        // loading.start();
        console.log("getOrdersRequest 获取用户");
        // 获取用户
        // function getOrdersRequest(state) {


            const setTableData = (tableData,res) => {
                tableData.origin = res;
                tableData.list = res;
               };



        const toModifyOrder = (order) => {
            router.push({
                name: 'ModifyOrder',
                query: { oid: order.oid },
                params: { state: 0 },
            });
        };

        const toModify = (item) => {
        let id=item.id
            let oid=  item.oid
            router.push({
                name: 'Modify{className}',
                query: { id, oid},
                params: { state: 0 },
            });
        };

{genToTableFuncCode}


        const upload = reactive({
                    img: '',
                    list: [],
                });


        const addOne = () => {
                    router.push({
                        name: 'Add{className}',

                    });
                };
        

        const toCheckIn = (order) => {
            router.push({
                name: 'CheckIn',
                query: { oid: order.oid },
                params: { uid: order.uid },
            });
        };



     // 放大图片
            let dialogVisible = ref(false);
            const imgPreview = (file) => {
                form.img = file.url;
                dialogVisible.value = true;
            };

            const verifyFileType = (file, fileList) => {
                if (!file.raw.type.includes('image')) {
                    fileList.splice(0, 1);
                    ElMessage.warning('上传文件类型必须是图片!');
                    return false;
                }
                upload.img = file.url;
            };

search()
            const uploadImg = (obj) => {
                let formData = new FormData();
                formData.append('file', obj.file);
                formData.append('type', -1);
                formData.append('oldImgName', upload.oldImgName);

                uploadIntroImgRequest(formData).then((res) => {
                    console.log(res.msg);
                });
            };

            const deleteOne = (item) => {
                        UiUtil.deleteOne(tableName, item, tableData, reload);
                    };


        return {
         search,
            //    currentPage,
           //     pageSize,
          //    total,
                 //   handlePageChange,
                       pagination,

        verifyFileType,
            deleteOne,
            searchOptions,
            ...toRefs(conditionsData),
            ...toRefs(tableData),
            defaultSort,
            sortType,
            sortReserved,
            toModifyOrder,
            toModify,
            toCheckIn,

              rules,
                form,
                addOne,
                  dialogVisible,
                            imgPreview,
                            verifyFileType,
                            uploadImg,
                              upload,
                              {genToTableFuncExportCode}
        };
    },
};
</script>

<style lang="scss">
.reserved-order {
    margin: 30px 10px;
    padding: 0 20px;

    .reserved-order__table {
        border: 1px solid #ebeef5;
        margin-top: 10px;
    }
}
</style>
