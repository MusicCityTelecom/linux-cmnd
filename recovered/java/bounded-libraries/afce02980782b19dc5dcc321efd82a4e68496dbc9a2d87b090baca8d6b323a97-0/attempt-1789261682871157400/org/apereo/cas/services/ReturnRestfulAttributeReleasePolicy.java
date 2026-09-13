/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredServiceAttributeReleasePolicyContext
 *  org.apereo.cas.util.serialization.JacksonObjectMapperFactory
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.Generated;
import org.apereo.cas.services.BaseMappedAttributeReleasePolicy;
import org.apereo.cas.services.RegisteredServiceAttributeReleasePolicyContext;
import org.apereo.cas.util.serialization.JacksonObjectMapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class ReturnRestfulAttributeReleasePolicy
extends BaseMappedAttributeReleasePolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(ReturnRestfulAttributeReleasePolicy.class);
    private static final long serialVersionUID = -6249488544306639050L;
    private static final ObjectMapper MAPPER = JacksonObjectMapperFactory.builder().singleValueAsArray(true).build().toObjectMapper();
    private String method = "GET";
    private String endpoint;
    private Map<String, String> headers = new TreeMap<String, String>();

    /*
     * Exception decompiling
     */
    @Override
    public Map<String, List<Object>> getAttributesInternal(RegisteredServiceAttributeReleasePolicyContext context, Map<String, List<Object>> attributes) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK], 8[CATCHBLOCK]], but top level block is 3[TRYBLOCK]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    @Generated
    public String toString() {
        return "ReturnRestfulAttributeReleasePolicy(super=" + super.toString() + ", method=" + this.method + ", endpoint=" + this.endpoint + ", headers=" + this.headers + ")";
    }

    @Generated
    public String getMethod() {
        return this.method;
    }

    @Generated
    public String getEndpoint() {
        return this.endpoint;
    }

    @Generated
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    @Generated
    public ReturnRestfulAttributeReleasePolicy setMethod(String method) {
        this.method = method;
        return this;
    }

    @Generated
    public ReturnRestfulAttributeReleasePolicy setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    @Generated
    public ReturnRestfulAttributeReleasePolicy setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    @Generated
    public ReturnRestfulAttributeReleasePolicy() {
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ReturnRestfulAttributeReleasePolicy)) {
            return false;
        }
        ReturnRestfulAttributeReleasePolicy other = (ReturnRestfulAttributeReleasePolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        String this$method = this.method;
        String other$method = other.method;
        if (this$method == null ? other$method != null : !this$method.equals(other$method)) {
            return false;
        }
        String this$endpoint = this.endpoint;
        String other$endpoint = other.endpoint;
        if (this$endpoint == null ? other$endpoint != null : !this$endpoint.equals(other$endpoint)) {
            return false;
        }
        Map<String, String> this$headers = this.headers;
        Map<String, String> other$headers = other.headers;
        return !(this$headers == null ? other$headers != null : !((Object)this$headers).equals(other$headers));
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof ReturnRestfulAttributeReleasePolicy;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        String $method = this.method;
        result = result * 59 + ($method == null ? 43 : $method.hashCode());
        String $endpoint = this.endpoint;
        result = result * 59 + ($endpoint == null ? 43 : $endpoint.hashCode());
        Map<String, String> $headers = this.headers;
        result = result * 59 + ($headers == null ? 43 : ((Object)$headers).hashCode());
        return result;
    }

    private /* synthetic */ Map lambda$getAttributesInternal$1(RegisteredServiceAttributeReleasePolicyContext context, Map returnedAttributes) {
        return this.authorizeMappedAttributes(context, returnedAttributes);
    }

    private static /* synthetic */ Map lambda$getAttributesInternal$0(Map returnedAttributes) {
        return returnedAttributes;
    }
}

