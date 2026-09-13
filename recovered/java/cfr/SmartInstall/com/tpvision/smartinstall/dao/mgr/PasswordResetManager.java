/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.dao.PasswordResetRepository;
import com.tpvision.smartinstall.dao.core.PasswordReset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetManager {
    @Autowired
    private PasswordResetRepository passwordResetRepository;

    public void savePasswordReset(PasswordReset passwordReset) {
        this.passwordResetRepository.save(passwordReset);
    }

    public PasswordReset getPasswordResetById(int resetId) {
        return this.passwordResetRepository.findById(resetId).orElse(null);
    }
}

