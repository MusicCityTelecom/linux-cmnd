package be.tpvision.smartcontrol.codecs.sicp188.video;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.video.VGAVideoParameters;
import be.tpvision.smartcontrol.messages.codecs.sicp188.video.vga_video_parameters.ToProtocolMessages;
import be.tpvision.smartcontrol.util.ValueUtilities;
import org.springframework.util.Assert;

public class VGAVideoParametersCodec extends Codec<VGAVideoParameters> {
   private static VGAVideoParametersCodec vgaVideoParametersCodec;

   private VGAVideoParametersCodec() {
      super(VGAVideoParameters.class);
   }

   public static synchronized VGAVideoParametersCodec getInstance() {
      if (vgaVideoParametersCodec == null) {
         vgaVideoParametersCodec = new VGAVideoParametersCodec();
      }

      return vgaVideoParametersCodec;
   }

   public byte[] toProtocol(final VGAVideoParameters vgaVideoParameters) {
      Assert.notNull(vgaVideoParameters != null, ToProtocolMessages.VGA_VIDEO_PARAMETERS_CAN_NOT_BE_NULL);
      int domainClock = vgaVideoParameters.getClock();
      byte protocolClock = ValueUtilities.getByteValueFromUnsigned(domainClock);
      int domainClockPhase = vgaVideoParameters.getClockPhase();
      byte protocolClockPhase = ValueUtilities.getByteValueFromUnsigned(domainClockPhase);
      int domainHorizontalPosition = vgaVideoParameters.getHorizontalPosition();
      byte protocolHorizontalPosition = ValueUtilities.getByteValueFromUnsigned(domainHorizontalPosition);
      int domainVerticalPosition = vgaVideoParameters.getVerticalPosition();
      byte protocolVerticalPosition = ValueUtilities.getByteValueFromUnsigned(domainVerticalPosition);
      return new byte[]{protocolClock, protocolClockPhase, protocolHorizontalPosition, protocolVerticalPosition};
   }

   public VGAVideoParameters toDomain(final byte[] bytes) {
      if (bytes != null && bytes.length >= 4) {
         byte protocolClock = bytes[0];
         int domainClock = Byte.toUnsignedInt(protocolClock);
         byte protocolClockPhase = bytes[1];
         int domainClockPhase = Byte.toUnsignedInt(protocolClockPhase);
         byte protocolHorizontalPosition = bytes[2];
         int domainHorizontalPosition = Byte.toUnsignedInt(protocolHorizontalPosition);
         byte protocolVerticalPosition = bytes[3];
         int domainVerticalPosition = Byte.toUnsignedInt(protocolVerticalPosition);
         return new VGAVideoParameters(domainClock, domainClockPhase, domainHorizontalPosition, domainVerticalPosition);
      } else {
         return null;
      }
   }
}
