package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.Billitem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BillItemRepository extends JpaRepository<Billitem, String> {
   @Query(nativeQuery = true, value = "select * from billitem where cast(RoomId as unsigned) = cast(? as unsigned) and RoomId is not null")
   List<Billitem> findByRoomId(String var1);

   List<Billitem> findByRoomIdIsNull();

   @Query(
      nativeQuery = true,
      value = "select sum( cast(BillItemAmount as DECIMAL(20,2))) from billitem where displayFlag=\"Yes\" and cast(RoomId as unsigned) = cast(? as unsigned)"
   )
   String getBillBalance(String var1);
}
