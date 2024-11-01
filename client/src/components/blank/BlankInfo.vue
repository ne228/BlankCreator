<template>
    <v-container class="form-container" v-if="data != null">
        <!-- Личные данные -->
        <v-card class="mb-4">
            <v-card-title align="center">Данные о справке {{ data.id }}</v-card-title>
            <v-card-text>
                <v-list>
                    <v-list-item>
                        <v-list-item-content>
                            <v-list-item-title><strong>Создатель справки:</strong> {{ data.user.username
                                }}</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>
                    <v-list-item>
                        <v-list-item-content>
                            <v-list-item-title><strong>Дата создания справки:</strong> {{ data.dateCreate
                                }}</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>
                    <v-list-item>
                        <v-list-item-content>
                            <v-list-item-title><strong>Статус справки:</strong> {{ data.blankStatus
                                }}</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>
                </v-list>
            </v-card-text>
        </v-card>
        <v-card class="mb-4">
            <v-card-title>Личные данные</v-card-title>
            <v-card-text>
                <v-list>
                    <v-list-item>
                        <v-list-item-content>
                            <v-list-item-title><strong>Пользователь:</strong> {{ data.user.username
                                }}</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>
                    <v-list-item>
                        <v-list-item-content>
                            <v-list-item-title><strong>ФИО:</strong> {{ data.name }}</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>
                    <v-list-item>
                        <v-list-item-content>
                            <v-list-item-title><strong>Дата рождения:</strong> {{ data.dateBirth }}</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>
                    <v-list-item>
                        <v-list-item-content>
                            <v-list-item-title><strong>Должность:</strong> {{ data.duty }}</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>
                    <v-list-item>
                        <v-list-item-content>
                            <v-list-item-title><strong>Звание:</strong> {{ data.rank }}</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>
                </v-list>
            </v-card-text>
        </v-card>

        <!-- Информация о приказе -->
        <v-card class="mb-4">
            <v-card-title>Информация о приказе</v-card-title>
            <v-card-text>
                <v-list>
                    <v-list-item>
                        <v-list-item-content>
                            <v-list-item-title><strong>Дата приказа о назначении на должность:</strong> {{ data.datePr
                                }}</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>
                    <v-list-item>
                        <v-list-item-content>
                            <v-list-item-title><strong>Номер приказа о назначении на должность:</strong> {{ data.numPr
                                }}</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>
                </v-list>
            </v-card-text>
        </v-card>

        <!-- Информация о службе -->
        <v-card class="mb-4">
            <v-card-title>Информация о службе</v-card-title>
            <v-card-text>
                <v-list>
                    <v-list-item>
                        <v-list-item-content>
                            <v-list-item-title><strong>Дата начала обучения:</strong> {{ data.dateEnrollment
                                }}</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>
                    <v-list-item>
                        <v-list-item-content>
                            <v-list-item-title><strong>Лет обучения:</strong> {{ data.trm }}</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>
                    <v-list-item>
                        <v-list-item-content>
                            <v-list-item-title><strong>Дата окончания учебы:</strong> {{ data.dateEnd
                                }}</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>
                </v-list>
            </v-card-text>
        </v-card>

        <!-- Местоположение -->
        <v-card class="mb-4">
            <v-card-title>Информация о месте назначения справки</v-card-title>
            <v-card-text>
                <v-list>
                    <v-list-item>
                        <v-list-item-content>
                            <v-list-item-title><strong>Место назначения справки:</strong> {{ data.place
                                }}</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>
                    <v-list-item>
                        <v-list-item-content>
                            <v-list-item-title><strong>Населенный пункт:</strong> {{ data.town }}</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>
                </v-list>
            </v-card-text>
        </v-card>
        <v-row cols="12">
            <v-col align="center">
                <v-btn variant="outlined" color="red" @click="back">Назад к хранилищу</v-btn>
            </v-col>
            <v-col align="center">
                <v-btn color="primary" @click="edit">Редактировать</v-btn>
            </v-col>
        </v-row>
    </v-container>



</template>

<script>
import router from '@/router';
import { getData } from '@/services/apiService';

export default {
    name: "BlankInfo",

    data() {
        return {
            id: null,
            data: null,
        };
    },
    mounted() {
        this.id = this.$route.params.blankId;
        console.log('ID из URL:', this.id);
        this.requestData();
    },
    methods: {
        back() {
            let id = this.$route.params.hubId;
            this.$router.push(`/hub/${id}`)
        },
        edit() {
            let blankId = this.$route.params.blankId;
            let hubId = this.$route.params.hubId;
            this.$router.push(`/hub/${hubId}/blank/edit/${blankId}`)
        },
        async requestData() {
            try {
                const response = await getData(`blank/${this.id}`);
                console.log("getBlankInfo", response.data);
                this.data = response.data || [];
            } catch (error) {
                console.error('Ошибка при получении данных', error);
            }
        }
    }
};
</script>

<style scoped>
.card {
    border: none;
    border-radius: 10px;
    width: 100%;
    /* Make the card full width */
    max-width: 800px;
    /* Optional: set a max width for larger screens */
    padding: 20px;
    background: linear-gradient(135deg, #ffffff, #f9f9f9);
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s;
    margin: 0 auto;
    /* Center the card within its container */
}

.card:hover {
    transform: translateY(-5px);
}

.card h3 {
    font-size: 20px;
    color: #333;
    margin-bottom: 15px;
    text-align: center;
}

.card ul {
    list-style: none;
    padding: 0;
}

.card li {
    margin-bottom: 12px;
    display: flex;
    justify-content: space-between;
    padding: 8px;
    border-bottom: 1px solid #eaeaea;
}

.card li:last-child {
    border-bottom: none;
    /* Remove border from the last item */
}

.card li strong {
    color: #555;
}

.card li span {
    color: #777;
    text-align: right;
}

.form-container {
    padding: 20px;
    /* background-color:; */
    margin-top: 5px;
    border-radius: 8px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.v-card {
    background-color: #ffffff;
    border-radius: 8px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}
</style>
