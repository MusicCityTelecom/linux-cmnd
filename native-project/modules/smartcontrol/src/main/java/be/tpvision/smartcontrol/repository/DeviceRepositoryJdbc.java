package be.tpvision.smartcontrol.repository;

import be.tpvision.smartcontrol.repository.data_transfer_objects.DeviceDTO;

public interface DeviceRepositoryJdbc {
   DeviceDTO getDeviceDTO(long id);

   boolean getContentRotatedBySerialCode(String serialCode);

   String getSerialCode(long id);
}
