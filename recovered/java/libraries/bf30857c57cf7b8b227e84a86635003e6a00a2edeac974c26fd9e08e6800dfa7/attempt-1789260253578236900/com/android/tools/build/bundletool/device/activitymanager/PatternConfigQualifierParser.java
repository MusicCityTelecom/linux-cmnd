/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device.activitymanager;

import com.android.tools.build.bundletool.device.activitymanager.ConfigStringIterator;
import com.android.tools.build.bundletool.device.activitymanager.ResourceConfigParser;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class PatternConfigQualifierParser<T>
implements ResourceConfigParser.ConfigQualifierParser<T> {
    PatternConfigQualifierParser() {
    }

    abstract Pattern getExpectedPattern();

    abstract Function<Matcher, Boolean> getValueProcessor(ResourceConfigParser.ResourceConfigHandler<T> var1);

    @Override
    public boolean parse(ConfigStringIterator iterator2, ResourceConfigParser.ResourceConfigHandler<T> handler) {
        String nextPart = iterator2.getValue();
        if (nextPart.equals("any")) {
            return true;
        }
        Matcher matcher = this.getExpectedPattern().matcher(nextPart);
        if (!matcher.matches()) {
            return false;
        }
        return this.getValueProcessor(handler).apply(matcher);
    }

    static class MncParser<T>
    extends PatternConfigQualifierParser<T> {
        MncParser() {
        }

        @Override
        Pattern getExpectedPattern() {
            return Pattern.compile("mnc(?<code>\\d{1,3}?)");
        }

        @Override
        Function<Matcher, Boolean> getValueProcessor(ResourceConfigParser.ResourceConfigHandler<T> handler) {
            return matcher -> {
                String mncCode = matcher.group("code");
                if (mncCode == null) {
                    return false;
                }
                handler.onMncCode(Integer.parseInt(mncCode));
                return true;
            };
        }
    }

    static class MccParser<T>
    extends PatternConfigQualifierParser<T> {
        MccParser() {
        }

        @Override
        Pattern getExpectedPattern() {
            return Pattern.compile("mcc(?<code>\\d\\d\\d?)");
        }

        @Override
        Function<Matcher, Boolean> getValueProcessor(ResourceConfigParser.ResourceConfigHandler<T> handler) {
            return matcher -> {
                String mccCode = matcher.group("code");
                if (mccCode == null) {
                    return false;
                }
                handler.onMccCode(Integer.parseInt(mccCode));
                return true;
            };
        }
    }
}

