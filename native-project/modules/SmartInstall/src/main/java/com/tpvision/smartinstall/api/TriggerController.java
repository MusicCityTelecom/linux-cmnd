package com.tpvision.smartinstall.api;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.TriggerInfo;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.servlet.TriggerServlet;
import com.tpvision.smartinstall.trigger.TriggerUtils;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exapi/")
public class TriggerController {
   private static final Logger LOG = LoggerFactory.getLogger(TriggerController.class);

   @GetMapping("/triggers")
   public Object list(@RequestParam(value = "type", required = false) String type) {
      TriggerServlet.TriggerType triggerType = null;
      if (StringUtils.isNoneBlank(type)) {
         triggerType = this.convertToTriggerType(type);
         if (triggerType == null) {
            return ApiErrorCode.TRIGGER_TYPE_ERROR;
         }
      }

      List<TriggerInfo> resultTriggerList = null;
      if (triggerType != null) {
         resultTriggerList = JpaManager.getTriggerInfoManager().findByTriggerType(triggerType.name());
      } else {
         resultTriggerList = JpaManager.getTriggerInfoManager().loadAll();
      }

      JSONArray result = new JSONArray();

      for (TriggerInfo triggerInfo : resultTriggerList) {
         JSONObject triggerObject = new JSONObject();
         triggerObject.put("id", triggerInfo.getId());
         triggerObject.put("name", triggerInfo.getName());
         triggerObject.put("type", TriggerServlet.TriggerType.valueOf(triggerInfo.getTriggerType()).getDisplayName());
         triggerObject.put("enabled", StringUtils.equalsIgnoreCase(triggerInfo.getTriggerActive(), TriggerInfo.TRIGGER_ACTIVE_YES));
         result.put(triggerObject);
      }

      return result;
   }

   @PutMapping("/triggers/{id}/launch")
   public Object launchTrigger(@PathVariable("id") String id, @RequestBody(required = false) String tvids) {
      LOG.info("trigger id = {} , tvids = {}", id, tvids);
      if (StringUtils.isEmpty(id) && !StringUtils.isNumeric(id)) {
         return ApiErrorCode.LAUNCH_TRIGGER_ID_EMPTY;
      }

      TriggerInfo triggerInfo = JpaManager.getTriggerInfoManager().loadByKey(Integer.parseInt(id));
      if (triggerInfo == null) {
         return ApiErrorCode.LAUNCH_TRIGGER_NOT_EXIST;
      }

      if (StringUtils.equals(triggerInfo.getTriggerActive(), TriggerInfo.TRIGGER_ACTIVE_NO)) {
         return ApiErrorCode.LAUNCH_TRIGGER_IN_ACTIVE;
      }

      if (!StringUtils.equals(triggerInfo.getTriggerType(), TriggerServlet.TriggerType.Manual.name())) {
         return ApiErrorCode.LAUNCH_TRIGGER_TYPE_NOT_MANUAL;
      }

      String targetTVIds = null;
      if (StringUtils.isNoneBlank(tvids)) {
         JSONObject idJson = new JSONObject(tvids);
         JSONArray idArray = idJson.optJSONArray("tvids");
         targetTVIds = String.join(",", idArray.toList().toArray(new String[0]));
      } else {
         targetTVIds = JpaManager.getDevicesManager()
            .findDevicesByType(triggerInfo.getTarget())
            .stream()
            .filter(e -> !e.isRFDevice())
            .map(Devices::getTvuniqueid)
            .collect(Collectors.joining(","));
      }

      if (StringUtils.isEmpty(targetTVIds)) {
         return ApiErrorCode.LAUNCH_TRIGGER_INVALID_TVIDS;
      }

      TriggerUtils.executeTriggerThread(triggerInfo, targetTVIds, null);
      JSONObject result = new JSONObject();
      result.put("id", triggerInfo.getId());
      result.put("name", triggerInfo.getName());
      result.put("type", TriggerServlet.TriggerType.valueOf(triggerInfo.getTriggerType()).getDisplayName());
      result.put("enabled", StringUtils.equalsIgnoreCase(triggerInfo.getTriggerActive(), TriggerInfo.TRIGGER_ACTIVE_YES));
      result.put("tvids", targetTVIds.split(","));
      return result;
   }

   private TriggerServlet.TriggerType convertToTriggerType(String type) {
      for (TriggerServlet.TriggerType t : TriggerServlet.TriggerType.values()) {
         if (t != TriggerServlet.TriggerType.Trigger && StringUtils.equals(t.getDisplayName(), type)) {
            return t;
         }
      }

      return null;
   }
}
