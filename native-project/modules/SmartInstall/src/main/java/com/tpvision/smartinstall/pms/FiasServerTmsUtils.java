package com.tpvision.smartinstall.pms;

import com.tpvision.smartinstall.pms.fiasserver.FiasTcpServer;
import io.netty.channel.ChannelHandlerContext;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FiasServerTmsUtils extends OracleTmsUtils {
   private static final Logger LOG = LoggerFactory.getLogger(FiasServerTmsUtils.class);
   private static final String CONFIG_NAME = "fiasServer";
   private FiasTcpServer tcpServer;
   private ChannelHandlerContext ctx;

   @Override
   protected void doLoadConfigs() {
      JSONObject pmsconfigs = PmsUtils.getPmsConfigs();
      if (pmsconfigs != null) {
         JSONObject config = pmsconfigs.optJSONObject("fiasServer");
         if (config == null) {
            LOG.error("get config failed");
            return;
         }

         LOG.info("FIAS configs:{}", config);
         this.port = config.optInt("PMS_PORT", 20099);
         String autoCreateTV = config.optString("AUTO_RFTV", "Off");
         this.setAutoCreateTV(autoCreateTV.equalsIgnoreCase("On"));
         this.encoding = config.optString("Encoding", "UTF-8");
      } else {
         LOG.error("get oracle tms configs failed");
      }
   }

   @Override
   public String isConnectedTms() {
      return this.ctx != null && this.ctx.channel().isActive() ? "Ok" : "Disconnected";
   }

   @Override
   public void stop() {
      if (this.tcpServer != null) {
         try {
            this.tcpServer.stop();
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
   }

   @Override
   public void doStart() {
      if (this.tcpServer == null) {
         this.tcpServer = new FiasTcpServer();
      }

      try {
         this.tcpServer.start(this.port);
      } catch (InterruptedException e) {
         LOG.error("start FIAS TCP Server failed:{}", e.getMessage());
      }
   }

   @Override
   public boolean sendCommandToTMS(String data) {
      if (this.ctx != null && this.ctx.channel().isActive()) {
         LOG.info("SI >>>>> FIAS:{}", data);
         this.ctx.channel().writeAndFlush(data);
         return true;
      } else {
         LOG.error("FIAS Client not connected");
         return false;
      }
   }

   public ChannelHandlerContext getCtx() {
      return this.ctx;
   }

   public void setCtx(ChannelHandlerContext ctx) {
      this.ctx = ctx;
   }
}
