package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.Roominfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoominfoRepository extends JpaRepository<Roominfo, String>, JpaSpecificationExecutor<Roominfo> {
   List<Roominfo> findByRoomid(String var1);
}
