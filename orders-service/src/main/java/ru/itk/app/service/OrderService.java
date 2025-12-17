package ru.itk.app.service;

import ru.itk.app.model.Order;

public interface OrderService {
  String createOrder(Order order);

  String updateOrder(Order order);
}
