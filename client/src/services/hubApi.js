import { da } from 'vuetify/lib/locale/index.mjs'
import apiService, { getData, post, put, del, downloadPost} from './apiService'
import hubApi from './hubApi'

class HubService {
  // HUBS
  async getHubs() {
    try {
      return await getData('hubs')
    } catch (error) {
      throw error
    }
  }

  async getHubById(id) {
    try {
      return await getData(`hubs/${id}`)
    } catch (error) {
      throw error
    }
  }

  async createHub(data) {
    try {
      return await post(`hubs`, data)
    } catch (error) {
      throw error
    }
  }

  async editHub(data) {
    try {
      return await put(`hubs`, data)
    } catch (error) {
      throw error
    }
  }

  // BLANKS
  async getBlanks() {
    try {
      return await getData('blanks')
    } catch (error) {
      throw error
    }
  }

  async createBlank(data) {
    try {
      return await post('blanks', data)
    } catch (error) {
      throw error
    }
  }

  async editBlank(data) {
    try {
      return await put('blanks', data)
    } catch (error) {
      throw error
    }
  }

  async getBlankById(id) {
    try {
      return await getData(`blanks/${id}`)
    } catch (error) {
      throw error
    }
  }

  async blankSuccess(id) {
    try {
      return await post(`blanks/${id}/success`)
    } catch (error) {
      throw error
    }
  }
  async blankReject(id) {
    try {
      return await post(`blanks/${id}/reject`)
    } catch (error) {
      throw error
    }
  }

  // TEMPLATES
  async getTemplates() {
    try {
      return await getData('templates')
    } catch (error) {
      throw error
    }
  }
  async templateImport(data) {
    try {
      return await post(`templates/import`, data)
    } catch (error) {
      throw error
    }
  }

  async deleteTemplate(id) {
    try {
      return await del(`templates/${id}`)
    } catch (error) {
      throw error
    }
  }

  // PRINT
  async printBlank(id, data){
    try {
      return await downloadPost(`print/${id}`, data)
    } catch (error) {
      throw error
    }
  }
}

export default HubService
