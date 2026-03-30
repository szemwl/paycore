package org.paycore.payment.api;

import org.junit.jupiter.api.Test;
import org.paycore.payment.data.OrderTestData;
import org.paycore.payment.model.Order;
import org.paycore.payment.spec.ResponseSpecs;

public class PaymentApiSmokeTest extends BaseTest {

    @Test
    void shouldCreatePayment() {
        Order order = OrderTestData.validOrder();

        orderSteps.createOrder(order)
                .spec(ResponseSpecs.ok201());
    }

    @Test
    void shouldGetPaymentById() {

    }
}
