/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model.chunk;

import groovyjarjarantlr4.v4.codegen.model.chunk.ActionChunk;
import groovyjarjarantlr4.v4.codegen.model.decl.StructDecl;

public class LabelRef
extends ActionChunk {
    public String name;

    public LabelRef(StructDecl ctx, String name) {
        super(ctx);
        this.name = name;
    }
}

