package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.dao.ProfileRoleRepository;
import com.tpvision.smartinstall.dao.core.ProfileRole;
import com.tpvision.smartinstall.dao.core.ProfileRolePK;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProfileRoleManager {
   @Autowired
   private ProfileRoleRepository profileRoleRepository;

   public void save(ProfileRole profileRole) {
      this.profileRoleRepository.save(profileRole);
   }

   public void deleteByKey(String profileId) {
      this.profileRoleRepository.deleteByProfileId(profileId);
   }

   public void deleteByKey(String profileId, int idRole) {
      ProfileRolePK pk = new ProfileRolePK();
      pk.setProfileId(profileId);
      pk.setRoleIdrole(idRole);
      this.profileRoleRepository.deleteById(pk);
   }

   public List<ProfileRole> loadByProfileId(String profileId) {
      return this.profileRoleRepository.findByProfileId(profileId);
   }
}
