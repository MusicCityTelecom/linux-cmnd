/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.Groups;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface GroupsRepository
extends JpaRepository<Groups, Integer> {
    public List<Groups> findByTvid(String var1);

    public List<Groups> findByTvidOrderByGroupnameAsc(String var1);

    public List<Groups> findByTvidAndPowerstatusOrderByGroupnameAsc(String var1, String var2);

    public List<Groups> findByGroupname(String var1);

    public List<Groups> findByCloneid(Integer var1);

    public Groups findByTvidAndGroupname(String var1, String var2);

    @Query(nativeQuery=true, value="select distinct GroupName as name from `groups`")
    public List<String> findAllGroupNames();

    @Modifying
    public void deleteByGroupname(String var1);
}

