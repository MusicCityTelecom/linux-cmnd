package be.tpvision.smartcontrol.codecs.sicp203.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.VideoPresent;
import be.tpvision.smartcontrol.messages.Messages;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class VideoPresentCodec extends SingleValueCodec<VideoPresent> {
   static final byte NO_BYTE = 0;
   static final byte YES_BYTE = 1;
   private static VideoPresentCodec videoPresentCodec;

   private VideoPresentCodec() {
      super(VideoPresent.class);
   }

   public static synchronized VideoPresentCodec getInstance() {
      if (videoPresentCodec == null) {
         videoPresentCodec = new VideoPresentCodec();
      }

      return videoPresentCodec;
   }

   @Override
   protected void initializeDeviceSettings() {
      Map<VideoPresent, Byte> domainVideoPresent = Collections.emptyMap();
      super.setDeviceSettings(domainVideoPresent);
   }

   @Override
   protected void initializeProtocolSettings() {
      Map<Byte, VideoPresent> protocolVideoPresent = new HashMap<>();
      protocolVideoPresent.put((byte)0, VideoPresent.NO);
      protocolVideoPresent.put((byte)1, VideoPresent.YES);
      super.setProtocolSettings(protocolVideoPresent);
   }

   public byte[] toProtocol(VideoPresent deviceSetting) {
      String message = Messages.getCodecToProtocolIsNotSupportedForThisSicpVersionMessage("Video present");
      throw new UnsupportedOperationException(message);
   }
}
