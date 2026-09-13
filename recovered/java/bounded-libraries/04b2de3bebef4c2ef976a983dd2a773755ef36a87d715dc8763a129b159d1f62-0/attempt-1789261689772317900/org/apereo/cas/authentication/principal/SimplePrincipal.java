/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.fasterxml.jackson.annotation.JsonSetter
 *  com.fasterxml.jackson.annotation.Nulls
 *  lombok.Generated
 *  lombok.NonNull
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.builder.HashCodeBuilder
 *  org.apereo.cas.authentication.principal.Principal
 */
package org.apereo.cas.authentication.principal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.Generated;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apereo.cas.authentication.principal.Principal;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SimplePrincipal
implements Principal {
    private static final long serialVersionUID = -1255260750151385796L;
    @JsonProperty
    private String id;
    @JsonSetter(nulls=Nulls.AS_EMPTY)
    private Map<String, List<Object>> attributes = new TreeMap<String, List<Object>>(String.CASE_INSENSITIVE_ORDER);

    @JsonCreator
    protected SimplePrincipal(@JsonProperty(value="id") @NonNull String id, @JsonProperty(value="attributes") Map<String, List<Object>> attributes) {
        if (id == null) {
            throw new NullPointerException("id is marked non-null but is null");
        }
        this.id = id;
        this.attributes = new TreeMap<String, List<Object>>(String.CASE_INSENSITIVE_ORDER);
        this.attributes.putAll(attributes);
    }

    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder(83, 31);
        builder.append((Object)this.id.toLowerCase());
        return builder.toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SimplePrincipal)) {
            return false;
        }
        SimplePrincipal rhs = (SimplePrincipal)obj;
        return StringUtils.equalsIgnoreCase((CharSequence)this.id, (CharSequence)rhs.getId());
    }

    @Generated
    public String toString() {
        return "SimplePrincipal(id=" + this.id + ", attributes=" + this.attributes + ")";
    }

    @Generated
    public String getId() {
        return this.id;
    }

    @Generated
    public Map<String, List<Object>> getAttributes() {
        return this.attributes;
    }

    @Generated
    public SimplePrincipal() {
    }
}

