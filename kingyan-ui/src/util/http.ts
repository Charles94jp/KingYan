import axios from 'axios'

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

// // 响应 response 拦截器
// service.interceptors.response.use(response => {
//   if (response.status === 200) {
//     const data = response.data
//     return data
//   } else {
//     // eslint-disable-next-line @typescript-eslint/ban-ts-comment
//     // @ts-ignore
//     if (response.code === 302) {
//       // 响应超时，跳转到登录页
//     }
//   }
//   return response
// })

export default service
