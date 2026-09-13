/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.ExApi;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExApiRepository
extends JpaRepository<ExApi, Integer> {
    public List<ExApi> findByApikey(String var1);
}

