/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.japit.webservices;

import com.tpvision.smartinstall.dao.core.RoomNotification;
import com.tpvision.smartinstall.dao.core.Weather;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.RoomNotificationManager;
import com.tpvision.smartinstall.japit.webservices.WebServiceCommandHandler;
import com.tpvision.smartinstall.pms.PmsUtils;
import com.tpvision.smartinstall.schedule.CmndMetricsTask;
import com.tpvision.smartinstall.util.TpvStringUtils;
import com.tpvision.smartinstall.weather.WeatherServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationControlHandler
extends WebServiceCommandHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationControlHandler.class);

    @Override
    public String execute() {
        JSONArray applicationAttributesValue;
        if ("Request".equalsIgnoreCase(this.cmdType)) {
            return this.handleWeatherQueryRequest();
        }
        if ("Response".equalsIgnoreCase(this.cmdType) && (applicationAttributesValue = this.commandDetails.optJSONArray("ApplicationAttributesValue")) != null) {
            for (int i = 0; i < applicationAttributesValue.length(); ++i) {
                JSONObject appAttribute = applicationAttributesValue.getJSONObject(i);
                if (!StringUtils.equalsIgnoreCase(appAttribute.optString("ApplicationName"), "Alarm")) continue;
                this.handleAlarmAppNotice(appAttribute.optJSONObject("ApplicationAttributes"));
            }
        }
        return "";
    }

    private void handleAlarmAppNotice(JSONObject applicationAttributes) {
        if (applicationAttributes == null) {
            return;
        }
        if (StringUtils.equalsIgnoreCase("AlarmTimedOut", applicationAttributes.optString("AlarmUserResponse"))) {
            LOG.info("log overslept alarm notification");
            RoomNotificationManager roomNotificationManager = JpaManager.getRoomNotificationManager();
            roomNotificationManager.save(this.device.getTvroomid(), 1, RoomNotification.EventType.ALARM_NOT_CONFIRM, "Wake up alarm not confirmed by guest");
            CmndMetricsTask.writePMSInfoToMetricsLog(this.device, "alarm_timed_out");
        } else if (StringUtils.equalsIgnoreCase("AlarmSwitchedOff", applicationAttributes.optString("AlarmUserResponse"))) {
            LOG.info("alarm notification ok");
            PmsUtils.responseWakeupStatus(this.device.getTvroomid(), true);
        }
    }

    private String handleWeatherQueryRequest() {
        Object reqs = this.commandDetails.opt("RequestApplicationAttributesValueDetails");
        if (reqs == null) {
            return "";
        }
        String data = null;
        JSONArray reqJsonArr = (JSONArray)reqs;
        JSONObject req = (JSONObject)reqJsonArr.get(0);
        String appName = req.getString("ApplicationName");
        String appType = req.getString("ApplicationType");
        JSONArray attrs = req.getJSONArray("RequestListForApplicationAttributesValue");
        if ("Weather".equals(appName) && "Native".equals(appType) && !attrs.isEmpty() && "WeatherForecast".equals(attrs.get(0).toString())) {
            JSONObject appAttrs = req.getJSONObject("ApplicationAttributes");
            Object geonameId = appAttrs.opt("GeonameLocationID");
            Object lang = appAttrs.opt("GuestLanguage");
            if (null != geonameId && StringUtils.isNotEmpty(geonameId.toString())) {
                WeatherServiceImpl ws = new WeatherServiceImpl();
                Weather result = ws.process(geonameId.toString(), null == lang ? null : lang.toString());
                String forecasts = "";
                if (result != null && result.getForecasts() != null && (forecasts = result.getForecasts()).startsWith("callback({") && forecasts.endsWith("})")) {
                    forecasts = forecasts.substring(9, forecasts.length() - 1);
                }
                int minCounter = TpvStringUtils.countStr(forecasts, "min");
                int maxCounter = TpvStringUtils.countStr(forecasts, "max");
                int observedCounter = TpvStringUtils.countStr(forecasts, "observed");
                data = this.responseApplicationControllWebService(geonameId.toString(), null == lang ? null : lang.toString(), forecasts);
                data = TpvStringUtils.removeNL(data);
                for (int i = 0; i < minCounter + maxCounter + observedCounter; ++i) {
                    data = data + " ";
                }
            }
        }
        return data;
    }

    private String responseApplicationControllWebService(String geonameId, String lang, String data) {
        return "{ \t\"Svc\" : \"WebServices\", \t\"SvcVer\": \"3.0\",\t\"Cookie\": 299,   \"CmdType\": \"Response\",\t\"Fun\" : \"ApplicationService\", \t\"CommandDetails\" : \t{\t\t\"ApplicationAttributesValue\" : [\t    {       \t\"ApplicationName\": \"Weather\",\t\t\t\"ApplicationType\": \"Native\",\t\t\t\"ApplicationAttributes\": {\t  \t\t\t\"GeonameLocationID\": " + geonameId + ",  \t\t\t\t\"GuestLanguage\": \"" + lang + "\",  \t\t\t\t\"WeatherForecast\": {               \t\"ForecastProvider\" : \"MeteoWeather\",\t\t\t\t\t\"Forecast\" : { \t\t\t\t\t\t\"Blob\":" + data + "  \t\t\t\t\t}     \t\t\t}\t\t\t}\t\t}\t\t]\t}}";
    }
}

