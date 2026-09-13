/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.config;

import be.tpvision.smartcontrol.messages.config.ajax_session_timeout_redirect_filter.DoFilterMessages;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

public class AjaxSessionTimeoutRedirectFilter
extends GenericFilterBean {
    private static final Logger logger = LoggerFactory.getLogger(AjaxSessionTimeoutRedirectFilter.class);
    private ThrowableAnalyzer throwableAnalyzer;
    private AuthenticationTrustResolver authenticationTrustResolver;
    private int sessionExpiredErrorCode = HttpStatus.I_AM_A_TEAPOT.value();

    public AjaxSessionTimeoutRedirectFilter() {
        this.throwableAnalyzer = new DefaultThrowableAnalyzer();
        this.authenticationTrustResolver = new AuthenticationTrustResolverImpl();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Assert.notNull((Object)servletRequest, DoFilterMessages.SERVLET_REQUEST_CAN_NOT_BE_NULL);
        Assert.notNull((Object)servletResponse, DoFilterMessages.SERVLET_RESPONSE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)filterChain, DoFilterMessages.FILTER_CHAIN_CAN_NOT_BE_NULL);
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        catch (IOException ioException) {
            throw ioException;
        }
        catch (Exception exception) {
            Throwable[] causeChain = this.throwableAnalyzer.determineCauseChain(exception);
            RuntimeException runtimeException = (AuthenticationException)this.throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class, causeChain);
            if (runtimeException == null) {
                runtimeException = (AccessDeniedException)this.throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
            }
            if (runtimeException != null) {
                if (runtimeException instanceof AuthenticationException) {
                    throw runtimeException;
                }
                if (runtimeException instanceof AccessDeniedException) {
                    SecurityContext securityContext = SecurityContextHolder.getContext();
                    Assert.state(securityContext != null, DoFilterMessages.SECURITY_CONTEXT_CAN_NOT_BE_NULL);
                    Authentication authentication = securityContext.getAuthentication();
                    Assert.state(authentication != null, DoFilterMessages.AUTHENTICATION_CAN_NOT_BE_NULL);
                    boolean isAnonymous = this.authenticationTrustResolver.isAnonymous(authentication);
                    if (!isAnonymous) {
                        throw runtimeException;
                    }
                    HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
                    String xRequestedWithHeader = httpServletRequest.getHeader("X-Requested-With");
                    boolean isAjaxRequest = "XMLHttpRequest".equals(xRequestedWithHeader);
                    if (!isAjaxRequest) {
                        throw runtimeException;
                    }
                    logger.debug("Ajax call detected, sending {} error code.", (Object)this.sessionExpiredErrorCode);
                    HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
                    httpServletResponse.sendError(this.sessionExpiredErrorCode);
                    return;
                }
            }
            throw exception;
        }
    }

    public void setSessionExpiredErrorCode(int sessionExpiredErrorCode) {
        this.sessionExpiredErrorCode = sessionExpiredErrorCode;
    }

    private static final class DefaultThrowableAnalyzer
    extends ThrowableAnalyzer {
        private DefaultThrowableAnalyzer() {
        }

        @Override
        protected void initExtractorMap() {
            super.initExtractorMap();
            this.registerExtractor(ServletException.class, throwable -> {
                ThrowableAnalyzer.verifyThrowableHierarchy(throwable, ServletException.class);
                ServletException servletException = (ServletException)throwable;
                return servletException.getRootCause();
            });
        }
    }
}

