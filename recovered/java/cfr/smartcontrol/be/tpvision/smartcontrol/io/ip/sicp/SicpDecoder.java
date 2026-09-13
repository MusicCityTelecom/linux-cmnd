/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.io.ip.sicp;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.io.ip.sicp.ChecksumVerificationFailedException;
import be.tpvision.smartcontrol.messages.io.ip.sicp.sicp_decoder.ConstructorMessages;
import be.tpvision.smartcontrol.messages.io.ip.sicp.sicp_decoder.DecodeMessages;
import be.tpvision.smartcontrol.protocol.ResponseWrapper;
import be.tpvision.smartcontrol.protocol.sicp.SicpCommandFactory;
import be.tpvision.smartcontrol.util.ChecksumUtilities;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class SicpDecoder
extends ByteToMessageDecoder {
    private static final Logger logger = LoggerFactory.getLogger(SicpDecoder.class);
    private final SicpCommandFactory sicpCommandFactory;

    public SicpDecoder(SicpCommandFactory sicpCommandFactory) {
        Assert.notNull((Object)sicpCommandFactory, ConstructorMessages.SICP_COMMAND_FACTORY_CAN_NOT_BE_NULL);
        this.sicpCommandFactory = sicpCommandFactory;
    }

    @Override
    public void decode(ChannelHandlerContext channelHandlerContext, ByteBuf input, List<Object> output) {
        logger.debug("Decoding server message...");
        if (input != null && input.isReadable()) {
            byte messageSize = input.getByte(0);
            if (input.readableBytes() >= messageSize) {
                byte[] receivedBytes = new byte[messageSize];
                input.readBytes(receivedBytes);
                logger.debug("Received Bytes : {}", (Object)receivedBytes);
                int checksumIndex = receivedBytes.length - 1;
                byte[] checksumInput = Arrays.copyOfRange(receivedBytes, 0, checksumIndex);
                byte expectedChecksum = ChecksumUtilities.xorChecksum(checksumInput);
                byte receivedChecksum = receivedBytes[checksumIndex];
                if (expectedChecksum != receivedChecksum) {
                    String message = DecodeMessages.getChecksumVerificationFailedMessage(expectedChecksum, receivedChecksum);
                    throw new ChecksumVerificationFailedException(message);
                }
                byte settingByte = receivedBytes[3];
                Codec<? extends DeviceSetting> codec = this.sicpCommandFactory.getDecoder(settingByte);
                Assert.state(codec != null, DecodeMessages.CODEC_CAN_NOT_BE_NULL);
                byte[] data = Arrays.copyOfRange(receivedBytes, 4, checksumIndex);
                logger.debug("Decoded bytes: {}", (Object)data);
                DeviceSetting decoded = codec.toDomain(data);
                ResponseWrapper response = new ResponseWrapper(decoded);
                logger.debug("Decoded response: {}", response.getResponse());
                output.add(response);
            }
        }
    }
}

