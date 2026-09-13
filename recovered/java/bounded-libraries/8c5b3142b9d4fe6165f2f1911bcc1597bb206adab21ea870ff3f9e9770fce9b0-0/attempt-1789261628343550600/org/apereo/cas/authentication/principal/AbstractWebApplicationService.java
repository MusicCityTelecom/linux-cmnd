/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  javax.persistence.Column
 *  javax.persistence.DiscriminatorColumn
 *  javax.persistence.DiscriminatorType
 *  javax.persistence.Entity
 *  javax.persistence.Id
 *  javax.persistence.Inheritance
 *  javax.persistence.Lob
 *  javax.persistence.Table
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.validation.ValidationResponseType
 */
package org.apereo.cas.authentication.principal;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.Generated;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.validation.ValidationResponseType;

@Entity
@Inheritance
@DiscriminatorColumn(name="service_type", length=50, discriminatorType=DiscriminatorType.STRING, columnDefinition="VARCHAR(50) DEFAULT 'simple'")
@Table(name="WebApplicationServices")
public abstract class AbstractWebApplicationService
implements WebApplicationService {
    private static final long serialVersionUID = 610105280927740076L;
    @Id
    @JsonProperty
    @Column
    private String id;
    @JsonProperty
    @Column(nullable=false)
    private String originalUrl;
    @Column
    private String artifactId;
    @JsonProperty
    @Column
    private String principal;
    @JsonProperty
    @Column
    private String source;
    @Column
    private boolean loggedOutAlready;
    @Column
    private ValidationResponseType format = ValidationResponseType.XML;
    @Column
    @Lob
    private Map<String, List<Object>> attributes = new HashMap<String, List<Object>>(0);

    protected AbstractWebApplicationService(String id, String originalUrl, String artifactId) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.artifactId = artifactId;
    }

    @Generated
    public String toString() {
        return "AbstractWebApplicationService(id=" + this.id + ", originalUrl=" + this.originalUrl + ", artifactId=" + this.artifactId + ", principal=" + this.principal + ", source=" + this.source + ", loggedOutAlready=" + this.loggedOutAlready + ", format=" + this.format + ", attributes=" + this.attributes + ")";
    }

    @Generated
    public String getId() {
        return this.id;
    }

    @Generated
    public String getOriginalUrl() {
        return this.originalUrl;
    }

    @Generated
    public String getArtifactId() {
        return this.artifactId;
    }

    @Generated
    public String getPrincipal() {
        return this.principal;
    }

    @Generated
    public String getSource() {
        return this.source;
    }

    @Generated
    public boolean isLoggedOutAlready() {
        return this.loggedOutAlready;
    }

    @Generated
    public ValidationResponseType getFormat() {
        return this.format;
    }

    @Generated
    public Map<String, List<Object>> getAttributes() {
        return this.attributes;
    }

    @JsonProperty
    @Generated
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty
    @Generated
    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    @Generated
    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    @JsonProperty
    @Generated
    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    @JsonProperty
    @Generated
    public void setSource(String source) {
        this.source = source;
    }

    @Generated
    public void setLoggedOutAlready(boolean loggedOutAlready) {
        this.loggedOutAlready = loggedOutAlready;
    }

    @Generated
    public void setFormat(ValidationResponseType format) {
        this.format = format;
    }

    @Generated
    public void setAttributes(Map<String, List<Object>> attributes) {
        this.attributes = attributes;
    }

    @Generated
    protected AbstractWebApplicationService() {
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AbstractWebApplicationService)) {
            return false;
        }
        AbstractWebApplicationService other = (AbstractWebApplicationService)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.loggedOutAlready != other.loggedOutAlready) {
            return false;
        }
        String this$id = this.id;
        String other$id = other.id;
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        String this$originalUrl = this.originalUrl;
        String other$originalUrl = other.originalUrl;
        if (this$originalUrl == null ? other$originalUrl != null : !this$originalUrl.equals(other$originalUrl)) {
            return false;
        }
        String this$artifactId = this.artifactId;
        String other$artifactId = other.artifactId;
        if (this$artifactId == null ? other$artifactId != null : !this$artifactId.equals(other$artifactId)) {
            return false;
        }
        String this$principal = this.principal;
        String other$principal = other.principal;
        if (this$principal == null ? other$principal != null : !this$principal.equals(other$principal)) {
            return false;
        }
        String this$source = this.source;
        String other$source = other.source;
        if (this$source == null ? other$source != null : !this$source.equals(other$source)) {
            return false;
        }
        ValidationResponseType this$format = this.format;
        ValidationResponseType other$format = other.format;
        if (this$format == null ? other$format != null : !this$format.equals(other$format)) {
            return false;
        }
        Map<String, List<Object>> this$attributes = this.attributes;
        Map<String, List<Object>> other$attributes = other.attributes;
        return !(this$attributes == null ? other$attributes != null : !((Object)this$attributes).equals(other$attributes));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof AbstractWebApplicationService;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.loggedOutAlready ? 79 : 97);
        String $id = this.id;
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        String $originalUrl = this.originalUrl;
        result = result * 59 + ($originalUrl == null ? 43 : $originalUrl.hashCode());
        String $artifactId = this.artifactId;
        result = result * 59 + ($artifactId == null ? 43 : $artifactId.hashCode());
        String $principal = this.principal;
        result = result * 59 + ($principal == null ? 43 : $principal.hashCode());
        String $source = this.source;
        result = result * 59 + ($source == null ? 43 : $source.hashCode());
        ValidationResponseType $format = this.format;
        result = result * 59 + ($format == null ? 43 : $format.hashCode());
        Map<String, List<Object>> $attributes = this.attributes;
        result = result * 59 + ($attributes == null ? 43 : ((Object)$attributes).hashCode());
        return result;
    }
}

