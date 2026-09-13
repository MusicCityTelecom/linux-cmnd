/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanCreationException
 *  org.springframework.beans.factory.BeanCurrentlyInCreationException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.InjectionPoint
 *  org.springframework.beans.factory.UnsatisfiedDependencyException
 *  org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.diagnostics.analyzer;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanCurrentlyInCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.util.StringUtils;

class BeanCurrentlyInCreationFailureAnalyzer
extends AbstractFailureAnalyzer<BeanCurrentlyInCreationException> {
    private final AbstractAutowireCapableBeanFactory beanFactory;

    BeanCurrentlyInCreationFailureAnalyzer(BeanFactory beanFactory) {
        this.beanFactory = beanFactory != null && beanFactory instanceof AbstractAutowireCapableBeanFactory ? (AbstractAutowireCapableBeanFactory)beanFactory : null;
    }

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, BeanCurrentlyInCreationException cause) {
        DependencyCycle dependencyCycle = this.findCycle(rootFailure);
        if (dependencyCycle == null) {
            return null;
        }
        return new FailureAnalysis(this.buildMessage(dependencyCycle), this.action(), (Throwable)cause);
    }

    private String action() {
        if (this.beanFactory != null && this.beanFactory.isAllowCircularReferences()) {
            return "Despite circular references being allowed, the dependency cycle between beans could not be broken. Update your application to remove the dependency cycle.";
        }
        return "Relying upon circular references is discouraged and they are prohibited by default. Update your application to remove the dependency cycle between beans. As a last resort, it may be possible to break the cycle automatically by setting spring.main.allow-circular-references to true.";
    }

    private DependencyCycle findCycle(Throwable rootFailure) {
        ArrayList<BeanInCycle> beansInCycle = new ArrayList<BeanInCycle>();
        int cycleStart = -1;
        for (Throwable candidate = rootFailure; candidate != null; candidate = candidate.getCause()) {
            BeanInCycle beanInCycle = BeanInCycle.get(candidate);
            if (beanInCycle == null) continue;
            int index = beansInCycle.indexOf(beanInCycle);
            if (index == -1) {
                beansInCycle.add(beanInCycle);
            }
            cycleStart = cycleStart != -1 ? cycleStart : index;
        }
        if (cycleStart == -1) {
            return null;
        }
        return new DependencyCycle(beansInCycle, cycleStart);
    }

    private String buildMessage(DependencyCycle dependencyCycle) {
        StringBuilder message = new StringBuilder();
        message.append(String.format("The dependencies of some of the beans in the application context form a cycle:%n%n", new Object[0]));
        List<BeanInCycle> beansInCycle = dependencyCycle.getBeansInCycle();
        boolean singleBean = beansInCycle.size() == 1;
        int cycleStart = dependencyCycle.getCycleStart();
        for (int i = 0; i < beansInCycle.size(); ++i) {
            String leftSide;
            BeanInCycle beanInCycle = beansInCycle.get(i);
            if (i == cycleStart) {
                message.append(String.format(singleBean ? "\u250c\u2500\u2500->\u2500\u2500\u2510%n" : "\u250c\u2500\u2500\u2500\u2500\u2500\u2510%n", new Object[0]));
            } else if (i > 0) {
                leftSide = i < cycleStart ? " " : "\u2191";
                message.append(String.format("%s     \u2193%n", leftSide));
            }
            leftSide = i < cycleStart ? " " : "|";
            message.append(String.format("%s  %s%n", leftSide, beanInCycle));
        }
        message.append(String.format(singleBean ? "\u2514\u2500\u2500<-\u2500\u2500\u2518%n" : "\u2514\u2500\u2500\u2500\u2500\u2500\u2518%n", new Object[0]));
        return message.toString();
    }

    private static final class BeanInCycle {
        private final String name;
        private final String description;

        private BeanInCycle(BeanCreationException ex) {
            this.name = ex.getBeanName();
            this.description = this.determineDescription(ex);
        }

        private String determineDescription(BeanCreationException ex) {
            if (StringUtils.hasText((String)ex.getResourceDescription())) {
                return String.format(" defined in %s", ex.getResourceDescription());
            }
            InjectionPoint failedInjectionPoint = this.findFailedInjectionPoint(ex);
            if (failedInjectionPoint != null && failedInjectionPoint.getField() != null) {
                return String.format(" (field %s)", failedInjectionPoint.getField());
            }
            return "";
        }

        private InjectionPoint findFailedInjectionPoint(BeanCreationException ex) {
            if (!(ex instanceof UnsatisfiedDependencyException)) {
                return null;
            }
            return ((UnsatisfiedDependencyException)ex).getInjectionPoint();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || this.getClass() != obj.getClass()) {
                return false;
            }
            return this.name.equals(((BeanInCycle)obj).name);
        }

        public int hashCode() {
            return this.name.hashCode();
        }

        public String toString() {
            return this.name + this.description;
        }

        static BeanInCycle get(Throwable ex) {
            if (ex instanceof BeanCreationException) {
                return BeanInCycle.get((BeanCreationException)ex);
            }
            return null;
        }

        private static BeanInCycle get(BeanCreationException ex) {
            if (StringUtils.hasText((String)ex.getBeanName())) {
                return new BeanInCycle(ex);
            }
            return null;
        }
    }

    private static final class DependencyCycle {
        private final List<BeanInCycle> beansInCycle;
        private final int cycleStart;

        private DependencyCycle(List<BeanInCycle> beansInCycle, int cycleStart) {
            this.beansInCycle = beansInCycle;
            this.cycleStart = cycleStart;
        }

        List<BeanInCycle> getBeansInCycle() {
            return this.beansInCycle;
        }

        int getCycleStart() {
            return this.cycleStart;
        }
    }
}

