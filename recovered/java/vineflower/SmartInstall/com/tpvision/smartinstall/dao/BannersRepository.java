package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.Banners;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannersRepository extends JpaRepository<Banners, Integer> {
   List<Banners> findByName(String var1);

   List<Banners> findByPlatform(String var1);

   List<Banners> findByContentLike(String var1);
}
