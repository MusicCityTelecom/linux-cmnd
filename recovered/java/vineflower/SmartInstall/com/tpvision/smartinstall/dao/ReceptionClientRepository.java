package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.ReceptionClient;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReceptionClientRepository extends JpaRepository<ReceptionClient, Integer>, JpaSpecificationExecutor<ReceptionClient> {
   ReceptionClient findByClientId(String var1);

   List<ReceptionClient> findByStatus(int var1);
}
