package ru.charushnikov.microauthservice.fiegn;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient("${feign.client.storage-service.name}")
public interface StorageServiceClient {

    @GetMapping("/storage-service/products")
    ResponseEntity<Integer> getQuantityGoods(@RequestParam("productId") Long productId);
}
