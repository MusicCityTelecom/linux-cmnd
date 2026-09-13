/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model.decl;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.decl.Decl;
import groovyjarjarantlr4.v4.runtime.misc.MurmurHash;

public abstract class ContextGetterDecl
extends Decl {
    public ContextGetterDecl(OutputModelFactory factory, String name) {
        super(factory, name);
    }

    public String getArgType() {
        return "";
    }

    @Override
    public int hashCode() {
        int hash = MurmurHash.initialize();
        hash = MurmurHash.update(hash, this.name);
        hash = MurmurHash.update(hash, this.getArgType());
        hash = MurmurHash.finish(hash, 2);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ContextGetterDecl)) {
            return false;
        }
        return this.name.equals(((Decl)obj).name) && this.getArgType().equals(((ContextGetterDecl)obj).getArgType());
    }
}

