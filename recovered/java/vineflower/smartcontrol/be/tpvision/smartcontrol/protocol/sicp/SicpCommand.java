package be.tpvision.smartcontrol.protocol.sicp;

import be.tpvision.smartcontrol.messages.protocol.sicp.sicp_command.ToStringMessages;
import be.tpvision.smartcontrol.protocol.SicpMessage;
import be.tpvision.smartcontrol.util.ByteArrayUtilities;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.nio.ByteBuffer;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class SicpCommand extends SicpMessage {
   private byte settingByte;
   private byte[] bytes;

   public SicpCommand(final int controlId, final int groupId, final byte settingByte) {
      super(controlId, groupId);
      this.settingByte = settingByte;
   }

   public SicpCommand(final int controlId, final int groupId, final byte settingByte, final byte[] bytes) {
      this(controlId, groupId, settingByte);
      this.bytes = bytes;
   }

   public byte getSettingByte() {
      return this.settingByte;
   }

   public void setSettingByte(final byte settingByte) {
      this.settingByte = settingByte;
   }

   public byte[] getBytes() {
      return this.bytes;
   }

   public void setBytes(final byte[] bytes) {
      this.bytes = bytes;
   }

   @Override
   protected byte[] getData() {
      byte[] data;
      if (this.bytes != null) {
         int capacity = 1 + this.bytes.length;
         ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
         byteBuffer.put(this.settingByte);
         byteBuffer.put(this.bytes);
         data = byteBuffer.array();
      } else {
         data = new byte[]{this.settingByte};
      }

      return data;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof SicpCommand)) {
         return false;
      }

      SicpCommand that = (SicpCommand)object;
      return new EqualsBuilder().append(this.getSettingByte(), that.getSettingByte()).append(this.getBytes(), that.getBytes()).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getSettingByte(), this.getBytes());
   }

   @Override
   public String toString() {
      byte settingByte = this.getSettingByte();
      String settingByteHex = ValueUtilities.toHexString(settingByte);
      Assert.state(StringUtils.hasText(settingByteHex), ToStringMessages.SETTING_BYTE_HEX_CAN_NOT_BE_EMPTY);
      byte[] bytes = this.getBytes();
      String bytesHex = ByteArrayUtilities.newBytesToHex(bytes);
      return new ToStringBuilder(this).append("settingByte", settingByteHex).append("bytes", bytesHex).toString();
   }
}
