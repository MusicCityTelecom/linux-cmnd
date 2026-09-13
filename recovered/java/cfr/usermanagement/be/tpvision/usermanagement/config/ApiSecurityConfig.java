/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.usermanagement.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.util.DigestUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@Order(value=50)
public class ApiSecurityConfig
extends WebSecurityConfigurerAdapter {
    private static final String API_KEY = "&&&&&FDSf@#R@#!!!#@$@#$";

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        ((HttpSecurity)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((HttpSecurity)httpSecurity.antMatcher("/api/**").addFilterAfter((Filter)this.apiRequestSignFilter(), HeaderWriterFilter.class)).authorizeRequests().anyRequest()).permitAll().and()).csrf().disable();
    }

    public OncePerRequestFilter apiRequestSignFilter() {
        return new OncePerRequestFilter(){

            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                String sign = request.getParameter("sign");
                if (StringUtils.isBlank(sign)) {
                    return;
                }
                ArrayList<String> paramNameList = new ArrayList<String>(request.getParameterMap().keySet());
                Collections.sort(paramNameList);
                paramNameList.remove("sign");
                StringBuilder signRawStr = new StringBuilder();
                for (String paramName : paramNameList) {
                    String values = StringUtils.join(request.getParameterValues(paramName));
                    signRawStr.append(paramName).append("=").append(values).append("&");
                }
                signRawStr.append(ApiSecurityConfig.API_KEY);
                String checkSign = DigestUtils.md5DigestAsHex(signRawStr.toString().getBytes());
                if (!sign.equalsIgnoreCase(checkSign)) {
                    return;
                }
                filterChain.doFilter(request, response);
            }
        };
    }
}

