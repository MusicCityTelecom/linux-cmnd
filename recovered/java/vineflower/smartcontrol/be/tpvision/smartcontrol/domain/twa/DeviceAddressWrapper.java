package be.tpvision.smartcontrol.domain.twa;

import be.tpvision.smartcontrol.messages.domain.twa.device_address_wrapper.GetDeviceAddressMessages;
import be.tpvision.smartcontrol.messages.domain.twa.device_address_wrapper.SetDeviceAddressMessages;
import java.util.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public class DeviceAddressWrapper implements TwaDeviceSetting {
   private DeviceAddress deviceAddress;

   public DeviceAddressWrapper(final DeviceAddress deviceAddress) {
      this.setDeviceAddress(deviceAddress);
   }

   public DeviceAddress getDeviceAddress() {
      Assert.state(this.deviceAddress != null, GetDeviceAddressMessages.DEVICE_ADDRESS_CAN_NOT_BE_NULL);
      return this.deviceAddress;
   }

   public void setDeviceAddress(final DeviceAddress deviceAddress) {
      Assert.notNull(deviceAddress, SetDeviceAddressMessages.DEVICE_ADDRESS_CAN_NOT_BE_NULL);
      this.deviceAddress = deviceAddress;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof DeviceAddressWrapper)) {
         return false;
      }

      DeviceAddressWrapper that = (DeviceAddressWrapper)object;
      return Objects.equals(this.getDeviceAddress(), that.getDeviceAddress());
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(this.getDeviceAddress());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("deviceAddress", this.getDeviceAddress()).toString();
   }
}
