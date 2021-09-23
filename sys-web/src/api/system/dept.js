import request from '@/request'

// 查询部门
export function listDept(orgId) {
  return request({
    url: '/sysDept/listTreeByOrgId/' + orgId,
    method: 'get'
  })
}

// 保存部门
export function saveDept(data) {
  return request({
    url: '/sysDept/save',
    method: 'post',
    data: data
  })
}

// 删除部门
export function delDept(deptId) {
  return request({
    url: '/sysDept/remove/' + deptId,
    method: 'get'
  })
}
