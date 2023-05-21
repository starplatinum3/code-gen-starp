<template>
    <div class="add-room">
        <div class="form__title">
            <h2>添加新{tableComment}</h2>
        </div>
        <div class="add-room__form">
            <el-form
                :model="form"
                :rules="rules"
                ref="formElem"
                label-position="right"
                label-width="80px"
            >
            
                <p class="form__msg">{{ errorMsg }}</p>
                {form_item_rows_add}
               

                <el-form-item>
                    <el-button class="form__btn" type="primary" @click="add{className}"
                        >添加新{tableComment}</el-button
                    >
                </el-form-item>
            </el-form>

               <!-- 分页组件 -->
    <el-pagination
      :current-page="currentPage"
      :page-size="pageSize"
      :total="total"
      layout="prev, pager, next"
      @current-change="handlePageChange"
    ></el-pagination>
    
        </div>
    </div>
</template>

<script>
import { reactive, ref,toRefs, inject, watch } from 'vue';
// import { ref } from 'vue';
import { ElPagination } from 'element-plus';

import { ElMessage } from 'element-plus';
import loading from '@/utils/loading';
import { addRoomRequest } from '@/utils/roomRequest';
// import { uploadImgRequest } from '@/utils/roomRequest';
import { uploadImgRequest } from '@/utils/roomRequest';
// import { uploadImgRequest } from 'src/utils/roomRequest';
import { useRouter, useRoute } from 'vue-router';
// import HttpUtil from '../../../utils/HttpUtil';
// import HttpUtil from 'src/utils/HttpUtil';
import StatusSelect from '@/components/StatusSelect.vue';
import ElmStatusSelect from '@/components/ElmStatusSelect.vue';
import HttpUtil from '@/utils/HttpUtil';
import k from '@/utils/Tables';
//import { ref } from 'vue';

import UiUtil from '@/utils/UiUtil';
// D:\proj\makeBook\hotel\hotel-management-origin\src\components\ElmStatusSelect.vue
// D:\proj\makeBook\hotel\hotel-management-origin\src\components\StatusSelect.vue
// D:\proj\makeBook\hotel\hotel-management-origin\src\views\hall\roomTypeInfo\AddRoomTypeInfo.vue
export default {
     name: 'Add{className}',
    components: {
        ElmStatusSelect,
        StatusSelect,
        ElPagination,
    },
    setup() {
        let  tableName=k.{entityName}
     const router = useRouter();
    const route = useRoute();


        const currentPage = ref(1);
    const pageSize = 10;
    const total = ref(0);
       const form = reactive({jsonDefaultNull});
          const queryId = route.query.id;
 form.id=queryId

HttpUtil.selectPage(tableName,{}, 1, 1, ).then(res => {
            console.log('res');
            console.log(res);
            form.value=res
        });

    const handlePageChange = (newPage) => {
      // 处理分页变更事件
      currentPage.value = newPage;
      // 根据新的页码请求数据或执行其他操作
      fetchData(newPage);
    };


        const formElem = ref(null);
        



        console.log("form");
        console.log(form);
        const uploadElem = ref(null);
        const upload = reactive({
            img: '',
            list: [],
        });

        // HttpUtil.addRoom

        const rules = reactive({
            number: [
                { required: true, message: '房间号不能为空', trigger: 'blur' },
                {
                    pattern: /^[0-9A-Za-z]{3,8}$/,
                    message: '长度3-8的数字或字母',
                    trigger: 'blur',
                },
            ],
        });



                const search = () => {
                            // HttpUtil.getList(tableName,this.form)
                            // UiUtil.getList(tableName,tableData,form)
                            // list
                            UiUtil.list(tableName,tableData,form)
                        }

                        const addOne = (order) => {
                                    // D:\proj\makeBook\hotel\hotel-management-origin\src\views\hall\roomTypeInfo\AddRoomTypeInfo.vue
                                    router.push({
                                        name: 'Add{className}',
                                        query: { oid: order.oid },
                                        params: { uid: order.uid },
                                    });
                                };

        const typeOptions = [
            {
                label: '大床间',
                value: 0,
            },
            {
                label: '单人间',
                value: 1,
            },
            {
                label: '双人间',
                value: 2,
            },
        ];

         const tableData = reactive({
                    origin: [],
                    list: [],
                    search: [],
                });

            //        UiUtil.list(tableName,tableData,{})
        const roomTypeOptions = [
            {
                    text: '大床间',
                value: 0,
            },
            {
                text: '单人间',
                value: 1,
            },
            {
                text: '双人间',
                value: 2,
            },

        ];
        const options = [
            {
                label: '无',
                value: 0,
            },
            {
                label: '有',
                value: 1,
            },
        ];

        let errorMsg = ref('');
        watch(form, () => {
            if (errorMsg.value) {
                errorMsg.value = '';
            }
        });

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

        const onSuccessHandle = (response, file, fileList) => {
            fileList.splice(0, 1);
        };

         const toModify = (order) => {
                    router.push({
                        name: 'Modify{className}',
                        query: { oid: order.oid },
                        params: { state: 0 },
                    });
                };
        

        const uploadImg = (obj) => {
            let formData = new FormData();
            formData.append('file', obj.file);
            formData.append('number', form.number);

            uploadImgRequest(formData).then((res) => {
                obj.onSuccess();
                console.log(res.msg);
            });
        };


        const add{className} = () => {
            formElem.value.validate((valid) => {
                if(!valid){
                    return
                }
                HttpUtil.save(tableName, form)
                .then(res => {
                        if (res.state) {
                            // uploadElem.value.submit();
                            ElMessage.success(res.msg);
                            formElem.value.resetFields();
                        } else {
                            errorMsg.value = res.msg;
                        }
                        loading.close();
                    })
                    .catch((err) => {
                        console.log(err);
                        loading.close();
                    });
              
               
            });
        };

         const btnNameUpAdd = ref("添加");

        return {
            currentPage,
      pageSize,
      total,
      handlePageChange,
  ...toRefs(tableData),
 search,
 toModify,
  add{className},
            addOne,
            form,
            formElem,
            rules,
            upload,
            uploadElem,
            typeOptions,
            options,
            errorMsg,
            dialogVisible,
            imgPreview,
            verifyFileType,
            onSuccessHandle,
            uploadImg,
            roomTypeOptions,
              dialogVisible,
                 btnNameUpAdd,
        };
    },
};
</script>

<style lang="scss">
.add-room {
    padding: 20px;

    .add-room__form {
        width: 50%;
        margin: 16px;
    }

    .add-room__input {
        width: 50%;
    }

    .add-room__tip {
        font-size: 12px;
        color: $info-color;
    }
}
</style>
