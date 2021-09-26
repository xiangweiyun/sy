import request from '@/request'

// 查询菜单
export function listMenu() {
  return request({
    url: '/sysMenu/listTreeData',
    method: 'get'
  })
}

// 保存菜单
export function saveMenu(data) {
  return request({
    url: '/sysMenu/save',
    method: 'post',
    data: data
  })
}

// 删除菜单
export function delMenu(MenuId) {
  return request({
    url: '/sysMenu/remove/' + MenuId,
    method: 'post'
  })
}
