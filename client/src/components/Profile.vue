<template>
  <v-container class="profile-container" fluid>
    <!-- Верхний блок -->
    <v-col class="profile-hero" align="center" justify="center">
      <v-row>
        <v-col cols="12" md="" class="text-center">
          <v-avatar size="40" class="profile-avatar mb-3">
            <v-icon size="30" color="white">mdi-account</v-icon>
          </v-avatar>
          <h1 class="profile-username">{{ user.username }}</h1>
          <p class="profile-welcome">Добро пожаловать в ваш профиль!</p>
        </v-col>
      </v-row>
      <!-- Основной контент -->
      <v-row justify="center">
        <v-col cols="12" md="8">
          <v-card class="profile-card elevate">
            <!-- Информация о пользователе -->
            <v-card-title>
              <h2 class="profile-section-title">Ваши действия</h2>
            </v-card-title>
            <v-divider></v-divider>
            <v-card-text>
              <v-row class="profile-actions">
                <v-col cols="12" md="6" class="text-center">
                  <RouterLink to="/blanks">
                    <v-btn color="primary" block elevation="2">
                      <v-icon left>mdi-file-document</v-icon>
                      Справки
                    </v-btn>
                  </RouterLink>
                </v-col>
                <v-col cols="12" md="6" class="text-center">
                  <RouterLink to="/hub">
                    <v-btn color="primary" block elevation="2">
                      <v-icon left>mdi-folder</v-icon>
                      Хранилища
                    </v-btn>
                  </RouterLink>
                </v-col>
              </v-row>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
      <!-- Нижний блок -->
      <v-row justify="center" class="mt-4">
        <v-col cols="12" md="8" class="text-center">
          <v-card class="elevate logout-card">
            <v-card-text>
              <p>Готовы закончить сессию? Вы можете выйти из своего профиля.</p>
              <v-btn @click="logout" color="error" large elevation="2">
                <v-icon left>mdi-logout</v-icon>
                Выйти
              </v-btn>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-col>
  </v-container>
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
    logout() {
      console.log('logout')
      authService.logout()
    }
  }
}
</script>

<style scoped>
/* Контейнер */
.profile-container {
  /* background: linear-gradient(to bottom, #e3f2fd, #e8eaf6); */
  padding: 40px 0;
  /* font-family: 'Poppins', sans-serif; */
}

/* Верхний блок */
.profile-hero {
  background: #3f51b5; 
  color: white;
  padding: 40px 20px;
  border-radius: 12px;
}

.profile-avatar {
  background: #5c6bc0;
}

.profile-username {
  font-size: 2rem;
  font-weight: 700;
  margin-bottom: 10px;
}

.profile-welcome {
  font-size: 1.2rem;
  color: #c5cae9;
}

/* Карточка действий */
.profile-card {
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
}

.profile-section-title {
  font-size: 1.5rem;
  font-weight: 600;
  color: #3f51b5;
}

.profile-actions {
  margin-top: 20px;
}

.profile-actions .v-btn {
  font-weight: 600;
  text-transform: uppercase;
}

/* Карточка выхода */
.logout-card {
  padding: 20px;
  border-radius: 12px;
  background: #fff;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
}

.logout-card p {
  font-size: 1rem;
  color: #555;
  margin-bottom: 20px;
}
</style>
