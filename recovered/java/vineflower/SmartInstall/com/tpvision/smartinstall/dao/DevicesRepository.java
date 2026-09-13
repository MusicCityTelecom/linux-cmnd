package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.Devices;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DevicesRepository extends JpaRepository<Devices, String>, JpaSpecificationExecutor<Devices> {
   List<Devices> findByClonePath(String var1);

   List<Devices> findByFirmwareid(int var1);

   @Query(nativeQuery = true, value = "select * from devices where cast(tvroomid as unsigned) = cast(? as unsigned) and tvroomid is not null")
   List<Devices> findByTvroomid(String var1);

   @Query(nativeQuery = true, value = "select * from devices where (cast(tvroomid as unsigned) = cast(? as unsigned) and tvroomid is not null) and type=?")
   List<Devices> findByTvroomidAndType(String var1, String var2);

   List<Devices> findByCloneidAndCloneType(int var1, String var2);

   List<Devices> findByType(String var1);

   List<Devices> findByTvipaddress(String var1);

   List<Devices> findByTvipaddressAndCloneMode(String var1, String var2);

   List<Devices> findByTvipaddressAndPowerstatus(String var1, String var2);

   List<Devices> findByTvname(String var1);

   List<Devices> findByTvuniqueidIn(List<String> var1);

   Devices findByTvuniqueid(String var1);

   @Query(nativeQuery = true, value = "select TVRoomID,Type,tv_firmware_Identifier from devices where TVRoomID in ?1")
   List<Map<String, Object>> findDeviceTypeAndFirmwareInfoByRooms(Set<String> var1);

   @Modifying
   @Query("update Devices set pmsSyncStatus = ?1 where id = ?2")
   void updateDevicePmsSyncStatus(int var1, String var2);

   @Query(nativeQuery = true, value = "select * from devices where id in (select tvid from `groups` where GroupName=?1)")
   List<Devices> findDevicesByGroupName(String var1);
}
