/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.util.RegexUtils
 */
package org.apereo.cas.services.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.services.domain.RegisteredServiceDomainExtractor;
import org.apereo.cas.util.RegexUtils;

public class DefaultRegisteredServiceDomainExtractor
implements RegisteredServiceDomainExtractor {
    private final Pattern domainExtractor = RegexUtils.createPattern((String)"^\\^?https?\\??://(.*?)(?:[(]?[:/]|$)");
    private final Pattern domainPattern = RegexUtils.createPattern((String)"^[a-z0-9-.]*$");

    @Override
    public String extract(String service) {
        String value = StringUtils.remove((String)service.toLowerCase(), (String)".+");
        Matcher extractor = this.domainExtractor.matcher(value = StringUtils.remove((String)value, (String)".*"));
        return extractor.lookingAt() ? this.validate(extractor.group(1)) : "default";
    }

    private String validate(String providedDomain) {
        String domain = StringUtils.remove((String)providedDomain, (String)"\\");
        Matcher match = this.domainPattern.matcher(StringUtils.remove((String)domain, (String)"\\"));
        return match.matches() ? domain : "default";
    }
}

