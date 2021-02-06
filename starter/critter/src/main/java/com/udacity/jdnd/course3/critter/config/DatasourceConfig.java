package com.udacity.jdnd.course3.critter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties("com.udacity.datasource")
    public DataSource getDatasource() {
        DataSourceBuilder dsb = DataSourceBuilder.create();
        dsb.url("jdbc:mysql://localhost:33060/critter");

        return dsb.build();
    }
}
