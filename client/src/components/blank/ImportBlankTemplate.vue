<template>
  <!-- Кнопка для открытия модального окна -->
  <v-btn size="small" variant="outlined" color="success" @click="openDialog">
    Импорт
    <v-icon class="ml-3">mdi-file-import</v-icon>
  </v-btn>

  <!-- Модальное окно с использованием v-dialog -->
  <v-dialog class="dialog" v-model="dialog">
    <v-card>
      <v-card-title>Ваши шаблоны</v-card-title>
      <v-divider></v-divider>
      <div class="text-body-2 mt-2 ml-5 font-weight-light">
        Выберите шаблон, чтобы вставить его в хранилище
      </div>
      <v-card-text>
        <!-- Список элементов -->
        <v-list v-if="items.length > 0">
          <v-list-item v-for="(item, index) in items" :key="item.id">
            <div class="d-flex flex-row justify-space-between align-center">
              <div class="d-flex flex-column font-weight-black">
                {{ index + 1 }}. {{ item.name }}
              </div>

              <div class="d-flex flex-wrap">
                <v-btn class="mr-2" size="small" variant="outlined" color="red" @click="deleteItem(item.id)"
                  >удалить</v-btn
                >
                <v-btn size="small" color="success" @click="submitItem(item.id)">импорт</v-btn>
              </div>
            </div>
            <div class="text-body-2 w-100 font-weight-light">
              {{ item.rank }} {{ item.duty }} {{ item.dateBirth }} г.р. {{ item.place }}
              {{ item.town }}
            </div>
          </v-list-item>
        </v-list>
        <div v-if="items == 0">
          У вас нет шаблонов. Чтобы создать шаблон, при оздании новой справки выберите пункт
          "Сохранить как шаблон".
        </div>
      </v-card-text>
      <v-divider></v-divider>
      <v-card-actions class="pa-5">
        <v-spacer></v-spacer>
        <v-btn color="error" text @click="closeDialog">Закрыть</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import HubService from '@/services/hubApi'

export default {
  name: 'ImportBlankTemplate',
  props: {
    hubId: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      hubService: new HubService(),
      dialog: false, // Состояние для управления модальным окном
      items: [] // Список элементов
    }
  },
  methods: {
    // Функция для открытия диалога
    openDialog() {
      this.dialog = true
    },
    // Функция для закрытия диалога
    closeDialog() {
      this.dialog = false
    },
    // Функция для отправки ID элемента
    async submitItem(itemId) {
      try {
        var data = { hubId: this.hubId, templateId: itemId }
        const response = await this.hubService.templateImport(data)
        this.$emit('importSuccess', response)
        this.closeDialog()
      } catch (error) {
        console.error('Ошибка при получении данных', error)
      }
    },
    async deleteItem(itemId) {
      try {
        const response = await this.hubService.deleteTemplate(itemId)
        console.log(response)
        if (response == 0) {
          this.items = this.items.filter((item) => item.id !== itemId)
        }
      } catch (error) {
        console.error('Ошибка при получении данных', error)
      }
    },
    async requestData() {
      try {
        const response = await this.hubService.getTemplates()

        // Check if response.data and response.data.blankList exist
        if (response && response.data) {
          console.log(response)
          this.items = response.data
        } else {
          console.error('Неверный формат данных', response)
        }
      } catch (error) {
        console.error('Ошибка при получении данных', error)
      }
    }
  },
  async mounted() {
    await this.requestData()
  }
}
</script>

<style scoped>
.dialog {
  padding: 5%;
  min-height: 900px;
  /* background-color: aqua; */
}
</style>
