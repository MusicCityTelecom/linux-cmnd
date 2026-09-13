package be.tpvision.smartcontrol.codecs.twa;

import be.tpvision.smartcontrol.domain.twa.Brightness;
import be.tpvision.smartcontrol.messages.codecs.twa.brightness.ToProtocolMessages;
import java.nio.ByteBuffer;
import org.springframework.util.Assert;

public class BrightnessCodec extends Codec<Brightness> {
   private static BrightnessCodec brightnessCodec;

   private BrightnessCodec() {
      super(Brightness.class);
   }

   public static synchronized BrightnessCodec getInstance() {
      if (brightnessCodec == null) {
         brightnessCodec = new BrightnessCodec();
      }

      return brightnessCodec;
   }

   public byte[] toProtocol(final Brightness brightness) {
      Assert.notNull(brightness, ToProtocolMessages.BRIGHTNESS_CAN_NOT_BE_NULL);
      ByteBuffer byteBuffer = ByteBuffer.allocate(20);

      for (int i = 0; i < 4; i++) {
         byteBuffer.put((byte)0);
      }

      int domainRed = brightness.getRed();
      byte protocolRedHigh = (byte)(domainRed >> 8 & 0xFF);
      byte protocolRedLow = (byte)(domainRed & 0xFF);
      byte[] protocolRed = new byte[]{protocolRedHigh, protocolRedLow};
      byteBuffer.put(protocolRed);
      int domainGreen = brightness.getGreen();
      byte protocolGreenHigh = (byte)(domainGreen >> 8 & 0xFF);
      byte protocolGreenLow = (byte)(domainGreen & 0xFF);
      byte[] protocolGreen = new byte[]{protocolGreenHigh, protocolGreenLow};
      byteBuffer.put(protocolGreen);
      int domainBlue = brightness.getBlue();
      byte protocolBlueHigh = (byte)(domainBlue >> 8 & 0xFF);
      byte protocolBlueLow = (byte)(domainBlue & 0xFF);
      byte[] protocolBlue = new byte[]{protocolBlueHigh, protocolBlueLow};
      byteBuffer.put(protocolBlue);

      for (int i = 0; i < 10; i++) {
         byteBuffer.put((byte)0);
      }

      return byteBuffer.array();
   }

   public Brightness toDomain(final byte[] bytes) {
      throw new UnsupportedOperationException("Brightness codec toDomain() is not supported.");
   }
}
