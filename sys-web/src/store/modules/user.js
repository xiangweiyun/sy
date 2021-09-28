import { login, logout, getUserInfo, getMenuInfo } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/cookie'
import { constantRoutes } from '@/router'
import Layout from '@/layout/index'

const user = {
  state: {
    token: getToken(),
    userInfo: {},
    roles: [],
    menus: [],
    routes: [],
    addRoutes: []
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_USER_INFO: (state, userInfo) => {
      state.userInfo = userInfo
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_MENUS: (state, menus) => {
      state.menus = menus
    },
    SET_ROUTES: (state, routes) => {
      state.addRoutes = routes
      state.routes = constantRoutes.concat(routes)
    }
  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {
      const username = userInfo.username.trim()
      const password = userInfo.password
      const data = {
        userName: username,
        passWord: password
      }
      return new Promise((resolve, reject) => {
        login(data).then(res => {
          setToken(res.authData.token)
          commit('SET_TOKEN', res.authData.token)
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 获取用户信息
    GetInfo({ commit }, data) {
      return new Promise((resolve, reject) => {
        getUserInfo(data).then(res => {
          commit('SET_USER_INFO', res)
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 获取菜单信息(生成路由)
    GetMenuInfo({ commit }, userId) {
      return new Promise((resolve, reject) => {
        getMenuInfo(userId).then(res => {
          let accessedRoutes = []
          if (res && res.length) {
            const menuData = fnAddDynamicMenus(res)
            commit('SET_MENUS', menuData)
            accessedRoutes = filterAsyncRouter(menuData)
            accessedRoutes.push({ path: '*', redirect: '/404', hidden: true })
            commit('SET_ROUTES', accessedRoutes)
          }
          resolve(accessedRoutes)
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 退出系统
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout().then(() => {
          commit('SET_TOKEN', '')
          commit('SET_USER_INFO', [])
          removeToken()
          sessionStorage.clear()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken()
        sessionStorage.clear()
        resolve()
      })
    }
  }
}

export default user

/**
 * 添加动态(菜单)路由
 * @param {*} menuList 菜单列表
 */
function fnAddDynamicMenus(menuList = [], parentPath) {
  if (!menuList || menuList.length === 0) {
    return false
  }
  const menuData = []
  menuList.filter(element => {
    const menuType = element.menuType
    if (menuType !== 'C') {
      return false
    }
    if (!element.url) {
      return false
    }
    let path = ''
    let component = ''
    const name = element.url.indexOf('/') !== -1 ? element.url.split('/') : []
    if (element.children != null && element.children && element.children.length) {
      path = name.length ? element.url : '/' + element.url
      component = 'Layout'
    } else {
      path = name.length ? name[1] : element.url
      if (parentPath) {
        const prentComponent = parentPath.indexOf('/') !== -1 ? parentPath.split('/') : []
        component = (prentComponent ? prentComponent[1] : parentPath) + '/' + path
      } else {
        component = path = name.length ? element.url : '/' + element.url
      }
    }
    const menu = {
      name: name.length ? name[1] : element.url,
      path: path,
      component: component,
      meta: {
        title: element.name,
        icon: element.icon
      }
    }
    if (element.children != null && element.children && element.children.length) {
      menu.children = fnAddDynamicMenus(element.children, path)
    }
    menuData.push(menu)
  })
  return menuData
}

// 遍历后台传来的路由字符串，转换为组件对象
/**
 *
 * @param {*} parent 父级路由
 * @param {*} asyncRouterMap 路由map
 * @returns
 */
function filterAsyncRouter(asyncRouterMap) {
  return asyncRouterMap.filter(route => {
    if (route.component) {
      // Layout组件特殊处理
      if (route.component === 'Layout') {
        route.component = Layout
      } else {
        route.component = loadView(route.component)
      }
    }
    if (route.children != null && route.children && route.children.length) {
      route.children = filterAsyncRouter(route.children)
    }
    return true
  })
}

export const loadView = (view) => { // 路由懒加载
  return (resolve) => require([`@/views/${view}`], resolve)
}
