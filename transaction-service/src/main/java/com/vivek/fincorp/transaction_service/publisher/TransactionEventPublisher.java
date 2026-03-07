// package com.vivek.fincorp.transaction_service.publisher;

// import org.springframework.kafka.core.KafkaTemplate;
// import org.springframework.stereotype.Component;

// import com.vivek.fincorp.transaction_service.event.TransactionCompletedEvent;

// import lombok.RequiredArgsConstructor;

// @Component
// @RequiredArgsConstructor
// public class TransactionEventPublisher {

//     private static final String TOPIC = "transaction-events";

//     private final KafkaTemplate<String, TransactionCompletedEvent> kafkaTemplate;

//     public void publish(TransactionCompletedEvent event) {
//         kafkaTemplate.send(TOPIC, event.transactionId(), event);
//     }
// }
