package com.bithumb.websocket.controller.dto;

import com.bithumb.websocket.domain.Quote;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class QuoteResponse {
    private String type;
    private Quote content;
}
