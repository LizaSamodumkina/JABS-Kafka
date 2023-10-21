package org.samodumkina.producer;

import java.util.concurrent.CompletableFuture;
import org.samodumkina.controller.ProductDTO;
import org.samodumkina.product.Product;
import org.samodumkina.product.ProductType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Component
public class ProductProducer {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductProducer.class);

  private final KafkaTemplate<String, Product> kafkaTemplate;
  @Value("${spring.kafka.topic.avro}")
  private String avroTopic;

  public ProductProducer(KafkaTemplate<String, Product> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendMessage(ProductDTO productDTO) {
    Product product = Product.newBuilder()
        .setProductId(productDTO.id())
        .setName(productDTO.name())
        .setPrice(productDTO.price())
        .setProductType(ProductType.valueOf(productDTO.productType()))
        .setDescription(productDTO.description())
        .build();

    LOGGER.info("Producing message -> {}", product);

    CompletableFuture<SendResult<String, Product>> sent = kafkaTemplate.send(avroTopic, product);
    sent.whenComplete((result, ex) -> {
      if (ex != null) {
        LOGGER.error("Unable to send message due to: {}", ex.getMessage());
      } else {
        LOGGER.info("Sent message {} to topic={} with offset={}", result.getProducerRecord().value(),
            result.getRecordMetadata().topic(), result.getRecordMetadata().offset());
      }
    });
  }
}
