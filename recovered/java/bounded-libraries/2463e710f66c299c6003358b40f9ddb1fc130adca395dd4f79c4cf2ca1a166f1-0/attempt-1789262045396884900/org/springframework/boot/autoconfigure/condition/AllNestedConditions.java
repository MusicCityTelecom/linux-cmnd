/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.ConfigurationCondition$ConfigurationPhase
 */
package org.springframework.boot.autoconfigure.condition;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.AbstractNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.context.annotation.ConfigurationCondition;

public abstract class AllNestedConditions
extends AbstractNestedCondition {
    public AllNestedConditions(ConfigurationCondition.ConfigurationPhase configurationPhase) {
        super(configurationPhase);
    }

    @Override
    protected ConditionOutcome getFinalMatchOutcome(AbstractNestedCondition.MemberMatchOutcomes memberOutcomes) {
        boolean match = this.hasSameSize(memberOutcomes.getMatches(), memberOutcomes.getAll());
        ArrayList<ConditionMessage> messages = new ArrayList<ConditionMessage>();
        messages.add(ConditionMessage.forCondition("AllNestedConditions", new Object[0]).because(memberOutcomes.getMatches().size() + " matched " + memberOutcomes.getNonMatches().size() + " did not"));
        for (ConditionOutcome outcome : memberOutcomes.getAll()) {
            messages.add(outcome.getConditionMessage());
        }
        return new ConditionOutcome(match, ConditionMessage.of(messages));
    }

    private boolean hasSameSize(List<?> list1, List<?> list2) {
        return list1.size() == list2.size();
    }
}

