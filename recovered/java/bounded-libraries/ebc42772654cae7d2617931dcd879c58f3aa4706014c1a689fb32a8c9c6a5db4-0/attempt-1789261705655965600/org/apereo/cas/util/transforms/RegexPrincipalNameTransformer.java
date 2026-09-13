/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.handler.PrincipalNameTransformer
 */
package org.apereo.cas.util.transforms;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Generated;
import org.apereo.cas.authentication.handler.PrincipalNameTransformer;
import org.apereo.cas.util.RegexUtils;

public class RegexPrincipalNameTransformer
implements PrincipalNameTransformer {
    private Pattern pattern;

    public RegexPrincipalNameTransformer(String pattern) {
        this.setPattern(RegexUtils.createPattern(pattern));
    }

    public String transform(String username) {
        Matcher matcher = this.pattern.matcher(username);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return username.trim();
    }

    @Generated
    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }
}

