import request from '../request'

// 登录
export function login(data) {
  return request({
    url: `${window.CONFIG.LOGIN_URL}/auth/auth`,
    method: 'post',
    data
  })
}

// 获取用户信息
export function getUserInfo(params) {
  return request({
    url: '/user/information',
    method: 'get',
    params
  })
}

// 获取菜单信息
export function getMenuInfo(userId) {
  return request({
    url: '/sysMenu/listTreeDataByAppIdAndUserId',
    method: 'get',
    params: {
      userId: userId,
      appId: window.CONFIG.SYSTEM_APP_ID
    }
  })
}

// 退出方法
export function logout() {
  return request({
    url: `${window.CONFIG.LOGIN_URL}/auth/logout`,
    method: 'post'
  })
}

