/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.weather;

import com.tpvision.smartinstall.dao.core.Weather;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.WeatherManager;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.weather.WeatherService;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeatherServiceImpl
implements WeatherService {
    private static final Logger LOG = LoggerFactory.getLogger(WeatherServiceImpl.class);
    private static final String SERVER_URI = "http://meteo.dotscreen.com/api/net-tv-2/observations/#.js";
    private int weatherFetchCount = 0;
    private static final int CONN_TIMEOUT = 15000;
    private static final int SOKECT_TIMEOUT = 20000;
    private static final String DEFAULT_FORECAST = "callback({\"description\":\"Unknown\",\"utcOffset\":120,\"forecasts\":[{\"day\":\"wo\",\"pictoId\":0,\"temperatures\":{\"min\":\"0\u00b0\",\"max\":\"0\u00b0\",\"observed\":\"0\u00b0C\"},\"daytime\":true},{\"day\":\"do\",\"pictoId\":0,\"temperatures\":{\"min\":\"0\u00b0\",\"max\":\"0\u00b0\"}},{\"day\":\"vr\",\"pictoId\":0,\"temperatures\":{\"min\":\"0\u00b0\",\"max\":\"0\u00b0\"}},{\"day\":\"za\",\"pictoId\":0,\"temperatures\":{\"min\":\"0\u00b0\",\"max\":\"0\u00b0\"}},{\"day\":\"zo\",\"pictoId\":0,\"temperatures\":{\"min\":\"0\u00b0\",\"max\":\"0\u00b0\"}}]})";

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
        }
        catch (ParseException e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    public Weather refreshCurrentWeather(String geonameId, String lang) {
        if (StringUtils.isEmpty(geonameId)) {
            LOG.warn("WeatherServiceImpl : get failure, geonameId is null");
            return null;
        }
        String forcasts = this.getWeatherForcasts(geonameId, lang);
        WeatherManager weatherManager = JpaManager.getWeatherManager();
        Weather weather = weatherManager.findByCityId(geonameId);
        return this.saveWeatherDataToDb(weatherManager, weather, forcasts, geonameId, lang);
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
            LOG.info("WeatherServiceImpl : save success, geonameId={}", (Object)geonameId);
        } else {
            weather.setForecasts(DEFAULT_FORECAST);
            weather.setErrorMsg("Cant get current weather info!");
            weather.setStatus(0);
            weatherManager.saveWeather(weather);
            LOG.warn("WeatherServiceImpl : get failure, geonameId={}", (Object)geonameId);
        }
        return weather;
    }

    public void refreshWeather() {
        WeatherManager weatherManager = JpaManager.getWeatherManager();
        List<Weather> list = weatherManager.findAllWeather();
        for (Weather weather : list) {
            String geonameId = weather.getCityId();
            if (!StringUtils.isNumeric(geonameId)) continue;
            String lang = weather.getLang();
            String forcasts = this.getWeatherForcasts(geonameId, lang);
            this.saveWeatherDataToDb(weatherManager, weather, forcasts, geonameId, lang);
        }
    }

    private String getWeatherForcasts(String geonameId, String lang) {
        String forcast = null;
        ++this.weatherFetchCount;
        String url = SERVER_URI.replace("#", geonameId);
        if (StringUtils.isNotEmpty(lang)) {
            url = url + "?lang=" + lang;
        }
        try (CloseableHttpClient httpClient = HttpClients.createDefault();){
            HttpEntity entity1;
            String ret;
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(15000).setConnectionRequestTimeout(5000).build());
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200 && StringUtils.isNotEmpty(ret = EntityUtils.toString(entity1 = response.getEntity(), StandardCharsets.UTF_8)) && ret.startsWith("callback({") && ret.endsWith("})")) {
                LOG.info("WeatherServiceImpl : url:{} get success, response: {}", (Object)url, (Object)ret);
                forcast = ret;
            }
        }
        catch (Exception e) {
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

