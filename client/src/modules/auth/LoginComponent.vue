<template>
  <v-row justify="center">
    <v-col>
      <p v-for="err in error" class="red--text"> {{ err.defaultMessage }}</p>
      <div class="d-flex flex-column">
        <v-card-title align="center">
          <v-icon left>mdi-login</v-icon>
          <span class="ml-2">Вход</span>
        </v-card-title>

        <!-- Блок с формой входа как гость -->
        <v-card-title class="">Вход как гость</v-card-title>
        <v-card-text class="flex-grow-1">
          <v-form @submit.prevent="guest_login">
            <v-text-field v-model="guest.username" label="Имя пользователя" required></v-text-field>
            <v-btn type="submit" color="primary">Войти как гость</v-btn>
          </v-form>
          <p v-if="guest_error" class="red--text">{{ guest_error }}</p>
        </v-card-text>

        <!-- Блок с формой входа -->
        <v-card-title class="mt-2">Вход</v-card-title>
        <v-card-text class="flex-grow-1">
          <v-form @submit.prevent="login">
            <v-text-field v-model="user.username" label="Имя пользователя" required></v-text-field>
            <v-text-field
              v-model="user.password"
              label="Пароль"
              type="password"
              required
            ></v-text-field>
            <v-btn type="submit" color="primary">Войти</v-btn>
          </v-form>
          
        </v-card-text>

        <v-divider></v-divider>

        <!-- Дополнительные действия -->
        <v-card-actions class="mt-auto d-flex flex-wrap">
          <RouterLink to="/register">
            <v-btn>
              <p>Регистрация</p>
            </v-btn>
          </RouterLink>
          <!-- <v-btn @click="forgotPassword" color="link">Забыли пароль?</v-btn> -->
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
        password: ''
      },
      guest: {
        username: ''
      },
      error: '',
      guest_error: ''
    }
  },
  methods: {
    async login() {
      try {
        await authService.login(this.user)
        const redirectPath = localStorage.getItem('redirectPath')
        localStorage.removeItem('redirectPath') // Очищаем сохранённый путь
        this.$router.push(redirectPath || '/') // Перенаправляем
      } catch (error) {
       this.error = error.response.data.errors || 'Произошла ошибка.'
      }
    },
    async guest_login() {
      try {
        await authService.guest_login(this.guest)
        const redirectPath = localStorage.getItem('redirectPath')
        localStorage.removeItem('redirectPath') // Очищаем сохранённый путь
        this.$router.push(redirectPath || '/') // Перенаправляем
      } catch (error) {
         this.error = error.response.data.errors || 'Произошла ошибка.'
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
