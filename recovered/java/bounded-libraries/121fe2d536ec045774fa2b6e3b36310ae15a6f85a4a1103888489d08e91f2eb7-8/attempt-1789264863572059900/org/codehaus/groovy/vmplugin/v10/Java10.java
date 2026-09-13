/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.vmplugin.v10;

import java.util.Arrays;
import org.codehaus.groovy.vmplugin.v10.PluginDefaultGroovyMethods;
import org.codehaus.groovy.vmplugin.v9.Java9;

public class Java10
extends Java9 {
    @Override
    public int getVersion() {
        return 10;
    }

    @Override
    public Class<?>[] getPluginDefaultGroovyMethods() {
        Class<?>[] answer = super.getPluginDefaultGroovyMethods();
        int n = answer.length;
        answer = Arrays.copyOf(answer, n + 1);
        answer[n] = PluginDefaultGroovyMethods.class;
        return answer;
    }
}

