/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.AppPackage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AppPackageRepository
extends JpaRepository<AppPackage, Integer>,
JpaSpecificationExecutor<AppPackage> {
    public List<AppPackage> findByPlatformIn(List<String> var1);

    public List<AppPackage> findByName(String var1);
}

