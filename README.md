# Lingro

Android-приложение для общения с ИИ-ассистентом на базе OpenAI GPT, с поддержкой поиска и отправки изображений, а также работы с PDF и Vision.

## Особенности

- Современный дизайн на базе Material 3 (Jetpack Compose)
  - Используются Material Icons и кастомный шрифт [Rubik](https://fonts.google.com/specimen/Rubik)
  - Поддержка светлой и тёмной темы
- Общение с ChatGPT через собственный прокси (обход региональных ограничений — приложение работает в любой стране)
- Поиск и отправка изображений через DuckDuckGo (через отдельный Python-прокси)
- Отправка PDF-файлов для анализа через Vision API
- Поддержка вложений, история чатов
- Приложение — Android-ответвление проекта [tggpt (Telegram-бот)](https://github.com/mxlskh/tggpt): общий backend, разные клиенты

## Архитектура

- Все текстовые и PDF-запросы идут через [Lingro-Proxy (Node.js)](https://github.com/sockmage/Lingro-Proxy)
- Поиск изображений — через [DuckDuckGo Image API for Lingro (Python FastAPI)](https://github.com/sockmage/DDG-Image-API-for-Lingro)
- Приложение не хранит ключи OpenAI/Unsplash и не зависит от региона пользователя

## Сборка и запуск

```bash
git clone https://github.com/ВАШ_РЕПОЗИТОРИЙ/Lingro.git
cd Lingro
# Откройте проект в Android Studio и соберите как обычное Android-приложение
```

## Backend

- [Lingro-Proxy (Node.js)](https://github.com/sockmage/Lingro-Proxy)
- [DuckDuckGo Image API for Lingro (Python)](https://github.com/sockmage/DDG-Image-API-for-Lingro)

## Деплой backend-сервисов на Railway

### Lingro_Proxy
1. Зайдите на [Railway](https://railway.app/), создайте новый проект.
2. Подключите репозиторий с [Lingro-Proxy](https://github.com/sockmage/Lingro-Proxy).
3. В настройках проекта добавьте переменную окружения `OPENAI_API_KEY`.
4. Запустите деплой — Railway сам соберёт и запустит сервер.

### DuckDuckGo Image API for Lingro
1. Создайте новый проект на Railway.
2. Подключите репозиторий с [DuckDuckGo Image API for Lingro](https://github.com/sockmage/DDG-Image-API-for-Lingro).
3. Railway автоматически определит Python и запустит сервис.

## Инструменты и сборка

- Для разработки использовалась [Android Studio](https://developer.android.com/studio) — официальная среда для Android.
- Интерфейс реализован на [Jetpack Compose](https://developer.android.com/jetpack/compose) — декларативном UI-фреймворке от Google.
- Для внедрения зависимостей используется [Hilt (Dagger)](https://dagger.dev/hilt/).
- Работа с сетью — через [Retrofit](https://square.github.io/retrofit/) и [OkHttp](https://square.github.io/okhttp/).
- Для загрузки изображений — [Coil](https://coil-kt.github.io/coil/).
- Для поддержки markdown — библиотека [Markwon](https://noties.io/Markwon/).
- Используются Material Icons и кастомный шрифт [Rubik](https://fonts.google.com/specimen/Rubik).

## License

All rights reserved.

This code is provided for viewing purposes only.
Any use, copying, modification, or distribution is strictly prohibited without explicit permission from the author.