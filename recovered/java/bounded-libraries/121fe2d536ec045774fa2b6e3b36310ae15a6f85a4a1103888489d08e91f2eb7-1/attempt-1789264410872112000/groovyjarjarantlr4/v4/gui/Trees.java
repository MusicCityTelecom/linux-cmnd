/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.gui;

import groovyjarjarantlr4.v4.gui.TreePostScriptGenerator;
import groovyjarjarantlr4.v4.gui.TreeTextProvider;
import groovyjarjarantlr4.v4.gui.TreeViewer;
import groovyjarjarantlr4.v4.runtime.Parser;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import groovyjarjarantlr4.v4.runtime.misc.Utils;
import groovyjarjarantlr4.v4.runtime.tree.Tree;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;
import javax.print.PrintException;
import javax.swing.JDialog;

public class Trees {
    public static Future<JDialog> inspect(@Nullable Tree t, @Nullable List<String> ruleNames) {
        TreeViewer viewer = new TreeViewer(ruleNames, t);
        return viewer.open();
    }

    public static Future<JDialog> inspect(@Nullable Tree t, @Nullable Parser parser) {
        List<String> ruleNames = parser != null ? Arrays.asList(parser.getRuleNames()) : null;
        return Trees.inspect(t, ruleNames);
    }

    public static void save(@Nullable Tree t, @Nullable Parser parser, String fileName) throws IOException, PrintException {
        List<String> ruleNames = parser != null ? Arrays.asList(parser.getRuleNames()) : null;
        Trees.save(t, ruleNames, fileName);
    }

    public static void save(Tree t, @Nullable Parser parser, String fileName, String fontName, int fontSize) throws IOException {
        List<String> ruleNames = parser != null ? Arrays.asList(parser.getRuleNames()) : null;
        Trees.save(t, ruleNames, fileName, fontName, fontSize);
    }

    public static void save(Tree t, @Nullable List<String> ruleNames, String fileName) throws IOException, PrintException {
        Trees.writePS(t, ruleNames, fileName);
    }

    public static void save(Tree t, @Nullable List<String> ruleNames, String fileName, String fontName, int fontSize) throws IOException {
        Trees.writePS(t, ruleNames, fileName, fontName, fontSize);
    }

    public static String getPS(Tree t, @Nullable List<String> ruleNames, String fontName, int fontSize) {
        TreePostScriptGenerator psgen = new TreePostScriptGenerator(ruleNames, t, fontName, fontSize);
        return psgen.getPS();
    }

    public static String getPS(Tree t, @Nullable List<String> ruleNames) {
        return Trees.getPS(t, ruleNames, "Helvetica", 11);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void writePS(Tree t, @Nullable List<String> ruleNames, String fileName, String fontName, int fontSize) throws IOException {
        String ps = Trees.getPS(t, ruleNames, fontName, fontSize);
        FileWriter f = new FileWriter(fileName);
        BufferedWriter bw = new BufferedWriter(f);
        try {
            bw.write(ps);
        }
        finally {
            bw.close();
        }
    }

    public static void writePS(Tree t, @Nullable List<String> ruleNames, String fileName) throws IOException {
        Trees.writePS(t, ruleNames, fileName, "Helvetica", 11);
    }

    public static String toStringTree(@Nullable Tree t, @NotNull TreeTextProvider nodeTextProvider) {
        if (t == null) {
            return "null";
        }
        String s = Utils.escapeWhitespace(nodeTextProvider.getText(t), false);
        if (t.getChildCount() == 0) {
            return s;
        }
        StringBuilder buf = new StringBuilder();
        buf.append("(");
        s = Utils.escapeWhitespace(nodeTextProvider.getText(t), false);
        buf.append(s);
        buf.append(' ');
        for (int i = 0; i < t.getChildCount(); ++i) {
            if (i > 0) {
                buf.append(' ');
            }
            buf.append(Trees.toStringTree(t.getChild(i), nodeTextProvider));
        }
        buf.append(")");
        return buf.toString();
    }

    private Trees() {
    }
}

