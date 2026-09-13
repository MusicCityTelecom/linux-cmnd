/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.SearchParam;
import com.tpvision.smartinstall.dao.UiCustomizationsRepository;
import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.dao.core.UiCustomizations;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.SettingManager;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.TpvDateUtils;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UiCustomizationsManager {
    private static final Logger LOG = LoggerFactory.getLogger(UiCustomizationsManager.class);
    @Autowired
    private UiCustomizationsRepository uiCustomizationsRepository;

    public JSONObject findUiCustomizationsPageBySearchParam(SearchParam sp) {
        try {
            return JpaManager.findSimpleLikeDataPageBySearchParam("uicustomizations", sp);
        }
        catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            return null;
        }
    }

    public List<UiCustomizations> findUiCustomizationsByPlatforms(String ... platforms) {
        return this.uiCustomizationsRepository.findByPlatformIn(Arrays.asList(platforms));
    }

    public UiCustomizations loadByKey(int id) {
        return this.uiCustomizationsRepository.findById(id).orElse(null);
    }

    public List<UiCustomizations> findByName(String name) {
        return this.uiCustomizationsRepository.findByName(name);
    }

    public void deleteByKey(int id) {
        this.uiCustomizationsRepository.deleteById(id);
        this.clearSettingLinkWhenDeleteUiCustomizations(id);
    }

    private void clearSettingLinkWhenDeleteUiCustomizations(int id) {
        SettingManager settingManager = JpaManager.getSettingManager();
        List<Setting> settings = settingManager.findSettingListByUiCustomizationsId(id);
        for (Setting set : settings) {
            set.setUiCustomizationsId(-1);
            set.setLastUpdatedDate(new Date());
            settingManager.save(set);
        }
    }

    public List<UiCustomizations> loadAll() {
        return this.uiCustomizationsRepository.findAll();
    }

    public void save(UiCustomizations obj) {
        if (!obj.isLastEditModified()) {
            obj.setLastEdit(TpvDateUtils.getCurrentIndentifierFormatTime());
        }
        this.uiCustomizationsRepository.save(obj);
        this.syncLastUpdate(obj.getId());
    }

    private void syncLastUpdate(int id) {
        SettingManager settingManager = JpaManager.getSettingManager();
        List<Setting> settings = settingManager.findSettingListByUiCustomizationsId(id);
        for (Setting set : settings) {
            set.setLastUpdatedDate(new Date());
            settingManager.save(set);
        }
    }

    public UiCustomizations copy(int id) {
        UiCustomizations srcItem = this.loadByKey(id);
        UiCustomizations newItem = new UiCustomizations();
        newItem.setName("Copy of " + srcItem.getName());
        newItem.setPlatform(srcItem.getPlatform());
        newItem.setValue(srcItem.getValue());
        newItem.setLastEdit(TpvDateUtils.getCurrentIndentifierFormatTime());
        this.save(newItem);
        File srcDir = new File(this.getPackageDataPath(srcItem.getId()));
        File destDir = new File(this.getPackageDataPath(newItem.getId()));
        destDir.mkdirs();
        try {
            FileUtils.copyDirectory(srcDir, destDir);
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return newItem;
    }

    public String getPackageDataPath(int packageId) {
        return String.format(Locale.ENGLISH, "%suiCustomizations/%d/", CommonConstants.CLONE_PROCESS_LOCATION, packageId);
    }
}

