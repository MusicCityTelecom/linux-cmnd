/*
 * Decompiled with CFR 0.152.
 */
package groovy.lang;

import groovy.lang.Sequence;
import java.util.List;

public class NonEmptySequence
extends Sequence {
    private static final long serialVersionUID = 1614604919062836998L;

    public NonEmptySequence() {
        super((Class)null);
    }

    public NonEmptySequence(Class type) {
        super(type);
    }

    public NonEmptySequence(Class type, List content) {
        super(type, content);
    }

    @Override
    public int minimumSize() {
        return 1;
    }
}

