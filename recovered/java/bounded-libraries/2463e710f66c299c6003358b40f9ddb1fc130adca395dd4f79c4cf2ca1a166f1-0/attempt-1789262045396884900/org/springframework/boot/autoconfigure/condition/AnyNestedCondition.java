/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.ConfigurationCondition$ConfigurationPhase
 *  org.springframework.core.annotation.Order
 */
package org.springframework.boot.autoconfigure.condition;

import java.util.ArrayList;
import org.springframework.boot.autoconfigure.condition.AbstractNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.core.annotation.Order;

@Order(value=2147483627)
public abstract class AnyNestedCondition
extends AbstractNestedCondition {
    public AnyNestedCondition(ConfigurationCondition.ConfigurationPhase configurationPhase) {
        super(configurationPhase);
    }

    @Override
    protected ConditionOutcome getFinalMatchOutcome(AbstractNestedCondition.MemberMatchOutcomes memberOutcomes) {
        boolean match = !memberOutcomes.getMatches().isEmpty();
        ArrayList<ConditionMessage> messages = new ArrayList<ConditionMessage>();
        messages.add(ConditionMessage.forCondition("AnyNestedCondition", new Object[0]).because(memberOutcomes.getMatches().size() + " matched " + memberOutcomes.getNonMatches().size() + " did not"));
        for (ConditionOutcome outcome : memberOutcomes.getAll()) {
            messages.add(outcome.getConditionMessage());
        }
        return new ConditionOutcome(match, ConditionMessage.of(messages));
    }
}

