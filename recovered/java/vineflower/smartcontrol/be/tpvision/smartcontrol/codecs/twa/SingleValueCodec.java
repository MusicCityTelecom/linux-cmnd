package be.tpvision.smartcontrol.codecs.twa;

import be.tpvision.smartcontrol.domain.twa.TwaDeviceSetting;
import be.tpvision.smartcontrol.messages.codecs.twa.single_value.SetDeviceSettingsMessages;
import be.tpvision.smartcontrol.messages.codecs.twa.single_value.SetProtocolSettingsMessages;
import be.tpvision.smartcontrol.messages.codecs.twa.single_value.ToDomainMessages;
import be.tpvision.smartcontrol.messages.codecs.twa.single_value.ToProtocolMessages;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public abstract class SingleValueCodec<D extends TwaDeviceSetting> extends Codec<D> {
   private Map<D, Byte> deviceSettings = new HashMap<>();
   private Map<Byte, D> protocolSettings = new HashMap<>();

   public SingleValueCodec(final Class<D> deviceSettingClass) {
      super(deviceSettingClass);
   }

   public Map<D, Byte> getDeviceSettings() {
      return this.deviceSettings;
   }

   protected final void setDeviceSettings(final Map<D, Byte> deviceSettings) {
      Assert.notNull(deviceSettings, SetDeviceSettingsMessages.DEVICE_SETTINGS_CAN_NOT_BE_NULL);
      this.deviceSettings = deviceSettings;
   }

   public Map<Byte, D> getProtocolSettings() {
      return this.protocolSettings;
   }

   protected final void setProtocolSettings(final Map<Byte, D> protocolSettings) {
      Assert.notNull(protocolSettings, SetProtocolSettingsMessages.PROTOCOL_SETTINGS_CAN_NOT_BE_NULL);
      this.protocolSettings = protocolSettings;
   }

   @Override
   public byte[] toProtocol(final D twaDeviceSetting) {
      Assert.notNull(twaDeviceSetting, ToProtocolMessages.DEVICE_SETTING_CAN_NOT_BE_NULL);
      ByteBuffer byteBuffer = ByteBuffer.allocate(20);

      for (int i = 0; i < 4; i++) {
         byteBuffer.put((byte)0);
      }

      Byte protocolSetting = this.deviceSettings.get(twaDeviceSetting);
      Assert.state(protocolSetting != null, ToProtocolMessages.PROTOCOL_SETTING_CAN_NOT_BE_NULL);
      byteBuffer.put(protocolSetting);

      for (int i = 0; i < 15; i++) {
         byteBuffer.put((byte)0);
      }

      return byteBuffer.array();
   }

   @Override
   public D toDomain(final byte[] bytes) {
      if (bytes != null && bytes.length >= 1) {
         byte protocolSetting = bytes[0];
         D deviceSetting = this.protocolSettings.get(protocolSetting);
         Assert.state(deviceSetting != null, ToDomainMessages.DEVICE_SETTING_CAN_NOT_BE_NULL);
         return deviceSetting;
      } else {
         return null;
      }
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof SingleValueCodec)) {
         return false;
      }

      SingleValueCodec<?> that = (SingleValueCodec<?>)object;
      return new EqualsBuilder()
         .append(this.getDeviceSettings(), that.getDeviceSettings())
         .append(this.getProtocolSettings(), that.getProtocolSettings())
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getDeviceSettings(), this.getProtocolSettings());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("deviceSettings", this.getDeviceSettings()).append("protocolSettings", this.getProtocolSettings()).toString();
   }
}
