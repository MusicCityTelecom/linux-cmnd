/*
 * Decompiled with CFR 0.152.
 */
package org.aspectj.weaver;

import org.aspectj.weaver.AnnotationValue;

public class AnnotationNameValuePair {
    private String name;
    private AnnotationValue val;

    public AnnotationNameValuePair(String name, AnnotationValue val) {
        this.name = name;
        this.val = val;
    }

    public String getName() {
        return this.name;
    }

    public AnnotationValue getValue() {
        return this.val;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name + "=" + this.val.toString());
        return sb.toString();
    }

    public String stringify() {
        StringBuilder sb = new StringBuilder();
        if (!this.name.equals("value")) {
            sb.append(this.name + "=");
        }
        sb.append(this.val.stringify());
        return sb.toString();
    }
}

