/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.annotation.WebServlet
 */
package com.tpvision.smartinstall.servlet;

import com.google.gson.Gson;
import com.tpvision.smartinstall.dao.core.Weather;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.Location;
import com.tpvision.smartinstall.util.Utils;
import com.tpvision.smartinstall.weather.WeatherDataParser;
import com.tpvision.smartinstall.weather.WeatherServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(value={"/weather"})
public class WeatherServiceServlet
extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(WeatherServiceServlet.class);

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        int current = Integer.parseInt(request.getParameter("current"));
        int rowCount = Integer.parseInt(request.getParameter("rowCount"));
        if ("localWeather".equals(type)) {
            this.queryWeatherList(request, response, current, rowCount);
        } else if ("weatherForcast".equals(type)) {
            this.queryWeatherForcast(response, current, rowCount);
        }
    }

    private void queryWeatherForcast(HttpServletResponse response, int current, int rowCount) {
        JSONObject data = JpaManager.getWeatherManager().findWeatherForcastPageData(current, rowCount);
        Utils.writeToResponse(data.toString(), "text/html;charset=UTF-8", response);
    }

    private void queryWeatherList(HttpServletRequest request, HttpServletResponse response, int current, int rowCount) {
        JSONObject data = new JSONObject();
        String cloneDataName = request.getParameter("sort[cloneDataName]");
        String timeOfDownload = request.getParameter("sort[timeOfDownload]");
        String geonameId = request.getParameter("sort[geonameId]");
        JSONArray array = new JSONArray();
        JSONObject jsonObj = null;
        JSONArray array4day = new JSONArray();
        String localGeonameId = "2759794";
        try {
            String locationData = FileUtils.readFileToString(new File(CommonConstants.LOCATION_MANAGER_STORE), StandardCharsets.UTF_8);
            LOG.info("locationData ={}", (Object)locationData);
            Location location = new Gson().fromJson(locationData, Location.class);
            if (null != location) {
                localGeonameId = location.getGeonameid();
            }
            LOG.info("localGeonameId ={}", (Object)localGeonameId);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        WeatherServiceImpl wsi = new WeatherServiceImpl();
        Weather weather = wsi.process(localGeonameId, null);
        jsonObj = new JSONObject();
        LOG.info("SmartInstall:{}", (Object)weather.getForecasts());
        WeatherDataParser siwdp = new WeatherDataParser(weather.getForecasts());
        List<WeatherDataParser.Forecast> siforecast = siwdp.wd.getForecast();
        WeatherDataParser.Forecast[] siday = siforecast.toArray(new WeatherDataParser.Forecast[siforecast.size()]);
        jsonObj.put("cloneDataName", "SmartInstall");
        jsonObj.put("timeOfDownload", weather.getFetchTime());
        jsonObj.put("geonameId", localGeonameId);
        jsonObj.put("iconName", siday[0].getWeatherIconName(siday[0].getDayTime()));
        jsonObj.put("isSuccess", weather.getStatus());
        jsonObj.put("description", siwdp.wd.getDescription());
        jsonObj.put("observed", siday[0].getTemperatures().getObserved());
        jsonObj.put("weatherDescription", siday[0].getWeatherDescription(siday[0].getDayTime()));
        for (int i = 1; i < 5; ++i) {
            JSONObject jsonObj4day = new JSONObject();
            jsonObj4day.put("weatherIconName", siday[i].getWeatherIconName(true));
            jsonObj4day.put("temperaturesMax", siday[i].getTemperatures().getMax());
            jsonObj4day.put("temperaturesMin", siday[i].getTemperatures().getMin());
            jsonObj4day.put("weatherDescription", siday[i].getWeatherDescription(true));
            jsonObj4day.put("day", siday[i].getDay());
            array4day.put(jsonObj4day);
        }
        jsonObj.put("forcast4day", array4day.toString());
        array.put(jsonObj);
        if (null != cloneDataName) {
            array = Utils.sortSimpleJSONArray(array, "cloneDataName", "string", cloneDataName);
        } else if (null != timeOfDownload) {
            array = Utils.sortSimpleJSONArray(array, "timeOfDownload", "int", timeOfDownload);
        } else if (null != geonameId) {
            array = Utils.sortSimpleJSONArray(array, "geonameId", "string", geonameId);
        }
        data.put("current", current);
        data.put("rowCount", rowCount);
        data.put("total", 1);
        data.put("rows", array);
        Utils.writeToResponse(data.toString(), "text/html;charset=UTF-8", response);
    }
}

