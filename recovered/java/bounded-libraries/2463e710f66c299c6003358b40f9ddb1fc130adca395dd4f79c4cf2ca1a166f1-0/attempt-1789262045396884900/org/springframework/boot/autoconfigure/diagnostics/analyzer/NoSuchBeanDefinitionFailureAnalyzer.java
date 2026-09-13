/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryUtils
 *  org.springframework.beans.factory.InjectionPoint
 *  org.springframework.beans.factory.ListableBeanFactory
 *  org.springframework.beans.factory.NoSuchBeanDefinitionException
 *  org.springframework.beans.factory.UnsatisfiedDependencyException
 *  org.springframework.beans.factory.annotation.AnnotatedBeanDefinition
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.boot.context.properties.ConfigurationProperties
 *  org.springframework.boot.context.properties.ConstructorBinding
 *  org.springframework.boot.diagnostics.FailureAnalysis
 *  org.springframework.boot.diagnostics.analyzer.AbstractInjectionFailureAnalyzer
 *  org.springframework.context.annotation.Bean
 *  org.springframework.core.KotlinDetector
 *  org.springframework.core.ResolvableType
 *  org.springframework.core.annotation.MergedAnnotation
 *  org.springframework.core.annotation.MergedAnnotations
 *  org.springframework.core.type.MethodMetadata
 *  org.springframework.core.type.classreading.CachingMetadataReaderFactory
 *  org.springframework.core.type.classreading.MetadataReader
 *  org.springframework.core.type.classreading.MetadataReaderFactory
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot.autoconfigure.diagnostics.analyzer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.diagnostics.analyzer.AbstractInjectionFailureAnalyzer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.KotlinDetector;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

class NoSuchBeanDefinitionFailureAnalyzer
extends AbstractInjectionFailureAnalyzer<NoSuchBeanDefinitionException> {
    private final ConfigurableListableBeanFactory beanFactory;
    private final MetadataReaderFactory metadataReaderFactory;
    private final ConditionEvaluationReport report;

    NoSuchBeanDefinitionFailureAnalyzer(BeanFactory beanFactory) {
        Assert.isInstanceOf(ConfigurableListableBeanFactory.class, (Object)beanFactory);
        this.beanFactory = (ConfigurableListableBeanFactory)beanFactory;
        this.metadataReaderFactory = new CachingMetadataReaderFactory(this.beanFactory.getBeanClassLoader());
        this.report = ConditionEvaluationReport.get(this.beanFactory);
    }

    protected FailureAnalysis analyze(Throwable rootFailure, NoSuchBeanDefinitionException cause, String description) {
        Constructor constructor;
        Class declaringClass;
        MergedAnnotation configurationProperties;
        Object injectionAnnotations;
        if (cause.getNumberOfBeansFound() != 0) {
            return null;
        }
        List<AutoConfigurationResult> autoConfigurationResults = this.getAutoConfigurationResults(cause);
        List<UserConfigurationResult> userConfigurationResults = this.getUserConfigurationResults(cause);
        StringBuilder message = new StringBuilder();
        message.append(String.format("%s required %s that could not be found.%n", description != null ? description : "A component", this.getBeanDescription(cause)));
        InjectionPoint injectionPoint = this.findInjectionPoint(rootFailure);
        if (injectionPoint != null && ((Annotation[])(injectionAnnotations = injectionPoint.getAnnotations())).length > 0) {
            message.append(String.format("%nThe injection point has the following annotations:%n", new Object[0]));
            for (Object injectionAnnotation : injectionAnnotations) {
                message.append(String.format("\t- %s%n", injectionAnnotation));
            }
        }
        if (!autoConfigurationResults.isEmpty() || !userConfigurationResults.isEmpty()) {
            message.append(String.format("%nThe following candidates were found but could not be injected:%n", new Object[0]));
            for (AutoConfigurationResult autoConfigurationResult : autoConfigurationResults) {
                message.append(String.format("\t- %s%n", autoConfigurationResult));
            }
            for (UserConfigurationResult userConfigurationResult : userConfigurationResults) {
                message.append(String.format("\t- %s%n", userConfigurationResult));
            }
        }
        String action = String.format("Consider %s %s in your configuration.", !autoConfigurationResults.isEmpty() || !userConfigurationResults.isEmpty() ? "revisiting the entries above or defining" : "defining", this.getBeanDescription(cause));
        if (injectionPoint != null && injectionPoint.getMember() instanceof Constructor && (configurationProperties = MergedAnnotations.from(declaringClass = (constructor = (Constructor)injectionPoint.getMember()).getDeclaringClass()).get(ConfigurationProperties.class)).isPresent()) {
            action = KotlinDetector.isKotlinType(declaringClass) && !KotlinDetector.isKotlinReflectPresent() ? String.format("%s%nConsider adding a dependency on kotlin-reflect so that the constructor used for @%s can be located. Also, ensure that @%s is present on '%s' if you intended to use constructor-based configuration property binding.", action, ConstructorBinding.class.getSimpleName(), ConstructorBinding.class.getSimpleName(), constructor.getName()) : String.format("%s%nConsider adding @%s to %s if you intended to use constructor-based configuration property binding.", action, ConstructorBinding.class.getSimpleName(), constructor.getName());
        }
        return new FailureAnalysis(message.toString(), action, (Throwable)cause);
    }

    private String getBeanDescription(NoSuchBeanDefinitionException cause) {
        if (cause.getResolvableType() != null) {
            Class<?> type = this.extractBeanType(cause.getResolvableType());
            return "a bean of type '" + type.getName() + "'";
        }
        return "a bean named '" + cause.getBeanName() + "'";
    }

    private Class<?> extractBeanType(ResolvableType resolvableType) {
        return resolvableType.getRawClass();
    }

    private List<AutoConfigurationResult> getAutoConfigurationResults(NoSuchBeanDefinitionException cause) {
        ArrayList<AutoConfigurationResult> results = new ArrayList<AutoConfigurationResult>();
        this.collectReportedConditionOutcomes(cause, results);
        this.collectExcludedAutoConfiguration(cause, results);
        return results;
    }

    private List<UserConfigurationResult> getUserConfigurationResults(NoSuchBeanDefinitionException cause) {
        ResolvableType type = cause.getResolvableType();
        if (type == null) {
            return Collections.emptyList();
        }
        String[] beanNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors((ListableBeanFactory)this.beanFactory, (ResolvableType)type);
        return Arrays.stream(beanNames).map(beanName -> new UserConfigurationResult(this.getFactoryMethodMetadata((String)beanName), this.beanFactory.getBean(beanName).equals(null))).collect(Collectors.toList());
    }

    private MethodMetadata getFactoryMethodMetadata(String beanName) {
        BeanDefinition beanDefinition = this.beanFactory.getBeanDefinition(beanName);
        if (beanDefinition instanceof AnnotatedBeanDefinition) {
            return ((AnnotatedBeanDefinition)beanDefinition).getFactoryMethodMetadata();
        }
        return null;
    }

    private void collectReportedConditionOutcomes(NoSuchBeanDefinitionException cause, List<AutoConfigurationResult> results) {
        this.report.getConditionAndOutcomesBySource().forEach((source, sourceOutcomes) -> this.collectReportedConditionOutcomes(cause, new Source((String)source), (ConditionEvaluationReport.ConditionAndOutcomes)sourceOutcomes, results));
    }

    private void collectReportedConditionOutcomes(NoSuchBeanDefinitionException cause, Source source, ConditionEvaluationReport.ConditionAndOutcomes sourceOutcomes, List<AutoConfigurationResult> results) {
        if (sourceOutcomes.isFullMatch()) {
            return;
        }
        BeanMethods methods = new BeanMethods(source, cause);
        for (ConditionEvaluationReport.ConditionAndOutcome conditionAndOutcome : sourceOutcomes) {
            if (conditionAndOutcome.getOutcome().isMatch()) continue;
            for (MethodMetadata method : methods) {
                results.add(new AutoConfigurationResult(method, conditionAndOutcome.getOutcome()));
            }
        }
    }

    private void collectExcludedAutoConfiguration(NoSuchBeanDefinitionException cause, List<AutoConfigurationResult> results) {
        for (String excludedClass : this.report.getExclusions()) {
            Source source = new Source(excludedClass);
            BeanMethods methods = new BeanMethods(source, cause);
            for (MethodMetadata method : methods) {
                String message = String.format("auto-configuration '%s' was excluded", ClassUtils.getShortName((String)excludedClass));
                results.add(new AutoConfigurationResult(method, new ConditionOutcome(false, message)));
            }
        }
    }

    private InjectionPoint findInjectionPoint(Throwable failure) {
        UnsatisfiedDependencyException unsatisfiedDependencyException = (UnsatisfiedDependencyException)this.findCause(failure, UnsatisfiedDependencyException.class);
        if (unsatisfiedDependencyException == null) {
            return null;
        }
        return unsatisfiedDependencyException.getInjectionPoint();
    }

    private static class UserConfigurationResult {
        private final MethodMetadata methodMetadata;
        private final boolean nullBean;

        UserConfigurationResult(MethodMetadata methodMetadata, boolean nullBean) {
            this.methodMetadata = methodMetadata;
            this.nullBean = nullBean;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("User-defined bean");
            if (this.methodMetadata != null) {
                sb.append(String.format(" method '%s' in '%s'", this.methodMetadata.getMethodName(), ClassUtils.getShortName((String)this.methodMetadata.getDeclaringClassName())));
            }
            if (this.nullBean) {
                sb.append(" ignored as the bean value is null");
            }
            return sb.toString();
        }
    }

    private static class AutoConfigurationResult {
        private final MethodMetadata methodMetadata;
        private final ConditionOutcome conditionOutcome;

        AutoConfigurationResult(MethodMetadata methodMetadata, ConditionOutcome conditionOutcome) {
            this.methodMetadata = methodMetadata;
            this.conditionOutcome = conditionOutcome;
        }

        public String toString() {
            return String.format("Bean method '%s' in '%s' not loaded because %s", this.methodMetadata.getMethodName(), ClassUtils.getShortName((String)this.methodMetadata.getDeclaringClassName()), this.conditionOutcome.getMessage());
        }
    }

    private class BeanMethods
    implements Iterable<MethodMetadata> {
        private final List<MethodMetadata> methods;

        BeanMethods(Source source, NoSuchBeanDefinitionException cause) {
            this.methods = this.findBeanMethods(source, cause);
        }

        private List<MethodMetadata> findBeanMethods(Source source, NoSuchBeanDefinitionException cause) {
            try {
                MetadataReader classMetadata = NoSuchBeanDefinitionFailureAnalyzer.this.metadataReaderFactory.getMetadataReader(source.getClassName());
                Set candidates = classMetadata.getAnnotationMetadata().getAnnotatedMethods(Bean.class.getName());
                ArrayList<MethodMetadata> result = new ArrayList<MethodMetadata>();
                for (MethodMetadata candidate : candidates) {
                    if (!this.isMatch(candidate, source, cause)) continue;
                    result.add(candidate);
                }
                return Collections.unmodifiableList(result);
            }
            catch (Exception ex) {
                return Collections.emptyList();
            }
        }

        private boolean isMatch(MethodMetadata candidate, Source source, NoSuchBeanDefinitionException cause) {
            if (source.getMethodName() != null && !source.getMethodName().equals(candidate.getMethodName())) {
                return false;
            }
            String name = cause.getBeanName();
            ResolvableType resolvableType = cause.getResolvableType();
            return name != null && this.hasName(candidate, name) || resolvableType != null && this.hasType(candidate, NoSuchBeanDefinitionFailureAnalyzer.this.extractBeanType(resolvableType));
        }

        private boolean hasName(MethodMetadata methodMetadata, String name) {
            String[] candidates;
            Map attributes = methodMetadata.getAnnotationAttributes(Bean.class.getName());
            String[] stringArray = candidates = attributes != null ? (String[])attributes.get("name") : null;
            if (candidates != null) {
                for (String candidate : candidates) {
                    if (!candidate.equals(name)) continue;
                    return true;
                }
                return false;
            }
            return methodMetadata.getMethodName().equals(name);
        }

        private boolean hasType(MethodMetadata candidate, Class<?> type) {
            String returnTypeName = candidate.getReturnTypeName();
            if (type.getName().equals(returnTypeName)) {
                return true;
            }
            try {
                Class returnType = ClassUtils.forName((String)returnTypeName, (ClassLoader)NoSuchBeanDefinitionFailureAnalyzer.this.beanFactory.getBeanClassLoader());
                return type.isAssignableFrom(returnType);
            }
            catch (Throwable ex) {
                return false;
            }
        }

        @Override
        public Iterator<MethodMetadata> iterator() {
            return this.methods.iterator();
        }
    }

    private static class Source {
        private final String className;
        private final String methodName;

        Source(String source) {
            String[] tokens = source.split("#");
            this.className = tokens.length > 1 ? tokens[0] : source;
            this.methodName = tokens.length != 2 ? null : tokens[1];
        }

        String getClassName() {
            return this.className;
        }

        String getMethodName() {
            return this.methodName;
        }
    }
}

