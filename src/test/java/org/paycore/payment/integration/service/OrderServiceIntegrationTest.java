package org.paycore.payment.integration.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.paycore.payment.integration.api.BaseApiTest;
import org.paycore.payment.data.OrderTestData;
import org.paycore.payment.model.Order;
import org.paycore.payment.spec.ResponseSpecs;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.paycore.payment.model.OrderStatus.PROCESSING;

@Tag("service")
@DisplayName("Тесты бизнес-логики для заказов")
public class OrderServiceIntegrationTest {

    @Test
    void shouldRejectInvalidStatusTransitionFromProcessingToCreated() {

    }

    @Test
    void shouldNotChangeStatusWhenOrderAlreadyInCompletedStatus() {

    }
}
