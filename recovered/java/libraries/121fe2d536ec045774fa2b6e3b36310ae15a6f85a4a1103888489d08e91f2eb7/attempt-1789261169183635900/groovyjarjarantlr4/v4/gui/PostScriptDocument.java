/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.gui;

import groovyjarjarantlr4.v4.gui.SystemFontMetrics;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PostScriptDocument {
    public static final String DEFAULT_FONT = "Courier New";
    public static final Map<String, String> POSTSCRIPT_FONT_NAMES = new HashMap<String, String>();
    protected int boundingBoxWidth;
    protected int boundingBoxHeight;
    protected SystemFontMetrics fontMetrics;
    protected String fontName;
    protected int fontSize = 12;
    protected double lineWidth = 0.3;
    protected String boundingBox;
    protected StringBuilder ps = new StringBuilder();
    protected boolean closed = false;

    public PostScriptDocument() {
        this(DEFAULT_FONT, 12);
    }

    public PostScriptDocument(String fontName, int fontSize) {
        this.header();
        this.setFont(fontName, fontSize);
    }

    public String getPS() {
        this.close();
        return this.header() + this.ps.toString();
    }

    public void boundingBox(int w, int h) {
        this.boundingBoxWidth = w;
        this.boundingBoxHeight = h;
        this.boundingBox = String.format(Locale.US, "%%%%BoundingBox: %d %d %d %d\n", 0, 0, this.boundingBoxWidth, this.boundingBoxHeight);
    }

    public void close() {
        if (this.closed) {
            return;
        }
        this.ps.append("%%Trailer\n");
        this.closed = true;
    }

    protected StringBuilder header() {
        StringBuilder b = new StringBuilder();
        b.append("%!PS-Adobe-3.0 EPSF-3.0\n");
        b.append(this.boundingBox).append("\n");
        b.append("0.3 setlinewidth\n");
        b.append("%% x y w h highlight\n/highlight {\n        4 dict begin\n        /h exch def\n        /w exch def\n        /y exch def\n        /x exch def\n        gsave\n        newpath\n        x y moveto\n        0 h rlineto     % up to left corner\n        w 0 rlineto     % to upper right corner\n        0 h neg rlineto % to lower right corner\n        w neg 0 rlineto % back home to lower left corner\n        closepath\n        .95 .83 .82 setrgbcolor\n        fill\n        grestore\n        end\n} def\n");
        return b;
    }

    public void setFont(String fontName, int fontSize) {
        this.fontMetrics = new SystemFontMetrics(fontName);
        this.fontName = this.fontMetrics.getFont().getPSName();
        this.fontSize = fontSize;
        String psname = POSTSCRIPT_FONT_NAMES.get(this.fontName);
        if (psname == null) {
            psname = this.fontName;
        }
        this.ps.append(String.format(Locale.US, "/%s findfont %d scalefont setfont\n", psname, fontSize));
    }

    public void lineWidth(double w) {
        this.lineWidth = w;
        this.ps.append(w).append(" setlinewidth\n");
    }

    public void move(double x, double y) {
        this.ps.append(String.format(Locale.US, "%1.3f %1.3f moveto\n", x, y));
    }

    public void lineto(double x, double y) {
        this.ps.append(String.format(Locale.US, "%1.3f %1.3f lineto\n", x, y));
    }

    public void line(double x1, double y1, double x2, double y2) {
        this.move(x1, y1);
        this.lineto(x2, y2);
    }

    public void rect(double x, double y, double width, double height) {
        this.line(x, y, x, y + height);
        this.line(x, y + height, x + width, y + height);
        this.line(x + width, y + height, x + width, y);
        this.line(x + width, y, x, y);
    }

    public void highlight(double x, double y, double width, double height) {
        this.ps.append(String.format(Locale.US, "%1.3f %1.3f %1.3f %1.3f highlight\n", x, y, width, height));
    }

    public void stroke() {
        this.ps.append("stroke\n");
    }

    public void text(String s, double x, double y) {
        StringBuilder buf = new StringBuilder();
        block3: for (char c : s.toCharArray()) {
            switch (c) {
                case '(': 
                case ')': 
                case '\\': {
                    buf.append('\\');
                    buf.append(c);
                    continue block3;
                }
                default: {
                    buf.append(c);
                }
            }
        }
        s = buf.toString();
        this.move(x, y);
        this.ps.append(String.format(Locale.US, "(%s) show\n", s));
        this.stroke();
    }

    public double getWidth(char c) {
        return this.fontMetrics.getWidth(c, this.fontSize);
    }

    public double getWidth(String s) {
        return this.fontMetrics.getWidth(s, this.fontSize);
    }

    public double getLineHeight() {
        return this.fontMetrics.getLineHeight(this.fontSize);
    }

    public int getFontSize() {
        return this.fontSize;
    }

    static {
        POSTSCRIPT_FONT_NAMES.put("SansSerif.plain", "ArialMT");
        POSTSCRIPT_FONT_NAMES.put("SansSerif.bold", "Arial-BoldMT");
        POSTSCRIPT_FONT_NAMES.put("SansSerif.italic", "Arial-ItalicMT");
        POSTSCRIPT_FONT_NAMES.put("SansSerif.bolditalic", "Arial-BoldItalicMT");
        POSTSCRIPT_FONT_NAMES.put("Serif.plain", "TimesNewRomanPSMT");
        POSTSCRIPT_FONT_NAMES.put("Serif.bold", "TimesNewRomanPS-BoldMT");
        POSTSCRIPT_FONT_NAMES.put("Serif.italic", "TimesNewRomanPS-ItalicMT");
        POSTSCRIPT_FONT_NAMES.put("Serif.bolditalic", "TimesNewRomanPS-BoldItalicMT");
        POSTSCRIPT_FONT_NAMES.put("Monospaced.plain", "CourierNewPSMT");
        POSTSCRIPT_FONT_NAMES.put("Monospaced.bold", "CourierNewPS-BoldMT");
        POSTSCRIPT_FONT_NAMES.put("Monospaced.italic", "CourierNewPS-ItalicMT");
        POSTSCRIPT_FONT_NAMES.put("Monospaced.bolditalic", "CourierNewPS-BoldItalicMT");
    }
}

