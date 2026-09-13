package com.tpvision.smartinstall.pms;

import com.tpvision.smartinstall.util.UserEmailMonitorHelper;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public abstract class TmsUtils {
   public static final String STATUS_OK = "Ok";
   private boolean isConnected = false;
   private String lastStatus;
   private long lastUpdated;
   protected JSONObject config;

   protected TmsUtils() {
   }

   private void loadConfigs() {
      JSONObject pmsconfigs = PmsUtils.getPmsConfigs();
      if (pmsconfigs != null) {
         this.config = pmsconfigs.optJSONObject(this.getConfigName());
      }

      if (this.config == null) {
         this.config = new JSONObject();
      }

      this.doLoadConfigs();
   }

   protected abstract String getConfigName();

   protected abstract void doLoadConfigs();

   public abstract void requestBill(String var1);

   public abstract void refresh();

   public abstract void requestRefresh(String var1);

   public abstract void requestExpressCheckout(String var1);

   public abstract void updateMessageStatus(String var1, PmsUtils.MessageStatus var2);

   public abstract void responseWakeup(String var1, String var2, String var3);

   public boolean isSupportExpressCheckout() {
      return false;
   }

   public boolean isInstantBill() {
      return false;
   }

   public abstract String isConnectedTms();

   public abstract void stop();

   public void start() {
      this.loadConfigs();
      this.doStart();
   }

   protected abstract void doStart();

   public void onConnected() {
   }

   public void onDisconnected() {
      this.setLastStatus("Disconnected");
      UserEmailMonitorHelper.sendPmsServerIsOfflineNotice(this.lastStatus);
   }

   public boolean isAutoCreateTV() {
      return "On".equalsIgnoreCase(this.getConfigValue("AUTO_RFTV", "Off"));
   }

   public void checkConnection() {
      if (System.currentTimeMillis() - this.lastUpdated > 10000L) {
         String status = this.isConnectedTms();
         if (!status.equals(this.lastStatus)) {
            PmsUtils.updatePmsConnectionStatus(status);
            this.lastStatus = status;
         }

         this.setConnected(status.equals("Ok"));
      }
   }

   public String getMappedLanguage(String srcLanguage) {
      String language = null;
      Map<String, String> map = new HashMap<>();
      map.put("FR", "fre");
      map.put("GE", "ger");
      map.put("IT", "ita");
      map.put("JA", "jpn");
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

   public boolean isConnected() {
      return this.isConnected;
   }

   public void setConnected(boolean isConnected) {
      this.lastUpdated = System.currentTimeMillis();
      if (this.isConnected != isConnected) {
         this.isConnected = isConnected;
         if (isConnected) {
            this.onConnected();
         } else {
            this.onDisconnected();
         }
      }
   }

   public void setAutoCreateTV(boolean isAutoCreateTV) {
   }

   public void setLastStatus(String status) {
      if (!status.equals(this.lastStatus)) {
         PmsUtils.updatePmsConnectionStatus(status);
         this.lastStatus = status;
      }
   }

   public String getLastStatus() {
      return this.lastStatus;
   }

   public String getConfigValue(String name, String defaultValue) {
      if (this.config == null) {
         this.loadConfigs();
      }

      return this.config.optString(name, defaultValue);
   }
}
