#!/bin/bash

# Указываем путь к .env файлу
ENV_FILE=".env"

# Проверяем, существует ли файл .env
if [[ -f "$ENV_FILE" ]]; then
    # Читаем файл построчно
    while IFS='=' read -r key value; do
        # Пропускаем пустые строки и строки с комментариями
        if [[ ! -z "$key" && ! "$key" =~ ^# ]]; then
            # Убираем возможные пробелы вокруг ключа и значения
            key=$(echo "$key" | xargs)
            value=$(echo "$value" | xargs)
            
            # Экспортируем переменную
            export "$key"="$value"
            
            # Выводим значение переменной
            echo "$key=$value"
        fi
    done < "$ENV_FILE"
else
    echo "Файл .env не найден!"
fi

echo "OK"
