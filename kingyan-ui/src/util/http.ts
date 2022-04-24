import axios from 'axios'
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import { ElMessage } from 'element-plus'
import router from '../router'

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
    ElMessage({
      message: '请先登录',
      type: 'warning'
    })
    router.push('/login')
  } else {
    return Promise.reject(error)
  }
})

export default service
