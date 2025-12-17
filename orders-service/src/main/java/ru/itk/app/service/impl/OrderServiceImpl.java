package ru.itk.app.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.itk.app.model.Order;
import ru.itk.app.service.OrderService;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private static final String TOPIC = "new_orders";
  private final KafkaTemplate<String, Order> kafkaTemplate;

  @Override
  public String createOrder(Order order) {
    log.info("Получен новый заказ: {}", order);

    sendToKafka(order);

    return "Заказ принят: " + order.getId();
  }

  @Override
  public String updateOrder(Order order) {
    log.info("Обновлён заказ: {}", order);

    sendToKafka(order);

    return "Заказ обновлён: " + order.getId();
  }

  private void sendToKafka(Order order) {
    kafkaTemplate.send(TOPIC, order.getId(), order);
    log.info("Заказ отправлен в Kafka: {}", order.getId());
  }
}
