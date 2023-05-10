<template>
    <div class="add-room">
        <div class="form__title">
            <h2>添加新房间</h2>
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
               
                <!-- <el-form-item>
                <el-upload
                    ref="uploadElem"
                    class="l-flex"
                    action="http://localhost:9092/uploadImg"
                    :http-request="uploadImg"
                    list-type="picture-card"
                    :file-list="upload.list"
                    :auto-upload="false"
                    :limit="1"
                    :on-preview="imgPreview"
                    :on-change="verifyFileType"
                    :on-success="onSuccessHandle"
                >
                    <i class="el-icon-plus"></i>
                </el-upload>
                <el-dialog v-model="dialogVisible">
                    <div style="text-align: center">
                        <img :src="upload.img" style="width: 100%" />
                    </div>
                </el-dialog>
                <p class="add-room__tip">只能上传一张图片</p>
            </el-form-item> -->
                <el-form-item>
                    <el-button class="form__btn" type="primary" @click="addRoom"
                        >添加新房间</el-button
                    >
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
import { reactive, ref, watch } from 'vue';
import { ElMessage } from 'element-plus';
import loading from '@/utils/loading';
import { addRoomRequest } from '@/utils/roomRequest';
// import { uploadImgRequest } from '@/utils/roomRequest';
import { uploadImgRequest } from '@/utils/roomRequest';
// import { uploadImgRequest } from 'src/utils/roomRequest';

// import HttpUtil from '../../../utils/HttpUtil';
// import HttpUtil from 'src/utils/HttpUtil';
import HttpUtil from '@/utils/HttpUtil';
import StatusSelect from '@/components/StatusSelect.vue';
import ElmStatusSelect from '@/components/ElmStatusSelect.vue';
import HttpUtil from '@/utils/HttpUtil';
import k from '@/utils/Tables';
import UiUtil from '@/utils/UiUtil';
// D:\proj\makeBook\hotel\hotel-management-origin\src\components\ElmStatusSelect.vue
// D:\proj\makeBook\hotel\hotel-management-origin\src\components\StatusSelect.vue
// D:\proj\makeBook\hotel\hotel-management-origin\src\views\hall\roomTypeInfo\AddRoomTypeInfo.vue
export default {
    name: 'Add{tableName}',
    components: {
        ElmStatusSelect,
        StatusSelect,
    },
    setup() {
        const formElem = ref(null);
        const form = reactive({
            number: '',
            type: 0,
            shower: 0,
            tv: 0,
            extra: '',
        });
        const form = reactive({jsonDefaultNull});

        let  tableName=k.{entityName}
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
                HttpUtil.add(tableName, form)
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

        return {
            form,
            formElem,
            rules,
            upload,
            uploadElem,
            typeOptions,
            options,
            errorMsg,
            addRoom,
            dialogVisible,
            imgPreview,
            verifyFileType,
            onSuccessHandle,
            uploadImg,
            roomTypeOptions,
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
