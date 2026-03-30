package org.paycore.payment.data;

import org.paycore.payment.model.Order;

import java.math.BigDecimal;
import java.util.UUID;

public final class OrderTestData {

    private OrderTestData() {
    }

    public static Order validOrder() {
        return Order
                .builder()
                .id(UUID.randomUUID())
                .sourceAccount("BANK001")
                .destinationAccount("BANK002")
                .amount(new BigDecimal("100.95"))
                .currency("BYN")
                .description("Test Payment For Service")
                .build();
    }
}
