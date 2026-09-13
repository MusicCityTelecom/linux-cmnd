/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.weather;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeatherDataParser {
    private static final Logger LOG = LoggerFactory.getLogger(WeatherDataParser.class);
    public WeatherData wd = null;
    private String temp = null;

    public WeatherDataParser(String data) {
        this.temp = data.substring("callback(".length());
        this.temp = this.temp.substring(0, this.temp.length() - 1);
        LOG.info(this.temp);
        Gson gson = new Gson();
        this.wd = gson.fromJson(this.temp, WeatherData.class);
    }

    static class WeatherConstant {
        protected static final Map<Integer, String> WEATHER_ICON_DAY_MAP = new HashMap<Integer, String>();
        protected static final Map<Integer, String> WEATHER_ICON_NIGHT_MAP;
        protected static final Map<Integer, String> WEATHER_DESC_DAY_MAP;
        protected static final Map<Integer, String> WEATHER_DESC_NIGHT_MAP;

        WeatherConstant() {
        }

        static {
            WEATHER_ICON_DAY_MAP.put(0, "icon_weather_12_night_unknown_98x98_hl.png");
            WEATHER_ICON_DAY_MAP.put(1, "icon_weather_2_day_partly_cloudy_98x98_hl.png");
            WEATHER_ICON_DAY_MAP.put(2, "icon_weather_3_day_sunny_with_some_clouds_98x98_hl.png");
            WEATHER_ICON_DAY_MAP.put(3, "icon_weather_3_day_sunny_with_some_clouds_98x98_hl.png");
            WEATHER_ICON_DAY_MAP.put(4, "icon_weather_4_day_thunder_storms_98x98_hl.png");
            WEATHER_ICON_DAY_MAP.put(5, "icon_weather_5_day_fog_98x98_hl.png");
            WEATHER_ICON_DAY_MAP.put(6, "icon_weather_6_day_rain_98x98_hl.png");
            WEATHER_ICON_DAY_MAP.put(7, "icon_weather_7_day_sunny_98x98_hl.png");
            WEATHER_ICON_DAY_MAP.put(8, "icon_weather_8_day_snowfall_98x98_hl.png");
            WEATHER_ICON_DAY_MAP.put(9, "icon_weather_9_day_mixed_rain_and_snow_98x98_hl.png");
            WEATHER_ICON_DAY_MAP.put(10, "icon_weather_10_day_overcast_98x98_hl.png");
            WEATHER_ICON_DAY_MAP.put(11, "icon_weather_11_day_showers_98x98_hl.png");
            WEATHER_ICON_NIGHT_MAP = new HashMap<Integer, String>();
            WEATHER_ICON_NIGHT_MAP.put(0, "icon_weather_12_night_unknown_98x98_hl.png");
            WEATHER_ICON_NIGHT_MAP.put(1, "icon_weather_13_night_mostly_cloudy_98x98_hl.png");
            WEATHER_ICON_NIGHT_MAP.put(2, "icon_weather_14_night_mostly_clear_98x98_hl.png");
            WEATHER_ICON_NIGHT_MAP.put(3, "icon_weather_14_night_mostly_clear_98x98_hl.png");
            WEATHER_ICON_NIGHT_MAP.put(4, "icon_weather_15_night_thunder_storms_98x98_hl.png");
            WEATHER_ICON_NIGHT_MAP.put(5, "icon_weather_16_night_fog_98x98_hl.png");
            WEATHER_ICON_NIGHT_MAP.put(6, "icon_weather_17_night_rain_98x98_hl.png");
            WEATHER_ICON_NIGHT_MAP.put(7, "icon_weather_18_night_clear_98x98_hl.png");
            WEATHER_ICON_NIGHT_MAP.put(8, "icon_weather_19_night_snowfall_98x98_hl.png");
            WEATHER_ICON_NIGHT_MAP.put(9, "icon_weather_20_night_mixed_rain_and_snow_98x98_hl.png");
            WEATHER_ICON_NIGHT_MAP.put(10, "icon_weather_21_night_overcast_98x98_hl.png");
            WEATHER_ICON_NIGHT_MAP.put(11, "icon_weather_22_night_showers_98x98_hl.png");
            WEATHER_DESC_DAY_MAP = new HashMap<Integer, String>();
            WEATHER_DESC_DAY_MAP.put(0, "Unknown");
            WEATHER_DESC_DAY_MAP.put(1, "Partly Cloudy");
            WEATHER_DESC_DAY_MAP.put(2, "Mostly sunny");
            WEATHER_DESC_DAY_MAP.put(3, "Mostly sunny");
            WEATHER_DESC_DAY_MAP.put(4, "Thunder Storms");
            WEATHER_DESC_DAY_MAP.put(5, "Fog");
            WEATHER_DESC_DAY_MAP.put(6, "Rain");
            WEATHER_DESC_DAY_MAP.put(7, "Sunny");
            WEATHER_DESC_DAY_MAP.put(8, "Snow");
            WEATHER_DESC_DAY_MAP.put(9, "Mixed rain and snow");
            WEATHER_DESC_DAY_MAP.put(10, "cloudy");
            WEATHER_DESC_DAY_MAP.put(11, "Showers");
            WEATHER_DESC_NIGHT_MAP = new HashMap<Integer, String>();
            WEATHER_DESC_NIGHT_MAP.put(0, "Unknown");
            WEATHER_DESC_NIGHT_MAP.put(1, "Partly Cloudy");
            WEATHER_DESC_NIGHT_MAP.put(2, "Mostly clear");
            WEATHER_DESC_NIGHT_MAP.put(3, "Mostly clear");
            WEATHER_DESC_NIGHT_MAP.put(4, "Thunder Storms");
            WEATHER_DESC_NIGHT_MAP.put(5, "Fog");
            WEATHER_DESC_NIGHT_MAP.put(6, "Rain");
            WEATHER_DESC_NIGHT_MAP.put(7, "clear");
            WEATHER_DESC_NIGHT_MAP.put(8, "Snow");
            WEATHER_DESC_NIGHT_MAP.put(9, "Mixed rain and snow");
            WEATHER_DESC_NIGHT_MAP.put(10, "Cloudy");
            WEATHER_DESC_NIGHT_MAP.put(11, "Showers");
        }
    }

    public class Temperatures {
        public String min = null;
        public String max = null;
        public String observed = null;

        public void setMin(String min) {
            this.min = min;
        }

        public String getMin() {
            return this.min;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public String getMax() {
            return this.max;
        }

        public void setObserved(String obs) {
            this.observed = obs;
        }

        public String getObserved() {
            return this.observed;
        }
    }

    public class Forecast {
        public String day = null;
        public int pictoId = 0;
        public Temperatures temperatures = null;
        public boolean daytime = false;

        public String getDay() {
            return this.day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public int getPictoId() {
            return this.pictoId;
        }

        public void setPictoId(int id) {
            this.pictoId = id;
        }

        public Temperatures getTemperatures() {
            return this.temperatures;
        }

        public void setTemperatures(Temperatures temp) {
            this.temperatures = temp;
        }

        public boolean getDayTime() {
            return this.daytime;
        }

        public void setDayTime(boolean dt) {
            this.daytime = dt;
        }

        public String getWeatherIconName(boolean daytime) {
            if (daytime) {
                return WeatherConstant.WEATHER_ICON_DAY_MAP.get(this.getPictoId());
            }
            return WeatherConstant.WEATHER_ICON_NIGHT_MAP.get(this.getPictoId());
        }

        public String getWeatherDescription(boolean daytime) {
            if (daytime) {
                return WeatherConstant.WEATHER_DESC_DAY_MAP.get(this.getPictoId());
            }
            return WeatherConstant.WEATHER_DESC_NIGHT_MAP.get(this.getPictoId());
        }
    }

    public class WeatherData {
        public String description = null;
        public String utcOffset = null;
        public List<Forecast> forecasts;

        public void setDescription(String desc) {
            this.description = desc;
        }

        public String getDescription() {
            return this.description;
        }

        public void setUtcOffset(String offset) {
            this.utcOffset = offset;
        }

        public String getUtcOffset() {
            return this.utcOffset;
        }

        public void setForecast(List<Forecast> fc) {
            this.forecasts = fc;
        }

        public List<Forecast> getForecast() {
            return this.forecasts;
        }
    }
}

