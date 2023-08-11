# Задача «Сервис авторизации»

## Описание

Сегодня вы реализуете сервис авторизации пользователей по логину и паролю. Ключевое в этом задании то, как ваше приложение будет реагировать на ошибки, которые ваш сервис будет выбрасывать в разных случаях.

Для работы нужно подготовить несколько классов.

**Шаг 0**. Создайте Spring Boot приложение и все классы контроллеры, сервисы и репозитории сделать бинами в вашем application context.

**Шаг 1**. Запрос на разрешения будет приходить на контроллер:

```java
@RestController
public class AuthorizationController {
    AuthorizationService service;
    
    @GetMapping("/authorize")
    public List<Authorities> getAuthorities(@RequestParam("user") String user, @RequestParam("password") String password) {
        return service.getAuthorities(user, password);
    }
}
``` 

**Шаг 2.** Класс-сервис, который будет обрабатывать введённые логин и пароль, выглядит так:

```java
public class AuthorizationService {
    UserRepository userRepository;

    List<Authorities> getAuthorities(String user, String password) {
        if (isEmpty(user) || isEmpty(password)) {
            throw new InvalidCredentials("User name or password is empty");
        }
        List<Authorities> userAuthorities = userRepository.getUserAuthorities(user, password);
        if (isEmpty(userAuthorities)) {
            throw new UnauthorizedUser("Unknown user " + user);
        }
        return userAuthorities;
    }

    private boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private boolean isEmpty(List<?> str) {
        return str == null || str.isEmpty();
    }
}
``` 
Он принимает в себя логин и пароль и возвращает разрешения для этого пользователя, если такой пользователь найден и данные валидны. Если присланные данные неверны, тогда выкидывается InvalidCredentials:

```java
public class InvalidCredentials extends RuntimeException {
    public InvalidCredentials(String msg) {
        super(msg);
    }
}
``` 

Если ваш репозиторий не вернул никаких разрешений, либо вернул пустую коллекцию, тогда выкидывается ошибка UnauthorizedUser:

```java
public class UnauthorizedUser extends RuntimeException {
    public UnauthorizedUser(String msg) {
        super(msg);
    }
}
``` 

Enum с разрешениями выглядит так:

```java
public enum Authorities {
    READ, WRITE, DELETE
}
``` 

**Шаг 3.** Код-метод getUserAuthorities класс UserRepository, который возвращает либо разрешения, либо пустой массив, надо реализовать вам.

```java
public class UserRepository {
    public List<Authorities> getUserAuthorities(String user, String password) {
        return ...;
    }
}
``` 

Для проверки работоспособности можно сделать запрос из браузера, заполнив `<ИМЯ_ЮЗЕРА>` и `<ПАРОЛЬ_ЮЗЕРА>` своими тестовыми данными:

localhost:8080/authorize?user=<ИМЯ_ЮЗЕРА>&password=<ПАРОЛЬ_ЮЗЕРА>

**Шаг 4.** Теперь, когда весь код у вас готов, вам нужно написать обработчики ошибок, которые выкидывает сервис `AuthorizationService`.

Требования к обработчикам ошибок:

* на `InvalidCredentials` он должен отсылать обратно клиенту HTTP-статус с кодом 400 и телом в виде сообщения из exception;
* на `UnauthorizedUser` он должен отсылать обратно клиенту HTTP-статус с кодом 401 и телом в виде сообщения из exception и писать в консоль сообщение из exception.

# Задача «Продвинутый сервис авторизации»* (со звёздочкой).

## Описание

**Задача — расширить функционал проедыдущего задания «Сервис авторизации».**

Теперь ваш контроллер должен принимать не два объекта отдельно, а один объект, содержащий значения user и password. Соответственно, и `AuthorizationService` теперь работает с одним объектом.
При этом API для клиента не изменилось, и он отправляет запрос вида: `localhost:8080/authorize?user=<ИМЯ_ЮЗЕРА>&password=<ПАРОЛЬ_ЮЗЕРА>`.

Также вы должны проверять объект на валидность с помощью аннотации @Valid. Подумайте, как вы должны валидировать поля объекта `User`:

```java
@RestController
public class AuthorizationController {
    AuthorizationService service;
    
    @GetMapping("/authorize")
    public List<Authorities> getAuthorities(@Valid User user) {
        return service.getAuthorities(user);
    }
}
``` 

Сделать преобразование одного объекта в два вы можете с помощью своего `HandlerMethodArgumentResolver` и, например, своей аннотации. 

# Задача «Прокси на NGINX»

## Описание

Реализуйте ваше первое приложение с обратным прокси перед ним. Напишите конфигурацию NGINX, который будет возвращать статический сайт. С помощью него можно обратиться к вашему сервису авторизации из прошлого домашнего задания.

**Шаг 1.** Сначала нужно создать html-форму для авторизации, которую вам будет возвращать NGINX. Этот файл нужно положить в соответствующую папку, откуда NGINX сможет её забрать.

```html
<html>
    <body>
        <h1>Sign in form</h1>
    
        <form action="/authorize" method="get" target="_blank">
          <label for="user">User name:</label>
          <input type="text" id="user" name="user"><br><br>
          <label for="password">Password:</label>
          <input type="text" id="password" name="password"><br><br>
          <button type="submit">Submit</button>
        </form>
    </body>
</html>
```

**Шаг 2.** Вам нужно написать конфигурацию для NGINX так, чтобы он при вызове http://localhost/signin возвращал вам эту html-страницу, а всё остальное он проксировал на ваше Spring Boot приложение, которое работает на порту 8080.

То, что вы напишите в конфигурации, добавьте в текстовый файл (формат файла любой, например, txt) в ваш проект с сервисом авторизации, запушьте в ваш репозиторий и пришлите ссылку на репозиторий.

# Задача «Dockerfile»

Соберите ваш первый Docker-образ на основе приложения авторизации, которое вы писали во [втором домашнем задании](../../spring_boot_rest/task1/README.md). Возьмите только серверное приложение без html из прошлого задания. Для этого вы сначала напишете ваш Dockerfile, а затем для удобства, напишете манифест для docker-compose.

## Описание

**Шаг 1.** Сначала вам надо собрать jar-архив с вашим Spring Boot приложением. Для этого в терминале, в корне вашего проекта выполните команду:

Для gradle: `./gradlew clean build` (если пишет Permission denied, тогда сначала выполните `chmod +x ./gradlew`).

Для maven: `./mvnw clean package` (если пишет Permission denied, тогда сначала выполните `chmod +x ./mvnw`).

**Шаг 2.** Теперь можно начинать писать `Dockerfile`. Базовым образом возьмите `openjdk:8-jdk-alpine` и не забудьте открыть для Docker порт(`EXPOSE`), на котором работает ваше приложение.

**Шаг 3.** Добавьте собранный jar в ваш образ(`ADD`). Если вы собирали с помощью maven, тогда jar будет лежать в папке `target`, а если gradle — в `build/libs`.

**Шаг 4**. Для удобства сборки образа и запуска контейнера вашего приложения напишите `docker-compose.yml`. Контейнер назовите, как вам нравится, а в его конфигурациях пропишите следующее:

- добавьте `build: ./`, который скажет docker-compose, что надо сначала собрать образ для этого контейнера;
- добавьте соответствие порта на хост машине и порта в контейнере для вашего приложения (аналог аргумента `-p` у команды `docker run`).

**Шаг 5.** Два полученных файла добавьте в репозиторий вашего приложения и пришлите ссылку на него.
