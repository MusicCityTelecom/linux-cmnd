/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.stringtemplate.v4.ST
 */
package groovyjarjarantlr4.v4.codegen.model.chunk;

import groovyjarjarantlr4.v4.codegen.model.chunk.ActionChunk;
import groovyjarjarantlr4.v4.codegen.model.decl.StructDecl;
import org.stringtemplate.v4.ST;

public class ActionTemplate
extends ActionChunk {
    public ST st;

    public ActionTemplate(StructDecl ctx, ST st) {
        super(ctx);
        this.st = st;
    }
}

