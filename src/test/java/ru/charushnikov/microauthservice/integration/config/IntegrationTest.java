package ru.charushnikov.microauthservice.integration.config;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//Параметр replace установлен в NONE, что означает, что необходимо использовать реальную базу данных,
// а не заменять ее на встроенную или временную.
@ContextConfiguration(classes = {PostgreSqlContainerConfig.class}, initializers = {PostgreSqlContainerConfig.Initializer.class})
//указывает конфигурационные классы и инициализаторы контекста, которые должны быть загружены при выполнении тестов. PostgreSqlContainerConfig.class
// вероятно содержит конфигурацию для использования контейнера Docker с PostgreSQL в тестах,
// а PostgreSqlContainerConfig.Initializer.class - инициализатор для этого контейнера.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// используется для запуска тестового контекста Spring Boot и указывает, что необходимо использовать случайный порт для веб-сервера.
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
//предоставляют информацию о том, что аннотация применяется к типам (классам) и сохраняется во время выполнения программы соответственно.
public @interface IntegrationTest {
}
