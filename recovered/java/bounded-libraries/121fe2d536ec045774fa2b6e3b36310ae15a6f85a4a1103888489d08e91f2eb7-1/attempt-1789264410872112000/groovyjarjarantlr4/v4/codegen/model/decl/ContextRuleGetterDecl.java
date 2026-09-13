/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model.decl;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.decl.ContextGetterDecl;

public class ContextRuleGetterDecl
extends ContextGetterDecl {
    public String ctxName;
    public boolean optional;

    public ContextRuleGetterDecl(OutputModelFactory factory, String name, String ctxName, boolean optional) {
        super(factory, name);
        this.ctxName = ctxName;
        this.optional = optional;
    }
}

