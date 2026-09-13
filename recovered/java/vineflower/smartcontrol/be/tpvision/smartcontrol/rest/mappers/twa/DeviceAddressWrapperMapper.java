package be.tpvision.smartcontrol.rest.mappers.twa;

import be.tpvision.smartcontrol.domain.twa.DeviceAddress;
import be.tpvision.smartcontrol.domain.twa.DeviceAddressWrapper;
import be.tpvision.smartcontrol.messages.mappers.twa.device_address_wrapper.ToDeviceAddressWrapperMessages;
import be.tpvision.smartcontrol.rest.view_models.twa.DeviceAddressViewModel;
import be.tpvision.smartcontrol.rest.view_models.twa.DeviceAddressWrapperViewModel;
import org.springframework.util.Assert;

public class DeviceAddressWrapperMapper {
   private DeviceAddressWrapperMapper() {
   }

   public static DeviceAddressWrapper toDeviceAddressWrapper(final DeviceAddressWrapperViewModel deviceAddressWrapperViewModel) {
      Assert.notNull(deviceAddressWrapperViewModel, ToDeviceAddressWrapperMessages.DEVICE_ADDRESS_WRAPPER_VIEW_MODEL_CAN_NOT_BE_NULL);
      DeviceAddressViewModel deviceAddressViewModel = deviceAddressWrapperViewModel.getDeviceAddress();
      Assert.state(deviceAddressViewModel != null, ToDeviceAddressWrapperMessages.DEVICE_ADDRESS_VIEW_MODEL_CAN_NOT_BE_NULL);
      DeviceAddress deviceAddress = DeviceAddressMapper.toDeviceAddress(deviceAddressViewModel);
      Assert.state(deviceAddress != null, ToDeviceAddressWrapperMessages.DEVICE_ADDRESS_CAN_NOT_BE_NULL);
      return new DeviceAddressWrapper(deviceAddress);
   }
}
