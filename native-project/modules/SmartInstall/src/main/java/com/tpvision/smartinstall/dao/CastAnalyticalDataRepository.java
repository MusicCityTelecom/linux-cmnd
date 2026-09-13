package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.CastAnalyticalData;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CastAnalyticalDataRepository extends JpaRepository<CastAnalyticalData, Integer> {
   @Query(value = "select max(`index`) from cast_analytical_data", nativeQuery = true)
   Long findMaxIndexId();

   @Query(
      "select c from CastAnalyticalData c where c.start < :endTime and c.end >= :startTime and (:applicationName = '' or c.applicationName = :applicationName) and (c.applicationName != 'IDLE' and c.applicationName != 'OFFLINE' )"
   )
   List<CastAnalyticalData> findOverLappedCastAnalyticalDataByTimeFrame(
      @Param("startTime") String var1, @Param("endTime") String var2, @Param("applicationName") String var3
   );
}
