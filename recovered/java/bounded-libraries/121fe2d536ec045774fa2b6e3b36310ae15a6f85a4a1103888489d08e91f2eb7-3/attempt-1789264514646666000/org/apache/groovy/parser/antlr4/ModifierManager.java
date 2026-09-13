/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.parser.antlr4;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.groovy.parser.antlr4.AstBuilder;
import org.apache.groovy.util.Maps;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.ModifierNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.VariableExpression;

class ModifierManager {
    private static final Map<Class, List<Integer>> INVALID_MODIFIERS_MAP = Maps.of(ConstructorNode.class, Arrays.asList(48, 28, 14, 37), MethodNode.class, Arrays.asList(59));
    private AstBuilder astBuilder;
    private List<ModifierNode> modifierNodeList;

    public ModifierManager(AstBuilder astBuilder, List<ModifierNode> modifierNodeList) {
        this.astBuilder = astBuilder;
        this.validate(modifierNodeList);
        this.modifierNodeList = Collections.unmodifiableList(modifierNodeList);
    }

    public int getModifierCount() {
        return this.modifierNodeList.size();
    }

    private void validate(List<ModifierNode> modifierNodeList) {
        LinkedHashMap<ModifierNode, Integer> modifierNodeCounter = new LinkedHashMap<ModifierNode, Integer>(modifierNodeList.size());
        int visibilityModifierCnt = 0;
        for (ModifierNode modifierNode : modifierNodeList) {
            Integer cnt = (Integer)modifierNodeCounter.get(modifierNode);
            if (null == cnt) {
                modifierNodeCounter.put(modifierNode, 1);
            } else if (1 == cnt && !modifierNode.isRepeatable()) {
                throw this.astBuilder.createParsingFailedException("Cannot repeat modifier[" + modifierNode.getText() + "]", modifierNode);
            }
            if (!modifierNode.isVisibilityModifier() || ++visibilityModifierCnt <= 1) continue;
            throw this.astBuilder.createParsingFailedException("Cannot specify modifier[" + modifierNode.getText() + "] when access scope has already been defined", modifierNode);
        }
    }

    public void validate(MethodNode methodNode) {
        this.validate(INVALID_MODIFIERS_MAP.get(MethodNode.class), methodNode);
    }

    public void validate(ConstructorNode constructorNode) {
        this.validate(INVALID_MODIFIERS_MAP.get(ConstructorNode.class), constructorNode);
    }

    private void validate(List<Integer> invalidModifierList, MethodNode methodNode) {
        this.modifierNodeList.forEach(e -> {
            if (invalidModifierList.contains(e.getType())) {
                throw this.astBuilder.createParsingFailedException(methodNode.getClass().getSimpleName().replace("Node", "") + " has an incorrect modifier '" + e + "'.", methodNode);
            }
        });
    }

    private int calcModifiersOpValue(int t) {
        int result = 0;
        for (ModifierNode modifierNode : this.modifierNodeList) {
            result |= modifierNode.getOpcode().intValue();
        }
        if (!this.containsVisibilityModifier()) {
            if (1 == t) {
                result |= 0x1001;
            } else if (2 == t) {
                result |= 1;
            }
        }
        return result;
    }

    public int getClassModifiersOpValue() {
        return this.calcModifiersOpValue(1);
    }

    public int getClassMemberModifiersOpValue() {
        return this.calcModifiersOpValue(2);
    }

    public List<AnnotationNode> getAnnotations() {
        return this.modifierNodeList.stream().filter(ModifierNode::isAnnotation).map(ModifierNode::getAnnotationNode).collect(Collectors.toList());
    }

    public boolean containsAny(int ... modifierTypes) {
        return this.modifierNodeList.stream().anyMatch(e -> {
            for (int modifierType : modifierTypes) {
                if (modifierType != e.getType()) continue;
                return true;
            }
            return false;
        });
    }

    public Optional<ModifierNode> get(int modifierType) {
        return this.modifierNodeList.stream().filter(e -> modifierType == e.getType()).findFirst();
    }

    public boolean containsAnnotations() {
        return this.modifierNodeList.stream().anyMatch(ModifierNode::isAnnotation);
    }

    public boolean containsVisibilityModifier() {
        return this.modifierNodeList.stream().anyMatch(ModifierNode::isVisibilityModifier);
    }

    public boolean containsNonVisibilityModifier() {
        return this.modifierNodeList.stream().anyMatch(ModifierNode::isNonVisibilityModifier);
    }

    public Parameter processParameter(Parameter parameter) {
        this.modifierNodeList.forEach(e -> {
            parameter.setModifiers(parameter.getModifiers() | e.getOpcode());
            if (e.isAnnotation()) {
                parameter.addAnnotation(e.getAnnotationNode());
            }
        });
        return parameter;
    }

    public int clearVisibilityModifiers(int modifiers) {
        return modifiers & 0xFFFFFFFE & 0xFFFFFFFB & 0xFFFFFFFD;
    }

    public MethodNode processMethodNode(MethodNode mn) {
        this.modifierNodeList.forEach(e -> {
            mn.setModifiers((e.isVisibilityModifier() ? this.clearVisibilityModifiers(mn.getModifiers()) : mn.getModifiers()) | e.getOpcode());
            if (e.isAnnotation()) {
                mn.addAnnotation(e.getAnnotationNode());
            }
        });
        return mn;
    }

    public VariableExpression processVariableExpression(VariableExpression ve) {
        this.modifierNodeList.forEach(e -> ve.setModifiers(ve.getModifiers() | e.getOpcode()));
        return ve;
    }

    public <T extends AnnotatedNode> T attachAnnotations(T node) {
        this.getAnnotations().forEach(node::addAnnotation);
        return node;
    }
}

