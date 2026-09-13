/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.gui;

import groovyjarjarantlr4.v4.gui.BasicFontMetrics;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;

public class SystemFontMetrics
extends BasicFontMetrics {
    protected final Font font;

    public SystemFontMetrics(String fontName) {
        BufferedImage img = new BufferedImage(40, 40, 6);
        Graphics2D graphics = GraphicsEnvironment.getLocalGraphicsEnvironment().createGraphics(img);
        FontRenderContext fontRenderContext = graphics.getFontRenderContext();
        this.font = new Font(fontName, 0, 1000);
        double maxHeight = 0.0;
        for (int i = 0; i < 255; ++i) {
            TextLayout layout = new TextLayout(Character.toString((char)i), this.font, fontRenderContext);
            maxHeight = Math.max(maxHeight, layout.getBounds().getHeight());
            this.widths[i] = (int)layout.getAdvance();
        }
        this.maxCharHeight = (int)Math.round(maxHeight);
    }

    public Font getFont() {
        return this.font;
    }
}

