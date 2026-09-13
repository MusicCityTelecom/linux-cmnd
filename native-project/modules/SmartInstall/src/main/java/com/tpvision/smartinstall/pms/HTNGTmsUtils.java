package com.tpvision.smartinstall.pms;

import com.tpvision.smartinstall.dao.core.Message;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.util.NetworkUtils;
import java.net.URL;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.htng._2011b.FolioManagementClient;
import org.htng._2011b.GuestCommunicationsClient;
import org.htng._2011b.HTNGHelper;
import org.htng._2011b.RoomStatusManagementClient;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTNGTmsUtils extends TmsUtils {
   private static final Logger LOG = LoggerFactory.getLogger(HTNGTmsUtils.class);
   private static final String CONFIG_NAME = "htngPMS";
   private String folioWsdl;
   private String roomStatusWsdl;
   private String messageWsdl;
   private String token;
   private RoomStatusManagementClient roomClient;
   private FolioManagementClient folioClient;
   private GuestCommunicationsClient messageClient;

   public String getFolioWsdl() {
      return this.folioWsdl;
   }

   public void setFolioWsdl(String folioWsdl) {
      this.folioWsdl = folioWsdl;
   }

   public String getRoomStatusWsdl() {
      return this.roomStatusWsdl;
   }

   public void setRoomStatusWsdl(String roomStatusWsdl) {
      this.roomStatusWsdl = roomStatusWsdl;
   }

   public String getMessageWsdl() {
      return this.messageWsdl;
   }

   public void setMessageWsdl(String messageWsdl) {
      this.messageWsdl = messageWsdl;
   }

   @Override
   protected void doLoadConfigs() {
      JSONObject pmsconfigs = PmsUtils.getPmsConfigs();
      if (pmsconfigs != null) {
         JSONObject config = pmsconfigs.optJSONObject("htngPMS");
         if (config == null) {
            LOG.error("get config failed");
            return;
         }

         LOG.info("pms configs:{}", config);
         this.setFolioWsdl(config.optString("KIOSK_SERVICE_URL", null));
         this.setRoomStatusWsdl(config.optString("ROOM_SERVICE_URL", null));
         this.setMessageWsdl(config.optString("MESSAGE_SERVICE_URL", null));
         this.setToken(config.optString("API_KEY"));
      } else {
         LOG.error("get oracle tms configs failed");
      }
   }

   private boolean htngClientEnabled() {
      return !StringUtils.isEmpty(this.folioWsdl) || !StringUtils.isEmpty(this.roomStatusWsdl) || !StringUtils.isEmpty(this.messageWsdl);
   }

   public String getToken() {
      return this.token;
   }

   public void setToken(String token) {
      this.token = token;
   }

   @Override
   public void requestBill(String roomid) {
      if (this.folioClient != null) {
         this.folioClient.requestBill(roomid);
      }
   }

   @Override
   public boolean isInstantBill() {
      return true;
   }

   @Override
   public void refresh() {
      this.requestRefresh(null);
      this.retrieveMessages();
   }

   private void retrieveMessages() {
      if (this.messageClient != null) {
         this.messageClient.retrieveMessages();
      }
   }

   @Override
   public void requestRefresh(String roomid) {
      if (this.roomClient != null) {
         this.roomClient.refresh(roomid);
      }
   }

   @Override
   public void requestExpressCheckout(String roomid) {
      LOG.info("not support expresscheckout");
   }

   @Override
   public String isConnectedTms() {
      String status = "Ok";
      if (this.htngClientEnabled()) {
         try {
            URL url = new URL(this.folioWsdl);
            int port = url.getPort() == -1 ? url.getDefaultPort() : url.getPort();
            if (!NetworkUtils.checkSocketPort(url.getHost(), port)) {
               status = "server socket [" + url.getHost() + ":" + port + "] is not opened";
            }
         } catch (Exception e) {
            status = e.getMessage();
         }
      } else {
         status = this.isConnected() ? "Ok" : "Disconnected";
      }

      return status;
   }

   @Override
   public void stop() {
      LOG.info("stop HTNG TMS");
      this.folioClient = null;
      this.roomClient = null;
      this.messageClient = null;
      this.setConnected(false);
   }

   @Override
   public void doStart() {
      LOG.info("start HTNG TMS");
      if (this.htngClientEnabled()) {
         this.folioClient = HTNGHelper.getFolioClient(this.getFolioWsdl());
         this.roomClient = HTNGHelper.getRoomStatusClient(this.getRoomStatusWsdl());
         this.messageClient = HTNGHelper.getMessageClient(this.getMessageWsdl());
      }

      this.checkConnection();
   }

   @Override
   public void onConnected() {
      LOG.error("htng PMS reconnected");
      super.onConnected();
      if (this.htngClientEnabled()) {
         if (this.folioClient != null) {
            this.folioClient.connect();
         }

         if (this.roomClient != null) {
            this.roomClient.connect();
         }

         if (this.messageClient != null) {
            this.messageClient.connect();
         }
      }

      this.refresh();
   }

   @Override
   public void onDisconnected() {
      super.onDisconnected();
      LOG.error("htng PMS disconnected");
   }

   @Override
   public String getMappedLanguage(String srcLanguage) {
      if (!"en-US".equalsIgnoreCase(srcLanguage)
         && !"zh-Hant".equalsIgnoreCase(srcLanguage)
         && !"zh-TW".equalsIgnoreCase(srcLanguage)
         && !"pt-BR".equalsIgnoreCase(srcLanguage)) {
         srcLanguage = srcLanguage.split("-")[0].toUpperCase();
         Locale locale = Locale.forLanguageTag(srcLanguage);
         String language = locale.getISO3Language();
         if ("fra".equalsIgnoreCase(language)) {
            language = "fre";
         }

         LOG.info("map language from {} to {}", srcLanguage, language);
         if (language == null) {
            LOG.info("language map for {} not found, using default as english", srcLanguage);
            language = "eng";
         }

         return language;
      } else {
         return srcLanguage;
      }
   }

   @Override
   public void updateMessageStatus(String msgId, PmsUtils.MessageStatus newStatus) {
      Message msg = JpaManager.getMessageManager().loadByKey(msgId);
      if (msg == null) {
         LOG.info("msg {} not found", msgId);
      } else {
         String roomId = msg.getGuestIds();
         if (this.messageClient != null) {
            this.messageClient.updateMessageStatus(roomId, msgId, newStatus.name());
         }
      }
   }

   @Override
   protected String getConfigName() {
      return "htngPMS";
   }

   @Override
   public void responseWakeup(String roomId, String wakeupTime, String status) {
   }
}
