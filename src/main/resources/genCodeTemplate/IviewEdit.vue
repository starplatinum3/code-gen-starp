<template>
    <div>
        <layout-edit-content title="#类名#Edit">
            <div slot="main">
                <#类名#Form :entityId="entityId" :type="type"></#类名#Form>
    </div>
    </layout-edit-content>

    <Table :columns="columns" :data="allList" ref="selection" >

    </Table>

    </div>
</template>

<script>
    import {getUserInfo} from "../../api/user";
    import LayoutEditContent from "../../components/layout/layout-edit-content";
    // import UserForm from "../../components/form/user-form";
    // import #类名#Form from "../../components/form/#实体名#-form";
    // import #类名#Form from "../../components/form/#实体名#Form";
    import #类名#Form from "../../components/form/#类名#Form";

    // console.log(1)
    // import {batchDelete, deleteOne, exportToWord,selectAll,selectPage} from "../../api/permission";
    import {batchDelete, deleteOne, exportToWord,selectAll} from "../../api/permission";
    import {update,create,selectPage} from "../../utils/request";
    export default {
        name: "#类名#Edit",
        components: {
            LayoutEditContent,
            #类名#Form
     },
    created() {
        // this.userId = this.$route.query.userId;
        // this.entityId =    this.$route.query.id;
        console.log( "this.entityId")
        console.log( this.entityId)
        if (!!this.entityId) {
            this.type = 'update';
        }

        this. selectPage();
    },
    methods:{
        // selectPage() {
        //     this.searchLoading = true;
        //     selectPage(this.select,this.select.pageNum,
        //         this.select.pageSize,this.tableName).then(res => {
        //         // this.writerList = res.data.data.list;
        //
        //         console.log("res")
        //         console.log(res)
        //         // this.allList = res.data.data.list;
        //         this.allList = res.data.data;
        //
        //         console.log( "this.allList")
        //         console.log( this.allList)
        //
        //         this.total = res.data.data.total;
        //         this.searchLoading = false;
        //     })
        // },

        selectPage() {
            this.searchLoading = true;
            selectPage(this.select,this.pageNum,
                this.pageSize,this.tableName).then(res => {
                // this.writerList = res.data.data.list;

                console.log("res")
                console.log(res)
                // this.allList = res.data.data.list;
                this.allList = res.data.data;

                console.log( "this.allList")
                console.log( this.allList)

                this.total = res.data.data.total;
                this.searchLoading = false;
            })
        },
        data() {
            return {
                // entityId:'',
                // entityId:this.$route.query.userId,
                pageNum:1,
                pageSize:10,
                entityId:this.$route.query.id,
                tableName:"#实体名#",
                total: 0,
                searchLoading: false,
                userId: '',
                type: 'create',
                select: #jsonDefaultNull#,

                columns: [
                    {
                        type: 'selection',
                        width: 60,
                        align: 'center'
                    },
                    {
                        type: 'index',
                        width: 60,
                        align: 'center'
                    },

                    #iViewColumnsRows#




                    {
                        title: '操作',
                        // slot: 'action',
                        fixed: 'right',
                        width: 240,
                        align: 'center'
                    }
                    ,
                ],
                allList:[],
            }
        },
    }
    }
</script>

<style scoped>

</style>
