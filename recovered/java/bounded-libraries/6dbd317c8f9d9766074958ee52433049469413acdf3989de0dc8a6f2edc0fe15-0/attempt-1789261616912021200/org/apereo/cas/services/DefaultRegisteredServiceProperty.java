/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  javax.persistence.Column
 *  javax.persistence.Embeddable
 *  javax.persistence.Lob
 *  javax.persistence.Table
 *  lombok.Generated
 *  org.apereo.cas.configuration.support.ExpressionLanguageCapable
 *  org.apereo.cas.services.RegisteredServiceProperty
 *  org.apereo.cas.util.spring.SpringExpressionLanguageValueResolver
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.Generated;
import org.apereo.cas.configuration.support.ExpressionLanguageCapable;
import org.apereo.cas.services.RegisteredServiceProperty;
import org.apereo.cas.util.spring.SpringExpressionLanguageValueResolver;

@Embeddable
@Table(name="RegexRegisteredServiceProperty")
@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class DefaultRegisteredServiceProperty
implements RegisteredServiceProperty {
    public static final String TABLE_NAME = "RegexRegisteredServiceProperty";
    private static final long serialVersionUID = 1349556364689133211L;
    @Lob
    @Column(name="property_values")
    @ExpressionLanguageCapable
    private HashSet<String> values = new HashSet(0);

    public DefaultRegisteredServiceProperty(String ... propertyValues) {
        this.setValues(Arrays.stream(propertyValues).collect(Collectors.toSet()));
    }

    public DefaultRegisteredServiceProperty(Collection<String> propertyValues) {
        this.setValues(new HashSet<String>(propertyValues));
    }

    public Set<String> getValues() {
        if (this.values == null) {
            this.values = new HashSet(0);
        }
        return this.values.stream().map(value -> SpringExpressionLanguageValueResolver.getInstance().resolve(value)).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public void setValues(Set<String> values) {
        this.values.clear();
        if (values == null) {
            return;
        }
        this.values.addAll(values);
    }

    @JsonIgnore
    public String getValue() {
        if (this.values.isEmpty()) {
            return null;
        }
        return SpringExpressionLanguageValueResolver.getInstance().resolve(this.values.iterator().next());
    }

    public boolean contains(String value) {
        return this.getValues().contains(value);
    }

    public void addValue(String value) {
        this.values.add(value);
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DefaultRegisteredServiceProperty)) {
            return false;
        }
        DefaultRegisteredServiceProperty other = (DefaultRegisteredServiceProperty)o;
        if (!other.canEqual(this)) {
            return false;
        }
        HashSet<String> this$values = this.values;
        HashSet<String> other$values = other.values;
        return !(this$values == null ? other$values != null : !((Object)this$values).equals(other$values));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof DefaultRegisteredServiceProperty;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        HashSet<String> $values = this.values;
        result = result * 59 + ($values == null ? 43 : ((Object)$values).hashCode());
        return result;
    }

    @Generated
    public String toString() {
        return "DefaultRegisteredServiceProperty(values=" + this.values + ")";
    }

    @Generated
    public DefaultRegisteredServiceProperty() {
    }
}

