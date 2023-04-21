<template>
    <div>
        <layout-content>
            <div slot="main">
                <!--查询-->
                <Form label-position="right" :label-width="120">
                    <Row>

                        #formInputs#


                        <Col>
                            <FormItem :label-width="0">
                                <div style="float: right">
                                    <Button type="primary" icon="ios-search" style="margin-right: 10px"
                                            @click="selectPage">查询
                                    </Button>
                                    <Button
                                            type="primary"
                                            icon="ios-download-outline"
                                            style="margin-right: 10px"
                                            @click="exportToExcel"
                                            :loading="exportLoading">导出
                                    </Button>
                                    <Button @click="reset" style="margin-right: 10px">重置</Button>
                                    <span class="scale" v-if="spread" @click="spread = !spread">收起<Icon
                                            type="ios-arrow-up"/></span>
                                    <span class="scale" v-if="!spread" @click="spread = !spread">展开<Icon
                                            type="ios-arrow-down"/></span>
                                </div>
                            </FormItem>
                        </Col>
                    </Row>
                </Form>


                <div style="margin-bottom: 20px;">
                    <Button icon="md-add" type="primary" @click="handleAdd" style="margin-right: 20px" ghost>添加作家
                    </Button>
                    <Button icon="md-trash" type="error" @click="handleBatchDelete" ghost>批量删除</Button>

                </div>

                <template >
                    <!--                    width="10000"-->
                    <Table  id="table" :columns="columns"
                            class="auto-column-size-table data-table"
                            @on-selection-change="handleSelectRow()" :data="allList" ref="selection"
                            :loading="searchLoading">
                        <!--              这么离谱 这里不用配置列表项-->
                        <!--              配置在 columns-->
                        <template slot-scope="{ row, index }" slot="action">
                            <Button type="text" size="small" style="margin-right: 10px; color: #2d8cf0"
                                    @click="handleUpdate(index)">
                                修改
                            </Button>
                            <Divider type="vertical"/>
                            <Button type="text" size="small" style="margin-right: 10px; color: #2d8cf0"
                                    @click="exportToWord(index)">
                                导出
                            </Button>
                            <Divider type="vertical"/>
                            <Button type="text" size="small" style="margin-right: 10px; color: #ed4014"
                                    @click="handleDelete(index)">
                                删除
                            </Button>
                        </template>
                    </Table>
                </template>
                <div style="margin: 20px; text-align: center">
                    <Page :current="select.pageNum" :total="total" :page-size="select.pageSize"
                          show-elevator @on-change="pageChange" show-total/>
                </div>
                <!--        898 total 每页是 10？ 这样是 89页， 每页是9 那要90页啊 都不是。。 不对啊 898/10==89...8 那确实要90页 -->
                <!--        那 数据不是少了吗  显示 是每页只有9个 啊 -->
            </div>
        </layout-content>

        <Button type="primary" icon="ios-search" style="margin-right: 10px" @click="compare">比较</Button>

        <div id="leiDaTu" class="echart" style="width: 600px;height: 600px;"></div>


        <Modal v-model="del.modal" width="360" class-name="vertical-center-modal">
            <p slot="header" style="color:#f60;text-align:center">
                <Icon type="ios-information-circle"/>
                <span>删除警告</span>
            </p>
            <div style="text-align:center">
                <p>{{ del.title }}</p>
                <p style="color: #ed4014">(删除作家信息时会删除其作品和专辑信息)</p>
            </div>
            <div slot="footer">
                <Button @click="del.modal = false">取消</Button>
                <Button type="error" :loading="del.loading" @click="deleteWriter">删除</Button>
            </div>
        </Modal>

    </div>
</template>

<script>
    import LayoutContent from "../../components/layout/layout-content";
    // import {batchDelete, deleteWriter, exportToWord, selectWriters} from "../../api/writer";
    import {batchDelete, deleteOne, exportToWord, selectAll, selectPage} from "../../api/#实体名#";
    // import {batchDelete, deleteWriter, exportToWord, selectWriters} from "../../api/writer";
    import WriterInfoCard from "../../components/card/writer-info-card";
    import FieldSelect from "../../components/input/field-select";
    import TeamSelect from "../../components/input/team-select";
    import {export2Excel} from "../../utils/excel";
    import {#实体名#Columns} from "../../utils/dictData";
    import Common, {resetDict} from "../../utils/common";
    import StateTag from "../../components/tag/state-tag";
    import {mapGetters} from "vuex";
    import Color from "../../utils/color";
    import StringUtil from "../../utils/StringUtil";
    // https://www.cnblogs.com/aidixie/p/14980957.html
    export default {
        name: "#实体名#-manage",
        components: {StateTag, TeamSelect, FieldSelect, WriterInfoCard, LayoutContent},

        beforeDestroy: function () {
            window.removeEventListener('resize', this.handleResize)
        },

        // https://blog.csdn.net/Little_Pig_Bug/article/details/88408445
        data() {
            // 八个词语 大概 宽度 100
            let columns= [
                {
                    type: 'selection',
                    width: 60,
                    align: 'center'
                },
                // {
                //     type: 'index',
                //     width: 60,
                //     align: 'center'
                // },

                #iViewColumnsRows#

                {
                    title: '操作',
                    slot: 'action',
                    fixed: 'right',
                    width: 240,
                    align: 'center'
                }

            ]

            for(let o of columns){
                let title=  o.title
                if(title){
                    // let  titleNoEndBra=  StringUtil.tail_delete_brackets(title)
                    o.title= StringUtil.tail_delete_brackets(title)
                    // console.log(titleNoEndBra)
                }
                // o.width=null
            }
            // 没有列宽的话  是所有平分

            return {
                charts: null,

                // 这里的items和 allItems都是数组。
                // items对应多选框的:label="item.id",当选中某个多选框时，会把该id加到items中
                selectedPersonLabels: ['1', '3'],
                selectedPersons: [],
                spread: true,
                fullWidth: document.documentElement.clientWidth,
                pageNum:1,
                pageSize:10,
                select:  #jsonDefaultNull#,
                status: 0,
                display: '列表',
                writerList: [],
                columns:columns,
                total: 0,
                allList: [],
                searchLoading: false,
                exportLoading: false,
                del: {
                    loading: false,
                    modal: false,
                    index: 0,
                    type: '',
                    title: '',
                    widList: []
                },

            }
        },
        created() {

            this.selectPage()
            window.addEventListener('resize', this.handleResize)

        },
        mounted(){

        },
        computed: {
            ...mapGetters([
                'isCollapse',
            ]),
            rightWidth() {
                let leftWidth = this.isCollapse ? '64' : '200';
                console.log(this.fullWidth)
                return (this.fullWidth - leftWidth) + 'px'
            },

        },
        methods: {

            handleSelectRow(selection, index) {
                // 获得了
                let selections = this.$refs.selection.getSelection()
                // 比较两个人
                console.log(this.$refs.selection.getSelection());
                // this.compare(selections)
            },


            selectPage() {
                this.searchLoading = true;
                selectPage(this.select, this.pageNum,
                    this.pageSize).then(res => {

                    this.allList = res.data.data.list || res.data.data;
                    this.total = res.data.data.total;
                    this.searchLoading = false;
                })
            },

            handleResize(event) {
                this.fullWidth = document.documentElement.clientWidth
            },
            handleAdd() {
                this.$router.push({name: '#类名#Edit'});
            },
            handleUpdate(index) {
                this.$router.push({name: '#类名#Edit', query: {id: this.allList[index].id}});
            },
            handleDelete(index) {
                this.del.index = index;
                this.del.title = '您确定要删除吗？';
                this.del.type = 'delete';
                this.del.modal = true;
            },
            handleBatchDelete() {
                let selectList = this.$refs.selection.getSelection();
                if (selectList.length === 0) {
                    this.$Message.error('请选择要删除的');
                    return;
                }
                this.del.widList.splice(0, this.del.widList.length);
                for (let item of selectList) {
                    this.del.widList.push(item.wid);
                }
                this.del.modal = true;
                this.del.title = '您确定要批量删除他们吗？';
                this.del.type = 'batchDelete';
            },


            selectAll() {
                this.searchLoading = true;
                selectAll(this.select).then(res => {
                    this.allList = res.data.data.list;
                    this.total = res.data.data.total;
                    this.searchLoading = false;
                })
            },
            // 下面选中了 哪个page  一共有90页
            pageChange(index) {
                this.select.pageNum = index;
                this.selectWriters();
            },
            exportToExcel() {
                this.select.pageSize = 99999;
                this.exportLoading = true;
                selectWriters(this.select).then(res => {
                    export2Excel(writerColumns, res.data.data.list, '作家查询下载结果');
                    this.select.pageSize = 9;
                    this.exportLoading = false;
                })
            },
            exportToWord(index) {
                exportToWord(this.writerList[index].wid).then(res => {
                    const a = document.createElement('a');
                    let blob = new Blob([res.data], {type: 'application/msword'});
                    const link = document.createElement('a');
                    let fName = this.writerList[index].name;
                    link.href = URL.createObjectURL(blob);
                    link.style.display = 'none';
                    link.setAttribute('download', fName);
                    document.body.appendChild(link);
                    link.click();
                    document.body.removeChild(link);
                })
            },
            // 重置查询
            reset() {
                resetDict(this.select);
            },
            changeSelectTeamId(val) {
                this.select.teamId = val;
            }
        }
    }
</script>

<style scoped>

    .data-table{
        /*width: 2000px;*/
        /*width: 22000px;*/
    }

    .auto-column-size-table table {
        table-layout: auto;
    }
    .auto-column-size-table table colgroup col {
        display: none;
    }

    .scale {
        color: #2d8cf0;
    }

    .scale:hover {
        cursor: pointer;
    }
</style>
