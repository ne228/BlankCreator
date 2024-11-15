<template>
  <div class="landing-container" fluid>
    <!-- Главный блок с заголовком -->
    <v-row class="hero-section" align="center" justify="center">
      <v-col cols="12" md="8" class="text-center">
        <h1 class="hero-title">
          Справочный Центр
          <v-icon class="hero-icon" large color="white">mdi-information-outline</v-icon>
        </h1>

        <p class="hero-text">
          Универсальное решение для создания, управления и хранения справок. Подходит как для
          пользователей, так и для администраторов.
        </p>
        <v-btn color="primary" large elevation="2" href="#features">Узнать больше</v-btn>
      </v-col>
    </v-row>

    <!-- Раздел о функциях -->
    <v-row id="features" class="features-section" justify="space-around">
      <v-col cols="12" md="5">
        <v-card class="feature-card elevate">
          <h3 class="feature-title">
            <v-icon class="feature-icon" color="primary">mdi-account-circle</v-icon> Для
            пользователей
          </h3>
          <p>Создавайте справки всего за несколько кликов:</p>
          <ul class="feature-list">
            <li>Заполните форму для справки</li>
            <li>Редактируйте данные при необходимости</li>
            <li>Просматривайте и отправляйте справки на печать</li>
          </ul>
        </v-card>
      </v-col>

      <v-col cols="12" md="5">
        <v-card href="/hub" class="feature-card elevate">
          <h3 class="feature-title">
            <v-icon class="feature-icon" color="primary">mdi-shield-account</v-icon> Для
            администраторов
          </h3>
          <p>Управляйте хранилищами и предоставляйте доступ:</p>
          <ul class="feature-list">
            <li>Создание и распространение ссылок</li>
            <li>Отслеживание статусов справок</li>
            <li>Управление данными пользователей</li>
          </ul>
        </v-card>
      </v-col>
    </v-row>

    <!-- Раздел с шагами работы -->
    <v-row class="steps-section" justify="center">
      <v-row cols="12" md="8" class="text-center" justify="center">
        <h2 class="section-title text-center">Как это работает?</h2>
      </v-row>
      <v-row>
        <v-col cols="12" md="4">
          <v-card class="step-card elevate">
            <v-icon class="step-icon" color="primary">mdi-login</v-icon>
            <h4 class="step-title">Авторизация</h4>
            <p>Пройдите авторизацию и получите доступ к инструментам справочного центра.</p>
          </v-card>
        </v-col>
        <v-col cols="12" md="4">
          <v-card class="step-card elevate">
            <v-icon class="step-icon" color="primary">mdi-pencil</v-icon>
            <h4 class="step-title">Создание справок</h4>
            <p>Заполните удобную форму для создания справки и внесите правки при необходимости.</p>
          </v-card>
        </v-col>
        <v-col cols="12" md="4">
          <v-card class="step-card elevate">
            <v-icon class="step-icon" color="primary">mdi-printer</v-icon>
            <h4 class="step-title">Печать</h4>
            <p>Предпросмотр готовой справки и отправка ее на печать в несколько кликов.</p>
          </v-card>
        </v-col>
      </v-row>
    </v-row>

    <!-- Раздел контактов -->
    <v-row class="contact-section" justify="center">
      <v-col cols="12" md="8" class="text-center">
        <h2 class="section-title">Нужна помощь?</h2>
        <p>
          Свяжитесь с администратором или обратитесь в техническую поддержку для получения подробной
          информации.
        </p>
        <v-btn
          class="mt-2"
          color="primary"
          large
          elevation="2"
          href="https://t.me/+vpWkKGdQizcwYWQ6"
          >Написать в поддержку</v-btn
        >
      </v-col>
    </v-row>
  </div>
</template>

<script>
export default {
  components: {}
}
</script>

<style scoped>
/* Главный контейнер */
.landing-container {
  /* font-family: 'Poppins', sans-serif; */
}

/* Hero Section */
.hero-section {
  background: linear-gradient(135deg, #3f51b5, #5c6bc0);
  color: white;
  padding: 60px 20px;
}

.hero-icon {
  font-size: 4rem;
  margin-bottom: 20px;
}

.hero-title {
  font-size: 2.5rem;
  font-weight: 700;
  margin-bottom: 20px;
}

.hero-text {
  font-size: 1.2rem;
  margin-bottom: 30px;
}

/* Features Section */
.features-section {
  padding: 60px 20px;
}

.feature-card {
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
}

.feature-icon {
  font-size: 3rem;
  margin-bottom: 15px;
}

.feature-title {
  font-size: 1.5rem;
  margin-bottom: 15px;
}

.feature-list {
  padding-left: 20px;
  list-style: disc;
}

/* Steps Section */
.steps-section {
  background: #f8f9fa;
  padding: 60px 20px;
}

.step-card {
  text-align: center;
  padding: 20px;
  border-radius: 10px;
}

.step-icon {
  font-size: 3rem;
  margin-bottom: 15px;
}

.step-title {
  font-size: 1.2rem;
  margin-bottom: 10px;
}

/* Contact Section */
.contact-section {
  background: linear-gradient(to bottom, #e3f2fd, #e8eaf6);
  text-align: center;
  padding: 60px 20px;
}

.section-title {
  text-align: center;
  font-size: 2rem;
  color: #3f51b5;
  font-weight: 700;
  margin-bottom: 20px;
}

.v-btn {
  font-weight: 600;
}
</style>
