package ru.itk.app.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicsConfig {

  public static final String TOPIC_NAME = "new_orders";

  @Value("${kafka.topics.new-orders.partitions:3}")
  private int newOrdersPartitions;

  @Bean
  public NewTopic newOrdersTopic() {
    return TopicBuilder.name(TOPIC_NAME)
      .partitions(newOrdersPartitions)
      .replicas(1)
      .build();
  }
}
