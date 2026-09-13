/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.gui;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class JFileChooserConfirmOverwrite
extends JFileChooser {
    public JFileChooserConfirmOverwrite() {
        this.setMultiSelectionEnabled(false);
    }

    @Override
    public void approveSelection() {
        int answer;
        File selectedFile = this.getSelectedFile();
        if (selectedFile.exists() && (answer = JOptionPane.showConfirmDialog(this, "Overwrite existing file?", "Overwrite?", 0)) != 0) {
            return;
        }
        super.approveSelection();
    }
}

