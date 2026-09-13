/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.DiscriminatorValue
 *  javax.persistence.Entity
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.services.CasRegisteredService
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.web.context.request.RequestContextHolder
 *  org.springframework.web.context.request.ServletRequestAttributes
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
    protected final Logger logger = LoggerFactory.getLogger(((Object)((Object)this)).getClass());

    public boolean matches(String serviceId) {
        boolean isMatch = super.matches(serviceId);
        if (isMatch) {
            isMatch = this.checkLocalHostIpAddress(serviceId);
        }
        return isMatch;
    }

    public boolean checkLocalHostIpAddress(String serviceId) {
        boolean isContainsLocalhost = StringUtils.containsIgnoreCase((CharSequence)serviceId, (CharSequence)"localhost");
        if (isContainsLocalhost) {
            ServletRequestAttributes requestAttrs = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
            if (requestAttrs != null) {
                HttpServletRequest request = requestAttrs.getRequest();
                String ipAddress = request.getRemoteAddr();
                boolean isValidRemoteAddress = StringUtils.equalsIgnoreCase((CharSequence)"127.0.0.1", (CharSequence)ipAddress) || StringUtils.equalsIgnoreCase((CharSequence)"0:0:0:0:0:0:0:1", (CharSequence)ipAddress) || StringUtils.equalsIgnoreCase((CharSequence)"::1", (CharSequence)ipAddress);
                this.logger.info("serviceid:{} installed on localhost, request remote ip:{}, checkResult:{}", new Object[]{serviceId, ipAddress, isValidRemoteAddress});
                return isValidRemoteAddress;
            }
        } else {
            this.logger.info("serviceid:{} not installed on localhost", (Object)serviceId);
        }
        return true;
    }
}

