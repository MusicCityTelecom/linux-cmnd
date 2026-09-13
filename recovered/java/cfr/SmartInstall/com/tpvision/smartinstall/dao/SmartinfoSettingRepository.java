/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.SmartinfoSetting;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmartinfoSettingRepository
extends JpaRepository<SmartinfoSetting, Integer> {
    public List<SmartinfoSetting> findBySmartuiId(int var1);

    public List<SmartinfoSetting> findBySettingId(int var1);
}

