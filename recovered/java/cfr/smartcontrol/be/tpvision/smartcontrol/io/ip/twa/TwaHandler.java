/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.io.ip.twa;

import be.tpvision.smartcontrol.messages.io.ip.twa.twa_handler.ChannelReadCompleteMessages;
import be.tpvision.smartcontrol.messages.io.ip.twa.twa_handler.ChannelRegisteredMessages;
import be.tpvision.smartcontrol.messages.io.ip.twa.twa_handler.SendMessages;
import be.tpvision.smartcontrol.protocol.Request;
import be.tpvision.smartcontrol.protocol.Response;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import java.net.InetSocketAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class TwaHandler
extends SimpleChannelInboundHandler<Response> {
    private static final Logger logger = LoggerFactory.getLogger(TwaHandler.class);
    private volatile Channel channel;

    public void send(Request request, InetSocketAddress destination) {
        Assert.notNull((Object)request, SendMessages.REQUEST_CAN_NOT_BE_NULL);
        Assert.notNull((Object)destination, SendMessages.DESTINATION_CAN_NOT_BE_NULL);
        byte[] bytes = request.toBytes();
        ByteBuf byteBuf = Unpooled.copiedBuffer(bytes);
        DatagramPacket datagramPacket = new DatagramPacket(byteBuf, destination);
        Assert.state(this.channel != null, SendMessages.CHANNEL_CAN_NOT_BE_NULL);
        this.channel.writeAndFlush(datagramPacket);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Response response) throws Exception {
        boolean received = true;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) {
        Assert.notNull((Object)channelHandlerContext, ChannelReadCompleteMessages.CHANNEL_HANDLER_CONTEXT_CAN_NOT_BE_NULL);
        channelHandlerContext.flush();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        Assert.notNull((Object)channelHandlerContext, ChannelRegisteredMessages.CHANNEL_HANDLER_CONTEXT_CAN_NOT_BE_NULL);
        this.channel = channelHandlerContext.channel();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        logger.error("Exception caught:", throwable);
    }
}

