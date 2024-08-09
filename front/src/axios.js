import axios from 'axios'

// Axios 인스턴스 생성
const axiosInstance = axios.create({
  baseURL: process.env.VUE_APP_BACK_END_URL
})

// 요청 인터셉터 설정
axiosInstance.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

export default axiosInstance
