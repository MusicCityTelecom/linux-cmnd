/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model.chunk;

import groovyjarjarantlr4.v4.codegen.model.chunk.ActionChunk;
import groovyjarjarantlr4.v4.codegen.model.chunk.SetAttr;
import groovyjarjarantlr4.v4.codegen.model.decl.StructDecl;
import java.util.List;

public class SetNonLocalAttr
extends SetAttr {
    public String ruleName;
    public int ruleIndex;

    public SetNonLocalAttr(StructDecl ctx, String ruleName, String name, int ruleIndex, List<ActionChunk> rhsChunks) {
        super(ctx, name, rhsChunks);
        this.ruleName = ruleName;
        this.ruleIndex = ruleIndex;
    }
}

