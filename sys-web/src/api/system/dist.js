import request from '@/request'

// 分页获取字典类型
export function pageDistType(query) {
  return request({
    url: '/sysDictType/pageSysDictType',
    method: 'get',
    params: query
  })
}

// 保存字典类型
export function saveDistType(data) {
  return request({
    url: '/sysDictType/save',
    method: 'post',
    data: data
  })
}

// 删除字典类型
export function delDistType(distTypeId) {
  return request({
    url: '/sysDictType/remove/' + distTypeId,
    method: 'get'
  })
}

// 根据字典类型获取字典项列表
export function listDistItem(query) {
  return request({
    url: '/sysDictData/listByDictType/' + query.dictType,
    method: 'get'
  })
}

// 保存字典项
export function saveDistItem(data) {
  return request({
    url: '/sysDictType/save',
    method: 'post',
    data: data
  })
}

// 删除字典项
export function delDistItem(distTypeId) {
  return request({
    url: '/sysDictType/remove/' + distTypeId,
    method: 'get'
  })
}
