package be.tpvision.smartcontrol.codecs.sicp188.general;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.general.RemoteControlLockState;
import java.util.EnumMap;
import java.util.Map;

public class RemoteControlLockStateCodec extends SingleValueCodec<RemoteControlLockState> {
   static final byte UNLOCK_ALL_BYTE = 1;
   static final byte LOCK_ALL_BYTE = 2;
   static final byte LOCK_ALL_BUT_POWER_BYTE = 3;
   static final byte LOCK_ALL_BUT_VOLUME_BYTE = 4;
   static final byte PRIMARY_BYTE = 5;
   static final byte SECONDARY_BYTE = 6;
   static final byte LOCK_ALL_EXCEPT_POWER_AND_VOLUME_BYTE = 7;
   private static RemoteControlLockStateCodec remoteControlLockStateCodec;

   private RemoteControlLockStateCodec() {
      super(RemoteControlLockState.class);
   }

   public static synchronized RemoteControlLockStateCodec getInstance() {
      if (remoteControlLockStateCodec == null) {
         remoteControlLockStateCodec = new RemoteControlLockStateCodec();
      }

      return remoteControlLockStateCodec;
   }

   @Override
   protected void initializeDeviceSettings() {
      Map<RemoteControlLockState, Byte> domainRemoteControlLockState = new EnumMap<>(RemoteControlLockState.class);
      domainRemoteControlLockState.put(RemoteControlLockState.UNLOCK_ALL, (byte)1);
      domainRemoteControlLockState.put(RemoteControlLockState.LOCK_ALL, (byte)2);
      domainRemoteControlLockState.put(RemoteControlLockState.LOCK_ALL_BUT_POWER, (byte)3);
      domainRemoteControlLockState.put(RemoteControlLockState.LOCK_ALL_BUT_VOLUME, (byte)4);
      domainRemoteControlLockState.put(RemoteControlLockState.PRIMARY, (byte)5);
      domainRemoteControlLockState.put(RemoteControlLockState.SECONDARY, (byte)6);
      domainRemoteControlLockState.put(RemoteControlLockState.LOCK_ALL_EXCEPT_POWER_AND_VOLUME, (byte)7);
      super.setDeviceSettings(domainRemoteControlLockState);
   }
}
