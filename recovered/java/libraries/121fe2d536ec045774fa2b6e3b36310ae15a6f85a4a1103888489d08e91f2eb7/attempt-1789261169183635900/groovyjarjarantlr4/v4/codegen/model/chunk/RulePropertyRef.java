/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model.chunk;

import groovyjarjarantlr4.v4.codegen.model.chunk.ActionChunk;
import groovyjarjarantlr4.v4.codegen.model.decl.StructDecl;

public class RulePropertyRef
extends ActionChunk {
    public String label;

    public RulePropertyRef(StructDecl ctx, String label) {
        super(ctx);
        this.label = label;
    }
}

