package be.tpvision.smartcontrol.domain.comparators;

import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.TemperatureSensor;
import java.util.Comparator;

public class TemperatureSensorComparator implements Comparator<TemperatureSensor> {
   public int compare(final TemperatureSensor temperatureSensor1, final TemperatureSensor temperatureSensor2) {
      int temperatureOne = temperatureSensor1.getTemperatureOne();
      Integer temperatureTwo = temperatureSensor1.getTemperatureTwo();
      int firstTemperature;
      if (temperatureTwo != null) {
         firstTemperature = Math.max(temperatureOne, temperatureTwo);
      } else {
         firstTemperature = temperatureOne;
      }

      temperatureOne = temperatureSensor2.getTemperatureOne();
      temperatureTwo = temperatureSensor2.getTemperatureTwo();
      int secondTemperature;
      if (temperatureTwo != null) {
         secondTemperature = Math.max(temperatureOne, temperatureTwo);
      } else {
         secondTemperature = temperatureOne;
      }

      return Integer.compare(firstTemperature, secondTemperature);
   }
}
