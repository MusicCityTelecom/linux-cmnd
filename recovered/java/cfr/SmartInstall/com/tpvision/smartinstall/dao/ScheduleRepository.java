/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.Schedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ScheduleRepository
extends JpaRepository<Schedule, Integer>,
JpaSpecificationExecutor<Schedule> {
    public List<Schedule> findByPlatformIn(List<String> var1);

    public List<Schedule> findByName(String var1);
}

