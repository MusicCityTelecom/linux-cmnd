package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.Smartui;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmartuiRepository extends JpaRepository<Smartui, Integer> {
   List<Smartui> findByName(String var1);

   List<Smartui> findByTypeAndIsdeleteAndId(String var1, String var2, int var3);

   List<Smartui> findByTypeAndIsdeleteAndName(String var1, String var2, String var3);

   List<Smartui> findByTypeAndIsdeleteAndNameContaining(String var1, String var2, String var3);
}
