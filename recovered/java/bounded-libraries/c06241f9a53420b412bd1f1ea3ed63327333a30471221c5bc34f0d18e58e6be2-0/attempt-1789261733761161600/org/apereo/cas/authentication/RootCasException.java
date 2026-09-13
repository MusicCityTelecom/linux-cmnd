/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.authentication;

import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

public class RootCasException
extends RuntimeException {
    private static final long serialVersionUID = -2384466176716541689L;
    private final String code;
    private final List<Object> args = new ArrayList<Object>(0);

    protected RootCasException(String code) {
        super(code);
        this.code = code;
    }

    protected RootCasException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    protected RootCasException(String code, String msg, List<Object> args) {
        this(code, msg);
        this.args.addAll(args);
    }

    protected RootCasException(String code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }

    protected RootCasException(String code, Throwable throwable, List<Object> args) {
        this(code, throwable);
        this.args.addAll(args);
    }

    public static RootCasException withCode(String code) {
        return new RootCasException(code, "");
    }

    public String getCode() {
        Throwable cause = this.getCause();
        if (cause instanceof RootCasException) {
            return ((RootCasException)cause).getCode();
        }
        return this.code;
    }

    @Generated
    public List<Object> getArgs() {
        return this.args;
    }
}

