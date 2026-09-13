package be.tpvision.smartcontrol.io.ip.twa;

import be.tpvision.smartcontrol.messages.io.ip.twa.netty_command_sender.SendMessages;
import be.tpvision.smartcontrol.protocol.Request;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import java.net.InetSocketAddress;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.SocketUtils;

@Component
public class NettyCommandSender {
   private static final Logger logger = LoggerFactory.getLogger(NettyCommandSender.class);
   private final EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
   private final Bootstrap bootstrap = new Bootstrap().group(this.eventLoopGroup).channel(NioDatagramChannel.class).handler(new TwaInitializer());
   private Channel channel;

   public NettyCommandSender() {
      try {
         int port = SocketUtils.findAvailableUdpPort();
         this.channel = this.bootstrap.bind(port).sync().channel();
      } catch (InterruptedException e) {
         String message = e.getMessage();
         logger.error(message);
      }
   }

   public void send(final Request command, final InetSocketAddress destination) {
      Assert.notNull(command, SendMessages.COMMAND_CAN_NOT_BE_NULL);
      Assert.notNull(destination, SendMessages.DESTINATION_CAN_NOT_BE_NULL);
      TwaHandler twaHandler = this.channel.pipeline().get(TwaHandler.class);
      Assert.state(twaHandler != null, SendMessages.TWA_HANDLER_CAN_NOT_BE_NULL);
      logger.info("Sending command {} to {}", command, destination);
      twaHandler.send(command, destination);
   }

   @PreDestroy
   public void shutdown() {
      if (this.channel != null) {
         this.channel.close();
      }

      this.eventLoopGroup.shutdownGracefully();
   }

   @Override
   protected void finalize() throws Throwable {
      this.shutdown();
      super.finalize();
   }
}
