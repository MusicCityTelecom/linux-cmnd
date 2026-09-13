/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model.decl;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.decl.ContextGetterDecl;

public class ContextTokenGetterDecl
extends ContextGetterDecl {
    public boolean optional;

    public ContextTokenGetterDecl(OutputModelFactory factory, String name, boolean optional) {
        super(factory, name);
        this.optional = optional;
    }
}

