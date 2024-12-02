<template>
  <v-form ref="form" v-model="valid" lazy-validation>
    <!-- Личные данные -->
    <v-card class="mb-4">
      <v-card-title>Личные данные</v-card-title>
      <v-card-text>
        <v-row>
          <v-col cols="12" md="6">
            <v-text-field
              v-model="form.rank"
              :rules="rules.required"
              label="Звание"
              hint="Например: рядовой полиции"
              required
            ></v-text-field>
          </v-col>
          <v-col cols="12" md="6">
            <v-text-field
              v-model="form.name"
              :rules="rules.required"
              label="Фамили И.О."
              hint="Например: Иванов И.И."
              required
            ></v-text-field>
          </v-col>
          <v-col cols="12" md="6">
            <v-text-field
              v-model="form.dateBirth"
              :rules="rules.dateFormat"
              label="Дата рождения (dd.MM.yyyy)"
              placeholder="dd.MM.yyyy"
              hint="Например: 12.01.2004"
              @input="validateDateInput('dateBirth', $event)"
            ></v-text-field>
          </v-col>
          <v-col cols="12" md="6">
            <v-text-field
              v-model="form.duty"
              :rules="rules.required"
              label="Должность (в родительном падеже)"
              hint="Например: курсанта"
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
              type="number"
              v-model="form.trm"
              :rules="rules.required"
              label="Срок обучения (лет)"
              hint="Например: 5"
              required
            ></v-text-field>
          </v-col>
          <v-col cols="12" md="6">
            <v-text-field
              type="number"
              v-model="form.dateEnd"
              :rules="[rules.required]"
              hint="Например: 2026"
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
              label="Место требования"
              hint="Например: МФЦ Центрального района"
              required
            ></v-text-field>
          </v-col>
          <v-col cols="12" md="6">
            <v-text-field
              v-model="form.town"
              :rules="rules.required"
              label="Город"
              hint="Например :г. Санкт-Петербург"
              required
            ></v-text-field>
          </v-col>

          <v-col cols="12" md="6">
            <v-text-field
              v-model="form.reason"
              :rules="rules.required"
              label="Для каких целей справка?"
              hint="Например: в налоговый учет"
              required
            ></v-text-field>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>

    <v-card class="mb-4">
      <v-card-title>Шаблонизация</v-card-title>
      <v-card-text>
        <v-row>
          <v-col cols="12" md="6">
            <v-switch 
            color="primary" v-model="form.saveAsTemplate" label="Сохранить как шаблон"></v-switch>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
    <v-row cols="12">
      <v-col align="center">
        <v-btn variant="outlined" color="red" @click="back">Назад к хранилищу</v-btn>
      </v-col>
      <v-col align="center">
        <v-btn color="success" @click="submit">Добавить справку</v-btn>
      </v-col>
    </v-row>

    <p v-for="err in error" class="text--red">{{ err.defaultMessage }}</p>

    <v-snackbar v-model="snackbar.show" :timeout="3000" color="success">
      {{ snackbar.message }}
      <v-btn color="white" @click="snackbar.show = false">Закрыть</v-btn>
    </v-snackbar>
  </v-form>
</template>

<script>
import { post } from '@/services/apiService'
import HubService from '@/services/hubApi';

export default {
  data() {
    const testData = false
    return {
      valid: false,
      hubService: new HubService(),
      form: {
        hubId: '',
        rank: testData ? 'рядовой полиции' : '',
        name: testData ? 'Иванов Иван Иванович' : '',
        dateBirth: testData ? '27.05.2001' : '',
        duty: testData ? 'курсанта' : '',
        trm: testData ? '5' : '',
        dateEnd: testData ? '2030' : '',
        place: testData ? 'МФЦ Благовещенский' : '',
        town: testData ? 'г. Благовещенск' : '',
        reason: testData ? 'В налоговый учет' : '',
        saveAsTemplate: testData ? true : false
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
        message: ''
      },
      error: null
    }
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
      let id = this.$route.params.hubId
      this.$router.push(`/hub/${id}`)
    },
    async submit() {
      if (this.$refs.form.validate() && this.$refs.form.isValid) {
        console.log('Данные отправлены:', this.form)
        this.form.hubId = this.$route.params.hubId
        try {
          const response = await this.hubService.createBlank(this.form);
          console.log('Response:', response.id)
          this.$router.push(`/hub/${this.$route.params.hubId}/blank/${response.id}`)
        } catch (error) {
          this.error = error.response.data.errors || 'Произошла ошибка.'
          console.error('Error sending data:', error)
        }
      }
      console.log(this.$refs.form)
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
