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
import static org.paycore.payment.model.OrderStatus.*;

@Tag("api")
@DisplayName("Тесты API смены статусов заказа")
public class OrderStatusApiTest extends BaseApiTest {

    @Test
    void shouldSetProcessingStatusAfterOrderCreated() {
        Order order = OrderTestData.validOrder();

        UUID id = UUID.fromString(
                orderSteps.createOrder(order)
                        .spec(ResponseSpecs.ok201())
                        .body("status", equalTo(CREATED.name()))
                        .extract()
                        .path("id")
                        .toString()
        );

        await()
                .atMost(10, TimeUnit.SECONDS)
                .pollInterval(1, TimeUnit.SECONDS)
                .untilAsserted(() ->
                        orderSteps.getOrderById(id)
                                .spec(ResponseSpecs.ok200())
                                .body("status", equalTo(PROCESSING.name()))
                );
    }

    @Test
    void shouldNotChangeStatusWhenOrderAlreadyInCompletedStatus() {
        Order order = OrderTestData.validOrder();

        String id = orderSteps.createOrder(order)
                .spec(ResponseSpecs.ok201())
                .body("status", equalTo(CREATED.name()))
                .extract()
                .path("id")
                .toString();

        await()
                .atMost(10, TimeUnit.SECONDS)
                .pollInterval(1, TimeUnit.SECONDS)
                .untilAsserted(() ->
                        orderSteps.getOrderById(UUID.fromString(id))
                                .spec(ResponseSpecs.ok200())
                                .body("status", equalTo(COMPLETED.name()))
                );

        orderSteps.updateOrderById(UUID.fromString(id), PROCESSING)
                .spec(ResponseSpecs.notValid400());

        orderSteps.getOrderById(UUID.fromString(id))
                .spec(ResponseSpecs.ok200())
                .body("status", equalTo(COMPLETED.name()));
    }

    @Test
    @DisplayName("Успешное завершение основного сценария заказа")
    void shouldProcessOrderToCompletedStatusAfterAsyncHandling() {
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
