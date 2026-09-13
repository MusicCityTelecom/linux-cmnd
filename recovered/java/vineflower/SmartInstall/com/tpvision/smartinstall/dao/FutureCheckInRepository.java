package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.FutureCheckIn;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface FutureCheckInRepository extends JpaRepository<FutureCheckIn, Integer>, JpaSpecificationExecutor<FutureCheckIn> {
   List<FutureCheckIn> findByRoomid(String var1);

   @Query("select t from FutureCheckIn t where t.checkinTime <= ?1")
   List<FutureCheckIn> findRequiredCheckInGuestInfo(String var1);

   @Query(
      value = "select * from future_check_in t where (''= ?1 or t.checkin_time like CONCAT(?1,'%')) and (t.room_id like CONCAT('%',?2,'%') or t.guest_name like CONCAT('%',?2,'%') or t.checkout_time like CONCAT('%',?2,'%') or t.checkin_time like CONCAT('%',?2,'%')) order by cast(room_id as unsigned) asc limit ?3,?4",
      nativeQuery = true
   )
   List<FutureCheckIn> findByKeywordOrderByRoomidAsc(String var1, String var2, int var3, int var4);

   @Query(
      value = "select * from future_check_in t where (''= ?1 or t.checkin_time like CONCAT(?1,'%')) and (t.room_id like CONCAT('%',?2,'%') or t.guest_name like CONCAT('%',?2,'%') or t.checkout_time like CONCAT('%',?2,'%') or t.checkin_time like CONCAT('%',?2,'%')) order by cast(room_id as unsigned) desc limit ?3,?4",
      nativeQuery = true
   )
   List<FutureCheckIn> findByKeywordOrderByRoomidDesc(String var1, String var2, int var3, int var4);
}
