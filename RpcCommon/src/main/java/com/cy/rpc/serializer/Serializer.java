package com.cy.rpc.serializer;

import java.io.IOException;

public interface Serializer {

    /**
     * 将对象转换为二进制数组
     * @param object
     * @return
     * @throws IOException
     */
    byte[] serialize(Object object) throws IOException;

    /**
     * 将二进制数据转为对象
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     * @throws IOException
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes) throws IOException;
}
