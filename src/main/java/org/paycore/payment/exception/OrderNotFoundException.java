package org.paycore.payment.exception;

import java.util.UUID;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(UUID id) {
        super("Заказ с id: " + id + " не найден.");
    }
}
