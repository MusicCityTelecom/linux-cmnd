/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeanUtils
 *  org.springframework.context.annotation.Condition
 *  org.springframework.context.annotation.ConditionContext
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.ConfigurationCondition
 *  org.springframework.context.annotation.ConfigurationCondition$ConfigurationPhase
 *  org.springframework.core.type.AnnotatedTypeMetadata
 *  org.springframework.core.type.AnnotationMetadata
 *  org.springframework.core.type.classreading.MetadataReaderFactory
 *  org.springframework.core.type.classreading.SimpleMetadataReaderFactory
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.LinkedMultiValueMap
 *  org.springframework.util.MultiValueMap
 */
package org.springframework.boot.autoconfigure.condition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public abstract class AbstractNestedCondition
extends SpringBootCondition
implements ConfigurationCondition {
    private final ConfigurationCondition.ConfigurationPhase configurationPhase;

    AbstractNestedCondition(ConfigurationCondition.ConfigurationPhase configurationPhase) {
        Assert.notNull((Object)configurationPhase, (String)"ConfigurationPhase must not be null");
        this.configurationPhase = configurationPhase;
    }

    public ConfigurationCondition.ConfigurationPhase getConfigurationPhase() {
        return this.configurationPhase;
    }

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String className = this.getClass().getName();
        MemberConditions memberConditions = new MemberConditions(context, this.configurationPhase, className);
        MemberMatchOutcomes memberOutcomes = new MemberMatchOutcomes(memberConditions);
        return this.getFinalMatchOutcome(memberOutcomes);
    }

    protected abstract ConditionOutcome getFinalMatchOutcome(MemberMatchOutcomes var1);

    private static class MemberOutcomes {
        private final ConditionContext context;
        private final AnnotationMetadata metadata;
        private final List<ConditionOutcome> outcomes;

        MemberOutcomes(ConditionContext context, AnnotationMetadata metadata, List<Condition> conditions) {
            this.context = context;
            this.metadata = metadata;
            this.outcomes = new ArrayList<ConditionOutcome>(conditions.size());
            for (Condition condition : conditions) {
                this.outcomes.add(this.getConditionOutcome(metadata, condition));
            }
        }

        private ConditionOutcome getConditionOutcome(AnnotationMetadata metadata, Condition condition) {
            if (condition instanceof SpringBootCondition) {
                return ((SpringBootCondition)condition).getMatchOutcome(this.context, (AnnotatedTypeMetadata)metadata);
            }
            return new ConditionOutcome(condition.matches(this.context, (AnnotatedTypeMetadata)metadata), ConditionMessage.empty());
        }

        ConditionOutcome getUltimateOutcome() {
            ConditionMessage.Builder message = ConditionMessage.forCondition("NestedCondition on " + ClassUtils.getShortName((String)this.metadata.getClassName()), new Object[0]);
            if (this.outcomes.size() == 1) {
                ConditionOutcome outcome = this.outcomes.get(0);
                return new ConditionOutcome(outcome.isMatch(), message.because(outcome.getMessage()));
            }
            ArrayList<ConditionOutcome> match = new ArrayList<ConditionOutcome>();
            ArrayList nonMatch = new ArrayList();
            for (ConditionOutcome outcome : this.outcomes) {
                (outcome.isMatch() ? match : nonMatch).add(outcome);
            }
            if (nonMatch.isEmpty()) {
                return ConditionOutcome.match(message.found("matching nested conditions").items(match));
            }
            return ConditionOutcome.noMatch(message.found("non-matching nested conditions").items(nonMatch));
        }
    }

    private static class MemberConditions {
        private final ConditionContext context;
        private final MetadataReaderFactory readerFactory;
        private final Map<AnnotationMetadata, List<Condition>> memberConditions;

        MemberConditions(ConditionContext context, ConfigurationCondition.ConfigurationPhase phase, String className) {
            this.context = context;
            this.readerFactory = new SimpleMetadataReaderFactory(context.getResourceLoader());
            String[] members = this.getMetadata(className).getMemberClassNames();
            this.memberConditions = this.getMemberConditions(members, phase, className);
        }

        private Map<AnnotationMetadata, List<Condition>> getMemberConditions(String[] members, ConfigurationCondition.ConfigurationPhase phase, String className) {
            LinkedMultiValueMap memberConditions = new LinkedMultiValueMap();
            for (String member : members) {
                AnnotationMetadata metadata = this.getMetadata(member);
                for (String[] conditionClasses : this.getConditionClasses((AnnotatedTypeMetadata)metadata)) {
                    for (String conditionClass : conditionClasses) {
                        Condition condition = this.getCondition(conditionClass);
                        this.validateMemberCondition(condition, phase, className);
                        memberConditions.add((Object)metadata, (Object)condition);
                    }
                }
            }
            return Collections.unmodifiableMap(memberConditions);
        }

        private void validateMemberCondition(Condition condition, ConfigurationCondition.ConfigurationPhase nestedPhase, String nestedClassName) {
            ConfigurationCondition.ConfigurationPhase memberPhase;
            if (nestedPhase == ConfigurationCondition.ConfigurationPhase.PARSE_CONFIGURATION && condition instanceof ConfigurationCondition && (memberPhase = ((ConfigurationCondition)condition).getConfigurationPhase()) == ConfigurationCondition.ConfigurationPhase.REGISTER_BEAN) {
                throw new IllegalStateException("Nested condition " + nestedClassName + " uses a configuration phase that is inappropriate for " + condition.getClass());
            }
        }

        private AnnotationMetadata getMetadata(String className) {
            try {
                return this.readerFactory.getMetadataReader(className).getAnnotationMetadata();
            }
            catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        }

        private List<String[]> getConditionClasses(AnnotatedTypeMetadata metadata) {
            MultiValueMap attributes = metadata.getAllAnnotationAttributes(Conditional.class.getName(), true);
            List<String[]> values = attributes != null ? attributes.get((Object)"value") : null;
            return values != null ? values : Collections.emptyList();
        }

        private Condition getCondition(String conditionClassName) {
            Class conditionClass = ClassUtils.resolveClassName((String)conditionClassName, (ClassLoader)this.context.getClassLoader());
            return (Condition)BeanUtils.instantiateClass((Class)conditionClass);
        }

        List<ConditionOutcome> getMatchOutcomes() {
            ArrayList outcomes = new ArrayList();
            this.memberConditions.forEach((metadata, conditions) -> outcomes.add(new MemberOutcomes(this.context, (AnnotationMetadata)metadata, (List<Condition>)conditions).getUltimateOutcome()));
            return Collections.unmodifiableList(outcomes);
        }
    }

    protected static class MemberMatchOutcomes {
        private final List<ConditionOutcome> all;
        private final List<ConditionOutcome> matches;
        private final List<ConditionOutcome> nonMatches;

        public MemberMatchOutcomes(MemberConditions memberConditions) {
            this.all = Collections.unmodifiableList(memberConditions.getMatchOutcomes());
            ArrayList<ConditionOutcome> matches = new ArrayList<ConditionOutcome>();
            ArrayList nonMatches = new ArrayList();
            for (ConditionOutcome outcome : this.all) {
                (outcome.isMatch() ? matches : nonMatches).add(outcome);
            }
            this.matches = Collections.unmodifiableList(matches);
            this.nonMatches = Collections.unmodifiableList(nonMatches);
        }

        public List<ConditionOutcome> getAll() {
            return this.all;
        }

        public List<ConditionOutcome> getMatches() {
            return this.matches;
        }

        public List<ConditionOutcome> getNonMatches() {
            return this.nonMatches;
        }
    }
}

