package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.repository.data_transfer_objects.DeviceDTO;

public interface DeviceServiceJdbc {
   DeviceDTO getDeviceDTO(long id);

   boolean getContentRotatedBySerialCode(String serialCode);

   String getSerialCode(long id);
}
