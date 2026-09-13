package be.tpvision.smartcontrol.io.ip.sicp201;

import be.tpvision.smartcontrol.protocol.sicp201.SicpCommandFactory;

public class SicpDecoder extends be.tpvision.smartcontrol.io.ip.sicp.SicpDecoder {
   public SicpDecoder() {
      super(SicpCommandFactory.getInstance());
   }
}
