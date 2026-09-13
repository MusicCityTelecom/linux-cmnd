/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model.decl;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.ListenerDispatchMethod;
import groovyjarjarantlr4.v4.codegen.model.VisitorDispatchMethod;
import groovyjarjarantlr4.v4.codegen.model.decl.StructDecl;
import groovyjarjarantlr4.v4.tool.Rule;
import java.util.ArrayList;

public class AltLabelStructDecl
extends StructDecl {
    public String parentRule;

    public AltLabelStructDecl(OutputModelFactory factory, Rule r, String label) {
        super(factory, r);
        this.name = factory.getTarget().getAltLabelContextStructName(label);
        this.parentRule = r.name;
        this.derivedFromName = label;
    }

    @Override
    public void addDispatchMethods(Rule r) {
        this.dispatchMethods = new ArrayList();
        if (this.factory.getGrammar().tool.gen_listener) {
            this.dispatchMethods.add(new ListenerDispatchMethod(this.factory, true));
            this.dispatchMethods.add(new ListenerDispatchMethod(this.factory, false));
        }
        if (this.factory.getGrammar().tool.gen_visitor) {
            this.dispatchMethods.add(new VisitorDispatchMethod(this.factory));
        }
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AltLabelStructDecl)) {
            return false;
        }
        return this.name.equals(((AltLabelStructDecl)obj).name);
    }
}

