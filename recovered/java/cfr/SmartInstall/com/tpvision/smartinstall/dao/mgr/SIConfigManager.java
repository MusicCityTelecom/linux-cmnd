/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.dao.SIConfigRepository;
import com.tpvision.smartinstall.dao.core.SIConfig;
import com.tpvision.smartinstall.util.TpvStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SIConfigManager {
    private static final int CONFIG_ID = 1;
    @Autowired
    private SIConfigRepository siConfigRepository;

    public String getCmndIp() {
        return this.getSIConfig().getCmndIp();
    }

    public SIConfig getSIConfig() {
        return this.siConfigRepository.findById(1).orElse(null);
    }

    public SIConfig saveSIConfig(SIConfig sIConfig) {
        return this.siConfigRepository.save(sIConfig);
    }

    public void setCmndIp(String cmndIp) {
        SIConfig siconfig = this.getSIConfig();
        siconfig.setCmndIp(cmndIp);
        this.siConfigRepository.save(siconfig);
    }

    public String getSecrectKey() {
        SIConfig sIConfig = this.getSIConfig();
        String secretKey = sIConfig.getSecretKey();
        if (StringUtils.isEmpty(secretKey)) {
            secretKey = TpvStringUtils.getRandomString(8);
            sIConfig.setSecretKey(secretKey);
            this.saveSIConfig(sIConfig);
        }
        return secretKey;
    }
}

