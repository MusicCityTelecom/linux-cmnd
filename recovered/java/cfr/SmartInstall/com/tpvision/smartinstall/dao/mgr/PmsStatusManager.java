/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.dao.PmsStatusRepository;
import com.tpvision.smartinstall.dao.core.PmsStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PmsStatusManager {
    @Autowired
    private PmsStatusRepository pmsStatusRepository;

    public PmsStatus loadByKey(int id) {
        return this.pmsStatusRepository.findById(id).orElse(null);
    }

    public void save(PmsStatus pmsStatus) {
        this.pmsStatusRepository.save(pmsStatus);
    }
}

