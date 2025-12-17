package ru.itk.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.itk.app.model.PaymentEvent;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShippingService {

  private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;
  private static final String TOPIC = "sent_orders";

  @KafkaListener(
    topics = "${kafka.shipping.consumer.topic}",
    groupId = "${kafka.shipping.consumer.group-id}",
    concurrency = "${kafka.shipping.consumer.concurrency}"
  )
  public void shipOrder(PaymentEvent event) {
    log.info("Начата отгрузка заказа: {}", event.getOrderId());
    log.info("Отгрузка заказа {}", event.getOrderId());
//    throw new RuntimeException("Имитация ошибки");
    kafkaTemplate.send(TOPIC, event.getOrderId(), new PaymentEvent(event.getOrderId(), "SENT"));
  }
}
