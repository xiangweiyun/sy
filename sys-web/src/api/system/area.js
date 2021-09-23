import request from '@/request'

// 查询区域
export function listArea() {
  return request({
    url: '/sysDistrict/listMain',
    method: 'get'
  })
}

// 查询子区域
export function listChildrenArea(areaId) {
  return request({
    url: '/sysDistrict/listChildren',
    method: 'get',
    params: { id: areaId }
  })
}

// 保存区域
export function saveArea(data) {
  return request({
    url: '/sysDistrict/save',
    method: 'post',
    data: data
  })
}

// 删除区域
export function delArea(areaId) {
  return request({
    url: '/sysDistrict/remove/' + areaId,
    method: 'get'
  })
}
