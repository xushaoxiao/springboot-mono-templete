package com.lightning.temu.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * BackendDatabaseSupport support mysql database configuration and initial connection work.
 */
@Configuration
@EnableJpaRepositories(
    basePackages = "com.lightning.temu.repository"
)
@ConfigurationProperties(prefix = "spring.datasource.hikari")
public class DatabaseSupport extends HikariConfig {

  @Autowired
  private Environment env;

  /**
   * Initialize and build a datasource of type {@link HikariDataSource}.
   *
   * @return bean dataSource of {@link DataSource}.
   */
  @Bean
  @Primary
  public DataSource dataSource() {
    return new HikariDataSource(this);
  }

  /**
   * Initialize and build an entity manager of type {@link LocalContainerEntityManagerFactoryBean}.
   *
   * @param dataSource specified dataSource bean.
   * @return bean entityManagerFactory of {@link LocalContainerEntityManagerFactoryBean}.
   */
  @Bean
  @Primary
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      @Qualifier("dataSource") final DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean manager = new LocalContainerEntityManagerFactoryBean();
    manager.setDataSource(dataSource);
    manager.setPackagesToScan("com.lightning.temu");
    HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
    manager.setJpaVendorAdapter(adapter);
    HashMap<String, Object> properties = new HashMap<>();
    properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
    properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
    manager.setJpaPropertyMap(properties);
    return manager;
  }

  /**
   * Initialized and build a transaction manager of type {@link PlatformTransactionManager}.
   *
   * @param entityManager specified entityManager.
   * @return bean transactionManager of {@link JpaTransactionManager}.
   */
  @Bean
  @Primary
  public PlatformTransactionManager transactionManager(
      @Qualifier("entityManagerFactory") final LocalContainerEntityManagerFactoryBean entityManager) {
    JpaTransactionManager manager = new JpaTransactionManager();
    manager.setEntityManagerFactory(entityManager.getObject());
    return manager;
  }

  /**
   * Initialized and build a jdbcTemplate of type {@link JdbcTemplate}.
   *
   * @param dataSource specified dataSource.
   * @return bean coverJdbcTemplate of {@link JdbcTemplate}.
   */
  @Bean
  @Primary
  public JdbcTemplate coverJdbcTemplate(@Qualifier("dataSource") final DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }
}
