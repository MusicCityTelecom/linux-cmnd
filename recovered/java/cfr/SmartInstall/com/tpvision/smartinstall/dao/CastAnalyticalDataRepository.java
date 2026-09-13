/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.CastAnalyticalData;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CastAnalyticalDataRepository
extends JpaRepository<CastAnalyticalData, Integer> {
    @Query(value="select max(`index`) from cast_analytical_data", nativeQuery=true)
    public Long findMaxIndexId();

    @Query(value="select c from CastAnalyticalData c where c.start < :endTime and c.end >= :startTime and (:applicationName = '' or c.applicationName = :applicationName) and (c.applicationName != 'IDLE' and c.applicationName != 'OFFLINE' )")
    public List<CastAnalyticalData> findOverLappedCastAnalyticalDataByTimeFrame(@Param(value="startTime") String var1, @Param(value="endTime") String var2, @Param(value="applicationName") String var3);
}

