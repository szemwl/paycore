package org.paycore.payment.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderConsumer {

    @KafkaListener(topics = "orders", groupId = "paycore-group")
    public void listen(OrderEvent event) {
        log.info("Получено Событие из Kafka: {}", event);
    }
}
