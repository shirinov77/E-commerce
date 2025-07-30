package org.example.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/ecommerce_db");
        dataSource.setUsername("postgres");
        dataSource.setPassword("1111");
        dataSource.setInitialSize(5);        // Boshlang'ich ulanishlar soni
        dataSource.setMaxTotal(20);          // Maksimal ulanishlar soni
        dataSource.setMaxIdle(10);           // Maksimal holatdagi idle ulanishlar
        dataSource.setMinIdle(5);            // Minimal holatdagi idle ulanishlar
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("org.example.model"); // Entity sinflaringiz paketini to'g'ri ko'rsating
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(hibernateProperties());
        return em;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty("hibernate.hbm2ddl.auto", "update"); // Rivojlanish uchun, ishlashda "validate" ni ko'rib chiqing
        properties.setProperty("hibernate.show_sql", "true");       // SQL so'rovlarini konsolda ko'rsatish
        properties.setProperty("hibernate.format_sql", "true");     // SQL formatlash
        return properties;
    }
}