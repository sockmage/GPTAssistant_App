# Language AI Helper

Android-приложение для общения с ИИ-ассистентом на базе OpenAI GPT, с поддержкой поиска и отправки изображений, а также работы с PDF и Vision.

## Особенности

- Общение с ChatGPT через собственный прокси (обход региональных ограничений)
- Поиск и отправка изображений через DuckDuckGo (через отдельный Python-прокси)
- Отправка PDF-файлов для анализа через Vision API
- Современный дизайн (Material 3), поддержка вложений, история чатов

## Архитектура

- Все текстовые и PDF-запросы идут через LAIH-Proxy (Node.js)
- Поиск изображений — через LAIH_DDG_Image_Proxy (Python FastAPI)
- Приложение не хранит ключи OpenAI/Unsplash и не зависит от региона пользователя

## Сборка и запуск

```bash
git clone https://github.com/ВАШ_РЕПОЗИТОРИЙ/Language-AI-Helper.git
cd Language-AI-Helper
# Откройте проект в Android Studio и соберите как обычное Android-приложение
```

## Backend

- [LAIH-Proxy (Node.js)](./LAIH_Proxy)
- [LAIH_DDG_Image_Proxy (Python)](./LAIH_DDG_Image_Proxy)

## Деплой backend-сервисов на Railway

### LAIH_Proxy
1. Зайдите на [Railway](https://railway.app/), создайте новый проект.
2. Подключите репозиторий с LAIH_Proxy.
3. В настройках проекта добавьте переменную окружения `OPENAI_API_KEY`.
4. Запустите деплой — Railway сам соберёт и запустит сервер.

### LAIH_DDG_Image_Proxy
1. Создайте новый проект на Railway.
2. Подключите репозиторий с LAIH_DDG_Image_Proxy.
3. Railway автоматически определит Python и запустит сервис.

## Лицензия

GPL-2.0