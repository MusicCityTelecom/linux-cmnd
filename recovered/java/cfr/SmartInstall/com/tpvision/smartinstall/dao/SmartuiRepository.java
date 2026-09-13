/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.Smartui;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmartuiRepository
extends JpaRepository<Smartui, Integer> {
    public List<Smartui> findByName(String var1);

    public List<Smartui> findByTypeAndIsdeleteAndId(String var1, String var2, int var3);

    public List<Smartui> findByTypeAndIsdeleteAndName(String var1, String var2, String var3);

    public List<Smartui> findByTypeAndIsdeleteAndNameContaining(String var1, String var2, String var3);
}

