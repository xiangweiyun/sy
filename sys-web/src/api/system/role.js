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

// 分页查询关联用户列表
export function pageRelationUser(query) {
  return request({
    url: '/sysUser/pageListVoByRoleId',
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

// 查询角色关联菜单列表
export function listRoleMenu(roleId) {
  return request({
    url: '/sysRoleMenu/listByRoleId',
    method: 'get',
    params: { roleId: roleId }
  })
}

// 角色授权菜单
export function saveBatchRoleMenu(data) {
  return request({
    url: '/sysRoleMenu/saveBatch',
    method: 'post',
    data: data
  })
}
