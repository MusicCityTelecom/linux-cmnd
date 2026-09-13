/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.UpgSetting;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UpgSettingRepository
extends JpaRepository<UpgSetting, Integer>,
JpaSpecificationExecutor<UpgSetting> {
    public List<UpgSetting> findByUpgrename(String var1);

    public List<UpgSetting> findByName(String var1);

    public List<UpgSetting> findByPlatformIn(List<String> var1);
}

