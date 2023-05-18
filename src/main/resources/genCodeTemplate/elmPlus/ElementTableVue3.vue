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
        {formItemRows}

                <el-button type="primary" @click="search">查询</el-button>

        <div class="reserved-order__table">
            <el-table :data="list" :default-sort="defaultSort" stripe border>
                {elTableColumnRows}
              
                <el-table-column align="center" label="操作" width="300">
                    <template #default="scope">
                        <div class="l-flex l-flex__justify--around">
                            <el-button
                                size="mini"
                                type="primary"
                                @click="toCheckIn(scope.row)"
                            >
                                办理入住
                            </el-button>
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
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
import Options from '@/utils/options';
import { reactive, toRefs, inject, watch } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';
import { ElMessage } from 'element-plus';
import loading from '@/utils/loading';
import socketIOTool from '@/utils/socketIOTool';
import HttpUtil from '@/utils/HttpUtil';
import k from '@/utils/Tables';
import UiUtil from '@/utils/UiUtil';

// function getOrdersRequest(state) {
import { get{className}sRequest, delete{className}Request } from '@/utils/{entityName}Request';
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
    },
    setup() {
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
                    UiUtil.getList(tableName,tableData,this.form)
                }

        const conditionsData = reactive({
            conditions: [],
        });

        // 查询
        const search = () => {
            let list = tableData.origin;

            for (let condition of conditionsData.conditions) {
                if (condition.key === 'type') {
                    list = list.filter((order) => {
                        return order.type === condition.value;
                    });
                }

                if (condition.key === 'date') {
                    list = list.filter((order) => {
                        return (
                            condition.value[0] <= order.reservation_time &&
                            order.reservation_time <= condition.value[1]
                        );
                    });
                }

                if (condition.key === 'search') {
                    const keywordReg = new RegExp(condition.value);

                    list = list.filter((order) => {
                        return keywordReg.test(order[condition.searchKey]);
                    });
                }
            }
            tableData.list = list;
        };

        watch(
            () => conditionsData.conditions,
            () => {
                search();
            }
        );


        let mockList=[
    {jsonMock}
]

        const tableData = reactive({
            origin: [],
            list: mockList,
        });



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
const tableName=k.{entityName}
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

     UiUtil.getList(tableName,tableData,{})
        // loading.start();
        console.log("getOrdersRequest 获取用户");
        // 获取用户
        // function getOrdersRequest(state) {
        getOrdersRequest(0)
            .then((res) => {
                console.log("getOrdersRequest");
                console.log(res);
                for (let order of res) {
                    order.typeText = typeTextArray[order.type];

                    let date = getFormatDate(order.reservation_time);
                    let nextDate = getFormatNextDate(
                        order.reservation_time,
                        order.reservation_during
                    );

                    order.placeTime = getFormatDateTime(order.place_time);
                    order.reservationDate = date + ' 至 ' + nextDate;
                }
                tableData.origin = res;
                tableData.list = res;

                // loading.close();
            })
            .catch((err) => {
                console.log(err);
                loading.close();
            });

            const setTableData = (tableData,res) => {
                tableData.origin = res;
                tableData.list = res;
        };

        HttpUtil.getList(tableName,{})
                .then(res=>{
                    console.log(res);
                }).catch(err=>{
                    console.log(err);
                })

        const toModifyOrder = (order) => {
            router.push({
                name: 'ModifyOrder',
                query: { oid: order.oid },
                params: { state: 0 },
            });
        };

        const toModify = (order) => {
            router.push({
                name: 'Modify{className}r',
                query: { oid: order.oid },
                params: { state: 0 },
            });
        };

        const addOne = (order) => {
            router.push({
                name: 'add{className}',
                query: { oid: order.oid },
                params: { uid: order.uid },
            });
        };
        

        const toCheckIn = (order) => {
            router.push({
                name: 'CheckIn',
                query: { oid: order.oid },
                params: { uid: order.uid },
            });
        };

        const deleteOder = (order) => {
            loading.start();
            deleteOrderRequest(order.oid)
                .then((res) => {
                    if (res.state) {
                        let index = tableData.list.findIndex((value) => {
                            return order.oid === value.oid;
                        });
                        tableData.list.splice(index, 1);
//                         使用 splice() 方法从 tableData.list 数组中删除指定索引位置 index 处的一个元素。
// 删除后，splice() 方法会返回被删除的元素，但在这段代码中并没有使用它，因此可以忽略返回值。
                        tableData.origin = tableData.list;
                        ElMessage.success(res.msg);

                        reload();
                    } else {
                        ElMessage.error(res.msg);
                    }

                    loading.close();
                })

                .catch((err) => {
                    console.log(err);
                    ElMessage.error('fail');
                    loading.close();
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
            loading.start();
            HttpUtil.delete(item)
            // deleteOrderRequest(order.oid)
                .then((res) => {
                    if (res.state) {
                        let index = tableData.list.findIndex((value) => {
                            return item.id === value.id;
                        });
                        tableData.list.splice(index, 1);
//                         使用 splice() 方法从 tableData.list 数组中删除指定索引位置 index 处的一个元素。
// 删除后，splice() 方法会返回被删除的元素，但在这段代码中并没有使用它，因此可以忽略返回值。
                        tableData.origin = tableData.list;
                        ElMessage.success(res.msg);

                        reload();
                    } else {
                        ElMessage.error(res.msg);
                    }

                    loading.close();
                })

                .catch((err) => {
                    console.log(err);
                    ElMessage.error('fail');
                    loading.close();
                });
        };
        return {
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
            deleteOder,
              rules,
                form,
                addOne,
                  dialogVisible,
                            imgPreview,
                            verifyFileType,
                            uploadImg,
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
