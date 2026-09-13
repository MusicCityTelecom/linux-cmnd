package be.tpvision.smartcontrol.rest.mappers.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.general.DateParameter;

public class DateParameterMapper {
   private DateParameterMapper() {
   }

   public static String toDateParameterViewString(final DateParameter dateParameter) {
      return dateParameter.getYear()
         + "-"
         + (dateParameter.getMonth() >= 10 ? dateParameter.getMonth() : "0" + dateParameter.getMonth())
         + "-"
         + (dateParameter.getDay() >= 10 ? dateParameter.getDay() : "0" + dateParameter.getDay());
   }

   public static DateParameter toDateParameter(final String dateParameterViewString) {
      String[] dateParameterViewArray = dateParameterViewString.split("-");
      return new DateParameter(
         Integer.valueOf(dateParameterViewArray[2]), Integer.valueOf(dateParameterViewArray[1]), Integer.valueOf(dateParameterViewArray[0])
      );
   }
}
