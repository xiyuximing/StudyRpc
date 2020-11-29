package com.cy.rpc.decoder;

import com.cy.rpc.entity.RpcRequest;
import com.cy.rpc.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


/**
 * 继承MessageToByteEncoder并重写encode方法，实现编码功能
 */
public class RpcEncoder extends MessageToByteEncoder<RpcRequest> {

    private Serializer serializer;

    public RpcEncoder(Serializer serializer) {

        this.serializer = serializer;

    }
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcRequest o, ByteBuf byteBuf) throws Exception {
            byte[] bytes = serializer.serialize(o);
            //写入长度
            byteBuf.writeInt(bytes.length);
            //写入内容
            byteBuf.writeBytes(bytes);
    }
}
