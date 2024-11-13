<template>
  <v-row justify="center">
    <v-col align="center">
      <v-card class="d-flex flex-column profile">
        <v-card-title>
          <v-icon left>mdi-account</v-icon>
          <span class="ml-2">Профиль {{ user.username }}</span>
        </v-card-title>
        <v-divider></v-divider>
        <!-- Блок с информацией о пользователе -->
        <!-- <v-card-subtitle align="center" class="mt-2">Информация о пользователе</v-card-subtitle> -->
        <v-card-text class="flex-grow-1">
          <!-- <p v-if="user">
            <v-icon left>mdi-account-circle</v-icon>
            <strong class="ml-2"> {{ user.username }}</strong>
          </p>
          <p v-if="user" class="mt-2">
            <v-icon left>mdi-email</v-icon>
            <strong class="ml-2"> {{ user.email }}</strong>
          </p> -->
          <div class="d-flex flex-wrap justify-space-around">
            <RouterLink class="ma-1" to="/blanks">
              <v-btn color="indigo">
                Справки
                <v-icon class="ml-3">mdi-file-document</v-icon>
              </v-btn>
            </RouterLink>
            <RouterLink class="ma-1" to="/hub">
              <v-btn color="indigo">
                Хранилища
                <v-icon class="ml-3">mdi-folder</v-icon>
              </v-btn>
            </RouterLink>
          </div>
        </v-card-text>

        <v-divider></v-divider>

        <!-- Блок с действиями -->
        <v-card-actions class="d-flex justify-end mt-auto">
          <v-btn @click="logout" color="primary">
            <v-icon left>mdi-logout</v-icon>
            Выйти
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import authService from '@/services/authService'

export default {
  data() {
    return {
      user: null
    }
  },
  beforeMount() {
    this.getUserData()
  },
  methods: {
    getUserData() {
      this.user = authService.getCurrentUser()
    },
    click() {
      // Добавьте здесь код, который нужно выполнить при нажатии на кнопку
    },
    logout() {
      console.log('logout')
      authService.logout()
    }
  }
}
</script>

<style scoped>
.profile {
  max-width: 400px;
  height: 75vh;
}
</style>
