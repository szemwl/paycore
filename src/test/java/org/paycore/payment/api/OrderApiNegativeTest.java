package org.paycore.payment.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.paycore.payment.model.OrderStatus;
import org.paycore.payment.spec.ResponseSpecs;

import java.util.UUID;

@Tag("api")
@DisplayName("Негативные тесты API для заказов")
public class OrderApiNegativeTest extends BaseTest {

    @Test
    @DisplayName("Запрос несуществующего заказа")
    void shouldReturn404WhenRequestingNonExistingOrder() {
        UUID nonExistingId = UUID.randomUUID();
        orderSteps.getOrderById(nonExistingId)
                .spec(ResponseSpecs.notFound404());
    }

    @Test
    @DisplayName("Обновление несуществующего заказа")
    void shouldReturn404WhenUpdatingNonExistingOrder() {
        UUID nonExistingId = UUID.randomUUID();
        OrderStatus status = OrderStatus.FAILED;

        orderSteps.updateOrderById(nonExistingId, status)
                .spec(ResponseSpecs.notFound404());
    }

    @Test
    @DisplayName("Удаление несуществующего заказа")
    void shouldReturn404WhenDeletingNonExistingOrder() {
        UUID nonExistingId = UUID.randomUUID();

        orderSteps.deleteOrderById(nonExistingId)
                .spec(ResponseSpecs.notFound404());
    }

    @Test
    @DisplayName("Повторное удаление уже удалённого заказа")
    void shouldReturn404WhenDeletingAlreadyDeletedOrder() {

    }

    @Test
    @DisplayName("Повторное обновление уже удалённого заказа")
    void shouldReturn404WhenUpdatingAlreadyDeletedOrder() {

    }
}
