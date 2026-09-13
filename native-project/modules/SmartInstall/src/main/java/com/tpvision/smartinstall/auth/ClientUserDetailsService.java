package com.tpvision.smartinstall.auth;

import com.tpvision.smartinstall.dao.core.Profile;
import com.tpvision.smartinstall.dao.core.ProfileRole;
import com.tpvision.smartinstall.dao.core.Role;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.ProfileManager;
import com.tpvision.smartinstall.dao.mgr.ProfileRoleManager;
import com.tpvision.smartinstall.dao.mgr.RoleManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class ClientUserDetailsService implements UserDetailsService {
   private static final Logger logger = LoggerFactory.getLogger(ClientUserDetailsService.class);
   private String passowrd = "passowrd";

   @Override
   public UserDetails loadUserByUsername(String userName) {
      logger.info("loadUserByUsername called here ");
      return new User(userName, this.passowrd, this.getAuthorities(userName));
   }

   private List<GrantedAuthority> getAuthorities(String uname) {
      List<GrantedAuthority> authList = new ArrayList<>();
      ProfileManager pm = JpaManager.getProfileManager();
      Profile p = pm.loadByKey(uname);
      if (null == p) {
         p = new Profile();
         p.setId(uname);
         p.setCreatedBy(uname);
         p.setPassword(DigestUtils.md5Hex(this.passowrd));
      }

      if (p.getCreatedDate() == null) {
         p.setCreatedDate(new Date());
      }

      p.setLastUpdatedDate(new Date());
      pm.save(p);
      ProfileRoleManager prm = JpaManager.getProfileRoleManager();
      List<ProfileRole> prs = prm.loadByProfileId(uname);
      ProfileRole pr = null;
      if (prs.isEmpty()) {
         pr = new ProfileRole();
         pr.setProfileId(uname);
         pr.setRoleIdrole(1);
         prm.save(pr);
      } else {
         pr = prs.get(0);
      }

      int roleid = pr.getRoleIdrole();
      RoleManager rm = JpaManager.getRoleManager();

      for (Role r : rm.loadAll()) {
         if (r.getIdrole() == roleid) {
            authList.add(new SimpleGrantedAuthority(r.getName()));
            break;
         }
      }

      return authList;
   }
}
