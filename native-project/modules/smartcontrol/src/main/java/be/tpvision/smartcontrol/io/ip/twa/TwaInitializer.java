package be.tpvision.smartcontrol.io.ip.twa;

import be.tpvision.smartcontrol.messages.io.ip.twa.twa_initializer.InitChannelMessages;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.util.Assert;

public class TwaInitializer extends ChannelInitializer<NioDatagramChannel> {
   protected void initChannel(final NioDatagramChannel nioDatagramChannel) throws Exception {
      Assert.notNull(nioDatagramChannel, InitChannelMessages.NIO_DATAGRAM_CHANNEL_CAN_NOT_BE_NULL);
      ChannelPipeline channelPipeline = nioDatagramChannel.pipeline();
      Assert.state(channelPipeline != null, InitChannelMessages.CHANNEL_PIPELINE_CAN_NOT_BE_NULL);
      LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
      channelPipeline.addLast(loggingHandler);
      TwaDecoder twaDecoder = new TwaDecoder();
      channelPipeline.addLast(twaDecoder);
      TwaHandler twaHandler = new TwaHandler();
      channelPipeline.addLast(twaHandler);
   }
}
