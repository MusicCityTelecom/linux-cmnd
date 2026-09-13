package be.tpvision.smartcontrol.codecs.sicp203.video;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureStyle;
import java.util.EnumMap;
import java.util.Map;

public class PictureStyleCodec extends SingleValueCodec<PictureStyle> {
   static final byte HIGHBRIGHT_BYTE = 0;
   static final byte SRGB_BYTE = 1;
   static final byte VIVID_BYTE = 2;
   static final byte NATURAL_BYTE = 3;
   static final byte STANDARD_BYTE = 4;
   static final byte VIDEO_BYTE = 5;
   static final byte STATIC_SIGNAGE_BYTE = 6;
   static final byte TEXT_BYTE = 7;
   static final byte ENERGY_SAVING_BYTE = 8;
   static final byte SOFT_BYTE = 9;
   static final byte USER_BYTE = 10;
   private static PictureStyleCodec pictureStyleCodec;

   private PictureStyleCodec() {
      super(PictureStyle.class);
   }

   public static synchronized PictureStyleCodec getInstance() {
      if (pictureStyleCodec == null) {
         pictureStyleCodec = new PictureStyleCodec();
      }

      return pictureStyleCodec;
   }

   @Override
   protected void initializeDeviceSettings() {
      Map<PictureStyle, Byte> domainPictureStyles = new EnumMap<>(PictureStyle.class);
      domainPictureStyles.put(PictureStyle.HIGHBRIGHT, (byte)0);
      domainPictureStyles.put(PictureStyle.SRGB, (byte)1);
      domainPictureStyles.put(PictureStyle.VIVID, (byte)2);
      domainPictureStyles.put(PictureStyle.NATURAL, (byte)3);
      domainPictureStyles.put(PictureStyle.STANDARD, (byte)4);
      domainPictureStyles.put(PictureStyle.VIDEO, (byte)5);
      domainPictureStyles.put(PictureStyle.STATIC_SIGNAGE, (byte)6);
      domainPictureStyles.put(PictureStyle.TEXT, (byte)7);
      domainPictureStyles.put(PictureStyle.ENERGY_SAVING, (byte)8);
      domainPictureStyles.put(PictureStyle.SOFT, (byte)9);
      domainPictureStyles.put(PictureStyle.USER, (byte)10);
      super.setDeviceSettings(domainPictureStyles);
   }
}
