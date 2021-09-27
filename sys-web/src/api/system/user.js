import request from '@/request'

// 分页查询用户列表
export function pageUser(query) {
  return request({
    url: '/sysUser/pageListVo',
    method: 'get',
    params: query
  })
}

// 保存用户
export function saveUser(data) {
  return request({
    url: '/sysUser/save',
    method: 'post',
    data: data
  })
}

// 删除用户
export function delUser(userId) {
  return request({
    url: '/sysUser/remove/' + userId,
    method: 'post'
  })
}

// 导出用户
export function exportUser(query) {
  return request({
    url: '/system/user/export',
    method: 'get',
    params: query
  })
}

// 用户密码重置
export function resetPassword(userId) {
  return request({
    url: '/sysUser/resetPassword/' + userId,
    method: 'post'
  })
}

// 修改密码
export function updatePassword(data) {
  return request({
    url: '/sysUser/modifyPassword',
    method: 'post',
    data: data
  })
}

// 用户头像上传
export function uploadAvatar(data) {
  return request({
    url: '/system/user/profile/avatar',
    method: 'post',
    data: data
  })
}

// 下载用户导入模板
export function importTemplate() {
  return request({
    url: '/system/user/importTemplate',
    method: 'get'
  })
}

// 查询用户下机构
export function listUserOrg(query) {
  return request({
    url: '/sysOrg/listChildById',
    method: 'get',
    params: query
  })
}

// 保存用户关联机构
export function saveUserRelationOrg(data) {
  return request({
    url: '/sysUserOrg/saveBatch',
    method: 'post',
    data: data
  })
}

// 查询用户关联科室
export function listUserRelationDept(query) {
  return request({
    url: '/sysUserOrgDept/listByUserId',
    method: 'get',
    params: query
  })
}

// 保存用户关联科室
export function saveUserRelationDept(data) {
  return request({
    url: '/sysUserOrgDept/saveBatch',
    method: 'post',
    data: data
  })
}

// 查询用户关联角色
export function listUserRelationRole(query) {
  return request({
    url: '/sysUserRole/listByUserId',
    method: 'get',
    params: query
  })
}

// 保存用户关联角色
export function saveUserRelationRole(data) {
  return request({
    url: '/sysUserRole/saveBatch',
    method: 'post',
    data: data
  })
}

// 复制用户角色
export function copyUserRole(data) {
  return request({
    url: '/sysUserRole/copyUserRole',
    method: 'post',
    data: data
  })
}
