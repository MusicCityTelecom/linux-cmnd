package com.tpvision.smartinstall.pms;

import com.tpvision.smartinstall.dao.core.GuestInfo;
import com.tpvision.smartinstall.dao.core.Message;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.util.TpvDateUtils;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TigerTmsUtils extends TmsUtils {
   private static final Logger LOG = LoggerFactory.getLogger(TigerTmsUtils.class);
   private static final String CONFIG_NAME = "tigerTMS";
   private String tmsUrl;
   private String tmsKey;
   private String tmsSite;
   private SOAPUtils soapUtils;

   public String getTmsUrl() {
      return this.tmsUrl;
   }

   public void setTmsUrl(String tmsUrl) {
      this.tmsUrl = tmsUrl;
   }

   public String getTmsKey() {
      return this.tmsKey;
   }

   public void setTmsKey(String tmsKey) {
      this.tmsKey = tmsKey;
   }

   public String getTmsSite() {
      return this.tmsSite;
   }

   public void setTmsSite(String tmsSite) {
      this.tmsSite = tmsSite;
   }

   @Override
   public void requestBill(String roomid) {
      LOG.info("TigerTMS request bill:{}", roomid);
      String soap = this.soapUtils.requestBillMsg(roomid);
      this.sendCommandToTMS(soap, "requestBill");
      PmsUtils.setRequestTimer(PmsUtils.RequestType.RequestBill, roomid);
   }

   @Override
   public void refresh() {
      this.requestRefresh("ALL");
   }

   @Override
   public void requestRefresh(String roomid) {
      String soap = this.soapUtils.requestRefreshMsg(roomid);
      this.sendCommandToTMS(soap, "requestRefresh");
   }

   @Override
   public boolean isInstantBill() {
      return true;
   }

   @Override
   public String isConnectedTms() {
      LOG.info("checking tiger pms connection:{}", this.tmsUrl);

      try {
         String regEx = "^http://.*";
         Pattern pattern = Pattern.compile(regEx);
         Matcher matcher = pattern.matcher(this.tmsUrl);
         if (!matcher.matches()) {
            throw new IOException("Pms URL format is wrong");
         }

         String response = this.soapUtils.sendCommand(null, null);
         if (StringUtils.countMatches(response, "InterfaceMessageIn") >= 3) {
            return "Ok";
         }

         LOG.error("tiger pms response:{}", response);
         return "Error:not a valid TigerTMS Url";
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
         return "Error: " + e.getMessage();
      }
   }

   private void sendCommandToTMS(String data, String soapAction) {
      LOG.info(" SI >>>>>>>> TMS {}", data);
      new Thread(() -> {
         try {
            String response = this.soapUtils.sendCommand(data, soapAction);
            LOG.info(" TMS >>>>>>>> SI {}", response);
         } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            this.setLastStatus("Error:" + e.getMessage());
         }
      }).start();
   }

   @Override
   public void stop() {
   }

   @Override
   public void doStart() {
      PmsUtils.updatePmsVersion("1.0.12.0");
      this.refresh();
   }

   @Override
   protected void doLoadConfigs() {
      LOG.info("TigerTMS configs:{}", this.config);
      this.setTmsUrl(this.config.optString("PMS_URL", ""));
      this.setTmsKey(this.config.optString("PMS_KEY", ""));
      this.setTmsSite(this.config.optString("PMS_SITE", ""));
      String autoCreateTV = this.config.optString("AUTO_RFTV", "Off");
      this.setAutoCreateTV(autoCreateTV.equalsIgnoreCase("On"));
      this.soapUtils = new SOAPUtils(this.tmsSite, this.tmsKey, this.tmsUrl);
   }

   @Override
   public String getMappedLanguage(String srcLanguage) {
      String language = null;
      Map<String, String> map = new HashMap<>();
      map.put("FR", "fre");
      map.put("GE", "ger");
      map.put("IT", "ita");
      map.put("JP", "jpn");
      map.put("SP", "spa");
      map.put("NL", "dut");
      if (null != map.get(srcLanguage)) {
         language = map.get(srcLanguage);
      } else if (map.containsValue(srcLanguage)) {
         language = srcLanguage;
      } else {
         language = "eng";
      }

      return language;
   }

   @Override
   public void requestExpressCheckout(String roomid) {
      LOG.info("request express checkout for room:{}", roomid);
      String balance = PmsUtils.getBalance(roomid);
      String data = this.soapUtils.requestCheckoutMsg(roomid, balance);
      this.sendCommandToTMS(data, SOAPUtils.SOAPAction.requestCheckout.name());
      PmsUtils.setRequestTimer(PmsUtils.RequestType.RequestExpressCheckout, roomid);
   }

   @Override
   public void updateMessageStatus(String msgId, PmsUtils.MessageStatus newStatus) {
      if (newStatus != PmsUtils.MessageStatus.Delete) {
         LOG.info("message {} status {} not support to update", msgId, newStatus);
      } else {
         Message msg = JpaManager.getMessageManager().loadByKey(msgId);
         if (msg == null) {
            LOG.info("msg {} not found", msgId);
         } else {
            String roomId = msg.getGuestIds();
            String data = this.soapUtils.requestDeleteMessage(roomId, msgId);
            this.sendCommandToTMS(data, SOAPUtils.SOAPAction.deleteMessageText.name());
         }
      }
   }

   @Override
   protected String getConfigName() {
      return "tigerTMS";
   }

   @Override
   public void responseWakeup(String roomId, String wakeupTime, String status) {
      LOG.info("response wakeup:{} for room:{}", wakeupTime, roomId);
      List<GuestInfo> guest = JpaManager.getGuestInfoManager().findGuestInfosByRoomid(roomId);
      String resno = "";
      if (!guest.isEmpty()) {
         resno = guest.get(0).getOrderid();
         if (resno == null) {
            resno = "1000TD";
         }
      } else {
         resno = "1000TD";
      }

      String data = this.soapUtils.responseWakeupStatus(roomId, resno, parseWakeupDate(wakeupTime), wakeupTime.split(" ")[1], status);
      this.sendCommandToTMS(data, SOAPUtils.SOAPAction.wakeupStatus.name());
   }

   private static String parseWakeupDate(String wakeupTime) {
      Date date = TpvDateUtils.parseDateString(wakeupTime, "yyyy-MM-dd HH:mm:ss");
      return TpvDateUtils.formatLocalDate(date, "dd/MM/yyyy");
   }
}
