package be.tpvision.smartcontrol.rest.mappers.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.TemperatureSensor;
import be.tpvision.smartcontrol.messages.mappers.miscellaneous.temperature_sensor.ToTemperatureSensorViewModelMessages;
import org.springframework.util.Assert;

public class TemperatureSensorMapper {
   private TemperatureSensorMapper() {
   }

   public static int toInteger(final TemperatureSensor temperatureSensor) {
      Assert.notNull(temperatureSensor, ToTemperatureSensorViewModelMessages.TEMPERATURE_SENSOR_CAN_NOT_BE_NULL);
      int temperatureOne = temperatureSensor.getTemperatureOne();
      Integer temperatureTwo = temperatureSensor.getTemperatureTwo();
      return temperatureTwo == null ? temperatureOne : Math.max(temperatureOne, temperatureTwo);
   }
}
