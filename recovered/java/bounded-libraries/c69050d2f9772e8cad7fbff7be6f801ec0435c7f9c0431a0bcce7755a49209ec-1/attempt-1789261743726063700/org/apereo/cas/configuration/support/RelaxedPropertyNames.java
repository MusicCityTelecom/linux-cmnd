/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Splitter
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 */
package org.apereo.cas.configuration.support;

import com.google.common.base.Splitter;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;

public class RelaxedPropertyNames
implements Iterable<String> {
    private static final Pattern CAMEL_CASE_PATTERN = Pattern.compile("([^A-Z-])([A-Z])");
    private static final Pattern SEPARATED_TO_CAMEL_CASE_PATTERN = Pattern.compile("[_\\-.]");
    private final String name;
    private final Set<String> values = new LinkedHashSet<String>(0);

    public RelaxedPropertyNames(String name) {
        this.name = StringUtils.defaultString((String)name);
        RelaxedPropertyNames.initialize(this.name, this.values);
    }

    public static RelaxedPropertyNames forCamelCase(String name) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < name.length(); ++i) {
            char c = name.charAt(i);
            result.append(Character.isUpperCase(c) && result.length() > 0 && result.charAt(result.length() - 1) != '-' ? "-" + Character.toLowerCase(c) : Character.valueOf(c));
        }
        return new RelaxedPropertyNames(result.toString());
    }

    @Override
    public Iterator<String> iterator() {
        return this.values.iterator();
    }

    private static void initialize(String name, Set<String> values) {
        if (values.contains(name)) {
            return;
        }
        for (Variation variation : Variation.values()) {
            for (Manipulation manipulation : Manipulation.values()) {
                String result = name;
                result = manipulation.apply(result);
                result = variation.apply(result);
                values.add(result);
                RelaxedPropertyNames.initialize(result, values);
            }
        }
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public Set<String> getValues() {
        return this.values;
    }

    static enum Manipulation {
        NONE{

            @Override
            public String apply(String value) {
                return value;
            }
        }
        ,
        HYPHEN_TO_UNDERSCORE{

            @Override
            public String apply(String value) {
                return value.indexOf(45) != -1 ? value.replace('-', '_') : value;
            }
        }
        ,
        UNDERSCORE_TO_PERIOD{

            @Override
            public String apply(String value) {
                return value.indexOf(95) != -1 ? value.replace('_', '.') : value;
            }
        }
        ,
        PERIOD_TO_UNDERSCORE{

            @Override
            public String apply(String value) {
                return value.indexOf(46) != -1 ? value.replace('.', '_') : value;
            }
        }
        ,
        CAMELCASE_TO_UNDERSCORE{

            @Override
            public String apply(String value) {
                if (value.isEmpty()) {
                    return value;
                }
                Matcher matcher = CAMEL_CASE_PATTERN.matcher(value);
                if (!matcher.find()) {
                    return value;
                }
                matcher = matcher.reset();
                StringBuilder result = new StringBuilder();
                while (matcher.find()) {
                    matcher.appendReplacement(result, matcher.group(1) + "_" + StringUtils.uncapitalize((String)matcher.group(2)));
                }
                matcher.appendTail(result);
                return result.toString();
            }
        }
        ,
        CAMELCASE_TO_HYPHEN{

            @Override
            public String apply(String value) {
                if (value.isEmpty()) {
                    return value;
                }
                Matcher matcher = CAMEL_CASE_PATTERN.matcher(value);
                if (!matcher.find()) {
                    return value;
                }
                matcher = matcher.reset();
                StringBuilder result = new StringBuilder();
                while (matcher.find()) {
                    matcher.appendReplacement(result, matcher.group(1) + "-" + StringUtils.uncapitalize((String)matcher.group(2)));
                }
                matcher.appendTail(result);
                return result.toString();
            }
        }
        ,
        SEPARATED_TO_CAMELCASE{

            @Override
            public String apply(String value) {
                return Manipulation.separatedToCamelCase(value, false);
            }
        }
        ,
        CASE_INSENSITIVE_SEPARATED_TO_CAMELCASE{

            @Override
            public String apply(String value) {
                return Manipulation.separatedToCamelCase(value, true);
            }
        };

        private static final char[] SUFFIXES;

        public abstract String apply(String var1);

        private static String separatedToCamelCase(String value, boolean caseInsensitive) {
            if (value.isEmpty()) {
                return value;
            }
            StringBuilder builder = new StringBuilder();
            for (String field : Splitter.on((Pattern)SEPARATED_TO_CAMEL_CASE_PATTERN).split((CharSequence)value)) {
                String fieldCased = caseInsensitive ? field.toLowerCase() : field;
                builder.append(builder.length() == 0 ? field : StringUtils.capitalize((String)fieldCased));
            }
            char lastChar = value.charAt(value.length() - 1);
            for (char suffix : SUFFIXES) {
                if (lastChar != suffix) continue;
                builder.append(suffix);
                break;
            }
            return builder.toString();
        }

        static {
            SUFFIXES = new char[]{'_', '-', '.'};
        }
    }

    private static enum Variation {
        NONE{

            @Override
            public String apply(String value) {
                return value;
            }
        }
        ,
        LOWERCASE{

            @Override
            public String apply(String value) {
                return value.isEmpty() ? value : value.toLowerCase();
            }
        }
        ,
        UPPERCASE{

            @Override
            public String apply(String value) {
                return value.isEmpty() ? value : value.toUpperCase();
            }
        };


        public abstract String apply(String var1);
    }
}

