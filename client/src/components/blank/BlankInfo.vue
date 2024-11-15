<template>
  <v-container class="form-container" v-if="data != null">
    <!-- Личные данные -->

    <v-card class="mb-4">
      <h3 align="center">Данные о справке {{ data.id }}</h3>
      <v-card-text>
        <v-list>
          <v-list-item>
            <v-list-item-content>
              <div><strong>Создатель справки:</strong> {{ data.user.username }}</div>
            </v-list-item-content>
          </v-list-item>
          <v-list-item>
            <v-list-item-content>
              <div><strong>Дата создания справки:</strong> {{ data.dateCreate }}</div>
            </v-list-item-content>
          </v-list-item>
          <v-list-item>
            <v-list-item-content>
              <div><strong>Статус справки:</strong> {{ data.blankStatus }}</div>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-card-text>
    </v-card>
    <v-card class="mb-4">
      <v-card-title>Личные данные</v-card-title>
      <v-card-text>
        <v-list>
          <v-list-item>
            <v-list-item-content>
              <div><strong>Пользователь:</strong> {{ data.user.username }}</div>
            </v-list-item-content>
          </v-list-item>
          <v-list-item>
            <v-list-item-content>
              <div><strong>ФИО:</strong> {{ data.name }}</div>
            </v-list-item-content>
          </v-list-item>
          <v-list-item>
            <v-list-item-content>
              <div><strong>Дата рождения:</strong> {{ data.dateBirth }}</div>
            </v-list-item-content>
          </v-list-item>
          <v-list-item>
            <v-list-item-content>
              <div><strong>Должность:</strong> {{ data.duty }}</div>
            </v-list-item-content>
          </v-list-item>
          <v-list-item>
            <v-list-item-content>
              <div><strong>Звание:</strong> {{ data.rank }}</div>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-card-text>
    </v-card>

    <!-- Информация о приказе -->
    <v-card class="mb-4">
      <v-card-title>Информация о приказе</v-card-title>
      <v-card-text>
        <v-list>
          <v-list-item>
            <v-list-item-content class="text-break">
              <div><strong>Дата приказа о назначении на должность:</strong> {{ data.datePr }}</div>
            </v-list-item-content>
          </v-list-item>
          <v-list-item>
            <v-list-item-content>
              <div><strong>Номер приказа о назначении на должность:</strong> {{ data.numPr }}</div>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-card-text>
    </v-card>

    <!-- Информация о службе -->
    <v-card class="mb-4">
      <v-card-title>Информация о службе</v-card-title>
      <v-card-text>
        <v-list>
          <v-list-item>
            <v-list-item-content>
              <div><strong>Дата начала обучения:</strong> {{ data.dateEnrollment }}</div>
            </v-list-item-content>
          </v-list-item>
          <v-list-item>
            <v-list-item-content>
              <div><strong>Лет обучения:</strong> {{ data.trm }}</div>
            </v-list-item-content>
          </v-list-item>
          <v-list-item>
            <v-list-item-content>
              <div><strong>Дата окончания учебы:</strong> {{ data.dateEnd }}</div>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-card-text>
    </v-card>

    <!-- Местоположение -->
    <v-card class="mb-4">
      <v-card-title>Информация о месте назначения справки</v-card-title>
      <v-card-text>
        <v-list>
          <v-list-item>
            <v-list-item-content>
              <div><strong>Место назначения справки:</strong> {{ data.place }}</div>
            </v-list-item-content>
          </v-list-item>
          <v-list-item>
            <v-list-item-content>
              <div><strong>Населенный пункт:</strong> {{ data.town }}</div>
            </v-list-item-content>
          </v-list-item>
          <v-list-item>
            <v-list-item-content>
              <div><strong>Цель справки:</strong> {{ data.reason }}</div>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-card-text>
    </v-card>
    <v-row cols="12">
      <v-col align="center">
        <v-btn variant="outlined" color="red" @click="back">Назад к хранилищу</v-btn>
      </v-col>
      <v-col align="center">
        <v-btn color="primary" @click="edit">Редактировать</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import router from '@/router'
import { getData } from '@/services/apiService'
import HubService from '@/services/hubApi'

export default {
  name: 'BlankInfo',

  data() {
    return {
      hubService: new HubService(),
      id: null,
      data: null
    }
  },
  mounted() {
    this.id = this.$route.params.blankId
    console.log('ID из URL:', this.id)
    this.requestData()
  },
  methods: {
    back() {
      let id = this.$route.params.hubId
      this.$router.push(`/hub/${id}`)
    },
    edit() {
      let blankId = this.$route.params.blankId
      let hubId = this.$route.params.hubId
      this.$router.push(`/hub/${hubId}/blank/edit/${blankId}`)
    },
    async requestData() {
      try {
        const response = await this.hubService.getBlankById(this.id)
        console.log('getBlankInfo', response.data)
        this.data = response.data || []
      } catch (error) {
        console.error('Ошибка при получении данных', error)
      }
    }
  }
}
</script>

<style scoped>
.card {
  border: none;
  border-radius: 10px;
  width: 100%;
  /* Make the card full width */
  max-width: 800px;
  /* Optional: set a max width for larger screens */
  padding: 20px;
  background: linear-gradient(135deg, #ffffff, #f9f9f9);
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s;
  margin: 0 auto;
  /* Center the card within its container */
}

.card:hover {
  transform: translateY(-5px);
}

.card h3 {
  font-size: 20px;
  color: #333;
  margin-bottom: 15px;
  text-align: center;
}

.card ul {
  list-style: none;
  padding: 0;
}

.card li {
  margin-bottom: 12px;
  display: flex;
  justify-content: space-between;
  padding: 8px;
  border-bottom: 1px solid #eaeaea;
}

.card li:last-child {
  border-bottom: none;
  /* Remove border from the last item */
}

.card li strong {
  color: #555;
}

.card li span {
  color: #777;
  text-align: right;
}

.form-container {
  padding: 20px;
  /* background-color:; */
  margin-top: 5px;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.v-card {
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}
</style>
