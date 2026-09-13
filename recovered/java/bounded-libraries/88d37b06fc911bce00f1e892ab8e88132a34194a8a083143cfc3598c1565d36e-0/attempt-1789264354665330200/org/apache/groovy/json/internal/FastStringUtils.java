/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.json.internal;

import java.util.ServiceLoader;
import org.apache.groovy.json.DefaultFastStringService;
import org.apache.groovy.json.FastStringService;
import org.apache.groovy.json.FastStringServiceFactory;

public class FastStringUtils {
    private static FastStringService getService() {
        if (ServiceHolder.INSTANCE == null) {
            throw new RuntimeException("Unable to load FastStringService");
        }
        return ServiceHolder.INSTANCE;
    }

    public static char[] toCharArray(String string) {
        return FastStringUtils.getService().toCharArray(string);
    }

    public static char[] toCharArray(CharSequence charSequence) {
        return FastStringUtils.toCharArray(charSequence.toString());
    }

    public static String noCopyStringFromChars(char[] chars) {
        return FastStringUtils.getService().noCopyStringFromChars(chars);
    }

    private static class ServiceHolder {
        static final FastStringService INSTANCE = ServiceHolder.loadService();

        private ServiceHolder() {
        }

        private static FastStringService loadService() {
            ServiceLoader<FastStringServiceFactory> serviceLoader = ServiceLoader.load(FastStringServiceFactory.class);
            FastStringService found = null;
            for (FastStringServiceFactory factory : serviceLoader) {
                FastStringService service = factory.getService();
                if (service == null) continue;
                found = service;
                if (service instanceof DefaultFastStringService) continue;
                break;
            }
            return found;
        }
    }
}

