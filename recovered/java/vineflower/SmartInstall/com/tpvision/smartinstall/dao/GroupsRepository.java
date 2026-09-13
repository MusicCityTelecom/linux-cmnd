package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.Groups;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface GroupsRepository extends JpaRepository<Groups, Integer> {
   List<Groups> findByTvid(String var1);

   List<Groups> findByTvidOrderByGroupnameAsc(String var1);

   List<Groups> findByTvidAndPowerstatusOrderByGroupnameAsc(String var1, String var2);

   List<Groups> findByGroupname(String var1);

   List<Groups> findByCloneid(Integer var1);

   Groups findByTvidAndGroupname(String var1, String var2);

   @Query(nativeQuery = true, value = "select distinct GroupName as name from `groups`")
   List<String> findAllGroupNames();

   @Modifying
   void deleteByGroupname(String var1);
}
