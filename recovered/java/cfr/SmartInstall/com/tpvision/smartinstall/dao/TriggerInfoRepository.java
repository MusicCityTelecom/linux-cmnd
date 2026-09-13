/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.TriggerInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TriggerInfoRepository
extends JpaRepository<TriggerInfo, Integer>,
JpaSpecificationExecutor<TriggerInfo> {
    public List<TriggerInfo> findByTriggerActiveAndTriggerTypeAndTriggerCondition(String var1, String var2, String var3);

    public List<TriggerInfo> findByTriggerActiveAndTriggerTypeAndTarget(String var1, String var2, String var3);

    public List<TriggerInfo> findByTriggerTypeAndTarget(String var1, String var2);

    public List<TriggerInfo> findByTarget(String var1);

    public List<TriggerInfo> findByTriggerActiveAndTriggerType(String var1, String var2);

    public List<TriggerInfo> findByTriggerActive(String var1);

    public List<TriggerInfo> findByCloneTypeAndCloneId(String var1, Integer var2);

    public List<TriggerInfo> findByTriggerType(String var1);
}

