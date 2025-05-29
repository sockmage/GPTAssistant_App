# Lingro

Android-приложение для общения с ИИ-ассистентом на базе OpenAI GPT, с поддержкой поиска и отправки изображений, а также работы с PDF и Vision.

## Особенности

- Общение с ChatGPT через собственный прокси (обход региональных ограничений)
- Поиск и отправка изображений через DuckDuckGo (через отдельный Python-прокси)
- Отправка PDF-файлов для анализа через Vision API
- Современный дизайн (Material 3), поддержка вложений, история чатов

## Архитектура

- Все текстовые и PDF-запросы идут через Lingro-Proxy (Node.js)
- Поиск изображений — через Lingro_DDG_Image_Proxy (Python FastAPI)
- Приложение не хранит ключи OpenAI/Unsplash и не зависит от региона пользователя

## Сборка и запуск

```bash
git clone https://github.com/ВАШ_РЕПОЗИТОРИЙ/Lingro.git
cd Lingro
# Откройте проект в Android Studio и соберите как обычное Android-приложение
```

## Backend

- [Lingro-Proxy (Node.js)](./Lingro_Proxy)
- [Lingro_DDG_Image_Proxy (Python)](./Lingro_DDG_Image_Proxy)

## Деплой backend-сервисов на Railway

### Lingro_Proxy
1. Зайдите на [Railway](https://railway.app/), создайте новый проект.
2. Подключите репозиторий с Lingro_Proxy.
3. В настройках проекта добавьте переменную окружения `OPENAI_API_KEY`.
4. Запустите деплой — Railway сам соберёт и запустит сервер.

### Lingro_DDG_Image_Proxy
1. Создайте новый проект на Railway.
2. Подключите репозиторий с Lingro_DDG_Image_Proxy.
3. Railway автоматически определит Python и запустит сервис.

## License

All rights reserved.

This code is provided for viewing purposes only.
Any use, copying, modification, or distribution is strictly prohibited without explicit permission from the author.