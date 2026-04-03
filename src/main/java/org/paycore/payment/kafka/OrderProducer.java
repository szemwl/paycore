package org.paycore.payment.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderProducer {

    @Value("${app.kafka.topics.orders}")
    private String ordersTopic;

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void sendOrderCreatedEvent(OrderEvent event) {
        log.info("Событие отправленно в топик '{}': {}", ordersTopic, event);
        kafkaTemplate.send(ordersTopic, event.getOrderId().toString(), event);
    }
}
