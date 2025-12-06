package com.links.linkservice.service;

import com.links.linkservice.dto.LinkClickEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.link-clicks}")
    private String topic;

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendLinkClickEvent(LinkClickEvent event) {
        CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send(topic, event.getAlias(), event);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Kafka message sent: alias={}, offset={}",
                        event.getAlias(),
                        result.getRecordMetadata().offset());
            } else {
                log.error("Failed to send kafka event", ex);
            }
        });
    }
}
