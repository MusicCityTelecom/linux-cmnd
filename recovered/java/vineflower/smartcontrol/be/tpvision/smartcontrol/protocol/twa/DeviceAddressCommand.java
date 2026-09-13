package be.tpvision.smartcontrol.protocol.twa;

public class DeviceAddressCommand extends TwaCommand {
   public static final byte HEAD_SIGN = 102;
   private static final byte DEVICE_ADDRESS_X = 0;
   private static final byte DEVICE_ADDRESS_Y = 0;
   private static final byte COMMAND_CODE_BYTE_1 = 3;
   private static final byte COMMAND_CODE_BYTE_2 = 87;

   public DeviceAddressCommand(final byte[] bytes) {
      super((byte)0, (byte)0, new byte[]{3, 87}, bytes);
   }

   @Override
   public void setCommandCode(final byte[] commandCode) {
      throw new UnsupportedOperationException("Set command code is not supported for a device address command.");
   }

   @Override
   public byte[] toBytes() {
      byte[] bytes = super.toBytes();
      bytes[0] = 102;
      return bytes;
   }
}
