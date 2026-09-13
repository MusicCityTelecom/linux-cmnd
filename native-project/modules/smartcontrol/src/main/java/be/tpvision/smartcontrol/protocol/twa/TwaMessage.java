package be.tpvision.smartcontrol.protocol.twa;

import be.tpvision.smartcontrol.messages.protocol.twa.twa_message.ToStringMessages;
import be.tpvision.smartcontrol.protocol.Request;
import be.tpvision.smartcontrol.util.ChecksumUtilities;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public abstract class TwaMessage implements Request {
   public static final byte HEAD_SIGN = 101;
   public static final byte SPECIAL_DATA = 1;
   public static final byte EMBEDDED_HEAD_SIGN = 85;
   public static final byte EMBEDDED_TAIL = -86;
   public static final byte TAIL = -8;
   public static final byte RESERVED = 0;
   public static final int COMMAND_SIZE = 20;
   public static final int EMBEDDED_CHECKSUM_START_INDEX = 15;
   public static final int EMBEDDED_CHECKSUM_END_INDEX = 40;
   public static final int CHECKSUM_START_INDEX = 1;
   public static final int CHECKSUM_END_INDEX = 42;
   private byte direction;
   private byte deviceAddressX;
   private byte deviceAddressY;
   private byte moduleAddressX;
   private byte moduleAddressY;

   public TwaMessage(final byte direction, final byte deviceAddressX, final byte deviceAddressY) {
      this(direction, deviceAddressX, deviceAddressY, deviceAddressX, deviceAddressY);
   }

   public TwaMessage(final byte direction, final byte deviceAddressX, final byte deviceAddressY, final byte moduleAddressX, final byte moduleAddressY) {
      this.direction = direction;
      this.deviceAddressX = deviceAddressX;
      this.deviceAddressY = deviceAddressY;
      this.moduleAddressX = moduleAddressX;
      this.moduleAddressY = moduleAddressY;
   }

   public byte getDirection() {
      return this.direction;
   }

   public void setDirection(final byte direction) {
      this.direction = direction;
   }

   public byte getDeviceAddressX() {
      return this.deviceAddressX;
   }

   public void setDeviceAddressX(final byte deviceAddressX) {
      this.deviceAddressX = deviceAddressX;
   }

   public byte getDeviceAddressY() {
      return this.deviceAddressY;
   }

   public void setDeviceAddressY(final byte deviceAddressY) {
      this.deviceAddressY = deviceAddressY;
   }

   public byte getModuleAddressX() {
      return this.moduleAddressX;
   }

   public void setModuleAddressX(final byte moduleAddressX) {
      this.moduleAddressX = moduleAddressX;
   }

   public byte getModuleAddressY() {
      return this.moduleAddressY;
   }

   public void setModuleAddressY(final byte moduleAddressY) {
      this.moduleAddressY = moduleAddressY;
   }

   static byte[] getDataLength() {
      return new byte[]{0, 45};
   }

   static byte[] getEmbeddedDataLength() {
      return new byte[]{0, 29};
   }

   @Override
   public byte[] toBytes() {
      ByteBuffer byteBuffer = ByteBuffer.allocate(45);
      byteBuffer.put((byte)101);
      byte[] dataLength = getDataLength();
      byteBuffer.put(dataLength);
      byteBuffer.put(this.direction);
      byteBuffer.put((byte)1);
      byteBuffer.put(this.deviceAddressX);
      byteBuffer.put(this.deviceAddressY);

      for (int i = 0; i < 7; i++) {
         byteBuffer.put((byte)0);
      }

      byteBuffer.put((byte)85);
      byte[] embeddedDataLength = getEmbeddedDataLength();
      byteBuffer.put(embeddedDataLength);
      byteBuffer.put(this.moduleAddressX);
      byteBuffer.put(this.moduleAddressY);
      byte[] data = this.getData();
      byteBuffer.put(data);
      byte[] byteBufferArray = byteBuffer.array();
      int embeddedChecksumTo = 41;
      byte[] embeddedChecksumInput = Arrays.copyOfRange(byteBufferArray, 15, 41);
      byte embeddedChecksum = ChecksumUtilities.modChecksum(embeddedChecksumInput);
      byteBuffer.put(embeddedChecksum);
      byteBuffer.put((byte)-86);
      byteBufferArray = byteBuffer.array();
      int checksumTo = 43;
      byte[] checksumInput = Arrays.copyOfRange(byteBufferArray, 1, 43);
      byte checksum = ChecksumUtilities.modChecksum(checksumInput);
      byteBuffer.put(checksum);
      byteBuffer.put((byte)-8);
      return byteBuffer.array();
   }

   protected abstract byte[] getData();

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof TwaMessage)) {
         return false;
      }

      TwaMessage that = (TwaMessage)object;
      return new EqualsBuilder()
         .append(this.getDirection(), that.getDirection())
         .append(this.getDeviceAddressX(), that.getDeviceAddressX())
         .append(this.getDeviceAddressY(), that.getDeviceAddressY())
         .append(this.getModuleAddressX(), that.getModuleAddressX())
         .append(this.getModuleAddressY(), that.getModuleAddressY())
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getDirection(), this.getDeviceAddressX(), this.getDeviceAddressY(), this.getModuleAddressX(), this.getModuleAddressY());
   }

   @Override
   public String toString() {
      byte direction = this.getDirection();
      String directionHex = ValueUtilities.toHexString(direction);
      Assert.state(StringUtils.hasText(directionHex), ToStringMessages.DIRECTION_HEX_CAN_NOT_BE_EMPTY);
      byte deviceAddressX = this.getDeviceAddressX();
      String deviceAddressXHex = ValueUtilities.toHexString(deviceAddressX);
      Assert.state(StringUtils.hasText(deviceAddressXHex), ToStringMessages.DEVICE_ADDRESS_X_HEX_CAN_NOT_BE_EMPTY);
      byte deviceAddressY = this.getDeviceAddressY();
      String deviceAddressYHex = ValueUtilities.toHexString(deviceAddressY);
      Assert.state(StringUtils.hasText(deviceAddressYHex), ToStringMessages.DEVICE_ADDRESS_Y_HEX_CAN_NOT_BE_EMPTY);
      byte moduleAddressX = this.getModuleAddressX();
      String moduleAddressXHex = ValueUtilities.toHexString(moduleAddressX);
      Assert.state(!ObjectUtils.isEmpty(moduleAddressX), ToStringMessages.MODULE_ADDRESS_X_HEX_CAN_NOT_BE_EMPTY);
      byte moduleAddressY = this.getModuleAddressY();
      String moduleAddressYHex = ValueUtilities.toHexString(moduleAddressY);
      Assert.state(!ObjectUtils.isEmpty(moduleAddressY), ToStringMessages.MODULE_ADDRESS_Y_HEX_CAN_NOT_BE_EMPTY);
      return new ToStringBuilder(this)
         .append("direction", directionHex)
         .append("deviceAddressX", deviceAddressXHex)
         .append("deviceAddressY", deviceAddressYHex)
         .append("moduleAddressX", moduleAddressXHex)
         .append("moduleAddressY", moduleAddressYHex)
         .toString();
   }
}
