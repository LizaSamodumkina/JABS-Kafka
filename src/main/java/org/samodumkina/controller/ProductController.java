package org.samodumkina.controller;

import org.samodumkina.producer.ProductProducer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {

  private final ProductProducer productProducer;

  public ProductController(ProductProducer productProducer) {
    this.productProducer = productProducer;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createProduct(@RequestBody ProductDTO productDTO) {
    productProducer.sendMessage(productDTO);
  }
}
