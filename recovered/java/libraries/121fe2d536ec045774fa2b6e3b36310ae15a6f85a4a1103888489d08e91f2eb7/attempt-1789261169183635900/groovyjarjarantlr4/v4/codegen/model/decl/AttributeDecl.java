/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model.decl;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.decl.Decl;
import groovyjarjarantlr4.v4.tool.Attribute;

public class AttributeDecl
extends Decl {
    public String type;
    public String initValue;

    public AttributeDecl(OutputModelFactory factory, Attribute a) {
        super(factory, a.name, a.decl);
        this.type = a.type;
        this.initValue = a.initValue;
    }
}

