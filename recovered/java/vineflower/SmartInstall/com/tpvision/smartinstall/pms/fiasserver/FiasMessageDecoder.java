package com.tpvision.smartinstall.pms.fiasserver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

public class FiasMessageDecoder extends ByteToMessageDecoder {
   @Override
   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
      in.markReaderIndex();
      boolean foundStart = false;
      List<Byte> buffer = new ArrayList<>();

      while (in.isReadable()) {
         Byte c = in.readByte();
         if (c == 2) {
            foundStart = true;
            buffer.clear();
         }

         if (foundStart) {
            buffer.add(c);
            if (c == 3) {
               FiasMessage msg = new FiasMessage();
               msg.setData(this.byteListToString(buffer));
               out.add(msg);
               foundStart = false;
               in.markReaderIndex();
            }
         }
      }

      if (foundStart) {
         in.resetReaderIndex();
      }
   }

   private String byteListToString(List<Byte> out) {
      Byte[] bytes = out.toArray(new Byte[out.size()]);
      return new String(ArrayUtils.toPrimitive(bytes));
   }
}
