package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.Welcome;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WelcomeRepository extends JpaRepository<Welcome, Integer>, JpaSpecificationExecutor<Welcome> {
   List<Welcome> findByType(Integer var1);

   List<Welcome> findByName(String var1);

   List<Welcome> findByPlatformIn(List<String> var1);
}
