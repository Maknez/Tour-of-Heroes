package com.meshpy.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan("com.meshpy")
@PropertySource({ "classpath:persistence-mysql.properties" })
public class AppConfig {

    @Autowired
    private Environment environment;

    private Logger logger = Logger.getLogger(getClass().getName());

    // define a bean for ViewResolver
    @Bean
    public DataSource dataSource() {

        // create connection pool
        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        // set JDBC driver
        try {
            dataSource.setDriverClass("com.mysql.jdbc.Driver");
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }

        // let's log url and user - just to make sure we are reading data
        logger.info("jdbc.url=" + environment.getProperty("jdbc.url"));
        logger.info("jdbc.user=" + environment.getProperty("jdbc.user"));


        // set database connection properties
        dataSource.setJdbcUrl(environment.getProperty("jdbc.url"));
        dataSource.setUser(environment.getProperty("jdbc.user"));
        dataSource.setPassword(environment.getProperty("jdbc.password"));

        // set connection pool properties
        dataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
        dataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
        dataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
        dataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));

        return dataSource;
    }

    private Properties getHibernateProperties() {

        // set hibernate properties
        Properties properties = new Properties();

        properties.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        properties.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));

        return properties;
    }

    // need a helper method
    // read environment property and convert to int
    private int getIntProperty(String propertyName) {
        String propertyValue = environment.getProperty(propertyName);
        int intPropertyValue = Integer.parseInt(propertyValue);
        return intPropertyValue;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {

        // create session factory
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        // set the properties
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(environment.getProperty("hibernate.packagesToScan"));
        sessionFactory.setHibernateProperties(getHibernateProperties());

        return sessionFactory;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {

        // setup transaction manager based on section factory
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);

        return transactionManager;
    }
}
