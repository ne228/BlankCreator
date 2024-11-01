<template>

  <v-row justify="center">
    <v-col>
      <v-card class="d-flex flex-column">
        <v-card-title>
          <v-icon left>mdi-login</v-icon>
          <span class="ml-2">Вход</span>
        </v-card-title>

        <!-- Блок с формой входа -->
        <v-card-subtitle class="mt-2">Вход</v-card-subtitle>
        <v-card-text class="flex-grow-1">
          <v-form @submit.prevent="login">
            <v-text-field v-model="user.username" label="Имя пользователя" required></v-text-field>
            <v-text-field v-model="user.password" label="Пароль" type="password" required></v-text-field>
            <v-btn type="submit" color="primary">Войти</v-btn>
          </v-form>
          <p v-if="error" class="red--text">{{ error }}</p>
        </v-card-text>

        <v-divider></v-divider>

        <!-- Блок с формой входа как гость -->
        <v-card-subtitle class="">Вход как гость</v-card-subtitle>
        <v-card-text class="flex-grow-1">
          <v-form @submit.prevent="guest_login">
            <v-text-field v-model="guest.username" label="Имя пользователя" required></v-text-field>
            <v-btn type="submit" color="primary">Войти как гость</v-btn>
          </v-form>
          <p v-if="guest_error" class="red--text">{{ guest_error }}</p>
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
      </v-card>
    </v-col>
  </v-row>

</template>

<script>
import authService from '@/services/authService';

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
      guest_error: '',
    };
  },
  methods: {
    async login() {
      try {
        await authService.login(this.user);
        const redirectPath = localStorage.getItem('redirectPath');
        localStorage.removeItem('redirectPath'); // Очищаем сохранённый путь
        this.$router.push(redirectPath || '/'); // Перенаправляем
      } catch (error) {
        this.error = error.response.data.message || 'Произошла ошибка.';
      }
    },
    async guest_login() {
      try {
        await authService.guest_login(this.guest);
        const redirectPath = localStorage.getItem('redirectPath');
        localStorage.removeItem('redirectPath'); // Очищаем сохранённый путь
        this.$router.push(redirectPath || '/'); // Перенаправляем
      } catch (error) {
        this.guest_error = error.response.data.message || 'Произошла ошибка.';
      }
    }
  }
};
</script>

<style>
.red--text {
  color: red;
}
</style>
