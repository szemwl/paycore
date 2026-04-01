package org.paycore.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.paycore.payment.model.Order;
import org.paycore.payment.model.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentProcessingService {

    private final OrderService orderService;

    public void process(UUID id) {
        Order order = orderService.getOrderById(id);

        if (!order.getStatus().equals(OrderStatus.CREATED)) {
            log.info("Заказ с id: {} - пропущен, потому что статус не CREATED", id);
            return;
        }

        orderService.updateStatus(id, OrderStatus.PROCESSING);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            orderService.updateStatus(id, OrderStatus.FAILED);
            log.info("Не успешная обработка Заказа с id: {}", id);
            throw new RuntimeException(ex);
        }

        if (!order.getCurrency().equals("BYN")) {
            orderService.updateStatus(id, OrderStatus.FAILED);
        } else {
            orderService.updateStatus(id, OrderStatus.COMPLETED);
        }
    }
}
