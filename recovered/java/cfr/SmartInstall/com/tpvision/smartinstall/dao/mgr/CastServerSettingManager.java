/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.dao.CastServerSettingRepository;
import com.tpvision.smartinstall.dao.core.CastServerSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CastServerSettingManager {
    private static final int DEFAULT_SETTING_ID = 1;
    @Autowired
    private CastServerSettingRepository castServerSettingRepository;

    public void save(CastServerSetting castServerSetting) {
        castServerSetting.setId(1);
        this.castServerSettingRepository.save(castServerSetting);
    }

    public CastServerSetting findCastServerSetting() {
        return this.castServerSettingRepository.findById(1).orElse(null);
    }

    public void delete(CastServerSetting castServerSetting) {
        this.castServerSettingRepository.delete(castServerSetting);
    }
}

