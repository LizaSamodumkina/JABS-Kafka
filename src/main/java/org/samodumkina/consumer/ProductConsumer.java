package org.samodumkina.consumer;

import org.samodumkina.product.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ProductConsumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductConsumer.class);

  @KafkaListener(topics = "${spring.kafka.topic.avro}", groupId = "local")
  public void receive(@Payload Product product, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
    LOGGER.info("Received message={} from partition={}", product, partition);
  }

}
