package com.lightning.temu.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(value = {SpringLiquibase.class, HikariDataSource.class})
@ConditionalOnProperty(prefix = "spring", name = "liquibase.enabled", matchIfMissing = true)
public class LiquibaseConfiguration {

  /**
   * Assemble liquibase configuration information starting with "spring.liquibase".
   *
   * @return bean liquibaseProperties of {@link LiquibaseProperties}.
   */
  @Bean
  @ConfigurationProperties(prefix = "spring.liquibase")
  public LiquibaseProperties liquibaseProperties() {
    return new LiquibaseProperties();
  }

  /**
   * Produce a <code>Bean</code> of {@link SpringLiquibase}.
   *
   * @param dataSource final DataSource.
   * @return bean springLiquibase of {@link SpringLiquibase}.
   */
  @Bean
  public SpringLiquibase springLiquibase(DataSource dataSource) {
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setChangeLog(liquibaseProperties().getChangeLog());
    liquibase.setDataSource(dataSource);
    liquibase.setShouldRun(true);
    return liquibase;
  }
}
