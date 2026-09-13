/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.Setting;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SettingRepository
extends JpaRepository<Setting, Integer>,
JpaSpecificationExecutor<Setting> {
    public List<Setting> findBySettingPackageId(int var1);

    public List<Setting> findByAppPackageId(int var1);

    public List<Setting> findByChannelPackageId(int var1);

    public List<Setting> findByBannersId(int var1);

    public List<Setting> findByWelcomeId(int var1);

    public List<Setting> findByScheduleId(int var1);

    public List<Setting> findByUiCustomizationsId(int var1);

    public List<Setting> findByName(String var1);

    public List<Setting> findByPlatform(String var1);

    public List<Setting> findByNameStartingWith(String var1);

    public List<Setting> findByClonerename(String var1);

    public List<Setting> findByLastUpdatedByOrderByLastUpdatedDateDesc(String var1);

    public List<Setting> findByPlatformIn(List<String> var1);

    public List<Setting> findByIdIn(List<Integer> var1);
}

