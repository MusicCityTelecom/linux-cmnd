/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model.decl;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.decl.ContextRuleListGetterDecl;

public class ContextRuleListIndexedGetterDecl
extends ContextRuleListGetterDecl {
    public ContextRuleListIndexedGetterDecl(OutputModelFactory factory, String name, String ctxName) {
        super(factory, name, ctxName);
    }

    @Override
    public String getArgType() {
        return "int";
    }
}

