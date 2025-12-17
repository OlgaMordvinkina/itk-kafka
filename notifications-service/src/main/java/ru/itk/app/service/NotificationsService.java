package ru.itk.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.itk.app.model.PaymentEvent;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationsService {

  @KafkaListener(
    topics = "${kafka.notifications.consumer.topic}",
    groupId = "${kafka.notifications.consumer.group-id}",
    concurrency = "${kafka.notifications.consumer.concurrency}"
  )
  public void notifyUser(PaymentEvent event) {
    log.info("Уведомление пользователя: заказ {} имеет статус {}", event.getOrderId(), event.getStatus());
    // какая-то логика дальше
  }
}
