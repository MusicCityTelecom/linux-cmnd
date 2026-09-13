/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.WakeupInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WakeupInfoRepository
extends JpaRepository<WakeupInfo, Integer> {
    public List<WakeupInfo> findByRoomidOrderByWakeuptimeAsc(String var1);

    public List<WakeupInfo> findByRoomidAndWakeupid(String var1, String var2);

    public WakeupInfo findByRoomidAndWakeuptime(String var1, String var2);

    public WakeupInfo findByRoomidAndWakeupidAndWakeuptime(String var1, String var2, String var3);

    @Query(value="select distinct roomid from wakeup_info", nativeQuery=true)
    public List<String> selectDistinctRoomId();

    @Modifying
    @Query(value="delete from wakeup_info where roomid=:roomId and wakeuptime=:wakeupTime", nativeQuery=true)
    public void deleteByRoomIdAndWakeupTime(@Param(value="roomId") String var1, @Param(value="wakeupTime") String var2);

    @Modifying
    @Query(value="delete from wakeup_info where roomid=:roomId and wakeupid=:wakeupId", nativeQuery=true)
    public void deleteByRoomIdAndWakeupid(@Param(value="roomId") String var1, @Param(value="wakeupId") String var2);

    @Modifying
    @Query(value="delete from wakeup_info where roomid=:roomId", nativeQuery=true)
    public void deleteByRoomId(@Param(value="roomId") String var1);

    @Modifying
    @Query(value="update wakeup_info set status=:status where id=:id", nativeQuery=true)
    public void updateStatus(@Param(value="id") int var1, @Param(value="status") int var2);
}

