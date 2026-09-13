package be.tpvision.smartcontrol.codecs.sicp207.general;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.general.DateParameter;

public class DateParameterCodec extends Codec<DateParameter> {
   private static DateParameterCodec dateParameterCodec;

   private DateParameterCodec() {
      super(DateParameter.class);
   }

   public static synchronized DateParameterCodec getInstance() {
      if (dateParameterCodec == null) {
         dateParameterCodec = new DateParameterCodec();
      }

      return dateParameterCodec;
   }

   public byte[] toProtocol(final DateParameter dateParameter) {
      byte day = (byte)dateParameter.getDay();
      byte month = (byte)dateParameter.getMonth();
      int year = dateParameter.getYear();
      byte highPart = (byte)(year / 100);
      byte lowPart = (byte)(year % 100);
      return new byte[]{day, month, highPart, lowPart};
   }

   public DateParameter toDomain(final byte[] bytes) {
      if (bytes != null && bytes.length >= 4) {
         int day = bytes[0];
         int month = bytes[1];
         int year = bytes[2] * 100 + bytes[3];
         return new DateParameter(day, month, year);
      } else {
         return null;
      }
   }
}
