<template>
  <v-container class="pa-4" fluid>
    <v-row justify="center">
      <v-col cols="12" sm="8" md="6" lg="4">
        <!-- Error Messages -->
        <v-alert v-if="error.length" type="error" dismissible>
          <v-list dense>
            <v-list-item-group>
              <v-list-item v-for="err in error" :key="err.defaultMessage">
                <v-list-item-content>
                  <v-list-item-title class="red--text">{{ err.defaultMessage }}</v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list-item-group>
          </v-list>
        </v-alert>

        <v-card>
          <v-card-title class="headline text-center">
            <v-icon left>mdi-login</v-icon>
            Вход
          </v-card-title>

          <!-- Guest Login Section -->
          <v-card-subtitle class="text-center">Вход как гость</v-card-subtitle>
          <v-card-text>
            <v-form @submit.prevent="guest_login">
              <v-text-field
                v-model="guest.username"
                label="Имя пользователя"
                outlined
                dense
                required
              ></v-text-field>
              <v-btn type="submit" color="primary" block>Войти как гость</v-btn>
            </v-form>
            <v-alert v-if="guest_error" type="error" dismissible>{{ guest_error }}</v-alert>
          </v-card-text>

          <v-divider></v-divider>

          <!-- Regular Login Section -->
          <v-card-subtitle class="text-center">Вход</v-card-subtitle>
          <v-card-text>
            <v-form @submit.prevent="login">
              <v-text-field
                v-model="user.username"
                label="Имя пользователя"
                outlined
                dense
                required
              ></v-text-field>
              <v-text-field
                v-model="user.password"
                label="Пароль"
                type="password"
                outlined
                dense
                required
              ></v-text-field>
              <v-btn type="submit" color="primary" block>Войти</v-btn>
            </v-form>
          </v-card-text>

          <v-divider></v-divider>

          <!-- Additional Actions (Registration, Password Recovery) -->
          <v-card-actions class="d-flex justify-center">
            <RouterLink to="/register">
              <v-btn text color="primary">Регистрация</v-btn>
            </RouterLink>
            <!-- Uncomment below for Forgot Password -->
            <!-- <v-btn text color="primary">Забыли пароль?</v-btn> -->
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
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
      error: [],
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
        this.error = error.response.data.errors || ['Произошла ошибка.']
      }
    },
    async guest_login() {
      try {
        await authService.guest_login(this.guest)
        const redirectPath = localStorage.getItem('redirectPath')
        localStorage.removeItem('redirectPath') // Очищаем сохранённый путь
        this.$router.push(redirectPath || '/') // Перенаправляем
      } catch (error) {
        this.guest_error = error.response.data.errors || 'Произошла ошибка.'
      }
    }
  }
}
</script>

<style scoped>
.v-card {
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.v-btn {
  font-weight: bold;
}

.v-text-field {
  margin-bottom: 16px;
}

.v-divider {
  margin: 16px 0;
}

.v-card-subtitle {
  font-weight: 500;
}

.red--text {
  color: red !important;
}

.v-alert {
  margin-bottom: 16px;
}

@media (max-width: 600px) {
  .v-card {
    margin: 0 16px;
  }
}
</style>
