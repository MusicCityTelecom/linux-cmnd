/*
 * Decompiled with CFR 0.152.
 */
package groovy.lang;

import groovy.lang.Tuple;

public final class Tuple0
extends Tuple {
    private static final long serialVersionUID = -3791115121904072346L;
    public static final Tuple0 INSTANCE = new Tuple0();

    private Tuple0() {
        super(new Object[0]);
    }

    @Override
    public Tuple0 clone() {
        return INSTANCE;
    }
}

