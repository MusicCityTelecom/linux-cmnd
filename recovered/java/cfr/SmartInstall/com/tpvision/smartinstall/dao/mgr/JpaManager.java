/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.SearchParam;
import com.tpvision.smartinstall.dao.mgr.AppPackageManager;
import com.tpvision.smartinstall.dao.mgr.BannersManager;
import com.tpvision.smartinstall.dao.mgr.BillitemManager;
import com.tpvision.smartinstall.dao.mgr.BootgridSettingManager;
import com.tpvision.smartinstall.dao.mgr.CastAnalyticalDataManager;
import com.tpvision.smartinstall.dao.mgr.CastServerSettingManager;
import com.tpvision.smartinstall.dao.mgr.ChannelPackageManager;
import com.tpvision.smartinstall.dao.mgr.DevicesManager;
import com.tpvision.smartinstall.dao.mgr.ExApiManager;
import com.tpvision.smartinstall.dao.mgr.FutureCheckInManager;
import com.tpvision.smartinstall.dao.mgr.GroupsManager;
import com.tpvision.smartinstall.dao.mgr.GuestInfoManager;
import com.tpvision.smartinstall.dao.mgr.JobLogManager;
import com.tpvision.smartinstall.dao.mgr.MessageManager;
import com.tpvision.smartinstall.dao.mgr.MyChoiceTemplateManager;
import com.tpvision.smartinstall.dao.mgr.OnlineDevicesManager;
import com.tpvision.smartinstall.dao.mgr.PincodeHistoryManager;
import com.tpvision.smartinstall.dao.mgr.PlayoutInfoManager;
import com.tpvision.smartinstall.dao.mgr.PmsStatusManager;
import com.tpvision.smartinstall.dao.mgr.ProfileManager;
import com.tpvision.smartinstall.dao.mgr.ProfileRoleManager;
import com.tpvision.smartinstall.dao.mgr.ReceptionClientManager;
import com.tpvision.smartinstall.dao.mgr.ReservationManager;
import com.tpvision.smartinstall.dao.mgr.RfPlayedoutSettingManager;
import com.tpvision.smartinstall.dao.mgr.RoleManager;
import com.tpvision.smartinstall.dao.mgr.RoomNotificationManager;
import com.tpvision.smartinstall.dao.mgr.RoominfoManager;
import com.tpvision.smartinstall.dao.mgr.SIConfigManager;
import com.tpvision.smartinstall.dao.mgr.ScheduleManager;
import com.tpvision.smartinstall.dao.mgr.SettingManager;
import com.tpvision.smartinstall.dao.mgr.SettingPackageManager;
import com.tpvision.smartinstall.dao.mgr.SmartcmsSettingManager;
import com.tpvision.smartinstall.dao.mgr.SmartinfoSettingManager;
import com.tpvision.smartinstall.dao.mgr.SmartuiManager;
import com.tpvision.smartinstall.dao.mgr.TriggerHistoryManager;
import com.tpvision.smartinstall.dao.mgr.TriggerInfoManager;
import com.tpvision.smartinstall.dao.mgr.UiCustomizationsManager;
import com.tpvision.smartinstall.dao.mgr.UpgSettingManager;
import com.tpvision.smartinstall.dao.mgr.VersionNoticeManager;
import com.tpvision.smartinstall.dao.mgr.WakeupInfoManager;
import com.tpvision.smartinstall.dao.mgr.WeatherManager;
import com.tpvision.smartinstall.dao.mgr.WelcomeManager;
import com.tpvision.smartinstall.util.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;

public class JpaManager {
    private static final Logger LOG = LoggerFactory.getLogger(JpaManager.class);

    private JpaManager() {
    }

    public static DataSource getDataSource() {
        return JpaManager.getObjectFromSpringContext(DataSource.class);
    }

    public static Connection getConnection() throws SQLException {
        return JpaManager.getDataSource().getConnection();
    }

    private static <T> T getObjectFromSpringContext(Class<T> baseClz) {
        WebApplicationContext webApplicationContext = (WebApplicationContext)Utils.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        return webApplicationContext.getBean(baseClz);
    }

    public static SmartinfoSettingManager getSmartinfoSettingManager() {
        return JpaManager.getObjectFromSpringContext(SmartinfoSettingManager.class);
    }

    public static RfPlayedoutSettingManager getRfPlayedoutSettingManager() {
        return JpaManager.getObjectFromSpringContext(RfPlayedoutSettingManager.class);
    }

    public static ReservationManager getReservationManager() {
        return JpaManager.getObjectFromSpringContext(ReservationManager.class);
    }

    public static RoleManager getRoleManager() {
        return JpaManager.getObjectFromSpringContext(RoleManager.class);
    }

    public static PmsStatusManager getPmsStatusManager() {
        return JpaManager.getObjectFromSpringContext(PmsStatusManager.class);
    }

    public static ProfileRoleManager getProfileRoleManager() {
        return JpaManager.getObjectFromSpringContext(ProfileRoleManager.class);
    }

    public static SmartuiManager getSmartuiManager() {
        return JpaManager.getObjectFromSpringContext(SmartuiManager.class);
    }

    public static PlayoutInfoManager getPlayoutInfoManager() {
        return JpaManager.getObjectFromSpringContext(PlayoutInfoManager.class);
    }

    public static PincodeHistoryManager getPincodeHistoryManager() {
        return JpaManager.getObjectFromSpringContext(PincodeHistoryManager.class);
    }

    public static RoominfoManager getRoominfoManager() {
        return JpaManager.getObjectFromSpringContext(RoominfoManager.class);
    }

    public static WeatherManager getWeatherManager() {
        return JpaManager.getObjectFromSpringContext(WeatherManager.class);
    }

    public static SIConfigManager getSIConfigManager() {
        return JpaManager.getObjectFromSpringContext(SIConfigManager.class);
    }

    public static UpgSettingManager getUpgSettingManager() {
        return JpaManager.getObjectFromSpringContext(UpgSettingManager.class);
    }

    public static OnlineDevicesManager getOnlineDevicesManager() {
        return JpaManager.getObjectFromSpringContext(OnlineDevicesManager.class);
    }

    public static ProfileManager getProfileManager() {
        return JpaManager.getObjectFromSpringContext(ProfileManager.class);
    }

    public static SettingManager getSettingManager() {
        return JpaManager.getObjectFromSpringContext(SettingManager.class);
    }

    public static AppPackageManager getAppPackageManager() {
        return JpaManager.getObjectFromSpringContext(AppPackageManager.class);
    }

    public static SettingPackageManager getSettingPackageManager() {
        return JpaManager.getObjectFromSpringContext(SettingPackageManager.class);
    }

    public static BannersManager getBannersManager() {
        return JpaManager.getObjectFromSpringContext(BannersManager.class);
    }

    public static BillitemManager getBillitemManager() {
        return JpaManager.getObjectFromSpringContext(BillitemManager.class);
    }

    public static BootgridSettingManager getBootgridSettingManager() {
        return JpaManager.getObjectFromSpringContext(BootgridSettingManager.class);
    }

    public static ChannelPackageManager getChannelPackageManager() {
        return JpaManager.getObjectFromSpringContext(ChannelPackageManager.class);
    }

    public static ExApiManager getExApiManager() {
        return JpaManager.getObjectFromSpringContext(ExApiManager.class);
    }

    public static GroupsManager getGroupsManager() {
        return JpaManager.getObjectFromSpringContext(GroupsManager.class);
    }

    public static ScheduleManager getScheduleManager() {
        return JpaManager.getObjectFromSpringContext(ScheduleManager.class);
    }

    public static UiCustomizationsManager getUiCustomizationsManager() {
        return JpaManager.getObjectFromSpringContext(UiCustomizationsManager.class);
    }

    public static GuestInfoManager getGuestInfoManager() {
        return JpaManager.getObjectFromSpringContext(GuestInfoManager.class);
    }

    public static SmartcmsSettingManager getSmartcmsSettingManager() {
        return JpaManager.getObjectFromSpringContext(SmartcmsSettingManager.class);
    }

    public static WelcomeManager getWelcomeManager() {
        return JpaManager.getObjectFromSpringContext(WelcomeManager.class);
    }

    public static DevicesManager getDevicesManager() {
        return JpaManager.getObjectFromSpringContext(DevicesManager.class);
    }

    public static TriggerInfoManager getTriggerInfoManager() {
        return JpaManager.getObjectFromSpringContext(TriggerInfoManager.class);
    }

    public static TriggerHistoryManager getTriggerHistoryManager() {
        return JpaManager.getObjectFromSpringContext(TriggerHistoryManager.class);
    }

    public static MessageManager getMessageManager() {
        return JpaManager.getObjectFromSpringContext(MessageManager.class);
    }

    public static JobLogManager getJobLogManager() {
        return JpaManager.getObjectFromSpringContext(JobLogManager.class);
    }

    public static MyChoiceTemplateManager getMyChoiceTemplateManager() {
        return JpaManager.getObjectFromSpringContext(MyChoiceTemplateManager.class);
    }

    public static FutureCheckInManager getFutureCheckInManager() {
        return JpaManager.getObjectFromSpringContext(FutureCheckInManager.class);
    }

    public static RoomNotificationManager getRoomNotificationManager() {
        return JpaManager.getObjectFromSpringContext(RoomNotificationManager.class);
    }

    public static WakeupInfoManager getWakeupInfoManager() {
        return JpaManager.getObjectFromSpringContext(WakeupInfoManager.class);
    }

    public static VersionNoticeManager getVersionNoticeManager() {
        return JpaManager.getObjectFromSpringContext(VersionNoticeManager.class);
    }

    public static ReceptionClientManager getReceptionClientManager() {
        return JpaManager.getObjectFromSpringContext(ReceptionClientManager.class);
    }

    public static CastServerSettingManager getCastServerSettingManager() {
        return JpaManager.getObjectFromSpringContext(CastServerSettingManager.class);
    }

    public static CastAnalyticalDataManager getCastAnalyticalDataManager() {
        return JpaManager.getObjectFromSpringContext(CastAnalyticalDataManager.class);
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
        ArrayList<String> paramList = new ArrayList<String>();
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
        sql.append(JpaManager.exportOrderByAndPageSql(searchParam));
        Object[] paramsArray = paramList.toArray(new Object[0]);
        JSONArray pageDataArray = JpaManager.getResultsetAsArray(sql.toString(), paramsArray);
        data.put("rows", pageDataArray);
        data.put("current", searchParam.getCurrentPage());
        data.put("rowCount", searchParam.getRowCount());
        data.put("total", JpaManager.countRecordsInDB(sqlCount.toString(), paramsArray));
        return data;
    }

    /*
     * Exception decompiling
     */
    public static int executeManipulationSql(String executeSql, Object ... params) throws SQLException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public static int countRecordsInDB(String sqlCountSetting, Object ... params) throws SQLException {
        int count = 0;
        try (Connection conn = JpaManager.getConnection();
             PreparedStatement stat = conn.prepareStatement(sqlCountSetting);){
            for (int i = 1; i <= params.length; ++i) {
                stat.setObject(i, params[i - 1]);
            }
            try (ResultSet rs = stat.executeQuery();){
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        }
        return count;
    }

    /*
     * Exception decompiling
     */
    public static JSONArray getResultsetAsArray(String pstmSql, Object ... params) throws SQLException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }
}

