/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.Devices;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DevicesRepository
extends JpaRepository<Devices, String>,
JpaSpecificationExecutor<Devices> {
    public List<Devices> findByClonePath(String var1);

    public List<Devices> findByFirmwareid(int var1);

    @Query(nativeQuery=true, value="select * from devices where cast(tvroomid as unsigned) = cast(? as unsigned) and tvroomid is not null")
    public List<Devices> findByTvroomid(String var1);

    @Query(nativeQuery=true, value="select * from devices where (cast(tvroomid as unsigned) = cast(? as unsigned) and tvroomid is not null) and type=?")
    public List<Devices> findByTvroomidAndType(String var1, String var2);

    public List<Devices> findByCloneidAndCloneType(int var1, String var2);

    public List<Devices> findByType(String var1);

    public List<Devices> findByTvipaddress(String var1);

    public List<Devices> findByTvipaddressAndCloneMode(String var1, String var2);

    public List<Devices> findByTvipaddressAndPowerstatus(String var1, String var2);

    public List<Devices> findByTvname(String var1);

    public List<Devices> findByTvuniqueidIn(List<String> var1);

    public Devices findByTvuniqueid(String var1);

    @Query(nativeQuery=true, value="select TVRoomID,Type,tv_firmware_Identifier from devices where TVRoomID in ?1")
    public List<Map<String, Object>> findDeviceTypeAndFirmwareInfoByRooms(Set<String> var1);

    @Modifying
    @Query(value="update Devices set pmsSyncStatus = ?1 where id = ?2")
    public void updateDevicePmsSyncStatus(int var1, String var2);

    @Query(nativeQuery=true, value="select * from devices where id in (select tvid from `groups` where GroupName=?1)")
    public List<Devices> findDevicesByGroupName(String var1);
}

