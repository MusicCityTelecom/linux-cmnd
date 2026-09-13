/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.annotation.WebFilter
 */
package com.tpvision.smartinstall.filter;

import com.tpvision.smartinstall.api.ApiConstants;
import com.tpvision.smartinstall.api.ApiErrorCode;
import com.tpvision.smartinstall.api.ApiLicenseChecker;
import com.tpvision.smartinstall.api.ApiLogger;
import com.tpvision.smartinstall.api.LicenseStatus;
import com.tpvision.smartinstall.japit.SecuredCmdControlManager;
import com.tpvision.smartinstall.util.Utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter(urlPatterns={"/api/*", "/exapi/*"})
public class ApiJwtAuthFilter
implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(ApiJwtAuthFilter.class);
    private static final List<String> SKIP_JWT_CHECK_URL_PREFIX = Arrays.asList("/api/user", "/exapi/login", "/api/pms/showMessageIcon", "/api/mychoice/showTemplateIcon", "/api/reception/socket").stream().map(String::toLowerCase).collect(Collectors.toList());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String path = httpRequest.getRequestURI().replaceFirst(httpRequest.getContextPath(), "").toLowerCase();
        ApiErrorCode failureResult = null;
        if (httpRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
            failureResult = ApiErrorCode.SUCCESS_OK;
        }
        ApiLogger.logApiStart(httpRequest);
        if (failureResult == null) {
            failureResult = this.checkApiLicense(path);
        }
        if (failureResult == null) {
            failureResult = this.checkExApiSwitch(httpRequest.getScheme(), path);
        }
        if (failureResult == null && !this.isSkipCheckJwt(path)) {
            failureResult = this.checkJwtToken(httpRequest, path, failureResult);
        }
        if (failureResult == null) {
            failureResult = this.checkApiVersion(httpRequest, path);
        }
        if (failureResult != null) {
            HttpServletResponse httpResponse = (HttpServletResponse)response;
            httpResponse.setStatus(failureResult.getStatus().value());
            String failureInfo = JSONObject.valueToString(failureResult.toMap());
            ApiLogger.logFilterFailure(httpResponse.getStatus(), failureInfo);
            Utils.writeJsonToResponse(failureInfo, httpResponse);
        } else {
            chain.doFilter(request, response);
        }
    }

    private ApiErrorCode checkApiVersion(HttpServletRequest request, String path) {
        int[] backwardSupportVersions;
        int currentVersion;
        int apiVer = 1;
        try {
            apiVer = Integer.parseInt(request.getHeader("x-api-version"));
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (this.isExApi(path)) {
            currentVersion = 3;
            backwardSupportVersions = ApiConstants.EXAPI_BACKWARD_SUPPORT_VERSIONS;
        } else {
            currentVersion = 6;
            backwardSupportVersions = ApiConstants.API_BACKWARD_SUPPORT_VERSIONS;
        }
        if (apiVer > currentVersion) {
            return ApiErrorCode.CLIENT_API_VERSION_TOO_HIGH;
        }
        if (apiVer != currentVersion && !ArrayUtils.contains(backwardSupportVersions, apiVer)) {
            return ApiErrorCode.SERVER_API_NOT_COMPATIBLE_WITH_CLINET_API_VERSION;
        }
        return null;
    }

    private ApiErrorCode checkExApiSwitch(String schema, String path) {
        if (this.isExApi(path)) {
            if (!StringUtils.equalsIgnoreCase("https", schema)) {
                return ApiErrorCode.REQUEST_HTTPS_REQURED;
            }
            if (!ApiLicenseChecker.getInstance().isExApiSwithOpen()) {
                return ApiErrorCode.EXAPI_SWITCH_CLOSED;
            }
        }
        return null;
    }

    private boolean isExApi(String path) {
        return path.startsWith("/exapi");
    }

    private boolean isSkipCheckJwt(String path) {
        for (String prefix : SKIP_JWT_CHECK_URL_PREFIX) {
            if (!path.startsWith(prefix)) continue;
            return true;
        }
        return false;
    }

    private ApiErrorCode checkApiLicense(String path) {
        LicenseStatus checkResult = ApiLicenseChecker.getInstance().check(path);
        switch (checkResult) {
            case NO_LICENSE: {
                return ApiErrorCode.NO_LICENSE;
            }
            case EXPIRE_LICENSE: {
                return ApiErrorCode.EXPIRED_LICENSE;
            }
            case DEVICE_LIMIT_REACHED: {
                return ApiErrorCode.DEVICE_LIMIT_REACHED;
            }
        }
        return null;
    }

    private ApiErrorCode checkJwtToken(HttpServletRequest httpRequest, String path, ApiErrorCode failureResult) {
        String authorization = httpRequest.getHeader("Authorization");
        if (StringUtils.isNotBlank(authorization) && authorization.startsWith("Bearer ")) {
            String token = authorization.replaceFirst("Bearer ", "");
            String key = SecuredCmdControlManager.SECURIT_KEY;
            try {
                String subject = ((Claims)Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody()).getSubject();
                if (!StringUtils.equalsIgnoreCase(subject, this.isExApi(path) ? "cmnd rest exapi" : "cmnd rest api")) {
                    failureResult = ApiErrorCode.JWT_TOKEN_SUBJECT_ERROR;
                }
            }
            catch (ExpiredJwtException ex) {
                logger.error("token:{} expire", (Object)token);
                failureResult = ApiErrorCode.JWT_TOKEN_EXPIRE;
            }
            catch (MalformedJwtException ex) {
                logger.error("token:{} format error", (Object)token);
                failureResult = ApiErrorCode.JWT_TOKEN_FORMATE_ERROR;
            }
            catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
                failureResult = ApiErrorCode.JWT_TOKEN_ERROR;
            }
        } else {
            logger.info("url: {} invalid token info: {}", (Object)path, (Object)authorization);
            failureResult = ApiErrorCode.JWT_EMPTY;
        }
        return failureResult;
    }

    @Override
    public void destroy() {
    }
}

