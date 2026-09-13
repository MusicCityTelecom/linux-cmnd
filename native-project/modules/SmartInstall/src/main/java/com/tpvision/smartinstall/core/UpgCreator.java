package com.tpvision.smartinstall.core;

import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.PlatformUtils;
import java.io.File;
import java.util.Map;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpgCreator {
   private static final Logger LOG = LoggerFactory.getLogger(UpgCreator.class);

   public String getParams(String pathname, UpgCreator.UpgCreationCommandArgument uca) {
      StringBuilder sbuild = new StringBuilder();
      File[] files = new File(pathname).listFiles();
      if (null == files) {
         return sbuild.toString();
      }

      if (uca.playout.toUpperCase().contains("F")) {
         for (File f : files) {
            if (f.getName().equalsIgnoreCase("Autorun.upg") && f.length() > 0L) {
               sbuild.append("s* ");
            }
         }
      }

      if (uca.playout.toUpperCase().contains("C")) {
         if (uca.hasAll) {
            sbuild.append("n* c* x* ci* ");

            for (File f : files) {
               if (f.getName().equals("SmartUI") && f.isDirectory()) {
                  sbuild.append("u* ");
               }

               if (f.getName().equalsIgnoreCase("welcomelogo.jpg") && f.length() > 0L) {
                  sbuild.append("w* ");
               }

               if (f.getName().equalsIgnoreCase("hotelinfo.jpg") && f.length() > 0L) {
                  sbuild.append("h* ");
               }

               if (f.getName().equalsIgnoreCase("ThemeTV") && f.isDirectory()) {
                  sbuild.append("t* ");
               }
            }
         } else {
            if (uca.hasHotelInfoPages) {
               for (File f : files) {
                  if (f.getName().equals("SmartUI") && f.isDirectory()) {
                     sbuild.append("u* ci* ");
                  }
               }
            }

            if (uca.hasHotelInfo) {
               for (File f : files) {
                  if (f.getName().equalsIgnoreCase("hotelinfo.jpg") && f.length() > 0L) {
                     sbuild.append("h* ci* ");
                  }
               }
            }

            if (uca.hasWelcomeLogo) {
               for (File f : files) {
                  if (f.getName().equalsIgnoreCase("welcomelogo.jpg") && f.length() > 0L) {
                     sbuild.append("w* ci* ");
                  }
               }
            }

            if (uca.hasThemeTV) {
               for (File f : files) {
                  if (f.getName().equalsIgnoreCase("ThemeTV") && f.isDirectory()) {
                     sbuild.append("t* ci* ");
                  }
               }
            }

            if (uca.hasSetting) {
               sbuild.append("n* c* x* ci* ");
            }
         }
      }

      return sbuild.toString().trim();
   }

   public CommandLine build2K14MSCmd(String pathname, UpgCreator.UpgCreationCommandArgument uca, CommandLine cmdLine, String platformId) {
      File[] files = new File(pathname).listFiles();
      cmdLine.addArgument("0");
      cmdLine.addArgument("PHILIPS_2K14_EU_HTV");
      if (null == files) {
         return cmdLine;
      }

      String rootFolderName = PlatformUtils.getRootFolderName(platformId);
      if (uca.hasAll) {
         if (new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_SSB.BIN").exists()) {
            cmdLine.addArgument("1");
            cmdLine.addArgument(rootFolderName + "_SSB.BIN");
         }

         if (new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_CHTB.BIN").exists()) {
            cmdLine.addArgument("2");
            cmdLine.addArgument(rootFolderName + "_CHTB.BIN");
         }

         if (new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_SSB.xml").exists()) {
            cmdLine.addArgument("3");
            cmdLine.addArgument(rootFolderName + "_SSB.xml");
         }

         if (new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_CHTB.xml").exists()) {
            cmdLine.addArgument("4");
            cmdLine.addArgument(rootFolderName + "_CHTB.xml");
         }

         if (new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_WelcomeLogo.png").exists()) {
            cmdLine.addArgument("5");
            cmdLine.addArgument(rootFolderName + "_WelcomeLogo.png");
         }

         if (new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_SmartInfoImages.zip").exists()) {
            cmdLine.addArgument("6");
            cmdLine.addArgument(rootFolderName + "_SmartInfoImages.zip");
         }

         if (new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_SmartInfoPages.zip").exists()) {
            cmdLine.addArgument("7");
            cmdLine.addArgument(rootFolderName + "_SmartInfoPages.zip");
         }

         if (new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_SystemUIBackup.zip").exists()) {
            cmdLine.addArgument("8");
            cmdLine.addArgument(rootFolderName + "_SystemUIBackup.zip");
         }

         if (new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_MiscSettings.zip").exists()) {
            cmdLine.addArgument("9");
            cmdLine.addArgument(rootFolderName + "_MiscSettings.zip");
         }

         if (new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_VSecureKey.zip").exists()) {
            cmdLine.addArgument("10");
            cmdLine.addArgument(rootFolderName + "_VSecureKey.zip");
         }

         if (new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_CombineMedia.zip").exists()) {
            cmdLine.addArgument("11");
            cmdLine.addArgument(rootFolderName + "_CombineMedia.zip");
         }
      } else {
         if (uca.hasHotelInfo && new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_SmartInfoImages.zip").exists()) {
            cmdLine.addArgument("6");
            cmdLine.addArgument(rootFolderName + "_SmartInfoImages.zip");
         }

         if (uca.hasHotelInfoPages) {
            if (new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_SmartInfoPages.zip").exists()) {
               cmdLine.addArgument("7");
               cmdLine.addArgument(rootFolderName + "_SmartInfoPages.zip");
            }

            if (new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_SystemUIBackup.zip").exists()) {
               cmdLine.addArgument("8");
               cmdLine.addArgument(rootFolderName + "_SystemUIBackup.zip");
            }
         }

         if (uca.hasWelcomeLogo && new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_WelcomeLogo.png").exists()) {
            cmdLine.addArgument("5");
            cmdLine.addArgument(rootFolderName + "_WelcomeLogo.png");
         }

         if (uca.hasSetting) {
            if (new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_SSB.BIN").exists()) {
               cmdLine.addArgument("1");
               cmdLine.addArgument(rootFolderName + "_SSB.BIN");
            }

            if (new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_SSB.xml").exists()) {
               cmdLine.addArgument("3");
               cmdLine.addArgument(rootFolderName + "_SSB.xml");
            }
         }

         if (uca.hasTVChannelList) {
            if (new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_CHTB.BIN").exists()) {
               cmdLine.addArgument("2");
               cmdLine.addArgument(rootFolderName + "_CHTB.BIN");
            }

            if (new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_CHTB.xml").exists()) {
               cmdLine.addArgument("4");
               cmdLine.addArgument(rootFolderName + "_CHTB.xml");
            }
         }
      }

      LOG.info(" cmdLine >> {}", cmdLine);
      return cmdLine;
   }

   public static void creatUpg(String line, String utilDir) {
      LOG.debug(line);
      CommandLine cmdLine = CommandLine.parse(line);
      DefaultExecutor executor = new DefaultExecutor();
      int[] values = new int[]{0, 1};
      executor.setExitValues(values);
      executor.setWorkingDirectory(new File(utilDir));
      ExecuteWatchdog watchdog = new ExecuteWatchdog(-1L);
      executor.setWatchdog(watchdog);
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
      executor.setStreamHandler(streamHandler);

      try {
         executor.execute(cmdLine);
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }
   }

   public static UpgCreator.UpgCreationCommandArgument getCommandArument(Map<String, String> parameters) {
      boolean rfall = false;
      rfall = true;
      String output = parameters.get("output");
      return new UpgCreator.UpgCreationCommandArgument.Builder().all(rfall).rfPlayOut(output).build();
   }

   public static class UpgCreationCommandArgument {
      private boolean hasAll;
      private boolean hasThemeTV;
      private boolean hasWelcomeLogo;
      private boolean hasHotelInfo;
      private boolean hasHotelInfoPages;
      private boolean hasSetting;
      private boolean hasTVChannelList = false;
      private String playout;

      private UpgCreationCommandArgument(UpgCreator.UpgCreationCommandArgument.Builder build) {
         this.hasAll = build.hasAll;
         this.hasHotelInfo = build.hasHotelInfo;
         this.hasHotelInfoPages = build.hasHotelInfoPages;
         this.hasThemeTV = build.hasThemeTV;
         this.hasWelcomeLogo = build.hasWelcomeLogo;
         this.hasSetting = build.hasSetting;
         this.playout = build.output;
         this.playout = build.output;
         this.hasTVChannelList = build.hasTVChannelList;
      }

      public static class Builder {
         private boolean hasAll = true;
         private boolean hasThemeTV = false;
         private boolean hasWelcomeLogo = false;
         private boolean hasHotelInfo = false;
         private boolean hasHotelInfoPages = false;
         private boolean hasSetting = false;
         private boolean hasTVChannelList = false;
         private String output = "C";

         private void toggleAll() {
            if (this.hasAll) {
               this.hasAll = false;
            } else {
               this.hasAll = true;
            }
         }

         public UpgCreator.UpgCreationCommandArgument.Builder all(boolean all) {
            this.hasAll = all;
            return this;
         }

         public UpgCreator.UpgCreationCommandArgument.Builder themeTv(boolean theme) {
            if (this.hasAll && theme) {
               this.toggleAll();
            }

            this.hasThemeTV = theme;
            return this;
         }

         public UpgCreator.UpgCreationCommandArgument.Builder welcomeLogo(boolean wl) {
            if (this.hasAll && wl) {
               this.toggleAll();
            }

            this.hasWelcomeLogo = wl;
            return this;
         }

         public UpgCreator.UpgCreationCommandArgument.Builder hotelInfo(boolean hi) {
            if (this.hasAll && hi) {
               this.toggleAll();
            }

            this.hasHotelInfo = hi;
            return this;
         }

         public UpgCreator.UpgCreationCommandArgument.Builder hotelInfoPages(boolean hip) {
            if (this.hasAll && hip) {
               this.toggleAll();
            }

            this.hasHotelInfoPages = hip;
            return this;
         }

         public UpgCreator.UpgCreationCommandArgument.Builder setting(boolean se) {
            if (this.hasAll && se) {
               this.toggleAll();
            }

            this.hasSetting = se;
            return this;
         }

         public UpgCreator.UpgCreationCommandArgument.Builder tvChannelList(boolean tvChannelList) {
            if (this.hasAll && tvChannelList) {
               this.toggleAll();
            }

            this.hasTVChannelList = tvChannelList;
            return this;
         }

         public UpgCreator.UpgCreationCommandArgument.Builder rfPlayOut(String put) {
            this.output = put;
            return this;
         }

         public UpgCreator.UpgCreationCommandArgument build() {
            return new UpgCreator.UpgCreationCommandArgument(this);
         }
      }
   }
}
