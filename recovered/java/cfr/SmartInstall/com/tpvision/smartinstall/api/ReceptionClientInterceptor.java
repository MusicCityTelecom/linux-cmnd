/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.api;

import com.tpvision.smartinstall.dao.core.ReceptionClient;
import com.tpvision.smartinstall.dao.mgr.ReceptionClientManager;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

public class ReceptionClientInterceptor
implements HandlerInterceptor {
    @Autowired
    private ReceptionClientManager receptionClientManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientId = request.getHeader("x-client-id");
        String clientVersionInfo = request.getHeader("x-client-info");
        if (StringUtils.isNotBlank(clientId) && StringUtils.isNoneBlank(clientVersionInfo)) {
            ReceptionClient receptionClient = this.receptionClientManager.findByClientId(clientId);
            String versionNumber = clientVersionInfo.toLowerCase().replace("reception", "").trim();
            if (receptionClient == null) {
                receptionClient = new ReceptionClient();
                receptionClient.setClientId(clientId);
                receptionClient.setAddTime(new Date());
                receptionClient.setCurrentVersion(versionNumber);
                receptionClient.setStatus(1);
                this.receptionClientManager.save(receptionClient);
            } else {
                boolean isUpdateRequreid = false;
                if (!StringUtils.equalsIgnoreCase(versionNumber, receptionClient.getCurrentVersion())) {
                    receptionClient.setCurrentVersion(versionNumber);
                    isUpdateRequreid = true;
                }
                if (receptionClient.getStatus() != 1) {
                    receptionClient.setStatus(1);
                    isUpdateRequreid = true;
                }
                if (isUpdateRequreid) {
                    this.receptionClientManager.save(receptionClient);
                }
            }
        }
        return true;
    }
}

