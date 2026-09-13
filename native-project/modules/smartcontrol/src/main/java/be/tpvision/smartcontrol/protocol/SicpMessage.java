package be.tpvision.smartcontrol.protocol;

import be.tpvision.smartcontrol.util.ChecksumUtilities;
import java.nio.ByteBuffer;

public abstract class SicpMessage implements Request {
   private byte controlId;
   private byte groupId;

   public SicpMessage(final int controlId, final int groupId) {
      this.controlId = (byte)controlId;
      this.groupId = (byte)groupId;
   }

   public final byte getControlId() {
      return this.controlId;
   }

   public final byte getGroupId() {
      return this.groupId;
   }

   @Override
   public final byte[] toBytes() {
      byte[] messageBody = this.getData();
      byte messageSize = (byte)(messageBody.length + 4);
      byte[] result = new byte[messageSize];
      ByteBuffer byteBuffer = ByteBuffer.wrap(result);
      byteBuffer.put(messageSize);
      byteBuffer.put(this.controlId);
      byteBuffer.put(this.groupId);
      byteBuffer.put(messageBody);
      byte checksum = ChecksumUtilities.xorChecksum(byteBuffer);
      byteBuffer.put(checksum);
      return result;
   }

   protected abstract byte[] getData();
}
