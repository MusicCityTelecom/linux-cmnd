package be.tpvision.smartcontrol.io.ip.sicp205;

import be.tpvision.smartcontrol.protocol.sicp205.SicpCommandFactory;

public class SicpDecoder extends be.tpvision.smartcontrol.io.ip.sicp.SicpDecoder {
   public SicpDecoder() {
      super(SicpCommandFactory.getInstance());
   }
}
