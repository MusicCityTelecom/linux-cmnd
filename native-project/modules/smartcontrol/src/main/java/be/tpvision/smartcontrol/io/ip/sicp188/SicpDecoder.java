package be.tpvision.smartcontrol.io.ip.sicp188;

import be.tpvision.smartcontrol.protocol.sicp188.SicpCommandFactory;

public class SicpDecoder extends be.tpvision.smartcontrol.io.ip.sicp.SicpDecoder {
   public SicpDecoder() {
      super(SicpCommandFactory.getInstance());
   }
}
