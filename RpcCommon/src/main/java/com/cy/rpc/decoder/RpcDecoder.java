package com.cy.rpc.decoder;

import com.cy.rpc.entity.RpcRequest;
import com.cy.rpc.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 自定义解码器
 */
public class RpcDecoder extends ByteToMessageDecoder {

    private Class<?> clazz;
    private Serializer serializer;

    public RpcDecoder(Class<?> clazz, Serializer serializer) {
        this.clazz = clazz;
        this.serializer = serializer;
    }
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        //会有一个长度，如果小于四，重新读取
        if (byteBuf.readableBytes() < 4) {
            return;
        }
        //标记当前读的位置
        byteBuf.markReaderIndex();
        //读取数据长度
        int dataLength = byteBuf.readInt();
        //如果可以读的数据小于协议中的数据，需要重置一下读的下标，重新读取
        if (byteBuf.readableBytes() < dataLength) {
            byteBuf.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        byteBuf.readBytes(data);
        Object obj = serializer.deserialize(clazz, data);
        list.add(obj);
    }
}
