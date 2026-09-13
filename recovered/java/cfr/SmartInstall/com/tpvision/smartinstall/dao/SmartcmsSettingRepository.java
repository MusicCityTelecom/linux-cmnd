/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.SmartcmsSetting;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SmartcmsSettingRepository
extends JpaRepository<SmartcmsSetting, Integer>,
JpaSpecificationExecutor<SmartcmsSetting> {
    public List<SmartcmsSetting> findBySettingId(int var1);
}

