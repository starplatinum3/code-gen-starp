
import request from "../utils/request";

export function selectAll(data) {
  return request({
    url: '/#实体名#/selectAll',
    data: data,
    method: 'POST'
  });
}


export function selectPage(data,pageNumber,pageSize) {
  return request({
    url: `/#实体名#/selectPage?pageNumber=${pageNumber}&pageSize=${pageSize}`,
    data: data,
    method: 'POST'
  });
}


export function create(data) {
  return request({
    url: '/#实体名#',
    data: data,
    method: 'POST'
  })
}


export function update(data) {
  return request({
    url: '/#实体名#',
    data: data,
    method: 'PUT'
  })
}


export function deleteOne(wid) {
  return request({
    url: '/#实体名#/' + wid,
    method: 'DELETE'
  })
}


export function getInfo(wid) {
  return request({
    url: '/#实体名#/' + wid,
    method: 'GET'
  })
}



export function batchInsert(data) {
  return request({
    url: '/#实体名#/batchInsert',
    data: data,
    method: 'POST'
  })
}

export function batchDelete(data) {
  return request({
    url: '/#实体名#/batchDelete',
    data: data,
    method: 'POST'
  })
}

export function
/*
let  postData=  #jsonDefaultNull#
   */
/*
 postData : #jsonDefaultNull#
   */
save(postData){
//  axios.post(Common.baseUrl + "/#entityName#/save",postData)
//  axios.post(Common.drawUrl + "/#entityName#/save",postData)
  this.$axios.post( "/#entityName#/save",postData)
      .then((res) => {
    console.log("res");
  console.log(res);
  let  resultData= res.data.data
});
},
