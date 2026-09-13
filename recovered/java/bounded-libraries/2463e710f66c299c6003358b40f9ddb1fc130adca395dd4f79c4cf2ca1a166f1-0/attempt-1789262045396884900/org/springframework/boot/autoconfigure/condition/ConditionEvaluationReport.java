/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.config.ConfigurableBeanFactory
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.context.annotation.Condition
 *  org.springframework.context.annotation.ConditionContext
 *  org.springframework.core.type.AnnotatedTypeMetadata
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.boot.autoconfigure.condition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

public final class ConditionEvaluationReport {
    private static final String BEAN_NAME = "autoConfigurationReport";
    private static final AncestorsMatchedCondition ANCESTOR_CONDITION = new AncestorsMatchedCondition();
    private final SortedMap<String, ConditionAndOutcomes> outcomes = new TreeMap<String, ConditionAndOutcomes>();
    private boolean addedAncestorOutcomes;
    private ConditionEvaluationReport parent;
    private final List<String> exclusions = new ArrayList<String>();
    private final Set<String> unconditionalClasses = new HashSet<String>();

    private ConditionEvaluationReport() {
    }

    public void recordConditionEvaluation(String source, Condition condition, ConditionOutcome outcome) {
        Assert.notNull((Object)source, (String)"Source must not be null");
        Assert.notNull((Object)condition, (String)"Condition must not be null");
        Assert.notNull((Object)outcome, (String)"Outcome must not be null");
        this.unconditionalClasses.remove(source);
        if (!this.outcomes.containsKey(source)) {
            this.outcomes.put(source, new ConditionAndOutcomes());
        }
        ((ConditionAndOutcomes)this.outcomes.get(source)).add(condition, outcome);
        this.addedAncestorOutcomes = false;
    }

    public void recordExclusions(Collection<String> exclusions) {
        Assert.notNull(exclusions, (String)"exclusions must not be null");
        this.exclusions.addAll(exclusions);
    }

    public void recordEvaluationCandidates(List<String> evaluationCandidates) {
        Assert.notNull(evaluationCandidates, (String)"evaluationCandidates must not be null");
        this.unconditionalClasses.addAll(evaluationCandidates);
    }

    public Map<String, ConditionAndOutcomes> getConditionAndOutcomesBySource() {
        if (!this.addedAncestorOutcomes) {
            this.outcomes.forEach((source, sourceOutcomes) -> {
                if (!sourceOutcomes.isFullMatch()) {
                    this.addNoMatchOutcomeToAncestors((String)source);
                }
            });
            this.addedAncestorOutcomes = true;
        }
        return Collections.unmodifiableMap(this.outcomes);
    }

    private void addNoMatchOutcomeToAncestors(String source) {
        String prefix = source + "$";
        this.outcomes.forEach((candidateSource, sourceOutcomes) -> {
            if (candidateSource.startsWith(prefix)) {
                ConditionOutcome outcome = ConditionOutcome.noMatch(ConditionMessage.forCondition("Ancestor " + source, new Object[0]).because("did not match"));
                sourceOutcomes.add(ANCESTOR_CONDITION, outcome);
            }
        });
    }

    public List<String> getExclusions() {
        return Collections.unmodifiableList(this.exclusions);
    }

    public Set<String> getUnconditionalClasses() {
        HashSet<String> filtered = new HashSet<String>(this.unconditionalClasses);
        filtered.removeAll(this.exclusions);
        return Collections.unmodifiableSet(filtered);
    }

    public ConditionEvaluationReport getParent() {
        return this.parent;
    }

    public static ConditionEvaluationReport find(BeanFactory beanFactory) {
        if (beanFactory != null && beanFactory instanceof ConfigurableBeanFactory) {
            return ConditionEvaluationReport.get((ConfigurableListableBeanFactory)beanFactory);
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ConditionEvaluationReport get(ConfigurableListableBeanFactory beanFactory) {
        ConfigurableListableBeanFactory configurableListableBeanFactory = beanFactory;
        synchronized (configurableListableBeanFactory) {
            ConditionEvaluationReport report;
            if (beanFactory.containsSingleton(BEAN_NAME)) {
                report = (ConditionEvaluationReport)beanFactory.getBean(BEAN_NAME, ConditionEvaluationReport.class);
            } else {
                report = new ConditionEvaluationReport();
                beanFactory.registerSingleton(BEAN_NAME, (Object)report);
            }
            ConditionEvaluationReport.locateParent(beanFactory.getParentBeanFactory(), report);
            return report;
        }
    }

    private static void locateParent(BeanFactory beanFactory, ConditionEvaluationReport report) {
        if (beanFactory != null && report.parent == null && beanFactory.containsBean(BEAN_NAME)) {
            report.parent = (ConditionEvaluationReport)beanFactory.getBean(BEAN_NAME, ConditionEvaluationReport.class);
        }
    }

    public ConditionEvaluationReport getDelta(ConditionEvaluationReport previousReport) {
        ConditionEvaluationReport delta = new ConditionEvaluationReport();
        this.outcomes.forEach((source, sourceOutcomes) -> {
            ConditionAndOutcomes previous = (ConditionAndOutcomes)previousReport.outcomes.get(source);
            if (previous == null || previous.isFullMatch() != sourceOutcomes.isFullMatch()) {
                sourceOutcomes.forEach(conditionAndOutcome -> delta.recordConditionEvaluation((String)source, conditionAndOutcome.getCondition(), conditionAndOutcome.getOutcome()));
            }
        });
        ArrayList<String> newExclusions = new ArrayList<String>(this.exclusions);
        newExclusions.removeAll(previousReport.getExclusions());
        delta.recordExclusions(newExclusions);
        ArrayList<String> newUnconditionalClasses = new ArrayList<String>(this.unconditionalClasses);
        newUnconditionalClasses.removeAll(previousReport.unconditionalClasses);
        delta.unconditionalClasses.addAll(newUnconditionalClasses);
        return delta;
    }

    private static class AncestorsMatchedCondition
    implements Condition {
        private AncestorsMatchedCondition() {
        }

        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            throw new UnsupportedOperationException();
        }
    }

    public static class ConditionAndOutcome {
        private final Condition condition;
        private final ConditionOutcome outcome;

        public ConditionAndOutcome(Condition condition, ConditionOutcome outcome) {
            this.condition = condition;
            this.outcome = outcome;
        }

        public Condition getCondition() {
            return this.condition;
        }

        public ConditionOutcome getOutcome() {
            return this.outcome;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || this.getClass() != obj.getClass()) {
                return false;
            }
            ConditionAndOutcome other = (ConditionAndOutcome)obj;
            return ObjectUtils.nullSafeEquals(this.condition.getClass(), other.condition.getClass()) && ObjectUtils.nullSafeEquals((Object)this.outcome, (Object)other.outcome);
        }

        public int hashCode() {
            return this.condition.getClass().hashCode() * 31 + this.outcome.hashCode();
        }

        public String toString() {
            return this.condition.getClass() + " " + this.outcome;
        }
    }

    public static class ConditionAndOutcomes
    implements Iterable<ConditionAndOutcome> {
        private final Set<ConditionAndOutcome> outcomes = new LinkedHashSet<ConditionAndOutcome>();

        public void add(Condition condition, ConditionOutcome outcome) {
            this.outcomes.add(new ConditionAndOutcome(condition, outcome));
        }

        public boolean isFullMatch() {
            for (ConditionAndOutcome conditionAndOutcomes : this) {
                if (conditionAndOutcomes.getOutcome().isMatch()) continue;
                return false;
            }
            return true;
        }

        @Override
        public Iterator<ConditionAndOutcome> iterator() {
            return Collections.unmodifiableSet(this.outcomes).iterator();
        }
    }
}

