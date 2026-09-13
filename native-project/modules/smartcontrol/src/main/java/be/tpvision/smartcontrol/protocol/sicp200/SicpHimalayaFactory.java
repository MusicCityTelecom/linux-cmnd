package be.tpvision.smartcontrol.protocol.sicp200;

import be.tpvision.smartcontrol.io.CommandSender;
import be.tpvision.smartcontrol.io.ip.IpDestination;
import be.tpvision.smartcontrol.io.ip.sicp200.NettyCommandSender;

public class SicpHimalayaFactory implements be.tpvision.smartcontrol.protocol.sicp.SicpFactory {
   private static SicpHimalayaFactory sicpFactory;

   public static synchronized SicpHimalayaFactory getInstance() {
      if (sicpFactory == null) {
         sicpFactory = new SicpHimalayaFactory();
      }

      return sicpFactory;
   }

   @Override
   public be.tpvision.smartcontrol.protocol.sicp.SicpCommandFactory getSicpCommandFactory() {
      return SicpHimalayaCommandFactory.getInstance();
   }

   @Override
   public CommandSender<IpDestination> getCommandSender() {
      return NettyCommandSender.getInstance();
   }
}
