import { downLoadXlsx } from '@/utils/downloadFile';
import request from 'umi-request';
import type { #className#Type, #className#ListParams } from './data.d';
import { paramsSortable } from '@/utils/utils';

// 查询岗位信息列表
export async function get#className#List(params?: any, sort?: any) {
  return request('/api/system/#entityName#/list', {
    params: paramsSortable(params, sort),
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  });
}

// 查询岗位信息详细
export function get#className#(id: number) {
  return request(`/api/system/#entityName#/${id}`, {
    method: 'GET',
  });
}

// 新增岗位信息
export async function add#className#(params: #className#Type) {
  return request('/api/system/#entityName#', {
    method: 'POST',
    data: params,
  });
}

// 修改岗位信息
export async function update#className#(params: #className#Type) {
  return request('/api/system/#entityName#', {
    method: 'PUT',
    data: params,
  });
}

// 删除岗位信息
export async function remove#className#(ids: string) {
  return request(`/api/system/#entityName#/${ids}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  });
}

// 导出岗位信息
export function export#className#(params?: #className#ListParams) {
  return downLoadXlsx(`/api/system/#entityName#/export`, { params }, `#entityName#_${new Date().getTime()}.xlsx`);
}
