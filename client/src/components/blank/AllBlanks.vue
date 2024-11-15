<template>
    <div>
        <h1 align="center">Мои справки</h1>
        <div class="mt-2">
            <v-text-field append-inner-icon="mdi-magnify" v-model="search" label="Поиск" clearable
                class="search-input"></v-text-field>
        </div>

        <div v-if="data.length == 0">У вас нет справок</div>
        <div v-if="data.length > 0" class="table-wrap">
            <table>
                <thead>
                    <tr>
                        <th v-for="header in headers">
                            {{ header.text }}
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="item, index in filteredData">
                        <td :data-label="headers[0].text">
                            {{ item.id }}
                        </td>
                        <td :data-label="headers[1].text">
                            {{ item.blankStatus }}
                        </td>
                        <td :data-label="headers[2].text">
                            {{ item.dateCreate }}
                        </td>
                        <td class="" :data-label="headers[3].text">
                            <div class="">                                                                  
                                    <RouterLink :to="`/hub/${item.hubId}`">
                                        <v-btn :size="33" icon>
                                            <v-icon small>mdi-eye</v-icon>
                                        </v-btn>
                                    </RouterLink>                                
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>
import { getData } from '@/services/apiService';

import ImportBlankTemplate from './ImportBlankTemplate.vue';
import HubService from '@/services/hubApi';
export default {
    components:{
        ImportBlankTemplate
    },
    data() {
        return {
            hubService: new HubService(),
            data: [], // Массив для хранения данных
            selectAll: false,
            selectedRows: [], // Выбранные строки
            hub: null,
            processPrint: false,
            isShowAction: true,
            search: "",
            headers: [
                { text: 'ID', value: 'id' },
                { text: 'Статус', value: 'blankStatus' },
                { text: 'Дата создания', value: 'dateCreate' },
                { text: 'Действия', value: 'actions' }
            ]
        };
    },
    computed: {
        filteredData() {
            console.log(this.search);

            if (!this.search) {
                return this.data;
            }
            const searchLower = this.search.toLowerCase();
            return this.data.filter(item =>
                Object.values(item).some(value =>
                    String(value).toLowerCase().includes(searchLower)
                ) || (item.user.username && item.user.username.toLowerCase().includes(searchLower))
            );
        }
    },
    methods: {
        async requestData() {
            try {
                const response = await this.hubService.getBlanks();

                // Check if response.data and response.data.blankList exist
                if (response && response.data) {
                    console.log(response.data);
                    this.data = response.data || [];
                } else {
                    console.error('Неверный формат данных', response);
                }
            } catch (error) {
                console.error('Ошибка при получении данных', error);
            }
        },
        toggleSelectAll() {
            if (this.selectAll) {
                // Если выбраны все, добавляем все ID в selectedItems               
                this.selectedRows = this.filteredData.map(item => item.id);
            } else {
                // Если "Выбрать все" снят, очищаем selectedItems
                this.selectedRows = [];
            }
        },
        updateFilteredData() {
            this.filteredData = this.filteredData;
        }, 
        handleImportSuccess(blank){
            this.data.push(blank);
        }
    },
    async mounted() {
        await this.requestData();
    },
    watch: {
        // Следим за изменением массива selectedItems для обновления состояния "Выбрать все"
        selectedRows(newVal) {
            this.selectAll = newVal.length === this.filteredData.length;
        }
    }

};
</script>

<style scoped>
.v-container {
    max-width: 1000px;
}

.table-wrap {
    text-align: center;
    background-color: #fff;
    padding: 0.3rem 0.3rem;
    color: #000;
}

table {
    border: 1px solid #ccc;
    width: 100%;
    margin: 0;
    padding: 0;
    border-collapse: collapse;
    border-spacing: 0;
}

table tr {
    border: 1px solid #ddd;
    padding: 5px;
  
}

table th,
table td {
    padding: 10px;
    text-align: center;
    border-right: 1px solid #ddd;
    
}

table th {
    color: #fff;
    background-color: #444;
    text-transform: uppercase;
    font-size: 14px;
    letter-spacing: 1px;
}

.selectAll {
    display: none;
}


@media screen and (max-width: 600px) {
    .selectAll {
        display: block;
    }

    table {
        border: 0;
        width: 100%;
    }

    table thead {
        display: none;
    }

    table tr {
        margin-bottom: 10px;
        display: block;
        border-bottom: 2px solid #ddd;
    }

    table td {
        display: block;
        text-align: right;
        font-size: 13px;
        border-bottom: 1px dotted #ccc;
        border-right: 1px solid transparent;
    }

    table td:last-child {
        border-bottom: 0;
    }

    table td:before {
        content: attr(data-label);
        float: left;
        text-transform: uppercase;
        font-weight: bold;
    }
}
</style>