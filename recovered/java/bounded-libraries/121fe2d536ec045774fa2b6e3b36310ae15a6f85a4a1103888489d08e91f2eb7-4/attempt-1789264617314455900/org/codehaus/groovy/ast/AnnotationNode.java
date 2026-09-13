/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.expr.AnnotationConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ListExpression;

public class AnnotationNode
extends ASTNode {
    public static final int CONSTRUCTOR_TARGET = 2;
    public static final int METHOD_TARGET = 4;
    public static final int FIELD_TARGET = 8;
    public static final int PARAMETER_TARGET = 16;
    public static final int LOCAL_VARIABLE_TARGET = 32;
    public static final int ANNOTATION_TARGET = 64;
    public static final int PACKAGE_TARGET = 128;
    public static final int TYPE_PARAMETER_TARGET = 256;
    public static final int TYPE_USE_TARGET = 512;
    public static final int RECORD_COMPONENT_TARGET = 1024;
    public static final int TYPE_TARGET = 65;
    private static final int ALL_TARGETS = 2047;
    private final ClassNode classNode;
    private Map<String, Expression> members;
    private boolean runtimeRetention = false;
    private boolean sourceRetention = false;
    private boolean classRetention = false;
    private int allowedTargets = 2047;

    public AnnotationNode(ClassNode classNode) {
        this.classNode = Objects.requireNonNull(classNode);
    }

    public ClassNode getClassNode() {
        return this.classNode;
    }

    public Map<String, Expression> getMembers() {
        if (this.members == null) {
            return Collections.emptyMap();
        }
        return this.members;
    }

    public Expression getMember(String name) {
        if (this.members == null) {
            return null;
        }
        return this.members.get(name);
    }

    private void assertMembers() {
        if (this.members == null) {
            this.members = new LinkedHashMap<String, Expression>();
        }
    }

    public void addMember(String name, Expression value) {
        this.assertMembers();
        Expression oldValue = this.members.get(name);
        if (oldValue != null) {
            throw new GroovyBugError(String.format("Annotation member %s has already been added", name));
        }
        this.members.put(name, value);
    }

    public void setMember(String name, Expression value) {
        this.assertMembers();
        this.members.put(name, value);
    }

    public boolean isBuiltIn() {
        return false;
    }

    public boolean hasRuntimeRetention() {
        return this.runtimeRetention;
    }

    public void setRuntimeRetention(boolean flag) {
        this.runtimeRetention = flag;
    }

    public boolean hasSourceRetention() {
        return this.sourceRetention;
    }

    public void setSourceRetention(boolean flag) {
        this.sourceRetention = flag;
    }

    public boolean hasClassRetention() {
        if (!this.runtimeRetention && !this.sourceRetention) {
            return true;
        }
        return this.classRetention;
    }

    public void setClassRetention(boolean flag) {
        this.classRetention = flag;
    }

    public void setAllowedTargets(int bitmap) {
        this.allowedTargets = bitmap;
    }

    public boolean isTargetAllowed(int target) {
        return (this.allowedTargets & target) == target;
    }

    public static String targetToName(int target) {
        switch (target) {
            case 65: {
                return "TYPE";
            }
            case 2: {
                return "CONSTRUCTOR";
            }
            case 4: {
                return "METHOD";
            }
            case 8: {
                return "FIELD";
            }
            case 16: {
                return "PARAMETER";
            }
            case 32: {
                return "LOCAL_VARIABLE";
            }
            case 64: {
                return "ANNOTATION";
            }
            case 128: {
                return "PACKAGE";
            }
            case 256: {
                return "TYPE_PARAMETER";
            }
            case 512: {
                return "TYPE_USE";
            }
            case 1024: {
                return "RECORD_COMPONENT";
            }
        }
        return "unknown target";
    }

    public String toString() {
        return super.toString() + "[" + this.getText() + "]";
    }

    @Override
    public String getText() {
        StringBuilder memberText = new StringBuilder();
        if (this.members != null) {
            boolean first = true;
            for (Map.Entry<String, Expression> next : this.members.entrySet()) {
                String text;
                if (first) {
                    first = false;
                } else {
                    memberText.append(", ");
                }
                Expression value = next.getValue();
                if (value instanceof ListExpression) {
                    List result = ((ListExpression)value).getExpressions().stream().map(exp -> exp instanceof AnnotationConstantExpression ? ((ASTNode)((AnnotationConstantExpression)exp).getValue()).getText() : exp.getText()).collect(Collectors.toList());
                    text = result.toString();
                } else {
                    text = value.getText();
                }
                memberText.append(next.getKey()).append(": ").append(text);
            }
        }
        return "@" + this.classNode.getText() + "(" + memberText + ")";
    }
}

