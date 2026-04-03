package org.paycore.payment.integration.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.paycore.payment.data.OrderTestData;
import org.paycore.payment.model.Order;
import org.paycore.payment.spec.ResponseSpecs;

@Tag("api")
@DisplayName("Validation тесты API для заказов")
public class OrderApiValidationTest extends BaseApiTest {

    @Test
    @DisplayName("Создание заказа с пустым телом")
    void shouldNotCreateWithEmptyBody() {
        orderSteps.createOrderWithoutBody()
                .spec(ResponseSpecs.notValid400());
    }

    @Test
    @DisplayName("Создание заказа с пустыми обязательными полями")
    void shouldNotCreateWithEmptyRequiredFields() {
        Order invalidOrder = OrderTestData.notValidOrder();

        orderSteps.createOrder(invalidOrder)
                .spec(ResponseSpecs.notValid400());
    }

    @Test
    @DisplayName("Создание заказа с отрицательной суммой")
    void shouldNotCreateWithNegativeAmount() {
        Order invalidOrder = OrderTestData.negativeAmountOrder();

        orderSteps.createOrder(invalidOrder)
                .spec(ResponseSpecs.notValid400());
    }
}
