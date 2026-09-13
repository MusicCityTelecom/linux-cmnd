/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package org.apereo.services.persondir.util;

import java.util.Locale;
import org.apache.commons.lang3.StringUtils;

public enum CaseCanonicalizationMode {
    LOWER{

        @Override
        public String canonicalize(String value) {
            return StringUtils.lowerCase((String)value);
        }

        @Override
        public String canonicalize(String value, Locale locale) {
            return StringUtils.lowerCase((String)value, (Locale)locale);
        }
    }
    ,
    UPPER{

        @Override
        public String canonicalize(String value) {
            return StringUtils.upperCase((String)value);
        }

        @Override
        public String canonicalize(String value, Locale locale) {
            return StringUtils.upperCase((String)value, (Locale)locale);
        }
    }
    ,
    NONE{

        @Override
        public String canonicalize(String value) {
            return value;
        }

        @Override
        public String canonicalize(String value, Locale locale) {
            return value;
        }
    };


    public abstract String canonicalize(String var1);

    public abstract String canonicalize(String var1, Locale var2);
}

