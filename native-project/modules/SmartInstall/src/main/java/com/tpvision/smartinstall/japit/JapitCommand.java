package com.tpvision.smartinstall.japit;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.util.JAPITUtils;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

public abstract class JapitCommand {
   private JapitCommand.CommandSvc svc;
   private String svcVer = "4.0";
   private Integer cookie = JAPITUtils.getJapitRandomCookieValue();
   private JapitCommand.CommandType cmdType;
   private JapitCommand.CommandFun cmdFun;
   private JSONObject cmdDetail;
   private JSONObject responseCmdDetail;

   public void setCmdSvc(JapitCommand.CommandSvc svc) {
      this.svc = svc;
   }

   public JapitCommand.CommandSvc getCmdSvc() {
      return this.svc;
   }

   public void setSvcVer(String svcVer) {
      this.svcVer = svcVer;
   }

   public String getSvcVer() {
      return this.svcVer;
   }

   public void setCookie(Integer cookie) {
      this.cookie = cookie;
   }

   public Integer getCookie() {
      return this.cookie;
   }

   public void setCmdType(JapitCommand.CommandType cmdType) {
      this.cmdType = cmdType;
   }

   public JapitCommand.CommandType getCmdType() {
      return this.cmdType;
   }

   public void setCmdFun(JapitCommand.CommandFun cmdFun) {
      this.cmdFun = cmdFun;
   }

   public JapitCommand.CommandFun getCmdFun() {
      return this.cmdFun;
   }

   public void setCmdDetail(JSONObject detail) {
      this.cmdDetail = detail;
   }

   public void setCmdDetail(String key, Object value) {
      if (this.cmdDetail == null) {
         this.cmdDetail = new JSONObject();
      }

      this.cmdDetail.put(key, value);
   }

   public JSONObject getCmdDetail() {
      return this.cmdDetail;
   }

   public void send(String tvid) throws Exception {
      Devices tv = JpaManager.getDevicesManager().loadByKey(tvid);
      if (tv == null) {
         throw new IOException("device not found:" + tvid);
      }

      String resp = JAPITUtils.sendJapitCommand(tv, this.generateCommand());
      if (!StringUtils.isBlank(resp)) {
         JSONObject cmdRespObject = new JSONObject(resp);
         this.setResponseCmdDetail(cmdRespObject.optJSONObject("CommandDetails"));
         if ("Error".equalsIgnoreCase(cmdRespObject.optString("Fun"))) {
            throw new IOException("TV is not support this Function or not enabled:" + this.getCmdFun().name());
         }
      }
   }

   public String generateCommand() {
      JSONObject jsObject = new JSONObject();
      jsObject.put("Svc", this.svc.toString());
      jsObject.put("SvcVer", this.svcVer);
      jsObject.put("Cookie", this.cookie);
      jsObject.put("CmdType", this.cmdType.toString());
      jsObject.put("Fun", this.cmdFun.toString());
      if (null != this.cmdDetail) {
         jsObject.put("CommandDetails", this.cmdDetail);
      } else {
         JSONObject dynamicObject = this.getCommandDetails();
         if (dynamicObject != null) {
            jsObject.put("CommandDetails", dynamicObject);
         }
      }

      return jsObject.toString();
   }

   public JSONObject getCommandDetails() {
      return null;
   }

   public JSONObject getResponseCmdDetail() {
      return this.responseCmdDetail;
   }

   public void setResponseCmdDetail(JSONObject responseCmdDetail) {
      this.responseCmdDetail = responseCmdDetail;
   }

   public enum CommandFun {
      TVDiscoveryService,
      IPUpgradeService("IPUpgradeService"),
      ProfessionalSettingsServices("ProfessionalSettingsServices"),
      ApplicationControl("ApplicationControl"),
      SystemStatusService("SystemStatusService"),
      PMSService("PMSService"),
      PowerService("PowerService"),
      AudioService("AudioService"),
      PictureService("PictureService"),
      RegionAndLanguageService("RegionAndLanguageService"),
      ClockService("ClockService"),
      SubtitleServices("SubtitleServices"),
      AmbilightService("AmbilightService"),
      UserInputService("UserInputService"),
      SourceService("SourceService"),
      ChannelSelectionServices("ChannelSelectionService"),
      MyChoiceService("MyChoiceService"),
      WebListeningServices("WebServicesEnablerService"),
      SecuredCmdService,
      WebServicesEnablerService("ErrorSvc"),
      EnablerService,
      ContentSecurityService;

      private String fun;

      CommandFun() {
         this.fun = this.name();
      }

      CommandFun(String fun) {
         this.fun = fun;
      }

      @Override
      public String toString() {
         return this.fun;
      }
   }

   public enum CommandSvc {
      WIXP("WIXP"),
      WebService("WebServices"),
      WebListeningServices("WebListeningServices"),
      ErrorSvc("ErrorSvc");

      private String svc;

      CommandSvc(String svc) {
         this.svc = svc;
      }

      @Override
      public String toString() {
         return this.svc;
      }
   }

   public enum CommandType {
      Request("Request"),
      Response("Response"),
      Change("Change");

      private String type;

      CommandType(String type) {
         this.type = type;
      }

      @Override
      public String toString() {
         return this.type;
      }
   }
}
