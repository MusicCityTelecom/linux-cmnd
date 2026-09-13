/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model.decl;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.decl.RuleContextDecl;

public class RuleContextListDecl
extends RuleContextDecl {
    public RuleContextListDecl(OutputModelFactory factory, String name, String ctxName) {
        super(factory, name, ctxName);
        this.isImplicit = false;
    }
}

