package be.tpvision.smartcontrol.codecs.sicp;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;

public abstract class Codec<D extends DeviceSetting> {
   private final Class<D> deviceSettingClass;

   public Codec(Class<D> deviceSettingClass) {
      this.deviceSettingClass = deviceSettingClass;
   }

   public abstract byte[] toProtocol(D deviceSetting);

   public abstract D toDomain(byte[] bytes);

   public Class<D> getDeviceSettingClass() {
      return this.deviceSettingClass;
   }
}
