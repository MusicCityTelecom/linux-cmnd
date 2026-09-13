package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.WakeupInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WakeupInfoRepository extends JpaRepository<WakeupInfo, Integer> {
   List<WakeupInfo> findByRoomidOrderByWakeuptimeAsc(String var1);

   List<WakeupInfo> findByRoomidAndWakeupid(String var1, String var2);

   WakeupInfo findByRoomidAndWakeuptime(String var1, String var2);

   WakeupInfo findByRoomidAndWakeupidAndWakeuptime(String var1, String var2, String var3);

   @Query(value = "select distinct roomid from wakeup_info", nativeQuery = true)
   List<String> selectDistinctRoomId();

   @Modifying
   @Query(value = "delete from wakeup_info where roomid=:roomId and wakeuptime=:wakeupTime", nativeQuery = true)
   void deleteByRoomIdAndWakeupTime(@Param("roomId") String var1, @Param("wakeupTime") String var2);

   @Modifying
   @Query(value = "delete from wakeup_info where roomid=:roomId and wakeupid=:wakeupId", nativeQuery = true)
   void deleteByRoomIdAndWakeupid(@Param("roomId") String var1, @Param("wakeupId") String var2);

   @Modifying
   @Query(value = "delete from wakeup_info where roomid=:roomId", nativeQuery = true)
   void deleteByRoomId(@Param("roomId") String var1);

   @Modifying
   @Query(value = "update wakeup_info set status=:status where id=:id", nativeQuery = true)
   void updateStatus(@Param("id") int var1, @Param("status") int var2);
}
