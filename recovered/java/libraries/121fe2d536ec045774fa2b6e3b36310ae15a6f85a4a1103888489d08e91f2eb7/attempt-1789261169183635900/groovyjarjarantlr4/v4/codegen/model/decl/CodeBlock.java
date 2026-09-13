/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model.decl;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.ModelElement;
import groovyjarjarantlr4.v4.codegen.model.SrcOp;
import groovyjarjarantlr4.v4.codegen.model.decl.Decl;
import groovyjarjarantlr4.v4.runtime.misc.OrderedHashSet;
import java.util.ArrayList;
import java.util.List;

public class CodeBlock
extends SrcOp {
    public int codeBlockLevel;
    public int treeLevel;
    @ModelElement
    public OrderedHashSet<Decl> locals;
    @ModelElement
    public List<SrcOp> preamble;
    @ModelElement
    public List<SrcOp> ops;

    public CodeBlock(OutputModelFactory factory) {
        super(factory);
    }

    public CodeBlock(OutputModelFactory factory, int treeLevel, int codeBlockLevel) {
        super(factory);
        this.treeLevel = treeLevel;
        this.codeBlockLevel = codeBlockLevel;
    }

    public void addLocalDecl(Decl d) {
        if (this.locals == null) {
            this.locals = new OrderedHashSet();
        }
        this.locals.add(d);
        d.isLocal = true;
    }

    public void addPreambleOp(SrcOp op) {
        if (this.preamble == null) {
            this.preamble = new ArrayList<SrcOp>();
        }
        this.preamble.add(op);
    }

    public void addOp(SrcOp op) {
        if (this.ops == null) {
            this.ops = new ArrayList<SrcOp>();
        }
        this.ops.add(op);
    }

    public void insertOp(int i, SrcOp op) {
        if (this.ops == null) {
            this.ops = new ArrayList<SrcOp>();
        }
        this.ops.add(i, op);
    }

    public void addOps(List<SrcOp> ops) {
        if (this.ops == null) {
            this.ops = new ArrayList<SrcOp>();
        }
        this.ops.addAll(ops);
    }
}

