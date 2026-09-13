/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.gui;

public abstract class BasicFontMetrics {
    public static final int MAX_CHAR = 255;
    protected int maxCharHeight;
    protected int[] widths = new int[256];

    public double getWidth(String s, int fontSize) {
        double w = 0.0;
        for (char c : s.toCharArray()) {
            w += this.getWidth(c, fontSize);
        }
        return w;
    }

    public double getWidth(char c, int fontSize) {
        if (c > '\u00ff' || this.widths[c] == 0) {
            return (double)this.widths[109] / 1000.0;
        }
        return (double)this.widths[c] / 1000.0 * (double)fontSize;
    }

    public double getLineHeight(int fontSize) {
        return (double)this.maxCharHeight / 1000.0 * (double)fontSize;
    }
}

