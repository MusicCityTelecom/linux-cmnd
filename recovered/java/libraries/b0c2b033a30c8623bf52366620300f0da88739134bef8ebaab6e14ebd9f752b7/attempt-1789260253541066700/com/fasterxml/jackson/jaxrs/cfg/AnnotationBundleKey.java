/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.jaxrs.cfg;

import java.lang.annotation.Annotation;

public final class AnnotationBundleKey {
    private static final Annotation[] NO_ANNOTATIONS = new Annotation[0];
    private final Annotation[] _annotations;
    private final Class<?> _type;
    private final boolean _annotationsCopied;
    private final int _hashCode;

    public AnnotationBundleKey(Annotation[] annotations, Class<?> type) {
        this._type = type;
        int typeHash = type.getName().hashCode();
        if (annotations == null || annotations.length == 0) {
            annotations = NO_ANNOTATIONS;
            this._annotationsCopied = true;
            this._hashCode = typeHash;
        } else {
            this._annotationsCopied = false;
            this._hashCode = AnnotationBundleKey.calcHash(annotations) ^ typeHash;
        }
        this._annotations = annotations;
    }

    private AnnotationBundleKey(Annotation[] annotations, Class<?> type, int hashCode) {
        this._annotations = annotations;
        this._annotationsCopied = true;
        this._type = type;
        this._hashCode = hashCode;
    }

    private static final int calcHash(Annotation[] annotations) {
        int len;
        int hash = len = annotations.length;
        for (int i = 0; i < len; ++i) {
            hash = hash * 31 + annotations[i].hashCode();
        }
        return hash;
    }

    public AnnotationBundleKey immutableKey() {
        if (this._annotationsCopied) {
            return this;
        }
        int len = this._annotations.length;
        Annotation[] newAnnotations = new Annotation[len];
        System.arraycopy(this._annotations, 0, newAnnotations, 0, len);
        return new AnnotationBundleKey(newAnnotations, this._type, this._hashCode);
    }

    public int hashCode() {
        return this._hashCode;
    }

    public String toString() {
        return "[Annotations: " + this._annotations.length + ", type: " + this._type.getName() + ", hash 0x" + Integer.toHexString(this._hashCode) + ", copied: " + this._annotationsCopied + "]";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        AnnotationBundleKey other = (AnnotationBundleKey)o;
        if (other._hashCode != this._hashCode || other._type != this._type) {
            return false;
        }
        return this._equals(other._annotations);
    }

    private final boolean _equals(Annotation[] otherAnn) {
        int len = this._annotations.length;
        if (otherAnn.length != len) {
            return false;
        }
        switch (len) {
            default: {
                for (int i = 0; i < len; ++i) {
                    if (this._annotations[i].equals(otherAnn[i])) continue;
                    return false;
                }
                return true;
            }
            case 3: {
                if (!this._annotations[2].equals(otherAnn[2])) {
                    return false;
                }
            }
            case 2: {
                if (!this._annotations[1].equals(otherAnn[1])) {
                    return false;
                }
            }
            case 1: {
                if (this._annotations[0].equals(otherAnn[0])) break;
                return false;
            }
            case 0: 
        }
        return true;
    }
}

