<template>
    <v-form ref="form" v-model="valid" lazy-validation>

        <!-- Должность и приказы -->
        <v-card class="mb-4">
            <v-card-title>Приказ о зачислении</v-card-title>
            <v-card-text>
                <v-row>
                    <v-col cols="12" md="6">
                        <v-text-field v-model="form.datePr" :rules="[rules.required, rules.dateFormat]"
                            label="Дата приказа о зачислении (dd.MM.yyyy)" placeholder="dd.MM.yyyy"
                            @input="validateDateInput('datePr', $event)"></v-text-field>
                    </v-col>
                    <v-col cols="12" md="6">
                        <v-text-field v-model="form.numPr" :rules="rules.required" label="Номер приказа"
                            required></v-text-field>
                    </v-col>
                    <v-col cols="12" md="6">
                        <v-text-field v-model="form.dateEnrollment" :rules="[rules.required, rules.dateFormat]"
                            label="Дата зачисления (dd.MM.yyyy)" placeholder="dd.MM.yyyy"
                            @input="validateDateInput('dateEnrollment', $event)"></v-text-field>
                    </v-col>
                </v-row>
            </v-card-text>
        </v-card>

        <v-row cols="12">
            <v-col align="center">
                <v-btn prepend-icon="mdi-arrow-left" variant="outlined" color="red" @click="back">Назад</v-btn>
            </v-col>
            <v-col align="center">
                <v-btn prepend-icon="mdi-folder" color="success" @click="submit">Создать хранилище</v-btn>
            </v-col>
        </v-row>

        <v-snackbar v-model="snackbar.show" :timeout="3000" color="success">
            {{ snackbar.message }}
            <v-btn color="white" @click="snackbar.show = false">Закрыть</v-btn>
        </v-snackbar>
    </v-form>
</template>

<script>
import { post } from '@/services/apiService';

export default {
    data() {
        const testData = false;
        return {
            valid: false,
            form: {
                datePr: testData ? '01.09.2019' : '',
                numPr: testData ? '515-12' : '',
                dateEnrollment: testData ? "01.09.2018" : ''
            },
            rules: {
                required: [(v) => !!v || 'Это поле обязательно'],
                dateFormat: [(v) => !v || /^\d{2}\.\d{2}\.\d{4}$/.test(v) || 'Неверный формат даты (dd.MM.yyyy)'],
            },
            snackbar: {
                show: false,
                message: '',
            },
        };
    },
    methods: {
        validateDateInput(field, event) {
            const regex = /^\d{0,2}(\.\d{0,2})?(\.\d{0,4})?$/;
            const input = event.target.value;

            if (!regex.test(input)) {
                this.form[field] = input.slice(0, -1);
            }
        },
        back() {
            let id = this.$route.params.hubId;
            this.$router.push(`/hub`)
        },
        async submit() {
            if (this.$refs.form.validate()) {
                console.log('Данные отправлены:', this.form);
                this.form.hubId = this.$route.params.hubId;
                try {
                    const response = await post('/create', this.form);
                    console.log('Response:', response.id);
                    this.$router.push(`/hub/${response.id}`)
                } catch (error) {
                    console.error('Error sending data:', error);
                }
            }
        },
    },
};
</script>

<style scoped>
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

.mb-4 {
    margin-bottom: 20px;
}
</style>
