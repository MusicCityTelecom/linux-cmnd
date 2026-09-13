/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.HttpStatus
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.boot.web.server;

import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

public class ErrorPage {
    private final HttpStatus status;
    private final Class<? extends Throwable> exception;
    private final String path;

    public ErrorPage(String path) {
        this.status = null;
        this.exception = null;
        this.path = path;
    }

    public ErrorPage(HttpStatus status, String path) {
        this.status = status;
        this.exception = null;
        this.path = path;
    }

    public ErrorPage(Class<? extends Throwable> exception, String path) {
        this.status = null;
        this.exception = exception;
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public Class<? extends Throwable> getException() {
        return this.exception;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public int getStatusCode() {
        return this.status != null ? this.status.value() : 0;
    }

    public String getExceptionName() {
        return this.exception != null ? this.exception.getName() : null;
    }

    public boolean isGlobal() {
        return this.status == null && this.exception == null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof ErrorPage) {
            ErrorPage other = (ErrorPage)obj;
            return ObjectUtils.nullSafeEquals((Object)this.getExceptionName(), (Object)other.getExceptionName()) && ObjectUtils.nullSafeEquals((Object)this.path, (Object)other.path) && this.status == other.status;
        }
        return false;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + ObjectUtils.nullSafeHashCode((Object)this.getExceptionName());
        result = 31 * result + ObjectUtils.nullSafeHashCode((Object)this.path);
        result = 31 * result + this.getStatusCode();
        return result;
    }
}

