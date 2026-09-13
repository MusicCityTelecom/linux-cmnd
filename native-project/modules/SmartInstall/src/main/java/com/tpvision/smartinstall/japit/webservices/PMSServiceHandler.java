package com.tpvision.smartinstall.japit.webservices;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.GuestInfo;
import com.tpvision.smartinstall.dao.core.Message;
import com.tpvision.smartinstall.dao.mgr.GuestInfoManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.MessageManager;
import com.tpvision.smartinstall.japit.PmsJapitCommand;
import com.tpvision.smartinstall.pms.CheckInVO;
import com.tpvision.smartinstall.pms.PmsUtils;
import com.tpvision.smartinstall.pms.TmsUtils;
import com.tpvision.smartinstall.schedule.CmndMetricsTask;
import com.tpvision.smartinstall.trigger.TriggerUtils;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.PmsMessageUtils;
import com.tpvision.smartinstall.util.TpvStringUtils;
import java.io.IOException;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PMSServiceHandler extends WebServiceCommandHandler {
   private static final Logger LOG = LoggerFactory.getLogger(PMSServiceHandler.class);

   @Override
   public String execute() {
      if ("Request".equalsIgnoreCase(this.cmdType)) {
         return this.handlePMSService4Request();
      } else {
         return "Change".equalsIgnoreCase(this.cmdType) ? this.handlePMSService4Change() : "";
      }
   }

   private String handlePMSService4Change() {
      String retData = "";
      JSONObject pmsParameters = this.commandDetails.getJSONObject("PMSParameters");
      String action = pmsParameters.optString("Action");
      LOG.info("handlePMSService4Change:{}", pmsParameters);
      switch (action) {
         case "UpdateGuestMessageStatus":
            JSONArray guestMessages = (JSONArray)pmsParameters.get("GuestMessages");
            updateGuestMessageStatus(guestMessages.toString());
            retData = this.updateGuestMessage(this.tvUniqueId);
            break;
         case "ExpressCheckOut":
            JSONObject expressCheckOutDetails = pmsParameters.getJSONObject("ExpressCheckOutDetails");
            retData = this.processExpressCheckout(expressCheckOutDetails);
            break;
         case "CheckInSuccessful":
            retData = this.processCheckInSuccessful();
            break;
         case "CheckOutSuccessful":
            retData = this.processCheckOutSuccessful();
            break;
         default:
            LOG.info("unsupport pmsservice Change Action:{}", action);
      }

      return retData;
   }

   private String processCheckOutSuccessful() {
      LOG.info("processCheckOutSuccessful");
      String roomId = this.device.getTvroomid();
      GuestInfoManager gm = JpaManager.getGuestInfoManager();
      List<GuestInfo> guests = gm.findGuestInfosByRoomid(roomId);
      if (!guests.isEmpty()) {
         GuestInfo gi = guests.get(0);
         if ("Y".equalsIgnoreCase(gi.getCheckin())) {
            TriggerUtils.executePMSTrigger(PmsUtils.PmsAction.CheckOut, roomId, null);
            PmsUtils.processAfterCheckout(roomId);
         }
      }

      return "";
   }

   private String processCheckInSuccessful() {
      LOG.info("processCheckInSuccessful");
      String roomId = this.device.getTvroomid();
      GuestInfoManager gm = JpaManager.getGuestInfoManager();
      List<GuestInfo> guests = gm.findGuestInfosByRoomid(roomId);
      GuestInfo gi = !guests.isEmpty() ? guests.get(0) : PmsUtils.createGuest(null, null, roomId, null, null);
      if (!"Y".equalsIgnoreCase(gi.getCheckin())) {
         gi.setCheckin("Y");
         gm.save(gi);
         PmsUtils.setUpdatedGuestInfo(gi.getGuestId());
      }

      return "";
   }

   private String processExpressCheckout(JSONObject expressCheckOutDetails) {
      LOG.info("processExpressCheckout");
      String retData = "";
      String roomId = expressCheckOutDetails.optString("RoomID", null);

      try {
         if (roomId == null) {
            throw new IOException("roomId not exists");
         }

         GuestInfoManager gm = JpaManager.getGuestInfoManager();
         List<GuestInfo> gi = gm.findGuestInfosByRoomid(roomId);
         List<Devices> device = PmsUtils.getTVsForRoom(roomId);
         if (gi.isEmpty() || device.isEmpty()) {
            throw new IOException("guest or device is empty");
         }

         String roomid = gi.get(0).getRoomid();
         String tvRoomId = device.get(0).getTvroomid();
         if (Integer.parseInt(roomid) != Integer.parseInt(tvRoomId)) {
            throw new IOException(TpvStringUtils.format("roomid {} not equal with TVRoomID:{}", roomid, tvRoomId));
         }

         TmsUtils tmsUtils = PmsUtils.getTmsInstance();
         if (null != tmsUtils && tmsUtils.isSupportExpressCheckout()) {
            LOG.info("TmsUtils not null, send expresscheckout request to tms");
            tmsUtils.requestExpressCheckout(roomId);
         } else {
            retData = this.expressCheckOut();
            gi.get(0).setCheckin("N");
            gm.save(gi.get(0));
            CmndMetricsTask.writePMSInfoToMetricsLog(device.get(0), "expresscheckout");
            PmsUtils.processAfterCheckout(roomid);
            LOG.info("expresscheckout successfull, cleaned data");
         }
      } catch (IOException e) {
         LOG.info(e.getMessage());
         retData = PmsJapitCommand.getExpressCheckOutError("RoomIDMismatch");
      }

      return retData;
   }

   private String responseGuestBill(String tvUniqueId) throws IOException {
      LOG.info("response guest bill:{}", tvUniqueId);
      if (StringUtils.isEmpty(tvUniqueId)) {
         throw new IOException("uniqueId is empty");
      }

      if (!PmsUtils.isGuestCheckin(this.device.getTvroomid())) {
         throw new IOException("Guest not checkin, cannot get bill");
      }

      if (!PmsUtils.isGuestViewBill(this.device.getTvroomid())) {
         throw new IOException("Guest ViewBill is off, cannot get bill");
      }

      PmsUtils.responseBill(this.device);
      return null;
   }

   private String handlePMSService4Request() {
      JSONObject pMSParameters = this.commandDetails.optJSONObject("PMSParameters");
      if (null == pMSParameters) {
         LOG.error("PMSService4request: PMSParameters are null in commanddetails:{} ", this.commandDetails);
         return "";
      }

      String requestPMSParameters = null;
      if (pMSParameters.has("RequestPMSParameters") && !pMSParameters.get("RequestPMSParameters").toString().isEmpty()) {
         requestPMSParameters = pMSParameters.get("RequestPMSParameters").toString();

         try {
            requestPMSParameters = requestPMSParameters.substring(1, requestPMSParameters.length() - 1);
         } catch (NullPointerException e) {
            LOG.error(e.getMessage(), e);
         }

         String[] requestParameters = StringUtils.split(requestPMSParameters, ",");
         String retData = this.response4PmsRequest(requestParameters);
         return TpvStringUtils.removeNL(retData);
      } else {
         return "";
      }
   }

   private JSONObject initialResponsePara(String cmdType) {
      JSONObject pmsResp = new JSONObject();
      pmsResp.put("Svc", "WebServices");
      pmsResp.put("SvcVer", "3.0");
      pmsResp.put("Cookie", 293);
      pmsResp.put("CmdType", cmdType);
      pmsResp.put("Fun", "PMSService");
      return pmsResp;
   }

   private JSONObject initialCommandDetails() {
      JSONObject commandDetails = new JSONObject();
      JSONObject webServiceParameters = new JSONObject();
      webServiceParameters.put("PollingFrequency", 15);
      webServiceParameters.put("TVUniqueID", this.tvUniqueId);
      commandDetails.put("WebServiceParameters", webServiceParameters);
      return commandDetails;
   }

   private String response4PmsRequest(String[] requestParameters) {
      if (!StringUtils.isEmpty(this.tvUniqueId) && this.device != null) {
         JSONObject pmsResp = this.initialResponsePara("Response");
         JSONObject commandDetails = this.initialCommandDetails();
         JSONObject pMSParameters = new JSONObject();
         String roomId = this.device.getTvroomid();
         List<GuestInfo> gi = JpaManager.getGuestInfoManager().findGuestInfosByRoomid(roomId);
         CheckInVO checkInVO = null;
         if (!gi.isEmpty()) {
            checkInVO = PmsUtils.getCheckInVO(gi.get(0));
         }

         if (!PlatformUtils.isMasf2019Up(this.device.getType())) {
            checkInVO = PmsUtils.processCheckInVOForASTA(checkInVO);
         }

         for (int i = 0; i < requestParameters.length; i++) {
            if ("\"GuestMessages\"".equalsIgnoreCase(requestParameters[i])) {
               if (!PmsUtils.isPmsMessagesEnabled()) {
                  LOG.warn("pms message is disabled!");
               } else {
                  List<Message> messages = null;
                  messages = JpaManager.getMessageManager().findMessageByGuestIdsAndIsSend(roomId, "Y");
                  JSONArray array = PmsMessageUtils.getGuestMessagesArray(this.device, messages);
                  pMSParameters.put("GuestMessages", array);
               }
            } else if ("\"GuestBill\"".equalsIgnoreCase(requestParameters[i])) {
               JSONObject guestBill = PmsUtils.getBillObject(this.device);
               if (guestBill != null) {
                  pMSParameters.put("GuestBill", guestBill);
               }
            } else if ("\"GuestDetails\"".equalsIgnoreCase(requestParameters[i])) {
               JSONObject guestDetails = PmsJapitCommand.getGuestDetails(checkInVO);
               if (guestDetails != null) {
                  pMSParameters.put("GuestDetails", guestDetails);
               }
            } else if ("\"PMSFeaturesSupported\"".equalsIgnoreCase(requestParameters[i])) {
               JSONArray featuresSupported = PmsJapitCommand.getPmsFeatureSupported(checkInVO);
               if (featuresSupported != null) {
                  pMSParameters.put("PMSFeaturesSupported", featuresSupported);
               }
            } else if ("\"GuestPreferences\"".equalsIgnoreCase(requestParameters[i])) {
               JSONObject guestPreferences = PmsJapitCommand.getGuestPreferences(checkInVO);
               if (guestPreferences != null) {
                  pMSParameters.put("GuestPreferences", guestPreferences);
               }
            } else if ("\"RoomStatus\"".equalsIgnoreCase(requestParameters[i])) {
               JSONObject roomStatus = PmsJapitCommand.getRoomStatus(checkInVO);
               if (roomStatus != null) {
                  pMSParameters.put("RoomStatus", roomStatus);
               }
            } else {
               LOG.error("PMSService4Request: Unknown requestparameter:{} ", requestParameters[i]);
            }
         }

         if (pMSParameters.isEmpty()) {
            return null;
         }

         commandDetails.put("PMSParameters", pMSParameters);
         pmsResp.put("CommandDetails", commandDetails);
         return pmsResp.toString();
      } else {
         return null;
      }
   }

   private static void updateGuestMessageStatus(String messagesStr) {
      JSONArray guestMsgs = new JSONArray(messagesStr);
      Message msg = null;
      MessageManager mm = JpaManager.getMessageManager();

      for (int i = 0; i < guestMsgs.length(); i++) {
         JSONObject msgItem = (JSONObject)guestMsgs.get(i);
         String msgid = msgItem.get("ID").toString();
         String newStatus = msgItem.optString("Status");
         msg = mm.loadByMsgId(msgid);
         if (msg == null) {
            LOG.info("msg id={} not found", msgid);
         } else if (!msg.getStatus().equalsIgnoreCase(newStatus)) {
            if (!msg.getStatus().equalsIgnoreCase("Delete")) {
               msg.setStatus(msgItem.get("Status").toString());
               mm.save(msg);
            }

            if (!newStatus.equalsIgnoreCase(PmsUtils.MessageStatus.New.name())) {
               PmsUtils.updateMessageStatus(msgid, newStatus);
            }

            PmsUtils.setMessageUpdated();
         }
      }
   }

   private String responseRoomVacant() {
      return "{\t\"Svc\": \"WebServices\",\t\"SvcVer\": 3.0,\t\"Cookie\": 293,\t\"CmdType\": \"Response\",\t\"Fun\": \"PMSService\",\t\"CommandDetails\": {\t\t\"PMSParameters\": {\t\t\t\"RoomStatus\": {\t\t\t\t\"Status\": \"Vacant\"\t\t\t}\t\t}\t}}";
   }

   private String expressCheckOut() {
      return "{\t\"Svc\" : \"WebServices\",\t\"SvcVer\" : \"3.0\",\t\"Cookie\" : 293,\t\"CmdType\" : \"Change\",\t\"Fun\" : \"PMSService\",\t\"CommandDetails\" : \t{\t\t\"PMSParameters\" : \t\t{\t\t\t\"Action\" : \"CheckOut\",\t\t\t\"PMSFeaturesSupported\" : [\t\t\t\t\"RoomStatus\",\t\t\t\t\"RoomStatus:CheckIn\"],\t\t\t\"RoomStatus\" : \t\t\t{\t\t\t\t\"Status\" : \"Vacant\"\t\t\t}\t\t}\t}}";
   }

   private String updateGuestMessage(String tvUniqueId) {
      LOG.info("updateGuestMessage :{}", tvUniqueId);
      List<Message> messages = null;
      if (this.device != null) {
         messages = JpaManager.getMessageManager().findMessageByGuestIdsAndIsSend(this.device.getTvroomid(), "Y");
      }

      JSONArray array = PmsMessageUtils.getGuestMessagesArray(this.device, messages);
      JSONObject pmsChange = this.initialResponsePara("Change");
      JSONObject commandDetails = this.initialCommandDetails();
      JSONObject pMSParameters = new JSONObject();
      pMSParameters.put("GuestMessages", array);
      pMSParameters.put("Action", "UpdateGuestMessage");
      commandDetails.put("PMSParameters", pMSParameters);
      pmsChange.put("CommandDetails", commandDetails);
      return pmsChange.toString();
   }
}
