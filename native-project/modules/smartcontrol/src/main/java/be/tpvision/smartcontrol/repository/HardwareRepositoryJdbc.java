package be.tpvision.smartcontrol.repository;

import be.tpvision.smartcontrol.domain.Hardware;
import java.util.Set;

public interface HardwareRepositoryJdbc {
   Set<Hardware> getHardwareByContentId(final String contentId);

   Hardware getHardware(String hardwareKey);

   void addHardware(Hardware hardware);

   void updateHardware(Hardware hardware);
}
