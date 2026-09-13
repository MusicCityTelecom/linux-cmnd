package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.Profile;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfileRepository extends JpaRepository<Profile, String> {
   Profile findByIdAndEmail(String var1, String var2);

   @Query(value = "select role.name from role,profile_role where profile_role.role_idrole = role.idrole and profile_role.profile_id = ?1", nativeQuery = true)
   String findUserRole(String var1);

   @Query(
      value = "select profile.id from profile,profile_role,role where profile.id = profile_role.profile_id and profile_role.role_idrole = role.idrole and role.name = ?1",
      nativeQuery = true
   )
   List<String> findUserIdsByRole(String var1);
}
