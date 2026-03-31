package org.paycore.payment.api;

import org.junit.jupiter.api.Test;
import org.paycore.payment.data.OrderTestData;
import org.paycore.payment.model.Order;
import org.paycore.payment.spec.ResponseSpecs;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;

public class PaymentApiSmokeTest extends BaseTest {

    @Test
    void shouldCreatePayment() {
        Order order = OrderTestData.validOrder();

        orderSteps.createOrder(order)
                .spec(ResponseSpecs.ok201());
    }

    @Test
    void shouldGetPaymentById() {
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
}
