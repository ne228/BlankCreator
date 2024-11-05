<template>
  <v-row justify="center">
    <v-col>
      <div class="d-flex flex-column" min-height="400px">
        <v-card-title>
          <v-icon left>mdi-account-plus</v-icon>
          <span class="ml-2">Регистрация</span>
        </v-card-title>

        <!-- Блок с формой регистрации -->
        <v-card-subtitle class="mt-2">Создайте учетную запись</v-card-subtitle>
        <v-card-text class="flex-grow-1">
          <v-form @submit.prevent="register">
            <v-text-field type="" v-model="user.username" label="Имя пользователя" required></v-text-field>
            <v-text-field  v-model="user.email" label="Электронная почта" required></v-text-field>
            <v-text-field
              v-model="user.password"
              label="Пароль"
              type="password"
              required
            ></v-text-field>
        
            <v-text-field
              v-model="user.retypePassword"
              label="Повторите пароль"
              type="password"
              required
            ></v-text-field>
            <v-btn type="submit" color="primary">Зарегистрироваться</v-btn>
          </v-form>
          <p class="red--text" v-for="err in error" >
              {{ err.defaultMessage }}
          </p>

        </v-card-text>

        <v-divider></v-divider>

        <!-- Дополнительные действия -->
        <v-card-actions class="mt-auto">
          <RouterLink to="/login">
            <v-btn>
              <p>Уже есть аккаунт? Войти</p>
            </v-btn>
          </RouterLink>
        </v-card-actions>
      </div>
    </v-col>
  </v-row>
</template>

<script>
import authService from '@/services/authService'

export default {
  data() {
    return {
      user: {
        username: '',
        email: '',
        password: '',
        retypePassword: ''
      },
      error: ''
    }
  },
  methods: {
    async register() {
      try {
        console.log(this.user);
        await authService.register(this.user)

        await authService.login(this.user)
        const redirectPath = localStorage.getItem('redirectPath')
        localStorage.removeItem('redirectPath') // Очищаем сохранённый путь
        this.$router.push(redirectPath || '/') // Перенаправляем
      } catch (error) {
        console.log(error);
        this.error = error.response.data.errors || [{defaultMessage: error.response.data.message}] || 'Произошла ошибка.'
      }
    }
  }
}
</script>

<style>
.red--text {
  color: red;
}
</style>
