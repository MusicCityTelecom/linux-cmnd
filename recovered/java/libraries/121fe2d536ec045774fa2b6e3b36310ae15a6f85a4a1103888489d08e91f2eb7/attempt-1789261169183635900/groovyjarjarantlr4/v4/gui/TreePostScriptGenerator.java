/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.abego.treelayout.Configuration
 *  org.abego.treelayout.Configuration$Location
 *  org.abego.treelayout.NodeExtentProvider
 *  org.abego.treelayout.TreeForTreeLayout
 *  org.abego.treelayout.TreeLayout
 *  org.abego.treelayout.util.DefaultConfiguration
 */
package groovyjarjarantlr4.v4.gui;

import groovyjarjarantlr4.v4.gui.PostScriptDocument;
import groovyjarjarantlr4.v4.gui.TreeLayoutAdaptor;
import groovyjarjarantlr4.v4.gui.TreeTextProvider;
import groovyjarjarantlr4.v4.gui.TreeViewer;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import groovyjarjarantlr4.v4.runtime.misc.Utils;
import groovyjarjarantlr4.v4.runtime.tree.ErrorNode;
import groovyjarjarantlr4.v4.runtime.tree.Tree;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.util.List;
import org.abego.treelayout.Configuration;
import org.abego.treelayout.NodeExtentProvider;
import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;

public class TreePostScriptGenerator {
    protected double gapBetweenLevels = 17.0;
    protected double gapBetweenNodes = 7.0;
    protected int nodeWidthPadding = 1;
    protected int nodeHeightPaddingAbove = 0;
    protected int nodeHeightPaddingBelow = 5;
    protected Tree root;
    protected TreeTextProvider treeTextProvider;
    protected TreeLayout<Tree> treeLayout;
    protected PostScriptDocument doc;

    public TreePostScriptGenerator(@Nullable List<String> ruleNames, Tree root) {
        this(ruleNames, root, "Courier New", 11);
    }

    public TreePostScriptGenerator(@Nullable List<String> ruleNames, Tree root, String fontName, int fontSize) {
        this.root = root;
        this.setTreeTextProvider(new TreeViewer.DefaultTreeTextProvider(ruleNames));
        this.doc = new PostScriptDocument(fontName, fontSize);
        boolean compareNodeIdentities = true;
        this.treeLayout = new TreeLayout(this.getTreeLayoutAdaptor(root), (NodeExtentProvider)new VariableExtentProvide(), (Configuration)new DefaultConfiguration(this.gapBetweenLevels, this.gapBetweenNodes, Configuration.Location.Bottom), compareNodeIdentities);
    }

    public TreeForTreeLayout<Tree> getTreeLayoutAdaptor(Tree root) {
        return new TreeLayoutAdaptor(root);
    }

    public String getPS() {
        this.generateEdges((Tree)this.getTree().getRoot());
        for (Tree node : this.treeLayout.getNodeBounds().keySet()) {
            this.generateNode(node);
        }
        Dimension size = this.treeLayout.getBounds().getBounds().getSize();
        this.doc.boundingBox(size.width, size.height);
        this.doc.close();
        return this.doc.getPS();
    }

    protected void generateEdges(Tree parent) {
        if (!this.getTree().isLeaf((Object)parent)) {
            Rectangle2D.Double parentBounds = this.getBoundsOfNode(parent);
            double x1 = parentBounds.getCenterX();
            double y1 = parentBounds.y;
            for (Tree child : this.getChildren(parent)) {
                Rectangle2D.Double childBounds = this.getBoundsOfNode(child);
                double x2 = childBounds.getCenterX();
                double y2 = childBounds.getMaxY();
                this.doc.line(x1, y1, x2, y2);
                this.generateEdges(child);
            }
        }
    }

    protected void generateNode(Tree t) {
        String[] lines = this.getText(t).split("\n");
        Rectangle2D.Double box = this.getBoundsOfNode(t);
        if (t instanceof ErrorNode) {
            this.doc.highlight(box.x, box.y, box.width, box.height);
        }
        double x = box.x + (double)this.nodeWidthPadding;
        double y = box.y + (double)this.nodeHeightPaddingBelow;
        for (int i = 0; i < lines.length; ++i) {
            this.doc.text(lines[i], x, y);
            y += this.doc.getLineHeight();
        }
    }

    protected TreeForTreeLayout<Tree> getTree() {
        return this.treeLayout.getTree();
    }

    protected Iterable<Tree> getChildren(Tree parent) {
        return this.getTree().getChildren((Object)parent);
    }

    protected Rectangle2D.Double getBoundsOfNode(Tree node) {
        return (Rectangle2D.Double)this.treeLayout.getNodeBounds().get(node);
    }

    protected String getText(Tree tree) {
        String s = this.treeTextProvider.getText(tree);
        s = Utils.escapeWhitespace(s, false);
        return s;
    }

    public TreeTextProvider getTreeTextProvider() {
        return this.treeTextProvider;
    }

    public void setTreeTextProvider(TreeTextProvider treeTextProvider) {
        this.treeTextProvider = treeTextProvider;
    }

    public class VariableExtentProvide
    implements NodeExtentProvider<Tree> {
        public double getWidth(Tree tree) {
            String s = TreePostScriptGenerator.this.getText(tree);
            return TreePostScriptGenerator.this.doc.getWidth(s) + (double)(TreePostScriptGenerator.this.nodeWidthPadding * 2);
        }

        public double getHeight(Tree tree) {
            String s = TreePostScriptGenerator.this.getText(tree);
            double h = TreePostScriptGenerator.this.doc.getLineHeight() + (double)TreePostScriptGenerator.this.nodeHeightPaddingAbove + (double)TreePostScriptGenerator.this.nodeHeightPaddingBelow;
            String[] lines = s.split("\n");
            return h * (double)lines.length;
        }
    }
}

