/*
 * Decompiled with CFR 0.152.
 */
package groovy.security;

import java.security.BasicPermission;

public final class GroovyCodeSourcePermission
extends BasicPermission {
    private static final long serialVersionUID = 8014290770546281019L;

    public GroovyCodeSourcePermission(String name) {
        super(name);
    }

    public GroovyCodeSourcePermission(String name, String actions) {
        super(name, actions);
    }
}

