/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.TriggerHistoryInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TriggerHistoryRepository
extends JpaRepository<TriggerHistoryInfo, Integer> {
    @Modifying
    @Query(value="update trigger_history set result=:result where id=:id")
    public void updateResult(@Param(value="id") int var1, @Param(value="result") String var2);

    @Modifying
    @Query(value="truncate table trigger_history", nativeQuery=true)
    public void truncateTable();
}

