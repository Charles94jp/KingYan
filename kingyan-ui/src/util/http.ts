import axios from 'axios'
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import router from '../router'
import elMessage from '@/util/el-message'

// 创建新的 axios 实例
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API,
  withCredentials: true, // send cookies when cross-domain requests
  timeout: 15000 // request timeout
})

console.log(process.env.VUE_APP_BASE_API)

// // 请求拦截
// service.interceptors.request.use(
//   config => {
//     // eslint-disable-next-line @typescript-eslint/ban-ts-comment
//     // @ts-ignore
//     config.headers['X-Token'] = getToken() // 设置请求头
//     return config
//   },
//   error => {
//     return Promise.reject(error)
//   }
// )

// 响应拦截器
service.interceptors.response.use(function (response) {
  // 2xx 范围内的状态码都会触发该函数。
  // 对响应数据做点什么
  return response
}, function (error) {
  // 超出 2xx 范围的状态码都会触发该函数。
  // 对响应错误做点什么
  if (error.response.status === 401) {
    elMessage.elMessage('请先登录', 'warning')
    router.push('/login')
  } else if (error.response.status === 403) {
    elMessage.elMessage('权限不足，拒绝访问', 'warning')
  } else {
    return Promise.reject(error)
  }
})

export default service
