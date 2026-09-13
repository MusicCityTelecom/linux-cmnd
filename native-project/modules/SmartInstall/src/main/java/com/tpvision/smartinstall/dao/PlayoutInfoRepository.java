package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.PlayoutInfo;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlayoutInfoRepository extends JpaRepository<PlayoutInfo, Integer>, JpaSpecificationExecutor<PlayoutInfo> {
   List<PlayoutInfo> findByType(String var1);

   List<PlayoutInfo> findByName(String var1);

   List<PlayoutInfo> findByTypeAndName(String var1, String var2);

   List<PlayoutInfo> findByTypeAndSource(String var1, String var2);

   List<PlayoutInfo> findByRoomsInAndSource(Set<String> var1, String var2);

   List<PlayoutInfo> findByTypeAndRoomsInAndSource(String var1, Set<String> var2, String var3);

   List<PlayoutInfo> findByTypeAndPlatformAndSource(String var1, String var2, String var3);

   List<PlayoutInfo> findByTypeIn(List<String> var1);

   List<PlayoutInfo> findByTypeNotIn(List<String> var1);

   int countByFilePath(String var1);

   @Modifying
   @Query("update PlayoutInfo set status=0")
   void resetStatus();

   @Modifying
   @Query("update PlayoutInfo set status = :status where filePath=:filePath")
   int updateStatusByFilePath(@Param("filePath") String var1, @Param("status") Integer var2);
}
