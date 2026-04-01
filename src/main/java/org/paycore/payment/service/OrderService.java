package org.paycore.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.paycore.payment.exception.OrderNotFoundException;
import org.paycore.payment.kafka.OrderEvent;
import org.paycore.payment.kafka.OrderProducer;
import org.paycore.payment.model.Order;
import org.paycore.payment.model.OrderStatus;
import org.paycore.payment.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    public Order createOrder(Order order) {
        log.info("Получен запрос на создание заказа: {}", order);

        order.setStatus(OrderStatus.CREATED);

        Order savedOrder = orderRepository.save(order);
        log.info("Заказ сохранён в БД с id: {}", savedOrder.getId());

        OrderEvent event = OrderEvent.builder()
                .orderId(savedOrder.getId())
                .sourceAccount(savedOrder.getSourceAccount())
                .destinationAccount(savedOrder.getDestinationAccount())
                .amount(savedOrder.getAmount())
                .currency(savedOrder.getCurrency())
                .status(savedOrder.getStatus())
                .build();

        orderProducer.sendOrderCreatedEvent(event);
        log.info("OrderEvent отправлен в Kafka для заказа с id: {}", savedOrder.getId());

        return savedOrder;
    }

    public List<Order> getAllOrders() {
        log.info("Получен список всех заказов");
        return orderRepository.findAll();
    }

    public Order getOrderById(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        log.info("Найден заказ с id: {}", id);
        return order;
    }

    public Order updateStatus(UUID id, OrderStatus status) {
        log.info(
                "Получен запрос на обновление статуса заказа. id: {}, новый статус: {}",
                id,
                status
        );

        Order order = getOrderById(id);
        order.setStatus(status);
        order.setUpdatedAt(LocalDateTime.now());

        Order updatedOrder = orderRepository.save(order);
        log.info(
                "Статус заказа успешно обновлён. id: {}, статус: {}",
                updatedOrder.getId(),
                updatedOrder.getStatus()
        );

        return updatedOrder;
    }

    public void deleteOrderById(UUID id) {
        log.info("Получен запрос на удаление заказа с id: {}", id);
        Order order = getOrderById(id);

        orderRepository.deleteById(order.getId());
        log.info("Успешно удалён Заказ с id: {}", id);
    }
}
