package com.tpvision.smartinstall.schedule;

import com.tpvision.smartinstall.dao.core.Banners;
import com.tpvision.smartinstall.dao.core.UiCustomizations;
import com.tpvision.smartinstall.dao.mgr.BannersManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.UiCustomizationsManager;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.ZipCommonUtils;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandleCloneFolderName extends Job {
   private static final Logger LOG = LoggerFactory.getLogger(HandleCloneFolderName.class);

   @Override
   public String description() {
      return "used to merge process folder name and replace ui/banner folder name to id";
   }

   @Override
   public Job.ExecuteType getExecuteType() {
      return Job.ExecuteType.AUTOMATICALLY;
   }

   @Override
   public boolean isExecuteOnce() {
      return true;
   }

   @Override
   public void execute() {
      LOG.info("start to handle clone save path");
      this.backupOrginalData();
      this.mergeProcessSubDirToAdmin();
      this.renameBannersFolderName();
      this.renameUiFolderName();
      LOG.info("end handle clone save path");
   }

   private void backupOrginalData() {
      String basePath = new File(CommonConstants.CLONE_PROCESS_LOCATION).getParent();
      String backupFileName = basePath + "-" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".zip";
      LOG.info("backupPath:{} -> target zip file:{}", basePath, backupFileName);
      StopWatch backupJob = new StopWatch();
      backupJob.start();
      ZipCommonUtils.zipFiles(basePath, backupFileName);
      backupJob.stop();
      LOG.info("backupPath cost time: {} ms", backupJob.getTime());
   }

   private void mergeProcessSubDirToAdmin() {
      StopWatch mergeJob = new StopWatch();
      mergeJob.start();
      File adminDir = new File(CommonConstants.CLONE_PROCESS_LOCATION);
      if (adminDir.exists() && adminDir.isFile()) {
         boolean isRenameSuccess = adminDir.renameTo(new File(adminDir.getParent() + "/admin.bk"));
         if (isRenameSuccess) {
            LOG.info("exist admin file, rename to backup");
         }

         adminDir = new File(CommonConstants.CLONE_PROCESS_LOCATION);
      }

      if (!adminDir.exists()) {
         adminDir.mkdir();
      }

      File[] adminSubFiles = adminDir.getParentFile().listFiles();
      if (adminSubFiles != null) {
         for (File dir : adminSubFiles) {
            if (!"admin".equalsIgnoreCase(dir.getName()) && !dir.isFile()) {
               LOG.info("start to mv directory: {}", dir);

               try {
                  FileUtils.copyDirectory(dir, adminDir, true);
                  FileUtils.deleteDirectory(dir);
                  LOG.info("merge directory:<{}> success", dir);
               } catch (IOException e) {
                  LOG.error("move directory failure:" + e.getMessage(), e);
               }
            }
         }
      }

      mergeJob.stop();
      LOG.info("merger process admin dir cost:{}ms", mergeJob.getTime());
   }

   private void renameBannersFolderName() {
      StopWatch renameBannerJob = new StopWatch();
      renameBannerJob.start();
      Map<String, String> conflicatedFolderNameMap = new HashMap<>();
      BannersManager bannersManager = JpaManager.getBannersManager();
      String baseBannderPath = CommonConstants.CLONE_PROCESS_LOCATION + "Banners" + File.separator;

      for (Banners banners : bannersManager.loadAll()) {
         String bannerName = banners.getName();
         if (bannerName.equalsIgnoreCase(String.valueOf(banners.getId()))) {
            LOG.info("banners <{}>, id==name skip handle", bannerName);
         } else {
            if (conflicatedFolderNameMap.containsKey(bannerName)) {
               bannerName = conflicatedFolderNameMap.remove(bannerName);
            }

            String fullBannersName = baseBannderPath + bannerName;
            File folder = new File(fullBannersName);
            if (folder.exists() && folder.isDirectory()) {
               String renameTo = String.valueOf(banners.getId());
               File targetDir = new File(baseBannderPath + renameTo);
               if (targetDir.exists()) {
                  String tmpName = UUID.randomUUID().toString();
                  conflicatedFolderNameMap.put(renameTo, tmpName);
                  LOG.info("new name have confilicated, rename confilicated folder to {}", tmpName);
                  targetDir.renameTo(new File(baseBannderPath + tmpName));
               }

               folder.renameTo(new File(baseBannderPath + renameTo));
               LOG.info("rename bannerName {} to {}", bannerName, renameTo);
            }
         }
      }

      renameBannerJob.stop();
      LOG.info("rename bannder folder name cost:{}ms", renameBannerJob.getTime());
   }

   private void renameUiFolderName() {
      StopWatch renameUiJob = new StopWatch();
      renameUiJob.start();
      Map<String, String> conflicatedFolderNameMap = new HashMap<>();
      String uiBasePath = CommonConstants.CLONE_PROCESS_LOCATION + "uiCustomizations" + File.separator;
      UiCustomizationsManager uiCustomizationsManager = JpaManager.getUiCustomizationsManager();

      for (UiCustomizations uiCustomizations : uiCustomizationsManager.loadAll()) {
         String uiName = uiCustomizations.getName();
         if (uiName.equalsIgnoreCase(String.valueOf(uiCustomizations.getId()))) {
            LOG.info("ui <{}>, id==name skip handle", uiName);
         } else {
            if (conflicatedFolderNameMap.containsKey(uiName)) {
               uiName = conflicatedFolderNameMap.remove(uiName);
            }

            String fullUiName = uiBasePath + uiName;
            File folder = new File(fullUiName);
            if (folder.exists() && folder.isDirectory()) {
               String renameTo = String.valueOf(uiCustomizations.getId());
               File targetDir = new File(uiBasePath + renameTo);
               if (targetDir.exists()) {
                  String tmpName = UUID.randomUUID().toString();
                  conflicatedFolderNameMap.put(renameTo, tmpName);
                  targetDir.renameTo(new File(uiBasePath + tmpName));
               }

               folder.renameTo(new File(uiBasePath + renameTo));
               LOG.info("rename UI folderName {} to {}", fullUiName, renameTo);
            } else {
               LOG.warn("UI folderName not exist ", fullUiName);
            }
         }
      }

      renameUiJob.stop();
      LOG.info("rename ui folder name cost:{}ms", renameUiJob.getTime());
   }
}
