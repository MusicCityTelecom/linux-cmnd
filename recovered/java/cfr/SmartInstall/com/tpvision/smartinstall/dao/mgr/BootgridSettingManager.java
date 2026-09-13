/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.dao.BootgridSettingRepository;
import com.tpvision.smartinstall.dao.core.BootgridSetting;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BootgridSettingManager {
    @Autowired
    private BootgridSettingRepository bootgridSettingRepository;

    public void save(BootgridSetting bootgridSetting) {
        this.bootgridSettingRepository.save(bootgridSetting);
    }

    public List<BootgridSetting> findBootgridSettingByUserAndGridid(String user, String gridid) {
        return this.bootgridSettingRepository.findByUserAndGridid(user, gridid);
    }
}

