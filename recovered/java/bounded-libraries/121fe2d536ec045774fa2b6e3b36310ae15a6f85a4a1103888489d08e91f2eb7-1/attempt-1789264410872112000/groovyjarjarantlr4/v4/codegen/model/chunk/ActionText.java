/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model.chunk;

import groovyjarjarantlr4.v4.codegen.model.chunk.ActionChunk;
import groovyjarjarantlr4.v4.codegen.model.decl.StructDecl;

public class ActionText
extends ActionChunk {
    public String text;

    public ActionText(StructDecl ctx, String text) {
        super(ctx);
        this.text = text;
    }
}

