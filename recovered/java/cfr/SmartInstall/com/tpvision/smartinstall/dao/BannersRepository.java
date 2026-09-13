/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.Banners;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannersRepository
extends JpaRepository<Banners, Integer> {
    public List<Banners> findByName(String var1);

    public List<Banners> findByPlatform(String var1);

    public List<Banners> findByContentLike(String var1);
}

