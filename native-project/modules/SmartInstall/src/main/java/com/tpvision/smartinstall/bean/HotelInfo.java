package com.tpvision.smartinstall.bean;

import java.util.ArrayList;
import java.util.List;

public class HotelInfo {
   private List<String> hotelinfo = new ArrayList<>();

   public List<String> getHotelinfo() {
      return this.hotelinfo;
   }

   public void setHotelinfo(List<String> fileNames) {
      this.hotelinfo = fileNames;
   }
}
