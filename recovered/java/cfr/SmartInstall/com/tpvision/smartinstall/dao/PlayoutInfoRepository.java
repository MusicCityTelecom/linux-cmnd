/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.PlayoutInfo;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlayoutInfoRepository
extends JpaRepository<PlayoutInfo, Integer>,
JpaSpecificationExecutor<PlayoutInfo> {
    public List<PlayoutInfo> findByType(String var1);

    public List<PlayoutInfo> findByName(String var1);

    public List<PlayoutInfo> findByTypeAndName(String var1, String var2);

    public List<PlayoutInfo> findByTypeAndSource(String var1, String var2);

    public List<PlayoutInfo> findByRoomsInAndSource(Set<String> var1, String var2);

    public List<PlayoutInfo> findByTypeAndRoomsInAndSource(String var1, Set<String> var2, String var3);

    public List<PlayoutInfo> findByTypeAndPlatformAndSource(String var1, String var2, String var3);

    public List<PlayoutInfo> findByTypeIn(List<String> var1);

    public List<PlayoutInfo> findByTypeNotIn(List<String> var1);

    public int countByFilePath(String var1);

    @Modifying
    @Query(value="update PlayoutInfo set status=0")
    public void resetStatus();

    @Modifying
    @Query(value="update PlayoutInfo set status = :status where filePath=:filePath")
    public int updateStatusByFilePath(@Param(value="filePath") String var1, @Param(value="status") Integer var2);
}

