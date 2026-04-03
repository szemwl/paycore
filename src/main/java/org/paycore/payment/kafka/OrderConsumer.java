package org.paycore.payment.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.paycore.payment.service.PaymentProcessingService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderConsumer {

    private final PaymentProcessingService paymentProcessingService;

    @KafkaListener(topics = "${app.kafka.topics.orders}", groupId = "paycore-group")
    public void listen(OrderEvent event) {
        log.info("Получено Событие из Kafka: {}", event);
        UUID id = event.getOrderId();
        paymentProcessingService.process(id);
    }
}
