##### Порядок запуска.
1. `config-server`
2. `discovery-server`
3. `gateway-server`
4. `yet-another-site`

> Запускаем каждый модуль командой `mvn spring-boot:run`

Для запуска `config-server` нужен репозиторий настроек.  
В моем случае, он расположен в директории `~/Documents/dev/geekbrains/spring2/spring-cloud-server-config-repo`

Указываем путь в `application.properties`.
> `spring.cloud.config.server.git.uri=file://${user.home}/Documents/dev/geekbrains/spring2/spring-cloud-server-config-repo`

##### Как выглядит репозиторий настроек.

* spring-cloud-server-config-repo
  * discovery-server.properties
  * gateway-server.properties
  * yet-another-site.properties

##### Что внутри.
*discovery-server.properties*
```properties
spring.application.name=discovery-server
server.port=8082
eureka.instance.hostname=localhost
eureka.client.serviceUrl.defaultZone=http://localhost:8082/eureka/
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```

*gateway-server.properties*
```properties
spring.application.name=gateway-server
server.port=8080
eureka.client.region=default
eureka.client.registryFetchIntervalSeconds=5

zuul.routes.yet-another-site.path=/**
zuul.routes.yet-another-site.sensitive-headers=Set-Cookie,Authorization
hystrix.command.yet-another-site.execution.isolation.thread.timeoutInMilliseconds=600000

zuul.routes.discovery-server.path=/discovery-server/**
zuul.routes.discovery-server.sensitive-headers=Set-Cookie,Authorization
zuul.routes.discovery-server.url=http://localhost:8082
hystrix.command.discovery-server.execution.isolation.thread.timeoutInMilliseconds=600000
```

*yet-another-site.properties*
```properties
spring.application.name=yet-another-site
server.port=8083
 
eureka.client.region=default
eureka.client.registryFetchIntervalSeconds=5
eureka.client.serviceUrl.defaultZone=http://localhost:8082/eureka/
```
