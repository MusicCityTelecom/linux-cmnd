package com.tpvision.smartinstall.servlet;

import com.tpvision.smartinstall.SearchParam;
import com.tpvision.smartinstall.dao.core.RfPlayedoutSetting;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.gateway.GatewayManager;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.TpvRunableTask;
import com.tpvision.smartinstall.util.Utils;
import com.tpvision.smartinstall.util.WelcomeLogoUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/getFile")
public class FileServlet extends BaseHttpServlet {
   private static final long serialVersionUID = 1L;
   private static final Logger LOG = LoggerFactory.getLogger(FileServlet.class);

   @Override
   public void init() {
      new Thread(new TpvRunableTask() {
         @Override
         public void execute() {
            WelcomeLogoUtils.getInstance().updateWelcomeThumbCache();
         }
      }).start();
   }

   @Override
   protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String type = request.getParameter("type");
      JSONObject data = new JSONObject();
      if (null == type) {
         type = "";
      }

      switch (type) {
         case "getCloneData":
            data = this.getCloneData(request);
            break;
         case "getWelcomeList":
            data = this.getWelcomeList(request);
            break;
         case "getFirmware":
            data = this.getFirmwareList(request);
            break;
         case "getPlayoutList":
            data = this.getPlayoutList(request);
            break;
         case "getChannelPackageData":
            data = this.getChannelPackageList(request);
            break;
         case "getSettingPackageData":
            data = this.getSettingPackageList(request);
            break;
         case "getAppPackageList":
            data = this.getAppPackageList(request);
            break;
         case "getBannersList":
            data = this.getBannersList(request);
            break;
         case "getUiCustomizations":
            data = this.getUiCustomizations(request);
            break;
         case "getScheduleList":
            data = this.getScheduleList(request);
            break;
         case "getTriggersList":
            data = this.getTriggersList(request);
      }

      String mode = request.getParameter("mode");
      if ("index".equalsIgnoreCase(mode)) {
         LastRFConfig lastRFConfig = LastRFConfig.loadLastConfig();
         String anchor = request.getParameter("anchor");
         if (StringUtils.isEmpty(anchor)) {
            anchor = "tabs_clone";
         }

         File f = new File(CommonConstants.LAST_SELECTED_CLONE_FILE);
         String lastSelectedCloneId = null;
         if (!f.exists()) {
            f.createNewFile();
         }

         try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
            lastSelectedCloneId = reader.readLine();
         } catch (IOException e) {
            LOG.error(e.getMessage(), e);
         }

         request.setAttribute("lastSelectedCloneId", lastSelectedCloneId);
         request.setAttribute("anchor", anchor);
         request.setAttribute("status", request.getParameter("msg"));
         request.setAttribute("lastRFConfig", lastRFConfig.toJson());
         request.setAttribute("jsonConfig", Utils.getUserConfig().getConfigsJson());
         request.getRequestDispatcher("manageFiles.jsp").forward(request, response);
      } else {
         if ("updateLastSelectedCloneId".equalsIgnoreCase(mode)) {
            String lastSelectedCloneId = request.getParameter("id");
            File f = new File(CommonConstants.LAST_SELECTED_CLONE_FILE);
            if (!f.exists()) {
               f.createNewFile();
            }

            try (
               FileOutputStream fos = new FileOutputStream(f);
               PrintStream ps = new PrintStream(fos);
            ) {
               ps.println(lastSelectedCloneId);
            } catch (Exception e) {
               LOG.error(e.getMessage(), e);
            }
         }

         Utils.writeToResponse(data.toString(), "text/html;charset=UTF-8", response);
      }
   }

   private JSONObject getTriggersList(HttpServletRequest request) {
      SearchParam sp = this.buildWhereClause(request, "name,trigger_condition,target,trigger_type,last_edit");
      this.updateProfileConfig("tabsTriggers", sp);
      return JpaManager.getTriggerInfoManager().findTriggerInfoPageBySearchParam(sp);
   }

   private JSONObject getScheduleList(HttpServletRequest request) {
      SearchParam sp = this.buildWhereClause(request, "name,content,lastEdit");
      this.updateProfileConfig("tabsSchedule", sp);
      JSONObject pageResult = JpaManager.getScheduleManager().findSchedulePageBySearchParam(sp);
      JSONArray pageData = pageResult.getJSONArray("rows");
      int i = 0;

      for (int j = pageData.length(); i < j; i++) {
         JSONObject rowData = pageData.getJSONObject(i);
         rowData.put("lastEdit", TpvDateUtils.convertLastEditFormat(rowData.getString("lastEdit")));
         rowData.put("id", Integer.parseInt(rowData.getString("id")));
         rowData.put("isSupportPlayout", PlatformUtils.isSupportRFPlayout(rowData.getString("platform")));
      }

      return pageResult;
   }

   private JSONObject getUiCustomizations(HttpServletRequest request) {
      SearchParam sp = this.buildWhereClause(request, "name,lastEdit");
      this.updateProfileConfig("tabsUI", sp);
      JSONObject pageResult = JpaManager.getUiCustomizationsManager().findUiCustomizationsPageBySearchParam(sp);
      JSONArray pageData = pageResult.getJSONArray("rows");
      int i = 0;

      for (int j = pageData.length(); i < j; i++) {
         JSONObject rowData = pageData.getJSONObject(i);
         rowData.put("lastEdit", TpvDateUtils.convertLastEditFormat(rowData.getString("lastEdit")));
         rowData.put("id", Integer.parseInt(rowData.getString("id")));
         rowData.put("isSupportPlayout", PlatformUtils.isSupportRFPlayout(rowData.getString("platform")));
      }

      return pageResult;
   }

   private JSONObject getBannersList(HttpServletRequest request) {
      SearchParam sp = this.buildWhereClause(request, "name,content,triggers");
      this.updateProfileConfig("tabsBanners", sp);
      JSONObject pageResult = JpaManager.getBannersManager().findBannersPageBySearchParam(sp);
      JSONArray pageData = pageResult.getJSONArray("rows");
      int i = 0;

      for (int j = pageData.length(); i < j; i++) {
         JSONObject rowData = pageData.getJSONObject(i);
         rowData.put("lastEdit", TpvDateUtils.convertLastEditFormat(rowData.getString("lastEdit")));
         rowData.put("id", Integer.parseInt(rowData.getString("id")));
         rowData.put("platform", PlatformUtils.getPlatformName(rowData.getString("platform")));
         rowData.put("isSupportPlayout", PlatformUtils.isSupportRFPlayout(rowData.getString("platform")));
      }

      return pageResult;
   }

   private JSONObject getAppPackageList(HttpServletRequest request) {
      SearchParam sp = this.buildWhereClause(request, "name,platform,lastEdit");
      this.updateProfileConfig("tabsApp", sp);
      JSONObject data = JpaManager.getAppPackageManager().findAppPackagePageBySearchParam(sp);
      JSONArray rows = data.getJSONArray("rows");
      int i = 0;

      for (int j = rows.length(); i < j; i++) {
         JSONObject rowData = rows.getJSONObject(i);
         rowData.put("lastEdit", TpvDateUtils.convertLastEditFormat(rowData.getString("lastEdit")));
         rowData.put("id", Integer.parseInt(rowData.getString("id")));
         rowData.put("isSupportPlayout", PlatformUtils.isSupportRFPlayout(rowData.getString("platform")));
      }

      return data;
   }

   private JSONObject getSettingPackageList(HttpServletRequest request) {
      SearchParam sp = this.buildWhereClause(request, "name,platform,lastEdit");
      this.updateProfileConfig("tabsSettingPackage", sp);
      JSONObject data = JpaManager.getSettingPackageManager().findSettingPackagePageBySearchParam(sp);
      JSONArray rows = data.getJSONArray("rows");
      int i = 0;

      for (int j = rows.length(); i < j; i++) {
         JSONObject rowData = rows.getJSONObject(i);
         rowData.put("lastEdit", TpvDateUtils.convertLastEditFormat(rowData.getString("lastEdit")));
         rowData.put("id", Integer.parseInt(rowData.getString("id")));
         rowData.put("platform", PlatformUtils.getPlatformName(rowData.getString("platform")));
         rowData.put("isSupportPlayout", PlatformUtils.isSupportRFPlayout(rowData.getString("platform")));
      }

      return data;
   }

   private JSONObject getChannelPackageList(HttpServletRequest request) {
      SearchParam sp = this.buildWhereClause(request, "name,platform,lastEdit,numberOfChs");
      this.updateProfileConfig("tabsChannelPackage", sp);
      JSONObject data = JpaManager.getChannelPackageManager().findChannelPackagePageBySearchParam(sp);
      JSONArray rows = data.getJSONArray("rows");
      int i = 0;

      for (int j = rows.length(); i < j; i++) {
         JSONObject rowData = rows.getJSONObject(i);
         rowData.put("lastEdit", TpvDateUtils.convertLastEditFormat(rowData.getString("lastEdit")));
         rowData.put("id", Integer.parseInt(rowData.getString("id")));
         rowData.put("numberOfChs", Integer.parseInt(rowData.getString("numberOfChs")));
         rowData.put("isSupportPlayout", PlatformUtils.isSupportRFPlayout(rowData.getString("platform")));
         rowData.put("isSupportEdit", PlatformUtils.isSupportChannelEditor(rowData.getString("platform")));
      }

      return data;
   }

   private JSONObject getPlayoutList(HttpServletRequest request) {
      SearchParam sp = this.buildWhereClause(request, "rooms,name,type,platform,status");
      this.updateProfileConfig("tabsPlay", sp);
      JSONObject data = JpaManager.getPlayoutInfoManager().findPlayoutInfoPageBySearchParam(sp);
      JSONArray rows = data.getJSONArray("rows");
      int i = 0;

      for (int j = rows.length(); i < j; i++) {
         JSONObject rowData = rows.getJSONObject(i);
         rowData.put("playoutId", Integer.parseInt(rowData.getString("playoutId")));
         rowData.put("statusMsg", GatewayManager.getInstance().getItemStatusByCode(Integer.parseInt(rowData.getString("status"))));
         rowData.put("platform", PlatformUtils.getPlatformName(rowData.getString("platform")));
      }

      return data;
   }

   private JSONObject getFirmwareList(HttpServletRequest request) {
      SearchParam sp = this.buildWhereClause(request, "name,platform,upgRename");
      this.updateProfileConfig("tabsFirmware", sp);
      JSONObject data = JpaManager.getUpgSettingManager().findUpgSettingPageBySearchParam(sp);
      JSONArray rows = data.getJSONArray("rows");
      int i = 0;

      for (int j = rows.length(); i < j; i++) {
         JSONObject rowData = rows.getJSONObject(i);
         rowData.put("id", Integer.parseInt(rowData.getString("id")));
         rowData.put("platForm", PlatformUtils.getPlatformName(rowData.getString("platform")));
         rowData.put("isSupportPlayout", PlatformUtils.isSupportRFPlayout(rowData.getString("platform")));
      }

      return data;
   }

   private JSONObject getCloneData(HttpServletRequest request) {
      SearchParam sp = this.buildWhereClause(
         request, "platform,cloneRename,content,welcomeName,channelPackageName,settingPackageName,appPackageName,bannersName,uiCustomizationsName,scheduleName"
      );
      this.updateProfileConfig("tabsClone", sp);
      JSONObject data = JpaManager.getSettingManager().findClonesPageBySearchParam(sp);
      JSONArray pageData = data.getJSONArray("rows");
      int i = 0;

      for (int j = pageData.length(); i < j; i++) {
         JSONObject row = pageData.getJSONObject(i);
         int id = Integer.parseInt(row.getString("id"));
         row.put("id", id);
         String contentStr = row.getString("content");
         row.put(
            "content", !StringUtils.isBlank(contentStr) && !StringUtils.equalsIgnoreCase("None", contentStr) ? StringEscapeUtils.escapeJava(contentStr) : ""
         );
         row.put("isCloud", "0");
         row.put("welcomeType", PlatformUtils.getWelcomeType(row.getString("platform")));
         row.put("hasApp", PlatformUtils.hasPackageFeature(row.getString("platform")));
         row.put("platForm", PlatformUtils.getPlatformName(row.getString("platform")));
         row.put("isSupportPlayout", PlatformUtils.isSupportRFPlayout(row.getString("platform")));
         row.put("isSupportChannelEdit", PlatformUtils.isSupportChannelEditor(row.getString("platform")));
         row.put("rf_played_on", this.getRFPlayoutOn(id));
         row.put("last_updated_date", this.getLastUpdatedDate(id));
      }

      return data;
   }

   private void updateProfileConfig(String itemName, SearchParam param) {
      Map<String, String> params = new HashMap<>();
      params.put("Files_" + itemName + "_page", String.valueOf(param.getCurrentPage()));
      params.put("Files_" + itemName + "_dropdownText", String.valueOf(param.getRowCount()));
      params.put("Files_" + itemName + "_search", param.getSearchPhrase());
      params.put("Files_" + itemName + "_sort", param.getSort());
      Utils.updateUserProfileConfig(params);
   }

   private JSONObject getWelcomeList(HttpServletRequest request) {
      SearchParam sp = this.buildWhereClause(request, "name");
      this.updateProfileConfig("tabsWelcome", sp);
      JSONObject data = JpaManager.getWelcomeManager().findWelcomesPageBySearchParam(sp);
      JSONArray rows = data.getJSONArray("rows");
      int i = 0;

      for (int j = rows.length(); i < j; i++) {
         JSONObject rowData = rows.getJSONObject(i);
         rowData.put("lastEdit", TpvDateUtils.convertLastEditFormat(rowData.getString("lastEdit")));
         rowData.put("id", Integer.parseInt(rowData.getString("id")));
         rowData.put("platform", PlatformUtils.getPlatformName(rowData.getString("platform")));
         rowData.put("thumbnailUrl", WelcomeLogoUtils.getInstance().getThumbnailUrl(rowData.getInt("id")));
         rowData.put("isSupportPlayout", PlatformUtils.isSupportRFPlayout(rowData.getString("platform")));
      }

      return data;
   }

   private String getLastUpdatedDate(int id) {
      String result = null;
      RfPlayedoutSetting rfPlayedoutSetting = JpaManager.getRfPlayedoutSettingManager().loadByKey(id);
      if (rfPlayedoutSetting != null) {
         result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rfPlayedoutSetting.getLastUpdatedDate());
      }

      return result;
   }

   private String getRFPlayoutOn(int id) {
      RfPlayedoutSetting rfPlayedoutSetting = JpaManager.getRfPlayedoutSettingManager().loadByKey(id);
      return rfPlayedoutSetting != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rfPlayedoutSetting.getRfPlayedOn()) : null;
   }
}
