import { post } from '@/utils/request'
// import #实体名#Api from "@/api/#实体名#";
let apiPreffix=`/api/admin/#实体名#`
export default {
    list: query => post(`/api/admin/education/subject/list`),
pageList: query => post('/api/admin/education/subject/page', query),
    edit: query => post('/api/admin/education/subject/edit', query),
    save: query => post(`${apiPreffix}/save`, query),
    saveAll: query => post(`${apiPreffix}/saveAll`, query),
    deleteBy: query => post(`${apiPreffix}/deleteBy`, query),
    selectPageEqual: query => post(`${apiPreffix}/selectPage/equal`, query),
    selectByExample: query => post(`${apiPreffix}/selectByExample`, query),
    selectPage: query => post(`${apiPreffix}/selectPage`, query),
    removeByIds: query => post(`${apiPreffix}/removeByIds`, query),
    selectPlusPage: query => post(`${apiPreffix}/selectPlusPage`, query),
    select: id => post('/api/admin/education/subject/select/' + id),
    deleteSubject: id => post('/api/admin/education/subject/delete/' + id)
}
