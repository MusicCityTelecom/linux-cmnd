package com.tpvision.smartinstall;

import com.tpvision.smartinstall.util.CommonConstants;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageStates {
   private static final Logger LOG = LoggerFactory.getLogger(ImageStates.class);
   private static final ImageStates SINGLE_INSTANCE = new ImageStates();
   private Map<String, List<File>> imageMapList = new HashMap<>();
   private Map<String, List<File>> imageThumbMapList = new HashMap<>();

   private ImageStates() {
      this.scan();
   }

   private void scan() {
      String[] folders = new String[]{
         CommonConstants.HOTEL_INFO_ES_LOCATION,
         CommonConstants.WELCOME_LOGO_IMG_LOCATION,
         CommonConstants.THEME_IMG_LOCATION,
         CommonConstants.HOTEL_INFO_IMG_LOCATION
      };
      String[] thumbFolders = new String[]{
         CommonConstants.HOTEL_INFO_ES_THUMB_LOCATION,
         CommonConstants.WELCOME_LOGO_THUMB_IMG_LOCATION,
         CommonConstants.THEME_IMG_LOCATION,
         CommonConstants.HOTEL_INFO_THUMB_IMG_LOCATION
      };
      String[] mapNames = new String[]{"smartinfoes", "welcome", "theme", "hotel"};
      int i = 0;

      for (String mapName : mapNames) {
         File file = new File(folders[i]);
         File thumbFile = new File(thumbFolders[i]);
         File[] files = file.listFiles();
         if (null != files && files.length > 0) {
            for (File f : files) {
               if (this.imageMapList.get(mapName) == null) {
                  this.imageMapList.put(mapName, new ArrayList<>());
               }

               if (mapName.indexOf("welcome") > -1) {
                  try {
                     BufferedImage bimg = ImageIO.read(f);
                     int width = null != bimg ? bimg.getWidth() : 0;
                     int height = null != bimg ? bimg.getHeight() : 0;
                     if (width == 1280 && height == 720) {
                        this.imageMapList.get(mapName).add(f);
                     }
                  } catch (IOException e) {
                     LOG.debug(e.getMessage());
                  }
               } else {
                  this.imageMapList.get(mapName).add(f);
               }
            }
         }

         File[] thumbFileList = thumbFile.listFiles();
         if (null != thumbFileList) {
            for (File f : thumbFileList) {
               if (this.imageThumbMapList.get(mapName) == null) {
                  this.imageThumbMapList.put(mapName, new ArrayList<>());
               }

               this.imageThumbMapList.get(mapName).add(f);
            }
         }

         i++;
      }
   }

   public static ImageStates instance() {
      return new ImageStates();
   }

   public Map<String, List<File>> getImageMapList() {
      return this.imageMapList;
   }

   public ImageStates refresh() {
      this.scan();
      return SINGLE_INSTANCE;
   }

   public Map<String, List<File>> getImageThumbMapList() {
      return this.imageThumbMapList;
   }

   public void setImageThumbMapList(Map<String, List<File>> imageThumbMapList) {
      this.imageThumbMapList = imageThumbMapList;
   }
}
