package org.paycore.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.paycore.payment.kafka.OrderEvent;
import org.paycore.payment.kafka.OrderProducer;
import org.paycore.payment.model.Order;
import org.paycore.payment.model.OrderStatus;
import org.paycore.payment.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    public Order createOrder(Order order) {
        log.info("Создан новый заказ: {}", order);

        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

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

    public Order getOrderById(UUID id) {
        log.info("Найден Заказ с id: {}", id);
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Заказ не найден по id: " + id));
    }

    public Order updateStatus(UUID id, OrderStatus status) {
        log.info("Обновлён статус заказа. Заказ с id: {}, новый статус: {}", id, status);

        Order order = getOrderById(id);
        order.setStatus(status);
        order.setUpdatedAt(LocalDateTime.now());

        Order updatedOrder = orderRepository.save(order);
        log.info("Статус заказа был успешно обновлён. Заказ с id: {}, статус: {}", updatedOrder.getId(), updatedOrder.getStatus());

        return updatedOrder;
    }

    public void deleteOrderById(UUID id) {
        orderRepository.deleteById(id);
        log.info("Успешно удалён Заказ с id: {}", id);
    }
}
