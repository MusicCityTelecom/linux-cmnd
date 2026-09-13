package be.tpvision.smartcontrol.codecs.sicp207.general;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.general.ClockParameter;

public class ClockParameterCodec extends Codec<ClockParameter> {
   private static ClockParameterCodec clockParameterCodec;

   private ClockParameterCodec() {
      super(ClockParameter.class);
   }

   public static synchronized ClockParameterCodec getInstance() {
      if (clockParameterCodec == null) {
         clockParameterCodec = new ClockParameterCodec();
      }

      return clockParameterCodec;
   }

   public byte[] toProtocol(final ClockParameter clockParameter) {
      byte hour = (byte)clockParameter.getHour();
      byte minute = (byte)clockParameter.getMinute();
      return new byte[]{hour, minute};
   }

   public ClockParameter toDomain(final byte[] bytes) {
      return bytes != null && bytes.length >= 2 ? new ClockParameter(bytes[0], bytes[1]) : null;
   }
}
