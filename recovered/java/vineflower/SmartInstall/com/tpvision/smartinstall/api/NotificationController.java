package com.tpvision.smartinstall.api;

import com.tpvision.smartinstall.dao.core.RoomNotification;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.util.TpvDateUtils;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
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
@RequestMapping("/api/notification")
public class NotificationController {
   @GetMapping("/getUnreadWarningCount")
   public JSONObject getUnreadWarningOrErrorCount() {
      int count = (int)JpaManager.getRoomNotificationManager().findCountByLevelAndReadStatus(1, 0);
      JSONObject object = new JSONObject();
      object.put("count", count);
      return object;
   }

   @PostMapping("/getUnreadWarnings")
   public JSONArray getUnreadWarnings(String type) {
      List<RoomNotification> roomNotifications = JpaManager.getRoomNotificationManager().findNotificationList(1, 0, RoomNotification.EventType.valueOf(type));
      JSONArray resultList = new JSONArray();

      for (RoomNotification roomNotification : roomNotifications) {
         JSONObject object = new JSONObject();
         object.put("id", roomNotification.getId());
         object.put("room", roomNotification.getRoom());
         object.put("alarmTime", roomNotification.getEventTime());
         object.put("message", roomNotification.getMessage());
         resultList.put(object);
      }

      return resultList;
   }

   @PostMapping("/deleteAll")
   public ApiErrorCode deleteAllNotification() {
      JpaManager.getRoomNotificationManager().deleteAll();
      return ApiErrorCode.SUCCESS_OK;
   }

   @PostMapping("/markAllRead")
   public ApiErrorCode markNoticationAsRead() {
      JpaManager.getRoomNotificationManager().markAllAsRead();
      return ApiErrorCode.SUCCESS_OK;
   }

   @PostMapping("/delete")
   public ApiErrorCode deleteOneNotification(int id) {
      JpaManager.getRoomNotificationManager().deleteById(id);
      return ApiErrorCode.SUCCESS_OK;
   }

   @PostMapping("/markRead")
   public ApiErrorCode markOneNotificationAsRead(int id) {
      RoomNotification roomNotification = JpaManager.getRoomNotificationManager().queryById(id);
      if (roomNotification == null) {
         return ApiErrorCode.NOTIFICATION_NOT_FOUND;
      }

      if (roomNotification.getReadStatus() == 0) {
         roomNotification.setReadStatus(1);
         roomNotification.setReadTime(TpvDateUtils.formatLocalDate(new Date(), "yyyy-MM-dd HH:mm"));
         JpaManager.getRoomNotificationManager().save(roomNotification);
      }

      return ApiErrorCode.SUCCESS_OK;
   }

   @PostMapping("/findList")
   public JSONObject findNotificationList(
      @RequestParam(value = "eventLevel", defaultValue = "-1") int eventLevel,
      @RequestParam(value = "keyword", defaultValue = "") String keyword,
      int pageNo,
      int pageSize,
      String sortKey,
      String sortDirection
   ) {
      List<String> validSortList = Arrays.asList("room", "message", "eventTime");
      Pageable pageable;
      if (!StringUtils.isEmpty(sortDirection) && validSortList.contains(sortKey)) {
         Direction direction = sortDirection.toLowerCase().startsWith("asc") ? Direction.ASC : Direction.DESC;
         Sort sort = Sort.by(direction, sortKey);
         pageable = PageRequest.of(pageNo - 1, pageSize, sort);
      } else {
         Sort sort = Sort.by(Direction.DESC, "id");
         pageable = PageRequest.of(pageNo - 1, pageSize, sort);
      }

      Page<RoomNotification> roomNotificationPage = JpaManager.getRoomNotificationManager().findByEventLevelAndKeyword(eventLevel, keyword, pageable);
      JSONObject result = new JSONObject();
      result.put("total", roomNotificationPage.getTotalElements());
      JSONArray dataList = new JSONArray();

      for (RoomNotification roomNotification : roomNotificationPage.getContent()) {
         JSONObject infoJson = new JSONObject();
         infoJson.put("id", roomNotification.getId());
         infoJson.put("room", roomNotification.getRoom());
         infoJson.put("eventTime", roomNotification.getEventTime());
         infoJson.put("eventLevel", roomNotification.getEventLevel());
         infoJson.put("message", roomNotification.getMessage());
         infoJson.put("readStatus", roomNotification.getReadStatus());
         dataList.put(infoJson);
      }

      result.put("data", dataList);
      return result;
   }
}
