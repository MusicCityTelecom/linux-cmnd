package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.AppPackage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AppPackageRepository extends JpaRepository<AppPackage, Integer>, JpaSpecificationExecutor<AppPackage> {
   List<AppPackage> findByPlatformIn(List<String> var1);

   List<AppPackage> findByName(String var1);
}
