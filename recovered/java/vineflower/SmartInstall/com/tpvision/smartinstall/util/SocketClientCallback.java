package com.tpvision.smartinstall.util;

public interface SocketClientCallback {
   void onError(Throwable var1);

   void onConnected();

   void onDisconnected();

   void onReceived(byte[] var1);

   void onSent(byte[] var1);
}
