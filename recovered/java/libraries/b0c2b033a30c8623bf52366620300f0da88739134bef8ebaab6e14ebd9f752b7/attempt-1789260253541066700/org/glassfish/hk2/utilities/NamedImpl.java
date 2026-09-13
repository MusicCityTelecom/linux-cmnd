/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities;

import javax.inject.Named;
import org.glassfish.hk2.api.AnnotationLiteral;

public class NamedImpl
extends AnnotationLiteral<Named>
implements Named {
    private static final long serialVersionUID = 9110325112008963155L;
    private final String name;

    public NamedImpl(String name) {
        this.name = name;
    }

    @Override
    public String value() {
        return this.name;
    }

    @Override
    public String toString() {
        return "@Named(" + this.name + ")";
    }
}

