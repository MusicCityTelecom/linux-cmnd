package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.protocol.SicpMessage;

public class CommunicationControlMessage extends SicpMessage {
   public static final byte IDENTIFYING_BYTE = 0;
   private CommunicationControl communicationControl;

   public CommunicationControlMessage(final int controlId, final int groupId, final CommunicationControl communicationControl) {
      super(controlId, groupId);
      this.communicationControl = communicationControl;
   }

   @Override
   protected byte[] getData() {
      return new byte[]{0, this.communicationControl.convert()};
   }
}
