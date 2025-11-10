up:
    @echo "Запуск сервисов..."
    docker compose up -d

down:
    @echo "Остановка сервисов..."
    docker compose down

logs:
    @echo "Просмотр логов..."
    docker compose logs -f

rebuild: 
    @echo "Пересборка образов и запуск контейнера"
    docker compose build
    docker compose up -d

fresh-start:
    @echo "Холодный запуск сервисов..."
    docker compose down -v
    docker compose build
    docker compose up -d
