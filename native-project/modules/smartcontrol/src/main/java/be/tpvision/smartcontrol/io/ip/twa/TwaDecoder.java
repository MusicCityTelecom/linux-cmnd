package be.tpvision.smartcontrol.io.ip.twa;

import be.tpvision.smartcontrol.protocol.Response;
import be.tpvision.smartcontrol.protocol.ResponseWrapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;

public class TwaDecoder extends MessageToMessageDecoder<DatagramPacket> {
   private static final byte DIRECTION_INDEX = 3;
   private static final byte DEVICE_ADDRESS_X_INDEX = 5;
   private static final byte DEVICE_ADDRESS_Y_INDEX = 6;
   private static final byte MODULE_ADDRESS_X_INDEX = 17;
   private static final byte MODULE_ADDRESS_Y_INDEX = 18;
   private static final byte COMMAND_CODE_INDEX_1 = 19;
   private static final byte COMMAND_CODE_INDEX_2 = 20;
   private static final byte COMMAND_RESULT_INDEX = 25;
   private static final byte COMMAND_RESULT_SUCCESS = 6;
   private static final byte COMMAND_RESULT_FAILURE = 21;

   protected void decode(final ChannelHandlerContext channelHandlerContext, final DatagramPacket datagramPacket, final List<Object> output) throws Exception {
      ByteBuf byteBuf = datagramPacket.content();
      byte direction = byteBuf.getByte(3);
      byte deviceAddressX = byteBuf.getByte(5);
      byte deviceAddressY = byteBuf.getByte(6);
      byte moduleAddressX = byteBuf.getByte(17);
      byte moduleAddressY = byteBuf.getByte(18);
      byte[] commandCode = new byte[2];
      byteBuf.getBytes(19, commandCode, 0, 2);
      byte commandResult = byteBuf.getByte(25);
      if (commandResult == 6) {
      }

      Response response = new ResponseWrapper(datagramPacket);
      output.add(response);
   }
}
