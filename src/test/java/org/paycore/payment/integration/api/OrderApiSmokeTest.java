package org.paycore.payment.integration.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.paycore.payment.data.OrderTestData;
import org.paycore.payment.model.Order;
import org.paycore.payment.model.OrderStatus;
import org.paycore.payment.spec.ResponseSpecs;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;

@Tag("api")
@DisplayName("Smoke тесты API для заказов")
public class OrderApiSmokeTest extends BaseApiTest {

    @Test
    @DisplayName("Создание заказа")
    void shouldCreateOrder() {
        Order order = OrderTestData.validOrder();

        orderSteps.createOrder(order)
                .spec(ResponseSpecs.ok201());
    }

    @Test
    @DisplayName("Получение заказа по id")
    void shouldGetOrderById() {
        Order order = OrderTestData.validOrder();

        String id = orderSteps.createOrder(order)
                .spec(ResponseSpecs.ok201())
                .extract()
                .path("id")
                .toString();

        orderSteps.getOrderById(UUID.fromString(id))
                .spec(ResponseSpecs.ok200())
                .body("id", equalTo(id))
                .body("currency", equalTo(order.getCurrency()))
                .body("description", equalTo(order.getDescription()));
    }

    @Test
    @DisplayName("Обновление заказа по id")
    void shouldUpdateOrderById() {
        Order order = OrderTestData.validOrder();

        String id = orderSteps.createOrder(order)
                .spec(ResponseSpecs.ok201())
                .extract()
                .path("id")
                .toString();

        orderSteps.updateOrderById(UUID.fromString(id), OrderStatus.COMPLETED)
                .spec(ResponseSpecs.ok200());

        orderSteps.getOrderById(UUID.fromString(id))
                .spec(ResponseSpecs.ok200())
                .body("id", equalTo(id))
                .body("status", equalTo(OrderStatus.COMPLETED.name()));
    }
}
