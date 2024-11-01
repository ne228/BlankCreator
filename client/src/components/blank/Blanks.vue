<template>
    <div v-if="hub != null">
        <h1 align="center">Хранилище: {{ hub.id }}</h1>
        <div class="d-flex flex-row justify-center align-center">
            <h3 class="mb-2">Приказ о зачислении №{{ hub.numPr }} от {{ hub.datePr }}</h3>
            <RouterLink v-if="isShowAction" class="pb-5 ml-1" :to="`/hub/edit/${this.hub.id}`">
                <v-btn size="x-small" icon>
                    <v-icon>mdi-pencil</v-icon>
                </v-btn>
            </RouterLink>
        </div>
        <div class="d-flex justify-space-between flex-wrap">
            <v-btn size="small" class="ma-1"
                :disabled="selectedRows.length == 0 || (processPrint && selectedRows.length > 0)" color="primary"
                @click="print(hub.id)">
                <div>Печать</div>
                <v-icon class="ml-3">mdi-printer</v-icon>
            </v-btn>
            <RouterLink class="ma-1" :to="`/hub/${hub.id}/blank/create`">
                <v-btn size="small" color="success">

                    <p class="mr-3">Справка</p>
                    <v-icon>mdi-plus-circle</v-icon>
                </v-btn>
            </RouterLink>
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
                            {{ item.blankStatus }}
                        </td>
                        <td :data-label="headers[3].text">
                            {{ item.dateCreate }}
                        </td>
                        <td :data-label="headers[4].text">
                            <div class="d-flex flex-wrap align-center justify-space-between pl-2 pr-2">
                                <div>
                                    <RouterLink :to="`/hub/${this.hub.id}/blank/${item.id}`">
                                        <v-btn :size="33" icon>
                                            <v-icon small>mdi-eye</v-icon>
                                        </v-btn>
                                    </RouterLink>
                                </div>
                                <div>
                                    <RouterLink :to="`/hub/${this.hub.id}/blank/edit/${item.id}`">
                                        <v-btn :size="33" icon>
                                            <v-icon small>mdi-pencil</v-icon>
                                        </v-btn>
                                    </RouterLink>
                                </div>
                                <div v-if="item.blankStatus == 'ЧЕРНОВИК' && isShowAction">
                                    <v-btn :size="33" icon color="red" @click="reject(item)">
                                        <v-icon>mdi-close</v-icon>
                                    </v-btn>
                                </div>
                                <div v-if="item.blankStatus == 'ЧЕРНОВИК' && isShowAction">
                                    <v-btn :size="33" icon color="green" @click="success(item)">
                                        <v-icon small>mdi-check</v-icon>
                                    </v-btn>
                                </div>

                                <div v-if="item.blankStatus == 'УСПЕШНО' && isShowAction">
                                    <v-btn :size="33" icon color="green" @click="reject(item)">
                                        <v-icon>mdi-check</v-icon>
                                    </v-btn>
                                </div>
                                <div v-if="item.blankStatus == 'ОТКЛОНЕН' && isShowAction">
                                    <v-btn :size="33" icon color="red" @click="success(item)">
                                        <v-icon small>mdi-close</v-icon>
                                    </v-btn>
                                </div>
                                <div>
                                    <v-btn :size="33" icon color="primary" @click="print(hub.id, item.id)">
                                        <v-icon small>mdi-printer</v-icon>
                                    </v-btn>
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
import { downloadPost, getData, post } from '@/services/apiService';
import { el, fa, tr } from 'vuetify/lib/locale/index.mjs';
import { saveAs } from 'file-saver';
import authService from '@/services/authService';
export default {
    data() {
        return {
            data: [], // Массив для хранения данных
            dataTest: [
                { name: 'Алексей', age: 30, city: 'Москва' },
                { name: 'Мария', age: 25, city: 'Санкт-Петербург' },
                { name: 'Иван', age: 35, city: 'Казань' },
            ],
            selectAll: false,
            selectedRows: [], // Выбранные строки
            hub: null,
            processPrint: false,
            isShowAction: true,
            search: "",
            headers: [
                { text: 'ID', value: 'id' },
                { text: 'Пользователь', value: 'user.username' },
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
                const id = this.$route.params.id;
                const response = await getData(`get?id=${id}`);

                // Check if response.data and response.data.blankList exist
                if (response && response.data) {
                    this.data = response.data.blankList || [];
                    this.hub = response.data;
                } else {
                    console.error('Неверный формат данных', response);
                }
            } catch (error) {
                console.error('Ошибка при получении данных', error);
            }
        },
        async print(hubId, blankId) {
            try {
                this.processPrint = true;
                var idx = [];
                var fileId = hubId;
                if (blankId != undefined) {
                    idx.push(blankId);
                    fileId = blankId;
                } else {
                    if (this.selectedRows.length == 0) {
                        alert("Выберите справки");
                        this.processPrint = false;
                        return;
                    }
                    idx = this.selectedRows;
                }

                const response = await downloadPost(`blank/print/${hubId}`, idx)
                // Создаем объект URL из Blob
                // const contentDisposition = response.headers['content-disposition'];
                const fileName = `Отчет-${fileId}.docx`

                // Сохраняем файл с помощью file-saver
                saveAs(new Blob([response]), fileName);
                this.processPrint = false;
            } catch (error) {
                this.processPrint = false;
                console.error('Ошибка при получении данных', error);
            }
        },

        async success(blankData) {
            try {

                const response = await post(`blank/success/${blankData.id}`);

                // Check if response.data and response.data.blankList exist
                if (response) {
                    // blankData = response;
                    this.data = this.data.map(dt => {
                        if (dt.id == blankData.id)
                            return response;
                        else
                            return dt;
                    });

                } else {
                    console.error('Неверный формат данных', response);
                }
            } catch (error) {
                console.error('Ошибка при получении данных', error);
            }
        },
        async reject(blankData) {
            try {
                const response = await post(`blank/reject/${blankData.id}`);

                // Check if response.data and response.data.blankList exist
                if (response) {
                    // blankData = response;
                    this.data = this.data.map(dt => {
                        if (dt.id == blankData.id)
                            return response;
                        else
                            return dt;
                    });
                }

            } catch (error) {
                console.error('Ошибка при получении данных', error);
            }
        },
        getSelectedRows() {
            alert(JSON.stringify(this.selectedRows));
        },
        editRow(item) {
            console.log('Редактировать строку:', item);
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
        }
    },
    async mounted() {
        await this.requestData();
        var user = authService.getCurrentUser();
        this.isShowAction = this.hub.user.id === user.id;
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