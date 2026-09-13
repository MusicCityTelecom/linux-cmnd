/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.JobLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobLogRepository
extends JpaRepository<JobLog, Integer> {
    public List<JobLog> findByJobName(String var1);
}

