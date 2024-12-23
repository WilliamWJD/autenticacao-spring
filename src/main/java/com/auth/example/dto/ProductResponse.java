package com.auth.example.dto;

import java.math.BigDecimal;

public record ProductResponse(
        Integer id,
        String name,
        String category,
        BigDecimal value
) {
}
