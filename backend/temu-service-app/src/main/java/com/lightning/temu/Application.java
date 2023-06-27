package com.lightning.temu;

import com.lightning.support.ObjectMappers;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.lightning"})
@EnableRetry
@EnableScheduling
@EnableTransactionManagement
public class Application {

  private static final Logger log = LoggerFactory.getLogger(Application.class);

  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  ProtobufHttpMessageConverter protobufHttpMessageConverter() {
    return new ProtobufHttpMessageConverter();
  }

  /**
   * Register a bean of MappingJackson2HttpMessageConverter.
   *
   * @return MappingJackson2HttpMessageConverter bean.
   */
  @Bean
  public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    converter.setObjectMapper(ObjectMappers.get());
    return converter;
  }

  /**
   * Register a bean of ApplicationContext.
   *
   * @param ctx given context of ApplicationContext.
   * @return ApplicationContext bean.
   */
  @Bean
  public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
    return args -> {

      log.info("Let's inspect the beans provided by Spring Boot:");

      String[] beanNames = ctx.getBeanDefinitionNames();
      Arrays.sort(beanNames);
      for (String beanName : beanNames) {
        log.trace(beanName);
      }

    };
  }
}