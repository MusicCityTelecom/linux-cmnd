/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.tree;

import groovyjarjarantlr4.runtime.tree.RewriteCardinalityException;

public class RewriteEarlyExitException
extends RewriteCardinalityException {
    public RewriteEarlyExitException() {
        super((String)null);
    }

    public RewriteEarlyExitException(String elementDescription) {
        super(elementDescription);
    }
}

