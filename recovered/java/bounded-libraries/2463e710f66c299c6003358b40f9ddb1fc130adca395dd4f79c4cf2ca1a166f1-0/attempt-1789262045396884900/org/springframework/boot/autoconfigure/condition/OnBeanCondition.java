/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.aop.scope.ScopedProxyUtils
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.HierarchicalBeanFactory
 *  org.springframework.beans.factory.ListableBeanFactory
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.ConditionContext
 *  org.springframework.context.annotation.ConfigurationCondition
 *  org.springframework.context.annotation.ConfigurationCondition$ConfigurationPhase
 *  org.springframework.core.ResolvableType
 *  org.springframework.core.annotation.MergedAnnotation
 *  org.springframework.core.annotation.MergedAnnotation$Adapt
 *  org.springframework.core.annotation.MergedAnnotationCollectors
 *  org.springframework.core.annotation.MergedAnnotationPredicates
 *  org.springframework.core.annotation.MergedAnnotations
 *  org.springframework.core.annotation.MergedAnnotations$SearchStrategy
 *  org.springframework.core.annotation.Order
 *  org.springframework.core.type.AnnotatedTypeMetadata
 *  org.springframework.core.type.MethodMetadata
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.MultiValueMap
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.ReflectionUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.condition;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigurationMetadata;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.condition.FilteringSpringBootCondition;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotationCollectors;
import org.springframework.core.annotation.MergedAnnotationPredicates;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

@Order(value=0x7FFFFFFF)
class OnBeanCondition
extends FilteringSpringBootCondition
implements ConfigurationCondition {
    OnBeanCondition() {
    }

    public ConfigurationCondition.ConfigurationPhase getConfigurationPhase() {
        return ConfigurationCondition.ConfigurationPhase.REGISTER_BEAN;
    }

    @Override
    protected final ConditionOutcome[] getOutcomes(String[] autoConfigurationClasses, AutoConfigurationMetadata autoConfigurationMetadata) {
        ConditionOutcome[] outcomes = new ConditionOutcome[autoConfigurationClasses.length];
        for (int i = 0; i < outcomes.length; ++i) {
            String autoConfigurationClass = autoConfigurationClasses[i];
            if (autoConfigurationClass == null) continue;
            Set<String> onBeanTypes = autoConfigurationMetadata.getSet(autoConfigurationClass, "ConditionalOnBean");
            outcomes[i] = this.getOutcome(onBeanTypes, ConditionalOnBean.class);
            if (outcomes[i] != null) continue;
            Set<String> onSingleCandidateTypes = autoConfigurationMetadata.getSet(autoConfigurationClass, "ConditionalOnSingleCandidate");
            outcomes[i] = this.getOutcome(onSingleCandidateTypes, ConditionalOnSingleCandidate.class);
        }
        return outcomes;
    }

    private ConditionOutcome getOutcome(Set<String> requiredBeanTypes, Class<? extends Annotation> annotation) {
        List<String> missing = this.filter(requiredBeanTypes, FilteringSpringBootCondition.ClassNameFilter.MISSING, this.getBeanClassLoader());
        if (!missing.isEmpty()) {
            ConditionMessage message = ConditionMessage.forCondition(annotation, new Object[0]).didNotFind("required type", "required types").items(ConditionMessage.Style.QUOTE, missing);
            return ConditionOutcome.noMatch(message);
        }
        return null;
    }

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        MatchResult matchResult;
        Spec spec;
        ConditionMessage matchMessage = ConditionMessage.empty();
        MergedAnnotations annotations = metadata.getAnnotations();
        if (annotations.isPresent(ConditionalOnBean.class)) {
            spec = new Spec(context, metadata, annotations, ConditionalOnBean.class);
            matchResult = this.getMatchingBeans(context, spec);
            if (!matchResult.isAllMatched()) {
                String reason = this.createOnBeanNoMatchReason(matchResult);
                return ConditionOutcome.noMatch(spec.message().because(reason));
            }
            matchMessage = spec.message(matchMessage).found("bean", "beans").items(ConditionMessage.Style.QUOTE, matchResult.getNamesOfAllMatches());
        }
        if (metadata.isAnnotated(ConditionalOnSingleCandidate.class.getName())) {
            spec = new SingleCandidateSpec(context, metadata, annotations);
            matchResult = this.getMatchingBeans(context, spec);
            if (!matchResult.isAllMatched()) {
                return ConditionOutcome.noMatch(spec.message().didNotFind("any beans").atAll());
            }
            Set<String> allBeans = matchResult.getNamesOfAllMatches();
            if (allBeans.size() == 1) {
                matchMessage = spec.message(matchMessage).found("a single bean").items(ConditionMessage.Style.QUOTE, allBeans);
            } else {
                List<String> primaryBeans = this.getPrimaryBeans(context.getBeanFactory(), allBeans, spec.getStrategy() == SearchStrategy.ALL);
                if (primaryBeans.isEmpty()) {
                    return ConditionOutcome.noMatch(spec.message().didNotFind("a primary bean from beans").items(ConditionMessage.Style.QUOTE, allBeans));
                }
                if (primaryBeans.size() > 1) {
                    return ConditionOutcome.noMatch(spec.message().found("multiple primary beans").items(ConditionMessage.Style.QUOTE, primaryBeans));
                }
                matchMessage = spec.message(matchMessage).found("a single primary bean '" + primaryBeans.get(0) + "' from beans").items(ConditionMessage.Style.QUOTE, allBeans);
            }
        }
        if (metadata.isAnnotated(ConditionalOnMissingBean.class.getName())) {
            spec = new Spec<ConditionalOnMissingBean>(context, metadata, annotations, ConditionalOnMissingBean.class);
            matchResult = this.getMatchingBeans(context, spec);
            if (matchResult.isAnyMatched()) {
                String reason = this.createOnMissingBeanNoMatchReason(matchResult);
                return ConditionOutcome.noMatch(spec.message().because(reason));
            }
            matchMessage = spec.message(matchMessage).didNotFind("any beans").atAll();
        }
        return ConditionOutcome.match(matchMessage);
    }

    protected final MatchResult getMatchingBeans(ConditionContext context, Spec<?> spec) {
        ClassLoader classLoader = context.getClassLoader();
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        boolean considerHierarchy = ((Spec)spec).getStrategy() != SearchStrategy.CURRENT;
        Set<Class<?>> parameterizedContainers = spec.getParameterizedContainers();
        if (((Spec)spec).getStrategy() == SearchStrategy.ANCESTORS) {
            BeanFactory parent = beanFactory.getParentBeanFactory();
            Assert.isInstanceOf(ConfigurableListableBeanFactory.class, (Object)parent, (String)"Unable to use SearchStrategy.ANCESTORS");
            beanFactory = (ConfigurableListableBeanFactory)parent;
        }
        MatchResult result = new MatchResult();
        Set<String> beansIgnoredByType = this.getNamesOfBeansIgnoredByType(classLoader, (ListableBeanFactory)beanFactory, considerHierarchy, spec.getIgnoredTypes(), parameterizedContainers);
        for (String type : spec.getTypes()) {
            Set<String> typeMatches = this.getBeanNamesForType(classLoader, considerHierarchy, (ListableBeanFactory)beanFactory, type, parameterizedContainers);
            Iterator iterator = typeMatches.iterator();
            while (iterator.hasNext()) {
                String match = (String)iterator.next();
                if (!beansIgnoredByType.contains(match) && !ScopedProxyUtils.isScopedTarget((String)match)) continue;
                iterator.remove();
            }
            if (typeMatches.isEmpty()) {
                result.recordUnmatchedType(type);
                continue;
            }
            result.recordMatchedType(type, typeMatches);
        }
        for (String annotation : spec.getAnnotations()) {
            Set<String> annotationMatches = this.getBeanNamesForAnnotation(classLoader, beanFactory, annotation, considerHierarchy);
            annotationMatches.removeAll(beansIgnoredByType);
            if (annotationMatches.isEmpty()) {
                result.recordUnmatchedAnnotation(annotation);
                continue;
            }
            result.recordMatchedAnnotation(annotation, annotationMatches);
        }
        for (String beanName : spec.getNames()) {
            if (!beansIgnoredByType.contains(beanName) && this.containsBean(beanFactory, beanName, considerHierarchy)) {
                result.recordMatchedName(beanName);
                continue;
            }
            result.recordUnmatchedName(beanName);
        }
        return result;
    }

    private Set<String> getNamesOfBeansIgnoredByType(ClassLoader classLoader, ListableBeanFactory beanFactory, boolean considerHierarchy, Set<String> ignoredTypes, Set<Class<?>> parameterizedContainers) {
        Set<String> result = null;
        for (String ignoredType : ignoredTypes) {
            Set<String> ignoredNames = this.getBeanNamesForType(classLoader, considerHierarchy, beanFactory, ignoredType, parameterizedContainers);
            result = OnBeanCondition.addAll(result, ignoredNames);
        }
        return result != null ? result : Collections.emptySet();
    }

    private Set<String> getBeanNamesForType(ClassLoader classLoader, boolean considerHierarchy, ListableBeanFactory beanFactory, String type, Set<Class<?>> parameterizedContainers) throws LinkageError {
        try {
            return this.getBeanNamesForType(beanFactory, considerHierarchy, OnBeanCondition.resolve(type, classLoader), parameterizedContainers);
        }
        catch (ClassNotFoundException | NoClassDefFoundError ex) {
            return Collections.emptySet();
        }
    }

    private Set<String> getBeanNamesForType(ListableBeanFactory beanFactory, boolean considerHierarchy, Class<?> type, Set<Class<?>> parameterizedContainers) {
        Set<String> result = this.collectBeanNamesForType(beanFactory, considerHierarchy, type, parameterizedContainers, null);
        return result != null ? result : Collections.emptySet();
    }

    private Set<String> collectBeanNamesForType(ListableBeanFactory beanFactory, boolean considerHierarchy, Class<?> type, Set<Class<?>> parameterizedContainers, Set<String> result) {
        BeanFactory parent;
        result = OnBeanCondition.addAll(result, beanFactory.getBeanNamesForType(type, true, false));
        for (Class<?> container : parameterizedContainers) {
            ResolvableType generic = ResolvableType.forClassWithGenerics(container, (Class[])new Class[]{type});
            result = OnBeanCondition.addAll(result, beanFactory.getBeanNamesForType(generic, true, false));
        }
        if (considerHierarchy && beanFactory instanceof HierarchicalBeanFactory && (parent = ((HierarchicalBeanFactory)beanFactory).getParentBeanFactory()) instanceof ListableBeanFactory) {
            result = this.collectBeanNamesForType((ListableBeanFactory)parent, considerHierarchy, type, parameterizedContainers, result);
        }
        return result;
    }

    private Set<String> getBeanNamesForAnnotation(ClassLoader classLoader, ConfigurableListableBeanFactory beanFactory, String type, boolean considerHierarchy) throws LinkageError {
        Set<String> result = null;
        try {
            result = this.collectBeanNamesForAnnotation((ListableBeanFactory)beanFactory, this.resolveAnnotationType(classLoader, type), considerHierarchy, result);
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
        return result != null ? result : Collections.emptySet();
    }

    private Class<? extends Annotation> resolveAnnotationType(ClassLoader classLoader, String type) throws ClassNotFoundException {
        return OnBeanCondition.resolve(type, classLoader);
    }

    private Set<String> collectBeanNamesForAnnotation(ListableBeanFactory beanFactory, Class<? extends Annotation> annotationType, boolean considerHierarchy, Set<String> result) {
        BeanFactory parent;
        result = OnBeanCondition.addAll(result, beanFactory.getBeanNamesForAnnotation(annotationType));
        if (considerHierarchy && (parent = ((HierarchicalBeanFactory)beanFactory).getParentBeanFactory()) instanceof ListableBeanFactory) {
            result = this.collectBeanNamesForAnnotation((ListableBeanFactory)parent, annotationType, considerHierarchy, result);
        }
        return result;
    }

    private boolean containsBean(ConfigurableListableBeanFactory beanFactory, String beanName, boolean considerHierarchy) {
        if (considerHierarchy) {
            return beanFactory.containsBean(beanName);
        }
        return beanFactory.containsLocalBean(beanName);
    }

    private String createOnBeanNoMatchReason(MatchResult matchResult) {
        StringBuilder reason = new StringBuilder();
        this.appendMessageForNoMatches(reason, matchResult.getUnmatchedAnnotations(), "annotated with");
        this.appendMessageForNoMatches(reason, matchResult.getUnmatchedTypes(), "of type");
        this.appendMessageForNoMatches(reason, matchResult.getUnmatchedNames(), "named");
        return reason.toString();
    }

    private void appendMessageForNoMatches(StringBuilder reason, Collection<String> unmatched, String description) {
        if (!unmatched.isEmpty()) {
            if (reason.length() > 0) {
                reason.append(" and ");
            }
            reason.append("did not find any beans ");
            reason.append(description);
            reason.append(" ");
            reason.append(StringUtils.collectionToDelimitedString(unmatched, (String)", "));
        }
    }

    private String createOnMissingBeanNoMatchReason(MatchResult matchResult) {
        StringBuilder reason = new StringBuilder();
        this.appendMessageForMatches(reason, matchResult.getMatchedAnnotations(), "annotated with");
        this.appendMessageForMatches(reason, matchResult.getMatchedTypes(), "of type");
        if (!matchResult.getMatchedNames().isEmpty()) {
            if (reason.length() > 0) {
                reason.append(" and ");
            }
            reason.append("found beans named ");
            reason.append(StringUtils.collectionToDelimitedString(matchResult.getMatchedNames(), (String)", "));
        }
        return reason.toString();
    }

    private void appendMessageForMatches(StringBuilder reason, Map<String, Collection<String>> matches, String description) {
        if (!matches.isEmpty()) {
            matches.forEach((key, value) -> {
                if (reason.length() > 0) {
                    reason.append(" and ");
                }
                reason.append("found beans ");
                reason.append(description);
                reason.append(" '");
                reason.append((String)key);
                reason.append("' ");
                reason.append(StringUtils.collectionToDelimitedString((Collection)value, (String)", "));
            });
        }
    }

    private List<String> getPrimaryBeans(ConfigurableListableBeanFactory beanFactory, Set<String> beanNames, boolean considerHierarchy) {
        ArrayList<String> primaryBeans = new ArrayList<String>();
        for (String beanName : beanNames) {
            BeanDefinition beanDefinition = this.findBeanDefinition(beanFactory, beanName, considerHierarchy);
            if (beanDefinition == null || !beanDefinition.isPrimary()) continue;
            primaryBeans.add(beanName);
        }
        return primaryBeans;
    }

    private BeanDefinition findBeanDefinition(ConfigurableListableBeanFactory beanFactory, String beanName, boolean considerHierarchy) {
        if (beanFactory.containsBeanDefinition(beanName)) {
            return beanFactory.getBeanDefinition(beanName);
        }
        if (considerHierarchy && beanFactory.getParentBeanFactory() instanceof ConfigurableListableBeanFactory) {
            return this.findBeanDefinition((ConfigurableListableBeanFactory)beanFactory.getParentBeanFactory(), beanName, considerHierarchy);
        }
        return null;
    }

    private static Set<String> addAll(Set<String> result, Collection<String> additional) {
        if (CollectionUtils.isEmpty(additional)) {
            return result;
        }
        result = result != null ? result : new LinkedHashSet<String>();
        result.addAll(additional);
        return result;
    }

    private static Set<String> addAll(Set<String> result, String[] additional) {
        if (ObjectUtils.isEmpty((Object[])additional)) {
            return result;
        }
        result = result != null ? result : new LinkedHashSet<String>();
        Collections.addAll(result, additional);
        return result;
    }

    static final class BeanTypeDeductionException
    extends RuntimeException {
        private BeanTypeDeductionException(String className, String beanMethodName, Throwable cause) {
            super("Failed to deduce bean type for " + className + "." + beanMethodName, cause);
        }
    }

    private static final class MatchResult {
        private final Map<String, Collection<String>> matchedAnnotations = new HashMap<String, Collection<String>>();
        private final List<String> matchedNames = new ArrayList<String>();
        private final Map<String, Collection<String>> matchedTypes = new HashMap<String, Collection<String>>();
        private final List<String> unmatchedAnnotations = new ArrayList<String>();
        private final List<String> unmatchedNames = new ArrayList<String>();
        private final List<String> unmatchedTypes = new ArrayList<String>();
        private final Set<String> namesOfAllMatches = new HashSet<String>();

        private MatchResult() {
        }

        private void recordMatchedName(String name) {
            this.matchedNames.add(name);
            this.namesOfAllMatches.add(name);
        }

        private void recordUnmatchedName(String name) {
            this.unmatchedNames.add(name);
        }

        private void recordMatchedAnnotation(String annotation, Collection<String> matchingNames) {
            this.matchedAnnotations.put(annotation, matchingNames);
            this.namesOfAllMatches.addAll(matchingNames);
        }

        private void recordUnmatchedAnnotation(String annotation) {
            this.unmatchedAnnotations.add(annotation);
        }

        private void recordMatchedType(String type, Collection<String> matchingNames) {
            this.matchedTypes.put(type, matchingNames);
            this.namesOfAllMatches.addAll(matchingNames);
        }

        private void recordUnmatchedType(String type) {
            this.unmatchedTypes.add(type);
        }

        boolean isAllMatched() {
            return this.unmatchedAnnotations.isEmpty() && this.unmatchedNames.isEmpty() && this.unmatchedTypes.isEmpty();
        }

        boolean isAnyMatched() {
            return !this.matchedAnnotations.isEmpty() || !this.matchedNames.isEmpty() || !this.matchedTypes.isEmpty();
        }

        Map<String, Collection<String>> getMatchedAnnotations() {
            return this.matchedAnnotations;
        }

        List<String> getMatchedNames() {
            return this.matchedNames;
        }

        Map<String, Collection<String>> getMatchedTypes() {
            return this.matchedTypes;
        }

        List<String> getUnmatchedAnnotations() {
            return this.unmatchedAnnotations;
        }

        List<String> getUnmatchedNames() {
            return this.unmatchedNames;
        }

        List<String> getUnmatchedTypes() {
            return this.unmatchedTypes;
        }

        Set<String> getNamesOfAllMatches() {
            return this.namesOfAllMatches;
        }
    }

    private static class SingleCandidateSpec
    extends Spec<ConditionalOnSingleCandidate> {
        private static final Collection<String> FILTERED_TYPES = Arrays.asList("", Object.class.getName());

        SingleCandidateSpec(ConditionContext context, AnnotatedTypeMetadata metadata, MergedAnnotations annotations) {
            super(context, metadata, annotations, ConditionalOnSingleCandidate.class);
        }

        @Override
        protected Set<String> extractTypes(MultiValueMap<String, Object> attributes) {
            Set<String> types = super.extractTypes(attributes);
            types.removeAll(FILTERED_TYPES);
            return types;
        }

        @Override
        protected void validate(BeanTypeDeductionException ex) {
            Assert.isTrue((this.getTypes().size() == 1 ? 1 : 0) != 0, () -> this.getAnnotationName() + " annotations must specify only one type (got " + StringUtils.collectionToCommaDelimitedString(this.getTypes()) + ")");
        }
    }

    private static class Spec<A extends Annotation> {
        private final ClassLoader classLoader;
        private final Class<? extends Annotation> annotationType;
        private final Set<String> names;
        private final Set<String> types;
        private final Set<String> annotations;
        private final Set<String> ignoredTypes;
        private final Set<Class<?>> parameterizedContainers;
        private final SearchStrategy strategy;

        Spec(ConditionContext context, AnnotatedTypeMetadata metadata, MergedAnnotations annotations, Class<A> annotationType) {
            MultiValueMap attributes = (MultiValueMap)annotations.stream(annotationType).filter(MergedAnnotationPredicates.unique(MergedAnnotation::getMetaTypes)).collect(MergedAnnotationCollectors.toMultiValueMap((MergedAnnotation.Adapt[])new MergedAnnotation.Adapt[]{MergedAnnotation.Adapt.CLASS_TO_STRING}));
            MergedAnnotation annotation = annotations.get(annotationType);
            this.classLoader = context.getClassLoader();
            this.annotationType = annotationType;
            this.names = this.extract((MultiValueMap<String, Object>)attributes, "name");
            this.annotations = this.extract((MultiValueMap<String, Object>)attributes, "annotation");
            this.ignoredTypes = this.extract((MultiValueMap<String, Object>)attributes, "ignored", "ignoredType");
            this.parameterizedContainers = this.resolveWhenPossible(this.extract((MultiValueMap<String, Object>)attributes, "parameterizedContainer"));
            this.strategy = annotation.getValue("search", SearchStrategy.class).orElse(null);
            Set<String> types = this.extractTypes((MultiValueMap<String, Object>)attributes);
            BeanTypeDeductionException deductionException = null;
            if (types.isEmpty() && this.names.isEmpty()) {
                try {
                    types = this.deducedBeanType(context, metadata);
                }
                catch (BeanTypeDeductionException ex) {
                    deductionException = ex;
                }
            }
            this.types = types;
            this.validate(deductionException);
        }

        protected Set<String> extractTypes(MultiValueMap<String, Object> attributes) {
            return this.extract(attributes, "value", "type");
        }

        private Set<String> extract(MultiValueMap<String, Object> attributes, String ... attributeNames) {
            if (attributes.isEmpty()) {
                return Collections.emptySet();
            }
            LinkedHashSet<String> result = new LinkedHashSet<String>();
            for (String attributeName : attributeNames) {
                List values = (List)attributes.getOrDefault((Object)attributeName, Collections.emptyList());
                for (Object value : values) {
                    if (value instanceof String[]) {
                        this.merge(result, (String[])value);
                        continue;
                    }
                    if (!(value instanceof String)) continue;
                    this.merge(result, (String)value);
                }
            }
            return result.isEmpty() ? Collections.emptySet() : result;
        }

        private void merge(Set<String> result, String ... additional) {
            Collections.addAll(result, additional);
        }

        private Set<Class<?>> resolveWhenPossible(Set<String> classNames) {
            if (classNames.isEmpty()) {
                return Collections.emptySet();
            }
            LinkedHashSet resolved = new LinkedHashSet(classNames.size());
            for (String className : classNames) {
                try {
                    resolved.add(FilteringSpringBootCondition.resolve(className, this.classLoader));
                }
                catch (ClassNotFoundException | NoClassDefFoundError throwable) {}
            }
            return resolved;
        }

        protected void validate(BeanTypeDeductionException ex) {
            if (!this.hasAtLeastOneElement(this.types, this.names, this.annotations)) {
                String message = this.getAnnotationName() + " did not specify a bean using type, name or annotation";
                if (ex == null) {
                    throw new IllegalStateException(message);
                }
                throw new IllegalStateException(message + " and the attempt to deduce the bean's type failed", ex);
            }
        }

        private boolean hasAtLeastOneElement(Set<?> ... sets) {
            for (Set<?> set : sets) {
                if (set.isEmpty()) continue;
                return true;
            }
            return false;
        }

        protected final String getAnnotationName() {
            return "@" + ClassUtils.getShortName(this.annotationType);
        }

        private Set<String> deducedBeanType(ConditionContext context, AnnotatedTypeMetadata metadata) {
            if (metadata instanceof MethodMetadata && metadata.isAnnotated(Bean.class.getName())) {
                return this.deducedBeanTypeForBeanMethod(context, (MethodMetadata)metadata);
            }
            return Collections.emptySet();
        }

        private Set<String> deducedBeanTypeForBeanMethod(ConditionContext context, MethodMetadata metadata) {
            try {
                Class<?> returnType = this.getReturnType(context, metadata);
                return Collections.singleton(returnType.getName());
            }
            catch (Throwable ex) {
                throw new BeanTypeDeductionException(metadata.getDeclaringClassName(), metadata.getMethodName(), ex);
            }
        }

        private Class<?> getReturnType(ConditionContext context, MethodMetadata metadata) throws ClassNotFoundException, LinkageError {
            ClassLoader classLoader = context.getClassLoader();
            Class<?> returnType = FilteringSpringBootCondition.resolve(metadata.getReturnTypeName(), classLoader);
            if (this.isParameterizedContainer(returnType)) {
                returnType = this.getReturnTypeGeneric(metadata, classLoader);
            }
            return returnType;
        }

        private boolean isParameterizedContainer(Class<?> type) {
            for (Class<?> parameterizedContainer : this.parameterizedContainers) {
                if (!parameterizedContainer.isAssignableFrom(type)) continue;
                return true;
            }
            return false;
        }

        private Class<?> getReturnTypeGeneric(MethodMetadata metadata, ClassLoader classLoader) throws ClassNotFoundException, LinkageError {
            Class<?> declaringClass = FilteringSpringBootCondition.resolve(metadata.getDeclaringClassName(), classLoader);
            Method beanMethod = this.findBeanMethod(declaringClass, metadata.getMethodName());
            return ResolvableType.forMethodReturnType((Method)beanMethod).resolveGeneric(new int[0]);
        }

        private Method findBeanMethod(Class<?> declaringClass, String methodName) {
            Method[] candidates;
            Method method = ReflectionUtils.findMethod(declaringClass, (String)methodName);
            if (this.isBeanMethod(method)) {
                return method;
            }
            for (Method candidate : candidates = ReflectionUtils.getAllDeclaredMethods(declaringClass)) {
                if (!candidate.getName().equals(methodName) || !this.isBeanMethod(candidate)) continue;
                return candidate;
            }
            throw new IllegalStateException("Unable to find bean method " + methodName);
        }

        private boolean isBeanMethod(Method method) {
            return method != null && MergedAnnotations.from((AnnotatedElement)method, (MergedAnnotations.SearchStrategy)MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).isPresent(Bean.class);
        }

        private SearchStrategy getStrategy() {
            return this.strategy != null ? this.strategy : SearchStrategy.ALL;
        }

        Set<String> getNames() {
            return this.names;
        }

        Set<String> getTypes() {
            return this.types;
        }

        Set<String> getAnnotations() {
            return this.annotations;
        }

        Set<String> getIgnoredTypes() {
            return this.ignoredTypes;
        }

        Set<Class<?>> getParameterizedContainers() {
            return this.parameterizedContainers;
        }

        ConditionMessage.Builder message() {
            return ConditionMessage.forCondition(this.annotationType, this);
        }

        ConditionMessage.Builder message(ConditionMessage message) {
            return message.andCondition(this.annotationType, this);
        }

        public String toString() {
            boolean hasNames = !this.names.isEmpty();
            boolean hasTypes = !this.types.isEmpty();
            boolean hasIgnoredTypes = !this.ignoredTypes.isEmpty();
            StringBuilder string = new StringBuilder();
            string.append("(");
            if (hasNames) {
                string.append("names: ");
                string.append(StringUtils.collectionToCommaDelimitedString(this.names));
                string.append(hasTypes ? " " : "; ");
            }
            if (hasTypes) {
                string.append("types: ");
                string.append(StringUtils.collectionToCommaDelimitedString(this.types));
                string.append(hasIgnoredTypes ? " " : "; ");
            }
            if (hasIgnoredTypes) {
                string.append("ignored: ");
                string.append(StringUtils.collectionToCommaDelimitedString(this.ignoredTypes));
                string.append("; ");
            }
            string.append("SearchStrategy: ");
            string.append(this.strategy.toString().toLowerCase(Locale.ENGLISH));
            string.append(")");
            return string.toString();
        }
    }
}

