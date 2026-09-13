/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.BootgridSetting;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BootgridSettingRepository
extends JpaRepository<BootgridSetting, Integer> {
    public List<BootgridSetting> findByUserAndGridid(String var1, String var2);
}

