package ru.itk.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.itk.app.model.Order;
import ru.itk.app.model.PaymentEvent;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

  private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;
  private static final String TOPIC = "payed_orders";

  @KafkaListener(
    topics = "${kafka.orders.consumer.topic}",
    groupId = "${kafka.orders.consumer.group-id}",
    concurrency = "${kafka.orders.consumer.concurrency}"
  )
  public void processOrder(Order order) {
    log.info("Начата обработка оплаты заказа: {}", order.getId());
    PaymentEvent event = new PaymentEvent(order.getId(), "PAID");
    kafkaTemplate.send(TOPIC, order.getId(), event);
  }
}
