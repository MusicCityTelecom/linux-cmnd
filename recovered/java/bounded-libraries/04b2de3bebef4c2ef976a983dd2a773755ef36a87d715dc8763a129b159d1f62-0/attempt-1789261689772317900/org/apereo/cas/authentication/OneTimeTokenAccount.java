/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  javax.persistence.CollectionTable
 *  javax.persistence.Column
 *  javax.persistence.ElementCollection
 *  javax.persistence.Id
 *  javax.persistence.JoinColumn
 *  javax.persistence.MappedSuperclass
 *  javax.persistence.Transient
 *  lombok.Generated
 *  org.apache.commons.lang3.builder.CompareToBuilder
 *  org.jooq.lambda.Unchecked
 */
package org.apereo.cas.authentication;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import lombok.Generated;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.jooq.lambda.Unchecked;

@MappedSuperclass
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class OneTimeTokenAccount
implements Serializable,
Comparable<OneTimeTokenAccount>,
Cloneable {
    public static final String TABLE_NAME_SCRATCH_CODES = "scratch_codes";
    private static final long serialVersionUID = -8289105320642735252L;
    @Id
    @Transient
    @JsonProperty(value="id")
    private long id;
    @Column(nullable=false, length=2048)
    @JsonProperty(value="secretKey")
    private String secretKey;
    @Column(nullable=false)
    @JsonProperty(value="validationCode")
    private int validationCode;
    @ElementCollection(targetClass=BigInteger.class)
    @CollectionTable(name="scratch_codes", joinColumns={@JoinColumn(name="id")})
    @Column(nullable=false, columnDefinition="numeric", precision=255, scale=0)
    private List<Number> scratchCodes;
    @Column(nullable=false)
    @JsonProperty(value="username")
    private String username;
    @Column(nullable=false)
    @JsonProperty(value="name")
    private String name;
    @Column
    @JsonProperty(value="registrationDate")
    private ZonedDateTime registrationDate;
    @Column
    @JsonProperty(value="lastUsedDateTime")
    private String lastUsedDateTime;

    @Override
    public int compareTo(OneTimeTokenAccount o) {
        return new CompareToBuilder().append(this.scratchCodes.toArray(), o.getScratchCodes().toArray()).append(this.validationCode, o.getValidationCode()).append((Object)this.secretKey, (Object)o.getSecretKey()).append((Object)this.username, (Object)o.getUsername()).append((Object)this.name, (Object)o.getName()).append((Object)this.lastUsedDateTime, (Object)o.getLastUsedDateTime()).build();
    }

    public OneTimeTokenAccount clone() {
        return (OneTimeTokenAccount)Unchecked.supplier(() -> (OneTimeTokenAccount)super.clone()).get();
    }

    @Generated
    private static long $default$id() {
        return System.currentTimeMillis();
    }

    @Generated
    private static List<Number> $default$scratchCodes() {
        return new ArrayList<Number>(0);
    }

    @Generated
    private static ZonedDateTime $default$registrationDate() {
        return ZonedDateTime.now(ZoneOffset.UTC);
    }

    @Generated
    protected OneTimeTokenAccount(OneTimeTokenAccountBuilder<?, ?> b) {
        this.id = b.id$set ? b.id$value : OneTimeTokenAccount.$default$id();
        this.secretKey = b.secretKey;
        this.validationCode = b.validationCode;
        this.scratchCodes = b.scratchCodes$set ? b.scratchCodes$value : OneTimeTokenAccount.$default$scratchCodes();
        this.username = b.username;
        this.name = b.name;
        this.registrationDate = b.registrationDate$set ? b.registrationDate$value : OneTimeTokenAccount.$default$registrationDate();
        this.lastUsedDateTime = b.lastUsedDateTime;
    }

    @Generated
    public static OneTimeTokenAccountBuilder<?, ?> builder() {
        return new OneTimeTokenAccountBuilderImpl();
    }

    @Generated
    public String toString() {
        return "OneTimeTokenAccount(id=" + this.id + ", validationCode=" + this.validationCode + ", username=" + this.username + ", name=" + this.name + ", registrationDate=" + this.registrationDate + ", lastUsedDateTime=" + this.lastUsedDateTime + ")";
    }

    @Generated
    public long getId() {
        return this.id;
    }

    @Generated
    public String getSecretKey() {
        return this.secretKey;
    }

    @Generated
    public int getValidationCode() {
        return this.validationCode;
    }

    @Generated
    public List<Number> getScratchCodes() {
        return this.scratchCodes;
    }

    @Generated
    public String getUsername() {
        return this.username;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public ZonedDateTime getRegistrationDate() {
        return this.registrationDate;
    }

    @Generated
    public String getLastUsedDateTime() {
        return this.lastUsedDateTime;
    }

    @JsonProperty(value="id")
    @Generated
    public void setId(long id) {
        this.id = id;
    }

    @JsonProperty(value="secretKey")
    @Generated
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @JsonProperty(value="validationCode")
    @Generated
    public void setValidationCode(int validationCode) {
        this.validationCode = validationCode;
    }

    @Generated
    public void setScratchCodes(List<Number> scratchCodes) {
        this.scratchCodes = scratchCodes;
    }

    @JsonProperty(value="username")
    @Generated
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty(value="name")
    @Generated
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty(value="registrationDate")
    @Generated
    public void setRegistrationDate(ZonedDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @JsonProperty(value="lastUsedDateTime")
    @Generated
    public void setLastUsedDateTime(String lastUsedDateTime) {
        this.lastUsedDateTime = lastUsedDateTime;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OneTimeTokenAccount)) {
            return false;
        }
        OneTimeTokenAccount other = (OneTimeTokenAccount)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        if (this.validationCode != other.validationCode) {
            return false;
        }
        String this$secretKey = this.secretKey;
        String other$secretKey = other.secretKey;
        if (this$secretKey == null ? other$secretKey != null : !this$secretKey.equals(other$secretKey)) {
            return false;
        }
        List<Number> this$scratchCodes = this.scratchCodes;
        List<Number> other$scratchCodes = other.scratchCodes;
        if (this$scratchCodes == null ? other$scratchCodes != null : !((Object)this$scratchCodes).equals(other$scratchCodes)) {
            return false;
        }
        String this$username = this.username;
        String other$username = other.username;
        if (this$username == null ? other$username != null : !this$username.equals(other$username)) {
            return false;
        }
        String this$name = this.name;
        String other$name = other.name;
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        ZonedDateTime this$registrationDate = this.registrationDate;
        ZonedDateTime other$registrationDate = other.registrationDate;
        if (this$registrationDate == null ? other$registrationDate != null : !((Object)this$registrationDate).equals(other$registrationDate)) {
            return false;
        }
        String this$lastUsedDateTime = this.lastUsedDateTime;
        String other$lastUsedDateTime = other.lastUsedDateTime;
        return !(this$lastUsedDateTime == null ? other$lastUsedDateTime != null : !this$lastUsedDateTime.equals(other$lastUsedDateTime));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof OneTimeTokenAccount;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $id = this.id;
        result = result * 59 + (int)($id >>> 32 ^ $id);
        result = result * 59 + this.validationCode;
        String $secretKey = this.secretKey;
        result = result * 59 + ($secretKey == null ? 43 : $secretKey.hashCode());
        List<Number> $scratchCodes = this.scratchCodes;
        result = result * 59 + ($scratchCodes == null ? 43 : ((Object)$scratchCodes).hashCode());
        String $username = this.username;
        result = result * 59 + ($username == null ? 43 : $username.hashCode());
        String $name = this.name;
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        ZonedDateTime $registrationDate = this.registrationDate;
        result = result * 59 + ($registrationDate == null ? 43 : ((Object)$registrationDate).hashCode());
        String $lastUsedDateTime = this.lastUsedDateTime;
        result = result * 59 + ($lastUsedDateTime == null ? 43 : $lastUsedDateTime.hashCode());
        return result;
    }

    @Generated
    public OneTimeTokenAccount() {
        this.id = OneTimeTokenAccount.$default$id();
        this.scratchCodes = OneTimeTokenAccount.$default$scratchCodes();
        this.registrationDate = OneTimeTokenAccount.$default$registrationDate();
    }

    @Generated
    private static final class OneTimeTokenAccountBuilderImpl
    extends OneTimeTokenAccountBuilder<OneTimeTokenAccount, OneTimeTokenAccountBuilderImpl> {
        @Generated
        private OneTimeTokenAccountBuilderImpl() {
        }

        @Override
        @Generated
        protected OneTimeTokenAccountBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public OneTimeTokenAccount build() {
            return new OneTimeTokenAccount(this);
        }
    }

    @Generated
    public static abstract class OneTimeTokenAccountBuilder<C extends OneTimeTokenAccount, B extends OneTimeTokenAccountBuilder<C, B>> {
        @Generated
        private boolean id$set;
        @Generated
        private long id$value;
        @Generated
        private String secretKey;
        @Generated
        private int validationCode;
        @Generated
        private boolean scratchCodes$set;
        @Generated
        private List<Number> scratchCodes$value;
        @Generated
        private String username;
        @Generated
        private String name;
        @Generated
        private boolean registrationDate$set;
        @Generated
        private ZonedDateTime registrationDate$value;
        @Generated
        private String lastUsedDateTime;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @JsonProperty(value="id")
        @Generated
        public B id(long id) {
            this.id$value = id;
            this.id$set = true;
            return this.self();
        }

        @JsonProperty(value="secretKey")
        @Generated
        public B secretKey(String secretKey) {
            this.secretKey = secretKey;
            return this.self();
        }

        @JsonProperty(value="validationCode")
        @Generated
        public B validationCode(int validationCode) {
            this.validationCode = validationCode;
            return this.self();
        }

        @Generated
        public B scratchCodes(List<Number> scratchCodes) {
            this.scratchCodes$value = scratchCodes;
            this.scratchCodes$set = true;
            return this.self();
        }

        @JsonProperty(value="username")
        @Generated
        public B username(String username) {
            this.username = username;
            return this.self();
        }

        @JsonProperty(value="name")
        @Generated
        public B name(String name) {
            this.name = name;
            return this.self();
        }

        @JsonProperty(value="registrationDate")
        @Generated
        public B registrationDate(ZonedDateTime registrationDate) {
            this.registrationDate$value = registrationDate;
            this.registrationDate$set = true;
            return this.self();
        }

        @JsonProperty(value="lastUsedDateTime")
        @Generated
        public B lastUsedDateTime(String lastUsedDateTime) {
            this.lastUsedDateTime = lastUsedDateTime;
            return this.self();
        }

        @Generated
        public String toString() {
            return "OneTimeTokenAccount.OneTimeTokenAccountBuilder(id$value=" + this.id$value + ", secretKey=" + this.secretKey + ", validationCode=" + this.validationCode + ", scratchCodes$value=" + this.scratchCodes$value + ", username=" + this.username + ", name=" + this.name + ", registrationDate$value=" + this.registrationDate$value + ", lastUsedDateTime=" + this.lastUsedDateTime + ")";
        }
    }
}

