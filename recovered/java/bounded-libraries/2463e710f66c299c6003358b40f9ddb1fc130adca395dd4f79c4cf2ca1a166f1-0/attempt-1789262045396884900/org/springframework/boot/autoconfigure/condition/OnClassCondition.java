/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.ConditionContext
 *  org.springframework.core.annotation.Order
 *  org.springframework.core.type.AnnotatedTypeMetadata
 *  org.springframework.util.MultiValueMap
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.condition;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.boot.autoconfigure.AutoConfigurationMetadata;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.FilteringSpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

@Order(value=-2147483648)
class OnClassCondition
extends FilteringSpringBootCondition {
    OnClassCondition() {
    }

    @Override
    protected final ConditionOutcome[] getOutcomes(String[] autoConfigurationClasses, AutoConfigurationMetadata autoConfigurationMetadata) {
        if (autoConfigurationClasses.length > 1 && Runtime.getRuntime().availableProcessors() > 1) {
            return this.resolveOutcomesThreaded(autoConfigurationClasses, autoConfigurationMetadata);
        }
        StandardOutcomesResolver outcomesResolver = new StandardOutcomesResolver(autoConfigurationClasses, 0, autoConfigurationClasses.length, autoConfigurationMetadata, this.getBeanClassLoader());
        return outcomesResolver.resolveOutcomes();
    }

    private ConditionOutcome[] resolveOutcomesThreaded(String[] autoConfigurationClasses, AutoConfigurationMetadata autoConfigurationMetadata) {
        int split = autoConfigurationClasses.length / 2;
        OutcomesResolver firstHalfResolver = this.createOutcomesResolver(autoConfigurationClasses, 0, split, autoConfigurationMetadata);
        StandardOutcomesResolver secondHalfResolver = new StandardOutcomesResolver(autoConfigurationClasses, split, autoConfigurationClasses.length, autoConfigurationMetadata, this.getBeanClassLoader());
        ConditionOutcome[] secondHalf = secondHalfResolver.resolveOutcomes();
        ConditionOutcome[] firstHalf = firstHalfResolver.resolveOutcomes();
        ConditionOutcome[] outcomes = new ConditionOutcome[autoConfigurationClasses.length];
        System.arraycopy(firstHalf, 0, outcomes, 0, firstHalf.length);
        System.arraycopy(secondHalf, 0, outcomes, split, secondHalf.length);
        return outcomes;
    }

    private OutcomesResolver createOutcomesResolver(String[] autoConfigurationClasses, int start, int end, AutoConfigurationMetadata autoConfigurationMetadata) {
        StandardOutcomesResolver outcomesResolver = new StandardOutcomesResolver(autoConfigurationClasses, start, end, autoConfigurationMetadata, this.getBeanClassLoader());
        try {
            return new ThreadedOutcomesResolver(outcomesResolver);
        }
        catch (AccessControlException ex) {
            return outcomesResolver;
        }
    }

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        List<String> onMissingClasses;
        ClassLoader classLoader = context.getClassLoader();
        ConditionMessage matchMessage = ConditionMessage.empty();
        List<String> onClasses = this.getCandidates(metadata, ConditionalOnClass.class);
        if (onClasses != null) {
            List<String> missing = this.filter(onClasses, FilteringSpringBootCondition.ClassNameFilter.MISSING, classLoader);
            if (!missing.isEmpty()) {
                return ConditionOutcome.noMatch(ConditionMessage.forCondition(ConditionalOnClass.class, new Object[0]).didNotFind("required class", "required classes").items(ConditionMessage.Style.QUOTE, missing));
            }
            matchMessage = matchMessage.andCondition(ConditionalOnClass.class, new Object[0]).found("required class", "required classes").items(ConditionMessage.Style.QUOTE, this.filter(onClasses, FilteringSpringBootCondition.ClassNameFilter.PRESENT, classLoader));
        }
        if ((onMissingClasses = this.getCandidates(metadata, ConditionalOnMissingClass.class)) != null) {
            List<String> present = this.filter(onMissingClasses, FilteringSpringBootCondition.ClassNameFilter.PRESENT, classLoader);
            if (!present.isEmpty()) {
                return ConditionOutcome.noMatch(ConditionMessage.forCondition(ConditionalOnMissingClass.class, new Object[0]).found("unwanted class", "unwanted classes").items(ConditionMessage.Style.QUOTE, present));
            }
            matchMessage = matchMessage.andCondition(ConditionalOnMissingClass.class, new Object[0]).didNotFind("unwanted class", "unwanted classes").items(ConditionMessage.Style.QUOTE, this.filter(onMissingClasses, FilteringSpringBootCondition.ClassNameFilter.MISSING, classLoader));
        }
        return ConditionOutcome.match(matchMessage);
    }

    private List<String> getCandidates(AnnotatedTypeMetadata metadata, Class<?> annotationType) {
        MultiValueMap attributes = metadata.getAllAnnotationAttributes(annotationType.getName(), true);
        if (attributes == null) {
            return null;
        }
        ArrayList<String> candidates = new ArrayList<String>();
        this.addAll(candidates, (List)attributes.get((Object)"value"));
        this.addAll(candidates, (List)attributes.get((Object)"name"));
        return candidates;
    }

    private void addAll(List<String> list, List<Object> itemsToAdd) {
        if (itemsToAdd != null) {
            for (Object item : itemsToAdd) {
                Collections.addAll(list, (String[])item);
            }
        }
    }

    private static final class StandardOutcomesResolver
    implements OutcomesResolver {
        private final String[] autoConfigurationClasses;
        private final int start;
        private final int end;
        private final AutoConfigurationMetadata autoConfigurationMetadata;
        private final ClassLoader beanClassLoader;

        private StandardOutcomesResolver(String[] autoConfigurationClasses, int start, int end, AutoConfigurationMetadata autoConfigurationMetadata, ClassLoader beanClassLoader) {
            this.autoConfigurationClasses = autoConfigurationClasses;
            this.start = start;
            this.end = end;
            this.autoConfigurationMetadata = autoConfigurationMetadata;
            this.beanClassLoader = beanClassLoader;
        }

        @Override
        public ConditionOutcome[] resolveOutcomes() {
            return this.getOutcomes(this.autoConfigurationClasses, this.start, this.end, this.autoConfigurationMetadata);
        }

        private ConditionOutcome[] getOutcomes(String[] autoConfigurationClasses, int start, int end, AutoConfigurationMetadata autoConfigurationMetadata) {
            ConditionOutcome[] outcomes = new ConditionOutcome[end - start];
            for (int i = start; i < end; ++i) {
                String candidates;
                String autoConfigurationClass = autoConfigurationClasses[i];
                if (autoConfigurationClass == null || (candidates = autoConfigurationMetadata.get(autoConfigurationClass, "ConditionalOnClass")) == null) continue;
                outcomes[i - start] = this.getOutcome(candidates);
            }
            return outcomes;
        }

        private ConditionOutcome getOutcome(String candidates) {
            try {
                if (!candidates.contains(",")) {
                    return this.getOutcome(candidates, this.beanClassLoader);
                }
                for (String candidate : StringUtils.commaDelimitedListToStringArray((String)candidates)) {
                    ConditionOutcome outcome = this.getOutcome(candidate, this.beanClassLoader);
                    if (outcome == null) continue;
                    return outcome;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            return null;
        }

        private ConditionOutcome getOutcome(String className, ClassLoader classLoader) {
            if (FilteringSpringBootCondition.ClassNameFilter.MISSING.matches(className, classLoader)) {
                return ConditionOutcome.noMatch(ConditionMessage.forCondition(ConditionalOnClass.class, new Object[0]).didNotFind("required class").items(ConditionMessage.Style.QUOTE, className));
            }
            return null;
        }
    }

    private static final class ThreadedOutcomesResolver
    implements OutcomesResolver {
        private final Thread thread = new Thread(() -> {
            this.outcomes = outcomesResolver.resolveOutcomes();
        });
        private volatile ConditionOutcome[] outcomes;

        private ThreadedOutcomesResolver(OutcomesResolver outcomesResolver) {
            this.thread.start();
        }

        @Override
        public ConditionOutcome[] resolveOutcomes() {
            try {
                this.thread.join();
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            return this.outcomes;
        }
    }

    private static interface OutcomesResolver {
        public ConditionOutcome[] resolveOutcomes();
    }
}

