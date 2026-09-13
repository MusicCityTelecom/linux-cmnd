/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool;

import groovyjarjarantlr4.v4.tool.ANTLRMessage;

public interface ANTLRToolListener {
    public void info(String var1);

    public void error(ANTLRMessage var1);

    public void warning(ANTLRMessage var1);
}

