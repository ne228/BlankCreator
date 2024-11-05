<script setup>
import { RouterView } from 'vue-router'
</script>

<template>
  <v-container>
    <div class="mx-auto">
      <v-layout>
        <v-app-bar>
          <template v-slot:image> </template>

          <!-- <template v-slot:prepend>
            <v-app-bar-nav-icon></v-app-bar-nav-icon>
          </template> -->
          <RouterLink to="/" class="ml-5">
            <v-btn variant="outlined" color="primary" prepend-icon="mdi-file-document">
              <h3>Справочный Центр</h3>
            </v-btn>
          </RouterLink>
          <v-spacer></v-spacer>

          <RouterLink to="/">
            <v-btn icon color="black">
              <v-icon>mdi-home</v-icon>
            </v-btn>
          </RouterLink>

          <RouterLink v-if="isAuthenticated" to="/blanks">
            <v-btn icon color="black">
              <v-icon>mdi-file-document</v-icon>
            </v-btn>
          </RouterLink>

          <RouterLink v-if="isAuthenticated" to="/profile" class="mr-2">
            <v-btn icon>
              <v-icon> mdi-account</v-icon>
            </v-btn>
          </RouterLink>

          <RouterLink v-if="!isAuthenticated" to="/login" class="mr-2">
            <v-btn icon>
              <v-icon> mdi-login </v-icon>
            </v-btn>
          </RouterLink>
        </v-app-bar>

        <v-main>
          <v-container fluid>
            <router-view />
            <v-footer app color="" dark>
              <v-container class="py-0">
                <v-row align="center">
                  <v-col>
                    <v-row justify="center" md="end">
                      <v-btn variant="text" href="https://t.me/+vpWkKGdQizcwYWQ6" target="_blank">
                        <v-icon small>mdi-telegram</v-icon>
                        <span class="body-2 ml-2">ne_smallaxe_dev</span>
                      </v-btn>
                    </v-row>
                  </v-col>
                </v-row>
              </v-container>
            </v-footer>
          </v-container>
        </v-main>
      </v-layout>
    </div>
  </v-container>
  <!-- <v-container>

    <header>

      <v-toolbar title="VGR">
        <div class="navbar">

          <RouterLink to="/hub">
            <v-btn rounded="lg">
              <v-icon>mdi-home</v-icon>
              <p class="ml-1">Главная</p>
            </v-btn>
          </RouterLink>
          <RouterLink v-if="isAuthenticated" to="/hub">
            <v-btn rounded="lg" prepend-icon="mdi-folder">
              <p class="ml-1">хранилища</p>
            </v-btn>
          </RouterLink>
          <RouterLink v-if="isAuthenticated" to="/#">
            <v-btn rounded="lg" prepend-icon="mdi-file-document">
              <p class="ml-1">Справки</p>
            </v-btn>
          </RouterLink>

          <RouterLink v-if="isAuthenticated" to="/profile">
            <v-btn rounded="lg" prepend-icon="mdi-account" variant="">
              <p class="ml-1">{{ username }}</p>
            </v-btn>
          </RouterLink>

          <RouterLink v-if="!isAuthenticated" to="/login">
            <v-btn rounded="lg" prepend-icon="mdi-account" variant="">
              <p class="ml-1">ВХОД</p>
            </v-btn>
          </RouterLink>

        </div>
      </v-toolbar>
    </header>

    <router-view />
  </v-container> -->
</template>
<script>
import authService from '@/services/authService'

export default {
  data() {
    return {
      isAuthenticated: false,
      user: null,
      intervalId: null
    }
  },
  mounted() {
    this.isAuth()
    this.intervalId = setInterval(this.isAuth, 1000)
  },
  beforeUnmount() {
    // Очищаем интервал перед размонтированием компонента
    clearInterval(this.intervalId)
  },
  computed: {
    username() {
      return authService.getCurrentUser().username
    }
  },
  methods: {
    getUser() {
      var curUser = authService.getCurrentUser()
      this.user = curUser
      return curUser
    },

    isAuth() {
      this.isAuthenticated = authService.isAuth()
      return this.isAuthenticated
    }
  }
}
</script>

<style scoped>
a {
  color: inherit;
  text-decoration: none;
}

.icon {
  width: 28px;
  height: 28px;
  /* margin-right: 4px; */
  padding: 1%;
  margin-left: 0;
}
</style>
