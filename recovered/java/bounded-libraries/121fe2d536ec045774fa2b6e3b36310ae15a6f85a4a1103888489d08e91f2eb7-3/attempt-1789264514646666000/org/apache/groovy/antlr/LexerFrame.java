/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.antlr;

import groovyjarjarantlr4.v4.runtime.Token;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import org.apache.groovy.parser.antlr4.GroovyLangLexer;
import org.apache.groovy.parser.antlr4.GroovyLexer;
import org.codehaus.groovy.runtime.IOGroovyMethods;
import org.codehaus.groovy.runtime.ResourceGroovyMethods;

public class LexerFrame
extends JFrame
implements ActionListener {
    private static final long serialVersionUID = 2715693043143492893L;
    private static final Class<GroovyLexer> TOKEN_TYPES_CLASS = GroovyLexer.class;
    private static final Font MONOSPACED_FONT = new Font("Monospaced", 0, 12);
    private final JSplitPane jSplitPane1 = new JSplitPane();
    private final JScrollPane jScrollPane1 = new JScrollPane();
    private final JScrollPane jScrollPane2 = new JScrollPane();
    private final JTextPane tokenPane = new HScrollableTextPane();
    private final JButton jbutton = new JButton("open");
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final JTextArea scriptPane = new JTextArea();
    private final JLabel tokenStreamLabel = new JLabel(" Token Stream:");
    private final Map<Integer, String> tokens = new HashMap<Integer, String>();
    private final Action loadFileAction = new AbstractAction("Open File..."){
        private static final long serialVersionUID = 4541927184172762704L;

        @Override
        public void actionPerformed(ActionEvent ae) {
            JFileChooser jfc = new JFileChooser();
            int response = jfc.showOpenDialog(LexerFrame.this);
            if (response != 0) {
                return;
            }
            LexerFrame.this.safeScanScript(jfc.getSelectedFile());
        }
    };

    public LexerFrame() {
        this((Reader)null);
    }

    public LexerFrame(Reader reader) {
        super("Token Stream Viewer");
        try {
            this.jbInit(reader);
            this.setSize(500, 500);
            this.listTokens(TOKEN_TYPES_CLASS);
            if (reader == null) {
                final JPopupMenu popup = new JPopupMenu();
                popup.add(this.loadFileAction);
                this.jbutton.setSize(30, 30);
                this.jbutton.addMouseListener(new MouseAdapter(){

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        popup.show(LexerFrame.this.scriptPane, e.getX(), e.getY());
                    }
                });
            } else {
                this.safeScanScript(reader);
            }
            this.setDefaultCloseOperation(2);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listTokens(Class tokenTypes) throws Exception {
        for (Field field : tokenTypes.getDeclaredFields()) {
            String fieldName;
            Object fieldValue;
            int modifiers = field.getModifiers();
            if (!Modifier.isPublic(modifiers) || !Modifier.isStatic(modifiers) || !Modifier.isFinal(modifiers) || !((fieldValue = field.get(null)) instanceof Integer) || (fieldName = field.getName()).endsWith("_MODE")) continue;
            this.tokens.put((Integer)fieldValue, fieldName);
        }
        this.tokens.put(-1, "EOF");
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Token token = (Token)((JComponent)ae.getSource()).getClientProperty("token");
        if (this.eof().equals(token.getType())) {
            this.scriptPane.select(0, 0);
            return;
        }
        try {
            int start = this.scriptPane.getLineStartOffset(token.getLine() - 1) + this.getColumn(token);
            String text = token.getText();
            this.scriptPane.select(start, "".equals(text.trim()) ? start : start + text.length());
            this.scriptPane.requestFocus();
        }
        catch (BadLocationException badLocationException) {
            // empty catch block
        }
    }

    private void safeScanScript(File file) {
        try {
            this.scanScript(new StringReader(ResourceGroovyMethods.getText(file)));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void safeScanScript(Reader reader) {
        try {
            this.scanScript(reader instanceof StringReader ? (StringReader)reader : new StringReader(IOGroovyMethods.getText(reader)));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void scanScript(StringReader reader) throws Exception {
        Token token;
        this.scriptPane.read(reader, null);
        reader.reset();
        GroovyLangLexer lexer = new GroovyLangLexer(reader);
        this.tokenPane.setEditable(true);
        this.tokenPane.setText("");
        int line = 1;
        ButtonGroup bg = new ButtonGroup();
        int tokenCnt = 0;
        do {
            token = lexer.nextToken();
            JToggleButton tokenButton = new JToggleButton(this.tokens.get(token.getType()));
            tokenButton.setFont(MONOSPACED_FONT);
            bg.add(tokenButton);
            tokenButton.addActionListener(this);
            tokenButton.setToolTipText(token.getText());
            tokenButton.putClientProperty("token", token);
            tokenButton.setMargin(new Insets(0, 1, 0, 1));
            tokenButton.setFocusPainted(false);
            if (token.getLine() > line) {
                this.tokenPane.getDocument().insertString(this.tokenPane.getDocument().getLength(), "\n", null);
                line = token.getLine();
            }
            this.insertComponent(tokenButton);
            ++tokenCnt;
        } while (!this.eof().equals(token.getType()));
        this.tokenStreamLabel.setText(" Token Stream(" + tokenCnt + "):");
        this.tokenPane.setEditable(false);
        this.tokenPane.setCaretPosition(0);
        reader.close();
    }

    private Integer eof() {
        return -1;
    }

    private Integer getColumn(Token token) {
        return token.getCharPositionInLine();
    }

    private void insertComponent(JComponent comp) {
        try {
            this.tokenPane.getDocument().insertString(this.tokenPane.getDocument().getLength(), " ", null);
        }
        catch (BadLocationException badLocationException) {
            // empty catch block
        }
        try {
            this.tokenPane.setCaretPosition(this.tokenPane.getDocument().getLength() - 1);
        }
        catch (Exception ex) {
            this.tokenPane.setCaretPosition(0);
        }
        this.tokenPane.insertComponent(comp);
    }

    private void jbInit(Reader reader) throws Exception {
        Border border = BorderFactory.createEmptyBorder();
        this.jSplitPane1.setOrientation(0);
        this.tokenPane.setEditable(false);
        this.tokenPane.setText("");
        this.scriptPane.setFont(MONOSPACED_FONT);
        this.scriptPane.setEditable(false);
        this.scriptPane.setMargin(new Insets(5, 5, 5, 5));
        this.scriptPane.setText("");
        this.jScrollPane1.setBorder(border);
        this.jScrollPane2.setBorder(border);
        this.jSplitPane1.setMinimumSize(new Dimension(800, 600));
        this.mainPanel.add((Component)this.jSplitPane1, "Center");
        if (reader == null) {
            this.mainPanel.add((Component)this.jbutton, "North");
        }
        this.getContentPane().add(this.mainPanel);
        this.jSplitPane1.add((Component)this.jScrollPane1, "left");
        this.jScrollPane1.getViewport().add((Component)this.tokenPane, null);
        this.jSplitPane1.add((Component)this.jScrollPane2, "right");
        this.jScrollPane2.getViewport().add((Component)this.scriptPane, null);
        this.jScrollPane1.setColumnHeaderView(this.tokenStreamLabel);
        this.jScrollPane2.setColumnHeaderView(new JLabel(" Input Script:"));
        this.jSplitPane1.setResizeWeight(0.5);
    }

    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception exception) {
            // empty catch block
        }
        LexerFrame lexerFrame = null;
        if (args.length == 0) {
            lexerFrame = new LexerFrame();
        } else if (args.length > 1) {
            System.err.println("usage: java LexerFrame [filename.ext]");
            System.exit(1);
        } else {
            String filename = args[0];
            FileReader fileReader = new FileReader(filename);
            lexerFrame = new LexerFrame(fileReader);
        }
        lexerFrame.setVisible(true);
    }

    private static class HScrollableTextPane
    extends JTextPane {
        private static final long serialVersionUID = -8582328309470654441L;

        private HScrollableTextPane() {
        }

        @Override
        public boolean getScrollableTracksViewportWidth() {
            return this.getSize().width < this.getParent().getSize().width;
        }

        @Override
        public void setSize(Dimension d) {
            if (d.width < this.getParent().getSize().width) {
                d.width = this.getParent().getSize().width;
            }
            super.setSize(d);
        }
    }
}

