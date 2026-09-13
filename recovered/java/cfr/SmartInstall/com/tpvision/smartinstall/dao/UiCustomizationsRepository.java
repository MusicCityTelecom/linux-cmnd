/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.UiCustomizations;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UiCustomizationsRepository
extends JpaRepository<UiCustomizations, Integer>,
JpaSpecificationExecutor<UiCustomizations> {
    public List<UiCustomizations> findByPlatformIn(List<String> var1);

    public List<UiCustomizations> findByName(String var1);
}

