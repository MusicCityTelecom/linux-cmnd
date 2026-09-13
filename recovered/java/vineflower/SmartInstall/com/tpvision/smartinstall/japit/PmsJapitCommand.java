package com.tpvision.smartinstall.japit;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.GuestInfo;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.pms.CheckInVO;
import com.tpvision.smartinstall.pms.PmsUtils;
import com.tpvision.smartinstall.util.JAPITUtils;
import com.tpvision.smartinstall.util.TpvStringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class PmsJapitCommand extends JapitCommand {
   private JSONObject pmsParameters;

   public static JSONArray getPmsFeatureSupported(CheckInVO checkinVO) {
      JSONArray featureList = new JSONArray();
      featureList.put("RoomStatus");
      if (checkinVO == null) {
         featureList.put("RoomStatus:CheckIn");
         return featureList;
      }

      if ("True".equalsIgnoreCase(checkinVO.getExpressCheckout())) {
         featureList.put("RoomStatus:ExpressCheckOut");
      }

      featureList.put("RoomStatus:CheckIn");
      featureList.put("RoomStatus:CheckOut");
      if (checkinVO.isGuestDetailsEnabled()) {
         featureList.put("GuestDetails");
      }

      featureList.put("GuestPreferences");
      if (checkinVO.isGuestPreferLanguageEnabled()) {
         featureList.put("GuestPreferences:Language");
      }

      featureList.put("GuestPreferences:DoNotDisturb");
      if (checkinVO.isMessagesEnabled()) {
         featureList.put("GuestMessages");
      }

      if ("True".equalsIgnoreCase(checkinVO.getViewBill())) {
         featureList.put("Bill");
      }

      return featureList;
   }

   public static JSONObject getGuestDetails(CheckInVO checkinVO) {
      if (checkinVO == null) {
         return null;
      }

      JSONObject guestDetails = new JSONObject();
      guestDetails.put("DisplayName", checkinVO.getDisplayName());
      String groupName = checkinVO.getGroupName();
      if (groupName != null && !groupName.isEmpty()) {
         guestDetails.put("GroupName", checkinVO.getGroupName());
      }

      guestDetails.put("FirstName", checkinVO.getFirstName());
      guestDetails.put("SurName", checkinVO.getSurName());
      return guestDetails;
   }

   public static JSONObject getGuestPreferences(CheckInVO checkinVO) {
      if (checkinVO == null) {
         return null;
      }

      JSONObject guestPreferences = new JSONObject();
      if (checkinVO.isGuestPreferLanguageEnabled()) {
         guestPreferences.put("Language", checkinVO.getLanguage());
      }

      guestPreferences.put("DonotDisturb", checkinVO.getDonotDisturb());
      return guestPreferences;
   }

   private static JSONObject getDepartureDate(CheckInVO checkinVO) {
      if (checkinVO == null) {
         return null;
      }

      JSONObject roomStatus = new JSONObject();
      roomStatus.put("DepartureDate", checkinVO.getDepartureDate());
      return roomStatus;
   }

   public static JSONObject getRoomStatus(CheckInVO checkinVO) {
      JSONObject roomStatus = new JSONObject();
      if (checkinVO == null) {
         roomStatus.put("Status", "Vacant");
         return roomStatus;
      } else {
         roomStatus.put("Status", checkinVO.getRoomStatus());
         roomStatus.put("SharingStatus", checkinVO.getSharingStatus());
         roomStatus.put("ArrivalDate", checkinVO.getArrivalDate());
         roomStatus.put("ArrivalTime", checkinVO.getArrivalTime());
         roomStatus.put("DepartureDate", checkinVO.getDepartureDate());
         roomStatus.put("DepartureTime", checkinVO.getDepartureTime());
         return roomStatus;
      }
   }

   public static JSONObject getGuestBill(Devices tv, GuestInfo gi, JSONArray billItems) {
      JSONObject guestBill = new JSONObject();
      CheckInVO checkInVO = PmsUtils.getCheckInVO(gi);
      String balance = Optional.ofNullable(gi.getBalance())
         .orElseGet(() -> Optional.ofNullable(JpaManager.getBillitemManager().getBillAmount(TpvStringUtils.getPureTvRoomId(gi.getRoomid()))).orElse("0"));
      String totalBillDateTime = gi.getTotalBillDateTime();
      String totalBillDate = null == totalBillDateTime ? "17/11/2017" : totalBillDateTime.split(" ")[0];
      String totalBillTime = null == totalBillDateTime ? "15:30" : totalBillDateTime.split(" ")[1];
      guestBill.put("AddressingDisplayName", "");
      guestBill.put("TitleOfTheBill", "Please find your list of expenses:");
      guestBill.put("Currency", PmsUtils.getCurrency());
      guestBill.put("BillRoomNo", "");
      guestBill.put("TotalDisplay", "");
      guestBill.put("TotalDisplayAmount", checkInVO.isDemo() ? "1200" : balance);
      guestBill.put("TotalBillDate", totalBillDate);
      guestBill.put("TotalBillTime", totalBillTime);
      String defaultBillIcon = JpaManager.getPmsStatusManager().loadByKey(1).getBillIcon();
      if (StringUtils.isNoneBlank(defaultBillIcon)) {
         String serverPath = JAPITUtils.getServerRequestPath(tv);
         guestBill.put("BillIcon", serverPath + "files/image/pms/" + defaultBillIcon);
      }

      guestBill.put("BillItems", billItems);
      return guestBill;
   }

   public void newGuestBill(Devices tv, GuestInfo gi, JSONArray billItems) {
      JSONObject guestBill = getGuestBill(tv, gi, billItems);
      this.pmsParameters.put("GuestBill", guestBill);
   }

   public void updateGuestBill(Devices tv, GuestInfo gi, JSONArray billItems) {
      JSONObject guestBill = getGuestBill(tv, gi, billItems);
      this.pmsParameters.put("GuestBill", guestBill);
      this.pmsParameters.put("Action", PmsJapitCommand.PMSAction.UpdateGuestBill.name());
   }

   public PmsJapitCommand() {
      this.setCmdSvc(JapitCommand.CommandSvc.WebListeningServices);
      this.setCmdType(JapitCommand.CommandType.Change);
      this.setCookie(JAPITUtils.getJapitRandomCookieValue());
      this.setSvcVer("3.0");
      this.setCmdFun(JapitCommand.CommandFun.PMSService);
      this.pmsParameters = new JSONObject();
      this.setCmdDetail("PMSParameters", this.pmsParameters);
   }

   public static PmsJapitCommand responsePmsJapitCommand() {
      PmsJapitCommand japit = new PmsJapitCommand();
      japit.setCmdType(JapitCommand.CommandType.Response);
      japit.setCmdSvc(JapitCommand.CommandSvc.WebService);
      return japit;
   }

   public void updatePMSFeatures(CheckInVO checkinVO) {
      this.pmsParameters.put("PMSFeaturesSupported", getPmsFeatureSupported(checkinVO));
      this.pmsParameters.put("Action", PmsJapitCommand.PMSAction.UpdatePMSFeatures.name());
   }

   public void updateRoomStatus(CheckInVO checkinVO) {
      this.pmsParameters.put("RoomStatus", getDepartureDate(checkinVO));
      this.pmsParameters.put("Action", PmsJapitCommand.PMSAction.RoomStatusUpdate.name());
   }

   public void updateGuestPreference(CheckInVO checkinVO) {
      this.pmsParameters.put("GuestPreferences", getGuestPreferences(checkinVO));
      this.pmsParameters.put("Action", PmsJapitCommand.PMSAction.UpdateGuestPreferences.name());
   }

   public void updateGuestDetails(CheckInVO checkinVO) {
      if (checkinVO.isGuestDetailsEnabled()) {
         this.pmsParameters.put("GuestDetails", getGuestDetails(checkinVO));
      }

      this.pmsParameters.put("Action", PmsJapitCommand.PMSAction.UpdateGuestDetails.name());
   }

   public void checkin(CheckInVO checkinVO) {
      this.pmsParameters.put("Action", PmsJapitCommand.PMSAction.CheckIn.name());
      this.pmsParameters.put("PMSFeaturesSupported", getPmsFeatureSupported(checkinVO));
      this.pmsParameters.put("GuestPreferences", getGuestPreferences(checkinVO));
      this.pmsParameters.put("RoomStatus", getRoomStatus(checkinVO));
      if (checkinVO.isGuestDetailsEnabled()) {
         this.pmsParameters.put("GuestDetails", getGuestDetails(checkinVO));
      }
   }

   public void checkout() {
      this.pmsParameters.put("Action", PmsJapitCommand.PMSAction.CheckOut.name());
      this.pmsParameters.put("PMSFeaturesSupported", getPmsFeatureSupported(null));
      this.pmsParameters.put("RoomStatus", getRoomStatus(null));
   }

   public void newGuestMessage(JSONArray array) {
      this.pmsParameters.put("GuestMessages", array);
      this.pmsParameters.put("Action", PmsJapitCommand.PMSAction.NewGuestMessage.name());
   }

   public void updateGuestMessage(JSONArray array) {
      this.pmsParameters.put("GuestMessages", array);
      this.pmsParameters.put("Action", PmsJapitCommand.PMSAction.UpdateGuestMessage.name());
   }

   public void expressCheckOutError(String reason) {
      this.pmsParameters.put("Action", PmsJapitCommand.PMSAction.ExpressCheckOutError.name());
      this.pmsParameters.put("ExpressCheckOutStatus", reason);
      CheckInVO checkinVO = new CheckInVO();
      checkinVO.setRoomStatus("Occupied");
      checkinVO.setArrivalDate("");
      checkinVO.setArrivalTime("");
      checkinVO.setDepartureDate("");
      checkinVO.setDepartureTime("");
      this.pmsParameters.put("RoomStatus", getRoomStatus(checkinVO));
   }

   public void getPmsParameters(CheckInVO checkinVO, JSONObject parameters) {
      if (null == parameters || parameters.has("PMSFeaturesSupported")) {
         this.pmsParameters.put("PMSFeaturesSupported", getPmsFeatureSupported(checkinVO));
      }

      if ((null == parameters || parameters.has("GuestDetails")) && checkinVO.isGuestDetailsEnabled()) {
         this.pmsParameters.put("GuestDetails", getGuestDetails(checkinVO));
      }

      if (null == parameters || parameters.has("GuestPreferences")) {
         this.pmsParameters.put("GuestPreferences", getGuestPreferences(checkinVO));
      }

      if (null == parameters || parameters.has("RoomStatus")) {
         this.pmsParameters.put("RoomStatus", getRoomStatus(checkinVO));
      }
   }

   public static String getUpdatePMSFeatures(CheckInVO checkinVO) {
      PmsJapitCommand cmd = new PmsJapitCommand();
      cmd.updatePMSFeatures(checkinVO);
      return cmd.generateCommand();
   }

   public static String getNewGuestMessage(JSONArray array) {
      PmsJapitCommand cmd = new PmsJapitCommand();
      cmd.newGuestMessage(array);
      return cmd.generateCommand();
   }

   public static String getCheckIn(CheckInVO checkinVO) {
      PmsJapitCommand cmd = new PmsJapitCommand();
      cmd.checkin(checkinVO);
      return cmd.generateCommand();
   }

   public static String getCheckOut() {
      PmsJapitCommand cmd = new PmsJapitCommand();
      cmd.checkout();
      return cmd.generateCommand();
   }

   public static String getExpressCheckOutError(String reason) {
      PmsJapitCommand cmd = new PmsJapitCommand();
      cmd.expressCheckOutError(reason);
      return cmd.generateCommand();
   }

   public static String getUpdateGuestDetails(CheckInVO checkinVO) {
      PmsJapitCommand cmd = new PmsJapitCommand();
      cmd.updateGuestDetails(checkinVO);
      return cmd.generateCommand();
   }

   public static String getUpdateGuestPreference(CheckInVO checkinVO) {
      PmsJapitCommand cmd = new PmsJapitCommand();
      cmd.updateGuestPreference(checkinVO);
      return cmd.generateCommand();
   }

   public static String getUpdateRoomStatus(CheckInVO checkinVO) {
      PmsJapitCommand cmd = new PmsJapitCommand();
      cmd.updateRoomStatus(checkinVO);
      return cmd.generateCommand();
   }

   public static String responsePmsParameters(CheckInVO checkinVO, JSONObject parameters) {
      PmsJapitCommand cmd = responsePmsJapitCommand();
      cmd.getPmsParameters(checkinVO, parameters);
      return cmd.generateCommand();
   }

   public static String getNewGuestBill(Devices tv, GuestInfo gi, JSONArray billItems) {
      PmsJapitCommand cmd = new PmsJapitCommand();
      cmd.setCmdType(JapitCommand.CommandType.Response);
      cmd.newGuestBill(tv, gi, billItems);
      return cmd.generateCommand();
   }

   public static String getUpdateGuestBill(Devices tv, GuestInfo gi, JSONArray billItems) {
      PmsJapitCommand cmd = new PmsJapitCommand();
      cmd.setCmdType(JapitCommand.CommandType.Response);
      cmd.updateGuestBill(tv, gi, billItems);
      return cmd.generateCommand();
   }

   public static List<String> getGuestBills(Devices tv, GuestInfo gi, JSONArray billItems) {
      List<String> guestBillList = new ArrayList<>();
      guestBillList.add(getNewGuestBill(tv, gi, billItems));
      return guestBillList;
   }

   public enum PMSAction {
      RoomStatusUpdate("RoomStatusUpdate"),
      CheckIn("CheckIn"),
      CheckInSuccessful("CheckInSuccessful"),
      CheckOut("CheckOut"),
      CheckOutSuccessful("CheckOutSuccessful"),
      ExpressCheckOut("ExpressCheckOut"),
      ExpressCheckOutError("ExpressCheckOutError"),
      UpdateGuestDetails("UpdateGuestDetails"),
      UpdateGuestPreferences("UpdateGuestPreferences"),
      NewGuestMessage("NewGuestMessage"),
      UpdateGuestMessageStatus("UpdateGuestMessageStatus"),
      UpdateGuestMessage("UpdateGuestMessage"),
      UpdatePMSFeatures("UpdatePMSFeatures"),
      NewGuestBill("NewGuestBill"),
      UpdateGuestBill("UpdateGuestBill");

      private String type;

      PMSAction(String type) {
         this.type = type;
      }

      @Override
      public String toString() {
         return this.type;
      }
   }

   public enum PMSFeatures {
      RoomStatus("RoomStatus"),
      RoomStatusCheckIn("RoomStatus:CheckIn"),
      RoomStatusCheckOut("RoomStatus:CheckOut"),
      RoomStatusExpressCheckOut("RoomStatus:ExpressCheckOut"),
      GuestDetails("GuestDetails"),
      GuestPreferences("GuestPreferences"),
      GuestPreferencesLanguage("GuestPreferences:Language"),
      GuestPreferencesDoNotDisturb("GuestPreferences:DoNotDisturb"),
      GuestMessages("GuestMessages"),
      Bill("Bill");

      private String type;

      PMSFeatures(String type) {
         this.type = type;
      }

      @Override
      public String toString() {
         return this.type;
      }
   }
}
