package com.tpvision.smartinstall.api;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.SIConfig;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.gateway.GatewayManager;
import com.tpvision.smartinstall.gateway.PlayoutUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reception")
public class ReceptionController {
   private static final Logger LOG = LoggerFactory.getLogger(ReceptionController.class);

   @GetMapping("/config")
   public Map<String, Object> config() {
      SIConfig siconfig = JpaManager.getSIConfigManager().getSIConfig();
      Map<String, Object> resultData = new HashMap<>();
      resultData.put("siName", Optional.ofNullable(siconfig.getSiName()).orElse(""));
      resultData.put("siAddress", Optional.ofNullable(siconfig.getSiAddress()).orElse(""));
      resultData.put("siSupport", Optional.ofNullable(siconfig.getSiSupport()).orElse(""));
      resultData.put("futureCheckIn", StringUtils.equalsIgnoreCase(siconfig.getFutureCheckIn(), Boolean.TRUE.toString()));
      resultData.put("checkoutTime", Optional.ofNullable(siconfig.getCheckoutTime()).orElse("00:00"));
      resultData.put("supportLanguage", Optional.ofNullable(siconfig.getSupportLanguage()).orElse(""));
      resultData.put("defaultLanguage", Optional.ofNullable(siconfig.getDefaultLanguage()).orElse(""));
      resultData.put("supportRoomtype", StringUtils.equalsIgnoreCase(siconfig.getSupportRoomtype(), Boolean.TRUE.toString()));
      resultData.put("roomType", Optional.ofNullable(siconfig.getRoomType()).orElse(""));
      return resultData;
   }

   @GetMapping("/license")
   public JSONObject license() {
      JSONObject result = new JSONObject();
      result.put("features", ApiLicenseChecker.getInstance().getSupportedLicenseFeatures());
      return result;
   }

   @PostMapping("/startPlay")
   public ApiErrorCode startPlayForRFRooms(String rooms) {
      boolean isRequirePlayoutStarted = false;

      for (String roomNo : rooms.split(",")) {
         for (Devices tv : JpaManager.getDevicesManager().findDevicesByRoomId(roomNo)) {
            if (tv.isRFDevice()) {
               isRequirePlayoutStarted = true;
               break;
            }
         }

         if (isRequirePlayoutStarted) {
            break;
         }
      }

      if (!isRequirePlayoutStarted) {
         return ApiErrorCode.PLAYOUT_NO_RF_ROOMS;
      }

      GatewayManager.GatewayState status = this.formatGatewayState();
      if (status == GatewayManager.GatewayState.PLAYING) {
         return ApiErrorCode.PLAYOUT_WORKING;
      }

      if (status == GatewayManager.GatewayState.STARTING) {
         return ApiErrorCode.PLAYOUT_STARTING;
      }

      try {
         PlayoutUtils.getInstance().setPlayoutStopped(false);
         PlayoutUtils.triggerStartPlayout();
         return ApiErrorCode.SUCCESS_OK;
      } catch (Exception e) {
         LOG.error("start play failure", e);
         return ApiErrorCode.PLAYOUT_DEKTECCARD_CHECK_FAILURE;
      }
   }

   @GetMapping("/playStatus")
   public JSONObject getPlayStatus() {
      JSONObject result = new JSONObject();
      result.put("status", this.formatGatewayState().toString());
      return result;
   }

   private GatewayManager.GatewayState formatGatewayState() {
      GatewayManager.GatewayState status = GatewayManager.GatewayState.ERROR;
      if (!PlayoutUtils.getInstance().isPlayoutStopped()) {
         if (GatewayManager.getInstance().isPlaying()) {
            status = GatewayManager.GatewayState.PLAYING;
         } else if (GatewayManager.getInstance().getState() == GatewayManager.GatewayState.STARTING
            || GatewayManager.getInstance().getState() == GatewayManager.GatewayState.INITIATING
            || GatewayManager.getInstance().getState() == GatewayManager.GatewayState.IDLE) {
            status = GatewayManager.GatewayState.STARTING;
         }
      }

      return status;
   }
}
