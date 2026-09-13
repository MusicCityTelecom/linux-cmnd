/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model.decl;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.SrcOp;
import groovyjarjarantlr4.v4.codegen.model.decl.ContextGetterDecl;
import groovyjarjarantlr4.v4.codegen.model.decl.StructDecl;

public class Decl
extends SrcOp {
    public String name;
    public String decl;
    public boolean isLocal;
    public StructDecl ctx;

    public Decl(OutputModelFactory factory, String name, String decl) {
        this(factory, name);
        this.decl = decl;
    }

    public Decl(OutputModelFactory factory, String name) {
        super(factory);
        this.name = name;
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Decl)) {
            return false;
        }
        if (obj instanceof ContextGetterDecl) {
            return false;
        }
        return this.name.equals(((Decl)obj).name);
    }
}

