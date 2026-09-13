/*
 * Decompiled with CFR 0.152.
 */
package groovy.lang;

import groovy.lang.GroovyRuntimeException;
import org.codehaus.groovy.runtime.FormatHelper;
import org.codehaus.groovy.runtime.MethodRankHelper;

public class MissingMethodException
extends GroovyRuntimeException {
    private static final long serialVersionUID = -6676430495683939401L;
    private final String method;
    private final Class<?> type;
    private final boolean isStatic;
    private final Object[] arguments;

    public MissingMethodException(String method, Class<?> type, Object[] arguments) {
        this(method, type, arguments, false);
    }

    public MissingMethodException(String method, Class<?> type, Object[] arguments, boolean isStatic) {
        this.method = method;
        this.type = type;
        this.isStatic = isStatic;
        this.arguments = arguments != null ? arguments : new Object[]{};
    }

    public Object[] getArguments() {
        return this.arguments;
    }

    @Override
    public String getMessage() {
        return "No signature of method: " + (this.isStatic ? "static " : "") + (this.type != null ? this.type.getName() : "<unknown>") + "." + this.method + "() is applicable for argument types: (" + FormatHelper.toTypeString(this.arguments, 60) + ") values: " + FormatHelper.toArrayString(this.arguments, 60, true) + (this.type != null ? MethodRankHelper.getMethodSuggestionString(this.method, this.type, this.arguments) : "");
    }

    public String getMethod() {
        return this.method;
    }

    public Class<?> getType() {
        return this.type;
    }

    public boolean isStatic() {
        return this.isStatic;
    }
}

