package be.tpvision.smartcontrol.codecs.sicp202.general;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.general.MonitorSystem;
import be.tpvision.smartcontrol.messages.Messages;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class MonitorRestartCodec extends SingleValueCodec<MonitorSystem> {
   static final byte ANDROID_BYTE = 0;
   static final byte SCALAR_BYTE = 1;
   private static MonitorRestartCodec monitorRestartCodec;

   private MonitorRestartCodec() {
      super(MonitorSystem.class);
   }

   public static synchronized MonitorRestartCodec getInstance() {
      if (monitorRestartCodec == null) {
         monitorRestartCodec = new MonitorRestartCodec();
      }

      return monitorRestartCodec;
   }

   @Override
   protected void initializeDeviceSettings() {
      Map<MonitorSystem, Byte> domainMonitorSystem = new EnumMap<>(MonitorSystem.class);
      domainMonitorSystem.put(MonitorSystem.ANDROID, (byte)0);
      domainMonitorSystem.put(MonitorSystem.SCALAR, (byte)1);
      super.setDeviceSettings(domainMonitorSystem);
   }

   @Override
   protected void initializeProtocolSettings() {
      Map<Byte, MonitorSystem> protocolMonitorSystem = Collections.emptyMap();
      super.setProtocolSettings(protocolMonitorSystem);
   }

   public MonitorSystem toDomain(byte[] bytes) {
      String message = Messages.getCodecToDomainIsNotSupportedForThisSicpVersionMessage("Monitor restart");
      throw new UnsupportedOperationException(message);
   }
}
