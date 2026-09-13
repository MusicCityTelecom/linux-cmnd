package com.tpvision.smartinstall.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tpvision.smartinstall.InQueryParameter;
import com.tpvision.smartinstall.SearchParam;
import com.tpvision.smartinstall.dao.core.BootgridSetting;
import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.mgr.BootgridSettingManager;
import com.tpvision.smartinstall.dao.mgr.DevicesManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.pms.PmsUtils;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.TpvStringUtils;
import com.tpvision.smartinstall.util.Utils;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/dev")
public class DeviceServlet extends BaseHttpServlet {
   private static final long serialVersionUID = 1L;
   private static final Logger LOG = LoggerFactory.getLogger(DeviceServlet.class);
   private static int cloneClickedCounter = -1;
   private static int swClickedCounter = -1;

   @Override
   protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String type = request.getParameter("type");
      String userName = Utils.getAuthenticationName();
      String tvColumns = "";
      String groupColumns = "";
      if ("index".equals(type)) {
         this.showDeviceList(request, response, userName, tvColumns, groupColumns);
      } else if ("addRFTVs".equalsIgnoreCase(type)) {
         this.addRFTvs(request, response);
      } else if ("getCurrentSwCloneClickedCounter".equals(type)) {
         this.getCurrentSwCloneClickedCounter(response);
      } else if ("InvisibleColumns".equals(type)) {
         this.invisibleColumns(request, response, userName);
      } else if ("group".equals(type)) {
         JSONObject data = this.getGroupsData(request);
         Utils.writeToResponse(data.toString(), "text/html;charset=UTF-8", response);
      } else if ("device".equals(type)) {
         this.queryDeviceData(request, response);
      } else if ("incomingTvAdrChange".equals(type)) {
         this.incomingTvAdrChange(request, response);
      }
   }

   private void incomingTvAdrChange(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String incomingTvAdr = request.getParameter("incomingTvAdr");
      String init = request.getParameter("init");
      if ("yes".equalsIgnoreCase(init)) {
         JSONObject data = new JSONObject();
         data.put("incomingTvAdr", Utils.getProductPropties().getProperty("incomingTvAdr"));
         Utils.writeToResponse(data.toString(), "text/html;charset=UTF-8", response);
      } else {
         Utils.updateProductPropties("incomingTvAdr", incomingTvAdr);
      }
   }

   private void queryDeviceData(HttpServletRequest request, HttpServletResponse response) {
      JSONObject data = new JSONObject();
      String clientip = request.getParameter("clientip");
      if (!StringUtils.isNotBlank(clientip)) {
         clientip = request.getRemoteAddr();
      }

      String filter = request.getParameter("filter");
      int current = TpvStringUtils.tryParseInt(request.getParameter("current"), 1);
      int rowCount = TpvStringUtils.tryParseInt(request.getParameter("rowCount"), 10);
      String searchPhrase = request.getParameter("searchPhrase");
      String tvModelsPieChartFilter = request.getParameter("tv_models_pie_chart_filter");
      String currentPowerStautsPieChartFilter = request.getParameter("current_power_status_pie_chart_filter");
      String currentSwPieChartFilter = request.getParameter("current_sw_pie_chart_filter");
      String currentClonedataPieChartFilter = request.getParameter("current_clonedata_pie_chart_filter");
      String currentUpgradeStatusPieChartFilter = request.getParameter("current_upgrade_status_pie_chart_filter");
      String loadAllDevice = request.getParameter("loadAllDevice");
      LOG.debug("[TVs-device]current={},rowCount={},loadAllDevice={}", current, rowCount, loadAllDevice);
      StopWatch deviceWatch = new StopWatch();
      deviceWatch.start();
      StringBuilder sql = new StringBuilder(
         "select Id,TVIPAddress,TVMACAddress,TVModelNumber,TVRoomID,TVName,remotecontrol_status,PowerStatus,title,Progress,Type,reservationId,TVSerialNumber,fw_color,FirmwareId,si_firmware_Identifier,tv_firmware_Identifier,CloneId,si_clone_Identifiers,tv_clone_Identifiers,success_siclone_Identifier,checkin,guestId,guestName,expressCheckout,viewBill,doNotDisturb,success_tvclone_Identifier,clone_color,clone_mode,upload_progress,Status,LastCloneRename,Lastonline,si_Identifiers,TVStatus from deviceinfo_view v where 1=1 "
      );
      StringBuilder countSql = new StringBuilder("select count(*) from deviceinfo_view v where 1=1 ");
      List<Object> paramsList = new ArrayList<>();
      if (StringUtils.isNotBlank(searchPhrase)) {
         String[] matchFields = new String[]{
            "TVName",
            "TVModelNumber",
            "TVSerialNumber",
            "TVRoomID",
            "TVIPAddress",
            "Type",
            "TVMACAddress",
            "tv_clone_Identifiers",
            "TVGroups",
            "tv_firmware_Identifier"
         };
         String condition = " and (" + StringUtils.join(Arrays.stream(matchFields).map(field -> field + " like ?").collect(Collectors.toList()), " or ") + ")";

         for (int i = 0; i < matchFields.length; i++) {
            paramsList.add("%" + searchPhrase + "%");
         }

         sql.append(condition);
         countSql.append(condition);
      }

      if (StringUtils.isNotBlank(tvModelsPieChartFilter)) {
         InQueryParameter newfilter = InQueryParameter.getInstance(tvModelsPieChartFilter);
         String condition = " and TVModelNumber in (" + newfilter.getPreparedInReplaceHolder() + ")";
         paramsList.addAll(newfilter.getParamList());
         sql.append(condition);
         countSql.append(condition);
      }

      if (StringUtils.isNotBlank(currentPowerStautsPieChartFilter)) {
         String condition = "";
         if (currentPowerStautsPieChartFilter.equals("RF")) {
            condition = " and TVIPAddress = 'RF'";
         } else {
            InQueryParameter newfilter = InQueryParameter.getInstance(currentPowerStautsPieChartFilter);
            condition = " and PowerStatus in (" + newfilter.getPreparedInReplaceHolder() + ") and TVIPAddress <> 'RF' ";
            paramsList.addAll(newfilter.getParamList());
         }

         sql.append(condition);
         countSql.append(condition);
      }

      if (StringUtils.isNotBlank(currentSwPieChartFilter)) {
         InQueryParameter newfilter = InQueryParameter.getInstance(currentSwPieChartFilter);
         String condition = " and (tv_firmware_Identifier in (" + newfilter.getPreparedInReplaceHolder() + ")";
         if (newfilter.getParamList().contains("Unknown")) {
            condition = condition + " or tv_firmware_Identifier is null";
         }

         condition = condition + ")";
         paramsList.addAll(newfilter.getParamList());
         sql.append(condition);
         countSql.append(condition);
      }

      if (StringUtils.isNotBlank(currentClonedataPieChartFilter)) {
         InQueryParameter newfilter = InQueryParameter.getInstance(currentClonedataPieChartFilter);
         String condition = " and ((tv_clone_Identifiers in (" + newfilter.getPreparedInReplaceHolder() + ") and Status = 'Successful')";
         if (newfilter.getParamList().contains("Unknown")) {
            condition = condition + " or Status = 'Failure' or Status is null or tv_clone_Identifiers is null ";
         }

         condition = condition + ")";
         paramsList.addAll(newfilter.getParamList());
         sql.append(condition);
         countSql.append(condition);
      }

      if (StringUtils.isNotBlank(currentUpgradeStatusPieChartFilter)) {
         InQueryParameter newfilter = InQueryParameter.getInstance(
            currentUpgradeStatusPieChartFilter.replace("Nothing assigned", "black")
               .replace("Not started", "blue")
               .replace("In progress", "#FFBF00")
               .replace("Successful", "#01DF01")
               .replace("Failed", "red")
         );
         String condition = " and (fw_color  in ("
            + newfilter.getPreparedInReplaceHolder()
            + ") or clone_color in ("
            + newfilter.getPreparedInReplaceHolder()
            + "))";
         paramsList.addAll(newfilter.getParamList());
         paramsList.addAll(newfilter.getParamList());
         sql.append(condition);
         countSql.append(condition);
      }

      String sort = "";
      String sortClone = TpvStringUtils.getSortDirectionValueFromRequest(request, "sort[CloneDate]");
      String sortTVSerialNumber = TpvStringUtils.getSortDirectionValueFromRequest(request, "sort[TVSerialNumber]");
      String sortName = TpvStringUtils.getSortDirectionValueFromRequest(request, "sort[name]");
      String sortTVRoomID = TpvStringUtils.getSortDirectionValueFromRequest(request, "sort[TVRoomID]");
      String sortType = TpvStringUtils.getSortDirectionValueFromRequest(request, "sort[Type]");
      String sortTVIPAddress = TpvStringUtils.getSortDirectionValueFromRequest(request, "sort[TVIPAddress]");
      String sortUpgVersion = TpvStringUtils.getSortDirectionValueFromRequest(request, "sort[UpgVersion]");
      String sortPower = TpvStringUtils.getSortDirectionValueFromRequest(request, "sort[power]");
      String sortTVModelNumber = TpvStringUtils.getSortDirectionValueFromRequest(request, "sort[TVModelNumber]");
      String sortTVMACAddress = TpvStringUtils.getSortDirectionValueFromRequest(request, "sort[TVMACAddress]");
      if (null != sortTVSerialNumber) {
         sql.append(" order by TVSerialNumber " + sortTVSerialNumber);
         sort = "TVSerialNumber&" + sortTVSerialNumber;
      } else if (null != sortName) {
         sql.append(" order by TVName " + sortName);
         sort = "TVName&" + sortName;
      } else if (null != sortTVRoomID) {
         sql.append(" order by cast(TVRoomID as SIGNED) " + sortTVRoomID);
         sort = "TVRoomID&" + sortTVRoomID;
      } else if (null != sortType) {
         sql.append(" order by Type " + sortType);
         sort = "Type&" + sortType;
      } else if (null != sortTVIPAddress) {
         sql.append(" order by TVIPAddress " + sortTVIPAddress);
         sort = "TVIPAddress&" + sortTVIPAddress;
      } else if (null != sortTVModelNumber) {
         sql.append(" order by TVModelNumber " + sortTVModelNumber);
         sort = "TVModelNumber&" + sortTVModelNumber;
      } else if (null != sortTVMACAddress) {
         sql.append(" order by TVMACAddress " + sortTVMACAddress);
         sort = "TVMACAddress&" + sortTVMACAddress;
      } else if (null != sortClone) {
         int flagClone = TpvStringUtils.tryParseInt(request.getParameter("clone_clicked_counter"), 0) % 6;
         cloneClickedCounter = flagClone;
         if (flagClone == 0) {
            sql.append(" order by tv_clone_Identifiers asc");
            sort = "tv_clone_Identifiers&asc";
         } else if (flagClone == 1) {
            sql.append(" order by tv_clone_Identifiers desc");
            sort = "tv_clone_Identifiers&desc";
         } else {
            String orderByWhenSql = this.generateFWAndCloneWhenQuerySql(flagClone, "clone_color", "tv_clone_Identifiers");
            sql.append(orderByWhenSql);
            sort = "sql&" + orderByWhenSql;
         }
      } else if (null != sortUpgVersion) {
         int flagSW = TpvStringUtils.tryParseInt(request.getParameter("sw_clicked_counter"), 0) % 6;
         swClickedCounter = flagSW;
         if (0 == flagSW) {
            sql.append(" order by tv_firmware_Identifier asc");
            sort = "tv_firmware_Identifier&asc";
         } else if (1 == flagSW) {
            sql.append(" order by tv_firmware_Identifier desc");
            sort = "tv_firmware_Identifier&desc";
         } else {
            String orderByWhenSql = this.generateFWAndCloneWhenQuerySql(flagSW, "fw_color", "si_firmware_Identifier");
            sql.append(orderByWhenSql);
            sort = "sql&" + orderByWhenSql;
         }
      } else if (null != sortPower) {
         int flagPower = TpvStringUtils.tryParseInt(request.getParameter("power_clicked_counter"), 0) % 3;
         String[] powerSortConfig = null;
         if (flagPower == 0) {
            powerSortConfig = new String[]{"On", "Standby", "offline"};
         } else if (flagPower == 1) {
            powerSortConfig = new String[]{"Standby", "offline", "On"};
         } else {
            powerSortConfig = new String[]{"offline", "On", "Standby"};
         }

         StringBuilder orderByWhenSql = new StringBuilder(" order by case");

         for (int i = 0; i < powerSortConfig.length; i++) {
            orderByWhenSql.append(" when ").append("PowerStatus ='").append(powerSortConfig[i]).append("' then ").append(i);
         }

         orderByWhenSql.append(" else ").append(powerSortConfig.length).append(" end");
         sql.append(orderByWhenSql);
         sort = "sql&" + orderByWhenSql;
      } else {
         sql.append(" order by cast(TVRoomID as SIGNED) asc");
         sort = "TVRoomID&asc";
      }

      if (rowCount > 0) {
         int offset = 0;
         if (current > 0) {
            offset = (current - 1) * rowCount;
         }

         sql.append(" limit " + offset + "," + rowCount);
      }

      LOG.debug("SWSql={}", sql);
      Map<String, String> params = new HashMap<>();
      params.put("TVs_tabsDevices_gridDevices_page", String.valueOf(current));
      params.put("TVs_tabsDevices_gridDevices_dropdownText", String.valueOf(rowCount));
      params.put("TVs_tabsDevices_gridDevices_search", searchPhrase);
      params.put("TVs_tabsDevices_gridDevices_sort", sort);
      Utils.updateUserProfileConfig(params);

      try {
         data.put("current", current);
         data.put("rowCount", rowCount);
         data.put("total", JpaManager.countRecordsInDB(countSql.toString(), paramsList.toArray()));
         data.put("rows", JpaManager.getDevicesManager().getDeviceJsonData(sql.toString(), paramsList, filter));
         data.put("clientip", clientip);
      } catch (SQLException e) {
         LOG.error(e.getMessage(), e);
      }

      deviceWatch.stop();
      LOG.info("device take :{}", deviceWatch.getTime());
      Utils.writeToResponse(data.toString(), "text/html;charset=UTF-8", response);
   }

   private String generateFWAndCloneWhenQuerySql(int flagClone, String queryField, String secondSortField) {
      String[] sortCloneColor = null;
      if (flagClone == 2) {
         sortCloneColor = new String[]{"#FFBF00", "black", "#01DF01", "blue"};
      } else if (flagClone == 3) {
         sortCloneColor = new String[]{"black", "#01DF01", "blue", "#FFBF00"};
      } else if (flagClone == 4) {
         sortCloneColor = new String[]{"#01DF01", "blue", "#FFBF00", "black"};
      } else {
         sortCloneColor = new String[]{"blue", "#FFBF00", "black", "#01DF01"};
      }

      StringBuilder orderByWhenSql = new StringBuilder(" order by case");

      for (int i = 0; i < sortCloneColor.length; i++) {
         orderByWhenSql.append(" when ").append(queryField).append("='").append(sortCloneColor[i]).append("' then ").append(i);
      }

      orderByWhenSql.append(" else ").append(sortCloneColor.length).append(" end,").append(secondSortField);
      return orderByWhenSql.toString();
   }

   private void invisibleColumns(HttpServletRequest request, HttpServletResponse response, String userName) {
      String columns = request.getParameter("columns");
      String gridId = request.getParameter("grid_id");
      BootgridSettingManager bm = JpaManager.getBootgridSettingManager();
      BootgridSetting grid = null;
      List<BootgridSetting> bs = bm.findBootgridSettingByUserAndGridid(userName, gridId);
      if (!bs.isEmpty()) {
         grid = bs.get(0);
         grid.setInvisibleColumnId(columns);
         bm.save(grid);
      } else {
         BootgridSetting newOne = new BootgridSetting();
         newOne.setGridid(gridId);
         newOne.setUser(userName);
         newOne.setInvisibleColumnId(columns);
         bm.save(newOne);
      }

      Utils.writeToResponse("{\"status\":\"success\"}", "text/html;charset=UTF-8", response);
   }

   private void getCurrentSwCloneClickedCounter(HttpServletResponse response) {
      JsonObject json = new JsonObject();
      json.addProperty("cl_clicked_counter", cloneClickedCounter);
      json.addProperty("sw_clicked_counter", swClickedCounter);
      Utils.writeToResponse(json.toString(), "text/html;charset=UTF-8", response);
   }

   private void addRFTvs(HttpServletRequest request, HttpServletResponse response) {
      String status = "{\"status\":\"success\"}";
      String allRoomId = request.getParameter("allRoomId");
      String platform = request.getParameter("platform");
      LOG.info("allRoomId:{},platform:{}", allRoomId, platform);

      try {
         if (!PlatformUtils.isValidPlatformForNewRFTV(platform)) {
            throw new IOException("Invalid platform " + platform + " for Adding RF TV");
         }

         if (StringUtils.isEmpty(allRoomId)) {
            throw new IOException("RoomId is empty");
         }

         DevicesManager tvmanager = JpaManager.getDevicesManager();
         List<Devices> existTv = null;
         String[] tvRoomIdsArr = allRoomId.split(",");
         int tvRoomIdlen = tvRoomIdsArr.length;

         for (int i = 0; i < tvRoomIdlen; i++) {
            String roomId = TpvStringUtils.getTvRoomId(tvRoomIdsArr[i]);
            existTv = tvmanager.findDevicesByRoomIdAndType(roomId, platform).stream().filter(Devices::isRFDevice).collect(Collectors.toList());
            if (!existTv.isEmpty()) {
               existTv.get(0).setPowerstatus("On");
               tvmanager.save(existTv.get(0));
            } else {
               PmsUtils.createRFTV(roomId, platform);
            }
         }
      } catch (IOException e) {
         status = this.failedStatus(e.getMessage());
      }

      Utils.writeToResponse(status, "text/html;charset=UTF-8", response);
   }

   private void showDeviceList(HttpServletRequest request, HttpServletResponse response, String userName, String tvColumns, String groupColumns) throws ServletException, IOException {
      BootgridSettingManager bm = JpaManager.getBootgridSettingManager();
      BootgridSetting getSetting = null;
      List<BootgridSetting> forTV = bm.findBootgridSettingByUserAndGridid(userName, "grid_devices");
      if (!forTV.isEmpty()) {
         getSetting = forTV.get(0);
         tvColumns = getSetting.getInvisibleColumnId();
      }

      List<BootgridSetting> forGroup = bm.findBootgridSettingByUserAndGridid(userName, "grid_groups");
      if (!forGroup.isEmpty()) {
         getSetting = forGroup.get(0);
         groupColumns = getSetting.getInvisibleColumnId();
      }

      request.setAttribute("tvColumns", tvColumns);
      request.setAttribute("groupColumns", groupColumns);
      request.setAttribute("pmsEnabled", PmsUtils.isPmsEnabled());
      LastRFConfig lastRFConfig = LastRFConfig.loadLastConfig();
      request.setAttribute("lastRFConfig", new Gson().toJson(lastRFConfig));
      IPProfileConfig iPProfileConfig = Utils.getUserConfig();
      request.setAttribute("jsonConfig", iPProfileConfig.getConfigsJson());
      request.setAttribute(
         "isHidePieChart",
         StringUtils.equalsIgnoreCase("hide", iPProfileConfig.getConfig(IPProfileConfig.IPProfileConfigName.TVs_tabsDevices_tvStatusOverview))
      );
      request.getRequestDispatcher("devices.jsp").forward(request, response);
   }

   private JSONObject getGroupsData(HttpServletRequest request) {
      SearchParam sp = this.buildWhereClause(request, "name,sw,cl");
      Map<String, String> params = new HashMap<>();
      params.put("TVs_tabsGroups_gridDevices_g_page", String.valueOf(sp.getCurrentPage()));
      params.put("TVs_tabsGroups_gridDevices_g_dropdownText", String.valueOf(sp.getRowCount()));
      params.put("TVs_tabsGroups_gridDevices_g_search", sp.getSearchPhrase());
      params.put("TVs_tabsGroups_gridDevices_g_sort", sp.getSort());
      Utils.updateUserProfileConfig(params);
      return JpaManager.getGroupsManager().findGroupInfoViewPageBySearchParam(sp);
   }
}
