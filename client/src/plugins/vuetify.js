// Styles
import '@mdi/font/css/materialdesignicons.css'
import 'vuetify/styles'

import { ru } from 'vuetify/locale'

// Vuetify
import { createVuetify } from 'vuetify'

const vuetify = createVuetify({
  theme: {
    // defaultTheme: 'dark'
  },
  locale: {
    current: 'ru', // Устанавливаем русский язык по умолчанию
    messages: { ru } // Используем импортированные сообщения для русского языка
  }
  // Вы также можете настроить другие параметры Vuetify здесь
})

export default vuetify
