/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.ProfileRole;
import com.tpvision.smartinstall.dao.core.ProfileRolePK;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface ProfileRoleRepository
extends JpaRepository<ProfileRole, ProfileRolePK> {
    public List<ProfileRole> findByProfileId(String var1);

    @Modifying
    public void deleteByProfileId(String var1);
}

