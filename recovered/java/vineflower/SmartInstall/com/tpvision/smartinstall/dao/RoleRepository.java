package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
   Role findByName(String var1);
}
