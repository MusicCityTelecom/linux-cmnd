/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 */
package org.springframework.boot.sql.init.dependency;

import java.util.Set;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public interface DependsOnDatabaseInitializationDetector {
    public Set<String> detect(ConfigurableListableBeanFactory var1);
}

