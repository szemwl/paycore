package org.paycore.payment.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderProducer {

    private static final String ORDER_TOPIC = "orders";

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void sendOrderCreatedEvent(OrderEvent event) {
        log.info("Событие отправленно в топик '{}': {}", ORDER_TOPIC, event);
        kafkaTemplate.send(ORDER_TOPIC, event.getOrderId().toString(), event);
    }
}
