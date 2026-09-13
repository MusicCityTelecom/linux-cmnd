/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.abego.treelayout.Configuration
 *  org.abego.treelayout.NodeExtentProvider
 *  org.abego.treelayout.TreeForTreeLayout
 *  org.abego.treelayout.TreeLayout
 *  org.abego.treelayout.util.DefaultConfiguration
 */
package groovyjarjarantlr4.v4.gui;

import groovyjarjarantlr4.v4.gui.GraphicsSupport;
import groovyjarjarantlr4.v4.gui.JFileChooserConfirmOverwrite;
import groovyjarjarantlr4.v4.gui.TreeLayoutAdaptor;
import groovyjarjarantlr4.v4.gui.TreeTextProvider;
import groovyjarjarantlr4.v4.runtime.ParserRuleContext;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import groovyjarjarantlr4.v4.runtime.misc.Utils;
import groovyjarjarantlr4.v4.runtime.tree.ErrorNode;
import groovyjarjarantlr4.v4.runtime.tree.Tree;
import groovyjarjarantlr4.v4.runtime.tree.Trees;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import javax.print.PrintException;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import org.abego.treelayout.Configuration;
import org.abego.treelayout.NodeExtentProvider;
import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;

public class TreeViewer
extends JComponent {
    public static final Color LIGHT_RED = new Color(244, 213, 211);
    protected TreeTextProvider treeTextProvider;
    protected TreeLayout<Tree> treeLayout;
    protected List<Tree> highlightedNodes;
    protected String fontName = "Helvetica";
    protected int fontStyle = 0;
    protected int fontSize = 11;
    protected Font font = new Font(this.fontName, this.fontStyle, this.fontSize);
    protected double gapBetweenLevels = 17.0;
    protected double gapBetweenNodes = 7.0;
    protected int nodeWidthPadding = 2;
    protected int nodeHeightPadding = 0;
    protected int arcSize = 0;
    protected double scale = 1.0;
    protected Color boxColor = null;
    protected Color highlightedBoxColor = Color.lightGray;
    protected Color borderColor = null;
    protected Color textColor = Color.black;
    private boolean useCurvedEdges = false;
    private static final String DIALOG_WIDTH_PREFS_KEY = "dialog_width";
    private static final String DIALOG_HEIGHT_PREFS_KEY = "dialog_height";
    private static final String DIALOG_X_PREFS_KEY = "dialog_x";
    private static final String DIALOG_Y_PREFS_KEY = "dialog_y";
    private static final String DIALOG_DIVIDER_LOC_PREFS_KEY = "dialog_divider_location";
    private static final String DIALOG_VIEWER_SCALE_PREFS_KEY = "dialog_viewer_scale";

    public TreeViewer(@Nullable List<String> ruleNames, Tree tree) {
        this.setRuleNames(ruleNames);
        if (tree != null) {
            this.setTree(tree);
        }
        this.setFont(this.font);
    }

    private void updatePreferredSize() {
        this.setPreferredSize(this.getScaledTreeSize());
        this.invalidate();
        if (this.getParent() != null) {
            this.getParent().validate();
        }
        this.repaint();
    }

    public boolean getUseCurvedEdges() {
        return this.useCurvedEdges;
    }

    public void setUseCurvedEdges(boolean useCurvedEdges) {
        this.useCurvedEdges = useCurvedEdges;
    }

    protected void paintEdges(Graphics g, Tree parent) {
        if (!this.getTree().isLeaf((Object)parent)) {
            BasicStroke stroke = new BasicStroke(1.0f, 1, 1);
            ((Graphics2D)g).setStroke(stroke);
            Rectangle2D.Double parentBounds = this.getBoundsOfNode(parent);
            double x1 = parentBounds.getCenterX();
            double y1 = parentBounds.getMaxY();
            for (Tree child : this.getTree().getChildren((Object)parent)) {
                Rectangle2D.Double childBounds = this.getBoundsOfNode(child);
                double x2 = childBounds.getCenterX();
                double y2 = childBounds.getMinY();
                if (this.getUseCurvedEdges()) {
                    CubicCurve2D.Double c = new CubicCurve2D.Double();
                    double ctrlx1 = x1;
                    double ctrly1 = (y1 + y2) / 2.0;
                    double ctrlx2 = x2;
                    double ctrly2 = y1;
                    ((CubicCurve2D)c).setCurve(x1, y1, ctrlx1, ctrly1, ctrlx2, ctrly2, x2, y2);
                    ((Graphics2D)g).draw(c);
                } else {
                    g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
                }
                this.paintEdges(g, child);
            }
        }
    }

    protected void paintBox(Graphics g, Tree tree) {
        Rectangle2D.Double box = this.getBoundsOfNode(tree);
        boolean ruleFailedAndMatchedNothing = false;
        if (tree instanceof ParserRuleContext) {
            ParserRuleContext ctx = (ParserRuleContext)tree;
            boolean bl = ruleFailedAndMatchedNothing = ctx.exception != null && ctx.stop != null && ctx.stop.getTokenIndex() < ctx.start.getTokenIndex();
        }
        if (this.isHighlighted(tree) || this.boxColor != null || tree instanceof ErrorNode || ruleFailedAndMatchedNothing) {
            if (this.isHighlighted(tree)) {
                g.setColor(this.highlightedBoxColor);
            } else if (tree instanceof ErrorNode || ruleFailedAndMatchedNothing) {
                g.setColor(LIGHT_RED);
            } else {
                g.setColor(this.boxColor);
            }
            g.fillRoundRect((int)box.x, (int)box.y, (int)box.width - 1, (int)box.height - 1, this.arcSize, this.arcSize);
        }
        if (this.borderColor != null) {
            g.setColor(this.borderColor);
            g.drawRoundRect((int)box.x, (int)box.y, (int)box.width - 1, (int)box.height - 1, this.arcSize, this.arcSize);
        }
        g.setColor(this.textColor);
        String s = this.getText(tree);
        String[] lines = s.split("\n");
        FontMetrics m = this.getFontMetrics(this.font);
        int x = (int)box.x + this.arcSize / 2 + this.nodeWidthPadding;
        int y = (int)box.y + m.getAscent() + m.getLeading() + 1 + this.nodeHeightPadding;
        for (int i = 0; i < lines.length; ++i) {
            this.text(g, lines[i], x, y);
            y += m.getHeight();
        }
    }

    public void text(Graphics g, String s, int x, int y) {
        s = Utils.escapeWhitespace(s, true);
        g.drawString(s, x, y);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (this.treeLayout == null) {
            return;
        }
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        this.paintEdges(g, (Tree)this.getTree().getRoot());
        for (Tree Tree2 : this.treeLayout.getNodeBounds().keySet()) {
            this.paintBox(g, Tree2);
        }
    }

    protected void generateEdges(Writer writer, Tree parent) throws IOException {
        if (!this.getTree().isLeaf((Object)parent)) {
            Rectangle2D.Double b1 = this.getBoundsOfNode(parent);
            double x1 = b1.getCenterX();
            double y1 = b1.getCenterY();
            for (Tree child : this.getTree().getChildren((Object)parent)) {
                Rectangle2D.Double childBounds = this.getBoundsOfNode(child);
                double x2 = childBounds.getCenterX();
                double y2 = childBounds.getMinY();
                writer.write(TreeViewer.line("" + x1, "" + y1, "" + x2, "" + y2, "stroke:black; stroke-width:1px;"));
                this.generateEdges(writer, child);
            }
        }
    }

    protected void generateBox(Writer writer, Tree parent) throws IOException {
        Rectangle2D.Double box = this.getBoundsOfNode(parent);
        writer.write(TreeViewer.rect("" + box.x, "" + box.y, "" + box.width, "" + box.height, "fill:orange; stroke:rgb(0,0,0);", "rx=\"1\""));
        String line = this.getText(parent).replace("<", "&lt;").replace(">", "&gt;");
        int fontSize = 10;
        int x = (int)box.x + 2;
        int y = (int)box.y + fontSize - 1;
        String style = String.format("font-family:sans-serif;font-size:%dpx;", fontSize);
        writer.write(TreeViewer.text("" + x, "" + y, style, line));
    }

    private static String line(String x1, String y1, String x2, String y2, String style) {
        return String.format("<line x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\" style=\"%s\" />\n", x1, y1, x2, y2, style);
    }

    private static String rect(String x, String y, String width, String height, String style, String extraAttributes) {
        return String.format("<rect x=\"%s\" y=\"%s\" width=\"%s\" height=\"%s\" style=\"%s\" %s/>\n", x, y, width, height, style, extraAttributes);
    }

    private static String text(String x, String y, String style, String text) {
        return String.format("<text x=\"%s\" y=\"%s\" style=\"%s\">\n%s\n</text>\n", x, y, style, text);
    }

    private void paintSVG(Writer writer) throws IOException {
        this.generateEdges(writer, (Tree)this.getTree().getRoot());
        for (Tree tree : this.treeLayout.getNodeBounds().keySet()) {
            this.generateBox(writer, tree);
        }
    }

    @Override
    protected Graphics getComponentGraphics(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.scale(this.scale, this.scale);
        return super.getComponentGraphics(g2d);
    }

    @NotNull
    protected static JDialog showInDialog(final TreeViewer viewer) {
        final JDialog dialog = new JDialog();
        dialog.setTitle("Parse Tree Inspector");
        final Preferences prefs = Preferences.userNodeForPackage(TreeViewer.class);
        JPanel mainPane = new JPanel(new BorderLayout(5, 5));
        JPanel contentPane = new JPanel(new BorderLayout(0, 0));
        ((Component)contentPane).setBackground(Color.white);
        JScrollPane scrollPane = new JScrollPane(viewer);
        contentPane.add((Component)scrollPane, "Center");
        JPanel wrapper = new JPanel(new FlowLayout());
        JPanel bottomPanel = new JPanel(new BorderLayout(0, 0));
        contentPane.add((Component)bottomPanel, "South");
        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispatchEvent(new WindowEvent(dialog, 201));
            }
        });
        wrapper.add(ok);
        JButton png = new JButton("Export as PNG");
        png.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TreeViewer.generatePNGFile(viewer, dialog);
            }
        });
        wrapper.add(png);
        JButton svg = new JButton("Export as SVG");
        svg.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TreeViewer.generateSVGFile(viewer, dialog);
            }
        });
        wrapper.add(svg);
        bottomPanel.add((Component)wrapper, "South");
        double lastKnownViewerScale = prefs.getDouble(DIALOG_VIEWER_SCALE_PREFS_KEY, viewer.getScale());
        viewer.setScale(lastKnownViewerScale);
        int sliderValue = (int)((lastKnownViewerScale - 1.0) * 1000.0);
        final JSlider scaleSlider = new JSlider(0, -999, 1000, sliderValue);
        scaleSlider.addChangeListener(new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent e) {
                int v = scaleSlider.getValue();
                viewer.setScale((double)v / 1000.0 + 1.0);
            }
        });
        bottomPanel.add((Component)scaleSlider, "Center");
        JPanel treePanel = new JPanel(new BorderLayout(5, 5));
        EmptyIcon empty = new EmptyIcon();
        UIManager.put("Tree.closedIcon", empty);
        UIManager.put("Tree.openIcon", empty);
        UIManager.put("Tree.leafIcon", empty);
        Tree parseTreeRoot = (Tree)viewer.getTree().getRoot();
        TreeNodeWrapper nodeRoot = new TreeNodeWrapper(parseTreeRoot, viewer);
        TreeViewer.fillTree(nodeRoot, parseTreeRoot, viewer);
        JTree tree = new JTree(nodeRoot);
        tree.getSelectionModel().setSelectionMode(1);
        tree.addTreeSelectionListener(new TreeSelectionListener(){

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                JTree selectedTree = (JTree)e.getSource();
                TreePath path = selectedTree.getSelectionPath();
                if (path != null) {
                    TreeNodeWrapper treeNode = (TreeNodeWrapper)path.getLastPathComponent();
                    viewer.setTree((Tree)treeNode.getUserObject());
                }
            }
        });
        treePanel.add(new JScrollPane(tree));
        final JSplitPane splitPane = new JSplitPane(1, treePanel, contentPane);
        mainPane.add((Component)splitPane, "Center");
        dialog.setContentPane(mainPane);
        WindowAdapter exitListener = new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent e) {
                prefs.putInt(TreeViewer.DIALOG_WIDTH_PREFS_KEY, (int)dialog.getSize().getWidth());
                prefs.putInt(TreeViewer.DIALOG_HEIGHT_PREFS_KEY, (int)dialog.getSize().getHeight());
                prefs.putDouble(TreeViewer.DIALOG_X_PREFS_KEY, dialog.getLocationOnScreen().getX());
                prefs.putDouble(TreeViewer.DIALOG_Y_PREFS_KEY, dialog.getLocationOnScreen().getY());
                prefs.putInt(TreeViewer.DIALOG_DIVIDER_LOC_PREFS_KEY, splitPane.getDividerLocation());
                prefs.putDouble(TreeViewer.DIALOG_VIEWER_SCALE_PREFS_KEY, viewer.getScale());
                dialog.setVisible(false);
                dialog.dispose();
            }
        };
        dialog.addWindowListener(exitListener);
        dialog.setDefaultCloseOperation(0);
        int width = prefs.getInt(DIALOG_WIDTH_PREFS_KEY, 600);
        int height = prefs.getInt(DIALOG_HEIGHT_PREFS_KEY, 500);
        dialog.setPreferredSize(new Dimension(width, height));
        dialog.pack();
        int dividerLocation = prefs.getInt(DIALOG_DIVIDER_LOC_PREFS_KEY, 200);
        splitPane.setDividerLocation(dividerLocation);
        if (prefs.getDouble(DIALOG_X_PREFS_KEY, -1.0) != -1.0) {
            dialog.setLocation((int)prefs.getDouble(DIALOG_X_PREFS_KEY, 100.0), (int)prefs.getDouble(DIALOG_Y_PREFS_KEY, 100.0));
        } else {
            dialog.setLocationRelativeTo(null);
        }
        dialog.setVisible(true);
        return dialog;
    }

    private static void generatePNGFile(TreeViewer viewer, JDialog dialog) {
        BufferedImage bi = new BufferedImage(viewer.getSize().width, viewer.getSize().height, 2);
        Graphics2D g = bi.createGraphics();
        viewer.paint(g);
        g.dispose();
        try {
            JFileChooser fileChooser = TreeViewer.getFileChooser(".png", "PNG files");
            int returnValue = fileChooser.showSaveDialog(dialog);
            if (returnValue == 0) {
                File pngFile = fileChooser.getSelectedFile();
                ImageIO.write((RenderedImage)bi, "png", pngFile);
                try {
                    Desktop.getDesktop().open(pngFile.getParentFile());
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Saved PNG to: " + pngFile.getAbsolutePath());
                    ex.printStackTrace();
                }
            }
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(dialog, "Could not export to PNG: " + ex.getMessage(), "Error", 0);
            ex.printStackTrace();
        }
    }

    private static JFileChooser getFileChooser(final String fileEnding, final String description) {
        File suggestedFile = TreeViewer.generateNonExistingFile(fileEnding);
        JFileChooserConfirmOverwrite fileChooser = new JFileChooserConfirmOverwrite();
        fileChooser.setCurrentDirectory(suggestedFile.getParentFile());
        fileChooser.setSelectedFile(suggestedFile);
        FileFilter filter = new FileFilter(){

            @Override
            public boolean accept(File pathname) {
                if (pathname.isFile()) {
                    return pathname.getName().toLowerCase().endsWith(fileEnding);
                }
                return true;
            }

            @Override
            public String getDescription() {
                return description + " (*" + fileEnding + ")";
            }
        };
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);
        return fileChooser;
    }

    private static void generateSVGFile(TreeViewer viewer, JDialog dialog) {
        try {
            JFileChooser fileChooser = TreeViewer.getFileChooser(".svg", "SVG files");
            int returnValue = fileChooser.showSaveDialog(dialog);
            if (returnValue == 0) {
                File svgFile = fileChooser.getSelectedFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(svgFile));
                writer.write("<svg width=\"" + viewer.getSize().getWidth() * 1.1 + "\" height=\"" + viewer.getSize().getHeight() * 1.1 + "\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">");
                viewer.paintSVG(writer);
                writer.write("</svg>");
                writer.flush();
                writer.close();
                try {
                    Desktop.getDesktop().open(svgFile.getParentFile());
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Saved SVG to: " + svgFile.getAbsolutePath());
                    ex.printStackTrace();
                }
            }
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(dialog, "Could not export to SVG: " + ex.getMessage(), "Error", 0);
            ex.printStackTrace();
        }
    }

    private static File generateNonExistingFile(String extension) {
        String parent = ".";
        String name = "antlr4_parse_tree";
        File file = new File(".", "antlr4_parse_tree" + extension);
        int counter = 1;
        while (file.exists()) {
            file = new File(".", "antlr4_parse_tree_" + counter + extension);
            ++counter;
        }
        return file;
    }

    private static void fillTree(TreeNodeWrapper node, Tree tree, TreeViewer viewer) {
        if (tree == null) {
            return;
        }
        for (int i = 0; i < tree.getChildCount(); ++i) {
            Tree childTree = tree.getChild(i);
            TreeNodeWrapper childNode = new TreeNodeWrapper(childTree, viewer);
            node.add(childNode);
            TreeViewer.fillTree(childNode, childTree, viewer);
        }
    }

    private Dimension getScaledTreeSize() {
        Dimension scaledTreeSize = this.treeLayout.getBounds().getBounds().getSize();
        scaledTreeSize = new Dimension((int)((double)scaledTreeSize.width * this.scale), (int)((double)scaledTreeSize.height * this.scale));
        return scaledTreeSize;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NotNull
    public Future<JDialog> open() {
        final TreeViewer viewer = this;
        viewer.setScale(1.5);
        Callable<JDialog> callable = new Callable<JDialog>(){
            JDialog result;

            @Override
            public JDialog call() throws Exception {
                SwingUtilities.invokeAndWait(new Runnable(){

                    @Override
                    public void run() {
                        result = TreeViewer.showInDialog(viewer);
                    }
                });
                return this.result;
            }
        };
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            Future<JDialog> future = executor.submit(callable);
            return future;
        }
        finally {
            executor.shutdown();
        }
    }

    public void save(String fileName) throws IOException, PrintException {
        JDialog dialog = new JDialog();
        Container contentPane = dialog.getContentPane();
        ((JComponent)contentPane).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.add(this);
        contentPane.setBackground(Color.white);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.dispose();
        GraphicsSupport.saveImage(this, fileName);
    }

    protected Rectangle2D.Double getBoundsOfNode(Tree node) {
        return (Rectangle2D.Double)this.treeLayout.getNodeBounds().get(node);
    }

    protected String getText(Tree tree) {
        String s = this.treeTextProvider.getText(tree);
        s = Utils.escapeWhitespace(s, true);
        return s;
    }

    public TreeTextProvider getTreeTextProvider() {
        return this.treeTextProvider;
    }

    public void setTreeTextProvider(TreeTextProvider treeTextProvider) {
        this.treeTextProvider = treeTextProvider;
    }

    public void setFontSize(int sz) {
        this.fontSize = sz;
        this.font = new Font(this.fontName, this.fontStyle, this.fontSize);
    }

    public void setFontName(String name) {
        this.fontName = name;
        this.font = new Font(this.fontName, this.fontStyle, this.fontSize);
    }

    public void addHighlightedNodes(Collection<Tree> nodes) {
        this.highlightedNodes = new ArrayList<Tree>();
        this.highlightedNodes.addAll(nodes);
    }

    public void removeHighlightedNodes(Collection<Tree> nodes) {
        if (this.highlightedNodes != null) {
            for (Tree t : nodes) {
                int i = this.getHighlightedNodeIndex(t);
                if (i < 0) continue;
                this.highlightedNodes.remove(i);
            }
        }
    }

    protected boolean isHighlighted(Tree node) {
        return this.getHighlightedNodeIndex(node) >= 0;
    }

    protected int getHighlightedNodeIndex(Tree node) {
        if (this.highlightedNodes == null) {
            return -1;
        }
        for (int i = 0; i < this.highlightedNodes.size(); ++i) {
            Tree t = this.highlightedNodes.get(i);
            if (t != node) continue;
            return i;
        }
        return -1;
    }

    @Override
    public Font getFont() {
        return this.font;
    }

    @Override
    public void setFont(Font font) {
        this.font = font;
    }

    public int getArcSize() {
        return this.arcSize;
    }

    public void setArcSize(int arcSize) {
        this.arcSize = arcSize;
    }

    public Color getBoxColor() {
        return this.boxColor;
    }

    public void setBoxColor(Color boxColor) {
        this.boxColor = boxColor;
    }

    public Color getHighlightedBoxColor() {
        return this.highlightedBoxColor;
    }

    public void setHighlightedBoxColor(Color highlightedBoxColor) {
        this.highlightedBoxColor = highlightedBoxColor;
    }

    public Color getBorderColor() {
        return this.borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public Color getTextColor() {
        return this.textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    protected TreeForTreeLayout<Tree> getTree() {
        return this.treeLayout.getTree();
    }

    public void setTree(Tree root) {
        if (root != null) {
            boolean useIdentity = true;
            this.treeLayout = new TreeLayout(this.getTreeLayoutAdaptor(root), (NodeExtentProvider)new VariableExtentProvide(this), (Configuration)new DefaultConfiguration(this.gapBetweenLevels, this.gapBetweenNodes), useIdentity);
            this.updatePreferredSize();
        } else {
            this.treeLayout = null;
            this.repaint();
        }
    }

    public TreeForTreeLayout<Tree> getTreeLayoutAdaptor(Tree root) {
        return new TreeLayoutAdaptor(root);
    }

    public double getScale() {
        return this.scale;
    }

    public void setScale(double scale) {
        if (scale <= 0.0) {
            scale = 1.0;
        }
        this.scale = scale;
        this.updatePreferredSize();
    }

    public void setRuleNames(List<String> ruleNames) {
        this.setTreeTextProvider(new DefaultTreeTextProvider(ruleNames));
    }

    private static class EmptyIcon
    implements Icon {
        private EmptyIcon() {
        }

        @Override
        public int getIconWidth() {
            return 0;
        }

        @Override
        public int getIconHeight() {
            return 0;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
        }
    }

    private static class TreeNodeWrapper
    extends DefaultMutableTreeNode {
        final TreeViewer viewer;

        TreeNodeWrapper(Tree tree, TreeViewer viewer) {
            super(tree);
            this.viewer = viewer;
        }

        @Override
        public String toString() {
            return this.viewer.getText((Tree)this.getUserObject());
        }
    }

    public static class VariableExtentProvide
    implements NodeExtentProvider<Tree> {
        TreeViewer viewer;

        public VariableExtentProvide(TreeViewer viewer) {
            this.viewer = viewer;
        }

        public double getWidth(Tree tree) {
            FontMetrics fontMetrics = this.viewer.getFontMetrics(this.viewer.font);
            String s = this.viewer.getText(tree);
            int w = fontMetrics.stringWidth(s) + this.viewer.nodeWidthPadding * 2;
            return w;
        }

        public double getHeight(Tree tree) {
            FontMetrics fontMetrics = this.viewer.getFontMetrics(this.viewer.font);
            int h = fontMetrics.getHeight() + this.viewer.nodeHeightPadding * 2;
            String s = this.viewer.getText(tree);
            String[] lines = s.split("\n");
            return h * lines.length;
        }
    }

    public static class DefaultTreeTextProvider
    implements TreeTextProvider {
        private final List<String> ruleNames;

        public DefaultTreeTextProvider(@Nullable List<String> ruleNames) {
            this.ruleNames = ruleNames;
        }

        @Override
        public String getText(Tree node) {
            return String.valueOf(Trees.getNodeText(node, this.ruleNames));
        }
    }
}

