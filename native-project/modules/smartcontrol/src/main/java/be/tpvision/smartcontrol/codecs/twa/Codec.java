package be.tpvision.smartcontrol.codecs.twa;

import be.tpvision.smartcontrol.domain.twa.TwaDeviceSetting;

public abstract class Codec<D extends TwaDeviceSetting> {
   private final Class<D> twaDeviceSettingClass;

   public Codec(final Class<D> twaDeviceSettingClass) {
      this.twaDeviceSettingClass = twaDeviceSettingClass;
   }

   public abstract byte[] toProtocol(D twaDeviceSetting);

   public abstract D toDomain(byte[] bytes);

   public Class<D> getTwaDeviceSettingClass() {
      return this.twaDeviceSettingClass;
   }
}
