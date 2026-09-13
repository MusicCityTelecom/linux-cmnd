/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.core.Ordered
 */
package org.springframework.boot.sql.init.dependency;

import java.util.Set;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;

public interface DatabaseInitializerDetector
extends Ordered {
    public Set<String> detect(ConfigurableListableBeanFactory var1);

    default public void detectionComplete(ConfigurableListableBeanFactory beanFactory, Set<String> dataSourceInitializerNames) {
    }

    default public int getOrder() {
        return 0;
    }
}

