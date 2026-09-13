/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.tree;

public class RewriteCardinalityException
extends RuntimeException {
    public String elementDescription;

    public RewriteCardinalityException(String elementDescription) {
        this.elementDescription = elementDescription;
    }

    public String getMessage() {
        if (this.elementDescription != null) {
            return this.elementDescription;
        }
        return null;
    }
}

