package be.tpvision.smartcontrol.codecs.sicp188.general;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.general.KeypadLockState;
import java.util.EnumMap;
import java.util.Map;

public class KeypadLockStateCodec extends SingleValueCodec<KeypadLockState> {
   static final byte UNLOCK_ALL_BYTE = 1;
   static final byte LOCK_ALL_BYTE = 2;
   static final byte LOCK_ALL_BUT_POWER_BYTE = 3;
   static final byte LOCK_ALL_BUT_VOLUME_BYTE = 4;
   static final byte LOCK_ALL_EXCEPT_POWER_AND_VOLUME_BYTE = 7;
   private static KeypadLockStateCodec keypadLockStateCodec;

   private KeypadLockStateCodec() {
      super(KeypadLockState.class);
   }

   public static synchronized KeypadLockStateCodec getInstance() {
      if (keypadLockStateCodec == null) {
         keypadLockStateCodec = new KeypadLockStateCodec();
      }

      return keypadLockStateCodec;
   }

   @Override
   protected void initializeDeviceSettings() {
      Map<KeypadLockState, Byte> domainKeypadLockState = new EnumMap<>(KeypadLockState.class);
      domainKeypadLockState.put(KeypadLockState.UNLOCK_ALL, (byte)1);
      domainKeypadLockState.put(KeypadLockState.LOCK_ALL, (byte)2);
      domainKeypadLockState.put(KeypadLockState.LOCK_ALL_BUT_POWER, (byte)3);
      domainKeypadLockState.put(KeypadLockState.LOCK_ALL_BUT_VOLUME, (byte)4);
      domainKeypadLockState.put(KeypadLockState.LOCK_ALL_EXCEPT_POWER_AND_VOLUME, (byte)7);
      super.setDeviceSettings(domainKeypadLockState);
   }
}
