/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.SearchParam;
import com.tpvision.smartinstall.dao.UpgSettingRepository;
import com.tpvision.smartinstall.dao.core.UpgSetting;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.util.PlatformUtils;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UpgSettingManager {
    private static final Logger LOG = LoggerFactory.getLogger(UpgSettingManager.class);
    @Autowired
    private UpgSettingRepository upgSettingRepository;

    public JSONObject findUpgSettingPageBySearchParam(SearchParam sp) {
        try {
            return JpaManager.findSimpleLikeDataPageBySearchParam("upg_setting", sp);
        }
        catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            return null;
        }
    }

    public List<UpgSetting> findUpgSettingsByPlatforms(String ... platforms) {
        List<String> platformList = Arrays.asList(platforms);
        List<String> platformIdList = platformList.stream().map(PlatformUtils::getPlatformType).collect(Collectors.toList());
        return this.upgSettingRepository.findByPlatformIn(platformIdList);
    }

    public UpgSetting loadByKey(int id) {
        return this.upgSettingRepository.findById(id).orElse(null);
    }

    public List<UpgSetting> loadAll() {
        return this.upgSettingRepository.findAll();
    }

    public List<UpgSetting> loadAllOrderByCreateDateDesc() {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        return this.upgSettingRepository.findAll(sort);
    }

    public List<UpgSetting> findByUpgRename(String upgRename) {
        return this.upgSettingRepository.findByUpgrename(upgRename);
    }

    public List<UpgSetting> findByName(String name) {
        return this.upgSettingRepository.findByName(name);
    }

    public void deleteByKey(int id) {
        this.upgSettingRepository.deleteById(id);
    }

    public void saveUpgSetting(UpgSetting upgSetting) {
        this.upgSettingRepository.save(upgSetting);
    }
}

