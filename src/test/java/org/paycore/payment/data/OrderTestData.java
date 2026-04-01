package org.paycore.payment.data;

import org.paycore.payment.model.Order;

import java.math.BigDecimal;
import java.util.UUID;

public final class OrderTestData {

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

    public static Order notValidOrder() {
        return Order
                .builder()
                .id(UUID.randomUUID())
                .sourceAccount("")
                .destinationAccount("")
                .amount(BigDecimal.ZERO)
                .currency("")
                .description("Test Invalid Payment For Service")
                .build();
    }

    public static Order negativeAmountOrder() {
        return Order
                .builder()
                .id(UUID.randomUUID())
                .sourceAccount("BANK001")
                .destinationAccount("BANK002")
                .amount(new BigDecimal("-0.01"))
                .currency("BYN")
                .description("Test Invalid Payment For Service")
                .build();
    }
}
