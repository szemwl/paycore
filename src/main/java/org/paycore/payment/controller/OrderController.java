package org.paycore.payment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.paycore.payment.model.Order;
import org.paycore.payment.model.OrderStatus;
import org.paycore.payment.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * Создание нового заказа.
     * Тело запроса валидируется через аннотации в модели Order.
     */
    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody(required = true) Order order) {
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    /**
     * Получение списка всех заказов.
     */
    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orderList = orderService.getAllOrders();
        return ResponseEntity.ok(orderList);
    }

    /**
     * Получение заказа по его идентификатору.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable UUID id) {
        Order foundOrder = orderService.getOrderById(id);
        return ResponseEntity.ok(foundOrder);
    }

    /**
     * Обновление статуса заказа.
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Order> updateStatus(@PathVariable UUID id,
                                              @RequestParam OrderStatus status) {
        Order updatedOrder = orderService.updateStatus(id, status);
        return ResponseEntity.ok(updatedOrder);
    }

    /**
     * Удаление заказа по идентификатору.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable UUID id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }
}
