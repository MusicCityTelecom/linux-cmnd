package be.tpvision.smartcontrol.messages.codecs.sicp188.miscellaneous.temperature_sensor;

import be.tpvision.smartcontrol.messages.Messages;

public class ToProtocolMessages {
   public static final String TO_PROTOCOL_IS_NOT_SUPPORTED_FOR_THIS_SICP_VERSION = Messages.getCodecToProtocolIsNotSupportedForThisSicpVersionMessage(
      "Temperature sensor"
   );

   private ToProtocolMessages() {
   }
}
