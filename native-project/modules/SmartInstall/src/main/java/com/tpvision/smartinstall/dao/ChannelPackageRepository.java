package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.ChannelPackage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChannelPackageRepository extends JpaRepository<ChannelPackage, Integer>, JpaSpecificationExecutor<ChannelPackage> {
   List<ChannelPackage> findByPlatformIn(List<String> var1);

   List<ChannelPackage> findByName(String var1);
}
