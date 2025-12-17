package ru.itk.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaNotificationsErrorConfig {

  public static final long INTERVAL = 1000L;
  public static final int MAX_ATTEMPTS = 3;

  @Bean
  public DefaultErrorHandler notificationsErrorHandler(KafkaTemplate<String, Object> kafkaTemplate) {
    DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate);
    return new DefaultErrorHandler(recoverer, new FixedBackOff(INTERVAL, MAX_ATTEMPTS));
  }
}
