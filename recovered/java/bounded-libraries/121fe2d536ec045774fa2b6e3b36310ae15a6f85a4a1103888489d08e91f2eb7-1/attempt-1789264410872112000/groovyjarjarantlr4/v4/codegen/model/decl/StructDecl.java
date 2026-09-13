/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model.decl;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.DispatchMethod;
import groovyjarjarantlr4.v4.codegen.model.ListenerDispatchMethod;
import groovyjarjarantlr4.v4.codegen.model.ModelElement;
import groovyjarjarantlr4.v4.codegen.model.OutputModelObject;
import groovyjarjarantlr4.v4.codegen.model.VisitorDispatchMethod;
import groovyjarjarantlr4.v4.codegen.model.decl.AttributeDecl;
import groovyjarjarantlr4.v4.codegen.model.decl.ContextGetterDecl;
import groovyjarjarantlr4.v4.codegen.model.decl.Decl;
import groovyjarjarantlr4.v4.codegen.model.decl.RuleContextDecl;
import groovyjarjarantlr4.v4.codegen.model.decl.RuleContextListDecl;
import groovyjarjarantlr4.v4.codegen.model.decl.TokenDecl;
import groovyjarjarantlr4.v4.codegen.model.decl.TokenListDecl;
import groovyjarjarantlr4.v4.codegen.model.decl.TokenTypeDecl;
import groovyjarjarantlr4.v4.runtime.misc.OrderedHashSet;
import groovyjarjarantlr4.v4.tool.Attribute;
import groovyjarjarantlr4.v4.tool.Rule;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StructDecl
extends Decl {
    public String derivedFromName;
    public boolean provideCopyFrom;
    @ModelElement
    public OrderedHashSet<Decl> attrs = new OrderedHashSet();
    @ModelElement
    public OrderedHashSet<Decl> getters = new OrderedHashSet();
    @ModelElement
    public Collection<AttributeDecl> ctorAttrs;
    @ModelElement
    public List<? super DispatchMethod> dispatchMethods;
    @ModelElement
    public List<OutputModelObject> interfaces;
    @ModelElement
    public List<OutputModelObject> extensionMembers;
    public OrderedHashSet<Decl> tokenDecls = new OrderedHashSet();
    public OrderedHashSet<Decl> tokenTypeDecls = new OrderedHashSet();
    public OrderedHashSet<Decl> tokenListDecls = new OrderedHashSet();
    public OrderedHashSet<Decl> ruleContextDecls = new OrderedHashSet();
    public OrderedHashSet<Decl> ruleContextListDecls = new OrderedHashSet();
    public OrderedHashSet<Decl> attributeDecls = new OrderedHashSet();

    public StructDecl(OutputModelFactory factory, Rule r) {
        super(factory, factory.getTarget().getRuleFunctionContextStructName(r));
        this.addDispatchMethods(r);
        this.derivedFromName = r.name;
        this.provideCopyFrom = r.hasAltSpecificContexts();
    }

    public void addDispatchMethods(Rule r) {
        this.dispatchMethods = new ArrayList<DispatchMethod>();
        if (!r.hasAltSpecificContexts()) {
            if (this.factory.getGrammar().tool.gen_listener) {
                this.dispatchMethods.add(new ListenerDispatchMethod(this.factory, true));
                this.dispatchMethods.add(new ListenerDispatchMethod(this.factory, false));
            }
            if (this.factory.getGrammar().tool.gen_visitor) {
                this.dispatchMethods.add(new VisitorDispatchMethod(this.factory));
            }
        }
    }

    public void addDecl(Decl d) {
        d.ctx = this;
        if (d instanceof ContextGetterDecl) {
            this.getters.add(d);
        } else {
            this.attrs.add(d);
        }
        if (d instanceof TokenTypeDecl) {
            this.tokenTypeDecls.add(d);
        } else if (d instanceof TokenListDecl) {
            this.tokenListDecls.add(d);
        } else if (d instanceof TokenDecl) {
            this.tokenDecls.add(d);
        } else if (d instanceof RuleContextListDecl) {
            this.ruleContextListDecls.add(d);
        } else if (d instanceof RuleContextDecl) {
            this.ruleContextDecls.add(d);
        } else if (d instanceof AttributeDecl) {
            this.attributeDecls.add(d);
        }
    }

    public void addDecl(Attribute a) {
        this.addDecl(new AttributeDecl(this.factory, a));
    }

    public void addDecls(Collection<Attribute> attrList) {
        for (Attribute a : attrList) {
            this.addDecl(a);
        }
    }

    public void implementInterface(OutputModelObject value) {
        if (this.interfaces == null) {
            this.interfaces = new ArrayList<OutputModelObject>();
        }
        this.interfaces.add(value);
    }

    public void addExtensionMember(OutputModelObject member) {
        if (this.extensionMembers == null) {
            this.extensionMembers = new ArrayList<OutputModelObject>();
        }
        this.extensionMembers.add(member);
    }

    public boolean isEmpty() {
        return this.attrs.isEmpty();
    }
}

