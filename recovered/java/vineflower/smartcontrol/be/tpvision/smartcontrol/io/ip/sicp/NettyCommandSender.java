package be.tpvision.smartcontrol.io.ip.sicp;

import be.tpvision.smartcontrol.io.CommandSender;
import be.tpvision.smartcontrol.io.SicpHandler;
import be.tpvision.smartcontrol.io.SicpInitializer;
import be.tpvision.smartcontrol.io.ip.IpDestination;
import be.tpvision.smartcontrol.messages.io.ip.sicp.netty_command_sender.SendMessages;
import be.tpvision.smartcontrol.protocol.Request;
import be.tpvision.smartcontrol.protocol.Response;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NoRouteToHostException;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class NettyCommandSender implements CommandSender<IpDestination> {
   private static final Logger logger = LoggerFactory.getLogger(NettyCommandSender.class);
   private final EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
   private final Bootstrap bootstrap;

   public NettyCommandSender(final SicpInitializer sicpInitializer) {
      this.bootstrap = new Bootstrap().group(this.eventLoopGroup).channel(NioSocketChannel.class).handler(sicpInitializer);
   }

   @PreDestroy
   public void shutdown() {
      this.eventLoopGroup.shutdownGracefully();
   }

   @Override
   protected void finalize() throws Throwable {
      this.shutdown();
      super.finalize();
   }

   public Response send(final Request command, final IpDestination destination) {
      Response response = null;
      if (command != null && destination != null) {
         InetSocketAddress inetSocketAddress = destination.getAddress();
         InetAddress inetAddress = inetSocketAddress.getAddress();
         String ip = inetAddress.getHostAddress();
         int port = inetSocketAddress.getPort();

         try {
            ChannelFuture channelFuture = this.bootstrap.connect(ip, port);
            Channel channel = channelFuture.channel();
            ChannelConfig channelConfig = channel.config();
            channelConfig.setConnectTimeoutMillis(500);
            channelFuture.sync();
            SicpHandler sicpHandler = channel.pipeline().get(SicpHandler.class);
            logger.info("sending command {} to {}", command, destination);
            response = sicpHandler.send(command);
            channel.closeFuture().sync();
            logger.info("response object {}", response);
            return response;
         } catch (InterruptedException interruptedException) {
            logger.error("command was interrupted", interruptedException);
         } catch (Exception exception) {
            logger.error("command failure", exception);
            if (!(exception instanceof ConnectException) && !(exception instanceof NoRouteToHostException)) {
               throw exception;
            }

            String message = SendMessages.getDestinationUnreachableMessage(ip, port);
            throw new DestinationUnreachableException(message);
         }
      }

      return response;
   }

   public <C> C send(final Request command, final IpDestination destination, final Class<C> responseClass) {
      Response response = this.send(command, destination);
      if (response != null && responseClass != null) {
         if (responseClass.equals(Response.class)) {
            return responseClass.cast(response);
         }

         Object responseObject = response.getResponse();
         if (responseClass.isInstance(responseObject)) {
            return responseClass.cast(responseObject);
         }
      }

      return null;
   }
}
