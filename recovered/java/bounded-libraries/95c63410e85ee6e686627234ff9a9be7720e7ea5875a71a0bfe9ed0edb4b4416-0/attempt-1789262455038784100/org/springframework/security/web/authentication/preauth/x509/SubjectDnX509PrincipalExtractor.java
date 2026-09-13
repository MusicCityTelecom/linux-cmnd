/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.context.MessageSource
 *  org.springframework.context.MessageSourceAware
 *  org.springframework.context.support.MessageSourceAccessor
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.authentication.BadCredentialsException
 *  org.springframework.security.core.SpringSecurityMessageSource
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.authentication.preauth.x509;

import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.web.authentication.preauth.x509.X509PrincipalExtractor;
import org.springframework.util.Assert;

public class SubjectDnX509PrincipalExtractor
implements X509PrincipalExtractor,
MessageSourceAware {
    protected final Log logger = LogFactory.getLog(this.getClass());
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private Pattern subjectDnPattern;

    public SubjectDnX509PrincipalExtractor() {
        this.setSubjectDnRegex("CN=(.*?)(?:,|$)");
    }

    @Override
    public Object extractPrincipal(X509Certificate clientCert) {
        String subjectDN = clientCert.getSubjectDN().getName();
        this.logger.debug((Object)LogMessage.format((String)"Subject DN is '%s'", (Object)subjectDN));
        Matcher matcher = this.subjectDnPattern.matcher(subjectDN);
        if (!matcher.find()) {
            throw new BadCredentialsException(this.messages.getMessage("SubjectDnX509PrincipalExtractor.noMatching", new Object[]{subjectDN}, "No matching pattern was found in subject DN: {0}"));
        }
        Assert.isTrue((matcher.groupCount() == 1 ? 1 : 0) != 0, (String)"Regular expression must contain a single group ");
        String username = matcher.group(1);
        this.logger.debug((Object)LogMessage.format((String)"Extracted Principal name is '%s'", (Object)username));
        return username;
    }

    public void setSubjectDnRegex(String subjectDnRegex) {
        Assert.hasText((String)subjectDnRegex, (String)"Regular expression may not be null or empty");
        this.subjectDnPattern = Pattern.compile(subjectDnRegex, 2);
    }

    public void setMessageSource(MessageSource messageSource) {
        Assert.notNull((Object)messageSource, (String)"messageSource cannot be null");
        this.messages = new MessageSourceAccessor(messageSource);
    }
}

