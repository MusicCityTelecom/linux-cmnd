/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model.decl;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.decl.ContextTokenListGetterDecl;

public class ContextTokenListIndexedGetterDecl
extends ContextTokenListGetterDecl {
    public ContextTokenListIndexedGetterDecl(OutputModelFactory factory, String name) {
        super(factory, name);
    }

    @Override
    public String getArgType() {
        return "int";
    }
}

