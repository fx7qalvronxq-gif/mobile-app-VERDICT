<div align="center">

<img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white"/>
<img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white"/>
<img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/>
<img src="https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white"/>
<img src="https://img.shields.io/badge/License-MIT-green?style=for-the-badge"/>

# Verdict Mobile

**[Русский](#ru) | [English](#en)**

</div>

---

<a name="ru"></a>
## Русский

### О проекте

**Verdict** — мобильное приложение для оценки блюд и заведений. В отличие от стандартных агрегаторов, Verdict фокусируется на конкретном блюде, а не на заведении в целом. Нашёл что-то стоящее — добавь фото, выстави оценку, прикрепи теги и укажи где это можно попробовать.

### Возможности

- Загрузка фотографий блюд
- Рейтинг и текстовые отзывы на каждое блюдо
- Теги для фильтрации и категоризации
- Привязка блюда к заведению с геолокацией
- Хранение данных локально в базе данных

### Стек технологий

| Слой | Технология |
|---|---|
| Язык | ![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?logo=kotlin&logoColor=white) ![Java](https://img.shields.io/badge/Java-ED8B00?logo=openjdk&logoColor=white) |
| UI | ![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4?logo=jetpackcompose&logoColor=white) |
| Архитектура | MVVM |
| База данных | Room |
| Навигация | Navigation Component |

### Установка

#### Вариант 1 — Клонирование репозитория

```bash
# Клонировать репозиторий
git clone https://github.com/username/verdict-mobile.git

# Перейти в папку проекта
cd verdict-mobile
```

#### Вариант 2 — Скачать ZIP

1. Нажми **Code → Download ZIP** на странице репозитория
2. Распакуй архив
3. Открой папку в Android Studio

### Запуск в Android Studio

```
1. Открой Android Studio
2. File → Open → выбери папку проекта
3. Дождись синхронизации Gradle (нижняя панель)
4. Tools → AVD Manager → создай эмулятор (API 26+)
   — или подключи реальное устройство через USB
5. Включи USB Debugging:
   Настройки → О телефоне → нажми 7 раз на "Номер сборки"
   → Для разработчиков → USB-отладка → ВКЛ
6. Нажми ▶ Run или Shift + F10
```

### Требования

- Android Studio Hedgehog или новее
- JDK 17+
- Android SDK API 26 (Android 8.0) и выше
- Gradle 8.0+

---

<a name="en"></a>
## English

### About

**Verdict** is a mobile application for rating dishes and discovering the best food in your city. Unlike standard review platforms, Verdict focuses on individual dishes rather than venues as a whole. Found something worth sharing — add a photo, leave a rating, attach tags, and pin the location where it can be found.

### Features

- Photo uploads for individual dishes
- Rating and review system per dish
- Tag-based filtering and categorization
- Location binding — attach a dish to a specific venue
- Persistent local storage via database

### Tech Stack

| Layer | Technology |
|---|---|
| Language | ![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?logo=kotlin&logoColor=white) ![Java](https://img.shields.io/badge/Java-ED8B00?logo=openjdk&logoColor=white) |
| UI | ![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4?logo=jetpackcompose&logoColor=white) |
| Architecture | MVVM |
| Database | Room |
| Navigation | Navigation Component |

### Installation

#### Option 1 — Clone the repository

```bash
# Clone the repository
git clone https://github.com/username/verdict-mobile.git

# Navigate to the project directory
cd verdict-mobile
```

#### Option 2 — Download ZIP

1. Click **Code → Download ZIP** on the repository page
2. Extract the archive
3. Open the folder in Android Studio

### Running in Android Studio

```
1. Open Android Studio
2. File → Open → select the project folder
3. Wait for Gradle sync to complete (bottom status bar)
4. Tools → AVD Manager → create an emulator (API 26+)
   — or connect a real device via USB
5. Enable USB Debugging on your device:
   Settings → About Phone → tap "Build Number" 7 times
   → Developer Options → USB Debugging → ON
6. Press ▶ Run or Shift + F10
```

### Requirements

- Android Studio Hedgehog or newer
- JDK 17+
- Android SDK API 26 (Android 8.0) or higher
- Gradle 8.0+

---

## License

```
MIT License

Copyright (c) 2026 Nikita Kornev

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
```
