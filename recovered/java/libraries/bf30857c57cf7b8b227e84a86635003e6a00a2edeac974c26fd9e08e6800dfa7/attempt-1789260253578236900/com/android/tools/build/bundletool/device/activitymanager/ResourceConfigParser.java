/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device.activitymanager;

import com.android.tools.build.bundletool.device.activitymanager.ConfigStringIterator;
import com.android.tools.build.bundletool.device.activitymanager.LocaleParser;
import com.android.tools.build.bundletool.device.activitymanager.PatternConfigQualifierParser;
import com.google.common.collect.ImmutableList;

public class ResourceConfigParser {
    public static <T> T parseDeviceConfig(String configString, ResourceConfigHandler<T> handler) {
        ConfigStringIterator iterator2 = new ConfigStringIterator(configString);
        if (!iterator2.hasNext()) {
            return handler.getOutput();
        }
        iterator2.next();
        ImmutableList configQualifierParsers = ImmutableList.of(new PatternConfigQualifierParser.MccParser(), new PatternConfigQualifierParser.MncParser(), new LocaleParser());
        for (ConfigQualifierParser configQualifierParser : configQualifierParsers) {
            if (!configQualifierParser.parse(iterator2, handler)) continue;
            if (!iterator2.hasNext()) {
                return handler.getOutput();
            }
            iterator2.next();
        }
        return handler.getOutput();
    }

    public static interface ResourceConfigHandler<T> {
        default public void onMccCode(int mccCode) {
        }

        default public void onMncCode(int mncCode) {
        }

        default public void onLocale(String locale) {
        }

        public T getOutput();
    }

    static interface ConfigQualifierParser<T> {
        public boolean parse(ConfigStringIterator var1, ResourceConfigHandler<T> var2);
    }
}

