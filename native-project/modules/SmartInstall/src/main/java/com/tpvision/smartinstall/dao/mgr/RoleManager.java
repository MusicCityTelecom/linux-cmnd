package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.dao.RoleRepository;
import com.tpvision.smartinstall.dao.core.Role;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleManager {
   @Autowired
   private RoleRepository roleRepository;

   public List<Role> loadAll() {
      return this.roleRepository.findAll();
   }

   public Role getRoleByName(String name) {
      return this.roleRepository.findByName(name);
   }
}
