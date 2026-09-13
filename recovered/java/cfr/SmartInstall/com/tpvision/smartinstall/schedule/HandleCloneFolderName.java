/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.schedule;

import com.tpvision.smartinstall.dao.core.Banners;
import com.tpvision.smartinstall.dao.core.UiCustomizations;
import com.tpvision.smartinstall.dao.mgr.BannersManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.UiCustomizationsManager;
import com.tpvision.smartinstall.schedule.Job;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.ZipCommonUtils;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandleCloneFolderName
extends Job {
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
        LOG.info("backupPath:{} -> target zip file:{}", (Object)basePath, (Object)backupFileName);
        StopWatch backupJob = new StopWatch();
        backupJob.start();
        ZipCommonUtils.zipFiles(basePath, backupFileName);
        backupJob.stop();
        LOG.info("backupPath cost time: {} ms", (Object)backupJob.getTime());
    }

    private void mergeProcessSubDirToAdmin() {
        File[] adminSubFiles;
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
        if ((adminSubFiles = adminDir.getParentFile().listFiles()) != null) {
            for (File dir : adminSubFiles) {
                if ("admin".equalsIgnoreCase(dir.getName()) || dir.isFile()) continue;
                LOG.info("start to mv directory: {}", (Object)dir);
                try {
                    FileUtils.copyDirectory(dir, adminDir, true);
                    FileUtils.deleteDirectory(dir);
                    LOG.info("merge directory:<{}> success", (Object)dir);
                }
                catch (IOException e) {
                    LOG.error("move directory failure:" + e.getMessage(), e);
                }
            }
        }
        mergeJob.stop();
        LOG.info("merger process admin dir cost:{}ms", (Object)mergeJob.getTime());
    }

    private void renameBannersFolderName() {
        StopWatch renameBannerJob = new StopWatch();
        renameBannerJob.start();
        HashMap<String, String> conflicatedFolderNameMap = new HashMap<String, String>();
        BannersManager bannersManager = JpaManager.getBannersManager();
        String baseBannderPath = CommonConstants.CLONE_PROCESS_LOCATION + "Banners" + File.separator;
        for (Banners banners : bannersManager.loadAll()) {
            String fullBannersName;
            File folder;
            String bannerName = banners.getName();
            if (bannerName.equalsIgnoreCase(String.valueOf(banners.getId()))) {
                LOG.info("banners <{}>, id==name skip handle", (Object)bannerName);
                continue;
            }
            if (conflicatedFolderNameMap.containsKey(bannerName)) {
                bannerName = (String)conflicatedFolderNameMap.remove(bannerName);
            }
            if (!(folder = new File(fullBannersName = baseBannderPath + bannerName)).exists() || !folder.isDirectory()) continue;
            String renameTo = String.valueOf(banners.getId());
            File targetDir = new File(baseBannderPath + renameTo);
            if (targetDir.exists()) {
                String tmpName = UUID.randomUUID().toString();
                conflicatedFolderNameMap.put(renameTo, tmpName);
                LOG.info("new name have confilicated, rename confilicated folder to {}", (Object)tmpName);
                targetDir.renameTo(new File(baseBannderPath + tmpName));
            }
            folder.renameTo(new File(baseBannderPath + renameTo));
            LOG.info("rename bannerName {} to {}", (Object)bannerName, (Object)renameTo);
        }
        renameBannerJob.stop();
        LOG.info("rename bannder folder name cost:{}ms", (Object)renameBannerJob.getTime());
    }

    private void renameUiFolderName() {
        StopWatch renameUiJob = new StopWatch();
        renameUiJob.start();
        HashMap<String, String> conflicatedFolderNameMap = new HashMap<String, String>();
        String uiBasePath = CommonConstants.CLONE_PROCESS_LOCATION + "uiCustomizations" + File.separator;
        UiCustomizationsManager uiCustomizationsManager = JpaManager.getUiCustomizationsManager();
        for (UiCustomizations uiCustomizations : uiCustomizationsManager.loadAll()) {
            String fullUiName;
            File folder;
            String uiName = uiCustomizations.getName();
            if (uiName.equalsIgnoreCase(String.valueOf(uiCustomizations.getId()))) {
                LOG.info("ui <{}>, id==name skip handle", (Object)uiName);
                continue;
            }
            if (conflicatedFolderNameMap.containsKey(uiName)) {
                uiName = (String)conflicatedFolderNameMap.remove(uiName);
            }
            if ((folder = new File(fullUiName = uiBasePath + uiName)).exists() && folder.isDirectory()) {
                String renameTo = String.valueOf(uiCustomizations.getId());
                File targetDir = new File(uiBasePath + renameTo);
                if (targetDir.exists()) {
                    String tmpName = UUID.randomUUID().toString();
                    conflicatedFolderNameMap.put(renameTo, tmpName);
                    targetDir.renameTo(new File(uiBasePath + tmpName));
                }
                folder.renameTo(new File(uiBasePath + renameTo));
                LOG.info("rename UI folderName {} to {}", (Object)fullUiName, (Object)renameTo);
                continue;
            }
            LOG.warn("UI folderName not exist ", (Object)fullUiName);
        }
        renameUiJob.stop();
        LOG.info("rename ui folder name cost:{}ms", (Object)renameUiJob.getTime());
    }
}

