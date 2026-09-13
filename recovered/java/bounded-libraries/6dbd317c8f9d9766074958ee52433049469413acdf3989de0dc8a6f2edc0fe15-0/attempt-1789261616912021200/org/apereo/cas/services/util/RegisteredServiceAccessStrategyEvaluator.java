/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.RegexUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.RegexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisteredServiceAccessStrategyEvaluator {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisteredServiceAccessStrategyEvaluator.class);
    private Map<String, Set<String>> requiredAttributes;
    private Map<String, Set<String>> rejectedAttributes;
    private boolean caseInsensitive;
    private boolean requireAllAttributes;

    public boolean evaluate(String principal, Map<String, Object> attributes) {
        if ((this.rejectedAttributes == null || this.rejectedAttributes.isEmpty()) && (this.requiredAttributes == null || this.requiredAttributes.isEmpty())) {
            LOGGER.trace("Skipping access strategy policy, since no attributes rules are defined");
            return true;
        }
        if (!this.enoughAttributesAvailableToProcess(principal, attributes)) {
            LOGGER.debug("Access is denied. There are not enough attributes available to satisfy requirements");
            return false;
        }
        if (this.doRejectedAttributesRefusePrincipalAccess(attributes)) {
            LOGGER.debug("Access is denied. The principal carries attributes that would reject service access");
            return false;
        }
        if (!this.doRequiredAttributesAllowPrincipalAccess(attributes, this.requiredAttributes)) {
            LOGGER.debug("Access is denied. The principal does not have the required attributes [{}]", this.requiredAttributes);
            return false;
        }
        return true;
    }

    protected boolean doRequiredAttributesAllowPrincipalAccess(Map<String, Object> principalAttributes, Map<String, Set<String>> requiredAttributes) {
        LOGGER.debug("These required attributes [{}] are examined against [{}] before service can proceed.", requiredAttributes, principalAttributes);
        return requiredAttributes.isEmpty() || this.requiredAttributesFoundInMap(principalAttributes, requiredAttributes);
    }

    protected boolean doRejectedAttributesRefusePrincipalAccess(Map<String, Object> principalAttributes) {
        LOGGER.debug("These rejected attributes [{}] are examined against [{}] before service can proceed.", this.rejectedAttributes, principalAttributes);
        return !this.rejectedAttributes.isEmpty() && this.requiredAttributesFoundInMap(principalAttributes, this.rejectedAttributes);
    }

    protected boolean enoughAttributesAvailableToProcess(String principal, Map<String, Object> principalAttributes) {
        if (!this.enoughRequiredAttributesAvailableToProcess(principalAttributes, this.requiredAttributes)) {
            return false;
        }
        if (principalAttributes.size() < this.rejectedAttributes.size()) {
            LOGGER.debug("The size of the principal attributes that are [{}] does not match defined rejected attributes, which means the principal is not carrying enough data to grant authorization", principalAttributes);
            return false;
        }
        return true;
    }

    protected boolean enoughRequiredAttributesAvailableToProcess(Map<String, Object> principalAttributes, Map<String, Set<String>> requiredAttributes) {
        if (principalAttributes.isEmpty() && !requiredAttributes.isEmpty()) {
            LOGGER.debug("No principal attributes are found to satisfy defined attribute requirements");
            return false;
        }
        if (principalAttributes.size() < requiredAttributes.size()) {
            LOGGER.debug("The size of the principal attributes that are [{}] does not match defined required attributes, which indicates the principal is not carrying enough data to grant authorization", principalAttributes);
            return false;
        }
        return true;
    }

    protected boolean requiredAttributesFoundInMap(Map<String, Object> principalAttributes, Map<String, Set<String>> requiredAttributes) {
        Set difference = requiredAttributes.keySet().stream().filter(principalAttributes::containsKey).collect(Collectors.toSet());
        LOGGER.debug("Difference of checking required attributes: [{}]", difference);
        if (this.requireAllAttributes && difference.size() < requiredAttributes.size()) {
            return false;
        }
        if (this.requireAllAttributes) {
            return difference.stream().allMatch(key -> this.requiredAttributeFound((String)key, principalAttributes, requiredAttributes));
        }
        return difference.stream().anyMatch(key -> this.requiredAttributeFound((String)key, principalAttributes, requiredAttributes));
    }

    protected boolean requiredAttributeFound(String attributeName, Map<String, Object> principalAttributes, Map<String, Set<String>> requiredAttributes) {
        Set<String> requiredValues = requiredAttributes.get(attributeName);
        Set availableValues = CollectionUtils.toCollection((Object)principalAttributes.get(attributeName));
        Pattern pattern = RegexUtils.concatenate(requiredValues, (boolean)this.caseInsensitive);
        LOGGER.debug("Checking [{}] against [{}] with pattern [{}] for attribute [{}]", new Object[]{requiredValues, availableValues, pattern, attributeName});
        if (!pattern.equals(RegexUtils.MATCH_NOTHING_PATTERN)) {
            return availableValues.stream().map(Object::toString).anyMatch(pattern.asPredicate());
        }
        return availableValues.stream().anyMatch(requiredValues::contains);
    }

    @Generated
    private static Map<String, Set<String>> $default$requiredAttributes() {
        return new HashMap<String, Set<String>>(0);
    }

    @Generated
    private static Map<String, Set<String>> $default$rejectedAttributes() {
        return new HashMap<String, Set<String>>(0);
    }

    @Generated
    private static boolean $default$requireAllAttributes() {
        return true;
    }

    @Generated
    protected RegisteredServiceAccessStrategyEvaluator(RegisteredServiceAccessStrategyEvaluatorBuilder<?, ?> b) {
        this.requiredAttributes = b.requiredAttributes$set ? b.requiredAttributes$value : RegisteredServiceAccessStrategyEvaluator.$default$requiredAttributes();
        this.rejectedAttributes = b.rejectedAttributes$set ? b.rejectedAttributes$value : RegisteredServiceAccessStrategyEvaluator.$default$rejectedAttributes();
        this.caseInsensitive = b.caseInsensitive;
        this.requireAllAttributes = b.requireAllAttributes$set ? b.requireAllAttributes$value : RegisteredServiceAccessStrategyEvaluator.$default$requireAllAttributes();
    }

    @Generated
    public static RegisteredServiceAccessStrategyEvaluatorBuilder<?, ?> builder() {
        return new RegisteredServiceAccessStrategyEvaluatorBuilderImpl();
    }

    @Generated
    public Map<String, Set<String>> getRequiredAttributes() {
        return this.requiredAttributes;
    }

    @Generated
    public Map<String, Set<String>> getRejectedAttributes() {
        return this.rejectedAttributes;
    }

    @Generated
    public boolean isCaseInsensitive() {
        return this.caseInsensitive;
    }

    @Generated
    public boolean isRequireAllAttributes() {
        return this.requireAllAttributes;
    }

    @Generated
    private static final class RegisteredServiceAccessStrategyEvaluatorBuilderImpl
    extends RegisteredServiceAccessStrategyEvaluatorBuilder<RegisteredServiceAccessStrategyEvaluator, RegisteredServiceAccessStrategyEvaluatorBuilderImpl> {
        @Generated
        private RegisteredServiceAccessStrategyEvaluatorBuilderImpl() {
        }

        @Override
        @Generated
        protected RegisteredServiceAccessStrategyEvaluatorBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public RegisteredServiceAccessStrategyEvaluator build() {
            return new RegisteredServiceAccessStrategyEvaluator(this);
        }
    }

    @Generated
    public static abstract class RegisteredServiceAccessStrategyEvaluatorBuilder<C extends RegisteredServiceAccessStrategyEvaluator, B extends RegisteredServiceAccessStrategyEvaluatorBuilder<C, B>> {
        @Generated
        private boolean requiredAttributes$set;
        @Generated
        private Map<String, Set<String>> requiredAttributes$value;
        @Generated
        private boolean rejectedAttributes$set;
        @Generated
        private Map<String, Set<String>> rejectedAttributes$value;
        @Generated
        private boolean caseInsensitive;
        @Generated
        private boolean requireAllAttributes$set;
        @Generated
        private boolean requireAllAttributes$value;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B requiredAttributes(Map<String, Set<String>> requiredAttributes) {
            this.requiredAttributes$value = requiredAttributes;
            this.requiredAttributes$set = true;
            return this.self();
        }

        @Generated
        public B rejectedAttributes(Map<String, Set<String>> rejectedAttributes) {
            this.rejectedAttributes$value = rejectedAttributes;
            this.rejectedAttributes$set = true;
            return this.self();
        }

        @Generated
        public B caseInsensitive(boolean caseInsensitive) {
            this.caseInsensitive = caseInsensitive;
            return this.self();
        }

        @Generated
        public B requireAllAttributes(boolean requireAllAttributes) {
            this.requireAllAttributes$value = requireAllAttributes;
            this.requireAllAttributes$set = true;
            return this.self();
        }

        @Generated
        public String toString() {
            return "RegisteredServiceAccessStrategyEvaluator.RegisteredServiceAccessStrategyEvaluatorBuilder(requiredAttributes$value=" + this.requiredAttributes$value + ", rejectedAttributes$value=" + this.rejectedAttributes$value + ", caseInsensitive=" + this.caseInsensitive + ", requireAllAttributes$value=" + this.requireAllAttributes$value + ")";
        }
    }
}

