package org.paycore.payment.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

@Tag("api")
@DisplayName("Negative тесты API для заказов")
public class OrderApiNegativeTest {
}

/*
Не про валидацию тела, а про негативные кейсы API:
- запрос несуществующего заказа
- обновление несуществующего заказа
- удаление несуществующего заказа
- повторное удаление / повторное обновление
 */