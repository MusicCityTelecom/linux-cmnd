package com.tpvision.smartinstall.weather;

import com.tpvision.smartinstall.util.TpvTimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeatherTask extends TpvTimerTask {
   private static final Logger LOG = LoggerFactory.getLogger(WeatherTask.class);
   private int count = 0;

   @Override
   public void tryRun() {
      this.count++;
      LOG.info("Weather task running...count={}", this.count);
      WeatherServiceImpl service = new WeatherServiceImpl();
      service.refreshWeather();
      LOG.info("Weather task finished.");
   }
}
