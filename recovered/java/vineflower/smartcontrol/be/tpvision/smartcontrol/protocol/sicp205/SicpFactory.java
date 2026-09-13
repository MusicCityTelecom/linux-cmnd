package be.tpvision.smartcontrol.protocol.sicp205;

import be.tpvision.smartcontrol.io.CommandSender;
import be.tpvision.smartcontrol.io.ip.IpDestination;
import be.tpvision.smartcontrol.io.ip.sicp205.NettyCommandSender;

public class SicpFactory implements be.tpvision.smartcontrol.protocol.sicp.SicpFactory {
   private static SicpFactory sicpFactory;

   public static synchronized SicpFactory getInstance() {
      if (sicpFactory == null) {
         sicpFactory = new SicpFactory();
      }

      return sicpFactory;
   }

   @Override
   public be.tpvision.smartcontrol.protocol.sicp.SicpCommandFactory getSicpCommandFactory() {
      return SicpCommandFactory.getInstance();
   }

   @Override
   public CommandSender<IpDestination> getCommandSender() {
      return NettyCommandSender.getInstance();
   }
}
