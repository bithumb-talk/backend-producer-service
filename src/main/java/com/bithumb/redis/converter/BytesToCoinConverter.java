//package com.bithumb.redis.converter;
//
//import com.bithumb.redis.domain.Symbol;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.data.convert.ReadingConverter;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.stereotype.Component;
//
//@Component @ReadingConverter
//public class BytesToCoinConverter implements Converter<byte[], String> {
//
//    private final Jackson2JsonRedisSerializer<Symbol> serializer;
//
//    public BytesToCoinConverter() {
//        this.serializer = new Jackson2JsonRedisSerializer<Symbol>(Symbol.class);
//        serializer.setObjectMapper(new ObjectMapper());
//    }
//    @Override
//    public String convert(byte[] source) {
//        return serializer.deserialize(source);
//    }
//}