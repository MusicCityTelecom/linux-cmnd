package com.tpvision.smartinstall.api;

import com.tpvision.smartinstall.core.SettingCreator;
import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.MyChoiceTemplate;
import com.tpvision.smartinstall.dao.core.PincodeHistory;
import com.tpvision.smartinstall.dao.core.PlayoutInfo;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.PlayoutInfoManager;
import com.tpvision.smartinstall.gateway.PlayoutUtils;
import com.tpvision.smartinstall.japit.EnablerServiceCmd;
import com.tpvision.smartinstall.japit.JapitCommand;
import com.tpvision.smartinstall.japit.MyChoiceServiceCmd;
import com.tpvision.smartinstall.soap.RemoteWebServiceManager;
import com.tpvision.smartinstall.soap.mychoice.ArrayOfInteger;
import com.tpvision.smartinstall.soap.mychoice.ArrayOfString;
import com.tpvision.smartinstall.soap.mychoice.MonthlyCreditUsageList;
import com.tpvision.smartinstall.soap.mychoice.MonthlyCreditUsageRecord;
import com.tpvision.smartinstall.soap.mychoice.MyChoiceWebService;
import com.tpvision.smartinstall.util.CloneItemUtils;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.JAPITUtils;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.TpvStringUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MyChoiceController extends ApiBaseController {
   private static final Logger LOG = LoggerFactory.getLogger(MyChoiceController.class);
   private static final String PLAYOUT_SOURCE = "API";
   private static final ConcurrentMapCache SimpleCache = new ConcurrentMapCache("RoomsCache");
   private static final String CACHE_KEY_ROOMS_TAG = MyChoiceController.class.getCanonicalName() + ".cache.key.rooms";
   private static final String CACHE_KEY_ROOMS_EXPIRED_DATE_TAG = CACHE_KEY_ROOMS_TAG + ".expire.date";
   private static final int CACHE_EXPIRE_MINUTES = 10;
   private static final String PACKAGE_NAME_PREFIX = "Package";

   @GetMapping("/api/mychoice/rooms")
   public Object getRooms() {
      try {
         return this.getRoomsInfoJsonWithCache();
      } catch (Exception ex) {
         LOG.error(ex.getMessage(), ex);
         return this.formatSoapError(ex.getMessage());
      }
   }

   @PostMapping("/api/mychoice/roomInfo")
   public Object getNumberPackage(String room) {
      String mychoiceApikey = JpaManager.getSIConfigManager().getSIConfig().getMychoiceApikey();
      if (StringUtils.isEmpty(mychoiceApikey)) {
         return ApiErrorCode.MYCHOICE_API_KEY_NOT_SET;
      }

      try {
         JSONObject result = new JSONObject();
         MyChoiceWebService mychoiceService = RemoteWebServiceManager.getMyChoiceWebService();
         BigInteger numOfPackage = mychoiceService.getNumPackages(mychoiceApikey, room);
         result.put("package_num", numOfPackage);
         ArrayOfInteger days = mychoiceService.getDurationsForRoom(mychoiceApikey, room);
         result.put("days", days.getIntegerList());
         boolean tvStatus = false;
         List<Devices> devices = JpaManager.getDevicesManager().findDevicesByRoomId(room);
         if (devices.size() == 1) {
            Devices tv = devices.get(0);
            if (tv.isRFDevice()) {
               tvStatus = !JpaManager.getPincodeHistoryManager().findValidPincdeHistoryForRoom(room).isEmpty();
            } else {
               tvStatus = tv.isOnline() && this.isMychoiceStatusActive(tv);
            }
         }

         result.put("mychoice_status", tvStatus);
         return result;
      } catch (Exception ex) {
         LOG.error(ex.getMessage(), ex);
         return this.formatSoapError(ex.getMessage());
      }
   }

   @PostMapping("/api/mychoice/activate")
   public Object activateMyChoice(String room, BigInteger days, @RequestParam("package") BigInteger iPackage) {
      if (!StringUtils.isNumeric(room)) {
         return ApiErrorCode.MYCHOICE_ONLY_SUPPORT_DIGIT_ROOM_NO;
      } else {
         Object activateResult = this.activateMyChoicePackageByRoomAndDaysAndPackage(room, days, iPackage);
         if (activateResult instanceof PincodeHistory) {
            this.writePMSInfoToMetricsLog(JpaManager.getDevicesManager().findDevicesByRoomId(room).get(0), "mychoice_pkg" + iPackage + "_activation");
            PincodeHistory pincodeHistory = (PincodeHistory)activateResult;
            return new JSONObject()
               .put("roomId", pincodeHistory.getRoomId())
               .put("duration", pincodeHistory.getDays())
               .put("pinCode", pincodeHistory.getPincode());
         } else {
            return activateResult;
         }
      }
   }

   @PostMapping("/api/mychoice/deactivate")
   public Object deactivateMyChoice(String room) {
      if (!StringUtils.isNumeric(room)) {
         return ApiErrorCode.MYCHOICE_ONLY_SUPPORT_DIGIT_ROOM_NO;
      }

      List<Devices> devices = JpaManager.getDevicesManager().findDevicesByRoomId(room);
      if (devices.size() != 1) {
         return ApiErrorCode.MYCHOICE_DEVICES_DATA_EMPTY_ERROR;
      }

      Devices device = devices.get(0);
      JSONArray myChoiceParametersArray = new JSONArray();
      JSONObject endPackage1Parameter = new JSONObject();
      endPackage1Parameter.put("Action", "StopMyChoice");
      endPackage1Parameter.put("MyChoicePackage", "Package1");
      myChoiceParametersArray.put(endPackage1Parameter);
      JSONObject endPackage2Parameter = new JSONObject();
      endPackage2Parameter.put("Action", "StopMyChoice");
      endPackage2Parameter.put("MyChoicePackage", "Package2");
      myChoiceParametersArray.put(endPackage2Parameter);
      ApiErrorCode deactivateResult = this.doRfOrIpUpgrade(device, room, myChoiceParametersArray);
      if (deactivateResult == ApiErrorCode.SUCCESS_OK) {
         JpaManager.getPincodeHistoryManager().deactivateAllNotExpiredPincodeForRoom(room);
         this.writePMSInfoToMetricsLog(device, "mychoice_deactivation");
      }

      return deactivateResult;
   }

   @PostMapping("/api/mychoice/deactivateByHistory")
   public Object deactivateMyChoicePincodeByHistory(int id) {
      PincodeHistory history = JpaManager.getPincodeHistoryManager().findById(id);
      if (history == null) {
         return ApiErrorCode.MYCHOICE_PINCODE_HISOTRY_NOT_EXIST;
      } else if (history.getDeactivationStatus() == 1) {
         return ApiErrorCode.MYCHOICE_PINCODE_ALREADY_DEACTIVED;
      } else {
         return TpvDateUtils.parseDateString(history.getStopTime(), "dd/MM/yyyy HH:mm:ss").before(new Date())
            ? ApiErrorCode.MYCHOICE_PINCODE_ALREADY_EXPIRED
            : this.deactivateMyChoiceByRoomAndPackageName(history.getRoomId(), history.getPackageName());
      }
   }

   @GetMapping("/api/mychoice/history")
   public JSONArray getLatestHistory() {
      List<PincodeHistory> pincodeHistorys = JpaManager.getPincodeHistoryManager().findLatestCountPincodeHistory(10);
      JSONArray result = new JSONArray();

      for (PincodeHistory history : pincodeHistorys) {
         result.put(this.formatHistoryToJsonObject(history));
      }

      return result;
   }

   @PostMapping("/api/mychoice/historyPage")
   public JSONObject findPincodeHistoryData(
      @RequestParam(value = "type", defaultValue = "all") String type,
      @RequestParam(value = "keyword", defaultValue = "") String keyword,
      @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
   ) {
      JSONObject result = new JSONObject();
      Page<PincodeHistory> pincodeHistorys = JpaManager.getPincodeHistoryManager()
         .findPincodeHistoryByKeyword(MyChoiceController.HistoryType.fromString(type), keyword, pageNo, pageSize);
      result.put("total", pincodeHistorys.getTotalElements());
      JSONArray data = new JSONArray();

      for (PincodeHistory history : pincodeHistorys) {
         data.put(this.formatHistoryToJsonObject(history));
      }

      result.put("data", data);
      return result;
   }

   @GetMapping("/api/mychoice/credit")
   public Object getCreditUsage() {
      String mychoiceApikey = JpaManager.getSIConfigManager().getSIConfig().getMychoiceApikey();
      if (StringUtils.isEmpty(mychoiceApikey)) {
         return ApiErrorCode.MYCHOICE_API_KEY_NOT_SET;
      }

      MonthlyCreditUsageList monthlyCreditUsageList = RemoteWebServiceManager.getMyChoiceWebService().getMonthlyCreditUsage(mychoiceApikey);
      JSONArray result = new JSONArray();

      for (MonthlyCreditUsageRecord record : monthlyCreditUsageList.getMonthlyCreditUsageRecord()) {
         result.put(new JSONObject(record));
      }

      return result;
   }

   @PostMapping("/api/mychoice/print")
   public Object printHistoryData(int id) {
      PincodeHistory history = JpaManager.getPincodeHistoryManager().findById(id);
      if (history == null) {
         return ApiErrorCode.MYCHOICE_PINCODE_HISOTRY_NOT_EXIST;
      }

      MyChoiceTemplate template = JpaManager.getMyChoiceTemplateManager().getMyChoiceTemplate();
      JSONObject result = new JSONObject();
      result.put("logo", this.formatReturnValue(template.getLogo(), "ReceptionLogo.png"));
      result.put("above_pin_code", this.formatReturnValue(template.getAbovePinCode(), "Your PIN is:"));
      result.put("pincode", history.getPincode());
      result.put("below_pin_code", this.formatReturnValue(template.getBelowPinCode(), "This PIN code is valid for:"));
      result.put("days", history.getDays());
      result.put("room_number", this.formatReturnValue(template.getRoomNumber(), "in room"));
      result.put("room_id", history.getRoomId());
      result.put("validity_notice", this.formatReturnValue(template.getValidityNotice(), "This PIN code is valid for one month after issuing"));
      result.put(
         "instructions",
         this.formatReturnValue(
            template.getInstructions(),
            "How to activate your TV:\r\nstep1: Press the left upper(MyChoice) button on the remote control\r\nstep2: Enter the received PIN code in the PIN field"
         )
      );
      return result;
   }

   @GetMapping("/api/mychoice/showTemplateIcon")
   public void showMessageIcon(String name, HttpServletResponse response) {
      String iconPath = CommonConstants.RECEPTION_LOGO_IMG_LOCATION;
      String path = iconPath + name;
      if (StringUtils.isEmpty(name) || !new File(path).exists()) {
         path = CommonConstants.servletContextPath + "/static/images/upload_normal.png";
      }

      response.setContentType(new MimetypesFileTypeMap().getContentType(path));

      try (
         ServletOutputStream outStream = response.getOutputStream();
         FileInputStream fis = new FileInputStream(path);
      ) {
         byte[] data = new byte[1000];

         while (fis.read(data) > 0) {
            outStream.write(data);
         }

         outStream.write(data);
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }
   }

   @GetMapping("/exapi/mychoice/{room_id}/packages")
   public Object getActiveMyChoicePackages(@PathVariable("room_id") String roomId) {
      JSONArray result = new JSONArray();

      for (PincodeHistory history : JpaManager.getPincodeHistoryManager().findValidPincdeHistoryForRoom(roomId)) {
         JSONObject pincode = new JSONObject();
         pincode.put("room_id", history.getRoomId());
         pincode.put("package_number", Integer.parseInt(history.getPackageName().replaceFirst("Package", "")));
         pincode.put("duration", history.getDays() >= 1000 ? history.getDays() - 1000 : history.getDays() * 24);
         pincode.put("pin_code", history.getPincode());
         pincode.put("create_time", history.getCreateTime());
         result.put(pincode);
      }

      return result;
   }

   @PostMapping("/exapi/mychoice/{room_id}/packages/{package_number}")
   public Object activateMyChoicePackage(
      @PathVariable("room_id") String roomId, @PathVariable("package_number") BigInteger iPackage, @RequestBody String postString
   ) {
      LOG.info("activateMyChoicePackage postString = {}", postString);
      if (!TpvStringUtils.isJSONString(postString)) {
         return ApiErrorCode.MYCHOICE_DURATION_PARAMETER_ERROR;
      } else if (!Arrays.asList(1, 2).contains(iPackage.intValue())) {
         return ApiErrorCode.MYCHOICE_PACKAGE_PARAMETER_ERROR;
      } else {
         JSONObject durationParameter = new JSONObject(postString);
         int duration = durationParameter.optInt("duration", -1);
         if (!Arrays.asList(1, 3, 24, 72, 168, 336, 504, 672).contains(duration)) {
            return ApiErrorCode.MYCHOICE_DURATION_PARAMETER_ERROR;
         } else {
            int days = duration >= 24 ? duration / 24 : duration + 1000;
            Object activateResult = this.activateMyChoicePackageByRoomAndDaysAndPackage(roomId, BigInteger.valueOf(days), iPackage);
            if (activateResult instanceof PincodeHistory) {
               PincodeHistory pincodeHistory = (PincodeHistory)activateResult;
               return new JSONObject()
                  .put("room_id", pincodeHistory.getRoomId())
                  .put("package_number", iPackage)
                  .put("duration", duration)
                  .put("pin_code", pincodeHistory.getPincode())
                  .put("create_time", pincodeHistory.getCreateTime());
            } else {
               return activateResult;
            }
         }
      }
   }

   @DeleteMapping("/exapi/mychoice/{room_id}/packages/{package_number}")
   public Object deactivateMyChoicePackage(@PathVariable("room_id") String roomId, @PathVariable("package_number") BigInteger iPackage) {
      return !Arrays.asList(1, 2).contains(iPackage.intValue())
         ? ApiErrorCode.MYCHOICE_PACKAGE_PARAMETER_ERROR
         : this.deactivateMyChoiceByRoomAndPackageName(roomId, "Package" + iPackage);
   }

   private Object getRoomsInfoJsonWithCache() {
      ValueWrapper infoWrapper = SimpleCache.get(CACHE_KEY_ROOMS_TAG);
      if (infoWrapper != null) {
         ValueWrapper expireWrapper = SimpleCache.get(CACHE_KEY_ROOMS_EXPIRED_DATE_TAG);
         if (expireWrapper != null && expireWrapper.get() != null && ((LocalTime)expireWrapper.get()).isAfter(LocalTime.now())) {
            return infoWrapper.get();
         }
      }

      String mychoiceApikey = JpaManager.getSIConfigManager().getSIConfig().getMychoiceApikey();
      if (StringUtils.isEmpty(mychoiceApikey)) {
         return ApiErrorCode.MYCHOICE_API_KEY_NOT_SET;
      }

      JSONObject info = new JSONObject();
      ArrayOfString rooms = RemoteWebServiceManager.getMyChoiceWebService().getRooms(mychoiceApikey);
      info.put("rooms", rooms.getStringList());
      String rateType = RemoteWebServiceManager.getMyChoiceWebService().getRateType(mychoiceApikey);
      info.put("show_credit", StringUtils.equalsIgnoreCase(rateType, "bundle"));
      SimpleCache.put(CACHE_KEY_ROOMS_TAG, info);
      SimpleCache.put(CACHE_KEY_ROOMS_EXPIRED_DATE_TAG, LocalTime.now().plusMinutes(10L));
      return info;
   }

   private String formatReturnValue(String value, String defaultValue) {
      return StringUtils.isEmpty(value) ? defaultValue : value;
   }

   private ApiErrorCode doRfOrIpUpgrade(Devices device, String room, JSONArray myChoiceParametersArray) {
      return device.isRFDevice() ? this.doMychoicePlayout(room, myChoiceParametersArray, device) : this.doMyChoiceJapitChange(myChoiceParametersArray, device);
   }

   private boolean isMychoiceStatusActive(Devices device) {
      if (!JAPITUtils.isJapitListening(device)) {
         return false;
      }

      String mychoiceInfo = this.getCurrentMychoiceJSONStringForIpDevices(device);
      return !StringUtils.isEmpty(mychoiceInfo) && mychoiceInfo.contains("Active");
   }

   private String getCurrentMychoiceJSONStringForIpDevices(Devices device) {
      MyChoiceServiceCmd cmd = new MyChoiceServiceCmd(device.getTvuniqueid(), JapitCommand.CommandType.Request, null);

      try {
         String result = JAPITUtils.sendJapitCommand(device, cmd.generateCommand());
         if (result.contains("FunCausedError")) {
            this.enableMychoiceService(device);
            result = JAPITUtils.sendJapitCommand(device, cmd.generateCommand());
         }

         return result;
      } catch (Exception ex) {
         LOG.error(ex.getMessage(), ex);
         return null;
      }
   }

   private void enableMychoiceService(Devices device) throws Exception {
      EnablerServiceCmd enablerServiceCmd = new EnablerServiceCmd(JapitCommand.CommandType.Change, JapitCommand.CommandSvc.WebListeningServices);
      enablerServiceCmd.setMychoiceService(true);
      JAPITUtils.sendJapitCommand(device, enablerServiceCmd.generateCommand());
   }

   private ApiErrorCode doMyChoiceJapitChange(JSONArray myChoiceParameterArray, Devices device) {
      if (!JAPITUtils.isJapitListening(device)) {
         return ApiErrorCode.MYCHOICE_IP_UPGRADE_DEVICES_OFFLINE_ERROR;
      }

      MyChoiceServiceCmd cmd = new MyChoiceServiceCmd(device.getTvuniqueid(), JapitCommand.CommandType.Change, myChoiceParameterArray);

      try {
         String result = JAPITUtils.sendJapitCommand(device, cmd.generateCommand());
         if (result.contains("FunCausedError")) {
            this.enableMychoiceService(device);
            result = JAPITUtils.sendJapitCommand(device, cmd.generateCommand());
         }

         return result.toLowerCase().contains("error") ? ApiErrorCode.MYCHOICE_IP_UPGRADE_ERROR : ApiErrorCode.SUCCESS_OK;
      } catch (Exception ex) {
         LOG.error(ex.getMessage(), ex);
         return ApiErrorCode.MYCHOICE_IP_UPGRADE_ERROR;
      }
   }

   private ApiErrorCode doMychoicePlayout(String room, JSONArray myChoiceParameterArray, Devices device) {
      PlayoutInfoManager playoutInfoManager = JpaManager.getPlayoutInfoManager();
      List<PlayoutInfo> oldPlayInfos = playoutInfoManager.findPlayoutInfoByTypeAndPlatformAndSource(
         CommonConstants.CloneItemType.MyChoice.name(), device.getType(), "API"
      );
      Set<String> allRoomIds = new HashSet<>();

      for (PlayoutInfo info : oldPlayInfos) {
         allRoomIds.addAll(Arrays.asList(info.getRooms().split(",")).stream().map(TpvStringUtils::removeHeadZeorForIntegerString).collect(Collectors.toList()));
      }

      String fixRoomValue = TpvStringUtils.removeHeadZeorForIntegerString(room);
      allRoomIds.add(fixRoomValue);
      List<String> roomIdList = Arrays.asList(allRoomIds.toArray(new String[0]));
      Collections.sort(roomIdList);
      String newRoomIds = StringUtils.join(roomIdList, ",");
      String platformId = PlatformUtils.getPlatformId(device.getType());
      CloneItemUtils.CloneItemInfo cloneItemInfo = new CloneItemUtils.CloneItemInfo();
      cloneItemInfo.setName("myChoice");
      cloneItemInfo.setClone(false);
      cloneItemInfo.setId(0);
      cloneItemInfo.setItemType(CommonConstants.CloneItemType.MyChoice);
      cloneItemInfo.setPlatform(platformId);
      cloneItemInfo.setVersion(TpvDateUtils.getCurrentIndentifierFormatTime());
      cloneItemInfo.setLastUpdated(new Date());
      PlayoutInfo savedPlayoutInfo = PlayoutUtils.savePlayout(cloneItemInfo, newRoomIds, "API");
      if (myChoiceParameterArray.length() == 1) {
         String targetPackageName = myChoiceParameterArray.toString().contains("Package1") ? "Package2" : "Package1";
         List<PincodeHistory> pincodeHistoryList = JpaManager.getPincodeHistoryManager().findValidPincdeHistoryForRfDevice(room, targetPackageName);
         if (!pincodeHistoryList.isEmpty()) {
            PincodeHistory combinedPincodeHistory = pincodeHistoryList.get(0);
            JSONObject formerStartParameter = new JSONObject();
            formerStartParameter.put("Action", "StartMyChoice");
            formerStartParameter.put("MyChoicePIN", combinedPincodeHistory.getPincode());
            formerStartParameter.put("MyChoicePackage", combinedPincodeHistory.getPackageName());
            String[] startTimeArray = combinedPincodeHistory.getCreateTime().split(" ");
            formerStartParameter.put("StartDate", startTimeArray[0]);
            formerStartParameter.put("StartTime", startTimeArray[1]);
            String[] stopTimeArray = combinedPincodeHistory.getStopTime().split(" ");
            formerStartParameter.put("StopDate", stopTimeArray[0]);
            formerStartParameter.put("StopTime", stopTimeArray[1]);
            if (targetPackageName.equalsIgnoreCase("Package1")) {
               JSONArray newArray = new JSONArray();
               newArray.put(formerStartParameter);
               newArray.put(myChoiceParameterArray.get(0));
               myChoiceParameterArray = newArray;
            } else {
               myChoiceParameterArray.put(formerStartParameter);
            }
         }
      }

      String createLocation = PlayoutUtils.getRFZipFullPath(savedPlayoutInfo);
      SettingCreator.createMyChoiceRFClonePackage(createLocation, platformId, room, myChoiceParameterArray, oldPlayInfos);

      for (PlayoutInfo info : oldPlayInfos) {
         PlayoutUtils.deletePlayout(info.getPlayoutId(), false);
      }

      try {
         PlayoutUtils.getInstance().onPlayoutChanged();
         return ApiErrorCode.SUCCESS_OK;
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         return ApiErrorCode.MYCHOICE_RP_PLAYOUT_ERROR;
      }
   }

   private Object getPinCode(String room, BigInteger days, BigInteger iPackage) {
      String mychoiceApikey = JpaManager.getSIConfigManager().getSIConfig().getMychoiceApikey();
      if (StringUtils.isEmpty(mychoiceApikey)) {
         return ApiErrorCode.MYCHOICE_API_KEY_NOT_SET;
      }

      try {
         return RemoteWebServiceManager.getMyChoiceWebService().getPin(mychoiceApikey, room, days, iPackage);
      } catch (Exception ex) {
         LOG.error(ex.getMessage(), ex);
         return this.formatSoapError(ex.getMessage());
      }
   }

   private ApiErrorCode formatSoapError(String errorMsg) {
      if (StringUtils.contains(errorMsg, "Unknown room")) {
         return ApiErrorCode.MYCHOICE_UNKNOW_ROOM_FOR_PACKAGE;
      } else {
         return StringUtils.contains(errorMsg, "Authentication failed") ? ApiErrorCode.MYCHOICE_API_KEY_ERROR : ApiErrorCode.MYCHOICE_BAD_MYCHOICE_REQUEST;
      }
   }

   private JSONObject formatHistoryToJsonObject(PincodeHistory history) {
      JSONObject obj = new JSONObject();
      obj.put("id", history.getId());
      obj.put("room_id", history.getRoomId());
      obj.put("create_time", history.getCreateTime());
      obj.put("package", history.getPackageName());
      obj.put("days", history.getDays());
      obj.put("pincode", history.getPincode());
      long remainSecs = 0L;
      if (history.getSuccess() == 1 && history.getDeactivationStatus() == 0) {
         Date stopTime = TpvDateUtils.parseDateString(history.getStopTime(), "dd/MM/yyyy HH:mm:ss");
         if (stopTime != null && stopTime.after(new Date())) {
            remainSecs = (stopTime.getTime() - System.currentTimeMillis()) / 1000L;
         }
      }

      obj.put("remain_seconds", remainSecs);
      return obj;
   }

   private ApiErrorCode deactivateMyChoiceByRoomAndPackageName(String room, String packageName) {
      List<Devices> devices = JpaManager.getDevicesManager().findDevicesByRoomId(room);
      if (devices.size() != 1) {
         return ApiErrorCode.MYCHOICE_DEVICES_DATA_EMPTY_ERROR;
      }

      Devices device = devices.get(0);
      JSONArray myChoiceParametersArray = new JSONArray();
      JSONObject endPackage1Parameter = new JSONObject();
      endPackage1Parameter.put("Action", "StopMyChoice");
      endPackage1Parameter.put("MyChoicePackage", packageName);
      myChoiceParametersArray.put(endPackage1Parameter);
      ApiErrorCode deactivateResult = this.doRfOrIpUpgrade(device, room, myChoiceParametersArray);
      if (deactivateResult == ApiErrorCode.SUCCESS_OK) {
         JpaManager.getPincodeHistoryManager().deactivatePackageNotExpiredPincodeForRoom(room, packageName);
      }

      return deactivateResult;
   }

   private Object activateMyChoicePackageByRoomAndDaysAndPackage(String room, BigInteger days, BigInteger iPackage) {
      List<Devices> devices = JpaManager.getDevicesManager().findDevicesByRoomId(room);
      if (devices.size() != 1) {
         return ApiErrorCode.MYCHOICE_DEVICES_DATA_EMPTY_ERROR;
      }

      Devices device = devices.get(0);
      Object result = this.getPinCode(room, days, iPackage);
      if (result instanceof ApiErrorCode) {
         return result;
      }

      String pinCode = (String)result;
      String packageName = "Package" + iPackage;
      JSONArray myChoiceParametersArray = new JSONArray();
      JSONObject startParameter = new JSONObject();
      startParameter.put("Action", "StartMyChoice");
      startParameter.put("MyChoicePIN", pinCode);
      startParameter.put("MyChoicePackage", packageName);
      LocalDateTime now = LocalDateTime.now();
      startParameter.put("StartDate", now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
      startParameter.put("StartTime", now.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
      LocalDateTime stopTime;
      if (days.intValue() > 1000) {
         int hours = days.intValue() - 1000;
         stopTime = now.plusHours(hours);
      } else {
         stopTime = now.plusDays(days.intValue());
      }

      startParameter.put("StopDate", stopTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
      startParameter.put("StopTime", stopTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
      myChoiceParametersArray.put(startParameter);
      ApiErrorCode upgradeResult = this.doRfOrIpUpgrade(device, room, myChoiceParametersArray);
      if (upgradeResult == ApiErrorCode.SUCCESS_OK) {
         JpaManager.getPincodeHistoryManager().deactivatePackageNotExpiredPincodeForRoom(room, packageName);
      }

      PincodeHistory pincodeHistory = new PincodeHistory();
      pincodeHistory.setPincode(pinCode);
      pincodeHistory.setCreateTime(now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
      pincodeHistory.setRoomId(room);
      pincodeHistory.setDays(days.intValue());
      pincodeHistory.setStopTime(stopTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
      pincodeHistory.setPackageName(packageName);
      pincodeHistory.setResult(upgradeResult.toString());
      pincodeHistory.setSuccess(upgradeResult == ApiErrorCode.SUCCESS_OK ? 1 : 0);
      pincodeHistory.setDeactivationStatus(0);
      JpaManager.getPincodeHistoryManager().savePincodeHistory(pincodeHistory);
      return upgradeResult == ApiErrorCode.SUCCESS_OK ? pincodeHistory : upgradeResult;
   }

   public enum HistoryType {
      ALL,
      ACTIVE;

      public static MyChoiceController.HistoryType fromString(String label) {
         return label.equalsIgnoreCase(ACTIVE.toString()) ? ACTIVE : ALL;
      }
   }
}
