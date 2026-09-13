/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.condition;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public final class ConditionMessage {
    private final String message;

    private ConditionMessage() {
        this(null);
    }

    private ConditionMessage(String message) {
        this.message = message;
    }

    private ConditionMessage(ConditionMessage prior, String message) {
        this.message = prior.isEmpty() ? message : prior + "; " + message;
    }

    public boolean isEmpty() {
        return !StringUtils.hasLength((String)this.message);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ConditionMessage)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        return ObjectUtils.nullSafeEquals((Object)((ConditionMessage)obj).message, (Object)this.message);
    }

    public int hashCode() {
        return ObjectUtils.nullSafeHashCode((Object)this.message);
    }

    public String toString() {
        return this.message != null ? this.message : "";
    }

    public ConditionMessage append(String message) {
        if (!StringUtils.hasLength((String)message)) {
            return this;
        }
        if (!StringUtils.hasLength((String)this.message)) {
            return new ConditionMessage(message);
        }
        return new ConditionMessage(this.message + " " + message);
    }

    public Builder andCondition(Class<? extends Annotation> condition, Object ... details) {
        Assert.notNull(condition, (String)"Condition must not be null");
        return this.andCondition("@" + ClassUtils.getShortName(condition), details);
    }

    public Builder andCondition(String condition, Object ... details) {
        Assert.notNull((Object)condition, (String)"Condition must not be null");
        String detail = StringUtils.arrayToDelimitedString((Object[])details, (String)" ");
        if (StringUtils.hasLength((String)detail)) {
            return new Builder(condition + " " + detail);
        }
        return new Builder(condition);
    }

    public static ConditionMessage empty() {
        return new ConditionMessage();
    }

    public static ConditionMessage of(String message, Object ... args) {
        if (ObjectUtils.isEmpty((Object[])args)) {
            return new ConditionMessage(message);
        }
        return new ConditionMessage(String.format(message, args));
    }

    public static ConditionMessage of(Collection<? extends ConditionMessage> messages) {
        ConditionMessage result = new ConditionMessage();
        if (messages != null) {
            for (ConditionMessage conditionMessage : messages) {
                result = new ConditionMessage(result, conditionMessage.toString());
            }
        }
        return result;
    }

    public static Builder forCondition(Class<? extends Annotation> condition, Object ... details) {
        return new ConditionMessage().andCondition(condition, details);
    }

    public static Builder forCondition(String condition, Object ... details) {
        return new ConditionMessage().andCondition(condition, details);
    }

    public static enum Style {
        NORMAL{

            @Override
            protected Object applyToItem(Object item) {
                return item;
            }
        }
        ,
        QUOTE{

            @Override
            protected String applyToItem(Object item) {
                return item != null ? "'" + item + "'" : null;
            }
        };


        public Collection<?> applyTo(Collection<?> items) {
            if (items == null) {
                return null;
            }
            ArrayList<Object> result = new ArrayList<Object>(items.size());
            for (Object item : items) {
                result.add(this.applyToItem(item));
            }
            return result;
        }

        protected abstract Object applyToItem(Object var1);
    }

    public final class ItemsBuilder {
        private final Builder condition;
        private final String reason;
        private final String singular;
        private final String plural;

        private ItemsBuilder(Builder condition, String reason, String singular, String plural) {
            this.condition = condition;
            this.reason = reason;
            this.singular = singular;
            this.plural = plural;
        }

        public ConditionMessage atAll() {
            return this.items(Collections.emptyList());
        }

        public ConditionMessage items(Object ... items) {
            return this.items(Style.NORMAL, items);
        }

        public ConditionMessage items(Style style, Object ... items) {
            return this.items(style, items != null ? Arrays.asList(items) : null);
        }

        public ConditionMessage items(Collection<?> items) {
            return this.items(Style.NORMAL, items);
        }

        public ConditionMessage items(Style style, Collection<?> items) {
            Assert.notNull((Object)((Object)style), (String)"Style must not be null");
            StringBuilder message = new StringBuilder(this.reason);
            items = style.applyTo(items);
            if ((this.condition == null || items == null || items.size() <= 1) && StringUtils.hasLength((String)this.singular)) {
                message.append(" ").append(this.singular);
            } else if (StringUtils.hasLength((String)this.plural)) {
                message.append(" ").append(this.plural);
            }
            if (items != null && !items.isEmpty()) {
                message.append(" ").append(StringUtils.collectionToDelimitedString(items, (String)", "));
            }
            return this.condition.because(message.toString());
        }
    }

    public final class Builder {
        private final String condition;

        private Builder(String condition) {
            this.condition = condition;
        }

        public ConditionMessage foundExactly(Object result) {
            return this.found("").items(result);
        }

        public ItemsBuilder found(String article) {
            return this.found(article, article);
        }

        public ItemsBuilder found(String singular, String plural) {
            return new ItemsBuilder(this, "found", singular, plural);
        }

        public ItemsBuilder didNotFind(String article) {
            return this.didNotFind(article, article);
        }

        public ItemsBuilder didNotFind(String singular, String plural) {
            return new ItemsBuilder(this, "did not find", singular, plural);
        }

        public ConditionMessage resultedIn(Object result) {
            return this.because("resulted in " + result);
        }

        public ConditionMessage available(String item) {
            return this.because(item + " is available");
        }

        public ConditionMessage notAvailable(String item) {
            return this.because(item + " is not available");
        }

        public ConditionMessage because(String reason) {
            if (StringUtils.hasLength((String)reason)) {
                return new ConditionMessage(ConditionMessage.this, StringUtils.hasLength((String)this.condition) ? this.condition + " " + reason : reason);
            }
            return new ConditionMessage(ConditionMessage.this, this.condition);
        }
    }
}

