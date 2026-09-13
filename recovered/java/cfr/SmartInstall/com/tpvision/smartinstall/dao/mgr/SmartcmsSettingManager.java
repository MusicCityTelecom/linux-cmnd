/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.dao.SmartcmsSettingRepository;
import com.tpvision.smartinstall.dao.core.SmartcmsSetting;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmartcmsSettingManager {
    @Autowired
    private SmartcmsSettingRepository smartcmsSettingRepository;

    public void save(SmartcmsSetting smartcmsSetting) {
        this.smartcmsSettingRepository.save(smartcmsSetting);
    }

    public void deleteByKey(int id) {
        this.smartcmsSettingRepository.deleteById(id);
    }

    public List<SmartcmsSetting> findSmartcmsSettingBySettingId(int settingId) {
        return this.smartcmsSettingRepository.findBySettingId(settingId);
    }
}

