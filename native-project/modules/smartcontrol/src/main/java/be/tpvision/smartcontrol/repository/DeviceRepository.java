package be.tpvision.smartcontrol.repository;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.io.ip.IpDestination;

public interface DeviceRepository extends Repository<Long, Device> {
   Device getByIpDestination(IpDestination ipDestination);

   Device getBySerialCode(String serialCode);
}
