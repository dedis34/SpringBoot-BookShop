package org.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.MySQLContainer;

@Component
public class CustomMySqlContainer extends MySQLContainer<CustomMySqlContainer> {

    private static final String DEFAULT_DB_IMAGE = "mysql:8.0";

    @Value("${MYSQL_IMAGE:mysql:8.0}")
    private String dbImage;

    private CustomMySqlContainer() {
        super(DEFAULT_DB_IMAGE);
    }

    private static class Holder {
        private static final CustomMySqlContainer INSTANCE = new CustomMySqlContainer();
    }

    public static CustomMySqlContainer getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("spring.datasource.url", getJdbcUrl());
        System.setProperty("spring.datasource.username", getUsername());
        System.setProperty("spring.datasource.password", getPassword());
    }

    @Override
    public void stop() {
    }
}
