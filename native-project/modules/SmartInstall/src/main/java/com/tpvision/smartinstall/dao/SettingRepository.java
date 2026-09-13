package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.Setting;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SettingRepository extends JpaRepository<Setting, Integer>, JpaSpecificationExecutor<Setting> {
   List<Setting> findBySettingPackageId(int var1);

   List<Setting> findByAppPackageId(int var1);

   List<Setting> findByChannelPackageId(int var1);

   List<Setting> findByBannersId(int var1);

   List<Setting> findByWelcomeId(int var1);

   List<Setting> findByScheduleId(int var1);

   List<Setting> findByUiCustomizationsId(int var1);

   List<Setting> findByName(String var1);

   List<Setting> findByPlatform(String var1);

   List<Setting> findByNameStartingWith(String var1);

   List<Setting> findByClonerename(String var1);

   List<Setting> findByLastUpdatedByOrderByLastUpdatedDateDesc(String var1);

   List<Setting> findByPlatformIn(List<String> var1);

   List<Setting> findByIdIn(List<Integer> var1);
}
