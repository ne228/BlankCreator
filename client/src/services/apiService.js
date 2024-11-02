import axios from 'axios'
import authService from './authService'

const BASE_URL = 'http://localhost:5007/api/hub'

const apiService = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  }
})

export const getUrl = () => {
  return BASE_URL
}

export const setAuthToken = () => {
  try {
    const user = JSON.parse(localStorage.getItem('user'))
    const token = user.accessToken
    if (token) {
      apiService.defaults.headers.common['Authorization'] = `Bearer ${token}`
    } else {
      delete apiService.defaults.headers.common['Authorization']
    }
    console.log('set auth')
  } catch (error) {
    console.log('Error in setAuthToken:', error)
    throw error
  }
}

export const getData = async (endpoint, data) => {
  var response = null
  try {
    console.log('API GET + ' + BASE_URL + '/' + endpoint + ' / ' + data)
    setAuthToken()
    response = await apiService.get(endpoint, data)

    return response
  } catch (error) {
    if (error.status == 401) authService.logout()
    console.error('Error API GET + ' + endpoint + ': ' + error.response.data.message)
    throw error
  }
}

export const post = async (endpoint, data) => {
  try {
    console.log('post:', endpoint)
    setAuthToken()
    const response = await apiService.post(endpoint, data)
    return response.data
  } catch (error) {
    if (error.status == 401) authService.logout()
    console.log('Error in post:', error)
    throw error
  }
}

export const downloadPost = async (endpoint, data) => {
  try {
    console.log('post:', endpoint)
    setAuthToken()
    const response = await apiService.post(endpoint, data, {
      responseType: 'blob' // для получения данных в виде blob
    })
    return response.data
  } catch (error) {
    if (error.status == 401) authService.logout()
    console.log('Error in post:', error)
    throw error
  }
}

export const put = async (endpoint, data) => {
  try {
    console.log('put:', endpoint)
    setAuthToken()
    const response = await apiService.put(endpoint, data)
    return response.data
  } catch (error) {
    if (error.status == 401) authService.logout()
    console.log('Error in put:', error)
    throw error
  }
}

export const del = async (endpoint) => {
  try {
    console.log('delete:', endpoint)
    setAuthToken()
    const response = await apiService.delete(endpoint)
    return response.data
  } catch (error) {
    if (error.status == 401) authService.logout()
    console.log('Error in delete:', error)
    throw error
  }
}

export default apiService
