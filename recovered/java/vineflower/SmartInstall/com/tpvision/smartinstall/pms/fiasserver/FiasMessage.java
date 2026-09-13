package com.tpvision.smartinstall.pms.fiasserver;

public class FiasMessage {
   public static final byte START_FLAG = 2;
   public static final byte END_FLAG = 3;
   String data;

   public String getData() {
      return this.data;
   }

   public void setData(String data) {
      this.data = data;
   }
}
