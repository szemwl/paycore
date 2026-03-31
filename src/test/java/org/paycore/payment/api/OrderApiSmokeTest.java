package org.paycore.payment.api;

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
public class OrderApiSmokeTest extends BaseTest {

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

        orderSteps.updateOrderById(UUID.fromString(id), "COMPLETED")
                .spec(ResponseSpecs.ok200());

        orderSteps.getOrderById(UUID.fromString(id))
                .spec(ResponseSpecs.ok200())
                .body("id", equalTo(id))
                .body("status", equalTo("COMPLETED"));
    }

    @Test
    @DisplayName("Успешное завершение основного сценария заказа")
    void shouldProcessOrderToCompletedStatus() {
        Order order = OrderTestData.validOrder();

        String id = orderSteps.createOrder(order)
                .spec(ResponseSpecs.ok201())
                .extract()
                .path("id")
                .toString();

        await()
                .atMost(10, TimeUnit.SECONDS)
                .pollInterval(1, TimeUnit.SECONDS)
                .untilAsserted(() ->
                        orderSteps.getOrderById(UUID.fromString(id))
                                .spec(ResponseSpecs.ok200())
                                .body("id", equalTo(id))
                                .body("status", equalTo(OrderStatus.COMPLETED.name()))
                );
    }
}
