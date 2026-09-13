/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.FilterChain
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.core.log.LogMessage
 *  org.springframework.util.Assert
 *  org.springframework.web.filter.OncePerRequestFilter
 */
package org.springframework.security.web.authentication.ui;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.log.LogMessage;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

public class DefaultLogoutPageGeneratingFilter
extends OncePerRequestFilter {
    private RequestMatcher matcher = new AntPathRequestMatcher("/logout", "GET");
    private Function<HttpServletRequest, Map<String, String>> resolveHiddenInputs = request -> Collections.emptyMap();

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (this.matcher.matches(request)) {
            this.renderLogout(request, response);
        } else {
            if (this.logger.isTraceEnabled()) {
                this.logger.trace((Object)LogMessage.format((String)"Did not render default logout page since request did not match [%s]", (Object)this.matcher));
            }
            filterChain.doFilter((ServletRequest)request, (ServletResponse)response);
        }
    }

    private void renderLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n");
        sb.append("<html lang=\"en\">\n");
        sb.append("  <head>\n");
        sb.append("    <meta charset=\"utf-8\">\n");
        sb.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n");
        sb.append("    <meta name=\"description\" content=\"\">\n");
        sb.append("    <meta name=\"author\" content=\"\">\n");
        sb.append("    <title>Confirm Log Out?</title>\n");
        sb.append("    <link href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M\" crossorigin=\"anonymous\">\n");
        sb.append("    <link href=\"https://getbootstrap.com/docs/4.0/examples/signin/signin.css\" rel=\"stylesheet\" crossorigin=\"anonymous\"/>\n");
        sb.append("  </head>\n");
        sb.append("  <body>\n");
        sb.append("     <div class=\"container\">\n");
        sb.append("      <form class=\"form-signin\" method=\"post\" action=\"" + request.getContextPath() + "/logout\">\n");
        sb.append("        <h2 class=\"form-signin-heading\">Are you sure you want to log out?</h2>\n");
        sb.append(this.renderHiddenInputs(request) + "        <button class=\"btn btn-lg btn-primary btn-block\" type=\"submit\">Log Out</button>\n");
        sb.append("      </form>\n");
        sb.append("    </div>\n");
        sb.append("  </body>\n");
        sb.append("</html>");
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(sb.toString());
    }

    public void setResolveHiddenInputs(Function<HttpServletRequest, Map<String, String>> resolveHiddenInputs) {
        Assert.notNull(resolveHiddenInputs, (String)"resolveHiddenInputs cannot be null");
        this.resolveHiddenInputs = resolveHiddenInputs;
    }

    private String renderHiddenInputs(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> input : this.resolveHiddenInputs.apply(request).entrySet()) {
            sb.append("<input name=\"");
            sb.append(input.getKey());
            sb.append("\" type=\"hidden\" value=\"");
            sb.append(input.getValue());
            sb.append("\" />\n");
        }
        return sb.toString();
    }
}

