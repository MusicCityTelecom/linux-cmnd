package be.tpvision.smartcontrol.rest.view_models.twa;

import java.util.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DeviceAddressWrapperViewModel implements TwaDeviceSettingViewModel {
   private DeviceAddressViewModel deviceAddress;

   protected DeviceAddressWrapperViewModel() {
   }

   public DeviceAddressWrapperViewModel(final DeviceAddressViewModel deviceAddress) {
      this.deviceAddress = deviceAddress;
   }

   public DeviceAddressViewModel getDeviceAddress() {
      return this.deviceAddress;
   }

   public void setDeviceAddress(final DeviceAddressViewModel deviceAddress) {
      this.deviceAddress = deviceAddress;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof DeviceAddressWrapperViewModel)) {
         return false;
      }

      DeviceAddressWrapperViewModel that = (DeviceAddressWrapperViewModel)object;
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
