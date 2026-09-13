package com.tpvision.smartinstall.util;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;

public class LocationManager {
   private LocationManager() {
   }

   public static void saveDataToFile(Location location) throws IOException {
      String str = new Gson().toJson(location, Location.class);
      File file = new File(CommonConstants.LOCATION_MANAGER_STORE);
      FileUtils.writeStringToFile(file, str, StandardCharsets.UTF_8);
   }

   public static Location getLocationFromFile() throws IOException {
      File file = new File(CommonConstants.LOCATION_MANAGER_STORE);
      if (!file.exists()) {
         FileUtils.touch(file);
      }

      return new Gson().fromJson(new FileReader(file), Location.class);
   }
}
