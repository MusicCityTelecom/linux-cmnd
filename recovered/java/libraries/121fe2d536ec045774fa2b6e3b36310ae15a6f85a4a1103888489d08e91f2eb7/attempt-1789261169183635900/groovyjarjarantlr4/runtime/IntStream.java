/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime;

public interface IntStream {
    public void consume();

    public int LA(int var1);

    public int mark();

    public int index();

    public void rewind(int var1);

    public void rewind();

    public void release(int var1);

    public void seek(int var1);

    public int size();

    public String getSourceName();
}

