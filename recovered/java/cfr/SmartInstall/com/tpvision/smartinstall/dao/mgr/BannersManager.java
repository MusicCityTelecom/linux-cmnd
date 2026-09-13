/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.SearchParam;
import com.tpvision.smartinstall.dao.BannersRepository;
import com.tpvision.smartinstall.dao.core.Banners;
import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.SettingManager;
import com.tpvision.smartinstall.util.CloneItemUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BannersManager {
    private static final Logger LOG = LoggerFactory.getLogger(BannersManager.class);
    @Autowired
    private BannersRepository bannersRepository;

    public JSONObject findBannersPageBySearchParam(SearchParam sp) {
        try {
            return JpaManager.findSimpleLikeDataPageBySearchParam("banners", sp);
        }
        catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            return null;
        }
    }

    public List<Banners> findByName(String name) {
        return this.bannersRepository.findByName(name);
    }

    public List<Banners> findByPlatform(String platform) {
        return this.bannersRepository.findByPlatform(platform);
    }

    public Banners loadByKey(int id) {
        return this.bannersRepository.findById(id).orElse(null);
    }

    public void deleteByKey(int id) {
        this.bannersRepository.deleteById(id);
        this.clearSettingLinkWhenDeleteBanners(id);
    }

    private void clearSettingLinkWhenDeleteBanners(int id) {
        SettingManager settingManager = JpaManager.getSettingManager();
        List<Setting> settings = settingManager.findSettingListByBannersId(id);
        for (Setting set : settings) {
            set.setBannersId(-1);
            set.setLastUpdatedDate(new Date());
            settingManager.save(set);
        }
    }

    public List<Banners> loadAll() {
        return this.bannersRepository.findAll();
    }

    public void save(Banners obj) {
        if (!obj.isLastEditModified()) {
            obj.setLastEdit(TpvDateUtils.getCurrentIndentifierFormatTime());
        }
        this.bannersRepository.save(obj);
        this.syncLastUpdate(obj.getId());
    }

    private void syncLastUpdate(int id) {
        SettingManager settingManager = JpaManager.getSettingManager();
        List<Setting> settings = settingManager.findSettingListByBannersId(id);
        for (Setting set : settings) {
            set.setLastUpdatedDate(new Date());
            settingManager.save(set);
        }
    }

    public Banners copy(int id) {
        Banners srcItem = this.loadByKey(id);
        Banners newItem = new Banners();
        String newName = "Copy of " + srcItem.getName();
        while (this.bannerNameExists(newName)) {
            newName = "Copy of " + newName;
        }
        newItem.setName(newName);
        newItem.setPlatform(srcItem.getPlatform());
        newItem.setType(srcItem.getType());
        newItem.setContent(srcItem.getContent());
        newItem.setDate(srcItem.getDate());
        newItem.setOrigin(srcItem.getOrigin());
        newItem.setResponse(srcItem.getResponse());
        newItem.setSchedule(srcItem.getSchedule());
        newItem.setTriggers(srcItem.getTriggers());
        newItem.setLastEdit(TpvDateUtils.getCurrentIndentifierFormatTime());
        this.save(newItem);
        File srcDir = new File(CloneItemUtils.getBannerProcessFolderPath(srcItem));
        File destDir = new File(CloneItemUtils.getBannerProcessFolderPath(newItem));
        destDir.mkdirs();
        try {
            FileUtils.copyDirectory(srcDir, destDir);
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return newItem;
    }

    public List<Banners> findBannersByContentLike(String content) {
        return this.bannersRepository.findByContentLike("%" + content + "%");
    }

    private boolean bannerNameExists(String bannerName) {
        List<Banners> banners = this.loadAll();
        for (Banners banner : banners) {
            if (!bannerName.equalsIgnoreCase(banner.getName())) continue;
            return true;
        }
        return false;
    }
}

