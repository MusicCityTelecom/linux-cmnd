/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model.decl;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.decl.ContextGetterDecl;

public class ContextRuleListGetterDecl
extends ContextGetterDecl {
    public String ctxName;

    public ContextRuleListGetterDecl(OutputModelFactory factory, String name, String ctxName) {
        super(factory, name);
        this.ctxName = ctxName;
    }
}

