package ru.charushnikov.microauthservice.integration.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

@Testcontainers
@TestConfiguration
public class PostgreSqlContainerConfig {

    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:12")
            .withDatabaseName("mydb")
            .withUsername("myuser")
            .withPassword("mypass")
            .withReuse(true);

    static {
        postgreSQLContainer.start();
    }

    public static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword(),
                    "spring.liquibase.enabled=true"
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    /**
     * целью является инициализация контекста приложения перед запуском тестов. В данном случае, он настраивает свойства
     * для подключения к базе данных, используя информацию из контейнера Docker, и включает Liquibase
     * для управления миграциями базы данных.
     */

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(postgreSQLContainer.getDriverClassName());
        dataSource.setUrl(postgreSQLContainer.getJdbcUrl());
        dataSource.setUsername(postgreSQLContainer.getUsername());
        dataSource.setPassword(postgreSQLContainer.getPassword());
        return dataSource;
    }
    /**
     * Это аннотация, указывающая на то, что метод dataSource() является спринговым бином,
     * который должен быть управляемым контейнером Spring. Метод создает и настраивает DataSource
     * (источник данных) для подключения к базе данных. Он использует информацию из контейнера Docker
     * для PostgreSQL для настройки подключения.
     */
}
