package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;

public enum RemoteControlLockState implements Convertible {
   UNLOCK_ALL((byte)1),
   LOCK_ALL((byte)2),
   LOCK_ALL_BUT_POWER((byte)3),
   LOCK_ALL_BUT_VOLUME((byte)4),
   PRIMARY((byte)5),
   SECONDARY((byte)6);

   private byte data;

   RemoteControlLockState(final byte data) {
      this.data = data;
   }

   public byte getData() {
      return this.data;
   }

   @Override
   public byte convert() {
      return this.data;
   }
}
