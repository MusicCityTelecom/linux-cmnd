/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.boot.web.error.ErrorAttributeOptions
 *  org.springframework.boot.web.servlet.error.ErrorAttributes
 *  org.springframework.boot.web.servlet.error.ErrorController
 *  org.springframework.core.annotation.AnnotationAwareOrderComparator
 *  org.springframework.http.HttpStatus
 *  org.springframework.util.Assert
 *  org.springframework.web.context.request.ServletWebRequest
 *  org.springframework.web.context.request.WebRequest
 *  org.springframework.web.servlet.ModelAndView
 */
package org.springframework.boot.autoconfigure.web.servlet.error;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

public abstract class AbstractErrorController
implements ErrorController {
    private final ErrorAttributes errorAttributes;
    private final List<ErrorViewResolver> errorViewResolvers;

    public AbstractErrorController(ErrorAttributes errorAttributes) {
        this(errorAttributes, null);
    }

    public AbstractErrorController(ErrorAttributes errorAttributes, List<ErrorViewResolver> errorViewResolvers) {
        Assert.notNull((Object)errorAttributes, (String)"ErrorAttributes must not be null");
        this.errorAttributes = errorAttributes;
        this.errorViewResolvers = this.sortErrorViewResolvers(errorViewResolvers);
    }

    private List<ErrorViewResolver> sortErrorViewResolvers(List<ErrorViewResolver> resolvers) {
        ArrayList<ErrorViewResolver> sorted = new ArrayList<ErrorViewResolver>();
        if (resolvers != null) {
            sorted.addAll(resolvers);
            AnnotationAwareOrderComparator.sortIfNecessary(sorted);
        }
        return sorted;
    }

    protected Map<String, Object> getErrorAttributes(HttpServletRequest request, ErrorAttributeOptions options) {
        ServletWebRequest webRequest = new ServletWebRequest(request);
        return this.errorAttributes.getErrorAttributes((WebRequest)webRequest, options);
    }

    protected boolean getTraceParameter(HttpServletRequest request) {
        return this.getBooleanParameter(request, "trace");
    }

    protected boolean getMessageParameter(HttpServletRequest request) {
        return this.getBooleanParameter(request, "message");
    }

    protected boolean getErrorsParameter(HttpServletRequest request) {
        return this.getBooleanParameter(request, "errors");
    }

    protected boolean getBooleanParameter(HttpServletRequest request, String parameterName) {
        String parameter = request.getParameter(parameterName);
        if (parameter == null) {
            return false;
        }
        return !"false".equalsIgnoreCase(parameter);
    }

    protected HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf((int)statusCode);
        }
        catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    protected ModelAndView resolveErrorView(HttpServletRequest request, HttpServletResponse response, HttpStatus status, Map<String, Object> model) {
        for (ErrorViewResolver resolver : this.errorViewResolvers) {
            ModelAndView modelAndView = resolver.resolveErrorView(request, status, model);
            if (modelAndView == null) continue;
            return modelAndView;
        }
        return null;
    }
}

