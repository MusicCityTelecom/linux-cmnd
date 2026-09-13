package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.SearchParam;
import com.tpvision.smartinstall.util.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.HtmlUtils;

public class JpaManager {
   private static final Logger LOG = LoggerFactory.getLogger(JpaManager.class);

   private JpaManager() {
   }

   public static DataSource getDataSource() {
      return getObjectFromSpringContext(DataSource.class);
   }

   public static Connection getConnection() throws SQLException {
      return getDataSource().getConnection();
   }

   private static <T> T getObjectFromSpringContext(Class<T> baseClz) {
      WebApplicationContext webApplicationContext = (WebApplicationContext)Utils.getServletContext()
         .getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
      return webApplicationContext.getBean(baseClz);
   }

   public static SmartinfoSettingManager getSmartinfoSettingManager() {
      return getObjectFromSpringContext(SmartinfoSettingManager.class);
   }

   public static RfPlayedoutSettingManager getRfPlayedoutSettingManager() {
      return getObjectFromSpringContext(RfPlayedoutSettingManager.class);
   }

   public static ReservationManager getReservationManager() {
      return getObjectFromSpringContext(ReservationManager.class);
   }

   public static RoleManager getRoleManager() {
      return getObjectFromSpringContext(RoleManager.class);
   }

   public static PmsStatusManager getPmsStatusManager() {
      return getObjectFromSpringContext(PmsStatusManager.class);
   }

   public static ProfileRoleManager getProfileRoleManager() {
      return getObjectFromSpringContext(ProfileRoleManager.class);
   }

   public static SmartuiManager getSmartuiManager() {
      return getObjectFromSpringContext(SmartuiManager.class);
   }

   public static PlayoutInfoManager getPlayoutInfoManager() {
      return getObjectFromSpringContext(PlayoutInfoManager.class);
   }

   public static PincodeHistoryManager getPincodeHistoryManager() {
      return getObjectFromSpringContext(PincodeHistoryManager.class);
   }

   public static RoominfoManager getRoominfoManager() {
      return getObjectFromSpringContext(RoominfoManager.class);
   }

   public static WeatherManager getWeatherManager() {
      return getObjectFromSpringContext(WeatherManager.class);
   }

   public static SIConfigManager getSIConfigManager() {
      return getObjectFromSpringContext(SIConfigManager.class);
   }

   public static UpgSettingManager getUpgSettingManager() {
      return getObjectFromSpringContext(UpgSettingManager.class);
   }

   public static OnlineDevicesManager getOnlineDevicesManager() {
      return getObjectFromSpringContext(OnlineDevicesManager.class);
   }

   public static ProfileManager getProfileManager() {
      return getObjectFromSpringContext(ProfileManager.class);
   }

   public static SettingManager getSettingManager() {
      return getObjectFromSpringContext(SettingManager.class);
   }

   public static AppPackageManager getAppPackageManager() {
      return getObjectFromSpringContext(AppPackageManager.class);
   }

   public static SettingPackageManager getSettingPackageManager() {
      return getObjectFromSpringContext(SettingPackageManager.class);
   }

   public static BannersManager getBannersManager() {
      return getObjectFromSpringContext(BannersManager.class);
   }

   public static BillitemManager getBillitemManager() {
      return getObjectFromSpringContext(BillitemManager.class);
   }

   public static BootgridSettingManager getBootgridSettingManager() {
      return getObjectFromSpringContext(BootgridSettingManager.class);
   }

   public static ChannelPackageManager getChannelPackageManager() {
      return getObjectFromSpringContext(ChannelPackageManager.class);
   }

   public static ExApiManager getExApiManager() {
      return getObjectFromSpringContext(ExApiManager.class);
   }

   public static GroupsManager getGroupsManager() {
      return getObjectFromSpringContext(GroupsManager.class);
   }

   public static ScheduleManager getScheduleManager() {
      return getObjectFromSpringContext(ScheduleManager.class);
   }

   public static UiCustomizationsManager getUiCustomizationsManager() {
      return getObjectFromSpringContext(UiCustomizationsManager.class);
   }

   public static GuestInfoManager getGuestInfoManager() {
      return getObjectFromSpringContext(GuestInfoManager.class);
   }

   public static SmartcmsSettingManager getSmartcmsSettingManager() {
      return getObjectFromSpringContext(SmartcmsSettingManager.class);
   }

   public static WelcomeManager getWelcomeManager() {
      return getObjectFromSpringContext(WelcomeManager.class);
   }

   public static DevicesManager getDevicesManager() {
      return getObjectFromSpringContext(DevicesManager.class);
   }

   public static TriggerInfoManager getTriggerInfoManager() {
      return getObjectFromSpringContext(TriggerInfoManager.class);
   }

   public static TriggerHistoryManager getTriggerHistoryManager() {
      return getObjectFromSpringContext(TriggerHistoryManager.class);
   }

   public static MessageManager getMessageManager() {
      return getObjectFromSpringContext(MessageManager.class);
   }

   public static JobLogManager getJobLogManager() {
      return getObjectFromSpringContext(JobLogManager.class);
   }

   public static MyChoiceTemplateManager getMyChoiceTemplateManager() {
      return getObjectFromSpringContext(MyChoiceTemplateManager.class);
   }

   public static FutureCheckInManager getFutureCheckInManager() {
      return getObjectFromSpringContext(FutureCheckInManager.class);
   }

   public static RoomNotificationManager getRoomNotificationManager() {
      return getObjectFromSpringContext(RoomNotificationManager.class);
   }

   public static WakeupInfoManager getWakeupInfoManager() {
      return getObjectFromSpringContext(WakeupInfoManager.class);
   }

   public static VersionNoticeManager getVersionNoticeManager() {
      return getObjectFromSpringContext(VersionNoticeManager.class);
   }

   public static ReceptionClientManager getReceptionClientManager() {
      return getObjectFromSpringContext(ReceptionClientManager.class);
   }

   public static CastServerSettingManager getCastServerSettingManager() {
      return getObjectFromSpringContext(CastServerSettingManager.class);
   }

   public static CastAnalyticalDataManager getCastAnalyticalDataManager() {
      return getObjectFromSpringContext(CastAnalyticalDataManager.class);
   }

   private static String exportOrderByAndPageSql(SearchParam searchParam) {
      StringBuilder sqlWhere = new StringBuilder();
      if (StringUtils.isNotBlank(searchParam.getSort())) {
         sqlWhere.append(" order by " + searchParam.getSort().replace("&", " "));
      }

      if (searchParam.getRowCount() > 0) {
         int offset = 0;
         if (searchParam.getCurrentPage() > 0) {
            offset = (searchParam.getCurrentPage() - 1) * searchParam.getRowCount();
         }

         sqlWhere.append(" limit " + offset + "," + searchParam.getRowCount());
      }

      return sqlWhere.toString();
   }

   public static JSONObject findSimpleLikeDataPageBySearchParam(String tableOrViewName, SearchParam searchParam) throws SQLException {
      JSONObject data = new JSONObject();
      String baseQuerySql = "select * from " + tableOrViewName;
      String baseCountSql = "select count(*) from " + tableOrViewName;
      StringBuilder sql = new StringBuilder(baseQuerySql);
      StringBuilder sqlCount = new StringBuilder(baseCountSql);
      List<Object> paramList = new ArrayList<>();
      if (StringUtils.isNotBlank(searchParam.getSearchPhrase()) && StringUtils.isNotBlank(searchParam.getFilterFields())) {
         String[] filtersList = searchParam.getFilterFields().split(",");
         StringBuilder sb = new StringBuilder();

         for (String filter : filtersList) {
            String condition = " " + filter + " like ? ";
            if (sb.length() > 0) {
               sb.append("or");
            }

            sb.append(condition);
            paramList.add("%" + searchParam.getSearchPhrase() + "%");
         }

         if (sb.length() > 0) {
            if (!baseQuerySql.toLowerCase().contains("where")) {
               sql.append(" where ");
            } else {
               sql.append(" and ");
            }

            sql.append(" (").append(sb.toString()).append(")");
            if (!baseCountSql.toLowerCase().contains("where")) {
               sqlCount.append(" where ");
            } else {
               sqlCount.append(" and ");
            }

            sqlCount.append(" (").append(sb.toString()).append(")");
         }
      }

      sql.append(exportOrderByAndPageSql(searchParam));
      Object[] paramsArray = paramList.toArray(new Object[0]);
      JSONArray pageDataArray = getResultsetAsArray(sql.toString(), paramsArray);
      data.put("rows", pageDataArray);
      data.put("current", searchParam.getCurrentPage());
      data.put("rowCount", searchParam.getRowCount());
      data.put("total", countRecordsInDB(sqlCount.toString(), paramsArray));
      return data;
   }

   public static int executeManipulationSql(String executeSql, Object... params) throws SQLException {
      try (
         Connection conn = getConnection();
         PreparedStatement stat = conn.prepareStatement(executeSql);
      ) {
         for (int i = 1; i <= params.length; i++) {
            stat.setObject(i, params[i - 1]);
         }

         return stat.executeUpdate();
      }
   }

   public static int countRecordsInDB(String sqlCountSetting, Object... params) throws SQLException {
      int count = 0;

      try (
         Connection conn = getConnection();
         PreparedStatement stat = conn.prepareStatement(sqlCountSetting);
      ) {
         for (int i = 1; i <= params.length; i++) {
            stat.setObject(i, params[i - 1]);
         }

         try (ResultSet rs = stat.executeQuery()) {
            if (rs.next()) {
               count = rs.getInt(1);
            }
         }
      }

      return count;
   }

   public static JSONArray getResultsetAsArray(String pstmSql, Object... params) throws SQLException {
      JSONArray array = new JSONArray();

      try (
         Connection conn = getConnection();
         PreparedStatement stat = conn.prepareStatement(pstmSql);
      ) {
         for (int i = 1; i <= params.length; i++) {
            stat.setObject(i, params[i - 1]);
         }

         try (ResultSet rs = stat.executeQuery()) {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            JSONObject jsonObj = null;

            while (rs.next()) {
               jsonObj = new JSONObject();

               for (int i = 1; i <= columnCount; i++) {
                  String columnName = metaData.getColumnLabel(i);
                  String value = "";
                  String columnData = rs.getString(columnName);
                  if (StringUtils.isNotBlank(columnData)) {
                     value = HtmlUtils.htmlEscape(columnData);
                  }

                  jsonObj.put(columnName, value);
               }

               array.put(jsonObj);
            }
         } catch (Exception ex) {
            LOG.error("execute sql : {} :{}", pstmSql, params);
            throw ex;
         }

         return array;
      }
   }
}
