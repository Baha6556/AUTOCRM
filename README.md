# AutoCRM — Система управления автобизнесом

Современное Android CRM-приложение для учёта автоимпорта и перепродажи автомобилей.

## Стек технологий

- **UI**: Jetpack Compose + Material 3 Dark Theme
- **Архитектура**: MVVM + Clean Architecture (data / domain / presentation)
- **DI**: Hilt
- **БД**: Room (offline-first)
- **Async**: Coroutines + Flow
- **Изображения**: Coil

## Функции

| Экран | Описание |
|-------|----------|
| 📊 Dashboard | Прибыль, статистика, топ машин, график по месяцам |
| 🚗 Мои машины | Список с поиском, фильтрами и сортировкой |
| 📋 Детали авто | Фото, расходы, финансы, продажа |
| ➕ Добавление | Форма с расчётом прибыли в реальном времени |
| 💰 Продажи | История сделок, ROI, дни в продаже |
| 📈 Аналитика | KPI, страны, статусы, расходы по категориям |

## Структура проекта

```
app/src/main/java/com/autocrm/
├── data/
│   ├── local/
│   │   ├── AppDatabase.kt          ← Room DB
│   │   ├── converters/Converters.kt
│   │   ├── dao/Daos.kt             ← CarDao, ExpenseDao, PhotoDao
│   │   └── entities/               ← Entity + Mappers
│   └── repository/CarRepositoryImpl.kt
├── domain/
│   ├── model/Models.kt             ← Car, Expense, CarPhoto, enums
│   └── repository/CarRepository.kt
├── presentation/
│   ├── MainActivity.kt             ← Navigation setup
│   ├── viewmodel/CarsViewModel.kt  ← Shared ViewModel
│   └── ui/
│       ├── Components.kt           ← Shared UI widgets
│       ├── theme/Theme.kt          ← Dark premium palette
│       ├── dashboard/
│       ├── cars/
│       ├── cardetail/
│       ├── addcar/
│       ├── sales/
│       └── analytics/
└── di/AppModule.kt                 ← Hilt modules
```

## Быстрый старт

### Требования
- Android Studio Ladybug (2024.2+) или новее
- JDK 17
- Android SDK 35
- Минимальная версия Android: 8.0 (API 26)

### Шаги

1. **Открыть проект**
   ```
   File → Open → выбрать папку AutoCRM
   ```

2. **Синхронизировать Gradle**
   ```
   Tools → Android → Sync Project with Gradle Files
   ```
   или нажать **Sync Now** в баннере вверху.

3. **Запустить**
   - Выбрать устройство или эмулятор (API 26+)
   - Нажать **Run ▶**

### Возможные проблемы

**Ошибка: `Duplicate class kotlin.collections`**
→ Проверьте версии в `gradle/libs.versions.toml`, все версии должны совпадать.

**Ошибка компиляции Room**
→ Убедитесь что используется KSP, не KAPT.

**Coil не загружает фото**
→ На Android 13+ нужно разрешение `READ_MEDIA_IMAGES`. Приложение запрашивает его автоматически.

## Модели данных

### Car
```kotlin
Car(
  uuid, make, model, year, vin, mileage,
  countryOfOrigin, purchasePrice, estimatedSalePrice,
  salePrice, status: CarStatus, purchaseDate, saleDate
)
```

### CarStatus (enum)
- `IN_TRANSIT` — В пути 🔵
- `FOR_SALE` — На продаже 🟢
- `SOLD` — Продана ⚫
- `REPAIR` — Ремонт 🟡
- `RESERVED` — Забронирована 🟣

### ExpenseCategory (enum)
`PURCHASE`, `DELIVERY`, `CUSTOMS`, `REPAIR`, `INSPECTION`, `INSURANCE`, `PARKING`, `OTHER`

## Офлайн-первая архитектура

Каждая запись содержит:
- `uuid` — локальный идентификатор
- `serverId` — ID после синхронизации с backend
- `syncStatus` — `PENDING` / `SYNCED` / `FAILED`
- `isDeleted` — soft-delete для синхронизации

## Расширение

### Добавить backend (FastAPI)
1. Реализовать `NetworkDataSource` с Retrofit/Ktor
2. В `CarRepositoryImpl` добавить sync-логику после локального сохранения
3. Использовать WorkManager для фоновой синхронизации

### Добавить фото
Для выбора фото из галереи используйте `rememberLauncherForActivityResult`:
```kotlin
val launcher = rememberLauncherForActivityResult(
    ActivityResultContracts.GetMultipleContents()
) { uris ->
    uris.forEach { uri -> vm.addPhoto(carUuid, uri.toString()) }
}
launcher.launch("image/*")
```

## Лицензия
MIT — свободное использование в коммерческих и личных проектах.
