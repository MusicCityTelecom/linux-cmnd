/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.schedule;

import com.tpvision.smartinstall.dao.core.UiCustomizations;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.schedule.Job;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.TpvFileUtils;
import com.tpvision.smartinstall.util.Utils;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneratePSUILogoThumbJob
extends Job {
    private static final Logger LOG = LoggerFactory.getLogger(GeneratePSUILogoThumbJob.class);

    @Override
    public String description() {
        return "use this job to sync ui customization hotel logo thumb to project work directory request supported folder";
    }

    @Override
    public Job.ExecuteType getExecuteType() {
        return Job.ExecuteType.ALL;
    }

    @Override
    public boolean isExecuteOnce() {
        return false;
    }

    @Override
    public void execute() {
        File file;
        File[] icons;
        LOG.info("start to sync ui hotel log thumb");
        List<UiCustomizations> uiCustomizationList = JpaManager.getUiCustomizationsManager().loadAll();
        for (UiCustomizations uiCustomization : uiCustomizationList) {
            File logoPath;
            File hotelLogoFile;
            if (!PlatformUtils.isSupportHotelImage(uiCustomization) || (hotelLogoFile = TpvFileUtils.getFileByName(logoPath = Utils.getUILogoPath(uiCustomization.getId()), "", CommonConstants.SUPPORTED_LOGO_FORMAT.toArray(new String[0]))) == null) continue;
            try {
                Utils.deployImgFileToSIServiceDir(hotelLogoFile, FilenameUtils.getExtension(hotelLogoFile.getName()));
            }
            catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        File hotelThumbDir = new File(CommonConstants.servletContextPath + "/static/images/uiCustomizations/logo/");
        if (!hotelThumbDir.exists()) {
            hotelThumbDir.mkdirs();
        }
        if ((icons = (file = new File(CommonConstants.UI_LOGO_IMG_LOCATION)).listFiles()) != null) {
            for (File icon : icons) {
                File cached = new File(hotelThumbDir.getAbsolutePath() + "/" + icon.getName());
                if (cached.exists()) continue;
                TpvFileUtils.exportThumbnailFile(icon, cached);
            }
        }
        LOG.info("end handle sync ui hotel log thumb");
    }
}

