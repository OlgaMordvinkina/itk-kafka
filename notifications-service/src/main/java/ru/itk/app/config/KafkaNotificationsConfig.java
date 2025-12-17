package ru.itk.app.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaNotificationsConfig {

  @Bean
  public NewTopic notificationsDltTopic() {
    return TopicBuilder.name("sent_orders_notifications_dlt")
      .partitions(2)
      .replicas(1)
      .build();
  }
}
