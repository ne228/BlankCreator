<template>
  <v-form ref="form" v-model="valid" lazy-validation>
    <v-container class="form-container">
      <!-- Личные данные -->
      <v-card-title align="center">Редактирование справки {{ form.id }}</v-card-title>
      <v-card class="mb-4">
        <v-card-title>Личные данные</v-card-title>
        <v-card-text>
          <v-row>
            <v-col cols="12" md="6">
              <v-text-field
                v-model="form.rank"
                :rules="rules.required"
                label="Звание"
                required
              ></v-text-field>
            </v-col>
            <v-col cols="12" md="6">
              <v-text-field
                v-model="form.name"
                :rules="rules.required"
                label="Фамили И.О."
                required
              ></v-text-field>
            </v-col>
            <v-col cols="12" md="6">
              <v-text-field
                v-model="form.dateBirth"
                :rules="rules.dateFormat"
                label="Дата рождения (dd.MM.yyyy)"
                placeholder="dd.MM.yyyy"
                @input="validateDateInput('dateBirth', $event)"
              ></v-text-field>
            </v-col>
            <v-col cols="12" md="6">
              <v-text-field
                v-model="form.duty"
                :rules="rules.required"
                label="Должность (в родительном падеже)"
                required
              ></v-text-field>
            </v-col>
          </v-row>
        </v-card-text>
      </v-card>

      <!-- Зачисление и окончание -->
      <v-card class="mb-4">
        <v-card-title>Информация о службе</v-card-title>
        <v-card-text>
          <v-row>
            <v-col cols="12" md="6">
              <v-text-field
                v-model="form.trm"
                :rules="rules.required"
                label="Срок обучения"
                required
              ></v-text-field>
            </v-col>
            <v-col cols="12" md="6">
              <v-text-field
                v-model="form.dateEnd"
                :rules="rules.required"
                label="Год окончания"
              ></v-text-field>
            </v-col>
          </v-row>
        </v-card-text>
      </v-card>

      <!-- Местоположение -->
      <v-card class="mb-4">
        <v-card-title>Информация о месте требования справки</v-card-title>
        <v-card-text>
          <v-row>
            <v-col cols="12" md="6">
              <v-text-field
                v-model="form.place"
                :rules="rules.required"
                label="Место назначения справки"
                required
              ></v-text-field>
            </v-col>
            <v-col cols="12" md="6">
              <v-text-field
                v-model="form.town"
                :rules="rules.required"
                label="Город"
                required
              ></v-text-field>
            </v-col>
          </v-row>
        </v-card-text>
      </v-card>
      <p v-for="err in error" class="red--text">{{ err.defaultMessage }}</p>
      <v-row cols="12">
        <v-col align="center">
          <v-btn variant="outlined" color="red" @click="back">Назад к хранилищу</v-btn>
        </v-col>
        <v-col align="center">
          <v-btn color="primary" @click="submit">Сохранить</v-btn>
        </v-col>
      </v-row>
    </v-container>

    <v-snackbar v-model="snackbar.show" :timeout="3000" color="success">
      <div align="center" class="d-flex flex-column">
        <div>{{ snackbar.message }}</div>
        <v-btn class="mt-3" color="white" @click="snackbar.show = false">Закрыть</v-btn>
      </div>
    </v-snackbar>
  </v-form>
</template>

<script>
import { post, getData } from '@/services/apiService'

export default {
  data() {
    const testData = false
    return {
      id: '',
      valid: false,
      form: {
        id: '',
        hubId: '',
        rank: testData ? 'рядовой полиции' : '',
        name: testData ? 'Иванов Иван Иванович' : '',
        dateBirth: testData ? '27.05.2001' : '',
        duty: testData ? 'курсанта' : '',
        datePr: testData ? '01.09.2019' : '',
        numPr: testData ? '515-12' : '',
        dateEnrollment: testData ? '01.09.2025' : '',
        trm: testData ? '5' : '',
        dateEnd: testData ? '01.09.2025' : '',
        place: testData ? 'МФЦ Благовещенский' : '',
        town: testData ? 'г. Благовещенск' : ''
      },
      rules: {
        required: [(v) => !!v || 'Это поле обязательно'],
        dateFormat: [
          (v) =>
            /^(0[1-9]|[12][0-9]|3[01])\.(0[1-9]|1[0-2])\.\d{4}$/.test(v) ||
            'Неверный формат даты (dd.MM.yyyy)'
        ]
      },
      snackbar: {
        show: false,
        message: 'Справка сохранена'
      }
    }
  },
  created() {
    this.requestData()
  },
  methods: {
    validateDateInput(field, event) {
      const regex = /^\d{0,2}(\.\d{0,2})?(\.\d{0,4})?$/
      const input = event.target.value

      if (!regex.test(input)) {
        this.form[field] = input.slice(0, -1)
      }
    },
    back() {
      console.log('hubID', this.$route.params)
      this.$router.push(`/hub/${this.$route.params.hubId}`)
    },
    async requestData() {
      try {
        const response = await getData(`blank/${this.$route.params.blankId}`)
        // this.form = response.data;
        console.log('getBlankInfo', response)
        this.form = response.data
      } catch (error) {
        this.error = error.response.data.errors || 'Произошла ошибка.'
      }
    },
    async submit() {
      if (this.$refs.form.validate() && this.$refs.form.isValid) {
        console.log('Данные отправлены:', this.form)
        this.form.hubId = this.$route.params.hubId
        try {
          const response = await post(`blank/edit`, this.form)
          console.log('Response:', response)
          this.snackbar.show = true
          this.error = null
        } catch (error) {
          this.error = error.response.data.errors || 'Произошла ошибка.'
        }
      }
    }
  }
}
</script>

<style scoped>
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

.mb-4 {
  margin-bottom: 20px;
}
</style>
