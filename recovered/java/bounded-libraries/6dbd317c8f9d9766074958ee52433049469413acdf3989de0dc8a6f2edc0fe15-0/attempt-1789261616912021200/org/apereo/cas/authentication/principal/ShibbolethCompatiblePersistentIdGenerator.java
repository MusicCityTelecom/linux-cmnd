/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apereo.cas.authentication.principal.PersistentIdGenerator
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.EncodingUtils
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.apereo.cas.util.gen.DefaultRandomStringGenerator
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.principal;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apereo.cas.authentication.principal.PersistentIdGenerator;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.EncodingUtils;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.gen.DefaultRandomStringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShibbolethCompatiblePersistentIdGenerator
implements PersistentIdGenerator {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(ShibbolethCompatiblePersistentIdGenerator.class);
    private static final long serialVersionUID = 6182838799563190289L;
    private static final byte CONST_SEPARATOR = 33;
    private static final int CONST_DEFAULT_SALT_COUNT = 16;
    private static final int CONST_SALT_ABBREV_LENGTH = 4;
    @JsonProperty
    private String salt;
    @JsonProperty
    private String attribute;

    public ShibbolethCompatiblePersistentIdGenerator(String salt) {
        this.salt = salt;
    }

    public String generate(String principal, String service) {
        if (StringUtils.isBlank((CharSequence)this.salt)) {
            this.salt = new DefaultRandomStringGenerator(16L).getNewString();
        }
        LOGGER.debug("Using principal [{}] to generate anonymous identifier for service [{}]", (Object)principal, (Object)service);
        MessageDigest md = ShibbolethCompatiblePersistentIdGenerator.prepareMessageDigest(principal, service);
        String result = this.digestAndEncodeWithSalt(md);
        LOGGER.debug("Generated persistent id for [{}] is [{}]", (Object)service, (Object)result);
        return result;
    }

    public String generate(Principal principal, String service) {
        Map attributes = principal.getAttributes();
        LOGGER.debug("Found principal attributes [{}] to use when generating persistent identifiers", (Object)attributes);
        String principalId = this.determinePrincipalIdFromAttributes(principal.getId(), attributes);
        return this.generate(principalId, service);
    }

    public String determinePrincipalIdFromAttributes(String defaultId, Map<String, List<Object>> attributes) {
        return (String)FunctionUtils.doIf((StringUtils.isNotBlank((CharSequence)this.attribute) && attributes.containsKey(this.attribute) ? 1 : 0) != 0, () -> {
            List attributeValue = (List)attributes.get(this.attribute);
            LOGGER.debug("Using attribute [{}] to establish principal id", (Object)this.attribute);
            Optional element = CollectionUtils.firstElement((Object)attributeValue);
            return element.map(Object::toString).orElse(null);
        }, () -> {
            LOGGER.debug("Using principal id [{}] to generate persistent identifier", (Object)defaultId);
            return defaultId;
        }).get();
    }

    public String toString() {
        return new ToStringBuilder((Object)this).append("attribute", (Object)this.attribute).append("salt", (Object)StringUtils.abbreviate((String)this.salt, (int)4)).toString();
    }

    protected String digestAndEncodeWithSalt(MessageDigest md) {
        String sanitizedSalt = StringUtils.replace((String)this.salt, (String)"\n", (String)" ");
        byte[] digested = md.digest(sanitizedSalt.getBytes(StandardCharsets.UTF_8));
        return EncodingUtils.encodeBase64((byte[])digested, (boolean)false);
    }

    protected static MessageDigest prepareMessageDigest(String principal, String service) {
        return (MessageDigest)FunctionUtils.doUnchecked(() -> {
            MessageDigest md = MessageDigest.getInstance("SHA");
            if (StringUtils.isNotBlank((CharSequence)service)) {
                md.update(service.getBytes(StandardCharsets.UTF_8));
                md.update((byte)33);
            }
            md.update(principal.getBytes(StandardCharsets.UTF_8));
            md.update((byte)33);
            return md;
        });
    }

    @Generated
    public String getSalt() {
        return this.salt;
    }

    @Generated
    public String getAttribute() {
        return this.attribute;
    }

    @JsonProperty
    @Generated
    public void setSalt(String salt) {
        this.salt = salt;
    }

    @JsonProperty
    @Generated
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    @Generated
    public ShibbolethCompatiblePersistentIdGenerator() {
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ShibbolethCompatiblePersistentIdGenerator)) {
            return false;
        }
        ShibbolethCompatiblePersistentIdGenerator other = (ShibbolethCompatiblePersistentIdGenerator)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$salt = this.salt;
        String other$salt = other.salt;
        if (this$salt == null ? other$salt != null : !this$salt.equals(other$salt)) {
            return false;
        }
        String this$attribute = this.attribute;
        String other$attribute = other.attribute;
        return !(this$attribute == null ? other$attribute != null : !this$attribute.equals(other$attribute));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof ShibbolethCompatiblePersistentIdGenerator;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $salt = this.salt;
        result = result * 59 + ($salt == null ? 43 : $salt.hashCode());
        String $attribute = this.attribute;
        result = result * 59 + ($attribute == null ? 43 : $attribute.hashCode());
        return result;
    }
}

