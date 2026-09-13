/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.io;

import be.tpvision.smartcontrol.codecs.sicp.CommunicationControlCodec;
import be.tpvision.smartcontrol.domain.device_settings.CommunicationControl;
import be.tpvision.smartcontrol.io.CommandNotAcknowledgedException;
import be.tpvision.smartcontrol.io.CommandNotAvailableException;
import be.tpvision.smartcontrol.messages.io.sicp_handler.SicpHandlerMessages;
import be.tpvision.smartcontrol.protocol.Request;
import be.tpvision.smartcontrol.protocol.Response;
import be.tpvision.smartcontrol.util.ByteArrayUtilities;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class SicpHandler
extends SimpleChannelInboundHandler<Response> {
    private static final Logger logger = LoggerFactory.getLogger(SicpHandler.class);
    private static final CommunicationControlCodec communicationControlCodec = CommunicationControlCodec.getInstance();
    private volatile Channel channel;
    private final BlockingQueue<Response> response = new LinkedBlockingQueue<Response>();
    private Thread thread;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Response send(Request request) {
        this.thread = Thread.currentThread();
        this.channel.writeAndFlush(Unpooled.copiedBuffer(request.toBytes()));
        Response response = null;
        try {
            response = this.response.take();
            if (response == null) {
                Response response2 = null;
                return response2;
            }
            Object responseObject = response.getResponse();
            if (responseObject instanceof CommunicationControl) {
                CommunicationControl communicationControl = (CommunicationControl)responseObject;
                byte[] requestBytes = request.toBytes();
                String requestHex = ByteArrayUtilities.bytesToHex(requestBytes);
                switch (communicationControl) {
                    case NOT_ACKNOWLEDGED: {
                        throw new CommandNotAcknowledgedException(requestHex);
                    }
                    case NOT_AVAILABLE: {
                        throw new CommandNotAvailableException(requestHex);
                    }
                }
            }
        }
        catch (InterruptedException ignore) {
            Thread.currentThread().interrupt();
        }
        finally {
            this.channel.close();
        }
        return response;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Response response) throws Exception {
        this.response.add(response);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.channel = channelHandlerContext.channel();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        logger.error("Exception caught:", throwable);
        this.thread.interrupt();
        channelHandlerContext.close();
    }

    static {
        Assert.state(communicationControlCodec != null, SicpHandlerMessages.COMMUNICATION_CONTROL_CODEC_CAN_NOT_BE_NULL);
    }
}

