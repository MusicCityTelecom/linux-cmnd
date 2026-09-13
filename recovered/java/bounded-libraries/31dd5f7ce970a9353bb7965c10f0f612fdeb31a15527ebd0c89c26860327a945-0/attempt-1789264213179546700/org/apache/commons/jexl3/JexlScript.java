/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import org.apache.commons.jexl3.JexlContext;

public interface JexlScript {
    public String getSourceText();

    public String getParsedText();

    public String getParsedText(int var1);

    public Object execute(JexlContext var1);

    public Object execute(JexlContext var1, Object ... var2);

    public String[] getParameters();

    public String[] getUnboundParameters();

    public String[] getLocalVariables();

    public Set<List<String>> getVariables();

    public Map<String, Object> getPragmas();

    public Callable<Object> callable(JexlContext var1);

    public Callable<Object> callable(JexlContext var1, Object ... var2);

    public JexlScript curry(Object ... var1);
}

