#!/bin/bash

# Массив с путями до docker-compose.yaml
exit;
paths=(
    "backend/docker-compose.yaml"
    "client/docker-compose.yaml"
    "gen_blank/docker-compose.yaml"
    # добавьте другие пути по мере необходимости
)

# Проход по каждому пути и выполнение команды docker-compose down
for path in "${paths[@]}"; do
    # Переход в директорию, содержащую docker-compose.yaml
    dir=$(dirname "$path")
    echo "Переход в директорию $dir и выполнение docker-compose build and up"

    # Переход в нужную директорию
    cd "$dir" || { echo "Не удалось перейти в директорию $dir"; continue; }

    # Выполнение docker-compose down
    docker compose down
    docker compose build
    docker compose --env-file ..\.env up -d

    # Возврат в исходную директорию (если нужно)
    cd - > /dev/null
done
