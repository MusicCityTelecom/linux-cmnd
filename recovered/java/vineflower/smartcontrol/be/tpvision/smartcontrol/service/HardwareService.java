package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Hardware;
import java.util.Set;

public interface HardwareService {
   Set<Hardware> getHardware();

   Hardware getHardware(String hardwareKey);

   void addHardware(Hardware hardware);

   void updateHardware(Hardware hardware);

   void deleteHardware(Hardware hardware);

   void deleteHardware(String hardwareKey);
}
