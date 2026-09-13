/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.io;

import be.tpvision.smartcontrol.io.SicpHandler;
import be.tpvision.smartcontrol.io.ip.sicp.SicpDecoder;
import be.tpvision.smartcontrol.messages.io.sicp_initializer.ConstructorMessages;
import be.tpvision.smartcontrol.protocol.sicp.SicpCommandFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import java.util.concurrent.TimeUnit;
import org.springframework.util.Assert;

public class SicpInitializer
extends ChannelInitializer<SocketChannel> {
    private final SicpCommandFactory sicpCommandFactory;

    public SicpInitializer(SicpCommandFactory sicpCommandFactory) {
        Assert.notNull((Object)sicpCommandFactory, ConstructorMessages.SICP_COMMAND_FACTORY_CAN_NOT_BE_NULL_MESSAGE);
        this.sicpCommandFactory = sicpCommandFactory;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
        channelPipeline.addLast(loggingHandler);
        ReadTimeoutHandler readTimeoutHandler = new ReadTimeoutHandler(1000L, TimeUnit.MILLISECONDS);
        channelPipeline.addLast("readTimeoutHandler", (ChannelHandler)readTimeoutHandler);
        SicpDecoder sicpDecoder = new SicpDecoder(this.sicpCommandFactory);
        channelPipeline.addLast(sicpDecoder);
        channelPipeline.addLast(new SicpHandler());
    }
}

