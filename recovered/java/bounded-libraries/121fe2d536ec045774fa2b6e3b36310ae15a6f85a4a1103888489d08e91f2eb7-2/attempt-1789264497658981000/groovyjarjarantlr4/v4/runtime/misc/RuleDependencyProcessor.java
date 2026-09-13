/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.misc;

import groovyjarjarantlr4.v4.runtime.Dependents;
import groovyjarjarantlr4.v4.runtime.RuleDependencies;
import groovyjarjarantlr4.v4.runtime.RuleDependency;
import groovyjarjarantlr4.v4.runtime.RuleVersion;
import groovyjarjarantlr4.v4.runtime.atn.ATN;
import groovyjarjarantlr4.v4.runtime.atn.ATNDeserializer;
import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.RuleTransition;
import groovyjarjarantlr4.v4.runtime.atn.Transition;
import groovyjarjarantlr4.v4.runtime.misc.Args;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import groovyjarjarantlr4.v4.runtime.misc.Tuple;
import groovyjarjarantlr4.v4.runtime.misc.Tuple2;
import java.lang.annotation.AnnotationTypeMismatchException;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes(value={"groovyjarjarantlr4.v4.runtime.RuleDependency", "groovyjarjarantlr4.v4.runtime.RuleDependencies", "groovyjarjarantlr4.v4.runtime.RuleVersion"})
public class RuleDependencyProcessor
extends AbstractProcessor {
    public static final String RuleDependencyClassName = "groovyjarjarantlr4.v4.runtime.RuleDependency";
    public static final String RuleDependenciesClassName = "groovyjarjarantlr4.v4.runtime.RuleDependencies";
    public static final String RuleVersionClassName = "groovyjarjarantlr4.v4.runtime.RuleVersion";
    private static final Set<Dependents> IMPLEMENTED_DEPENDENTS = EnumSet.of(Dependents.SELF, Dependents.PARENTS, Dependents.CHILDREN, Dependents.ANCESTORS, Dependents.DESCENDANTS);

    @Override
    public SourceVersion getSupportedSourceVersion() {
        SourceVersion latestSupported = SourceVersion.latestSupported();
        if (latestSupported.ordinal() <= 6) {
            return SourceVersion.RELEASE_6;
        }
        if (latestSupported.ordinal() <= 8) {
            return latestSupported;
        }
        return SourceVersion.values()[8];
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (!this.checkClassNameConstants()) {
            return true;
        }
        List<Tuple2<RuleDependency, Element>> dependencies = RuleDependencyProcessor.getDependencies(roundEnv);
        HashMap<TypeMirror, ArrayList<Tuple2<RuleDependency, Element>>> recognizerDependencies = new HashMap<TypeMirror, ArrayList<Tuple2<RuleDependency, Element>>>();
        for (Tuple2<RuleDependency, Element> tuple2 : dependencies) {
            TypeMirror recognizerType = RuleDependencyProcessor.getRecognizerType(tuple2.getItem1());
            ArrayList<Tuple2<RuleDependency, Element>> list = (ArrayList<Tuple2<RuleDependency, Element>>)recognizerDependencies.get(recognizerType);
            if (list == null) {
                list = new ArrayList<Tuple2<RuleDependency, Element>>();
                recognizerDependencies.put(recognizerType, list);
            }
            list.add(tuple2);
        }
        for (Map.Entry entry : recognizerDependencies.entrySet()) {
            this.processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, String.format("ANTLR 4: Validating %d dependencies on rules in %s.", ((List)entry.getValue()).size(), ((TypeMirror)entry.getKey()).toString()));
            this.checkDependencies((List)entry.getValue(), (TypeMirror)entry.getKey());
        }
        return true;
    }

    private boolean checkClassNameConstants() {
        boolean success = this.checkClassNameConstant(RuleDependencyClassName, RuleDependency.class);
        success &= this.checkClassNameConstant(RuleDependenciesClassName, RuleDependencies.class);
        return success &= this.checkClassNameConstant(RuleVersionClassName, RuleVersion.class);
    }

    private boolean checkClassNameConstant(String className, Class<?> clazz) {
        Args.notNull("className", className);
        Args.notNull("clazz", clazz);
        if (!className.equals(clazz.getCanonicalName())) {
            this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format("Unable to process rule dependencies due to class name mismatch: %s != %s", className, clazz.getCanonicalName()));
            return false;
        }
        return true;
    }

    private static TypeMirror getRecognizerType(RuleDependency dependency) {
        try {
            dependency.recognizer();
            String message = String.format("Expected %s to get the %s.", MirroredTypeException.class.getSimpleName(), TypeMirror.class.getSimpleName());
            throw new UnsupportedOperationException(message);
        }
        catch (MirroredTypeException ex) {
            return ex.getTypeMirror();
        }
    }

    private void checkDependencies(List<Tuple2<RuleDependency, Element>> dependencies, TypeMirror recognizerType) {
        String[] ruleNames = this.getRuleNames(recognizerType);
        int[] ruleVersions = this.getRuleVersions(recognizerType, ruleNames);
        RuleRelations relations = this.extractRuleRelations(recognizerType);
        for (Tuple2<RuleDependency, Element> dependency : dependencies) {
            try {
                int declaredVersion;
                int required;
                if (!this.processingEnv.getTypeUtils().isAssignable(RuleDependencyProcessor.getRecognizerType(dependency.getItem1()), recognizerType)) continue;
                int effectiveRule = dependency.getItem1().rule();
                if (effectiveRule < 0 || effectiveRule >= ruleVersions.length) {
                    Tuple2<AnnotationMirror, AnnotationValue> ruleReferenceElement = this.findRuleDependencyProperty(dependency, RuleDependencyProperty.RULE);
                    String message = String.format("Rule dependency on unknown rule %d@%d in %s", dependency.getItem1().rule(), dependency.getItem1().version(), RuleDependencyProcessor.getRecognizerType(dependency.getItem1()).toString());
                    if (ruleReferenceElement != null) {
                        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, dependency.getItem2(), ruleReferenceElement.getItem1(), ruleReferenceElement.getItem2());
                        continue;
                    }
                    this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, dependency.getItem2());
                    continue;
                }
                EnumSet<Dependents[]> dependents = EnumSet.of(Dependents.SELF, dependency.getItem1().dependents());
                this.reportUnimplementedDependents(dependency, dependents);
                BitSet checked = new BitSet();
                int highestRequiredDependency = this.checkDependencyVersion(dependency, ruleNames, ruleVersions, effectiveRule, null);
                if (dependents.contains((Object)Dependents.PARENTS)) {
                    BitSet parents = relations.parents[dependency.getItem1().rule()];
                    int parent = parents.nextSetBit(0);
                    while (parent >= 0) {
                        if (parent >= 0 && parent < ruleVersions.length && !checked.get(parent)) {
                            checked.set(parent);
                            required = this.checkDependencyVersion(dependency, ruleNames, ruleVersions, parent, "parent");
                            highestRequiredDependency = Math.max(highestRequiredDependency, required);
                        }
                        parent = parents.nextSetBit(parent + 1);
                    }
                }
                if (dependents.contains((Object)Dependents.CHILDREN)) {
                    BitSet children = relations.children[dependency.getItem1().rule()];
                    int child = children.nextSetBit(0);
                    while (child >= 0) {
                        if (child >= 0 && child < ruleVersions.length && !checked.get(child)) {
                            checked.set(child);
                            required = this.checkDependencyVersion(dependency, ruleNames, ruleVersions, child, "child");
                            highestRequiredDependency = Math.max(highestRequiredDependency, required);
                        }
                        child = children.nextSetBit(child + 1);
                    }
                }
                if (dependents.contains((Object)Dependents.ANCESTORS)) {
                    BitSet ancestors = relations.getAncestors(dependency.getItem1().rule());
                    int ancestor = ancestors.nextSetBit(0);
                    while (ancestor >= 0) {
                        if (ancestor >= 0 && ancestor < ruleVersions.length && !checked.get(ancestor)) {
                            checked.set(ancestor);
                            required = this.checkDependencyVersion(dependency, ruleNames, ruleVersions, ancestor, "ancestor");
                            highestRequiredDependency = Math.max(highestRequiredDependency, required);
                        }
                        ancestor = ancestors.nextSetBit(ancestor + 1);
                    }
                }
                if (dependents.contains((Object)Dependents.DESCENDANTS)) {
                    BitSet descendants = relations.getDescendants(dependency.getItem1().rule());
                    int descendant = descendants.nextSetBit(0);
                    while (descendant >= 0) {
                        if (descendant >= 0 && descendant < ruleVersions.length && !checked.get(descendant)) {
                            checked.set(descendant);
                            required = this.checkDependencyVersion(dependency, ruleNames, ruleVersions, descendant, "descendant");
                            highestRequiredDependency = Math.max(highestRequiredDependency, required);
                        }
                        descendant = descendants.nextSetBit(descendant + 1);
                    }
                }
                if ((declaredVersion = dependency.getItem1().version()) <= highestRequiredDependency) continue;
                Tuple2<AnnotationMirror, AnnotationValue> versionElement = this.findRuleDependencyProperty(dependency, RuleDependencyProperty.VERSION);
                String message = String.format("Rule dependency version mismatch: %s has maximum dependency version %d (expected %d) in %s", ruleNames[dependency.getItem1().rule()], highestRequiredDependency, declaredVersion, RuleDependencyProcessor.getRecognizerType(dependency.getItem1()).toString());
                if (versionElement != null) {
                    this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, dependency.getItem2(), versionElement.getItem1(), versionElement.getItem2());
                    continue;
                }
                this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, dependency.getItem2());
            }
            catch (AnnotationTypeMismatchException ex) {
                this.processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, String.format("Could not validate rule dependencies for element %s", dependency.getItem2().toString()), dependency.getItem2());
            }
        }
    }

    private void reportUnimplementedDependents(Tuple2<RuleDependency, Element> dependency, EnumSet<Dependents> dependents) {
        Object unimplemented = dependents.clone();
        ((AbstractSet)unimplemented).removeAll(IMPLEMENTED_DEPENDENTS);
        if (!((AbstractCollection)unimplemented).isEmpty()) {
            Tuple2<AnnotationMirror, AnnotationValue> dependentsElement = this.findRuleDependencyProperty(dependency, RuleDependencyProperty.DEPENDENTS);
            if (dependentsElement == null) {
                dependentsElement = this.findRuleDependencyProperty(dependency, RuleDependencyProperty.RULE);
            }
            String message = String.format("Cannot validate the following dependents of rule %d: %s", dependency.getItem1().rule(), unimplemented);
            if (dependentsElement != null) {
                this.processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, message, dependency.getItem2(), dependentsElement.getItem1(), dependentsElement.getItem2());
            } else {
                this.processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, message, dependency.getItem2());
            }
        }
    }

    private int checkDependencyVersion(Tuple2<RuleDependency, Element> dependency, String[] ruleNames, int[] ruleVersions, int relatedRule, String relation) {
        String path;
        String ruleName = ruleNames[dependency.getItem1().rule()];
        if (relation == null) {
            path = ruleName;
        } else {
            String mismatchedRuleName = ruleNames[relatedRule];
            path = String.format("rule %s (%s of %s)", mismatchedRuleName, relation, ruleName);
        }
        int declaredVersion = dependency.getItem1().version();
        int actualVersion = ruleVersions[relatedRule];
        if (actualVersion > declaredVersion) {
            Tuple2<AnnotationMirror, AnnotationValue> versionElement = this.findRuleDependencyProperty(dependency, RuleDependencyProperty.VERSION);
            String message = String.format("Rule dependency version mismatch: %s has version %d (expected <= %d) in %s", path, actualVersion, declaredVersion, RuleDependencyProcessor.getRecognizerType(dependency.getItem1()).toString());
            if (versionElement != null) {
                this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, dependency.getItem2(), versionElement.getItem1(), versionElement.getItem2());
            } else {
                this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, dependency.getItem2());
            }
        }
        return actualVersion;
    }

    private int[] getRuleVersions(TypeMirror recognizerClass, String[] ruleNames) {
        int[] versions = new int[ruleNames.length];
        List<? extends Element> elements = this.processingEnv.getElementUtils().getAllMembers((TypeElement)this.processingEnv.getTypeUtils().asElement(recognizerClass));
        for (Element element : elements) {
            if (element.getKind() != ElementKind.FIELD) continue;
            VariableElement field = (VariableElement)element;
            boolean isStatic = element.getModifiers().contains((Object)Modifier.STATIC);
            Object constantValue = field.getConstantValue();
            boolean isInteger = constantValue instanceof Integer;
            String name = field.getSimpleName().toString();
            if (!isStatic || !isInteger || !name.startsWith("RULE_")) continue;
            try {
                int version;
                if ((name = name.substring("RULE_".length())).isEmpty() || !Character.isLowerCase(name.charAt(0))) continue;
                int index = (Integer)constantValue;
                if (index < 0 || index >= versions.length) {
                    String message = String.format("Rule index %d for rule '%s' out of bounds for recognizer %s.", index, name, recognizerClass.toString());
                    this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
                    continue;
                }
                if (name.indexOf(36) >= 0) continue;
                ExecutableElement ruleMethod = this.getRuleMethod(recognizerClass, name);
                if (ruleMethod == null) {
                    String message = String.format("Could not find rule method for rule '%s' in recognizer %s.", name, recognizerClass.toString());
                    this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
                    continue;
                }
                RuleVersion ruleVersion = ruleMethod.getAnnotation(RuleVersion.class);
                versions[index] = version = ruleVersion != null ? ruleVersion.value() : 0;
            }
            catch (IllegalArgumentException ex) {
                this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Exception occurred while validating rule dependencies.", element);
            }
        }
        return versions;
    }

    private ExecutableElement getRuleMethod(TypeMirror recognizerClass, String name) {
        List<? extends Element> elements = this.processingEnv.getElementUtils().getAllMembers((TypeElement)this.processingEnv.getTypeUtils().asElement(recognizerClass));
        for (Element element : elements) {
            ExecutableElement method;
            if (element.getKind() != ElementKind.METHOD || !(method = (ExecutableElement)element).getSimpleName().contentEquals(name) || !this.hasRuleVersionAnnotation(method)) continue;
            return method;
        }
        return null;
    }

    private boolean hasRuleVersionAnnotation(ExecutableElement method) {
        TypeElement ruleVersionAnnotationElement = this.processingEnv.getElementUtils().getTypeElement(RuleVersionClassName);
        if (ruleVersionAnnotationElement == null) {
            return false;
        }
        for (AnnotationMirror annotationMirror : method.getAnnotationMirrors()) {
            if (!this.processingEnv.getTypeUtils().isSameType(annotationMirror.getAnnotationType(), ruleVersionAnnotationElement.asType())) continue;
            return true;
        }
        return false;
    }

    private String[] getRuleNames(TypeMirror recognizerClass) {
        ArrayList<String> result = new ArrayList<String>();
        List<? extends Element> elements = this.processingEnv.getElementUtils().getAllMembers((TypeElement)this.processingEnv.getTypeUtils().asElement(recognizerClass));
        for (Element element : elements) {
            if (element.getKind() != ElementKind.FIELD) continue;
            VariableElement field = (VariableElement)element;
            boolean isStatic = element.getModifiers().contains((Object)Modifier.STATIC);
            Object constantValue = field.getConstantValue();
            boolean isInteger = constantValue instanceof Integer;
            String name = field.getSimpleName().toString();
            if (!isStatic || !isInteger || !name.startsWith("RULE_")) continue;
            try {
                int index;
                if ((name = name.substring("RULE_".length())).isEmpty() || !Character.isLowerCase(name.charAt(0)) || (index = ((Integer)constantValue).intValue()) < 0) continue;
                while (result.size() <= index) {
                    result.add("");
                }
                result.set(index, name);
            }
            catch (IllegalArgumentException ex) {
                this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Exception occurred while validating rule dependencies.", element);
            }
        }
        return result.toArray(new String[result.size()]);
    }

    public static List<Tuple2<RuleDependency, Element>> getDependencies(RoundEnvironment roundEnv) {
        ArrayList<Tuple2<RuleDependency, Element>> result = new ArrayList<Tuple2<RuleDependency, Element>>();
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(RuleDependency.class);
        for (Element element : elements) {
            RuleDependency dependency = element.getAnnotation(RuleDependency.class);
            if (dependency == null) continue;
            result.add(Tuple.create(dependency, element));
        }
        elements = roundEnv.getElementsAnnotatedWith(RuleDependencies.class);
        for (Element element : elements) {
            RuleDependencies dependencies = element.getAnnotation(RuleDependencies.class);
            if (dependencies == null || dependencies.value() == null) continue;
            for (RuleDependency dependency : dependencies.value()) {
                result.add(Tuple.create(dependency, element));
            }
        }
        return result;
    }

    @Nullable
    private Tuple2<AnnotationMirror, AnnotationValue> findRuleDependencyProperty(@NotNull Tuple2<RuleDependency, Element> dependency, @NotNull RuleDependencyProperty property) {
        TypeElement ruleDependencyTypeElement = this.processingEnv.getElementUtils().getTypeElement(RuleDependencyClassName);
        TypeElement ruleDependenciesTypeElement = this.processingEnv.getElementUtils().getTypeElement(RuleDependenciesClassName);
        List<? extends AnnotationMirror> mirrors = dependency.getItem2().getAnnotationMirrors();
        block0: for (AnnotationMirror annotationMirror : mirrors) {
            if (this.processingEnv.getTypeUtils().isSameType(ruleDependencyTypeElement.asType(), annotationMirror.getAnnotationType())) {
                AnnotationValue element = this.findRuleDependencyProperty(dependency, annotationMirror, property);
                if (element == null) continue;
                return Tuple.create(annotationMirror, element);
            }
            if (!this.processingEnv.getTypeUtils().isSameType(ruleDependenciesTypeElement.asType(), annotationMirror.getAnnotationType())) continue;
            Map<? extends ExecutableElement, ? extends AnnotationValue> values = annotationMirror.getElementValues();
            block1: for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> value : values.entrySet()) {
                if ("value()".equals(value.getKey().toString())) {
                    AnnotationValue annotationValue = value.getValue();
                    if (!(annotationValue.getValue() instanceof List)) {
                        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "Expected array of RuleDependency annotations for annotation property 'value()'.", dependency.getItem2(), annotationMirror, annotationValue);
                        continue block0;
                    }
                    List annotationValueList = (List)annotationValue.getValue();
                    for (Object obj : annotationValueList) {
                        if (!(obj instanceof AnnotationMirror)) {
                            this.processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "Expected RuleDependency annotation mirror for element of property 'value()'.", dependency.getItem2(), annotationMirror, annotationValue);
                            continue block1;
                        }
                        AnnotationValue element = this.findRuleDependencyProperty(dependency, (AnnotationMirror)obj, property);
                        if (element == null) continue;
                        return Tuple.create((AnnotationMirror)obj, element);
                    }
                    continue;
                }
                this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format("Unexpected annotation property %s.", value.getKey().toString()), dependency.getItem2(), annotationMirror, value.getValue());
            }
        }
        return null;
    }

    @Nullable
    private AnnotationValue findRuleDependencyProperty(@NotNull Tuple2<RuleDependency, Element> dependency, @NotNull AnnotationMirror annotationMirror, @NotNull RuleDependencyProperty property) {
        AnnotationValue recognizerValue = null;
        AnnotationValue ruleValue = null;
        AnnotationValue versionValue = null;
        AnnotationValue dependentsValue = null;
        Map<? extends ExecutableElement, ? extends AnnotationValue> values = annotationMirror.getElementValues();
        for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> value : values.entrySet()) {
            AnnotationValue annotationValue = value.getValue();
            if ("rule()".equals(value.getKey().toString())) {
                ruleValue = annotationValue;
                if (!(annotationValue.getValue() instanceof Integer)) {
                    this.processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "Expected int constant for annotation property 'rule()'.", dependency.getItem2(), annotationMirror, annotationValue);
                    return null;
                }
                if (((Integer)annotationValue.getValue()).intValue() == dependency.getItem1().rule()) continue;
                return null;
            }
            if ("recognizer()".equals(value.getKey().toString())) {
                recognizerValue = annotationValue;
                if (!(annotationValue.getValue() instanceof TypeMirror)) {
                    this.processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "Expected Class constant for annotation property 'recognizer()'.", dependency.getItem2(), annotationMirror, annotationValue);
                    return null;
                }
                TypeMirror annotationRecognizer = (TypeMirror)annotationValue.getValue();
                TypeMirror expectedRecognizer = RuleDependencyProcessor.getRecognizerType(dependency.getItem1());
                if (this.processingEnv.getTypeUtils().isSameType(expectedRecognizer, annotationRecognizer)) continue;
                return null;
            }
            if (!"version()".equals(value.getKey().toString())) continue;
            versionValue = annotationValue;
            if (!(annotationValue.getValue() instanceof Integer)) {
                this.processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "Expected int constant for annotation property 'version()'.", dependency.getItem2(), annotationMirror, annotationValue);
                return null;
            }
            if (((Integer)annotationValue.getValue()).intValue() == dependency.getItem1().version()) continue;
            return null;
        }
        if (recognizerValue != null) {
            if (property == RuleDependencyProperty.RECOGNIZER) {
                return recognizerValue;
            }
            if (ruleValue != null) {
                if (property == RuleDependencyProperty.RULE) {
                    return ruleValue;
                }
                if (versionValue != null) {
                    if (property == RuleDependencyProperty.VERSION) {
                        return versionValue;
                    }
                    if (property == RuleDependencyProperty.DEPENDENTS) {
                        return dependentsValue;
                    }
                }
            }
        }
        if (recognizerValue == null) {
            this.processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "Could not find 'recognizer()' element in annotation.", dependency.getItem2(), annotationMirror);
        }
        if (property == RuleDependencyProperty.RECOGNIZER) {
            return null;
        }
        if (ruleValue == null) {
            this.processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "Could not find 'rule()' element in annotation.", dependency.getItem2(), annotationMirror);
        }
        if (property == RuleDependencyProperty.RULE) {
            return null;
        }
        if (versionValue == null) {
            this.processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "Could not find 'version()' element in annotation.", dependency.getItem2(), annotationMirror);
        }
        return null;
    }

    private RuleRelations extractRuleRelations(TypeMirror recognizer) {
        String serializedATN = this.getSerializedATN(recognizer);
        if (serializedATN == null) {
            return null;
        }
        ATN atn = new ATNDeserializer().deserialize(serializedATN.toCharArray());
        RuleRelations relations = new RuleRelations(atn.ruleToStartState.length);
        for (ATNState state : atn.states) {
            if (!state.epsilonOnlyTransitions) continue;
            for (Transition transition : state.getTransitions()) {
                if (transition.getSerializationType() != 3) continue;
                RuleTransition ruleTransition = (RuleTransition)transition;
                relations.addRuleInvocation(state.ruleIndex, ruleTransition.target.ruleIndex);
            }
        }
        return relations;
    }

    private String getSerializedATN(TypeMirror recognizerClass) {
        List<? extends Element> elements = this.processingEnv.getElementUtils().getAllMembers((TypeElement)this.processingEnv.getTypeUtils().asElement(recognizerClass));
        for (Element element : elements) {
            if (element.getKind() != ElementKind.FIELD) continue;
            VariableElement field = (VariableElement)element;
            boolean isStatic = element.getModifiers().contains((Object)Modifier.STATIC);
            Object constantValue = field.getConstantValue();
            boolean isString = constantValue instanceof String;
            String name = field.getSimpleName().toString();
            if (!isStatic || !isString || !name.equals("_serializedATN")) continue;
            return (String)constantValue;
        }
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Could not retrieve serialized ATN from grammar.");
        return null;
    }

    private static final class RuleRelations {
        private final BitSet[] parents;
        private final BitSet[] children;

        public RuleRelations(int ruleCount) {
            int i;
            this.parents = new BitSet[ruleCount];
            for (i = 0; i < ruleCount; ++i) {
                this.parents[i] = new BitSet();
            }
            this.children = new BitSet[ruleCount];
            for (i = 0; i < ruleCount; ++i) {
                this.children[i] = new BitSet();
            }
        }

        public boolean addRuleInvocation(int caller, int callee) {
            if (caller < 0) {
                return false;
            }
            if (this.children[caller].get(callee)) {
                return false;
            }
            this.children[caller].set(callee);
            this.parents[callee].set(caller);
            return true;
        }

        public BitSet getAncestors(int rule) {
            int cardinality;
            BitSet ancestors = new BitSet();
            ancestors.or(this.parents[rule]);
            do {
                cardinality = ancestors.cardinality();
                int i = ancestors.nextSetBit(0);
                while (i >= 0) {
                    ancestors.or(this.parents[i]);
                    i = ancestors.nextSetBit(i + 1);
                }
            } while (ancestors.cardinality() != cardinality);
            return ancestors;
        }

        public BitSet getDescendants(int rule) {
            int cardinality;
            BitSet descendants = new BitSet();
            descendants.or(this.children[rule]);
            do {
                cardinality = descendants.cardinality();
                int i = descendants.nextSetBit(0);
                while (i >= 0) {
                    descendants.or(this.children[i]);
                    i = descendants.nextSetBit(i + 1);
                }
            } while (descendants.cardinality() != cardinality);
            return descendants;
        }
    }

    public static enum RuleDependencyProperty {
        RECOGNIZER,
        RULE,
        VERSION,
        DEPENDENTS;

    }
}

