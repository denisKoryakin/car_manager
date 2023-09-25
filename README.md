# Car manager service
___
### Описание
Приложение представляет собой серверную часть (back) справочника автомобилей.
Предназначен для работы с отдельным клиентским приложением.
___
### Первый запуск
Запуск производится через команды Docker:

Сборка image:

    docker build

Поднятие контенеров:

    docker compose up
___
### Добавление нового автомобиля
После первого запуска необходимо добавить автомобили путем отправки POST 
запроса с телом JSON на адрес: http://localhost:8080/cars

Пример тела:

    {
    "carNumber": "123",
    "brand": "Opel",
    "color": "green",
    "releaseYear": 2010
    }

Хранение записей производится в PostgreSQL:

    public.car

---
### Функционал: 
 - отображение списка автомобилей (имеется кэширование запроса), также возможно добавления Query для сортировки (до трех параметров):


      method: GET,  endpoint: /cars?brandSort=anyString&colorSort=anyString&releaseYearSort=anyString
   
 - добавление автомобилей


      method: POST,  MediaType: JSON,  endpoint: /cars

 - удаление автомобиля по id (id известно при выполнении метода GET на эндпоинт /cars )


      method: DELETE,  endpoint: /cars/id

-  получение статистики БД


      method: GET,  endpoint: /statistic
---
### Архитектура:
 - REST
 - 3 layers
---
### Стек:
 - Spring Boot
 - PostgreSQL
 - Docker
 - JUnit5
 - testcontainer (integration test with PostgreSQL)
 - GIT
