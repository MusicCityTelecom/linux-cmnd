package com.tpvision.smartinstall.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BroadcastUtils {
   private static final Logger LOG = LoggerFactory.getLogger(BroadcastUtils.class);
   private static final int WOL_PORT = 7;

   private BroadcastUtils() {
   }

   public static void broadcastWakeOnLanMagicPackageForAddress(String macStr, InetAddress address) {
      LOG.info("broadcast wakeup package to interface {} for mac address {}", address, macStr);

      try {
         byte[] wolBytes = assembleWOLCommand(macStr);
         broadcast(wolBytes, address, 7);
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }

   public static void broadcastWakeOnLanMagicPackageForAllNetworkInterfaces(String macStr) {
      LOG.info("broadcast wakeup package to all interface for mac address {}", macStr);

      try {
         byte[] wolBytes = assembleWOLCommand(macStr);

         for (InetAddress address : listAllBroadcastAddresses()) {
            broadcast(wolBytes, address, 7);
         }
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }

   private static List<InetAddress> listAllBroadcastAddresses() throws SocketException {
      List<InetAddress> broadcastList = new ArrayList<>();
      Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

      while (interfaces.hasMoreElements()) {
         NetworkInterface networkInterface = interfaces.nextElement();
         if (!networkInterface.isLoopback() && networkInterface.isUp()) {
            networkInterface.getInterfaceAddresses().stream().map(a -> a.getBroadcast()).filter(Objects::nonNull).forEach(broadcastList::add);
         }
      }

      return broadcastList;
   }

   private static void broadcast(byte[] sendBytes, InetAddress address, int port) throws IOException {
      LOG.info("broadcast wakeup package to interface {}", address);

      try (DatagramSocket socket = new DatagramSocket()) {
         socket.setBroadcast(true);
         DatagramPacket packet = new DatagramPacket(sendBytes, sendBytes.length, address, port);
         socket.send(packet);
      }
   }

   private static byte[] assembleWOLCommand(String macStr) {
      byte[] macBytes = getMacBytes(macStr);
      byte[] bytes = new byte[6 + 16 * macBytes.length];

      for (int i = 0; i < 6; i++) {
         bytes[i] = -1;
      }

      for (int i = 6; i < bytes.length; i += macBytes.length) {
         System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
      }

      return bytes;
   }

   private static byte[] getMacBytes(String macStr) throws IllegalArgumentException {
      byte[] bytes = new byte[6];
      String[] hex = macStr.split("[\\:\\-]");
      if (hex.length != 6) {
         throw new IllegalArgumentException("Invalid MAC address.");
      }

      try {
         for (int i = 0; i < 6; i++) {
            bytes[i] = (byte)Integer.parseInt(hex[i], 16);
         }

         return bytes;
      } catch (NumberFormatException e) {
         throw new IllegalArgumentException("Invalid hex digit in MAC address.");
      }
   }

   public static void main(String[] args) throws IOException {
      broadcastWakeOnLanMagicPackageForAllNetworkInterfaces("1C:5A:6B:CC:FA:A9");
      broadcastWakeOnLanMagicPackageForAddress("1C:5A:6B:CC:FA:A9", InetAddress.getByName("255.255.255.255"));
   }
}
