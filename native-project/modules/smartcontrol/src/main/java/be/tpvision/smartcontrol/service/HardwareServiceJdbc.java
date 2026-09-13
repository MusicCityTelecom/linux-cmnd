package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Hardware;
import java.util.Set;

public interface HardwareServiceJdbc {
   Set<Hardware> getHardwareByContentId(String contentId);

   Hardware getHardware(final String hardwareKey);

   void addHardware(Hardware hardware);

   void updateHardware(Hardware hardware);
}
