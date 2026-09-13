/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.handler.PrincipalNameTransformer
 *  org.apereo.cas.configuration.model.core.authentication.PrincipalTransformationProperties
 *  org.apereo.cas.configuration.model.core.authentication.PrincipalTransformationProperties$CaseConversion
 *  org.apereo.cas.util.transforms.BlockingPrincipalNameTransformer
 *  org.apereo.cas.util.transforms.ChainingPrincipalNameTransformer
 *  org.apereo.cas.util.transforms.ConvertCasePrincipalNameTransformer
 *  org.apereo.cas.util.transforms.GroovyPrincipalNameTransformer
 *  org.apereo.cas.util.transforms.PrefixSuffixPrincipalNameTransformer
 *  org.apereo.cas.util.transforms.RegexPrincipalNameTransformer
 */
package org.apereo.cas.authentication.principal;

import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.handler.PrincipalNameTransformer;
import org.apereo.cas.configuration.model.core.authentication.PrincipalTransformationProperties;
import org.apereo.cas.util.transforms.BlockingPrincipalNameTransformer;
import org.apereo.cas.util.transforms.ChainingPrincipalNameTransformer;
import org.apereo.cas.util.transforms.ConvertCasePrincipalNameTransformer;
import org.apereo.cas.util.transforms.GroovyPrincipalNameTransformer;
import org.apereo.cas.util.transforms.PrefixSuffixPrincipalNameTransformer;
import org.apereo.cas.util.transforms.RegexPrincipalNameTransformer;

public final class PrincipalNameTransformerUtils {
    public static PrincipalNameTransformer newPrincipalNameTransformer(PrincipalTransformationProperties p) {
        GroovyPrincipalNameTransformer t;
        ChainingPrincipalNameTransformer chain = new ChainingPrincipalNameTransformer();
        if (p.getGroovy().getLocation() != null) {
            t = new GroovyPrincipalNameTransformer(p.getGroovy().getLocation());
            chain.addTransformer((PrincipalNameTransformer)t);
        }
        if (StringUtils.isNotBlank((CharSequence)p.getPattern())) {
            t = new RegexPrincipalNameTransformer(p.getPattern());
            chain.addTransformer((PrincipalNameTransformer)t);
        }
        if (StringUtils.isNotBlank((CharSequence)p.getPrefix()) || StringUtils.isNotBlank((CharSequence)p.getSuffix())) {
            t = new PrefixSuffixPrincipalNameTransformer();
            t.setPrefix(p.getPrefix());
            t.setSuffix(p.getSuffix());
            chain.addTransformer((PrincipalNameTransformer)t);
        }
        if (p.getCaseConversion() == PrincipalTransformationProperties.CaseConversion.UPPERCASE) {
            t = new ConvertCasePrincipalNameTransformer();
            t.setToUpperCase(true);
            chain.addTransformer((PrincipalNameTransformer)t);
        }
        if (p.getCaseConversion() == PrincipalTransformationProperties.CaseConversion.LOWERCASE) {
            t = new ConvertCasePrincipalNameTransformer();
            t.setToUpperCase(false);
            chain.addTransformer((PrincipalNameTransformer)t);
        }
        if (StringUtils.isNotBlank((CharSequence)p.getBlockingPattern())) {
            t = new BlockingPrincipalNameTransformer(p.getBlockingPattern());
            chain.addTransformer((PrincipalNameTransformer)t);
        }
        return chain;
    }

    @Generated
    private PrincipalNameTransformerUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

