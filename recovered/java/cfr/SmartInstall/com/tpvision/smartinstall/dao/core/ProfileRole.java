/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.core;

import com.tpvision.smartinstall.dao.core.ProfileRolePK;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="profile_role")
@IdClass(value=ProfileRolePK.class)
public class ProfileRole {
    @Id
    @Column(name="profile_id")
    private String profileId;
    @Id
    @Column(name="role_idrole")
    private int roleIdrole;

    public String getProfileId() {
        return this.profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public int getRoleIdrole() {
        return this.roleIdrole;
    }

    public void setRoleIdrole(int roleIdrole) {
        this.roleIdrole = roleIdrole;
    }
}

