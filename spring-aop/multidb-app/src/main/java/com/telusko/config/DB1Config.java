package com.telusko.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "db1EntityManager",
transactionManagerRef = "db1TransactionManager",
basePackages = "com.telusko.repo.cx")
public class DB1Config
{
    @Bean
    @ConfigurationProperties(prefix = "db1.datasource")
    public DataSource db1DataSource()
    {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean db1EntityManager(EntityManagerFactoryBuilder emfb)
    {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hdm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        return emfb.dataSource(db1DataSource())
                .packages("com.telusko.entity.cx")
                .persistenceUnit("teluskodb")
                .properties(properties)
                .build();

    }

    @Bean
    public PlatformTransactionManager db1TransactionManager(@Qualifier("db1EntityManager") EntityManagerFactory factory)
    {
        return new JpaTransactionManager(factory);
    }
}
