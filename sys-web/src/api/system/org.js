import request from '@/request'

// 查询机构
export function listOrg(query) {
  return request({
    url: '/sysOrg/getTreeData',
    method: 'get',
    params: query
  })
}

// 保存机构
export function saveOrg(data) {
  return request({
    url: '/sysOrg/save',
    method: 'post',
    data: data
  })
}

// 删除机构
export function delOrg(orgId) {
  return request({
    url: '/sysOrg/remove/' + orgId,
    method: 'get'
  })
}
