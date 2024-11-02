import router from '@/router'
import axios from 'axios'

const API_URL = 'http://194.58.66.113:5007/api/auth/'

class AuthService {
  login(user) {
    return axios
      .post(API_URL + 'signin', {
        username: user.username,
        password: user.password
      })
      .then((response) => {
        if (response.data.accessToken) {
          localStorage.setItem('user', JSON.stringify(response.data))
        }
        return response.data
      })
  }

  guest_login(user) {
    return axios
      .post(API_URL + 'guest_signin', {
        username: user.username
      })
      .then((response) => {
        if (response.data.accessToken) {
          localStorage.setItem('user', JSON.stringify(response.data))
        }

        return response.data
      })
  }
  isAuth() {
    if (localStorage.getItem('user')) return true
    else return false
  }

  getCurrentUser() {
    const user = JSON.parse(localStorage.getItem('user'))
    return user
  }

  logout() {
    localStorage.removeItem('user')
    router.push('/login')
  }

  register(user) {
    return axios.post(API_URL + 'signup', {
      username: user.username,
      email: user.email,
      password: user.password
    })
  }
}

export default new AuthService()
