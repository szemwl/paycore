package org.paycore.payment.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

@Tag("service")
@DisplayName("Тесты бизнес-логики для заказов")
public class OrderStatusTest {
}

/*
Уже есть маленькая бизнес-логика:
- корректный переход статуса
- некорректный переход статуса
- попытка перевести заказ в финальный/недопустимый статус
 */