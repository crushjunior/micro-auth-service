package ru.charushnikov.microauthservice.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.charushnikov.microauthservice.fiegn.StorageServiceClient;

@RestController
@RequestMapping("/auth-service/feign")
@RequiredArgsConstructor
public class FeignController {

    private final StorageServiceClient storageServiceClient;

    @GetMapping
    ResponseEntity<?> getAvailableCountOfGoods(@RequestParam @Parameter(description = "Product ID") Long productId) {
        return ResponseEntity.ok(storageServiceClient.getQuantityGoods(productId));
    }
}
