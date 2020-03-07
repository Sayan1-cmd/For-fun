package com.project.project.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@EnableJpaRepositories(basePackages = "com.project.project.repository")
@EntityScan("com.project.project.entity")
@Configuration
public class ProjectConfig {

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder<?> builder = DataSourceBuilder.create();
        builder.driverClassName("org.h2.Driver");
        builder.username("sa");
        builder.password("");
        builder.url("jdbc:h2:mem:test");
        return builder.build();
    }

    @Bean
    public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        template.setCacheLimit(0);
        return template;
    }
}
