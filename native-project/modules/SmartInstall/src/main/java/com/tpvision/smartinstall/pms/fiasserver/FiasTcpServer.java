package com.tpvision.smartinstall.pms.fiasserver;

import com.tpvision.smartinstall.pms.FiasServerTmsUtils;
import com.tpvision.smartinstall.pms.PmsUtils;
import com.tpvision.smartinstall.pms.TmsUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FiasTcpServer {
   private static final Logger LOG = LoggerFactory.getLogger(FiasTcpServer.class);
   private EventLoopGroup boss = new NioEventLoopGroup();
   private EventLoopGroup work = new NioEventLoopGroup();

   public void start(Integer port) throws InterruptedException {
      String ipaddress = PmsUtils.getLimitNetworkIpAddress();
      SocketAddress socketAddress = null;
      if ("Off".equalsIgnoreCase(ipaddress)) {
         socketAddress = new InetSocketAddress(port);
      } else if ("localhost".equalsIgnoreCase(ipaddress)) {
         socketAddress = new InetSocketAddress("127.0.0.1", port);
      } else {
         socketAddress = new InetSocketAddress(ipaddress, port);
      }

      ServerBootstrap bootstrap = new ServerBootstrap();
      bootstrap.group(this.boss, this.work)
         .channel(NioServerSocketChannel.class)
         .localAddress(socketAddress)
         .option(ChannelOption.SO_BACKLOG, 1024)
         .childOption(ChannelOption.SO_KEEPALIVE, true)
         .childOption(ChannelOption.TCP_NODELAY, true)
         .childHandler(new FiasTcpServer.ServerChannelInitializer());
      ChannelFuture future = bootstrap.bind().sync();
      if (future.isSuccess()) {
         LOG.info("FIAS Server started");
      }
   }

   public void stop() throws InterruptedException {
      this.boss.shutdownGracefully().sync();
      this.work.shutdownGracefully().sync();
      LOG.info("FIAS Server stopped");
   }

   public class NettyServerHandler extends ChannelInboundHandlerAdapter {
      public FiasServerTmsUtils getTmsUtils() {
         TmsUtils tms = PmsUtils.getTmsInstance();
         if (tms instanceof FiasServerTmsUtils) {
            return (FiasServerTmsUtils)tms;
         }

         FiasTcpServer.LOG.info("PMS not FIAS Service mode");
         return null;
      }

      @Override
      public void channelInactive(ChannelHandlerContext ctx) throws Exception {
         FiasTcpServer.LOG.info("FIAS client disconnected");
         FiasServerTmsUtils tms = this.getTmsUtils();
         if (tms != null) {
            tms.setConnected(false);
         }
      }

      @Override
      public void channelActive(ChannelHandlerContext ctx) throws Exception {
         InetSocketAddress insocket = (InetSocketAddress)ctx.channel().remoteAddress();
         String clientIP = insocket.getAddress().getHostAddress();
         int clientPort = insocket.getPort();
         FiasTcpServer.LOG.info("FIAS client connected, {}:{}", clientIP, clientPort);
         FiasServerTmsUtils tms = this.getTmsUtils();
         if (tms != null) {
            tms.setCtx(ctx);
            tms.setConnected(true);
         }
      }

      @Override
      public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
         if (msg instanceof FiasMessage) {
            FiasMessage fiasMsg = (FiasMessage)msg;
            FiasTcpServer.LOG.info("Receive from Fias Client: {}", fiasMsg.getData());
            FiasServerTmsUtils tms = this.getTmsUtils();
            if (tms != null) {
               try {
                  tms.processReceivedData(fiasMsg.data);
               } catch (IOException e) {
                  FiasTcpServer.LOG.error(e.getMessage());
               }
            }
         }
      }

      @Override
      public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
         cause.printStackTrace();
         ctx.close();
         FiasServerTmsUtils tms = this.getTmsUtils();
         if (tms != null) {
            tms.setConnected(false);
         }
      }
   }

   public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
      protected void initChannel(SocketChannel socketChannel) throws Exception {
         socketChannel.pipeline().addLast(new FiasMessageDecoder()).addLast(new FiasMessageEncoder()).addLast(FiasTcpServer.this.new NettyServerHandler());
      }
   }
}
