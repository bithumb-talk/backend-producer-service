package com.bithumb.websocket.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Data
public class QuoteRequestDto {
    private String type;
    private String[] symbols;
    private String[] tickTypes;
}
