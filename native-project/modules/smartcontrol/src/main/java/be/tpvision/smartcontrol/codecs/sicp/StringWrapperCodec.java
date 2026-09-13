package be.tpvision.smartcontrol.codecs.sicp;

import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;
import be.tpvision.smartcontrol.messages.codecs.sicp.string_wrapper.ToProtocolMessages;

public class StringWrapperCodec extends Codec<StringWrapper> {
   private static StringWrapperCodec stringWrapperCodec;

   private StringWrapperCodec() {
      super(StringWrapper.class);
   }

   public static synchronized StringWrapperCodec getInstance() {
      if (stringWrapperCodec == null) {
         stringWrapperCodec = new StringWrapperCodec();
      }

      return stringWrapperCodec;
   }

   public byte[] toProtocol(StringWrapper stringWrapper) {
      throw new UnsupportedOperationException(ToProtocolMessages.STRING_WRAPPER_CAN_NOT_BE_NULL);
   }

   public StringWrapper toDomain(byte[] bytes) {
      if (bytes != null && bytes.length >= 1) {
         String value = new String(bytes);
         return new StringWrapper(value);
      } else {
         return null;
      }
   }
}
