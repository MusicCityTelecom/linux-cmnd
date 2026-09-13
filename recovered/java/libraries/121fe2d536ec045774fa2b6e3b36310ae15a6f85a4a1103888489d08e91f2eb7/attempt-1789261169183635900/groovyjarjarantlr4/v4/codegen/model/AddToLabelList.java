/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.SrcOp;
import groovyjarjarantlr4.v4.codegen.model.decl.Decl;

public class AddToLabelList
extends SrcOp {
    public Decl label;
    public String listName;

    public AddToLabelList(OutputModelFactory factory, String listName, Decl label) {
        super(factory);
        this.label = label;
        this.listName = listName;
    }
}

