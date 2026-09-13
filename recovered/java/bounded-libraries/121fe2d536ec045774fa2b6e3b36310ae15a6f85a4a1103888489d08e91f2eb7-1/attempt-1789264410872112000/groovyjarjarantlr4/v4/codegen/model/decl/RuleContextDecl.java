/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model.decl;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.decl.Decl;

public class RuleContextDecl
extends Decl {
    public String ctxName;
    public boolean isImplicit;

    public RuleContextDecl(OutputModelFactory factory, String name, String ctxName) {
        super(factory, name);
        this.ctxName = ctxName;
    }
}

