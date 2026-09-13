package be.tpvision.smartcontrol.codecs.sicp207.general;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.general.AutoRestartParameter;

public class AutoRestartParameterCodec extends Codec<AutoRestartParameter> {
   private static AutoRestartParameterCodec autoRestartParameterCodec;

   private AutoRestartParameterCodec() {
      super(AutoRestartParameter.class);
   }

   public static synchronized AutoRestartParameterCodec getInstance() {
      if (autoRestartParameterCodec == null) {
         autoRestartParameterCodec = new AutoRestartParameterCodec();
      }

      return autoRestartParameterCodec;
   }

   public byte[] toProtocol(final AutoRestartParameter autoRestartParameter) {
      byte status = (byte)autoRestartParameter.getStatus().ordinal();
      byte hour = (byte)autoRestartParameter.getHour();
      byte minute = (byte)autoRestartParameter.getMinute();
      return new byte[]{status, hour, minute};
   }

   public AutoRestartParameter toDomain(final byte[] bytes) {
      if (bytes != null && bytes.length >= 3) {
         AutoRestartParameter.Status status = AutoRestartParameter.Status.values()[bytes[0]];
         int hour = bytes[1];
         int minute = bytes[2];
         return new AutoRestartParameter(status, hour, minute);
      } else {
         return null;
      }
   }
}
