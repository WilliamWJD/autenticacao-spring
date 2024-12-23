package com.auth.example.controllers;

import com.auth.example.dto.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping
    public ResponseEntity<ProductResponse> getProducts(){
        return ResponseEntity.ok().body(new ProductResponse(1, "PlayStation 5", "Eletronicos", BigDecimal.valueOf(5000)));
    }
}
