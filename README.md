# **Operation Service**

Микросервис **Operation Service** предназначен для управления операциями. Он работает с базой данных PostgreSQL и использует Redis для кэширования. Приложение запускается в контейнерах с использованием Docker Compose.

---

## **Содержание**
- [Требования](#требования)
- [Запуск](#запуск)
- [Конфигурация](#конфигурация)
- [API Документация](#api-документация)
- [Тестирование](#тестирование)
- [Остановка](#остановка)
- [Контакты](#контакты)

---

## **Требования**

Перед запуском убедитесь, что у вас установлены:
- **Docker** (версия >= 20.10)
- **Docker Compose** (версия >= 1.29)

---

## **Запуск**

1. **Клонируйте репозиторий:**
    ```bash
    git clone https://github.com/your-repo/operation-service.git
    cd operation-service
    ```

2. **Создайте `.env` файл** в корневой директории и укажите переменные окружения:
    ```env
    PG_URL_PROD=jdbc:postgresql://postgres-db:5432/operations
    PG_USERNAME_PROD=admin
    PG_PASSWORD_PROD=admin123

    REDIS_PORT_PROD=6379
    REDIS_PASSWORD_PROD=redisPassword
    REDIS_USER_PROD=redisUser
    REDIS_USER_PASSWORD_PROD=redisUserPassword
    ```

3. **Запустите приложение** с помощью команды:
    ```bash
    docker-compose up -d
    ```

4. Приложение будет доступно по адресу:
    ```
    http://localhost:8081
    ```

---

## **Конфигурация**

### **Переменные окружения**

| Переменная              | Описание                              | Пример значения                       |
|-------------------------|---------------------------------------|---------------------------------------|
| `PG_URL_PROD`           | URL подключения к PostgreSQL          | `jdbc:postgresql://postgres-db:5432/operations` |
| `PG_USERNAME_PROD`      | Имя пользователя PostgreSQL           | `admin`                               |
| `PG_PASSWORD_PROD`      | Пароль пользователя PostgreSQL        | `admin123`                            |
| `REDIS_PORT_PROD`       | Порт Redis                            | `6379`                                |
| `REDIS_PASSWORD_PROD`   | Пароль для Redis                      | `redisPassword`                       |
| `REDIS_USER_PROD`       | Имя пользователя Redis                | `redisUser`                           |
| `REDIS_USER_PASSWORD_PROD` | Пароль пользователя Redis          | `redisUserPassword`                   |

### **Сеть**
Все сервисы используют сеть `app-network`, которая создаётся автоматически. Она обеспечивает взаимодействие между контейнерами.

---

## **API Документация**

### 1. **Получить список транзакций, по которым превышен лимит расходов**
- **Метод:** `GET`
- **URL:** `/transaction-limit/exceeding-limit`
- **Описание:** Возвращает список всех транзакций, по которым превышен лимит, актуальный на момент осуществления транзакции.
- **Пример ответа:**
    ```json
    [
    {
        "accountFrom": 1034567812,
        "accountTo": 1022456789,
        "currency": "RUB",
        "category": "PRODUCT",
        "transactionSum": 300000.89,
        "transactionTime": "2025-01-28T18:49:57.91587Z",
        "restLimitSum": -734.38,
        "limitValue": 7000.00,
        "limitCurrency": "USD",
        "limitSettingTime": "2025-01-27T18:04:09Z"
    },
    {
        "accountFrom": 1034567812,
        "accountTo": 1022456789,
        "currency": "RUB",
        "category": "PRODUCT",
        "transactionSum": 400000.89,
        "transactionTime": "2025-01-28T18:52:36.624259Z",
        "restLimitSum": -2853.02,
        "limitValue": 15000.00,
        "limitCurrency": "USD",
        "limitSettingTime": "2025-01-28T18:51:54.550641Z"
    },
    {
        "accountFrom": 1034567812,
        "accountTo": 1022456789,
        "currency": "KZT",
        "category": "SERVICE",
        "transactionSum": 300000.89,
        "transactionTime": "2025-01-28T18:50:14.268161Z",
        "restLimitSum": -489.16,
        "limitValue": 700.00,
        "limitCurrency": "USD",
        "limitSettingTime": "2025-01-27T18:04:09Z"
    },
    {
        "accountFrom": 1034567812,
        "accountTo": 1022456789,
        "currency": "USD",
        "category": "SERVICE",
        "transactionSum": 300.89,
        "transactionTime": "2025-01-28T18:51:43.113185Z",
        "restLimitSum": -291.52,
        "limitValue": 1500.00,
        "limitCurrency": "USD",
        "limitSettingTime": "2025-01-28T18:50:51.178337Z"
    }
]
    ```

### 2. **Добавить новый лимит по категории расходов**
- **Метод:** `POST`
- **URL:** `/transaction-limit/add-limit`
- **Описание:** Добавляет новый лимит расходов по категории. Он распространяется на транзакции, которые будут осуществлены после его добавления.
- **Пример запроса:**
    ```json
    {
    "category": "PRODUCT",
    "limitValue": 15000.00
}
    ```

    ### 3. **Передать данные о транзакции на уровне межсервисного взаимодействия**
- **Метод:** `POST`
- **URL:** `/api/transactions/expense-transaction`
- **Описание:** Обрабатывает входящие транзакции, устанавливая флаг (не)превышения лимита расходов.
- **Пример запроса:**
    ```json
    {
    "transactionSum": 400000.89,
    "accountFrom": 1034567812,
    "accountTo": 1022456789,
    "currency": "RUB", 
    "category": "PRODUCT"
}
    ```

---

## **Тестирование**

Для тестирования приложения выполните:

1. Запустите тесты локально:
    ```bash
    ./mvnw test
    ```

2. Или выполните тесты в контейнере:
    ```bash
    docker exec -it operation-service ./mvnw test
    ```

---

## **Остановка**

Чтобы остановить и удалить все контейнеры, выполните:

```bash
docker-compose down
