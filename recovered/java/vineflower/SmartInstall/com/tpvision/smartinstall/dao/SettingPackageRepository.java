package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.SettingPackage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SettingPackageRepository extends JpaRepository<SettingPackage, Integer>, JpaSpecificationExecutor<SettingPackage> {
   List<SettingPackage> findByPlatformIn(List<String> var1);

   List<SettingPackage> findByName(String var1);
}
