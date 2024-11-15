<template>
    <h1 align="center" class="mb-2">Ваши хранилища справок</h1>
    <div class="mt-2">
        <div class="d-flex justify-space-between mb-2">
            <div>
                <RouterLink :to="`/hub/create`">
                    <v-btn size="small" prepend-icon="mdi-folder" color="success">
                        Создать хранилище
                    </v-btn>
                </RouterLink>
            </div>
        </div>
        <div class="mt-2">
            <v-text-field append-inner-icon="mdi-magnify" v-model="search" label="Поиск" clearable
                class="search-input"></v-text-field>
        </div>

        <div v-if="data.length == 0">В хранилище нет справок</div>
        <div v-if="data.length > 0" class="table-wrap">
            <v-checkbox-btn v-model="selectAll" @change="toggleSelectAll" class="selectAll mb-1" label="Выбрать все">
            </v-checkbox-btn>
            <table>
                <thead>
                    <tr>
                        <th>
                            <v-checkbox-btn v-model="selectAll" @change="toggleSelectAll" class="pe-2">
                            </v-checkbox-btn>
                        </th>
                        <th v-for="header in headers">
                            {{ header.text }}
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="item, index in filteredData">
                        <td class="d-flex align-center justify-space-between" data-label="Выбран">
                            <div class="flex-end">
                                <v-checkbox-btn :value="item.id" v-model="selectedRows"></v-checkbox-btn>
                            </div>

                        </td>
                        <td :data-label="headers[0].text">
                            {{ item.id }}
                        </td>
                        <td :data-label="headers[1].text">
                            {{ item.user.username }}
                        </td>
                        <td :data-label="headers[2].text">
                            {{ item.status }}
                        </td>
                        <td :data-label="headers[3].text">
                            {{ item.blankList.length }}
                        </td>
                        <td :data-label="headers[4].text">
                            {{ item.dateCreate }}
                        </td>
                        <td :data-label="headers[5].text">
                            <div class="">
                                <div>
                                    <RouterLink :to="`/hub/${item.id}`">
                                        <v-btn size="small" icon>
                                            <v-icon>mdi-eye</v-icon>
                                        </v-btn>
                                    </RouterLink>
                                </div>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>

import router from '@/router';
import HubService from '@/services/hubApi';

export default {
    name: 'FilteredTable',
    data() {
        return {
            data: [],                // Данные
            selectedRows: [],
            selectAll: false,     // Выбранные строки
            search: "",
            headers: [
                { text: 'ID', value: 'id' },
                { text: 'Пользователь', value: 'user' },
                { text: 'Статус', value: 'status' },
                { text: 'Количество справок', value: 'blankList' },
                { text: 'Дата создания', value: 'dateCreate' },
                { text: 'Действия', value: 'actions' },
            ]
        };
    },
    computed: {
        filteredData() {
            if (!this.search) {
                return this.data;
            }
            const searchLower = this.search.toLowerCase();

            console.log(objects);
            return this.data.filter(item =>
                Object.values(item).some(value =>
                    String(value).toLowerCase().includes(searchLower)
                )
            );
        }
    },
    methods: {
        async requestData() {
            try {
                var hubService = new HubService();
                const response = await hubService.getHubs();
                console.log(response);


                // Check if response.data and response.data.blankList exist
                if (response && response.data) {
                    this.data = response.data || [];
                } else {
                    console.error('Неверный формат данных', response);
                }
            } catch (error) {
                console.error('Ошибка при получении данных', error);
            }
        },
        async createHub() {
            router.push(`hub/create`);
        },
        getSelectedRows() {
            console.log('Выбранные строки:', this.selectedRows);
            alert(JSON.stringify(this.selectedRows));
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
    },
    mounted() {
        this.requestData();
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