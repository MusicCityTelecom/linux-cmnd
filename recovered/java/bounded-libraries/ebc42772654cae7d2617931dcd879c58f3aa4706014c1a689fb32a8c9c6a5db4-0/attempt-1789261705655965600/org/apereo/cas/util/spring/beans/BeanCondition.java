/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.CanIgnoreReturnValue
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.core.env.PropertyResolver
 */
package org.apereo.cas.util.spring.beans;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.util.RegexUtils;
import org.apereo.cas.util.ResourceUtils;
import org.apereo.cas.util.spring.SpringExpressionLanguageValueResolver;
import org.springframework.core.env.PropertyResolver;

public interface BeanCondition {
    public static BeanCondition alwaysTrue() {
        return BeanCondition.on("cas.server.name").evenIfMissing();
    }

    public static BeanCondition on(String name) {
        return new PropertyBeanCondition(name);
    }

    public BeanCondition evenIfMissing();

    public BeanCondition withDefaultValue(String var1);

    public BeanCondition havingValue(Serializable var1);

    public BeanCondition exists();

    default public BeanCondition isTrue() {
        return this.havingValue((Serializable)((Object)Boolean.TRUE.toString()));
    }

    default public BeanCondition isFalse() {
        return this.havingValue((Serializable)((Object)Boolean.FALSE.toString()));
    }

    public BeanCondition isUrl();

    public BeanCondition and(String var1);

    public Supplier<Boolean> given(PropertyResolver var1);

    public static class PropertyBeanCondition
    implements BeanCondition {
        private static final Pattern EXPRESSION_PATTERN = RegexUtils.createPattern("\\$\\{.+\\}");
        private final Deque<Condition> conditionList = new ArrayDeque<Condition>();

        PropertyBeanCondition(String name) {
            this.conditionList.push(new Condition(name));
        }

        private static String resolvePropertyValue(PropertyResolver propertyResolver, Condition condition) {
            try {
                String result = propertyResolver.getProperty(condition.getPropertyName(), condition.getDefaultValue());
                return SpringExpressionLanguageValueResolver.getInstance().resolve(result);
            }
            catch (IllegalArgumentException e) {
                String placeholder = StringUtils.substringBetween((String)e.getMessage(), (String)"\"", (String)"\"");
                Matcher matcher = EXPRESSION_PATTERN.matcher(placeholder);
                if (matcher.find()) {
                    String match = matcher.group();
                    String result = SpringExpressionLanguageValueResolver.getInstance().resolve(match);
                    return placeholder.replaceAll(matcher.pattern().pattern(), result);
                }
                return null;
            }
        }

        @Override
        @CanIgnoreReturnValue
        public BeanCondition evenIfMissing() {
            this.conditionList.peek().setMatchIfMissing(true);
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public BeanCondition withDefaultValue(String value) {
            this.conditionList.peek().setDefaultValue(value);
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public BeanCondition havingValue(Serializable value) {
            this.conditionList.peek().setHavingValue(value);
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public BeanCondition exists() {
            this.conditionList.peek().setExists(true);
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public BeanCondition isUrl() {
            this.conditionList.peek().setUrl(true);
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public BeanCondition and(String name) {
            this.conditionList.push(new Condition(name));
            return this;
        }

        @Override
        public Supplier<Boolean> given(PropertyResolver propertyResolver) {
            return () -> this.conditionList.stream().allMatch(condition -> {
                if (condition.isMatchIfMissing() && !propertyResolver.containsProperty(condition.getPropertyName())) {
                    return true;
                }
                String result = PropertyBeanCondition.resolvePropertyValue(propertyResolver, condition);
                if (condition.getHavingValue() != null) {
                    return condition.getHavingValue().toString().equalsIgnoreCase(result);
                }
                if (condition.isUrl() && StringUtils.isNotBlank((CharSequence)result)) {
                    return RegexUtils.find("^https*:\\/\\/.+", result);
                }
                if (condition.isExists()) {
                    return ResourceUtils.doesResourceExist(result);
                }
                return StringUtils.isNotBlank((CharSequence)result);
            });
        }

        @Generated
        public PropertyBeanCondition() {
        }
    }

    public static class Condition {
        private final String propertyName;
        private boolean matchIfMissing;
        private String defaultValue;
        private Serializable havingValue;
        private boolean exists;
        private boolean url;

        @Generated
        public Condition(String propertyName) {
            this.propertyName = propertyName;
        }

        @Generated
        public String getPropertyName() {
            return this.propertyName;
        }

        @Generated
        public boolean isMatchIfMissing() {
            return this.matchIfMissing;
        }

        @Generated
        public String getDefaultValue() {
            return this.defaultValue;
        }

        @Generated
        public Serializable getHavingValue() {
            return this.havingValue;
        }

        @Generated
        public boolean isExists() {
            return this.exists;
        }

        @Generated
        public boolean isUrl() {
            return this.url;
        }

        @Generated
        public void setMatchIfMissing(boolean matchIfMissing) {
            this.matchIfMissing = matchIfMissing;
        }

        @Generated
        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }

        @Generated
        public void setHavingValue(Serializable havingValue) {
            this.havingValue = havingValue;
        }

        @Generated
        public void setExists(boolean exists) {
            this.exists = exists;
        }

        @Generated
        public void setUrl(boolean url) {
            this.url = url;
        }

        @Generated
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Condition)) {
                return false;
            }
            Condition other = (Condition)o;
            if (!other.canEqual(this)) {
                return false;
            }
            if (this.matchIfMissing != other.matchIfMissing) {
                return false;
            }
            if (this.exists != other.exists) {
                return false;
            }
            if (this.url != other.url) {
                return false;
            }
            String this$propertyName = this.propertyName;
            String other$propertyName = other.propertyName;
            if (this$propertyName == null ? other$propertyName != null : !this$propertyName.equals(other$propertyName)) {
                return false;
            }
            String this$defaultValue = this.defaultValue;
            String other$defaultValue = other.defaultValue;
            if (this$defaultValue == null ? other$defaultValue != null : !this$defaultValue.equals(other$defaultValue)) {
                return false;
            }
            Serializable this$havingValue = this.havingValue;
            Serializable other$havingValue = other.havingValue;
            return !(this$havingValue == null ? other$havingValue != null : !this$havingValue.equals(other$havingValue));
        }

        @Generated
        protected boolean canEqual(Object other) {
            return other instanceof Condition;
        }

        @Generated
        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            result = result * 59 + (this.matchIfMissing ? 79 : 97);
            result = result * 59 + (this.exists ? 79 : 97);
            result = result * 59 + (this.url ? 79 : 97);
            String $propertyName = this.propertyName;
            result = result * 59 + ($propertyName == null ? 43 : $propertyName.hashCode());
            String $defaultValue = this.defaultValue;
            result = result * 59 + ($defaultValue == null ? 43 : $defaultValue.hashCode());
            Serializable $havingValue = this.havingValue;
            result = result * 59 + ($havingValue == null ? 43 : $havingValue.hashCode());
            return result;
        }

        @Generated
        public String toString() {
            return "BeanCondition.Condition(propertyName=" + this.propertyName + ", matchIfMissing=" + this.matchIfMissing + ", defaultValue=" + this.defaultValue + ", havingValue=" + this.havingValue + ", exists=" + this.exists + ", url=" + this.url + ")";
        }
    }
}

