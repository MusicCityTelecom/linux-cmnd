/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.SearchParam;
import com.tpvision.smartinstall.dao.ChannelPackageRepository;
import com.tpvision.smartinstall.dao.core.ChannelPackage;
import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.SettingManager;
import com.tpvision.smartinstall.util.CloneItemUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.Utils;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelPackageManager {
    private static final Logger LOG = LoggerFactory.getLogger(ChannelPackageManager.class);
    @Autowired
    private ChannelPackageRepository channelPackageRepository;

    public JSONObject findChannelPackagePageBySearchParam(SearchParam sp) {
        try {
            return JpaManager.findSimpleLikeDataPageBySearchParam("channelPackage", sp);
        }
        catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            return null;
        }
    }

    public List<ChannelPackage> findByName(String name) {
        return this.channelPackageRepository.findByName(name);
    }

    public List<ChannelPackage> findChannelPackagesByPlatforms(String ... platforms) {
        return this.channelPackageRepository.findByPlatformIn(Arrays.asList(platforms));
    }

    public ChannelPackage loadByKey(int id) {
        return this.channelPackageRepository.findById(id).orElse(null);
    }

    public void deleteByKey(int id) {
        ChannelPackage channelPackage = this.loadByKey(id);
        if (channelPackage != null) {
            this.channelPackageRepository.delete(channelPackage);
            this.clearSettingLinkWhenDeleteChannelPackage(channelPackage);
        }
    }

    private void clearSettingLinkWhenDeleteChannelPackage(ChannelPackage cp) {
        SettingManager settingManager = JpaManager.getSettingManager();
        List<Setting> setWithSameCP = settingManager.findSettingListByChannelPackageId(cp.getId());
        for (Setting s : setWithSameCP) {
            s.setChannelPackageId(-1);
            s.setLastUpdatedDate(new Date());
            settingManager.save(s);
        }
    }

    public List<ChannelPackage> loadAll() {
        return this.channelPackageRepository.findAll();
    }

    public void save(ChannelPackage obj) {
        if (!obj.isLastEditModified()) {
            obj.setLastEdit(TpvDateUtils.getCurrentIndentifierFormatTime());
        }
        this.channelPackageRepository.save(obj);
        this.updateChannelToSettingsWithSameCP(obj);
    }

    private void updateChannelToSettingsWithSameCP(ChannelPackage toSave) {
        SettingManager settingManager = JpaManager.getSettingManager();
        List<Setting> setWithSameCP = settingManager.findSettingListByChannelPackageId(toSave.getId());
        for (Setting s : setWithSameCP) {
            s.setLastUpdatedDate(new Date());
            settingManager.save(s);
        }
    }

    public ChannelPackage copy(int id) {
        ChannelPackage srcItem = this.loadByKey(id);
        ChannelPackage newItem = new ChannelPackage();
        newItem.setName("Copy of " + srcItem.getName());
        newItem.setPlatform(srcItem.getPlatform());
        newItem.setNumberOfChs(srcItem.getNumberOfChs());
        newItem.setValue(srcItem.getValue());
        newItem.setConfigName(srcItem.getConfigName());
        newItem.setLastEdit(TpvDateUtils.getCurrentIndentifierFormatTime());
        newItem.setCreatedBy(Utils.getAuthenticationName());
        this.save(newItem);
        File srcDir = new File(CloneItemUtils.getChannelPackageDataPath(srcItem));
        File destDir = new File(CloneItemUtils.getChannelPackageDataPath(newItem));
        destDir.mkdirs();
        try {
            FileUtils.copyDirectory(srcDir, destDir);
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return newItem;
    }
}

