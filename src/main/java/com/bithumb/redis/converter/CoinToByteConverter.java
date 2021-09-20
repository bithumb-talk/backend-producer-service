//package com.bithumb.redis.converter;
//
//import com.bithumb.redis.domain.Symbol;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.data.convert.ReadingConverter;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.stereotype.Component;
//
//@Component
//@ReadingConverter
//public class CoinToByteConverter implements Converter<Symbol, byte[]>{
//
//    private final Jackson2JsonRedisSerializer<Symbol> serializer;
//
//    public CoinToByteConverter() {
//        this.serializer = new Jackson2JsonRedisSerializer<Symbol>(Symbol.class);
//        serializer.setObjectMapper(new ObjectMapper());
//    }
//    @Override
//    public byte[] convert(Symbol source) {
//        return serializer.serialize(source);
//    }
//}