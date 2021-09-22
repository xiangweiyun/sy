package com.sy.center.common.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * @author xiangwy
 * @description: Long类型丢失精度处理类
 * @date: 2021-09-22 14:54
 * @Copyright: Copyright (c) 2021 - 2021
 * @Company: 云南升玥信息技术有限公司
 * @Version: V1.0
 */
@Configuration
public class JsonMessageConverter {

    @Bean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        //通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化。
        //JsonInclude.Include.NON_NULL 属性为NULL 不序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //jackson中自定义处理序列化和反序列化
        SimpleModule module = new SimpleModule();
        //Method for adding serializer to handle values of specific type.
        module.addSerializer(Long.class, ToStringSerializer.instance);
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);
        //register the module with the object-mapper
        objectMapper.registerModule(module);
        return objectMapper;
    }
}
