/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.parser.antlr4;

import groovy.lang.Groovydoc;
import groovy.lang.groovydoc.GroovydocHolder;
import groovyjarjarantlr4.v4.runtime.ParserRuleContext;
import groovyjarjarantlr4.v4.runtime.tree.ParseTree;
import groovyjarjarantlr4.v4.runtime.tree.TerminalNode;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.groovy.parser.antlr4.GroovyParser;
import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;

public class GroovydocManager {
    public static final String DOC_COMMENT = "_DOC_COMMENT";
    private static final String GROOVYDOC_PREFIX = "/**";
    private static final String RUNTIME_GROOVYDOC_PREFIX = "/**@";
    private static final String VALUE = "value";
    private static final Pattern SPACES_PATTERN = Pattern.compile("\\s+");
    private final boolean groovydocEnabled;
    private final boolean runtimeGroovydocEnabled;

    public GroovydocManager(boolean groovydocEnabled, boolean runtimeGroovydocEnabled) {
        this.groovydocEnabled = groovydocEnabled;
        this.runtimeGroovydocEnabled = runtimeGroovydocEnabled;
    }

    public void handle(ASTNode node, GroovyParser.GroovyParserRuleContext ctx) {
        if (!this.groovydocEnabled && !this.runtimeGroovydocEnabled) {
            return;
        }
        if (!DefaultGroovyMethods.asBoolean(node) || !DefaultGroovyMethods.asBoolean(ctx)) {
            return;
        }
        String docCommentNodeText = this.findDocCommentByNode(ctx);
        if (null == docCommentNodeText) {
            return;
        }
        this.attachDocCommentAsMetaData(node, docCommentNodeText);
        this.attachGroovydocAnnotation(node, docCommentNodeText);
    }

    private void attachDocCommentAsMetaData(ASTNode node, String docCommentNodeText) {
        if (!this.groovydocEnabled) {
            return;
        }
        if (!(node instanceof GroovydocHolder)) {
            return;
        }
        node.putNodeMetaData(DOC_COMMENT, new groovy.lang.groovydoc.Groovydoc(docCommentNodeText, (GroovydocHolder)((Object)node)));
    }

    private void attachGroovydocAnnotation(ASTNode node, String docCommentNodeText) {
        if (!this.runtimeGroovydocEnabled) {
            return;
        }
        if (!(node instanceof AnnotatedNode)) {
            return;
        }
        if (!docCommentNodeText.startsWith(RUNTIME_GROOVYDOC_PREFIX)) {
            return;
        }
        AnnotatedNode annotatedNode = (AnnotatedNode)node;
        AnnotationNode annotationNode = new AnnotationNode(ClassHelper.make(Groovydoc.class));
        annotationNode.addMember(VALUE, new ConstantExpression(docCommentNodeText));
        annotatedNode.addAnnotation(annotationNode);
    }

    private String findDocCommentByNode(ParserRuleContext node) {
        if (!DefaultGroovyMethods.asBoolean(node)) {
            return null;
        }
        if (node instanceof GroovyParser.ClassBodyContext) {
            return null;
        }
        ParserRuleContext parentContext = node.getParent();
        if (!DefaultGroovyMethods.asBoolean(parentContext)) {
            return null;
        }
        String docCommentNodeText = null;
        boolean sameTypeNodeBefore = false;
        block0: for (ParseTree child : parentContext.children) {
            List<? extends TerminalNode> nlList;
            int nlListSize;
            if (node == child) {
                if (!DefaultGroovyMethods.asBoolean(docCommentNodeText) && !sameTypeNodeBefore) {
                    return this.findDocCommentByNode(parentContext);
                }
                return docCommentNodeText;
            }
            if (node.getClass() == child.getClass()) {
                docCommentNodeText = null;
                sameTypeNodeBefore = true;
                continue;
            }
            if (!(child instanceof GroovyParser.NlsContext) && !(child instanceof GroovyParser.SepContext) || 0 == (nlListSize = (nlList = child instanceof GroovyParser.NlsContext ? ((GroovyParser.NlsContext)child).NL() : ((GroovyParser.SepContext)child).NL()).size())) continue;
            for (int i = nlListSize - 1; i >= 0; --i) {
                String text = nlList.get(i).getText();
                if (StringUtils.matches(text, SPACES_PATTERN)) continue;
                if (text.startsWith(GROOVYDOC_PREFIX)) {
                    docCommentNodeText = text;
                    continue block0;
                }
                docCommentNodeText = null;
                continue block0;
            }
        }
        throw new GroovyBugError("node can not be found: " + node.getText());
    }
}

