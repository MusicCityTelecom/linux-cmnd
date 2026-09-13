package be.tpvision.smartcontrol.rest.mappers.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.general.ClockParameter;
import java.time.LocalTime;

public class ClockParameterMapper {
   private ClockParameterMapper() {
   }

   public static String toClockParameterString(final ClockParameter clockParameter) {
      LocalTime localTime = LocalTime.of(clockParameter.getHour(), clockParameter.getMinute());
      return String.valueOf(localTime);
   }

   public static ClockParameter toClockParameter(final String clockParameterViewString) {
      String[] clockParameterViewArray = clockParameterViewString.split(":");
      return new ClockParameter(Integer.valueOf(clockParameterViewArray[0]), Integer.valueOf(clockParameterViewArray[1]));
   }
}
