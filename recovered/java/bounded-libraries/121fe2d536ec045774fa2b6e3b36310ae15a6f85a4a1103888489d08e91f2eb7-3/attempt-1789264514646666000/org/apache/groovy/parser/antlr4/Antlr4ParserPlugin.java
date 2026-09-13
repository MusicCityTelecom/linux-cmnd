/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.parser.antlr4;

import java.io.IOException;
import java.io.Reader;
import org.apache.groovy.parser.antlr4.AstBuilder;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.ModuleNode;
import org.codehaus.groovy.control.ParserPlugin;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.control.io.StringReaderSource;
import org.codehaus.groovy.runtime.IOGroovyMethods;
import org.codehaus.groovy.syntax.Reduction;

public class Antlr4ParserPlugin
implements ParserPlugin {
    @Override
    public Reduction parseCST(SourceUnit sourceUnit, Reader reader) {
        if (!sourceUnit.getSource().canReopenSource()) {
            try {
                sourceUnit.setSource(new StringReaderSource(IOGroovyMethods.getText(reader), sourceUnit.getConfiguration()));
            }
            catch (IOException e) {
                throw new GroovyBugError("Failed to create StringReaderSource", e);
            }
        }
        return null;
    }

    @Override
    public ModuleNode buildAST(SourceUnit sourceUnit, ClassLoader classLoader, Reduction cst) {
        AstBuilder builder = new AstBuilder(sourceUnit, sourceUnit.getConfiguration().isGroovydocEnabled(), sourceUnit.getConfiguration().isRuntimeGroovydocEnabled());
        return builder.buildAST();
    }
}

