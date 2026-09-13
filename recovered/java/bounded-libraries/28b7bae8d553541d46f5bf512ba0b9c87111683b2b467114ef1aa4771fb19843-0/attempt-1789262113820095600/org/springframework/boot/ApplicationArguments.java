/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot;

import java.util.List;
import java.util.Set;

public interface ApplicationArguments {
    public String[] getSourceArgs();

    public Set<String> getOptionNames();

    public boolean containsOption(String var1);

    public List<String> getOptionValues(String var1);

    public List<String> getNonOptionArgs();
}

