package ru.itk.app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.itk.app.model.Order;
import ru.itk.app.service.OrderService;

@Slf4j
@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public String createOrder(@RequestBody Order order) {
    return orderService.createOrder(order);
  }

  @PutMapping
  public String updateOrder(@RequestBody Order order) {
    return orderService.updateOrder(order);
  }
}