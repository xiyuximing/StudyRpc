package com.cy.rpc.serializer;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.Serializable;

public class JsonSerializer implements Serializer {


    public byte[] serialize(Object object) throws IOException {
        return JSON.toJSONBytes(object);
    }

    public <T> T deserialize(Class<T> clazz, byte[] bytes) throws IOException {
        return JSON.parseObject(bytes, clazz);
    }
}
