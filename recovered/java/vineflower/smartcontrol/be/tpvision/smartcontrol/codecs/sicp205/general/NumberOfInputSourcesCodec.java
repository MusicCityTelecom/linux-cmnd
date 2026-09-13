package be.tpvision.smartcontrol.codecs.sicp205.general;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.codecs.sicp205.miscellaneous.BootOnSourceCodec;
import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;
import be.tpvision.smartcontrol.domain.device_settings.general.BootOnSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NumberOfInputSourcesCodec extends Codec<StringWrapper> {
   private static NumberOfInputSourcesCodec numberOfInputSourcesCodec;

   private NumberOfInputSourcesCodec() {
      super(StringWrapper.class);
   }

   public static synchronized NumberOfInputSourcesCodec getInstance() {
      if (numberOfInputSourcesCodec == null) {
         numberOfInputSourcesCodec = new NumberOfInputSourcesCodec();
      }

      return numberOfInputSourcesCodec;
   }

   public byte[] toProtocol(final StringWrapper numberOfInputSources) {
      return new byte[0];
   }

   public StringWrapper toDomain(final byte[] bytes) {
      if (bytes != null && bytes.length >= 2) {
         List<String> inputSourceTypeNames = new ArrayList<>();
         Map<Byte, BootOnSource.VideoSourceType> protocolVideoSourceTypes = BootOnSourceCodec.getProtocolvideosourcetypes();
         int i = 1;

         for (int j = bytes.length; i < j; i++) {
            byte typeByte = bytes[i];
            if (protocolVideoSourceTypes.containsKey(typeByte)) {
               inputSourceTypeNames.add(protocolVideoSourceTypes.get(typeByte).name());
            }
         }

         return new StringWrapper(String.join(",", inputSourceTypeNames));
      } else {
         return null;
      }
   }
}
