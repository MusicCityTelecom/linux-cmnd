/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.tree;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface PositionTrackingStream<T> {
    public T getKnownPositionElement(boolean var1);

    public boolean hasPositionInformation(T var1);
}

