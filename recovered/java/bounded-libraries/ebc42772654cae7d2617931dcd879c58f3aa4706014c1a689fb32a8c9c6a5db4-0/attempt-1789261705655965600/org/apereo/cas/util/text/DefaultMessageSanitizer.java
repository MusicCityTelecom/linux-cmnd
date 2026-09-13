/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 */
package org.apereo.cas.util.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.util.InetAddressUtils;
import org.apereo.cas.util.text.MessageSanitizer;

public class DefaultMessageSanitizer
implements MessageSanitizer {
    private static final Pattern SENSITIVE_TEXT_PATTERN = Pattern.compile("(clientSecret|password|token|credential|secret)\\s*=\\s*(['\"]*\\S+\\b['\"]*)");
    private static final Boolean CAS_TICKET_ID_SANITIZE_SKIP = Boolean.getBoolean("CAS_TICKET_ID_SANITIZE_SKIP");
    private static final int VISIBLE_TAIL_LENGTH = 7;
    private static final int OBFUSCATION_LENGTH = 8;
    public static final String OBFUSCATED_STRING = "*".repeat(8);
    private static final int HOST_NAME_LENGTH = InetAddressUtils.getCasServerHostName().length();
    private final Pattern ticketIdPattern;

    @Override
    public String sanitize(String msg) {
        Matcher matcher;
        String modifiedMessage = msg;
        if (StringUtils.isNotBlank((CharSequence)msg) && !CAS_TICKET_ID_SANITIZE_SKIP.booleanValue()) {
            matcher = this.ticketIdPattern.matcher(msg);
            while (matcher.find()) {
                String match = matcher.group();
                String group = matcher.group(1);
                int length = group.length();
                int replaceLength = length - 7 - (HOST_NAME_LENGTH + 1);
                if (replaceLength <= 0) {
                    replaceLength = length;
                }
                String newId = match.replace(group.substring(0, replaceLength), OBFUSCATED_STRING);
                modifiedMessage = modifiedMessage.replace(match, newId);
            }
        }
        matcher = SENSITIVE_TEXT_PATTERN.matcher(msg);
        while (matcher.find()) {
            String group = matcher.group(2);
            modifiedMessage = modifiedMessage.replace(group, OBFUSCATED_STRING);
        }
        return modifiedMessage;
    }

    @Generated
    public DefaultMessageSanitizer(Pattern ticketIdPattern) {
        this.ticketIdPattern = ticketIdPattern;
    }
}

