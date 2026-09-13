package be.tpvision.smartcontrol.io.ip.sicp207;

import be.tpvision.smartcontrol.protocol.sicp207.SicpCommandFactory;

public class SicpDecoder extends be.tpvision.smartcontrol.io.ip.sicp.SicpDecoder {
   public SicpDecoder() {
      super(SicpCommandFactory.getInstance());
   }
}
