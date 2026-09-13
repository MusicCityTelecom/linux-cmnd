/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.springframework.security.web.util.ThrowableCauseExtractor;
import org.springframework.util.Assert;

public class ThrowableAnalyzer {
    public static final ThrowableCauseExtractor DEFAULT_EXTRACTOR = throwable -> throwable.getCause();
    public static final ThrowableCauseExtractor INVOCATIONTARGET_EXTRACTOR = throwable -> {
        ThrowableAnalyzer.verifyThrowableHierarchy(throwable, InvocationTargetException.class);
        return ((InvocationTargetException)throwable).getTargetException();
    };
    private static final Comparator<Class<? extends Throwable>> CLASS_HIERARCHY_COMPARATOR = (class1, class2) -> {
        if (class1.isAssignableFrom((Class<?>)class2)) {
            return 1;
        }
        if (class2.isAssignableFrom((Class<?>)class1)) {
            return -1;
        }
        return class1.getName().compareTo(class2.getName());
    };
    private final Map<Class<? extends Throwable>, ThrowableCauseExtractor> extractorMap = new TreeMap<Class<Throwable>, ThrowableCauseExtractor>(CLASS_HIERARCHY_COMPARATOR);

    public ThrowableAnalyzer() {
        this.initExtractorMap();
    }

    protected final void registerExtractor(Class<? extends Throwable> throwableType, ThrowableCauseExtractor extractor) {
        Assert.notNull((Object)extractor, (String)"Invalid extractor: null");
        this.extractorMap.put(throwableType, extractor);
    }

    protected void initExtractorMap() {
        this.registerExtractor(InvocationTargetException.class, INVOCATIONTARGET_EXTRACTOR);
        this.registerExtractor(Throwable.class, DEFAULT_EXTRACTOR);
    }

    final Class<? extends Throwable>[] getRegisteredTypes() {
        Set<Class<? extends Throwable>> typeList = this.extractorMap.keySet();
        return typeList.toArray(new Class[0]);
    }

    public final Throwable[] determineCauseChain(Throwable throwable) {
        Assert.notNull((Object)throwable, (String)"Invalid throwable: null");
        ArrayList<Throwable> chain = new ArrayList<Throwable>();
        Throwable currentThrowable = throwable;
        while (currentThrowable != null) {
            chain.add(currentThrowable);
            currentThrowable = this.extractCause(currentThrowable);
        }
        return chain.toArray(new Throwable[0]);
    }

    private Throwable extractCause(Throwable throwable) {
        for (Map.Entry<Class<? extends Throwable>, ThrowableCauseExtractor> entry : this.extractorMap.entrySet()) {
            Class<? extends Throwable> throwableType = entry.getKey();
            if (!throwableType.isInstance(throwable)) continue;
            ThrowableCauseExtractor extractor = entry.getValue();
            return extractor.extractCause(throwable);
        }
        return null;
    }

    public final Throwable getFirstThrowableOfType(Class<? extends Throwable> throwableType, Throwable[] chain) {
        if (chain != null) {
            for (Throwable t : chain) {
                if (t == null || !throwableType.isInstance(t)) continue;
                return t;
            }
        }
        return null;
    }

    public static void verifyThrowableHierarchy(Throwable throwable, Class<? extends Throwable> expectedBaseType) {
        if (expectedBaseType == null) {
            return;
        }
        Assert.notNull((Object)throwable, (String)"Invalid throwable: null");
        Class<?> throwableType = throwable.getClass();
        Assert.isTrue((boolean)expectedBaseType.isAssignableFrom(throwableType), () -> "Invalid type: '" + throwableType.getName() + "'. Has to be a subclass of '" + expectedBaseType.getName() + "'");
    }
}

