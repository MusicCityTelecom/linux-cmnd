package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.Reservation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReservationRepository extends JpaRepository<Reservation, String> {
   @Query(nativeQuery = true, value = "select * from reservation where cast(rooms as unsigned) = cast(? as unsigned) and rooms is not null")
   List<Reservation> findByRooms(String var1);

   @Query(
      nativeQuery = true,
      value = "SELECT * FROM reservation WHERE (DATEDIFF(NOW(),STR_TO_DATE(starttime,'%d/%m/%Y'))>=7 OR DATEDIFF(STR_TO_DATE(endtime,'%d/%m/%Y'),NOW())>=7) AND STATUS='checked_in'"
   )
   List<Reservation> findLongTermBookings();
}
