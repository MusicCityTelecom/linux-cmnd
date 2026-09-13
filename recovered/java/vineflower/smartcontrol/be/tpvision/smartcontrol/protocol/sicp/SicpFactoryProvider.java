package be.tpvision.smartcontrol.protocol.sicp;

import be.tpvision.smartcontrol.domain.Device;

public interface SicpFactoryProvider {
   SicpFactory getSicpFactory(Device device);
}
