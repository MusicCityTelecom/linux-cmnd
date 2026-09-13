/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.services;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.services.CasRegisteredService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Entity
@DiscriminatorValue(value="regex")
public class TpvRegexRegisteredService
extends CasRegisteredService {
    private static final long serialVersionUID = 756611775875823055L;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean matches(String serviceId) {
        boolean isMatch = super.matches(serviceId);
        if (isMatch) {
            isMatch = this.checkLocalHostIpAddress(serviceId);
        }
        return isMatch;
    }

    public boolean checkLocalHostIpAddress(String serviceId) {
        boolean isContainsLocalhost = StringUtils.containsIgnoreCase(serviceId, "localhost");
        if (isContainsLocalhost) {
            ServletRequestAttributes requestAttrs = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
            if (requestAttrs != null) {
                HttpServletRequest request = requestAttrs.getRequest();
                String ipAddress = request.getRemoteAddr();
                boolean isValidRemoteAddress = StringUtils.equalsIgnoreCase("127.0.0.1", ipAddress) || StringUtils.equalsIgnoreCase("0:0:0:0:0:0:0:1", ipAddress) || StringUtils.equalsIgnoreCase("::1", ipAddress);
                this.logger.info("serviceid:{} installed on localhost, request remote ip:{}, checkResult:{}", serviceId, ipAddress, isValidRemoteAddress);
                return isValidRemoteAddress;
            }
        } else {
            this.logger.info("serviceid:{} not installed on localhost", (Object)serviceId);
        }
        return true;
    }
}

