package com.tpvision.smartinstall.weather;

import com.tpvision.smartinstall.dao.core.Weather;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.WeatherManager;
import com.tpvision.smartinstall.util.TpvDateUtils;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeatherServiceImpl implements WeatherService {
   private static final Logger LOG = LoggerFactory.getLogger(WeatherServiceImpl.class);
   private static final String SERVER_URI = "http://meteo.dotscreen.com/api/net-tv-2/observations/#.js";
   private int weatherFetchCount = 0;
   private static final int CONN_TIMEOUT = 15000;
   private static final int SOKECT_TIMEOUT = 20000;
   private static final String DEFAULT_FORECAST = "callback({\"description\":\"Unknown\",\"utcOffset\":120,\"forecasts\":[{\"day\":\"wo\",\"pictoId\":0,\"temperatures\":{\"min\":\"0°\",\"max\":\"0°\",\"observed\":\"0°C\"},\"daytime\":true},{\"day\":\"do\",\"pictoId\":0,\"temperatures\":{\"min\":\"0°\",\"max\":\"0°\"}},{\"day\":\"vr\",\"pictoId\":0,\"temperatures\":{\"min\":\"0°\",\"max\":\"0°\"}},{\"day\":\"za\",\"pictoId\":0,\"temperatures\":{\"min\":\"0°\",\"max\":\"0°\"}},{\"day\":\"zo\",\"pictoId\":0,\"temperatures\":{\"min\":\"0°\",\"max\":\"0°\"}}]})";

   @Override
   public Weather process(String geonameId, String lang) {
      Weather weather = JpaManager.getWeatherManager().findByCityId(geonameId);
      if (null == weather || this.isExpired24H(weather.getFetchTime()) || weather.getStatus() == 0) {
         weather = this.refreshCurrentWeather(geonameId, lang);
      }

      return weather;
   }

   private boolean isExpired24H(String fetchTime) {
      if (StringUtils.isEmpty(fetchTime)) {
         return true;
      }

      try {
         long diff = (System.currentTimeMillis() - this.getWeatherTimeFormat().parse(fetchTime).getTime()) / 60000L / 60L / 24L;
         return diff >= 1L;
      } catch (ParseException e) {
         LOG.error(e.getMessage(), e);
         return false;
      }
   }

   public Weather refreshCurrentWeather(String geonameId, String lang) {
      if (StringUtils.isEmpty(geonameId)) {
         LOG.warn("WeatherServiceImpl : get failure, geonameId is null");
         return null;
      } else {
         String forcasts = this.getWeatherForcasts(geonameId, lang);
         WeatherManager weatherManager = JpaManager.getWeatherManager();
         Weather weather = weatherManager.findByCityId(geonameId);
         return this.saveWeatherDataToDb(weatherManager, weather, forcasts, geonameId, lang);
      }
   }

   private Weather saveWeatherDataToDb(WeatherManager weatherManager, Weather weather, String forcasts, String geonameId, String lang) {
      if (weather == null) {
         weather = new Weather();
         weather.setCityId(geonameId);
         weather.setLang(lang);
      }

      String nowtime = this.getWeatherTimeFormat().format(new Date());
      weather.setFetchTime(nowtime);
      weather.setLastFetchTime(nowtime);
      if (forcasts != null) {
         weather.setForecasts(forcasts);
         weather.setErrorMsg("");
         weather.setStatus(1);
         weatherManager.saveWeather(weather);
         LOG.info("WeatherServiceImpl : save success, geonameId={}", geonameId);
      } else {
         weather.setForecasts(
            "callback({\"description\":\"Unknown\",\"utcOffset\":120,\"forecasts\":[{\"day\":\"wo\",\"pictoId\":0,\"temperatures\":{\"min\":\"0°\",\"max\":\"0°\",\"observed\":\"0°C\"},\"daytime\":true},{\"day\":\"do\",\"pictoId\":0,\"temperatures\":{\"min\":\"0°\",\"max\":\"0°\"}},{\"day\":\"vr\",\"pictoId\":0,\"temperatures\":{\"min\":\"0°\",\"max\":\"0°\"}},{\"day\":\"za\",\"pictoId\":0,\"temperatures\":{\"min\":\"0°\",\"max\":\"0°\"}},{\"day\":\"zo\",\"pictoId\":0,\"temperatures\":{\"min\":\"0°\",\"max\":\"0°\"}}]})"
         );
         weather.setErrorMsg("Cant get current weather info!");
         weather.setStatus(0);
         weatherManager.saveWeather(weather);
         LOG.warn("WeatherServiceImpl : get failure, geonameId={}", geonameId);
      }

      return weather;
   }

   public void refreshWeather() {
      WeatherManager weatherManager = JpaManager.getWeatherManager();

      for (Weather weather : weatherManager.findAllWeather()) {
         String geonameId = weather.getCityId();
         if (StringUtils.isNumeric(geonameId)) {
            String lang = weather.getLang();
            String forcasts = this.getWeatherForcasts(geonameId, lang);
            this.saveWeatherDataToDb(weatherManager, weather, forcasts, geonameId, lang);
         }
      }
   }

   private String getWeatherForcasts(String geonameId, String lang) {
      String forcast = null;
      this.weatherFetchCount++;
      String url = "http://meteo.dotscreen.com/api/net-tv-2/observations/#.js".replace("#", geonameId);
      if (StringUtils.isNotEmpty(lang)) {
         url = url + "?lang=" + lang;
      }

      try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
         HttpGet httpGet = new HttpGet(url);
         httpGet.setConfig(RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(15000).setConnectionRequestTimeout(5000).build());
         HttpResponse response = httpClient.execute(httpGet);
         if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity1 = response.getEntity();
            String ret = EntityUtils.toString(entity1, StandardCharsets.UTF_8);
            if (StringUtils.isNotEmpty(ret) && ret.startsWith("callback({") && ret.endsWith("})")) {
               LOG.info("WeatherServiceImpl : url:{} get success, response: {}", url, ret);
               forcast = ret;
            }
         }
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }

      if (forcast == null && this.weatherFetchCount < 3) {
         forcast = this.getWeatherForcasts(geonameId, lang);
      }

      return forcast;
   }

   private SimpleDateFormat getWeatherTimeFormat() {
      return TpvDateUtils.getSimpleDateFormatWithEnglishLocale("HH:mm, yyyy/MM/dd");
   }
}
