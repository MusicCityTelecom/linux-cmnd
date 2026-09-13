package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.TriggerHistoryInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TriggerHistoryRepository extends JpaRepository<TriggerHistoryInfo, Integer> {
   @Modifying
   @Query("update trigger_history set result=:result where id=:id")
   void updateResult(@Param("id") int var1, @Param("result") String var2);

   @Modifying
   @Query(value = "truncate table trigger_history", nativeQuery = true)
   void truncateTable();
}
