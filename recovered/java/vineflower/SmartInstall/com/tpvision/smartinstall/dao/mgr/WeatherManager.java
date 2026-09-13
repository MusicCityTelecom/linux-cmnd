package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.dao.WeatherRepository;
import com.tpvision.smartinstall.dao.core.Weather;
import com.tpvision.smartinstall.weather.WeatherDataParser;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherManager {
   private static final Logger LOG = LoggerFactory.getLogger(WeatherManager.class);
   @Autowired
   private WeatherRepository weatherRepository;

   public List<Weather> findAllWeather() {
      return this.weatherRepository.findAll();
   }

   public Weather findByCityId(String cityId) {
      return this.weatherRepository.findById(cityId).orElse(null);
   }

   public Weather saveWeather(Weather weather) {
      return this.weatherRepository.save(weather);
   }

   public JSONObject findWeatherForcastPageData(int current, int rowCount) {
      JSONObject data = new JSONObject();
      JSONArray array4day = new JSONArray();
      StringBuilder sql = new StringBuilder("select * from config_view");
      LOG.info("logQuery={}", sql);
      int count = 0;

      try (
         Connection conn = JpaManager.getConnection();
         Statement stat = conn.createStatement();
         ResultSet rs = stat.executeQuery(sql.toString());
      ) {
         ResultSetMetaData metaData = rs.getMetaData();
         int columnCount = metaData.getColumnCount();
         JSONArray array = new JSONArray();
         JSONObject jsonObj = null;

         while (rs.next()) {
            boolean isValid = true;
            jsonObj = new JSONObject();

            for (int i = 1; i <= columnCount; i++) {
               String columnName = metaData.getColumnLabel(i);
               String value = rs.getString(columnName);
               if ("geoname_id".equals(columnName) && null == value) {
                  LOG.info("geoname is null");
                  isValid = false;
                  break;
               }
            }

            if (isValid) {
               count++;

               for (int i = 1; i <= columnCount; i++) {
                  String columnName = metaData.getColumnLabel(i);
                  String value = rs.getString(columnName);
                  jsonObj.put(columnName, value);
                  if ("forecasts".equals(columnName) && null != value) {
                     WeatherDataParser siwdp = new WeatherDataParser(value);
                     List<WeatherDataParser.Forecast> siforecast = siwdp.wd.getForecast();
                     WeatherDataParser.Forecast[] siday = siforecast.toArray(new WeatherDataParser.Forecast[siforecast.size()]);
                     jsonObj.put("iconName", siday[0].getWeatherIconName(siday[0].getDayTime()));
                     jsonObj.put("description", siwdp.wd.getDescription());
                     jsonObj.put("observed", siday[0].getTemperatures().getObserved());
                     jsonObj.put("weatherDescription", siday[0].getWeatherDescription(siday[0].getDayTime()));

                     for (int j = 1; j < 5; j++) {
                        JSONObject jsonObj4day = new JSONObject();
                        jsonObj4day.put("weatherIconName", siday[j].getWeatherIconName(true));
                        jsonObj4day.put("temperaturesMax", siday[j].getTemperatures().getMax());
                        jsonObj4day.put("temperaturesMin", siday[j].getTemperatures().getMin());
                        jsonObj4day.put("weatherDescription", siday[j].getWeatherDescription(true));
                        jsonObj4day.put("day", siday[j].getDay());
                        array4day.put(jsonObj4day);
                        JSONObject var78 = null;
                     }

                     jsonObj.put("forcast4day", array4day.toString());
                  }

                  if (jsonObj.has("status")) {
                     if (jsonObj.get("status") != null) {
                        jsonObj.put("state", jsonObj.getInt("status"));
                     }

                     jsonObj.remove("status");
                  }
               }

               array.put(jsonObj);
            }
         }

         data.put("current", current);
         data.put("rowCount", rowCount);
         data.put("total", count);
         data.put("rows", array);
      } catch (SQLException e) {
         LOG.error(e.getMessage(), e);
      }

      return data;
   }
}
