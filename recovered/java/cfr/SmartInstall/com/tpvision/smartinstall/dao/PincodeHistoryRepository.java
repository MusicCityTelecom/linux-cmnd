/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.PincodeHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PincodeHistoryRepository
extends JpaRepository<PincodeHistory, Integer>,
JpaSpecificationExecutor<PincodeHistory> {
    @Query(value="select * from pincode_history order by id desc limit 0, ?1", nativeQuery=true)
    public List<PincodeHistory> findByOrderByIdDesc(int var1);

    @Query(value="from PincodeHistory where success=1 and deactivationStatus=0 and roomId = ?1 and str_to_date(stopTime,'%d/%m/%Y %H:%i:%s') >= str_to_date(?2,'%d/%m/%Y %H:%i:%s') order by id desc")
    public List<PincodeHistory> findByRommIdAndStopTime(String var1, String var2);

    @Query(value="from PincodeHistory where success=1 and deactivationStatus=0 and roomId = ?1 and packageName = ?2 and str_to_date(stopTime,'%d/%m/%Y %H:%i:%s') >= str_to_date(?3,'%d/%m/%Y %H:%i:%s') order by id desc")
    public List<PincodeHistory> findByRommIdAndPackageNameAndStopTime(String var1, String var2, String var3);

    @Modifying
    @Query(value="update PincodeHistory set deactivationStatus=1 where deactivationStatus=0 and roomId = ?1 and str_to_date(stopTime,'%d/%m/%Y %H:%i:%s') >= now()")
    public void deactivateAllNotExpiredPincodeForRoom(String var1);

    @Modifying
    @Query(value="update PincodeHistory set deactivationStatus=1 where deactivationStatus=0 and roomId = ?1 and packageName=?2 and str_to_date(stopTime,'%d/%m/%Y %H:%i:%s') >= now()")
    public void deactivatePackageNotExpiredPincodeForRoom(String var1, String var2);

    @Query(value="select count(h) from PincodeHistory h where h.success=1 and h.deactivationStatus=0 and (?1='' or h.roomId like CONCAT('%',?1,'%') or h.packageName like CONCAT('%',?1,'%') or h.stopTime like CONCAT('%',?1,'%') or h.pincode like CONCAT('%',?1,'%')) and str_to_date(h.stopTime,'%d/%m/%Y %H:%i:%s') >= now()")
    public Long countAllActivePincodeHistory(String var1);

    @Query(nativeQuery=true, value="select h.* from pincode_history h where h.success=1 and h.deactivation_status=0 and (?1 = '' or h.room_id like CONCAT('%',?1,'%') or h.package_name like CONCAT('%',?1,'%') or h.stop_time like CONCAT('%',?1,'%') or h.pincode like CONCAT('%',?1,'%')) and str_to_date(h.stop_time,'%d/%m/%Y %H:%i:%s') >= now() order by h.id desc limit ?2, ?3")
    public List<PincodeHistory> findPageDataOfActivePincodeHistory(String var1, int var2, int var3);
}

