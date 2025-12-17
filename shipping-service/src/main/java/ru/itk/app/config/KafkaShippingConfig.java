
package ru.itk.app.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaShippingConfig {

  @Bean
  public NewTopic shippingDltTopic() {
    return TopicBuilder.name("sent_orders_dlt")
      .partitions(3)
      .replicas(1)
      .build();
  }
}
