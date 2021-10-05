package com.bithumb.websocket.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Data @Builder
public class QuoteRequest {
    private String type;
    private String[] symbols;
    private String[] tickTypes;
}
