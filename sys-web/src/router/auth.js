import router from './index'
import { getToken } from '../utils/cookie'
import store from '@/store'
import { Message, MessageBox } from 'element-ui'
import NProgress from 'nprogress'

// 白名单列表
const whiteList = ['/login']

NProgress.configure({ showSpinner: false })

router.beforeEach((to, from, next) => {
  if (getToken()) {
    // 已登录且要跳转的是登录页
    if (to.path === '/login') {
      router.options.isAddDynamicMenuRoutes = false
      next({ path: '/' })
    } else {
      if (router.options.isAddDynamicMenuRoutes) {
        next()
      } else {
        const param = {
          appId: window.CONFIG.SYSTEM_APP_ID
        }
        // 获取用户信息
        store.dispatch('GetInfo', param).then(res => {
          store.dispatch('GetMenuInfo', res.id).then(accessRoutes => {
            if (!accessRoutes.length) {
              MessageBox.alert('该用户尚未授权，请先授权', '未授权提醒', {
                confirmButtonText: '确定',
                callback: action => {
                  store.dispatch('FedLogOut').then(() => {
                    next(`/login`)
                  })
                }
              })
              return false
            }
            // 根据roles权限生成可访问的路由表
            console.log(accessRoutes)
            router.addRoutes(accessRoutes) // 动态添加可访问路由表
            router.options.isAddDynamicMenuRoutes = true
            next({ ...to, replace: true }) // hack方法 确保addRoutes已完成
          })
        }).catch((err) => {
          store.dispatch('FedLogOut').then(() => {
            Message.error(err)
            next({ path: '/' })
          })
        })
      }
    }
  } else {
    router.options.isAddDynamicMenuRoutes = false
    // 在免登录白名单，直接进入
    if (whiteList.includes(to.path)) {
      next()
    } else {
      // 否则重定向到登录页
      next(`/login`)
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})
