package com.tpvision.smartinstall.servlet;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.Groups;
import com.tpvision.smartinstall.dao.core.GuestInfo;
import com.tpvision.smartinstall.dao.core.Message;
import com.tpvision.smartinstall.dao.core.PmsStatus;
import com.tpvision.smartinstall.dao.core.Reservation;
import com.tpvision.smartinstall.dao.mgr.DevicesManager;
import com.tpvision.smartinstall.dao.mgr.GroupsManager;
import com.tpvision.smartinstall.dao.mgr.GuestInfoManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.MessageManager;
import com.tpvision.smartinstall.dao.mgr.PmsStatusManager;
import com.tpvision.smartinstall.dao.mgr.ReservationManager;
import com.tpvision.smartinstall.japit.IPCloneServiceManager;
import com.tpvision.smartinstall.pms.PmsUtils;
import com.tpvision.smartinstall.pms.TmsUtils;
import com.tpvision.smartinstall.schedule.CmndMetricsTask;
import com.tpvision.smartinstall.util.CloneItemUtils;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.ContentUtils;
import com.tpvision.smartinstall.util.IPUpgradeManager;
import com.tpvision.smartinstall.util.NetworkUtils;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.PmsMessageUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.TpvStringUtils;
import com.tpvision.smartinstall.util.Utils;
import com.tpvision.smartinstall.util.WelcomeLogoUtils;
import com.tpvision.smartinstall.util.ZipCommonUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PMSServlet extends BaseHttpServlet {
   private static final long serialVersionUID = 1L;
   private static final Logger LOG = LoggerFactory.getLogger(PMSServlet.class);
   private static final String MESSAGE_SEND_PAGE_SUBMIT_TIME_FORMAT = "dd/MM/yyyy HH:mm";

   @Override
   public void init() {
      PmsMessageUtils.syncMessageIconCached();
   }

   @Override
   protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String type = request.getParameter("type");

      try {
         switch (type.toUpperCase(Locale.ROOT)) {
            case "CREATEMSG":
               this.createMsg(request, response);
               break;
            case "GETMSGDATA":
               this.getMsgData(request, response);
               break;
            case "DELMSG":
               this.delMsg(request, response);
               break;
            case "GETMESSAGEICONLIST":
               this.getMessageIconList(request, response);
               break;
            case "UPLOADMESSAGEICON":
               this.uploadMessageIcon(request, response);
               break;
            case "DELETEMESSAGEICON":
               this.deleteMessageIcon(request, response);
               break;
            case "GETMESSAGEICON":
               this.getMessageIcon(request, response);
               break;
            case "GETMESSAGEDETAILS":
               this.getMessageDetails(request, response);
               break;
            case "RENAMEPROPERTY":
               this.renameProperty(request, response);
               break;
            case "SWITCHCHECKSTATUS":
               this.switchCheckStatus(request, response);
               break;
            case "SWITCHDONOTDISTURB":
               this.switchDoNotDisturb(request, response);
               break;
            case "SWITCHVIEWBILL":
               this.switchViewBill(request, response);
               break;
            case "SWITCHEXPRESSCHECKOUT":
               this.switchExpressCheckout(request, response);
               break;
            case "RENAMEGUESTNAME":
               this.renameGuestname(request, response);
               break;
            case "RENAMEGROUPNAME":
               this.renameGroupName(request, response);
               break;
            case "MODIFYMESSAGE":
               this.modifyMessage(request, response);
               break;
            case "SELECTGUESTLANGUAGE":
               this.selectGuestLanguage(request, response);
               break;
            case "SELECTCHANNELPACKAGE":
               this.selectChannelPackage(request, response);
               break;
            case "SELECTAPPPACKAGE":
               this.selectAppPackage(request, response);
               break;
            case "UPDATEGUESTINFO":
               this.updateGuestInfo(request, response);
               break;
            case "GETGUESTIDDATA":
               this.getGuestIdData(response);
               break;
            case "ADMINPMSMGRCHANGE":
               this.adminPmsMgrChange(request, response);
               break;
            case "ADMINPMSURLCHANGE":
               this.adminPmsUrlChange(request, response);
               break;
            case "ADMINPMSTAG":
               this.adminPmsTag(response);
               break;
            case "UPDATEALLOWMULTIPLESTATUS":
               String status = request.getParameter("Status");
               PmsUtils.updateAllowMultipleStatus(status);
               break;
            case "GETMSGFILTER2":
               this.getMsgFilter2(request, response);
               break;
            case "REFRESHCURRENTROOMINFO":
               this.refreshCurrentRoomInfo(request, response);
               break;
            case "GETMSGRCPNT":
               this.getMsgRcpnt(request, response);
               break;
            case "GETDEVICEDETAILS":
               this.getDeviceDetails(request, response);
               break;
            case "UPDATESELECTEDCLONE":
               this.updateSelectedClone(request, response);
               break;
            case "GOTOEDIT":
               this.gotoEditPage(request, response);
               break;
            case "GETROOMDIGITS":
               this.getRoomDigits(request, response);
               break;
            case "GETGROUPDETAILS":
               this.getGroupDetails(request, response);
               break;
            case "CHANGEROOMID":
               this.changeRoomID(request, response);
               break;
            case "GET_PMS_STATUS":
               this.getTMSConnectionStatus(response);
               break;
            case "LOADENCODINGLIST":
               this.loadEncodingList(response);
               break;
            case "SAVE_ICON":
               this.saveIcons(request, response);
               break;
            case "UPDATELIMITNETWORKINTERFACE":
               this.updateLimitNetworkInterface(request, response);
               break;
            case "GET_NETWORK_INTERFACE":
               this.getLimitNetworkInterface(response);
               break;
            default:
               this.unknownCommand(request, response);
         }
      } catch (IOException | SQLException e) {
         LOG.error(e.getMessage(), e);
      }
   }

   private void loadEncodingList(HttpServletResponse response) {
      JSONArray arr = new JSONArray();

      for (String charset : Charset.availableCharsets().keySet()) {
         arr.put(charset);
      }

      this.responseJSON(this.successStatus(arr), response);
   }

   private void saveIcons(HttpServletRequest request, HttpServletResponse response) {
      try {
         List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
         FileItem fileItem = null;
         String iconType = null;

         for (FileItem item : items) {
            if (item.isFormField()) {
               if (StringUtils.equalsIgnoreCase("icon_type", item.getFieldName())) {
                  iconType = item.getString("UTF-8");
               }
            } else {
               fileItem = item;
            }
         }

         if (fileItem == null) {
            Utils.renderErrorJsonMsg("upload icon file is empty", response);
            return;
         }

         if (!Arrays.asList("message", "bill").contains(iconType)) {
            Utils.renderErrorJsonMsg("icon type error, only support message/bill icon", response);
            return;
         }

         String imageName = PmsMessageUtils.saveIconFileToServer(iconType, fileItem);
         JSONObject result = new JSONObject();
         result.put("type", iconType);
         result.put("image", imageName);
         Utils.renderSuccessJsonData(result, response);
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         Utils.renderErrorJsonMsg(e.getMessage(), response);
      }
   }

   private void unknownCommand(HttpServletRequest request, HttpServletResponse response) {
      String status = "{\"status\":\"fail\"}";

      try {
         response.setContentType("text/json");
         IOUtils.write(status.getBytes(), response.getOutputStream());
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }
   }

   private void getTMSConnectionStatus(HttpServletResponse response) {
      JSONObject obj = new JSONObject();
      PmsStatus ps = PmsUtils.getPmsStatus();
      obj.put("pmsStatus", ps.getPmsconnectionstatus());
      obj.put("pmsVersion", ps.getPmsconnectionversion());
      obj.put("pmsInfo", ps.getPmsconnectioninfo());
      this.responseJSON(this.successStatus(obj), response);
   }

   private void updateLimitNetworkInterface(HttpServletRequest request, HttpServletResponse response) {
      String networkInterface = request.getParameter("NetworkInterface");
      PmsUtils.updateLimitNetworkInterface(networkInterface);
      this.responseJSON(this.successStatus(""), response);
   }

   private void getLimitNetworkInterface(HttpServletResponse response) {
      List<String> limitNetworkList = NetworkUtils.getSubnetList();
      limitNetworkList.add(0, "Off");
      limitNetworkList.add(1, "localhost");
      this.responseJSON(this.successStatus(limitNetworkList), response);
   }

   private void changeRoomID(HttpServletRequest request, HttpServletResponse response) {
      String status = "{\"status\":\"success\"}";
      String roomID = request.getParameter("roomID");
      String tvID = request.getParameter("tvID");
      DevicesManager devicesManager = JpaManager.getDevicesManager();
      Devices devices = devicesManager.loadByKey(tvID);
      if ("TPM215HEA".equalsIgnoreCase(devices.getType()) || "TPM215HKN".equalsIgnoreCase(devices.getType())) {
         boolean isFiveDigits = roomID.matches("\\d{5}");
         if (!isFiveDigits) {
            Utils.renderErrorJsonMsg(" the room id lenght must be 5 digits!", response);
            return;
         }
      }

      devices.setTvroomid(roomID);
      devicesManager.save(devices);
      IPCloneServiceManager.sendRoomIdToTV(devices);
      Utils.writeToResponse(status, "text/html;charset=UTF-8", response);
   }

   private void getGroupDetails(HttpServletRequest request, HttpServletResponse response) {
      String platform = request.getParameter("platform");
      JSONObject resObj = new JSONObject("{\"status\":\"success\"}");
      JSONObject groupObj = new JSONObject();
      JSONObject cloneListObj = this.getCloneList(platform);
      groupObj.put("cloneList", cloneListObj);
      resObj.put("group", groupObj);
      Utils.writeToResponse(resObj.toString(), "text/html;charset=UTF-8", response);
   }

   private void getRoomDigits(HttpServletRequest request, HttpServletResponse response) {
      String platform = request.getParameter("platform");
      int roomDigits = PlatformUtils.getRoomDigits(platform);
      JSONObject resObj = new JSONObject("{\"status\":\"success\"}");
      resObj.put("digits", roomDigits);
      Utils.writeToResponse(resObj.toString(), "text/html;charset=UTF-8", response);
   }

   private void gotoEditPage(HttpServletRequest request, HttpServletResponse response) {
      JSONObject resObj = new JSONObject("{\"status\":\"success\"}");
      String cloneName = request.getParameter("id");
      String value = request.getParameter("value");
      String name = request.getParameter("name");
      int val = 0;

      try {
         val = Integer.parseInt(value);
      } catch (NumberFormatException e) {
         LOG.error("invalid integer");
      }

      String basePath = this.getServletContext().getContextPath();
      String path = "";
      CommonConstants.CloneItemType cloneType = CloneItemUtils.getCloneItemTypeByName(cloneName);
      switch (cloneType) {
         case Clone:
            path = val > 0 ? "/setting?mode=SET&sname=" + name : "/getFile?mode=index#tabs_clone";
            break;
         case Firmware:
            path = "/getFile?mode=index#tabs_firmware";
            break;
         case TVSettings:
            path = val > 0 ? "/settingpackage?mode=SETTING_INDEX&id=" + val : "/getFile?mode=index#tabs_settingPackage";
            break;
         case AndroidApps:
            path = val > 0 ? "/appPackage?mode=APP_INDEX&id=" + val : "/getFile?mode=index#tabs_app";
            break;
         case ChannelList:
            path = val > 0 ? "/channel?mode=INDEX&channelPackageId=" + val : "/getFile?mode=index#tabs_channelPackage";
            break;
         case SmartInfoBrowser:
            path = ContentUtils.getContentEditUrl(val);
            break;
         case Banner:
            path = val > 0 ? "/banners?mode=BANNERS_INDEX&id=" + val : "/getFile?mode=index#tabs_banners";
            break;
         case Schedules:
            path = val > 0 ? "/schedule?mode=SCHEDULE_INDEX&id=" + val + "&cloneId=-1" : "/getFile?mode=index#tabs_schedule";
            break;
         case WelcomeLogo:
            path = WelcomeLogoUtils.getWelcomeEditPath(val);
            break;
         case UiCustomizations:
            path = val > 0 ? "/ui?mode=UICUSTOMIZATIONS_INDEX&id=" + val : "/getFile?mode=index#tabs_uiCustomizations";
            break;
         default:
            path = "";
      }

      if (!path.isEmpty() && !path.startsWith("http")) {
         path = basePath + path;
      }

      resObj.put("status", path.length() > 0 ? "success" : "fail");
      resObj.put("url", path);
      Utils.writeToResponse(resObj.toString(), "text/html;charset=UTF-8", response);
   }

   private void getMessageDetails(HttpServletRequest request, HttpServletResponse response) {
      String status = "{\"status\":\"fail\"}";
      JSONObject statusObj = new JSONObject("{\"status\":\"fail\"}");
      String msgid = request.getParameter("msgid");
      Message message = JpaManager.getMessageManager().loadByKey(msgid);
      if (null != message) {
         JSONObject jsonObj = new JSONObject();
         jsonObj.put("id", message.getId());
         jsonObj.put("timeSend", message.getTimeSend());
         jsonObj.put("status", message.getStatus());
         String content = message.getContent();
         jsonObj.put("content", content);
         jsonObj.put("guestIds", message.getGuestIds());
         String title = message.getTitle();
         jsonObj.put("title", title);
         String icon = message.getIcon();
         jsonObj.put("icon", icon);
         jsonObj.put("isSent", message.getIsSent());
         statusObj.put("message", jsonObj);
         statusObj.put("status", "success");
         status = statusObj.toString();
      }

      Utils.writeToResponse(status, "text/json;charset=UTF-8", response);
   }

   private void getMessageIcon(HttpServletRequest request, HttpServletResponse response) {
      String name = request.getParameter("iconPath");
      PmsMessageUtils.renderIcon(name, response);
   }

   private void uploadMessageIcon(HttpServletRequest request, HttpServletResponse response) {
      JSONObject statusObj = new JSONObject("{\"status\":\"fail\"}");

      try {
         String filename = PmsMessageUtils.doMessageIconUpload(request);
         statusObj = new JSONObject("{\"status\":\"success\"}");
         statusObj.put("filename", filename);
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         statusObj.put("reason", e.getMessage());
      }

      Utils.writeToResponse(statusObj.toString(), "text/json;charset=UTF-8", response);
   }

   private void deleteMessageIcon(HttpServletRequest request, HttpServletResponse response) {
      JSONObject statusObj = new JSONObject("{\"status\":\"fail\"}");

      try {
         PmsMessageUtils.deleteMessageIcon(request.getParameter("iconName"));
         statusObj = new JSONObject("{\"status\":\"success\"}");
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         statusObj.put("reason", e.getMessage());
      }

      Utils.writeToResponse(statusObj.toString(), "text/json;charset=UTF-8", response);
   }

   private void getMessageIconList(HttpServletRequest request, HttpServletResponse response) {
      JSONObject obj = new JSONObject("{\"status\":\"success\"}");
      obj.put("iconList", PmsMessageUtils.loadPmsIconArray());
      Utils.writeToResponse(obj.toString(), "text/json;charset=UTF-8", response);
   }

   private void modifyMessage(HttpServletRequest request, HttpServletResponse response) {
      String status = "{\"status\":\"success\"}";
      String msgid = request.getParameter("id");
      int id = Integer.parseInt(msgid);
      String recipients = request.getParameter("recipients");
      String message = request.getParameter("message");
      String timeSendNow = request.getParameter("timeSendNow");
      String timeSendNotNow = request.getParameter("timeSendNotNow");
      MessageManager msgMgr = JpaManager.getMessageManager();
      Message msg = null;
      Date date = null;

      try {
         msg = msgMgr.loadByKey(msgid);
         if (msg == null) {
            throw new IOException("message not found:" + id);
         }

         msg.setGuestIds(recipients);
         msg.setContent(message);
         String title = request.getParameter("msgtitle");
         if (title.length() > 200) {
            title = title.substring(0, 200);
         }

         msg.setTitle(title);
         msg.setIcon(request.getParameter("icon"));
         SimpleDateFormat format = TpvDateUtils.getMessageTimeFormat();
         if ("true".equalsIgnoreCase(timeSendNow)) {
            date = new Date();
            msg.setIsSent("Y");
         } else {
            SimpleDateFormat formatter = TpvDateUtils.getSimpleDateFormatWithEnglishLocale("dd/MM/yyyy HH:mm");
            date = formatter.parse(timeSendNotNow);
            msg.setIsSent("N");
         }

         msg.setTimeSend(format.format(date));
         msgMgr.save(msg);
      } catch (IOException | ParseException e) {
         LOG.error(e.getMessage(), e);
         status = this.failedStatus(e.getMessage());
      }

      try {
         IOUtils.write(status.getBytes(), response.getOutputStream());
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }
   }

   private JSONArray getSupportedList(CommonConstants.CloneItemType cloneType, String platform) {
      String sqltpl = "select id,name from %s where platform='%s'";
      String sql = "";
      String tableName = "";
      switch (cloneType) {
         case Clone:
            tableName = "setting";
            sqltpl = "select id,cloneRename as name from %s where platform='%s'";
            sql = String.format(Locale.ENGLISH, sqltpl, tableName, PlatformUtils.getPlatformId(platform));
            break;
         case Firmware:
            tableName = "upg_setting";
            sqltpl = "select id,upgRename as name from %s where platform='%s'";
            sql = String.format(Locale.ENGLISH, sqltpl, tableName, PlatformUtils.getPlatformType(platform));
            break;
         case TVSettings:
            tableName = "settingpackage";
            sql = String.format(Locale.ENGLISH, sqltpl, tableName, platform);
            break;
         case AndroidApps:
            tableName = "apppackage";
            sql = String.format(Locale.ENGLISH, sqltpl, tableName, platform);
            break;
         case ChannelList:
            tableName = "channelpackage";
            sql = String.format(Locale.ENGLISH, sqltpl, tableName, platform);
            break;
         case SmartInfoBrowser:
            String contentListStr = ContentUtils.getContentList();

            try {
               return new JSONArray(contentListStr);
            } catch (JSONException e) {
               LOG.error("SmartInfoBrowser's content is not a valid JSON array:{}", contentListStr, e);
               return null;
            }
         case Banner:
            tableName = "banners";
            sql = String.format(Locale.ENGLISH, "select id,name from %s", tableName);
            break;
         case Schedules:
            tableName = "schedule";
            sql = String.format(Locale.ENGLISH, sqltpl, tableName, platform);
            break;
         case WelcomeLogo:
            String logoList = WelcomeLogoUtils.getInstance().getWelcomeLogoListByJson(platform);
            return new JSONArray(logoList);
         case UiCustomizations:
            tableName = "uiCustomizations";
            sql = String.format(Locale.ENGLISH, sqltpl, tableName, platform);
            break;
         default:
            LOG.error("Unknown content type in getSupportedList:{} ", cloneType);
      }

      if (!sql.isEmpty()) {
         try {
            return JpaManager.getResultsetAsArray(sql);
         } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
         }
      }

      return new JSONArray();
   }

   private JSONObject getCloneList(String platform) {
      JSONObject device = new JSONObject();
      JSONArray partialClones = new JSONArray();

      for (CommonConstants.CloneItemType itemType : PlatformUtils.getPartialCloneList(platform)) {
         JSONArray list = this.getSupportedList(itemType, platform);
         JSONObject partialClone = new JSONObject();
         partialClone.put("name", CloneItemUtils.getItemTypeMapName(itemType));
         partialClone.put("idName", CloneItemUtils.getItemTypeIdName(itemType));
         partialClone.put("playType", itemType.name());
         partialClone.put("data", list);
         partialClones.put(partialClone);
      }

      device.put("partialClones", partialClones);
      device.put("Clone", this.getSupportedList(CommonConstants.CloneItemType.Clone, platform));
      device.put("Software", this.getSupportedList(CommonConstants.CloneItemType.Firmware, platform));
      return device;
   }

   private void getDeviceDetails(HttpServletRequest request, HttpServletResponse response) throws SQLException {
      JSONObject data = new JSONObject();
      String tvid = request.getParameter("tvid");
      JSONArray array = JpaManager.getDevicesManager().findDeviceInfoViewDataByTvId(tvid);
      if (array.length() > 0) {
         JSONObject device = (JSONObject)array.get(0);
         String platform = device.get("Type").toString();
         JSONObject cloneList = this.getCloneList(platform);
         device.put("cloneList", cloneList);
         this.updateDeviceInfo(device);
         data.put("devices", array);
         Utils.writeToResponse(data.toString(), "text/html;charset=UTF-8", response);
      } else {
         LOG.error("Display cannot be found in database, tvid={}", tvid);
         String status = this.failedStatus("Display cannot be found in database, tvid=" + tvid);
         Utils.writeToResponse(status, "text/html;charset=UTF-8", response);
      }
   }

   private void updateDeviceInfo(JSONObject device) {
      String departureDate = device.getString("departureDate");
      String checkoutTime = device.getString("checkout_time");
      String formatTime = null;
      if (!"".equalsIgnoreCase(checkoutTime)) {
         LOG.info("checkoutTime is {}", checkoutTime);
         formatTime = TpvDateUtils.formatStringDate(checkoutTime, "yyyy-MM-dd HH:mm");
         device.put("checkoutTime", formatTime);
      } else if (!"".equalsIgnoreCase(departureDate)) {
         LOG.info("departureDate is {}", departureDate);
         formatTime = TpvDateUtils.formatStringDate(departureDate, "yyyy-MM-dd HH:mm");
         device.put("departureDate", formatTime);
      }
   }

   private void getGuestIdData(HttpServletResponse response) throws SQLException {
      JSONArray roomidsArray = JpaManager.getGuestInfoManager().findAllRoomids();
      JSONObject result = new JSONObject();
      result.put("availableTags", roomidsArray);
      Utils.writeToResponse(result.toString(), "text/html;charset=UTF-8", response);
   }

   private void renameGuestname(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String status = null;
      String _id = request.getParameter("id");
      String guestName = request.getParameter("name");
      String roomId = request.getParameter("roomid");
      LOG.info("renameGuestname for room {} to {}", roomId, guestName);

      try {
         GuestInfo gi = PmsUtils.renameGuest(_id, guestName, roomId);
         status = this.successStatus(gi);
      } catch (IOException e1) {
         LOG.error(e1.getMessage(), e1);
         status = this.failedStatus(e1.getMessage());
      }

      this.responseJSON(status, response);
   }

   private void renameGroupName(HttpServletRequest request, HttpServletResponse response) {
      String status = "{\"status\":\"fail\"}";
      String guestId = request.getParameter("id");
      String groupName = request.getParameter("groupName");
      String roomId = request.getParameter("roomid");
      LOG.info("renameGroupName for room:{} to {}", roomId, groupName);

      try {
         GuestInfo gi = PmsUtils.renameGroup(guestId, groupName, roomId);
         status = this.successStatus(gi);
      } catch (Exception e1) {
         LOG.error(e1.getMessage(), e1);
         status = this.failedStatus(e1.getMessage());
      }

      this.responseJSON(status, response);
   }

   private void renameProperty(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String status = "{\"status\":\"fail\"}";
      String propertyName = request.getParameter("name");
      File file = new File(CommonConstants.LOCATION_MANAGER_STORE);
      JSONObject obj = null;
      String hotelInfo = null;
      if (!file.exists()) {
         file.createNewFile();
      }

      hotelInfo = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
      if ("".equals(hotelInfo)) {
         hotelInfo = "{\"country\":\"\",\"city\":\"\",\"geonameid\":\"\",\"pin\":\"\",\"addressLin1\":\"\",\"addressLin2\":\"\",\"hotelName\":\"\"}";
      }

      obj = new JSONObject(hotelInfo);
      obj.put("hotelName", propertyName);
      FileUtils.writeStringToFile(file, obj.toString(), StandardCharsets.UTF_8);
      status = "{\"status\":\"success\"}";
      IOUtils.write(status.getBytes(), response.getOutputStream());
   }

   private void createMsg(HttpServletRequest request, HttpServletResponse response) {
      String status = "{\"status\":\"success\"}";
      String sendTime = request.getParameter("optionsRadios");
      String roomIds = request.getParameter("recipienttext");
      String title = request.getParameter("msgtitle");
      String content = request.getParameter("msgtext");
      String icon = request.getParameter("icon");
      LOG.info("createMsg for room:{}", roomIds);

      try {
         List<Message> createdMessages = PmsMessageUtils.createMessage(roomIds, title, content, icon, sendTime);
         if (createdMessages.isEmpty()) {
            status = this.failedStatus("unable to create message");
         }
      } catch (Exception e2) {
         LOG.error(e2.getMessage(), e2);
         status = this.failedStatus(e2.getMessage());
      }

      Utils.writeToResponse(status, "text/html;charset=UTF-8", response);
   }

   private void getMsgData(HttpServletRequest request, HttpServletResponse response) throws SQLException {
      int current = null != request.getParameter("current") ? Integer.valueOf(request.getParameter("current")) : 1;
      int rowCount = null != request.getParameter("rowCount") ? Integer.valueOf(request.getParameter("rowCount")) : 10;
      String searchPhrase = request.getParameter("searchPhrase");
      String msgFilter1 = request.getParameter("msg_filter1");
      String msgFilter2 = request.getParameter("msg_filter2");
      if ("Guest Name".equals(msgFilter1)) {
         msgFilter1 = "guestName";
      } else if ("Guest Group".equals(msgFilter1)) {
         msgFilter1 = "guestGroup";
      } else if ("TV Group".equals(msgFilter1)) {
         msgFilter1 = "tvGroupName";
      } else if ("Reservation Id".equals(msgFilter1)) {
         msgFilter1 = "reservationId";
      } else if ("Room Id".equals(msgFilter1)) {
         msgFilter1 = "roomid";
      }

      StringBuilder sql = new StringBuilder("select * from message_view v where 1=1 ");
      StringBuilder sqlCount = new StringBuilder("select count(*) from message_view v where 1=1 ");
      List<Object> paramList = new ArrayList<>();
      if (StringUtils.isNotBlank(searchPhrase)) {
         String condition = " and (timeSend like ?  or content like ? or guestIds like ?)";
         sql.append(condition);
         sqlCount.append(condition);
         paramList.add("%" + searchPhrase + "%");
         paramList.add("%" + searchPhrase + "%");
         paramList.add("%" + searchPhrase + "%");
      }

      if (null != msgFilter1 && msgFilter1.equalsIgnoreCase("tvGroupName") && StringUtils.isNotBlank(msgFilter2)) {
         String condition = " and find_in_set(?, TVGroups)";
         sql.append(condition);
         sqlCount.append(condition);
         paramList.add(msgFilter2);
         msgFilter1 = null;
      }

      if (StringUtils.isNotBlank(msgFilter1) && !"All".equals(msgFilter1) && TpvStringUtils.isValidMysqlFieldName(msgFilter1)) {
         String condition = " and " + msgFilter1 + "=?";
         paramList.add(msgFilter2);
         sql.append(condition);
         sqlCount.append(condition);
      }

      String sort = "";
      String guestIds = request.getParameter("sort[guestIds]");
      String content = request.getParameter("sort[content]");
      String timeSend = request.getParameter("sort[timeSend]");
      if (null != guestIds) {
         if (guestIds.equals("asc")) {
            guestIds = "desc";
         } else {
            guestIds = "asc";
         }

         sql.append(" order by guestIds " + guestIds);
         sort = "guestIds&" + guestIds;
      } else if (null != content) {
         if (content.equals("asc")) {
            content = "desc";
         } else {
            content = "asc";
         }

         sql.append(" order by content " + content);
         sort = "content&" + content;
      } else if (null != timeSend) {
         if (timeSend.equals("asc")) {
            timeSend = "desc";
         } else {
            timeSend = "asc";
         }

         sql.append(" order by timeSend " + timeSend);
         sort = "timeSend&" + timeSend;
      } else {
         sql.append(" order by id desc");
         sort = "id&desc";
      }

      if (rowCount > 0) {
         int offset = 0;
         if (current > 0) {
            offset = (current - 1) * rowCount;
         }

         sql.append(" limit " + offset + "," + rowCount);
      }

      Map<String, String> params = new HashMap<>();
      params.put("PMS_tabsMsg_page", String.valueOf(current));
      params.put("PMS_tabsMsg_dropdownText", String.valueOf(rowCount));
      params.put("PMS_tabsMsg_search", searchPhrase);
      params.put("PMS_tabsMsg_sort", sort);
      Utils.updateUserProfileConfig(params);
      JSONArray resultArray = JpaManager.getResultsetAsArray(sql.toString(), paramList.toArray());
      int i = 0;

      for (int j = resultArray.length(); i < j; i++) {
         JSONObject row = resultArray.getJSONObject(i);
         row.put("timeSend", this.convertMessageTime(row.getString("timeSend")));
      }

      JSONObject data = new JSONObject();
      data.put("current", current);
      data.put("rowCount", rowCount);
      data.put("total", JpaManager.countRecordsInDB(sqlCount.toString(), paramList.toArray()));
      data.put("rows", resultArray);
      Utils.writeToResponse(data.toString(), "text/json;charset=UTF-8", response);
   }

   public String convertMessageTime(String timeSend) {
      SimpleDateFormat dateFormat2 = TpvDateUtils.getSimpleDateFormatWithEnglishLocale("dd MMM yyyy HH:mm");
      Date date = new Date();
      if (null != timeSend && timeSend.length() > 0) {
         date = TpvDateUtils.parseMessageDate(timeSend);
      }

      return dateFormat2.format(date);
   }

   private void delMsg(HttpServletRequest request, HttpServletResponse response) {
      String id = request.getParameter("id");
      LOG.info("delMsg {}", id);
      PmsUtils.deleteMessage(id);
      this.responseJSON("{\"status\":\"success\"}", response);
   }

   private void switchCheckStatus(HttpServletRequest request, HttpServletResponse response) {
      String status = "{\"status\":\"fail\"}";
      String id = request.getParameter("id");
      String roomId = request.getParameter("roomId");
      LOG.info("switchCheckStatus for guest:{},room:{}", id, roomId);
      String checkin = request.getParameter("checkin");
      boolean toCheckin = !"true".equalsIgnoreCase(checkin);

      try {
         CmndMetricsTask.setPmsTypeName("CMND");
         GuestInfo gi = PmsUtils.switchCheckInStatus(id, toCheckin);
         CmndMetricsTask.resetPmsTypeName();
         status = this.successStatus(gi);
      } catch (NumberFormatException | IOException e1) {
         LOG.error(e1.getMessage(), e1);
         CmndMetricsTask.resetPmsTypeName();
         status = this.failedStatus(e1.getMessage());
      }

      this.responseJSON(status, response);
   }

   private void switchDoNotDisturb(HttpServletRequest request, HttpServletResponse response) {
      String status = "{\"status\":\"fail\"}";
      String id = request.getParameter("id");
      LOG.info("switchDoNotDisturb for {}", id);

      try {
         GuestInfo gi = PmsUtils.switchDoNotDisturb(id);
         status = this.successStatus(gi);
      } catch (IOException e1) {
         LOG.error(e1.getMessage(), e1);
         status = this.failedStatus(e1.getMessage());
      }

      this.responseJSON(status, response);
   }

   private void switchViewBill(HttpServletRequest request, HttpServletResponse response) {
      String status = "{\"status\":\"fail\"}";
      String id = request.getParameter("id");
      LOG.info("switchViewBill for {}", id);

      try {
         GuestInfo gi = PmsUtils.switchViewBill(id);
         status = this.successStatus(gi);
      } catch (SQLException | IOException e) {
         LOG.error(e.getMessage(), e);
         status = this.failedStatus(e.getMessage());
      }

      this.responseJSON(status, response);
   }

   private void switchExpressCheckout(HttpServletRequest request, HttpServletResponse response) {
      String status = "{\"status\":\"fail\"}";
      String id = request.getParameter("id");
      LOG.info("switchExpressCheckout for {}", id);

      try {
         GuestInfo gi = PmsUtils.switchExpressCheckout(id);
         status = this.successStatus(gi);
      } catch (SQLException | IOException e) {
         LOG.error(e.getMessage(), e);
         status = this.failedStatus(e.getMessage());
      }

      this.responseJSON(status, response);
   }

   private void selectChannelPackage(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String status = "{\"status\":\"fail\"}";
      String id = request.getParameter("id");
      String curVal = request.getParameter("currentValue");
      String tvUId = request.getParameter("tvUId");
      LOG.info("selectChannelPackage for {} to lang:{}", id, curVal);
      GuestInfoManager gim = JpaManager.getGuestInfoManager();
      GuestInfo gi = null;
      DevicesManager tvmgr = JpaManager.getDevicesManager();
      Devices tv = null;

      try {
         gi = gim.loadByKey(id);
         gi.setChannelPackageId(Integer.valueOf(curVal));
         gim.save(gi);
         if ("0".equals(curVal)) {
            tv = tvmgr.loadByKey(tvUId);
            tv.setCloneColor("black");
            tv.setUpgradeType("channel");
            tvmgr.save(tv);
         }

         status = "{\"status\":\"success\"}";
      } catch (NumberFormatException e) {
         LOG.error(e.getMessage(), e);
         status = this.failedStatus(e.getMessage());
      }

      response.setContentType("text/json");
      IOUtils.write(status.getBytes(), response.getOutputStream());
   }

   private void selectAppPackage(HttpServletRequest request, HttpServletResponse response) {
      String tvid = request.getParameter("tvid");
      String apid = request.getParameter("currentValue");
      LOG.info("selectAppPackage for tv:{} to:{}", tvid, apid);
      if ("0".equals(apid)) {
         try {
            request.getRequestDispatcher("/IPTVServlet?mode=updating&id=" + tvid + "&progress=ST").forward(request, response);
         } catch (ServletException | IOException e) {
            LOG.error(e.getMessage(), e);
         }
      } else {
         int cloneId = Integer.parseInt("9999" + apid);
         String zipPathDir = CommonConstants.servletContextPath + "/Profile/Clone/9999" + apid + "/";
         String zipPath = CommonConstants.servletContextPath + "/Profile/Clone/9999" + apid + "/AndroidApps.zip";
         String srcPath = CommonConstants.CLONE_PROCESS_LOCATION + "AppPackages/" + apid + "/";

         try {
            File f = new File(zipPathDir);
            if (null != f && !f.exists()) {
               f.mkdirs();
            }

            ZipCommonUtils.createZip(srcPath, zipPath);
         } catch (IOException e) {
            LOG.error(e.getMessage(), e);
         }

         Devices device = JpaManager.getDevicesManager().loadByKey(tvid);
         device.setCloneid(cloneId);
         device.setUpgradeType("app");
         IPCloneServiceManager.setColorForFWClone(device, "blue");
         JpaManager.getDevicesManager().save(device);

         try {
            request.getRequestDispatcher("/IPTVServlet?mode=updating&id=" + tvid + "&progress=U").forward(request, response);
         } catch (ServletException | IOException e) {
            LOG.error(e.getMessage(), e);
         }
      }
   }

   public List<Devices> getTVListByGroupName(String groupName) {
      return JpaManager.getDevicesManager().findDevicesByGroupName(groupName).stream().filter(e -> !e.isRFDevice()).collect(Collectors.toList());
   }

   private void updateSelectedClone(HttpServletRequest request, HttpServletResponse response) throws IOException {
      AsyncContext asyncContext = request.startAsync();
      asyncContext.setTimeout(51000L);
      asyncContext.start(new PMSServlet.PartialUpgradeTask(asyncContext));
   }

   private void selectGuestLanguage(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String roomid = request.getParameter("roomid");
      String value = request.getParameter("value");
      String status = null;
      LOG.info("selectGuestLanguage for room:{} to {}", roomid, value);

      try {
         CmndMetricsTask.setPmsTypeName("CMND");
         PmsUtils.changeLanguage(roomid, value);
         CmndMetricsTask.resetPmsTypeName();
         status = "{\"status\":\"success\"}";
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         CmndMetricsTask.resetPmsTypeName();
         status = this.failedStatus(e.getMessage());
      }

      this.responseJSON(status, response);
   }

   private void updateGuestInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String status = "{\"status\":\"fail\"}";
      String id = request.getParameter("id");
      String apid = request.getParameter("currentValue");
      GuestInfoManager gim = JpaManager.getGuestInfoManager();
      GuestInfo gi = null;

      try {
         gi = gim.loadByKey(id);
         gi.setAppPackageId(Integer.parseInt(apid));
         gim.save(gi);
         status = "{\"status\":\"success\"}";
      } catch (NumberFormatException e) {
         LOG.error(e.getMessage(), e);
         status = this.failedStatus(e.getMessage());
      }

      response.setContentType("text/json;charset=UTF-8");
      IOUtils.write(status.getBytes(), response.getOutputStream());
   }

   private void adminPmsMgrChange(HttpServletRequest request, HttpServletResponse response) throws IOException {
      LOG.info("adminPmsMgrChange");
      String status = this.successStatus();
      String pms = request.getParameter("PMS");
      String pmstype = request.getParameter("PMS_Type");
      String pmsurl = request.getParameter("PMS_URL");
      String pmskey = request.getParameter("PMS_Key");
      String pmssite = request.getParameter("PMS_Site");
      String currencyPreference = request.getParameter("Currency_Preference");
      String billCurrency = request.getParameter("Bill_Currency");
      String autoWakeUpTv = request.getParameter("auto_wake_up_tv");
      String autoSwitchOffTv = request.getParameter("auto_switch_off_tv");
      String guestname = request.getParameter("Guest_Name");
      String guestlanguage = request.getParameter("Guest_Language");
      String billontv = request.getParameter("Bill_on_TV");
      String welcomemessage = request.getParameter("Welcome_Message");
      String messages = request.getParameter("Messages");
      String expresscheckout = request.getParameter("Express_Checkout");
      String donotDisturb = request.getParameter("Donot_Disturb");
      int alarmRingingVolume = Integer.parseInt(request.getParameter("alarm_ringing_volume"));
      String pmsconnectiontype = request.getParameter("PMS_Connection_type");
      String pmsconnectionversion = request.getParameter("PMS_Connection_version");
      String pmsconnectionstatus = request.getParameter("PMS_Connection_status");
      PmsStatusManager psm = JpaManager.getPmsStatusManager();
      PmsStatus ps = PmsUtils.getPmsStatus();
      if (!ps.getPms().equalsIgnoreCase(pms)) {
         LOG.info("change pms enable to:{}", pms);
      }

      if (!ps.getPmstype().equalsIgnoreCase(pmstype)) {
         LOG.info("change pms type to: {}", pmstype);
      }

      ps.setPms(pms);
      ps.setPmstype(pmstype);
      ps.setPmsurl(pmsurl);
      ps.setPmskey(pmskey);
      ps.setPmssite(pmssite);
      ps.setGuestname(guestname);
      ps.setGuestlanguage(guestlanguage);
      ps.setBillontv(billontv);
      ps.setWelcomemessage(welcomemessage);
      ps.setMessages(messages);
      ps.setExpresscheckout(expresscheckout);
      ps.setDoNotDisturb(donotDisturb);
      ps.setPmsconnectiontype(pmsconnectiontype);
      ps.setPmsconnectionversion(pmsconnectionversion);
      ps.setPmsconnectionstatus(pmsconnectionstatus);
      ps.setCurrency(billCurrency);
      ps.setCurrencyPreference(currencyPreference);
      ps.setAutoWakeUpTv(autoWakeUpTv);
      ps.setAutoSwitchOffTv(autoSwitchOffTv);
      ps.setAlarmRingingVolume(alarmRingingVolume);
      psm.save(ps);
      PmsUtils.updatePMSFeaturesToTV();
      PmsUtils.setPMSEnabled(pms.equalsIgnoreCase("On"));
      this.responseJSON(status, response);
   }

   private void adminPmsUrlChange(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String status = this.successStatus();
      String pmsconfigs = request.getParameter("pmsconfigs");
      String pmstype = request.getParameter("PMS_Type");
      LOG.info("adminPmsUrlChange, pmsType:{},configs:{}", pmstype, pmsconfigs);
      PmsStatusManager psm = JpaManager.getPmsStatusManager();
      JSONObject congfigs = new JSONObject(pmsconfigs);
      PmsStatus ps = PmsUtils.getPmsStatus();
      if (null != ps) {
         if (!pmstype.equalsIgnoreCase(ps.getPmstype())) {
            LOG.info("PMSType changed, clean Pms Data");
            PmsUtils.cleanDatabase();
            ps.setPmstype(pmstype);
         }

         ps.setPmsconfigs(congfigs.toString());
         ps.setPmsconnectionstatus("");
         psm.save(ps);
         PmsUtils.createTmsInstace(pmstype);
      }

      this.responseJSON(status, response);
   }

   private void adminPmsTag(HttpServletResponse response) {
      String status = null;
      PmsStatusManager psm = JpaManager.getPmsStatusManager();
      PmsStatus ps = PmsUtils.getPmsStatus();
      if (StringUtils.isBlank(ps.getPmsconfigs())) {
         ps.setPmstype("None");
         psm.save(ps);
      }

      PmsStatus pmsStatus = JpaManager.getPmsStatusManager().loadByKey(1);
      status = this.successStatus(pmsStatus);
      this.responseJSON(status, response);
   }

   private void getMsgFilter2(HttpServletRequest request, HttpServletResponse response) {
      String msgfilter1 = request.getParameter("msgfilter1");
      ArrayList<String> rs = new ArrayList<>();
      if (!"Guest Name".equals(msgfilter1) && !"Guest Group".equals(msgfilter1) && !"Room Id".equals(msgfilter1)) {
         if ("TV Group".equals(msgfilter1)) {
            GroupsManager gpm = JpaManager.getGroupsManager();
            List<Groups> gp = gpm.loadAll();

            for (int i = 0; i < gp.size(); i++) {
               String temp = gp.get(i).getGroupname();
               if (null != temp && !"".equals(temp) && !rs.contains(temp)) {
                  rs.add(temp);
               }
            }
         } else if ("Reservation Id".equals(msgfilter1)) {
            ReservationManager rvm = JpaManager.getReservationManager();
            List<Reservation> rv = rvm.loadAll();

            for (int i = 0; i < rv.size(); i++) {
               String temp = rv.get(i).getReservationId();
               if (null != temp && !"".equals(temp) && !rs.contains(temp)) {
                  rs.add(temp);
               }
            }
         } else if ("All".equals(msgfilter1)) {
         }
      } else {
         GuestInfoManager gm = JpaManager.getGuestInfoManager();
         List<GuestInfo> gi = gm.findGuestInfosByCheckin("Y");

         for (int i = 0; i < gi.size(); i++) {
            String temp = null;
            if ("Guest Name".equals(msgfilter1)) {
               temp = gi.get(i).getGuestName();
            } else if ("Guest Group".equals(msgfilter1)) {
               temp = gi.get(i).getGroupName();
            } else if ("Room Id".equals(msgfilter1)) {
               temp = String.valueOf(gi.get(i).getRoomid());
            }

            if (null != temp && !"".equals(temp) && !rs.contains(temp)) {
               rs.add(temp);
            }
         }
      }

      rs.sort(new Comparator<String>() {
         public int compare(String o1, String o2) {
            String s1 = o1.toLowerCase();
            String s2 = o2.toLowerCase();
            return s1.compareTo(s2);
         }
      });
      JSONObject jsonObj = new JSONObject();
      jsonObj.put("data", String.join(",", rs));
      Utils.writeToResponse(jsonObj.toString(), "text/html;charset=UTF-8", response);
   }

   private void refreshCurrentRoomInfo(HttpServletRequest request, HttpServletResponse response) {
      String roomid = request.getParameter("roomInfoId");
      TmsUtils tms = PmsUtils.getTmsInstance();
      if (tms != null) {
         tms.requestRefresh(roomid);
      }

      Utils.writeToResponse("", "text/html;charset=UTF-8", response);
   }

   private void getMsgRcpnt(HttpServletRequest request, HttpServletResponse response) {
      String rcpntfilter1 = request.getParameter("rcpntfilter1");
      String rcpntfilter2 = request.getParameter("rcpntfilter2");
      ArrayList<String> rs = new ArrayList<>();
      if (null != rcpntfilter1 && null != rcpntfilter2) {
         if (!"All".equals(rcpntfilter1) && !"Guest Name".equals(rcpntfilter1) && !"Guest Group".equals(rcpntfilter1) && !"Room Id".equals(rcpntfilter1)) {
            if ("TV Group".equals(rcpntfilter1)) {
               for (Devices tv : JpaManager.getDevicesManager().findDevicesByGroupName(rcpntfilter2)) {
                  rs.add(tv.getTvroomid());
               }
            } else if ("Reservation Id".equals(rcpntfilter1)) {
               ReservationManager rvm = JpaManager.getReservationManager();
               List<Reservation> rv = rvm.loadAll();

               for (int m = 0; m < rv.size(); m++) {
                  String temp = rv.get(m).getReservationId();
                  if (null != temp && temp.equals(rcpntfilter2)) {
                     temp = rv.get(m).getRooms();
                     if (null != temp && !"".equals(temp)) {
                        String[] id = temp.split(",");

                        for (int n = 0; n < id.length; n++) {
                           if (!"".equals(id[n]) && !rs.contains(id[n])) {
                              rs.add(id[n]);
                           }
                        }
                     }
                  }
               }
            }
         } else {
            GuestInfoManager gm = JpaManager.getGuestInfoManager();
            List<GuestInfo> gi = gm.loadAll();

            for (int m = 0; m < gi.size(); m++) {
               String room = null;
               String filter = null;
               if ("All".equals(rcpntfilter1)) {
                  filter = rcpntfilter2;
               } else if ("Guest Name".equals(rcpntfilter1)) {
                  filter = gi.get(m).getGuestName();
               } else if ("Guest Group".equals(rcpntfilter1)) {
                  filter = gi.get(m).getGroupName();
               } else if ("Room Id".equals(rcpntfilter1)) {
                  room = rcpntfilter2;
               }

               if (null != filter && filter.equals(rcpntfilter2)) {
                  room = String.valueOf(gi.get(m).getRoomid());
               }

               if (null != room && !"".equals(room)) {
                  String[] id = room.split(",");

                  for (int n = 0; n < id.length; n++) {
                     if (!"".equals(id[n]) && !rs.contains(id[n])) {
                        rs.add(id[n]);
                     }
                  }
               }
            }
         }
      }

      if (rs.size() >= 2) {
         rs.sort(new Comparator<String>() {
            public int compare(String o1, String o2) {
               String s1 = o1.toLowerCase();
               String s2 = o2.toLowerCase();
               return s1.compareTo(s2);
            }
         });
      }

      JSONObject jsonObj = new JSONObject();
      jsonObj.put("data", String.join(",", rs));
      Utils.writeToResponse(jsonObj.toString(), "text/html;charset=UTF-8", response);
   }

   class PartialUpgradeTask implements Runnable {
      AsyncContext asyncContext;

      public PartialUpgradeTask(AsyncContext asyncContext) {
         this.asyncContext = asyncContext;
      }

      @Override
      public void run() {
         String status = "{\"status\":\"fail\"}";
         String cloneName = this.asyncContext.getRequest().getParameter("cloneType");
         String roomid = this.asyncContext.getRequest().getParameter("roomid");
         String value = this.asyncContext.getRequest().getParameter("value");
         String tvid = this.asyncContext.getRequest().getParameter("tvid");

         try {
            String groupName = this.asyncContext.getRequest().getParameter("groupName");
            CommonConstants.CloneItemType cloneType = CloneItemUtils.getCloneItemTypeByName(cloneName);
            int cloneId = TpvStringUtils.tryParseInt(value, 0);
            List<Devices> tvs = null;
            if (null != tvid) {
               Devices tv = JpaManager.getDevicesManager().loadByKey(tvid);
               if (null == tv) {
                  throw new Exception("Device does not exist,tvid=" + tvid);
               }

               tvs = PmsUtils.getCompatibleTVsForRoom(roomid, tvid);
               if (tvs.isEmpty()) {
                  throw new Exception("No TV found in provided room");
               }

               if (IPUpgradeManager.isTVUpgrading(tv) && cloneId != 0) {
                  PMSServlet.LOG.error("Unable to change, TV is in another upgrading!");
               }
            } else {
               if (null == groupName) {
                  throw new Exception("need provide valid tvid or groupname to upgrade");
               }

               tvs = PMSServlet.this.getTVListByGroupName(groupName);
               if (tvs.isEmpty()) {
                  throw new Exception("no TV found in provided group");
               }
            }

            String tvsStr = IPUpgradeManager.getDeviceList(tvid, groupName);
            if (cloneId > 0) {
               String selectCloneType = IPUpgradeManager.convertCloneItemTypeToUpgradeType(cloneType);
               status = IPUpgradeManager.processCloneUpgradeType(selectCloneType, value, tvid == null ? "" : tvid, groupName == null ? "" : groupName, null);
               IPUpgradeManager.assignRFPlayouts(tvsStr, cloneType, cloneId, "PMS");
            }

            IPUpgradeManager.startUpgrades(tvsStr, cloneId > 0 ? "U" : "ST");
         } catch (Exception e) {
            PMSServlet.LOG.error(e.getMessage(), e);
            status = Utils.buildFailReturnJson(e.getMessage());
         }

         this.asyncContext.getResponse().setContentType("text/json;charset=UTF-8");

         try {
            IOUtils.write(status.getBytes(), this.asyncContext.getResponse().getOutputStream());
         } catch (IOException e) {
            PMSServlet.LOG.error(e.getMessage(), e);
         }

         this.asyncContext.complete();
      }
   }
}
