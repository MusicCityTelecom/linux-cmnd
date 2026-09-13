/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.dao.ProfileRepository;
import com.tpvision.smartinstall.dao.core.Profile;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileManager {
    @Autowired
    private ProfileRepository profileRepository;

    public Profile loadByKey(String username) {
        return this.profileRepository.findById(username).orElse(null);
    }

    public void deleteByKey(String username) {
        this.profileRepository.deleteById(username);
    }

    public Profile findProfileByIdAndEmail(String username, String email) {
        return this.profileRepository.findByIdAndEmail(username, email);
    }

    public Profile save(Profile profile) {
        return this.profileRepository.save(profile);
    }

    public List<Profile> loadAll() {
        return this.profileRepository.findAll();
    }

    public String findRoleNameByUsername(String userName) {
        return this.profileRepository.findUserRole(userName);
    }

    public List<String> findUserIdsByRole(String roleName) {
        return this.profileRepository.findUserIdsByRole(roleName);
    }
}

