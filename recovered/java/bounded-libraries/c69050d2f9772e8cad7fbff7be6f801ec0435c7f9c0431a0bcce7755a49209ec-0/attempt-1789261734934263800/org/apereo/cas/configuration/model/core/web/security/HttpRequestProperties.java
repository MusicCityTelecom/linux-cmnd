/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.web.security;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.web.security.HttpCorsRequestProperties;
import org.apereo.cas.configuration.model.core.web.security.HttpHeadersRequestProperties;
import org.apereo.cas.configuration.model.core.web.security.HttpWebRequestProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-web", automated=true)
@JsonFilter(value="HttpRequestProperties")
public class HttpRequestProperties
implements Serializable {
    private static final long serialVersionUID = -5175966163542099866L;
    private boolean allowMultiValueParameters;
    private String onlyPostParams = "username,password";
    private String paramsToCheck = "ticket,service,renew,gateway,warn,method,target,SAMLart,pgtUrl,pgt,pgtId,pgtIou,targetService,entityId,token";
    private String charactersToForbid = "none";
    private String patternToBlock;
    private Map<String, String> customHeaders = new LinkedHashMap<String, String>(0);
    @NestedConfigurationProperty
    private HttpWebRequestProperties web = new HttpWebRequestProperties();
    @NestedConfigurationProperty
    private HttpHeadersRequestProperties header = new HttpHeadersRequestProperties();
    @NestedConfigurationProperty
    private HttpCorsRequestProperties cors = new HttpCorsRequestProperties();

    @Generated
    public boolean isAllowMultiValueParameters() {
        return this.allowMultiValueParameters;
    }

    @Generated
    public String getOnlyPostParams() {
        return this.onlyPostParams;
    }

    @Generated
    public String getParamsToCheck() {
        return this.paramsToCheck;
    }

    @Generated
    public String getCharactersToForbid() {
        return this.charactersToForbid;
    }

    @Generated
    public String getPatternToBlock() {
        return this.patternToBlock;
    }

    @Generated
    public Map<String, String> getCustomHeaders() {
        return this.customHeaders;
    }

    @Generated
    public HttpWebRequestProperties getWeb() {
        return this.web;
    }

    @Generated
    public HttpHeadersRequestProperties getHeader() {
        return this.header;
    }

    @Generated
    public HttpCorsRequestProperties getCors() {
        return this.cors;
    }

    @Generated
    public HttpRequestProperties setAllowMultiValueParameters(boolean allowMultiValueParameters) {
        this.allowMultiValueParameters = allowMultiValueParameters;
        return this;
    }

    @Generated
    public HttpRequestProperties setOnlyPostParams(String onlyPostParams) {
        this.onlyPostParams = onlyPostParams;
        return this;
    }

    @Generated
    public HttpRequestProperties setParamsToCheck(String paramsToCheck) {
        this.paramsToCheck = paramsToCheck;
        return this;
    }

    @Generated
    public HttpRequestProperties setCharactersToForbid(String charactersToForbid) {
        this.charactersToForbid = charactersToForbid;
        return this;
    }

    @Generated
    public HttpRequestProperties setPatternToBlock(String patternToBlock) {
        this.patternToBlock = patternToBlock;
        return this;
    }

    @Generated
    public HttpRequestProperties setCustomHeaders(Map<String, String> customHeaders) {
        this.customHeaders = customHeaders;
        return this;
    }

    @Generated
    public HttpRequestProperties setWeb(HttpWebRequestProperties web) {
        this.web = web;
        return this;
    }

    @Generated
    public HttpRequestProperties setHeader(HttpHeadersRequestProperties header) {
        this.header = header;
        return this;
    }

    @Generated
    public HttpRequestProperties setCors(HttpCorsRequestProperties cors) {
        this.cors = cors;
        return this;
    }
}

