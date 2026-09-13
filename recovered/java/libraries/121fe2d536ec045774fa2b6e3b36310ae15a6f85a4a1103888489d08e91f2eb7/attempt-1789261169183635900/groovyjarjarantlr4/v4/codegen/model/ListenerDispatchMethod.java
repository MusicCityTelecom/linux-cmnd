/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.DispatchMethod;

public class ListenerDispatchMethod
extends DispatchMethod {
    public boolean isEnter;

    public ListenerDispatchMethod(OutputModelFactory factory, boolean isEnter) {
        super(factory);
        this.isEnter = isEnter;
    }
}

