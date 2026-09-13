/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model.chunk;

import groovyjarjarantlr4.v4.codegen.model.chunk.ActionChunk;
import groovyjarjarantlr4.v4.codegen.model.decl.StructDecl;

public class NonLocalAttrRef
extends ActionChunk {
    public String ruleName;
    public String name;
    public int ruleIndex;

    public NonLocalAttrRef(StructDecl ctx, String ruleName, String name, int ruleIndex) {
        super(ctx);
        this.name = name;
        this.ruleName = ruleName;
        this.ruleIndex = ruleIndex;
    }
}

