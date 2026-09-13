package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.ProfileRole;
import com.tpvision.smartinstall.dao.core.ProfileRolePK;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface ProfileRoleRepository extends JpaRepository<ProfileRole, ProfileRolePK> {
   List<ProfileRole> findByProfileId(String var1);

   @Modifying
   void deleteByProfileId(String var1);
}
