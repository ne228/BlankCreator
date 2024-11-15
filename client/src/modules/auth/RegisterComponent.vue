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
            <v-icon left>mdi-account-plus</v-icon>
            Регистрация
          </v-card-title>

          <!-- Registration Form -->
          <v-card-subtitle class="text-center">Создайте учетную запись</v-card-subtitle>
          <v-card-text>
            <v-form @submit.prevent="register">
              <v-text-field
                v-model="user.username"
                label="Имя пользователя"
                outlined
                dense
                required
              ></v-text-field>
              <v-text-field
                v-model="user.email"
                label="Электронная почта"
                type="email"
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
              <v-text-field
                v-model="user.retypePassword"
                label="Повторите пароль"
                type="password"
                outlined
                dense
                required
              ></v-text-field>
              <v-btn type="submit" color="primary" block>Зарегистрироваться</v-btn>
            </v-form>
          </v-card-text>

          <!-- Error Messages -->
          <v-alert v-if="error" type="error" dismissible>
            <p class="red--text">{{ error }}</p>
          </v-alert>

          <v-divider></v-divider>

          <!-- Additional Actions -->
          <v-card-actions class="d-flex justify-center">
            <RouterLink to="/login">
              <v-btn text color="primary">Уже есть аккаунт? Войти</v-btn>
            </RouterLink>
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

        // Automatically log in after registration
        await authService.login(this.user)

        const redirectPath = localStorage.getItem('redirectPath')
        localStorage.removeItem('redirectPath') // Очищаем сохранённый путь
        this.$router.push(redirectPath || '/') // Перенаправляем
      } catch (error) {
        console.log(error);
        this.error = error.response.data.errors || error.response.data.message || 'Произошла ошибка.'
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
