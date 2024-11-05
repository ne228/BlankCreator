#!/bin/bash
source ./export.sh
# Массив с путями до docker-compose.yaml
paths=(
    "backend/docker-compose.yaml"
    "client/docker-compose.yaml"
    "gen_blank/docker-compose.yaml"
    # добавьте другие пути по мере необходимости
)

# Проход по каждому пути и выполнение команды docker-compose build and up
for path in "${paths[@]}"; do
    # Переход в директорию, содержащую docker-compose.yaml
    dir=$(dirname "$path")
    echo "Переход в директорию $dir и выполнение docker-compose build and up"

    # Переход в нужную директорию
    cd "$dir" || { echo "Не удалось перейти в директорию $dir"; continue; }
    
    cp "../.env" ".env"
    # Выполнение docker-compose build и up
    docker compose down  # Можно оставить, если хотите остановить контейнеры перед перезапуском
    docker compose build
    docker compose --env-file ".env" up -d

    # Возврат в исходную директорию (если нужно)
    cd - > /dev/null
done
