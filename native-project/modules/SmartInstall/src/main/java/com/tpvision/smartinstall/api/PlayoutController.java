package com.tpvision.smartinstall.api;

import com.tpvision.smartinstall.dao.core.PlayoutInfo;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.gateway.GatewayManager;
import com.tpvision.smartinstall.gateway.PlayoutUtils;
import com.tpvision.smartinstall.roomcheck.RoomCheckResult;
import com.tpvision.smartinstall.roomcheck.RoomCheckUtil;
import com.tpvision.smartinstall.servlet.LastRFConfig;
import com.tpvision.smartinstall.util.CloneItemUtils;
import com.tpvision.smartinstall.util.IPUpgradeManager;
import com.tpvision.smartinstall.util.TpvStringUtils;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exapi/")
public class PlayoutController {
   private static final Logger LOG = LoggerFactory.getLogger(PlayoutController.class);

   @GetMapping("/playouts")
   public Object list(@RequestParam(value = "room", required = false) String roomStr) {
      if (StringUtils.isNotBlank(roomStr) && !StringUtils.isNumeric(roomStr)) {
         return ApiErrorCode.PLAYOUT_QUERY_ROOM_PARA_ERROR;
      }

      List<PlayoutInfo> playoutInfoList = JpaManager.getPlayoutInfoManager().loadAll();
      if (StringUtils.isNotBlank(roomStr)) {
         int roomId = TpvStringUtils.tryParseInt(roomStr, -1);
         playoutInfoList = playoutInfoList.stream().filter(playoutx -> {
            String rooms = playoutx.getRooms().toLowerCase();
            if (Arrays.asList("00000", "all").contains(rooms)) {
               return true;
            }

            for (String roomItem : Arrays.asList(rooms.split(","))) {
               if (roomItem.contains("-")) {
                  String[] roomsRange = roomItem.split("-");
                  int lowerRoom = TpvStringUtils.tryParseInt(roomsRange[0], -1);
                  int higherRoom = TpvStringUtils.tryParseInt(roomsRange[1], -1);
                  if (roomId >= lowerRoom && roomId <= higherRoom) {
                     return true;
                  }
               } else if (TpvStringUtils.tryParseInt(roomItem, -1) == roomId) {
                  return true;
               }
            }

            return false;
         }).collect(Collectors.toList());
      }

      JSONArray result = new JSONArray();

      for (PlayoutInfo playout : playoutInfoList) {
         result.put(this.formatPlayoutInfo(playout));
      }

      return result;
   }

   @GetMapping("/playouts/{id}")
   public Object getPlayoutById(@PathVariable("id") int id) {
      PlayoutInfo playout = JpaManager.getPlayoutInfoManager().loadByKey(id);
      return playout == null ? ApiErrorCode.PLAYOUT_QUERY_PLAYOUT_NOT_EXIST : this.formatPlayoutInfo(playout);
   }

   @PostMapping("/playouts")
   public Object addPlayout(@RequestBody String postString) {
      LOG.info("postString = {}", postString);
      if (!TpvStringUtils.isJSONString(postString)) {
         return ApiErrorCode.PLAYOUT_ADD_PARAMETER_ERROR;
      }

      JSONObject playoutParameter = new JSONObject(postString);
      String packageType = playoutParameter.optString("package_type");
      String rooms = playoutParameter.optString("room_filter");
      if (ApiCloneConfig.isValidPackageTypeIgnoreCase(packageType) && !StringUtils.isBlank(rooms)) {
         packageType = ApiCloneConfig.fixPackageTypeCase(packageType);
         String cloneName = playoutParameter.optString("name");
         int id = IPUpgradeManager.getCloneIdByCloneTypeAndName(packageType, cloneName);
         if (id <= 0) {
            return ApiErrorCode.PLAYOUT_ADD_CLONE_DATA_NOT_EXIST;
         }

         String platformId;
         if ("Content".equalsIgnoreCase(packageType)) {
            platformId = "TPM181HE_CloneData";
         } else {
            platformId = IPUpgradeManager.getPlatformByID(packageType, id, null, null);
         }

         if (platformId == null) {
            return ApiErrorCode.PLAYOUT_PLAY_DATA_ERROR;
         }

         if (!PlayoutUtils.checkPlayoutCompatible(platformId, rooms)) {
            return ApiErrorCode.PLAYOUT_PLATFORM_NOT_COMPATIBLE_ERROR;
         }

         LastRFConfig lastRFConfig = LastRFConfig.loadLastConfig();
         String output = lastRFConfig.outputConfig.output;
         if (output.equalsIgnoreCase("RF") && !GatewayManager.getInstance().isDektecCardInstalled()) {
            return ApiErrorCode.PLAYOUT_DEKTECK_CHECK_FAILURE;
         }

         try {
            String playType = CloneItemUtils.getCloneItemTypeByName(packageType).name();
            RoomCheckResult checkResult = RoomCheckUtil.checkOverWrite(playType, rooms, String.valueOf(id), false);
            if (checkResult.getCurrentAllPlayoutInfoList().size()
                  - checkResult.getFullOverlapRemoveRequiredPlayoutInfoList().size()
                  + checkResult.getWillAddCount()
               > 42L) {
               return ApiErrorCode.PLAYOUT_REACH_MAX_SUPPORT_COUNT;
            }

            PlayoutUtils.addToPlayoutList(String.valueOf(id), playType, rooms, null);
            return ApiErrorCode.SUCCESS_OK;
         } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return ApiErrorCode.PLAYOUT_ADD_FAILURE;
         }
      } else {
         return ApiErrorCode.PLAYOUT_ADD_PARAMETER_ERROR;
      }
   }

   @DeleteMapping("/playouts/{id}")
   public ApiErrorCode deletePlayoutsById(@PathVariable("id") Integer id) {
      return this.doPlayoutDelete(id);
   }

   @DeleteMapping("/playouts")
   public ApiErrorCode deleteAllPlayouts() {
      return this.doPlayoutDelete(null);
   }

   private ApiErrorCode doPlayoutDelete(Integer id) {
      if (id != null) {
         PlayoutInfo playout = JpaManager.getPlayoutInfoManager().loadByKey(id);
         if (playout != null) {
            PlayoutUtils.deletePlayout(playout.getPlayoutId(), true);
         }
      } else {
         List<String> playoutIds = JpaManager.getPlayoutInfoManager()
            .loadAll()
            .stream()
            .map(p -> String.valueOf(p.getPlayoutId()))
            .collect(Collectors.toList());
         if (!playoutIds.isEmpty()) {
            PlayoutUtils.deletePlayouts(StringUtils.join(playoutIds, ","));
         }
      }

      return ApiErrorCode.SUCCESS_OK;
   }

   private JSONObject formatPlayoutInfo(PlayoutInfo playout) {
      JSONObject obj = new JSONObject();
      obj.put("id", playout.getPlayoutId());
      obj.put("room_filter", playout.getRooms());
      obj.put("name", playout.getName());
      obj.put("platform", TpvStringUtils.removeSpaceFromTvPlatform(playout.getPlatform()));
      obj.put("clone_item_type", playout.getType());
      obj.put("version", playout.getVersion());
      return obj;
   }
}
