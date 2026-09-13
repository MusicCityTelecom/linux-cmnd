package com.tpvision.smartinstall.api;

import com.tpvision.smartinstall.bean.PmsAlarmStatus;
import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.FutureCheckIn;
import com.tpvision.smartinstall.dao.core.GuestInfo;
import com.tpvision.smartinstall.dao.core.PmsStatus;
import com.tpvision.smartinstall.dao.mgr.GuestInfoManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.pms.PmsUtils;
import com.tpvision.smartinstall.schedule.CmndMetricsTask;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.PmsMessageUtils;
import com.tpvision.smartinstall.util.TpvStringUtils;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pms")
public class PmsController extends ApiBaseController {
   private static final Logger logger = LoggerFactory.getLogger(PmsController.class);

   @PostMapping("/findCheckInList")
   public JSONObject findCheckInList(
      @RequestParam(value = "guestType", defaultValue = "current") String guestType,
      @RequestParam(value = "keyword", defaultValue = "") String keyword,
      int pageNo,
      int pageSize,
      String sortKey,
      String sortDirection
   ) {
      PmsController.GuestType queryType = PmsController.GuestType.fromString(guestType);
      List<String> validSortList;
      if (queryType == PmsController.GuestType.CURRENT) {
         if ("checkinTime".equalsIgnoreCase(sortKey)) {
            sortKey = "arrivalDate";
         }

         validSortList = Arrays.asList("roomid", "guestName", "arrivalDate", "checkoutTime");
      } else {
         validSortList = Arrays.asList("roomid", "guestName", "checkinTime", "checkoutTime");
      }

      Pageable pageable;
      if (!StringUtils.isEmpty(sortDirection) && validSortList.contains(sortKey)) {
         Direction direction = sortDirection.toLowerCase().startsWith("asc") ? Direction.ASC : Direction.DESC;
         Sort sort = Sort.by(direction, sortKey);
         pageable = PageRequest.of(pageNo - 1, pageSize, sort);
      } else {
         pageable = PageRequest.of(pageNo - 1, pageSize);
      }

      JSONObject result = new JSONObject();
      if (queryType == PmsController.GuestType.CURRENT) {
         Page<GuestInfo> guestInfoPage = JpaManager.getGuestInfoManager().findGuestInfoByKeywordAndCheckInStatus(keyword, pageable);
         result.put("total", guestInfoPage.getTotalElements());
         JSONArray dataList = new JSONArray();

         for (GuestInfo info : guestInfoPage.getContent()) {
            JSONObject infoJson = new JSONObject();
            infoJson.put("id", info.getGuestId());
            infoJson.put("checkIn", info.getCheckin());
            infoJson.put("language", info.getGuestLanguage());
            infoJson.put("roomid", info.getRoomid());
            infoJson.put("guestName", info.getGuestName());
            infoJson.put("checkinTime", Optional.ofNullable(info.getCheckinTime()).orElse(""));
            infoJson.put("checkoutTime", Optional.ofNullable(info.getCheckoutTime()).orElse(""));
            dataList.put(infoJson);
         }

         result.put("data", dataList);
      } else if (queryType != PmsController.GuestType.FUTURE && queryType != PmsController.GuestType.INCOMING) {
         String checkoutTime = "";
         if (queryType == PmsController.GuestType.OUTGOING) {
            checkoutTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
         }

         JSONArray jsonArray = JpaManager.getGuestInfoManager().findAllPmsDataFromGuestInfoUnionFutureCheckIn(checkoutTime, keyword, pageable);
         int total = jsonArray.length();
         result.put("total", total);
         JSONArray dataList = new JSONArray();

         for (int i = (int)pageable.getOffset(); i < total && i < pageable.getOffset() + pageable.getPageSize(); i++) {
            dataList.put(jsonArray.get(i));
         }

         result.put("data", dataList);
      } else {
         String checkInTime = "";
         if (queryType == PmsController.GuestType.INCOMING) {
            checkInTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
         }

         Page<FutureCheckIn> futureCheckInPage = JpaManager.getFutureCheckInManager().findFutureCheckInsByKeyword(checkInTime, keyword, pageable);
         result.put("total", futureCheckInPage.getTotalElements());
         JSONArray dataList = new JSONArray();

         for (FutureCheckIn info : futureCheckInPage.getContent()) {
            JSONObject infoJson = new JSONObject();
            infoJson.put("id", String.valueOf(info.getId()));
            infoJson.put("checkIn", "N");
            infoJson.put("roomid", info.getRoomid());
            infoJson.put("guestName", info.getGuestName());
            infoJson.put("language", info.getGuestLanguage());
            infoJson.put("checkinTime", Optional.ofNullable(info.getCheckinTime()).orElse(""));
            infoJson.put("checkoutTime", Optional.ofNullable(info.getCheckoutTime()).orElse(""));
            dataList.put(infoJson);
         }

         result.put("data", dataList);
      }

      if (queryType != PmsController.GuestType.FUTURE && queryType != PmsController.GuestType.INCOMING) {
         this.checkSupportAlarmForData(result.getJSONArray("data"));
      }

      return result;
   }

   private void checkSupportAlarmForData(JSONArray dataArray) {
      Map<String, List<JSONObject>> checkRoomListMap = new HashMap<>();
      int i = 0;

      for (int j = dataArray.length(); i < j; i++) {
         JSONObject info = dataArray.getJSONObject(i);
         if ("Y".equalsIgnoreCase(info.getString("checkIn"))) {
            checkRoomListMap.computeIfAbsent(info.getString("roomid"), key -> new ArrayList<>()).add(info);
         }
      }

      Set<String> checkRoomSet = checkRoomListMap.keySet();
      if (!checkRoomSet.isEmpty()) {
         for (Map<String, Object> roomDevice : JpaManager.getDevicesManager().findDeviceTypeAndFirmwareInfoByRooms(checkRoomSet)) {
            String tVRoomID = (String)roomDevice.get("TVRoomID");
            String type = (String)roomDevice.get("Type");
            String tvFirmwareIdentifier = (String)roomDevice.get("tv_firmware_Identifier");
            if (PlatformUtils.isSupportAlarm(type, tvFirmwareIdentifier)) {
               List<JSONObject> roomJsonList = checkRoomListMap.get(tVRoomID);
               if (roomJsonList != null) {
                  for (JSONObject info : roomJsonList) {
                     info.put("alarm", true);
                  }
               }
            }
         }
      }
   }

   @GetMapping("/roomNos")
   public Object getRoomNos() {
      JSONObject info = new JSONObject();
      List<String> roomIdList = JpaManager.getDevicesManager().loadAll().stream().map(Devices::getTvroomid).distinct().sorted().collect(Collectors.toList());
      info.put("rooms", roomIdList);
      return info;
   }

   @PostMapping("/checkIn")
   public ApiErrorCode checkIn(PmsCheckinVO checkinVO) {
      if (StringUtils.isEmpty(checkinVO.roomNo)) {
         return ApiErrorCode.PMS_CHECK_ROOMNO_EMPTY;
      } else if (JpaManager.getDevicesManager().findDevicesByRoomId(checkinVO.roomNo).isEmpty()) {
         return ApiErrorCode.PMS_NO_SUCH_ROOMS;
      } else if (StringUtils.isEmpty(checkinVO.guestName)) {
         return ApiErrorCode.PMS_GUESTNAME_EMPTY;
      } else if (!this.isValidOutTime(checkinVO.checkoutTime)) {
         return ApiErrorCode.PMS_CHECK_OUT_TIME_INVALID;
      } else if (StringUtils.isEmpty(checkinVO.checkinTime) || !this.isFutureTime(checkinVO.checkinTime)) {
         return this.doCheckInRightNow(checkinVO);
      } else {
         return checkinVO.checkinTime.compareTo(checkinVO.checkoutTime) >= 0
            ? ApiErrorCode.PMS_CHECK_IN_TIME_AFTER_CHECK_OUT_TIME
            : this.doFutureCheckInSave(checkinVO);
      }
   }

   @PostMapping("/checkInFuture")
   public ApiErrorCode checkInFuture(String futureIds) {
      if (StringUtils.isEmpty(futureIds)) {
         return ApiErrorCode.PMS_FUTURE_ID_EMPTY;
      }

      String[] futureIdArray = futureIds.split(",");

      for (String futureId : futureIdArray) {
         int intfutureId = TpvStringUtils.tryParseInt(futureId, -1);
         FutureCheckIn futureCheckIn = JpaManager.getFutureCheckInManager().loadByKey(intfutureId);
         if (futureCheckIn == null) {
            return ApiErrorCode.PMS_FUTURE_CHECK_ID_RECORD_NOT_EXSIT;
         }

         GuestInfo uncheckGuestInfo = this.initUncheckGuestInfo(
            futureCheckIn.getRoomid(), futureCheckIn.getGuestName(), futureCheckIn.getRoomType(), futureCheckIn.getGuestLanguage()
         );
         if (uncheckGuestInfo == null) {
            return ApiErrorCode.PMS_ROOM_ALREADY_CHECKED;
         }

         try {
            PmsUtils.processCheckin(uncheckGuestInfo.getGuestId(), futureCheckIn.getCheckoutTime());
            JpaManager.getFutureCheckInManager().deleteFutureCheckIn(futureCheckIn);
         } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
            return ApiErrorCode.PMS_STATUS_DIABLED;
         }
      }

      return ApiErrorCode.SUCCESS_OK;
   }

   @PostMapping("/updateGuestName")
   public ApiErrorCode updateCheckInGuestName(String guestId, String guestName) {
      if (StringUtils.isEmpty(guestId)) {
         return ApiErrorCode.PMS_GUEST_ID_EMPTY;
      }

      if (StringUtils.isEmpty(guestName)) {
         return ApiErrorCode.PMS_GUESTNAME_EMPTY;
      }

      try {
         PmsUtils.renameGuest(guestId, guestName, null);
      } catch (Exception ex) {
         logger.error(ex.getMessage(), ex);
         return ApiErrorCode.PMS_CHECK_IN_GUEST_MODIFY_NAME_FAILURE;
      }

      return ApiErrorCode.SUCCESS_OK;
   }

   @PostMapping("/updateFutureGuestName")
   public ApiErrorCode updateFutureCheckInGuestName(String futureId, String guestName) {
      if (StringUtils.isEmpty(futureId)) {
         return ApiErrorCode.PMS_FUTURE_ID_EMPTY;
      }

      if (StringUtils.isEmpty(guestName)) {
         return ApiErrorCode.PMS_GUESTNAME_EMPTY;
      }

      int intfutureId = TpvStringUtils.tryParseInt(futureId, -1);
      FutureCheckIn futureCheckIn = JpaManager.getFutureCheckInManager().loadByKey(intfutureId);
      if (futureCheckIn == null) {
         return ApiErrorCode.PMS_FUTURE_CHECK_ID_RECORD_NOT_EXSIT;
      }

      futureCheckIn.setGuestName(guestName);
      JpaManager.getFutureCheckInManager().save(futureCheckIn);
      return ApiErrorCode.SUCCESS_OK;
   }

   @PostMapping("/updateGuestLanguage")
   public ApiErrorCode updateCheckInGuestLanguage(String guestId, String language) {
      if (StringUtils.isEmpty(guestId)) {
         return ApiErrorCode.PMS_GUEST_ID_EMPTY;
      }

      if (StringUtils.isEmpty(language)) {
         return ApiErrorCode.PMS_GUEST_LANGUAGE_EMPTY;
      }

      try {
         GuestInfo guestInfo = PmsUtils.getGuest(guestId);
         PmsUtils.changeLanguage(guestInfo.getRoomid(), language);
      } catch (Exception ex) {
         logger.error(ex.getMessage(), ex);
         return ApiErrorCode.PMS_CHECK_IN_GUEST_MODIFY_LANGUAGE_FAILURE;
      }

      return ApiErrorCode.SUCCESS_OK;
   }

   @PostMapping("/updateFutureGuestLanguage")
   public ApiErrorCode updateFutureCheckInGuestLanguage(String futureId, String language) {
      if (StringUtils.isEmpty(futureId)) {
         return ApiErrorCode.PMS_FUTURE_ID_EMPTY;
      }

      if (StringUtils.isEmpty(language)) {
         return ApiErrorCode.PMS_GUEST_LANGUAGE_EMPTY;
      }

      int intfutureId = TpvStringUtils.tryParseInt(futureId, -1);
      FutureCheckIn futureCheckIn = JpaManager.getFutureCheckInManager().loadByKey(intfutureId);
      if (futureCheckIn == null) {
         return ApiErrorCode.PMS_FUTURE_CHECK_ID_RECORD_NOT_EXSIT;
      }

      futureCheckIn.setGuestLanguage(language);
      JpaManager.getFutureCheckInManager().save(futureCheckIn);
      return ApiErrorCode.SUCCESS_OK;
   }

   @PostMapping("/updateRoomNo")
   public ApiErrorCode updateCheckInGuestRoomNo(String guestId, String roomNo) {
      if (StringUtils.isEmpty(guestId)) {
         return ApiErrorCode.PMS_GUEST_ID_EMPTY;
      }

      if (StringUtils.isEmpty(roomNo)) {
         return ApiErrorCode.PMS_CHECK_ROOMNO_EMPTY;
      }

      if (JpaManager.getDevicesManager().findDevicesByRoomId(roomNo).isEmpty()) {
         return ApiErrorCode.PMS_NO_SUCH_ROOMS;
      }

      GuestInfo guestInfo = JpaManager.getGuestInfoManager().loadByKey(guestId);
      if (guestInfo == null) {
         return ApiErrorCode.PMS_GUEST_NOT_EXISTS;
      }

      if (TpvStringUtils.tryParseInt(guestInfo.getRoomid(), -1) == TpvStringUtils.tryParseInt(roomNo, -1)) {
         return ApiErrorCode.PMS_GUEST_ROOMNO_SAME;
      }

      try {
         if (StringUtils.isBlank(guestInfo.getCheckoutTime())) {
            return ApiErrorCode.PMS_UPDATE_ROOMNO_FAILURE_AS_CHECKED_BY_OTHER_SYSTEM;
         }

         Object alarmStatusReturn = this.getAlarmStatus(guestInfo.getRoomid());
         PmsUtils.changeGuestRoom(guestInfo.getRoomid(), roomNo);
         if (alarmStatusReturn instanceof PmsAlarmStatus) {
            PmsAlarmStatus pmsAlarmStatus = (PmsAlarmStatus)alarmStatusReturn;
            this.updateAlarmTime(roomNo, pmsAlarmStatus.isAlarmEnabled() ? pmsAlarmStatus.getAlarmTime() : "");
         }

         return ApiErrorCode.SUCCESS_OK;
      } catch (Exception ex) {
         logger.error(ex.getMessage(), ex);
         return ApiErrorCode.PMS_UPDATE_CHECK_IN_ROOMNO_FAILURE;
      }
   }

   @PostMapping("/updateFutureRoomNo")
   public ApiErrorCode updateFutureCheckInRoomNo(String futureId, String roomNo) {
      if (StringUtils.isEmpty(futureId)) {
         return ApiErrorCode.PMS_FUTURE_ID_EMPTY;
      }

      if (StringUtils.isEmpty(roomNo)) {
         return ApiErrorCode.PMS_CHECK_ROOMNO_EMPTY;
      }

      if (JpaManager.getDevicesManager().findDevicesByRoomId(roomNo).isEmpty()) {
         return ApiErrorCode.PMS_NO_SUCH_ROOMS;
      }

      int intfutureId = TpvStringUtils.tryParseInt(futureId, -1);
      FutureCheckIn futureCheckIn = JpaManager.getFutureCheckInManager().loadByKey(intfutureId);
      if (futureCheckIn == null) {
         return ApiErrorCode.PMS_FUTURE_CHECK_ID_RECORD_NOT_EXSIT;
      }

      if (TpvStringUtils.tryParseInt(futureCheckIn.getRoomid(), -1) == TpvStringUtils.tryParseInt(roomNo, -1)) {
         return ApiErrorCode.PMS_GUEST_ROOMNO_SAME;
      }

      ApiErrorCode checkResult = this.checkCheckInOutTimeValidate(roomNo, futureCheckIn.getCheckinTime(), futureCheckIn.getCheckoutTime(), -1);
      if (checkResult != null) {
         return checkResult;
      }

      futureCheckIn.setRoomid(roomNo);
      JpaManager.getFutureCheckInManager().save(futureCheckIn);
      return ApiErrorCode.SUCCESS_OK;
   }

   @PostMapping("/removeFutureCheckIn")
   public ApiErrorCode removeFutureCheckIn(String futureId) {
      if (StringUtils.isEmpty(futureId)) {
         return ApiErrorCode.PMS_FUTURE_ID_EMPTY;
      }

      int intfutureId = TpvStringUtils.tryParseInt(futureId, -1);
      FutureCheckIn futureCheckIn = JpaManager.getFutureCheckInManager().loadByKey(intfutureId);
      if (futureCheckIn != null) {
         JpaManager.getFutureCheckInManager().deleteFutureCheckIn(futureCheckIn);
      }

      return ApiErrorCode.SUCCESS_OK;
   }

   @PostMapping("/checkOut")
   public ApiErrorCode checkOut(String roomNos) {
      if (StringUtils.isEmpty(roomNos)) {
         return ApiErrorCode.PMS_CHECK_ROOMNO_EMPTY;
      }

      try {
         String[] roomArray = roomNos.split(",");
         CmndMetricsTask.setPmsTypeName(this.getRecpetionInfo());

         for (String roomNo : roomArray) {
            PmsUtils.processCheckout(roomNo);
         }

         CmndMetricsTask.resetPmsTypeName();
      } catch (IOException ex) {
         CmndMetricsTask.resetPmsTypeName();
         return this.formatCheckFailureError(ex);
      }

      return ApiErrorCode.SUCCESS_OK;
   }

   @PostMapping("/updateFutureCheckInTime")
   public ApiErrorCode updateFutureCheckInTime(String futureId, String checkinTime) {
      if (StringUtils.isEmpty(futureId)) {
         return ApiErrorCode.PMS_FUTURE_ID_EMPTY;
      }

      int intfutureId = TpvStringUtils.tryParseInt(futureId, -1);
      FutureCheckIn futureCheckIn = JpaManager.getFutureCheckInManager().loadByKey(intfutureId);
      if (futureCheckIn == null) {
         return ApiErrorCode.PMS_FUTURE_CHECK_ID_RECORD_NOT_EXSIT;
      }

      if (StringUtils.isEmpty(checkinTime)) {
         return ApiErrorCode.PMS_CHECK_IN_TIME_INVALID;
      }

      if (checkinTime.compareTo(futureCheckIn.getCheckoutTime()) >= 0) {
         return ApiErrorCode.PMS_CHECK_IN_TIME_AFTER_CHECK_OUT_TIME;
      }

      ApiErrorCode checkResult = this.checkCheckInOutTimeValidate(futureCheckIn.getRoomid(), checkinTime, futureCheckIn.getCheckoutTime(), intfutureId);
      if (checkResult != null) {
         return checkResult;
      }

      try {
         futureCheckIn.setCheckinTime(checkinTime);
         JpaManager.getFutureCheckInManager().save(futureCheckIn);
      } catch (Exception ex) {
         logger.error(ex.getMessage(), ex);
         return ApiErrorCode.PMS_CHECKIN_TIME_UPDATE_FAILURE;
      }

      return ApiErrorCode.SUCCESS_OK;
   }

   @PostMapping("/updateFutureCheckOutTime")
   public ApiErrorCode updateFutureCheckOutTime(String futureId, String checkoutTime) {
      if (StringUtils.isEmpty(futureId)) {
         return ApiErrorCode.PMS_FUTURE_ID_EMPTY;
      }

      int intfutureId = TpvStringUtils.tryParseInt(futureId, -1);
      FutureCheckIn futureCheckIn = JpaManager.getFutureCheckInManager().loadByKey(intfutureId);
      if (futureCheckIn == null) {
         return ApiErrorCode.PMS_FUTURE_CHECK_ID_RECORD_NOT_EXSIT;
      }

      if (StringUtils.isEmpty(checkoutTime)) {
         return ApiErrorCode.PMS_CHECKOUT_TIME_EMPTY;
      }

      if (!this.isValidOutTime(checkoutTime)) {
         return ApiErrorCode.PMS_CHECK_OUT_TIME_INVALID;
      }

      if (checkoutTime.compareTo(futureCheckIn.getCheckinTime()) <= 0) {
         return ApiErrorCode.PMS_CHECK_IN_TIME_AFTER_CHECK_OUT_TIME;
      }

      ApiErrorCode checkResult = this.checkCheckInOutTimeValidate(futureCheckIn.getRoomid(), futureCheckIn.getCheckinTime(), checkoutTime, intfutureId);
      if (checkResult != null) {
         return checkResult;
      }

      try {
         futureCheckIn.setCheckoutTime(checkoutTime);
         JpaManager.getFutureCheckInManager().save(futureCheckIn);
      } catch (Exception ex) {
         logger.error(ex.getMessage(), ex);
         return ApiErrorCode.PMS_CHECKOUT_TIME_UPDATE_FAILURE;
      }

      return ApiErrorCode.SUCCESS_OK;
   }

   @PostMapping("/updateCheckOutTime")
   public ApiErrorCode updateCheckOutTime(String guestId, String checkoutTime) {
      if (StringUtils.isEmpty(guestId)) {
         return ApiErrorCode.PMS_GUEST_ID_EMPTY;
      }

      GuestInfoManager guestInfoManager = JpaManager.getGuestInfoManager();
      GuestInfo guestInfo = guestInfoManager.loadByKey(guestId);
      if (guestInfo == null) {
         return ApiErrorCode.PMS_GUEST_NOT_EXISTS;
      }

      ApiErrorCode checkResult = this.checkGuestCheckOutTime(checkoutTime, guestInfo.getRoomid());
      if (checkResult != null) {
         return checkResult;
      }

      try {
         guestInfo.setCheckoutTime(checkoutTime);
         guestInfoManager.save(guestInfo);
      } catch (Exception ex) {
         logger.error(ex.getMessage(), ex);
         return ApiErrorCode.PMS_CHECKOUT_TIME_UPDATE_FAILURE;
      }

      return ApiErrorCode.SUCCESS_OK;
   }

   @GetMapping("/getMessageIcons")
   public JSONArray getMessageIcons() {
      return PmsMessageUtils.loadPmsIconArray();
   }

   @GetMapping("/showMessageIcon")
   public void showMessageIcon(String name, HttpServletResponse response) {
      PmsMessageUtils.renderIcon(name, response);
   }

   @PostMapping("/uploadMessageIcon")
   public Object uploadMessageIcon(HttpServletRequest request, HttpServletResponse response) {
      try {
         return new JSONObject().put("filename", PmsMessageUtils.doMessageIconUpload(request));
      } catch (Exception ex) {
         logger.error(ex.getMessage(), ex);
         return ApiErrorCode.PMS_MESSAGE_ICON_UPLOAD_FAILURE;
      }
   }

   @PostMapping("/deleteMessageIcon")
   public void deleteMessageIcon(HttpServletRequest request, HttpServletResponse response) {
      String iconName = request.getParameter("iconName");
      PmsMessageUtils.deleteMessageIcon(iconName);
   }

   @PostMapping("/sendMessage")
   public ApiErrorCode sendMessage(String rommNos, String title, String icon, String message) {
      CmndMetricsTask.setPmsTypeName(this.getRecpetionInfo());
      PmsMessageUtils.createMessage(rommNos, title, message, icon, "Now");
      CmndMetricsTask.resetPmsTypeName();
      return ApiErrorCode.SUCCESS_OK;
   }

   @GetMapping("/getPmsSetting")
   public JSONObject getPmsSetting() {
      JSONObject info = new JSONObject();
      PmsStatus pmsStatus = JpaManager.getPmsStatusManager().loadByKey(1);
      String defaultMessageIcon = pmsStatus.getMessageIcon();
      if (StringUtils.isNoneBlank(defaultMessageIcon)) {
         info.put("message_icon", "files/image/pms/" + defaultMessageIcon);
      }

      String defaultBillIcon = pmsStatus.getBillIcon();
      if (StringUtils.isNoneBlank(defaultBillIcon)) {
         info.put("bill_icon", "files/image/pms/" + defaultBillIcon);
      }

      return info;
   }

   @PostMapping("/uploadDefaultMessageIcon")
   public Object uploadPmsDefaultMessageIcon(HttpServletRequest request, HttpServletResponse response) {
      try {
         return new JSONObject().put("iconPath", "files/image/pms/" + PmsMessageUtils.doDefaultMessageIconUpload(request));
      } catch (Exception ex) {
         logger.error(ex.getMessage(), ex);
         return ApiErrorCode.PMS_DEFAULT_MESSAGE_ICON_UPLOAD_FAILURE;
      }
   }

   @PostMapping("/uploadDefaultBillIcon")
   public Object uploadPmsDefaultBillIcon(HttpServletRequest request, HttpServletResponse response) {
      try {
         return new JSONObject().put("iconPath", "files/image/pms/" + PmsMessageUtils.doDefaultBillIconUpload(request));
      } catch (Exception ex) {
         logger.error(ex.getMessage(), ex);
         return ApiErrorCode.PMS_DEFAULT_BILL_ICON_UPLOAD_FAILURE;
      }
   }

   @PostMapping("/getAlarm")
   public Object getAlarmStatus(String roomNo) {
      List<Devices> devices = null;

      try {
         devices = this.findCheckInDeviceForRoom(roomNo);
      } catch (ApiErrorException ex) {
         return ex.getApiErrorCode();
      }

      for (Devices tv : devices) {
         PmsAlarmStatus pmsAlarmStatus = PmsUtils.getPmsAlarmStatusForDevice(tv);
         if (pmsAlarmStatus != null) {
            return pmsAlarmStatus;
         }
      }

      return ApiErrorCode.PMS_ALARM_STATUS_QUERY_FAILURE;
   }

   @PostMapping("/updateAlarm")
   public ApiErrorCode updateAlarmTime(String roomNo, String alarmTime) {
      boolean isDiableAlarm = StringUtils.isBlank(alarmTime);
      if (!isDiableAlarm) {
         Date time = this.parseAlarmTimeToData(alarmTime);
         if (time == null) {
            return ApiErrorCode.PMS_ALARM_TIME_FORMAT_ERROR;
         }

         alarmTime = new SimpleDateFormat("HH:mm:ss").format(time);
      }

      List<Devices> devices = null;

      try {
         devices = this.findCheckInDeviceForRoom(roomNo);
      } catch (ApiErrorException ex) {
         return ex.getApiErrorCode();
      }

      for (Devices tv : devices) {
         boolean isSuccessUpdateTime = false;
         CmndMetricsTask.setPmsTypeName(this.getRecpetionInfo());
         if (isDiableAlarm) {
            isSuccessUpdateTime = PmsUtils.disablePmsAlarm(tv);
         } else {
            isSuccessUpdateTime = PmsUtils.updatePmsAlarmTime(tv, alarmTime);
         }

         CmndMetricsTask.resetPmsTypeName();
         if (!isSuccessUpdateTime) {
            return ApiErrorCode.PMS_ALARM_TIME_SET_FAILURE;
         }
      }

      return ApiErrorCode.SUCCESS_OK;
   }

   private List<Devices> findCheckInDeviceForRoom(String roomNo) {
      if (StringUtils.isEmpty(roomNo)) {
         throw new ApiErrorException(ApiErrorCode.PMS_CHECK_ROOMNO_EMPTY);
      }

      for (GuestInfo info : JpaManager.getGuestInfoManager().findGuestInfosByRoomid(String.valueOf(roomNo))) {
         if (!"Y".equalsIgnoreCase(info.getCheckin())) {
            throw new ApiErrorException(ApiErrorCode.PMS_ROOM_NOT_CHECKED);
         }
      }

      List<Devices> roomDevices = JpaManager.getDevicesManager().findDevicesByRoomId(roomNo);
      if (roomDevices.isEmpty()) {
         throw new ApiErrorException(ApiErrorCode.PMS_NO_SUCH_ROOMS);
      } else {
         return roomDevices;
      }
   }

   private Date parseAlarmTimeToData(String alarmTime) {
      try {
         return new SimpleDateFormat("HH:mm").parse(alarmTime);
      } catch (Exception var4) {
         try {
            return new SimpleDateFormat("HH:mm:ss").parse(alarmTime);
         } catch (Exception var3) {
            return null;
         }
      }
   }

   private boolean isFutureTime(String checkInTime) {
      String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
      return checkInTime.compareTo(currentTime) > 0;
   }

   private ApiErrorCode doFutureCheckInSave(PmsCheckinVO checkinVO) {
      ApiErrorCode checkResult = this.checkCheckInOutTimeValidate(checkinVO.roomNo, checkinVO.checkinTime, checkinVO.checkoutTime, -1);
      if (checkResult != null) {
         return checkResult;
      }

      FutureCheckIn futureCheckIn = new FutureCheckIn();
      futureCheckIn.setRoomid(checkinVO.roomNo);
      futureCheckIn.setGuestName(checkinVO.guestName);
      futureCheckIn.setCheckinTime(checkinVO.checkinTime);
      futureCheckIn.setCheckoutTime(checkinVO.checkoutTime);
      futureCheckIn.setRoomType(checkinVO.roomType);
      futureCheckIn.setGuestLanguage(checkinVO.guestLanguage);
      JpaManager.getFutureCheckInManager().save(futureCheckIn);
      List<Devices> tvs = PmsUtils.getTVsForRoom(checkinVO.roomNo);
      CmndMetricsTask.setPmsTypeName(this.getRecpetionInfo());

      for (Devices tv : tvs) {
         CmndMetricsTask.writePMSInfoToMetricsLog(tv, "future_checkin");
      }

      CmndMetricsTask.resetPmsTypeName();
      return ApiErrorCode.SUCCESS_OK;
   }

   private ApiErrorCode checkCheckInOutTimeValidate(String roomNo, String checkinTime, String checkoutTime, int excludeFutureId) {
      for (GuestInfo info : JpaManager.getGuestInfoManager().findGuestInfosByRoomid(String.valueOf(roomNo))) {
         if ("Y".equalsIgnoreCase(info.getCheckin()) && (StringUtils.isEmpty(info.getCheckoutTime()) || info.getCheckoutTime().compareTo(checkinTime) > 0)) {
            return ApiErrorCode.PMS_FUTURE_CHECK_TIME_CONFILIT_WITH_CURRENT_CHECK_IN_RECORD;
         }
      }

      for (FutureCheckIn futureCheckIn2 : JpaManager.getFutureCheckInManager().findFutureCheckInListByRoomId(roomNo)) {
         if (excludeFutureId != futureCheckIn2.getId()
            && this.isTwoTimeIntervalOverLap(checkinTime, checkoutTime, futureCheckIn2.getCheckinTime(), futureCheckIn2.getCheckoutTime())) {
            return ApiErrorCode.PMS_FUTURE_CHECK_TIME_CONFILIT_WITH_OTHER_FUTURE_RECORD;
         }
      }

      return null;
   }

   private boolean isTwoTimeIntervalOverLap(String beginTime1, String endTime1, String beginTime2, String endTime2) {
      boolean isOverLap = true;
      if (endTime1.compareTo(beginTime2) <= 0 || beginTime1.compareTo(endTime2) >= 0) {
         isOverLap = false;
      }

      return isOverLap;
   }

   private ApiErrorCode doCheckInRightNow(PmsCheckinVO checkinVO) {
      ApiErrorCode checkResult = this.checkGuestCheckOutTime(checkinVO.checkoutTime, checkinVO.roomNo);
      if (checkResult != null) {
         return checkResult;
      }

      GuestInfo uncheckedGuestInfo = this.initUncheckGuestInfo(checkinVO.roomNo, checkinVO.guestName, checkinVO.roomType, checkinVO.guestLanguage);
      if (uncheckedGuestInfo == null) {
         return ApiErrorCode.PMS_ROOM_ALREADY_CHECKED;
      }

      try {
         CmndMetricsTask.setPmsTypeName(this.getRecpetionInfo());
         PmsUtils.processCheckin(uncheckedGuestInfo.getGuestId(), checkinVO.checkoutTime);
         CmndMetricsTask.resetPmsTypeName();
      } catch (IOException ex) {
         CmndMetricsTask.resetPmsTypeName();
         return this.formatCheckFailureError(ex);
      }

      return ApiErrorCode.SUCCESS_OK;
   }

   private GuestInfo initUncheckGuestInfo(String roomNo, String guestName, String roomType, String guestLanguage) {
      List<GuestInfo> guestInfoList = JpaManager.getGuestInfoManager().findGuestInfosByRoomid(roomNo);
      GuestInfo uncheckedGuestInfo = null;

      for (GuestInfo info : guestInfoList) {
         if ("Y".equalsIgnoreCase(info.getCheckin())) {
            return null;
         }

         if (StringUtils.equalsIgnoreCase(info.getGuestName(), guestName)) {
            uncheckedGuestInfo = info;
         }
      }

      if (uncheckedGuestInfo == null) {
         uncheckedGuestInfo = PmsUtils.createGuest(guestName, null, roomNo, roomType, guestLanguage);
      }

      return uncheckedGuestInfo;
   }

   private boolean isValidOutTime(String checkTime) {
      SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

      try {
         Date checkInDate = spf.parse(checkTime);
         return checkInDate.getTime() > System.currentTimeMillis();
      } catch (Exception ex) {
         return false;
      }
   }

   private ApiErrorCode checkGuestCheckOutTime(String checkoutTime, String roomNo) {
      if (StringUtils.isEmpty(checkoutTime)) {
         return ApiErrorCode.PMS_CHECKOUT_TIME_EMPTY;
      }

      if (!this.isValidOutTime(checkoutTime)) {
         return ApiErrorCode.PMS_CHECK_OUT_TIME_INVALID;
      }

      for (FutureCheckIn futureCheckIn2 : JpaManager.getFutureCheckInManager().findFutureCheckInListByRoomId(roomNo)) {
         if (checkoutTime.compareTo(futureCheckIn2.getCheckinTime()) > 0) {
            return ApiErrorCode.PMS_GUEST_CHECK_OUT_TIME_CONFILIT_WITH_FUTURE_CHECK_RECORD;
         }
      }

      return null;
   }

   private ApiErrorCode formatCheckFailureError(Exception ex) {
      logger.error(ex.getMessage(), ex);
      if (ex.getMessage().contains("disabled")) {
         return ApiErrorCode.PMS_STATUS_DIABLED;
      } else if (ex.getMessage().contains("timed out")) {
         return ApiErrorCode.PMS_CHECK_DEVICE_OFFLINE;
      } else {
         return ex.getMessage().contains("no device found!") ? ApiErrorCode.PMS_CHECK_IN_ONLY_RF_DEVICES : ApiErrorCode.PMS_CHECK_UNKONWN_FAILURE;
      }
   }

   private enum GuestType {
      CURRENT,
      FUTURE,
      OUTGOING,
      INCOMING,
      ALL;

      public static PmsController.GuestType fromString(String label) {
         if (label.equalsIgnoreCase(CURRENT.toString())) {
            return CURRENT;
         } else if (label.equalsIgnoreCase(FUTURE.toString())) {
            return FUTURE;
         } else if (label.equalsIgnoreCase(OUTGOING.toString())) {
            return OUTGOING;
         } else {
            return label.equalsIgnoreCase(INCOMING.toString()) ? INCOMING : ALL;
         }
      }
   }
}
