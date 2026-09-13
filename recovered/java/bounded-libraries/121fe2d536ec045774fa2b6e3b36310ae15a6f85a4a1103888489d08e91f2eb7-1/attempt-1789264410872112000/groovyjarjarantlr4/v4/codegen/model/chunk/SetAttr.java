/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model.chunk;

import groovyjarjarantlr4.v4.codegen.model.ModelElement;
import groovyjarjarantlr4.v4.codegen.model.chunk.ActionChunk;
import groovyjarjarantlr4.v4.codegen.model.decl.StructDecl;
import java.util.List;

public class SetAttr
extends ActionChunk {
    public String name;
    @ModelElement
    public List<ActionChunk> rhsChunks;

    public SetAttr(StructDecl ctx, String name, List<ActionChunk> rhsChunks) {
        super(ctx);
        this.name = name;
        this.rhsChunks = rhsChunks;
    }
}

