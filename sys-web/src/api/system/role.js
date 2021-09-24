import request from '@/request'

// 分页查询角色列表
export function pageRole(query) {
  return request({
    url: '/sysRole/pageSysRole',
    method: 'get',
    params: query
  })
}

// 查询角色列表
export function listRole(query) {
  return request({
    url: '/sysRole/listSysRole',
    method: 'get',
    params: query
  })
}

// 保存角色
export function saveRole(data) {
  return request({
    url: '/sysRole/save',
    method: 'post',
    data: data
  })
}

// 删除角色
export function delRole(roleId) {
  return request({
    url: '/sysRole/remove/' + roleId,
    method: 'post'
  })
}

// 查询角色权限tree
export function listRolePower(query) {
  return request({
    url: '/tob/role/getRolePower',
    method: 'get',
    params: query
  })
}

// 角色授权
export function saveRolePower(data) {
  return request({
    url: '/tob/role/saveRolePower',
    method: 'post',
    data: data
  })
}
