/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model.chunk;

import groovyjarjarantlr4.v4.codegen.model.chunk.RetValueRef;
import groovyjarjarantlr4.v4.codegen.model.decl.StructDecl;

public class QRetValueRef
extends RetValueRef {
    public String dict;

    public QRetValueRef(StructDecl ctx, String dict, String name) {
        super(ctx, name);
        this.dict = dict;
    }
}

