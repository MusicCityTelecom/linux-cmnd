/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Splitter
 *  javax.servlet.Filter
 *  javax.servlet.FilterChain
 *  javax.servlet.FilterConfig
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServletRequest
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.util.RegexUtils
 *  org.springframework.http.HttpRequest
 *  org.springframework.http.server.ServletServerHttpRequest
 *  org.springframework.web.util.UriComponentsBuilder
 */
package org.apereo.cas.web.support.filters;

import com.google.common.base.Splitter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.util.RegexUtils;
import org.apereo.cas.web.support.filters.AbstractSecurityFilter;
import org.springframework.http.HttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.util.UriComponentsBuilder;

public class RequestParameterPolicyEnforcementFilter
extends AbstractSecurityFilter
implements Filter {
    public static final String DEFAULT_CHARACTERS_BLOCKED = "? & # %";
    public static final String PARAMETERS_TO_CHECK = "parametersToCheck";
    public static final String CHARACTERS_TO_FORBID = "charactersToForbid";
    public static final String ALLOW_MULTI_VALUED_PARAMETERS = "allowMultiValuedParameters";
    public static final String PATTERN_TO_BLOCK = "patternToBlock";
    public static final String ONLY_POST_PARAMETERS = "onlyPostParameters";
    private Pattern patternToBlock;
    private Set<String> parametersToCheck;
    private Set<Character> charactersToForbid;
    private boolean allowMultiValueParameters;
    private Set<String> onlyPostParameters;

    public static void throwIfUnrecognizedParamName(Enumeration initParamNames) {
        HashSet<String> recognizedParameterNames = new HashSet<String>();
        recognizedParameterNames.add(ALLOW_MULTI_VALUED_PARAMETERS);
        recognizedParameterNames.add(PARAMETERS_TO_CHECK);
        recognizedParameterNames.add(ONLY_POST_PARAMETERS);
        recognizedParameterNames.add(CHARACTERS_TO_FORBID);
        recognizedParameterNames.add("throwOnError");
        recognizedParameterNames.add(PATTERN_TO_BLOCK);
        while (initParamNames.hasMoreElements()) {
            String initParamName = (String)initParamNames.nextElement();
            if (recognizedParameterNames.contains(initParamName)) continue;
            RequestParameterPolicyEnforcementFilter.logException((Exception)new ServletException("Unrecognized init parameter [" + initParamName + "]."));
        }
    }

    public static Set<String> parseParametersList(String initParamValue, boolean allowWildcard) {
        if (null == initParamValue) {
            return new HashSet<String>(0);
        }
        if (initParamValue.trim().isEmpty()) {
            RequestParameterPolicyEnforcementFilter.logException(new IllegalArgumentException("[" + initParamValue + "] had no tokens but should have had at least one token."));
        }
        List tokens = Splitter.onPattern((String)"\\s+").splitToList((CharSequence)initParamValue.trim());
        if (allowWildcard && 1 == tokens.size() && "*".equals(tokens.get(0))) {
            return new HashSet<String>(0);
        }
        HashSet<String> parameterNames = new HashSet<String>();
        for (String parameterName : tokens) {
            if ("*".equals(parameterName)) {
                RequestParameterPolicyEnforcementFilter.logException(new IllegalArgumentException("Star token encountered among other tokens in parsing [" + initParamValue + "]"));
            }
            parameterNames.add(parameterName);
        }
        return parameterNames;
    }

    public static Set<Character> parseCharactersToForbid(String value) {
        HashSet<Character> charactersToForbid = new HashSet<Character>();
        String paramValue = value;
        if (paramValue == null) {
            paramValue = DEFAULT_CHARACTERS_BLOCKED;
        } else if (paramValue.trim().isEmpty()) {
            RequestParameterPolicyEnforcementFilter.logException(new IllegalArgumentException("Expected tokens when parsing [" + paramValue + "] but found no tokens."));
        }
        if ("none".equals(paramValue)) {
            return charactersToForbid;
        }
        List tokens = Splitter.onPattern((String)"\\s+").splitToList((CharSequence)paramValue);
        for (String token : tokens) {
            if (token.length() > 1) {
                RequestParameterPolicyEnforcementFilter.logException(new IllegalArgumentException("Expected tokens of length 1 but found [" + token + "] when parsing [" + paramValue + "]"));
            }
            char character = token.charAt(0);
            charactersToForbid.add(Character.valueOf(character));
        }
        return charactersToForbid;
    }

    public static void requireNotMultiValued(Set<String> parametersToCheck, Map parameterMap) {
        for (String parameterName : parametersToCheck) {
            Object[] values;
            if (!parameterMap.containsKey(parameterName) || (values = (String[])parameterMap.get(parameterName)).length <= 1) continue;
            RequestParameterPolicyEnforcementFilter.logException(new IllegalStateException("Parameter [" + parameterName + "] had multiple values [" + Arrays.toString(values) + "] but at most one value is allowable."));
        }
    }

    public static void enforceParameterContentCharacterRestrictions(Set<String> parametersToCheck, Set<Character> charactersToForbid, Map parameterMap) {
        if (charactersToForbid.isEmpty()) {
            return;
        }
        for (String parameterToCheck : parametersToCheck) {
            String[] parameterValues = (String[])parameterMap.get(parameterToCheck);
            if (null == parameterValues) continue;
            for (String parameterValue : parameterValues) {
                for (Character forbiddenCharacter : charactersToForbid) {
                    if (!parameterValue.contains(forbiddenCharacter.toString())) continue;
                    RequestParameterPolicyEnforcementFilter.logException(new IllegalArgumentException("Disallowed character [" + forbiddenCharacter + "] found in value [" + parameterValue + "] of parameter named [" + parameterToCheck + "]"));
                }
            }
        }
    }

    public static void checkOnlyPostParameters(String method, Map parameterMap, Set<String> onlyPostParameters) {
        if (!"POST".equals(method)) {
            Set names = parameterMap.keySet();
            for (String onlyPostParameter : onlyPostParameters) {
                if (!names.contains(onlyPostParameter)) continue;
                RequestParameterPolicyEnforcementFilter.logException(new IllegalArgumentException(onlyPostParameter + " parameter should only be used in POST requests"));
            }
        }
    }

    public void init(FilterConfig filterConfig) {
        String failSafeParam = filterConfig.getInitParameter("throwOnError");
        if (null != failSafeParam) {
            RequestParameterPolicyEnforcementFilter.setThrowOnErrors(Boolean.parseBoolean(failSafeParam));
        }
        Enumeration initParamNames = filterConfig.getInitParameterNames();
        RequestParameterPolicyEnforcementFilter.throwIfUnrecognizedParamName(initParamNames);
        String initParamAllowMultiValuedParameters = filterConfig.getInitParameter(ALLOW_MULTI_VALUED_PARAMETERS);
        String initParamParametersToCheck = filterConfig.getInitParameter(PARAMETERS_TO_CHECK);
        String initParamOnlyPostParameters = filterConfig.getInitParameter(ONLY_POST_PARAMETERS);
        String initParamCharactersToForbid = filterConfig.getInitParameter(CHARACTERS_TO_FORBID);
        String initParamPatternToBlock = filterConfig.getInitParameter(PATTERN_TO_BLOCK);
        this.patternToBlock = StringUtils.isNotBlank((CharSequence)initParamPatternToBlock) ? RegexUtils.createPattern((String)initParamPatternToBlock) : null;
        this.allowMultiValueParameters = Boolean.parseBoolean(initParamAllowMultiValuedParameters);
        this.parametersToCheck = RequestParameterPolicyEnforcementFilter.parseParametersList(initParamParametersToCheck, true);
        this.onlyPostParameters = RequestParameterPolicyEnforcementFilter.parseParametersList(initParamOnlyPostParameters, false);
        this.charactersToForbid = RequestParameterPolicyEnforcementFilter.parseCharactersToForbid(initParamCharactersToForbid);
        if (this.allowMultiValueParameters && this.charactersToForbid.isEmpty()) {
            RequestParameterPolicyEnforcementFilter.logException((Exception)new ServletException("Configuration to allow multi-value parameters and forbid no characters makes " + this.getClass().getSimpleName() + " a no-op"));
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            if (request instanceof HttpServletRequest) {
                HttpServletRequest httpServletRequest = (HttpServletRequest)request;
                Map parameterMap = httpServletRequest.getParameterMap();
                this.blockRequestIfNecessary(httpServletRequest);
                HashSet<String> parametersToCheckHere = new HashSet<String>();
                if (this.parametersToCheck.isEmpty()) {
                    parametersToCheckHere.addAll(parameterMap.keySet());
                } else {
                    parametersToCheckHere.addAll(this.parametersToCheck);
                }
                if (!this.allowMultiValueParameters) {
                    RequestParameterPolicyEnforcementFilter.requireNotMultiValued(parametersToCheckHere, parameterMap);
                }
                RequestParameterPolicyEnforcementFilter.enforceParameterContentCharacterRestrictions(parametersToCheckHere, this.charactersToForbid, parameterMap);
                RequestParameterPolicyEnforcementFilter.checkOnlyPostParameters(httpServletRequest.getMethod(), parameterMap, this.onlyPostParameters);
            }
        }
        catch (Exception e) {
            RequestParameterPolicyEnforcementFilter.logException((Exception)new ServletException(this.getClass().getSimpleName() + " is blocking this request.", (Throwable)e));
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }

    private void blockRequestIfNecessary(HttpServletRequest httpServletRequest) {
        if (this.patternToBlock != null && StringUtils.isNotBlank((CharSequence)httpServletRequest.getRequestURI())) {
            String uri = UriComponentsBuilder.fromHttpRequest((HttpRequest)new ServletServerHttpRequest(httpServletRequest)).build().toUriString();
            if (!this.patternToBlock.equals(RegexUtils.MATCH_NOTHING_PATTERN) && this.patternToBlock.matcher(uri).find()) {
                RequestParameterPolicyEnforcementFilter.logException((Exception)new ServletException("The request is blocked as it matches a prohibited pattern"));
            }
        }
    }

    @Generated
    public void setPatternToBlock(Pattern patternToBlock) {
        this.patternToBlock = patternToBlock;
    }

    @Generated
    public void setParametersToCheck(Set<String> parametersToCheck) {
        this.parametersToCheck = parametersToCheck;
    }

    @Generated
    public void setCharactersToForbid(Set<Character> charactersToForbid) {
        this.charactersToForbid = charactersToForbid;
    }

    @Generated
    public void setAllowMultiValueParameters(boolean allowMultiValueParameters) {
        this.allowMultiValueParameters = allowMultiValueParameters;
    }

    @Generated
    public void setOnlyPostParameters(Set<String> onlyPostParameters) {
        this.onlyPostParameters = onlyPostParameters;
    }

    @Generated
    public Pattern getPatternToBlock() {
        return this.patternToBlock;
    }

    @Generated
    public Set<String> getParametersToCheck() {
        return this.parametersToCheck;
    }

    @Generated
    public Set<Character> getCharactersToForbid() {
        return this.charactersToForbid;
    }

    @Generated
    public boolean isAllowMultiValueParameters() {
        return this.allowMultiValueParameters;
    }

    @Generated
    public Set<String> getOnlyPostParameters() {
        return this.onlyPostParameters;
    }
}

