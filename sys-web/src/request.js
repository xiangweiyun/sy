import axois from 'axios'
import { Message } from 'element-ui'
import { getToken } from './utils/cookie'

// 创建axios实例
const service = axois.create({
  baseURL: window.CONFIG.BASE_URL, // config.js 的 base_url
  timeout: 120000 // 请求超时时间
})

// request拦截器设置
service.interceptors.request.use(
  config => {
    if (getToken()) {
      config.headers['Authorization'] = 'Bearer ' + getToken() // 请求头设置自带 token
    }
    config.headers['Content-Type'] = 'application/json' // 请求的数据格式为 json
    return config
  },
  error => {
    console.log(error)
    Promise.reject(error)
  }
)

// response 拦截器设置
service.interceptors.response.use(
  response => {
    const data = response.data
    const success = data.success
    const message = data.message
    if (success) {
      return data.data
    } else {
      Message({
        type: 'error',
        message
      })
      return Promise.reject('error')
    }
  },
  error => {
    const code = error.response.data.status
    if (!code) {
      Message({
        type: 'error',
        message: '接口请求失败'
      })
    }
    return Promise.reject(error)
  }
)

export default service
